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
 * Order MicroService
 * 
 * @author Tim McMaster
 */

/**
 * Controller
 */

@RestController
@Api(value = "/order", description = "Manages Orders with a RESTful CRUD API")
@RequestMapping("${domain.order.mapping:/order}")
@ConditionalOnExpression("${domain.order.enabled:true}")
class OrderController extends GenericController<Order, OrderRepository, OrderFactory>
{
    @Autowired
    private OrderService orderService;

    @RequestMapping(method = RequestMethod.GET, value = "/test")
    public void test()
    {
        orderService.test();
    }
}

/**
 * Service
 */
@Service
class OrderService
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
class OrderHealth implements HealthIndicator
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

interface OrderRepository extends MongoRepository<Order, String>
{
}

/**
 * Factory
 */

@Component
class OrderFactory extends DomainObjectFactory<Order>
{
    @Override
    public Order example()
    {
        Order order = new Order();
        order.setTitle("Title");
        order.setDescription("Description");
        return order;
    }
}

/**
 * Client
 */

@Component
class OrderClient extends GenericRestClient<Order>
{
    @Autowired
    public OrderClient(@Value("${gateway.uri}") final String gatewayUri,
            @Value("${domain.order.mapping:/order}") final String mapping)
    {
        super(gatewayUri, mapping);
    }
}

/**
 * Domain
 */

@XmlRootElement
class Order extends DomainRef
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