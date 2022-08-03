package com.dio.Application.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.dio.Application.model.Endereco;

@Component
@FeignClient(name = "viacep", url = "https://viacep.com.br/ws")
public interface ViaCepService {
    
	@GetMapping("/{cep}/json/") 
	Endereco consultarCep(@PathVariable("cep") String cep);
}