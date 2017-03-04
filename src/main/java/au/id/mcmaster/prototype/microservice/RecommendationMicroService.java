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
 * Recommendation MicroService
 * 
 * @author Tim McMaster
 */

/**
 * Controller
 */

@RestController
@Api(value = "/recommendation", description = "Manages Recommendations with a RESTful CRUD API")
@RequestMapping("${domain.recommendation.mapping:/recommendation}")
@ConditionalOnExpression("${domain.recommendation.enabled:true}")
class RecommendationController extends GenericController<Recommendation, RecommendationRepository, RecommendationFactory>
{
    @Autowired
    private RecommendationService recommendationService;

    @RequestMapping(method = RequestMethod.GET, value = "/test")
    public void test()
    {
        recommendationService.test();
    }
}

/**
 * Service
 */
@Service
class RecommendationService
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
class RecommendationHealth implements HealthIndicator
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

interface RecommendationRepository extends MongoRepository<Recommendation, String>
{
}

/**
 * Factory
 */

@Component
class RecommendationFactory extends DomainObjectFactory<Recommendation>
{
    @Override
    public Recommendation example()
    {
        Recommendation recommendation = new Recommendation();
        recommendation.setTitle("Title");
        recommendation.setDescription("Description");
        return recommendation;
    }
}

/**
 * Client
 */

@Component
class RecommendationClient extends GenericRestClient<Recommendation>
{
    @Autowired
    public RecommendationClient(@Value("${gateway.uri}") final String gatewayUri,
            @Value("${domain.recommendation.mapping:/recommendation}") final String mapping)
    {
        super(gatewayUri, mapping);
    }
}

/**
 * Domain
 */

@XmlRootElement
class Recommendation extends DomainRef
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