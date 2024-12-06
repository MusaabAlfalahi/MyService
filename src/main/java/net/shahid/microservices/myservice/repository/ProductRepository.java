package net.shahid.microservices.myservice.repository;

import net.shahid.microservices.myservice.domain.entity.Product;
import net.shahid.microservices.myservice.domain.enums.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {
    Page<Product> findByActiveTrue(Pageable pageable);

    Optional<Product> findByIdAndActiveTrue(String id);

    Page<Product> findAllByTypeAndActiveTrue(Type type, Pageable pageable);
}
