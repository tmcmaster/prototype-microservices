package au.id.mcmaster.scratch.producthistory;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import au.id.mcmaster.scratch.common.DomainObjectFactory;
import au.id.mcmaster.scratch.needsanalysis.NeedsAnalysisFactory;
import au.id.mcmaster.scratch.product.ProductRef;

@Component
public class ProductHistoryFactory extends DomainObjectFactory<ProductHistory>
{
    @Autowired
    private NeedsAnalysisFactory needsAnalysisFactory;
    
    @Override
    public ProductHistory example()
    {
        ProductHistory productHistory = new ProductHistory();
        productHistory.setNeedsAnalysis(needsAnalysisFactory.example());
        List<ProductRef> productList = new ArrayList<ProductRef>();
        ProductRef productRef = new ProductRef();
        productRef.setId("Product001");
        productRef.setTitle("Product Obe");
        productList.add(productRef);
        productHistory.setProductList(productList);
        return productHistory;
    }
}
