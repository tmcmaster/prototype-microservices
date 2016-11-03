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
 * Need MicroService
 * 
 * @author Tim McMaster
 */

/**
 * Controller
 */

@RestController
@RequestMapping("${domain.need.mapping:/need}")
@ConditionalOnExpression("${domain.need.enabled:true}")
class NeedController extends GenericController<Need, NeedRepository, NeedFactory>
{
    @Autowired
    private NeedService needService;

    @RequestMapping(method = RequestMethod.GET, value = "/test")
    public void test()
    {
        needService.test();
    }
}

/**
 * Service
 */
@Service
class NeedService
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
class NeedHealth implements HealthIndicator
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

interface NeedRepository extends MongoRepository<Need, String>
{
}

/**
 * Factory
 */

@Component
class NeedFactory extends DomainObjectFactory<Need>
{
    @Override
    public Need example()
    {
        Need Need = new Need();
        return Need;
    }
}

/**
 * Client
 */

@Component
class NeedClient extends GenericRestClient<Need>
{
    @Autowired
    public NeedClient(@Value("${gateway.uri}") final String gatewayUri,
            @Value("${domain.need.mapping:/need}") final String mapping)
    {
        super(gatewayUri, mapping);
    }
}

/**
 * Domain
 */

@XmlRootElement
class Need extends DomainRef
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