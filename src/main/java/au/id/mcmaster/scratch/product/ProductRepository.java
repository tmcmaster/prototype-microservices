package au.id.mcmaster.scratch.product;

import org.springframework.data.mongodb.repository.MongoRepository;

import au.id.mcmaster.scratch.product.domain.Product;

public interface ProductRepository extends MongoRepository<Product, String> {}
