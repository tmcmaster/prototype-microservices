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
 * OrderSpecItem MicroService
 * 
 * @author Tim McMaster
 */

/**
 * Controller
 */

@RestController
@RequestMapping("${domain.orderSpecItem.mapping:/orderSpecItem}")
@ConditionalOnExpression("${domain.orderSpecItem.enabled:true}")
class OrderSpecItemController extends GenericController<OrderSpecItem, OrderSpecItemRepository, OrderSpecItemFactory>
{
    @Autowired
    private OrderSpecItemService orderSpecItemService;

    @RequestMapping(method = RequestMethod.GET, value = "/test")
    public void test()
    {
        orderSpecItemService.test();
    }
}

/**
 * Service
 */
@Service
class OrderSpecItemService
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
class OrderSpecItemHealth implements HealthIndicator
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

interface OrderSpecItemRepository extends MongoRepository<OrderSpecItem, String>
{
}

/**
 * Factory
 */

@Component
class OrderSpecItemFactory extends DomainObjectFactory<OrderSpecItem>
{
    @Override
    public OrderSpecItem example()
    {
        OrderSpecItem orderSpecItemObject = new OrderSpecItem();
        orderSpecItemObject.setTitle("Title");
        orderSpecItemObject.setName("orderSpecItemName");
        orderSpecItemObject.setLabel("Order Spec Item Name");
        orderSpecItemObject.setRequired(true);
        orderSpecItemObject.setType(OrderSpecType.STRING);
        return orderSpecItemObject;
    }
}

/**
 * Client
 */

@Component
class OrderSpecItemClient extends GenericRestClient<OrderSpecItem>
{
    @Autowired
    public OrderSpecItemClient(@Value("${gateway.uri}") final String gatewayUri,
            @Value("${domain.orderSpecItem.mapping:/orderSpecItem}") final String mapping)
    {
        super(gatewayUri, mapping);
    }
}

/**
 * Domain
 */

enum OrderSpecType
{
    STRING,
    NUMBER,
    DATE,
    TIMESTAMP
}

@XmlRootElement
class OrderSpecItem extends DomainRef
{
    private String name;
    private String label;
    private OrderSpecType type;
    private boolean required;
    
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public String getLabel()
    {
        return label;
    }
    public void setLabel(String label)
    {
        this.label = label;
    }
    public OrderSpecType getType()
    {
        return type;
    }
    public void setType(OrderSpecType type)
    {
        this.type = type;
    }
    public boolean isRequired()
    {
        return required;
    }
    public void setRequired(boolean required)
    {
        this.required = required;
    }
}