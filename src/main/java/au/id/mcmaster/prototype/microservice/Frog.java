package au.id.mcmaster.prototype.microservice;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import au.id.mcmaster.scratch.common.DomainObjectFactory;
import au.id.mcmaster.scratch.common.DomainRef;
import au.id.mcmaster.scratch.common.GenericController;
import au.id.mcmaster.scratch.common.GenericRestClient;

/**
 * Frog MicroService
 * 
 * @author Tim McMaster
 */

/**
 * Controller
 */

@RestController
@RequestMapping("${domain.frog.mapping:/frog}")
@ConditionalOnExpression("${domain.frog.enabled:true}")
class FrogController extends GenericController<Frog, FrogRepository, FrogFactory>
{
    @Autowired
    private FrogService frogService;

    @RequestMapping(method = RequestMethod.GET, value = "/test")
    public void test()
    {
        frogService.test();
    }
}

/**
 * Service
 */
@Service
class FrogService
{
    @Autowired
    private ProductClient productClient;

    public void test()
    {
        System.out.println("Number of products: " + productClient.list().getTotalElements());
    }
}

/**
 * Health Indicator
 */

@Component
class FrogHealth implements HealthIndicator
{

    @Override
    public Health health()
    {
        return Health.up().build();
    }

}

/**
 * Repository
 */

interface FrogRepository extends MongoRepository<Frog, String>
{
}

/**
 * Factory
 */

@Component
class FrogFactory extends DomainObjectFactory<Frog>
{
    @Override
    public Frog example()
    {
        Frog frogObject = new Frog();
        frogObject.setTitle("Title");
        frogObject.setDescription("Description");
        return frogObject;
    }
}

/**
 * Client
 */

@Component
class FrogClient extends GenericRestClient<Frog>
{
    @Autowired
    public FrogClient(@Value("${gateway.uri}") final String gatewayUri,
            @Value("${domain.frog.mapping:/frog}") final String mapping)
    {
        super(gatewayUri, mapping);
    }
}

/**
 * Domain
 */

@XmlRootElement
class Frog extends DomainRef
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