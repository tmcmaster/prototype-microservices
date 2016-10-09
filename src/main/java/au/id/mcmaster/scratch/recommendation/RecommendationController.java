package au.id.mcmaster.scratch.recommendation;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import au.id.mcmaster.scratch.common.GenericController;
import au.id.mcmaster.scratch.needsanalysis.NeedsAnalysisRef;

@RestController @RequestMapping("/recommendation")
@ConditionalOnExpression("${domain.recommendation.enabled:false}")
public class RecommendationController extends GenericController<Recommendation, RecommendationRepository, RecommendationFactory>
{
    @RequestMapping(method = RequestMethod.POST, value="generate")
    public RecommendationRef generate(NeedsAnalysisRef needsAnalysisRef)
    {
        Recommendation recommendation = example();
        recommendation = create(recommendation);
        RecommendationRef recommendationRef = new RecommendationRef();
        recommendationRef.setId(recommendation.getId());
        return recommendationRef;
    }
}