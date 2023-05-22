package com.algaworks.algalog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algalog.domain.model.Cliente;
import com.algaworks.algalog.domain.repository.ClienteRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/clientes")
public class ClienteController {

	@PersistenceContext
	private EntityManager manager;
	
	@Autowired
	private ClienteRepository clienteRepository;
		
	@GetMapping
	public List<Cliente> findAll() {
		return clienteRepository.findAll();	
	} 
	
	@GetMapping("/{id}")
	public ResponseEntity<Cliente> findById(@PathVariable Long id) {
		return  
				clienteRepository.findById(id)
					.map(ResponseEntity::ok)
					.orElse(ResponseEntity.notFound().build());
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cliente add(@RequestBody Cliente cliente) {
		return clienteRepository.save(cliente);
	}
	
	@PutMapping("{id}")
	public ResponseEntity<Cliente> update(@PathVariable Long id, @RequestBody Cliente cliente) {
		if(!clienteRepository.existsById(id)) 
			return ResponseEntity.notFound().build();
		
		//cliente.setId(id);
		return ResponseEntity.ok(clienteRepository.save(cliente));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		if(!clienteRepository.existsById(id)) 
			return ResponseEntity.notFound().build();
		
		clienteRepository.deleteById(id);
		
		return ResponseEntity.noContent().build();
	}
	
	// METODOS DE TESTE
	@GetMapping("/findAllManager")
	public List<Cliente> findAllManager() {
		return manager.createQuery("from Cliente", Cliente.class)
				.getResultList();	
	} 
	
	@GetMapping("/byNome/{nome}")
	public List<Cliente> findByNome(@PathVariable String nome) {
		return clienteRepository.findByNome(nome);
	}
	
	
}
