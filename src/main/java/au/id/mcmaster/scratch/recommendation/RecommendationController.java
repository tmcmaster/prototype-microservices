package au.id.mcmaster.scratch.recommendation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import au.id.mcmaster.scratch.common.DomainRef;
import au.id.mcmaster.scratch.common.GenericController;
import au.id.mcmaster.scratch.needsanalysis.domain.NeedsAnalysisRef;
import au.id.mcmaster.scratch.product.domain.ProductRef;

@RestController @RequestMapping("/recommendation")
@ConditionalOnExpression("${domain.recommendation.enabled:false}")
public class RecommendationController extends GenericController<Recommendation, RecommendationRepository, RecommendationFactory>
{
    @Autowired
    private RecommendationService recommendationService;
    
    @RequestMapping(method = RequestMethod.POST, value="generate")
    public DomainRef generate(NeedsAnalysisRef needsAnalysisRef)
    {
        Recommendation recommendation = new Recommendation();
        recommendation.setNeedsAnalysisRef(needsAnalysisRef);
        List<ProductRef> recommendedProducts = recommendationService.getRecommendedProducts(needsAnalysisRef);
        DomainRef recommendationRef = create(recommendation);
        recommendationRef.setId(recommendation.getId());
        return recommendationRef;
    }
}