package com.burakkarahan.notificationservice;

import com.burakkarahan.notificationservice.event.OrderPlacedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@Slf4j
public class NotificationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(NotificationServiceApplication.class, args);
    }

    @Bean
    public Queue notificationQueue() {
        return new Queue("notificationQueue", false);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @RabbitListener(queues = "notificationQueue")
    public void handleNotification(OrderPlacedEvent orderPlacedEvent) {
        log.info("📧 YENİ BİLDİRİM GELDİ! Sipariş Numarası: {}", orderPlacedEvent.getOrderNumber());
    }
}