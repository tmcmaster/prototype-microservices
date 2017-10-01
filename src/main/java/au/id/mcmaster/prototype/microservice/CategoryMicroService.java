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
 * Category MicroService
 * 
 * @author Tim McMaster
 */

/**
 * Controller
 */

@RestController
@Api(value = "/category", description = "Manages Categories with a RESTful CRUD API")
@RequestMapping("${domain.category.mapping:/category}")
@ConditionalOnExpression("${domain.category.enabled:true}")
class CategoryController extends GenericController<CategoryMicroService, CategoryRepository, CategoryFactory>
{
    @Autowired
    private CategoryService categoryService;

    @RequestMapping(method = RequestMethod.GET, value = "/test")
    public void test()
    {
        categoryService.test();
    }
}

/**
 * Service
 */
@Service
class CategoryService
{
    public void test()
    {
    }
}

/**
 * Health Indicator
 */

@Component
class CategoryHealth implements HealthIndicator
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

interface CategoryRepository extends MongoRepository<CategoryMicroService, String>
{
}

/**
 * Factory
 */

@Component
class CategoryFactory extends DomainObjectFactory<CategoryMicroService>
{
    @Override
    public CategoryMicroService example()
    {
        CategoryMicroService categoryObject = new CategoryMicroService();
        categoryObject.setTitle("Title");
        categoryObject.setDescription("Description");
        return categoryObject;
    }
}

/**
 * Client
 */

@Component
class CategoryClient extends GenericRestClient<CategoryMicroService>
{
    @Autowired
    public CategoryClient(@Value("${gateway.uri}") final String gatewayUri,
            @Value("${domain.category.mapping:/category}") final String mapping)
    {
        super(gatewayUri, mapping);
    }
}

/**
 * Domain
 */

@XmlRootElement
class CategoryMicroService extends DomainRef
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
