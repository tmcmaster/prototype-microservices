package au.id.mcmaster.scratch.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import au.id.mcmaster.scratch.common.GenericRestClient;
import au.id.mcmaster.scratch.product.domain.Product;

@Component
public class ProductClient extends GenericRestClient<Product>
{   
    @Autowired
    public ProductClient(@Value("${gateway.uri}") final String gatewayUri, @Value("${domain.product.mapping}") final String mapping)
    {
        super(gatewayUri, mapping);
    }
}
