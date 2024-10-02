package com.example.OrderService.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;


@Entity
@Table(name="order_details")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull(message = "Product ID is required")
    private int product_id;
    @Min(value = 1, message = "Quantity must be greater than 0")
    private int quantity;
    private double total_price;
}
