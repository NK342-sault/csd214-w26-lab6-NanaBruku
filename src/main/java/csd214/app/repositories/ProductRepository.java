package csd214.app.repositories;

import csd214.app.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// @Repository tells Spring's IoC container to manage this bean
@Repository
public interface ProductRepository<P> extends JpaRepository<ProductEntity, Long> {
    // Spring Data JPA automatically provides:
    // save(), findById(), findAll(), deleteById(), count(), deleteAll()
    List<ProductEntity> findByPriceLessThan(double price);
    List<ProductEntity> findByNameContainingIgnoreCase(String name);
}