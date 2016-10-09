package au.id.mcmaster.scratch.producthistory;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import au.id.mcmaster.scratch.needsanalysis.domain.NeedsAnalysis;
import au.id.mcmaster.scratch.product.ProductRef;

@XmlRootElement
public class ProductHistory extends ProductHistoryRef
{
    private NeedsAnalysis needsAnalysis;
    private List<ProductRef> productList;
    
    public NeedsAnalysis getNeedsAnalysis()
    {
        return needsAnalysis;
    }
    public void setNeedsAnalysis(NeedsAnalysis needsAnalysis)
    {
        this.needsAnalysis = needsAnalysis;
    }
    public List<ProductRef> getProductList()
    {
        return productList;
    }
    public void setProductList(List<ProductRef> productList)
    {
        this.productList = productList;
    }
}
