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
 * Page MicroService
 * 
 * @author Tim McMaster
 */

/**
 * Controller
 */

@RestController
@Api(value = "/page", description = "Manages Pages with a RESTful CRUD API")
@RequestMapping("${domain.page.mapping:/page}")
@ConditionalOnExpression("${domain.page.enabled:true}")
class PageController extends GenericController<PageMicroService, PageRepository, PageFactory>
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

interface PageRepository extends MongoRepository<PageMicroService, String>
{
}

/**
 * Factory
 */

@Component
class PageFactory extends DomainObjectFactory<PageMicroService>
{
    @Override
    public PageMicroService example()
    {
        PageMicroService pageObject = new PageMicroService();
        pageObject.setTitle("Title");
        pageObject.setDescription("Description");
        return pageObject;
    }
}

/**
 * Client
 */

@Component
class PageClient extends GenericRestClient<PageMicroService>
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
class PageMicroService extends DomainRef
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