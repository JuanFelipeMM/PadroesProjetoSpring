package com.dio.Application.service.impl;

import java.util.Optional;

import org.apache.catalina.startup.ClassLoaderFactory.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dio.Application.model.Cliente;
import com.dio.Application.model.ClienteRepository;
import com.dio.Application.model.Endereco;
import com.dio.Application.model.EnderecoRepository;
import com.dio.Application.service.ClienteService;
import com.dio.Application.service.ViaCepService;

@Service
public class ClienteServiceImpl implements ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private EnderecoRepository enderecoRepository;

	@Autowired
	private ViaCepService viaCepService;

	@Override
	public Iterable<Cliente> buscarTodos() {
		return clienteRepository.findAll();
	}

	@Override
	public Cliente buscarPorId(Long id) {
		return clienteRepository.findById(id).get();
	}

	@Override
	public void inserir(Cliente cliente) {
		String cep = cliente.getEndereco().getCep();
		Endereco endereco = enderecoRepository.findById(Long.parseLong(cep)).orElseGet(() -> {
			Endereco novoEndereco = viaCepService.consultarCep(cep.toString());
			enderecoRepository.save(novoEndereco);
			return novoEndereco;
		});
		cliente.setEndereco(endereco);
		clienteRepository.save(cliente);
	}

	@Override
	public void atualizar(Long id, Cliente cliente) {
		Optional<Cliente> clienteBd = clienteRepository.findById(id);
		if (clienteBd.isPresent()) {
			salvarClienteComCep(cliente);
		}

	}

	private void salvarClienteComCep(Cliente cliente) {

		String cep = cliente.getEndereco().getCep();
		Endereco endereco = enderecoRepository.findById(Long.parseLong(cep)).orElseGet(() -> {

			Endereco novoEndereco = viaCepService.consultarCep(cep);
			enderecoRepository.save(novoEndereco);
			return novoEndereco;
		});
		cliente.setEndereco(endereco);

		clienteRepository.save(cliente);

	}

	@Override
	public void deletar(Long id) {
		clienteRepository.deleteById(id);

	}

}
