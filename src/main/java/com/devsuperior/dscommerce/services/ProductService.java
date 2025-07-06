package com.devsuperior.dscommerce.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dscommerce.dto.CategoryDTO;
import com.devsuperior.dscommerce.dto.ProductDTO;
import com.devsuperior.dscommerce.dto.ProductMinDTO;
import com.devsuperior.dscommerce.entities.Category;
import com.devsuperior.dscommerce.entities.Product;
import com.devsuperior.dscommerce.repositories.ProductRepository;
import com.devsuperior.dscommerce.services.exception.DatabaseException;
import com.devsuperior.dscommerce.services.exception.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository repository;
	
	@Transactional(readOnly = true)
	public ProductDTO findById(Long id) {
		Optional<Product> result = repository.findById(id); //Optional recebe resultado da buscar por id a partir do id
		Product product = result.orElseThrow(
				() -> new ResourceNotFoundException("Recurso não encontrado.")); // Product product recebe o resultado (objeto) que esta no Optional OU lança uma exceção personalizada.
		ProductDTO dto = new ProductDTO(product); // productDTO recebe uma copia do Product que era resultado da busca por id
		return dto; // e o método devolve um ProductDTO dto  para o controller que chamou o service ao invés de um Product da camada de entidades
	}
	
	@Transactional(readOnly = true)
	public Page<ProductMinDTO> findAll(String name, Pageable pageable) {
		Page<Product> result = repository.searchByName(name, pageable);
		return result.map(x -> new ProductMinDTO(x));
	}
	
	@Transactional
	public ProductDTO insert(ProductDTO dto) {
		
		Product entity = new Product(); //Instanciou
		copyDtoToEntity(dto, entity); //Copiou
		entity = repository.save(entity);//Salvou
		return new ProductDTO(entity);	
	}
	
	@Transactional
	public ProductDTO update(Long id, ProductDTO dto) {
		try {
			Product entity = repository.getReferenceById(id); //Instanciou pela referência, sem buscar no banco de dados como no método findById()
			copyDtoToEntity(dto, entity); //Copiou
			entity = repository.save(entity); //Salvou
			return new ProductDTO(entity);	
		} 
		catch (EntityNotFoundException e)	{
			 throw new ResourceNotFoundException("Recurso não encontrado");
	}
	}

	private void copyDtoToEntity(ProductDTO dto, Product entity) {
		entity.setName(dto.getName());
		entity.setDescription(dto.getDescription());
		entity.setPrice(dto.getPrice());
		entity.setImgUrl(dto.getImgUrl());
		//limpar a lista que já existe na entity - especial no caso de update
		entity.getCategories().clear();
		for (CategoryDTO catDto : dto.getCategories()) {
			Category cat = new Category();
			cat.setId(catDto.getId());
			entity.getCategories().add(cat);
		}
	}
	
	@Transactional(propagation = Propagation.SUPPORTS)
	public void delete(Long id) {
		if (!repository.existsById(id)) {
			throw new ResourceNotFoundException("Recurso não encontrado");
		}
		try {
			repository.deleteById(id);
		}
		catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Falha na integridade referencial");
		}
	}
	
}
