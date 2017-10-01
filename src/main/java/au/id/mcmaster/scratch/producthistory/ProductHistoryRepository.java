package au.id.mcmaster.scratch.producthistory;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductHistoryRepository extends MongoRepository<ProductHistory, String> {}
