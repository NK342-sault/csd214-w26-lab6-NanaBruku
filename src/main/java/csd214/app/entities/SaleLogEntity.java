package csd214.app.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class SaleLogEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String productId;
    private double priceSoldAt;
    private LocalDateTime timestamp;

    public SaleLogEntity() {}
    public SaleLogEntity(String productId, double price) {
        this.productId = productId;
        this.priceSoldAt = price;
        this.timestamp = LocalDateTime.now();
    }
    // Getters/Setters…
    // add setters and gettres, toString and equals/hashCode with intellij alt-insert
}

