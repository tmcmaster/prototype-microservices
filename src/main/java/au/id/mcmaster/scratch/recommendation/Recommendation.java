package au.id.mcmaster.scratch.recommendation;

import java.util.List;

import au.id.mcmaster.scratch.common.DomainRef;
import au.id.mcmaster.scratch.needsanalysis.domain.NeedsAnalysisRef;

public class Recommendation extends DomainRef
{
    private NeedsAnalysisRef needsAnalysisRef;
    private List<ProductSummary> productSummary;

    public NeedsAnalysisRef getNeedsAnalysisRef()
    {
        return needsAnalysisRef;
    }
    public void setNeedsAnalysisRef(NeedsAnalysisRef needsAnalysisRef)
    {
        this.needsAnalysisRef = needsAnalysisRef;
    }
    public List<ProductSummary> getProductSummary()
    {
        return productSummary;
    }
    public void setProductSummary(List<ProductSummary> productSummary)
    {
        this.productSummary = productSummary;
    }
}
