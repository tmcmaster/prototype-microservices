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
 * Case MicroService
 * 
 * @author Tim McMaster
 */

/**
 * Controller
 */

@RestController
@RequestMapping("${domain.case.mapping:/case}")
@ConditionalOnExpression("${domain.case.enabled:true}")
class CaseController extends GenericController<Case, CaseRepository, CaseFactory>
{
    @Autowired
    private CaseService caseService;

    @RequestMapping(method = RequestMethod.GET, value = "/test")
    public void test()
    {
        caseService.test();
    }
}

/**
 * Service
 */
@Service
class CaseService
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
class CaseHealth implements HealthIndicator
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

interface CaseRepository extends MongoRepository<Case, String>
{
}

/**
 * Factory
 */

@Component
class CaseFactory extends DomainObjectFactory<Case>
{
    @Override
    public Case example()
    {
        Case caseObject = new Case();
        caseObject.setTitle("Title");
        caseObject.setDescription("Description");
        return caseObject;
    }
}

/**
 * Client
 */

@Component
class CaseClient extends GenericRestClient<Case>
{
    @Autowired
    public CaseClient(@Value("${gateway.uri}") final String gatewayUri,
            @Value("${domain.case.mapping:/case}") final String mapping)
    {
        super(gatewayUri, mapping);
    }
}

/**
 * Domain
 */

@XmlRootElement
class Case extends DomainRef
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