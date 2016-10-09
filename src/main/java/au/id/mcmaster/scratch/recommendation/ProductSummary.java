package au.id.mcmaster.scratch.recommendation;

public class ProductSummary
{
    private String productId;
    private String productTitle;
    
    public ProductSummary()
    {
    	
    }
    
    public ProductSummary(String id, String title)
    {
        this.productId = id;
        this.productTitle = title;
    }
    
    public String getProductId()
    {
        return productId;
    }
    public void setProductId(String productId)
    {
        this.productId = productId;
    }
    public String getProductTitle()
    {
        return productTitle;
    }
    public void setProductTitle(String productTitle)
    {
        this.productTitle = productTitle;
    }
}
