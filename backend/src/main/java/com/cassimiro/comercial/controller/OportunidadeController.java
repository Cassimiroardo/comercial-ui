package com.cassimiro.comercial.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.cassimiro.comercial.model.Oportunidade;
import com.cassimiro.comercial.repository.OportunidadeRepository;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/oportunidades")
public class OportunidadeController {

	@Autowired
	private OportunidadeRepository oportunidades;
	
	@GetMapping
	public List<Oportunidade> listar(){
		return oportunidades.findAll() ;
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Oportunidade> buscar(@Validated @PathVariable Long id){
			Optional<Oportunidade> oportunidade = oportunidades.findById(id);
			
			if(oportunidade.isEmpty()) return ResponseEntity.notFound().build();
			else return ResponseEntity.ok(oportunidade.get());
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Oportunidade adicionar(@RequestBody Oportunidade oportunidade) {
		Optional<Oportunidade> oportunidadeExistente = oportunidades
				.findByDescricaoAndNomeProspecto(oportunidade.getDescricao(), 
						oportunidade.getNomeProspecto());
		
		if(oportunidadeExistente.isPresent()) { throw new ResponseStatusException(HttpStatus.BAD_REQUEST, 
				"OPORTUNIDADE JA EXISTENTE!"); }
		
		else return oportunidades.save(oportunidade);
	}
}
