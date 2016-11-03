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
 * Question MicroService
 * 
 * @author Tim McMaster
 */

/**
 * Controller
 */

@RestController
@RequestMapping("${domain.question.mapping:/question}")
@ConditionalOnExpression("${domain.question.enabled:true}")
class QuestionController extends GenericController<Question, QuestionRepository, QuestionFactory>
{
    @Autowired
    private QuestionService questionService;

    @RequestMapping(method = RequestMethod.GET, value = "/test")
    public void test()
    {
        questionService.test();
    }
}

/**
 * Service
 */
@Service
class QuestionService
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
class QuestionHealth implements HealthIndicator
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

interface QuestionRepository extends MongoRepository<Question, String>
{
}

/**
 * Factory
 */

@Component
class QuestionFactory extends DomainObjectFactory<Question>
{
    @Override
    public Question example()
    {
        Question Question = new Question();
        return Question;
    }
}

/**
 * Client
 */

@Component
class QuestionClient extends GenericRestClient<Question>
{
    @Autowired
    public QuestionClient(@Value("${gateway.uri}") final String gatewayUri,
            @Value("${domain.question.mapping:/question}") final String mapping)
    {
        super(gatewayUri, mapping);
    }
}

/**
 * Domain
 */

@XmlRootElement
class Question extends DomainRef
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