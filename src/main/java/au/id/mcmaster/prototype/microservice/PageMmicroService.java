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
 * Page MicroService
 * 
 * @author Tim McMaster
 */

/**
 * Controller
 */

@RestController
@RequestMapping("${domain.page.mapping:/page}")
@ConditionalOnExpression("${domain.page.enabled:true}")
class PageController extends GenericController<PageMmicroService, PageRepository, PageFactory>
{
    @Autowired
    private PageService pageService;

    @RequestMapping(method = RequestMethod.GET, value = "/test")
    public void test()
    {
        pageService.test();
    }
}

/**
 * Service
 */
@Service
class PageService
{
    public void test()
    {
    }
}

/**
 * Health Indicator
 */

@Component
class PageHealth implements HealthIndicator
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

interface PageRepository extends MongoRepository<PageMmicroService, String>
{
}

/**
 * Factory
 */

@Component
class PageFactory extends DomainObjectFactory<PageMmicroService>
{
    @Override
    public PageMmicroService example()
    {
        PageMmicroService pageObject = new PageMmicroService();
        pageObject.setTitle("Title");
        pageObject.setDescription("Description");
        return pageObject;
    }
}

/**
 * Client
 */

@Component
class PageClient extends GenericRestClient<PageMmicroService>
{
    @Autowired
    public PageClient(@Value("${gateway.uri}") final String gatewayUri,
            @Value("${domain.page.mapping:/page}") final String mapping)
    {
        super(gatewayUri, mapping);
    }
}

/**
 * Domain
 */

@XmlRootElement
class PageMmicroService extends DomainRef
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