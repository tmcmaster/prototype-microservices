package au.id.mcmaster.scratch.needsanalysis;

import org.springframework.data.mongodb.repository.MongoRepository;

import au.id.mcmaster.scratch.needsanalysis.domain.NeedsAnalysis;

public interface NeedsAnalysisRepository extends MongoRepository<NeedsAnalysis, String> {}
