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
 * Correspondence MicroService
 * 
 * @author Tim McMaster
 */

/**
 * Controller
 */

@RestController
@RequestMapping("${domain.correspondence.mapping:/correspondence}")
@ConditionalOnExpression("${domain.correspondence.enabled:true}")
class CorrespondenceController extends GenericController<Correspondence, CorrespondenceRepository, CorrespondenceFactory>
{
    @Autowired
    private CorrespondenceService correspondenceService;

    @RequestMapping(method = RequestMethod.GET, value = "/test")
    public void test()
    {
        correspondenceService.test();
    }
}

/**
 * Service
 */
@Service
class CorrespondenceService
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
class CorrespondenceHealth implements HealthIndicator
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

interface CorrespondenceRepository extends MongoRepository<Correspondence, String>
{
}

/**
 * Factory
 */

@Component
class CorrespondenceFactory extends DomainObjectFactory<Correspondence>
{
    @Override
    public Correspondence example()
    {
        Correspondence Correspondence = new Correspondence();
        return Correspondence;
    }
}

/**
 * Client
 */

@Component
class CorrespondenceClient extends GenericRestClient<Correspondence>
{
    @Autowired
    public CorrespondenceClient(@Value("${gateway.uri}") final String gatewayUri,
            @Value("${domain.correspondence.mapping:/correspondence}") final String mapping)
    {
        super(gatewayUri, mapping);
    }
}

/**
 * Domain
 */

@XmlRootElement
class Correspondence extends DomainRef
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