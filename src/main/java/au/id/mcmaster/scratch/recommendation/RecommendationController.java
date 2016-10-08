package au.id.mcmaster.scratch.recommendation;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import au.id.mcmaster.scratch.common.GenericController;

@RestController @RequestMapping("/recommendation")
@ConditionalOnExpression("${domain.recommendation.enabled:false}")
public class RecommendationController extends GenericController<Recommendation, RecommendationRepository, RecommendationFactory> {}