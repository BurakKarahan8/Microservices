package com.burakkarahan.inventoryservice;

import com.burakkarahan.inventoryservice.model.Inventory;
import com.burakkarahan.inventoryservice.repository.InventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class InventoryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventoryServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner loadData(InventoryRepository inventoryRepository) {
        return args -> {
            if(inventoryRepository.count() == 0) {
                Inventory inventory = new Inventory();
                inventory.setSkuCode("iphone_15");
                inventory.setQuantity(100);

                Inventory inventory1 = new Inventory();
                inventory1.setSkuCode("airpods_pro");
                inventory1.setQuantity(0);

                inventoryRepository.save(inventory);
                inventoryRepository.save(inventory1);

                System.out.println("✅ Test verileri yüklendi!");
            }
        };
    }

}
