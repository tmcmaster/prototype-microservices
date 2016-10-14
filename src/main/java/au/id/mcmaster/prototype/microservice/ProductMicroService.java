package au.id.mcmaster.prototype.microservice;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import au.id.mcmaster.scratch.common.DomainObjectFactory;
import au.id.mcmaster.scratch.common.DomainRef;
import au.id.mcmaster.scratch.common.GenericController;
import au.id.mcmaster.scratch.common.GenericRestClient;

/**
 * Product MicroService
 * 
 * @author Tim McMaster
 */


/**
 * Controller
 */

@RestController @RequestMapping("${domain.product.mapping}")
@ConditionalOnExpression("${domain.product.enabled:false}")
class ProductController extends GenericController<Product, ProductRepository, ProductFactory> {
}

/**
 * Health Indicator
 */

class ProductHealth implements HealthIndicator
{

    @Override
    public Health health()
    {
        return Health.up().build();
    }
    
}


/**
 * Client
 */

@Component
class ProductClient extends GenericRestClient<Product>
{   
    @Autowired
    public ProductClient(@Value("${gateway.uri}") final String gatewayUri, @Value("${domain.product.mapping}") final String mapping)
    {
        super(gatewayUri, mapping);
    }
}

/**
 * Repository
 */

interface ProductRepository extends MongoRepository<Product, String> {}

/**
 * Factory
 */

@Component
class ProductFactory extends DomainObjectFactory<Product>
{
    @Override
    public Product example()
    {
        Product product = new Product();
        return product;
    }
}

/**
 * Domain
 */

@XmlRootElement
class Product extends DomainRef
{
    private String description;
    
    public String getDescription()
    {
        return description;
    }
    public void setDescription(String description)
    {
        this.description = description;
    }
}
