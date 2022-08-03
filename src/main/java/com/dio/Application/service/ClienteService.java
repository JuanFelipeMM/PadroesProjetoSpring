package com.dio.Application.service;

import org.springframework.stereotype.Service;

import com.dio.Application.model.Cliente;

@Service
public interface ClienteService {
    Iterable<Cliente> buscarTodos();

    Cliente buscarPorId(Long id);

    void inserir(Cliente cliente);

    void atualizar(Long id, Cliente cliente);

    void deletar(Long id);
}