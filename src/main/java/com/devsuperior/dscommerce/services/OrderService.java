package com.devsuperior.dscommerce.services;

import java.time.Instant;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dscommerce.dto.OrderDTO;
import com.devsuperior.dscommerce.dto.OrderItemDTO;
import com.devsuperior.dscommerce.entities.Order;
import com.devsuperior.dscommerce.entities.OrderItem;
import com.devsuperior.dscommerce.entities.OrderStatus;
import com.devsuperior.dscommerce.entities.Product;
import com.devsuperior.dscommerce.entities.User;
import com.devsuperior.dscommerce.repositories.OrderItemRepository;
import com.devsuperior.dscommerce.repositories.OrderRepository;
import com.devsuperior.dscommerce.repositories.ProductRepository;
import com.devsuperior.dscommerce.services.exception.ResourceNotFoundException;

@Service
public class OrderService {

	@Autowired
	private OrderRepository repository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private OrderItemRepository orderItemRepository;
	
	@Autowired
	private AuthService authService;
	
	@Transactional(readOnly = true)
	public OrderDTO findById(Long id) {
		Optional<Order> result = repository.findById(id); //Optional recebe resultado da buscar por id a partir do id
		Order order = result.orElseThrow(
				() -> new ResourceNotFoundException("Recurso não encontrado.")); // Product product recebe o resultado (objeto) que esta no Optional OU lança uma exceção personalizada.
		//antes de retornar, tem que testar (ou é admin ou é o dono do pedido)
		authService.validadeSelfOrAdmin(order.getClient().getId());
		//tudo certo, vida que segue. Caso contrário, authService lança exceção, corta execução.
		OrderDTO dto = new OrderDTO(order); // productDTO recebe uma copia do Product que era resultado da busca por id
		return dto; // e o método devolve um ProductDTO dto  para o controller que chamou o service ao invés de um Product da camada de entidades
	}

	@Transactional
	public OrderDTO insert(OrderDTO dto) {
		
		Order order = new Order();
		
		order.setMoment(Instant.now());
		order.setStatus(OrderStatus.WAITING_PAYMENT);
		
		User user = userService.authenticated();
		order.setClient(user);
		
		for (OrderItemDTO itemDto : dto.getItems()) {
			Product product = productRepository.getReferenceById(itemDto.getProductId());
			OrderItem item = new OrderItem(order, product, itemDto.getQuantity(), product.getPrice());
			order.getItems().add(item);
		}
		
		repository.save(order);
		orderItemRepository.saveAll(order.getItems());
		
		return new OrderDTO(order);
	}
	
}
