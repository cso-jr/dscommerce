package com.devsuperior.dscommerce.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dscommerce.dto.OrderDTO;
import com.devsuperior.dscommerce.entities.Order;
import com.devsuperior.dscommerce.repositories.OrderRepository;
import com.devsuperior.dscommerce.services.exception.ResourceNotFoundException;

@Service
public class OrderService {

	@Autowired
	private OrderRepository repository;
	
	@Transactional(readOnly = true)
	public OrderDTO findById(Long id) {
		Optional<Order> result = repository.findById(id); //Optional recebe resultado da buscar por id a partir do id
		Order order = result.orElseThrow(
				() -> new ResourceNotFoundException("Recurso não encontrado.")); // Product product recebe o resultado (objeto) que esta no Optional OU lança uma exceção personalizada.
		OrderDTO dto = new OrderDTO(order); // productDTO recebe uma copia do Product que era resultado da busca por id
		return dto; // e o método devolve um ProductDTO dto  para o controller que chamou o service ao invés de um Product da camada de entidades
	}
	
}
