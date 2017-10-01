package au.id.mcmaster.scratch.producthistory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import au.id.mcmaster.scratch.common.GenericRestClient;

@Component
public class ProductHistoryClient extends GenericRestClient<ProductHistory>
{   
    @Autowired
    public ProductHistoryClient(@Value("${gateway.uri}") final String gatewayUri, @Value("${domain.producthistory.mapping}") final String mapping)
    {
        super(gatewayUri, mapping);
    }
}
