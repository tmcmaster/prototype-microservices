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
import io.swagger.annotations.Api;

/**
 * Lead MicroService
 * 
 * @author Tim McMaster
 */


/**
 * Controller
 */

@RestController @RequestMapping("${domain.lead.mapping}")
@Api(value = "/lead", description = "Manages Leads with a RESTful CRUD API")
@ConditionalOnExpression("${domain.Lead.enabled:true}")
class LeadController extends GenericController<Lead, LeadRepository, LeadFactory> {
    @Autowired
    private LeadService leadService;
    
    @RequestMapping(method = RequestMethod.GET, value="/test")
    public void test()
    {
        leadService.test();
    }
}

/**
 * Service
 */
@Service
class LeadService
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
class LeadHealth implements HealthIndicator
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

interface LeadRepository extends MongoRepository<Lead, String> {}

/**
 * Factory
 */

@Component
class LeadFactory extends DomainObjectFactory<Lead>
{
    @Override
    public Lead example()
    {
        Lead lead = new Lead();
        lead.setTitle("Title");
        lead.setDescription("Description");
        return lead;
    }
}

/**
 * Client
 */

@Component
class LeadClient extends GenericRestClient<Lead>
{   
    @Autowired
    public LeadClient(@Value("${gateway.uri}") final String gatewayUri, @Value("${domain.lead.mapping}") final String mapping)
    {
        super(gatewayUri, mapping);
    }
}

/**
 * Domain
 */

@XmlRootElement
class Lead extends DomainRef
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

