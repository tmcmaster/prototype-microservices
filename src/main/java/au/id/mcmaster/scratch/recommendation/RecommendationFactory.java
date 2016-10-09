package au.id.mcmaster.scratch.recommendation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import au.id.mcmaster.scratch.common.DomainObjectFactory;

@Component
public class RecommendationFactory extends DomainObjectFactory<Recommendation>
{
    @Override
    public Recommendation example()
    {
        Recommendation recommendation = new Recommendation();
        List<ProductSummary> productSummaryList = new ArrayList<ProductSummary>();
        productSummaryList.add(new ProductSummary("1", "Product One"));
        productSummaryList.add(new ProductSummary("2", "Product Two"));
        recommendation.setProductSummary(productSummaryList);
        return recommendation;
    }
}