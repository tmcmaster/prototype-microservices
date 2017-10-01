package au.id.mcmaster.scratch.recommendation;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface RecommendationRepository extends MongoRepository<Recommendation, String> {}
