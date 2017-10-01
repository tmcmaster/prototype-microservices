package au.id.mcmaster.prototype.microservice;

import java.util.ArrayList;
import java.util.List;

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
 * OrderSpec MicroService
 * 
 * @author Tim McMaster
 */

/**
 * Controller
 */

@RestController
@RequestMapping("${domain.orderspec.mapping:/orderspec}")
@ConditionalOnExpression("${domain.orderspec.enabled:true}")
class OrderSpecController extends GenericController<OrderSpec, OrderSpecRepository, OrderSpecFactory>
{
    @Autowired
    private OrderSpecService orderspecService;

    @RequestMapping(method = RequestMethod.GET, value = "/test")
    public void test()
    {
        orderspecService.test();
    }
}

/**
 * Service
 */
@Service
class OrderSpecService
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
class OrderSpecHealth implements HealthIndicator
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

interface OrderSpecRepository extends MongoRepository<OrderSpec, String>
{
}

/**
 * Factory
 */

@Component
class OrderSpecFactory extends DomainObjectFactory<OrderSpec>
{
    @Override
    public OrderSpec example()
    {
        OrderSpec orderSpecObject = new OrderSpec();
        orderSpecObject.setTitle("Title");
        List<OrderSpecItem> orderSpecItems = new ArrayList<OrderSpecItem>();
        orderSpecItems.add(new OrderSpecItemFactory().example());
        orderSpecObject.setOrderSpecItems(orderSpecItems);
        return orderSpecObject;
    }
}

/**
 * Client
 */

@Component
class OrderSpecClient extends GenericRestClient<OrderSpec>
{
    @Autowired
    public OrderSpecClient(@Value("${gateway.uri}") final String gatewayUri,
            @Value("${domain.orderspec.mapping:/orderspec}") final String mapping)
    {
        super(gatewayUri, mapping);
    }
}

/**
 * Domain
 */

@XmlRootElement
class OrderSpec extends DomainRef
{
    private List<OrderSpecItem> orderSpecItems;

    public List<OrderSpecItem> getOrderSpecItems()
    {
        return orderSpecItems;
    }

    public void setOrderSpecItems(List<OrderSpecItem> orderSpecItems)
    {
        this.orderSpecItems = orderSpecItems;
    }
}