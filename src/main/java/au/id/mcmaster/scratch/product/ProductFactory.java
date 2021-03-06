package au.id.mcmaster.scratch.product;

import org.springframework.stereotype.Component;

import au.id.mcmaster.scratch.common.DomainObjectFactory;
import au.id.mcmaster.scratch.product.domain.Product;

@Component
public class ProductFactory extends DomainObjectFactory<Product>
{
    @Override
    public Product example()
    {
        Product product = new Product();
        return product;
    }
}
