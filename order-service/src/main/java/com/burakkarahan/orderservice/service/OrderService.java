package com.burakkarahan.orderservice.service;

import com.burakkarahan.orderservice.dto.OrderLineItemsDto;
import com.burakkarahan.orderservice.dto.OrderRequest;
import com.burakkarahan.orderservice.event.OrderPlacedEvent;
import com.burakkarahan.orderservice.model.Order;
import com.burakkarahan.orderservice.model.OrderLineItems;
import com.burakkarahan.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;
    private final RabbitTemplate rabbitTemplate;

    @CircuitBreaker(name = "inventory", fallbackMethod = "fallbackMethod")
    public String placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList()
                .stream()
                .map(this::mapToDto)
                .toList();

        order.setOrderLineItemsList(orderLineItems);

        boolean allProductsInStock = orderLineItems.stream().allMatch(item -> {
            Boolean result = webClientBuilder.build().get()
                    .uri("http://inventory-service/api/inventory/" + item.getSkuCode())
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .block();

            return Boolean.TRUE.equals(result);
        });

        if(allProductsInStock) {
            orderRepository.save(order);
            rabbitTemplate.convertAndSend("notificationQueue", new OrderPlacedEvent(order.getOrderNumber()));
            return "✅ Sipariş başarıyla kaydedildi ve RabbitMQ'ya mesaj atıldı!";
        } else {
            throw new IllegalArgumentException("❌ Ürün stokta yok, sipariş alınamadı!");
        }
    }

    public String fallbackMethod(OrderRequest orderRequest, RuntimeException runtimeException) {
        return "⚠️ HATA: Stok servisine şu an ulaşılamıyor. Lütfen daha sonra tekrar deneyiniz.";
    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        return orderLineItems;
    }
}