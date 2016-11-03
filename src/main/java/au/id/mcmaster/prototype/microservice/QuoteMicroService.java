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
 * Quote MicroService
 * 
 * @author Tim McMaster
 */

/**
 * Controller
 */

@RestController
@RequestMapping("${domain.quote.mapping:/quote}")
@ConditionalOnExpression("${domain.quote.enabled:true}")
class QuoteController extends GenericController<Quote, QuoteRepository, QuoteFactory>
{
    @Autowired
    private QuoteService quoteService;

    @RequestMapping(method = RequestMethod.GET, value = "/test")
    public void test()
    {
        quoteService.test();
    }
}

/**
 * Service
 */
@Service
class QuoteService
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
class QuoteHealth implements HealthIndicator
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

interface QuoteRepository extends MongoRepository<Quote, String>
{
}

/**
 * Factory
 */

@Component
class QuoteFactory extends DomainObjectFactory<Quote>
{
    @Override
    public Quote example()
    {
        Quote Quote = new Quote();
        return Quote;
    }
}

/**
 * Client
 */

@Component
class QuoteClient extends GenericRestClient<Quote>
{
    @Autowired
    public QuoteClient(@Value("${gateway.uri}") final String gatewayUri,
            @Value("${domain.quote.mapping:/quote}") final String mapping)
    {
        super(gatewayUri, mapping);
    }
}

/**
 * Domain
 */

@XmlRootElement
class Quote extends DomainRef
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