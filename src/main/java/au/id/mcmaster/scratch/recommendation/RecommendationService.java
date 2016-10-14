package au.id.mcmaster.scratch.recommendation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import au.id.mcmaster.scratch.needsanalysis.NeedsAnalysisClient;
import au.id.mcmaster.scratch.needsanalysis.domain.NeedsAnalysis;
import au.id.mcmaster.scratch.needsanalysis.domain.NeedsAnalysisRef;
import au.id.mcmaster.scratch.product.domain.ProductRef;

@Service
public class RecommendationService
{
    @Autowired
    private NeedsAnalysisClient needsAnalysisClient;

    public List<ProductRef> getRecommendedProducts(NeedsAnalysisRef needsAnalysisRef)
    {
        NeedsAnalysis needsAnalysis = needsAnalysisClient.get(needsAnalysisRef.getId());
        
        return doSomeMagic(needsAnalysis);
    }
    
    private List<ProductRef> doSomeMagic(NeedsAnalysis needsAnalysis)
    {
        List<ProductRef> fromHistory = getProductListFromHistory(needsAnalysis);
        List<ProductRef> currentlyRelavent = filterToCurrenlyRelavent(fromHistory);
        return currentlyRelavent;
    }

    private List<ProductRef> getProductListFromHistory(NeedsAnalysis needsAnalysis)
    {
        List<ProductRef> productReferenceList = new ArrayList<ProductRef>();

        return productReferenceList;
    }

    private List<ProductRef> filterToCurrenlyRelavent(List<ProductRef> fromHistory)
    {
        List<ProductRef> relaventProducts = new ArrayList<ProductRef>();
        

        return relaventProducts;
    }    
}
