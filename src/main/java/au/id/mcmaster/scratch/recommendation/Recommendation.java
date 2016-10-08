package au.id.mcmaster.scratch.recommendation;

import java.util.List;

public class Recommendation
{
    private String id;
    private List<ProductSummary> productSummary;

    public String getId()
    {
        return id;
    }
    public void setId(String id)
    {
        this.id = id;
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
