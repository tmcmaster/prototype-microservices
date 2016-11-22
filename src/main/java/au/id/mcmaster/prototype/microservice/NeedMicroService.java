package au.id.mcmaster.prototype.microservice;

import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rethinkdb.RethinkDB;
import com.rethinkdb.model.MapObject;
import com.rethinkdb.net.Connection;

import au.id.mcmaster.scratch.common.CommonUtils;
import au.id.mcmaster.scratch.common.DomainObjectFactory;
import au.id.mcmaster.scratch.common.DomainRef;
import au.id.mcmaster.scratch.common.GenericController;
import au.id.mcmaster.scratch.common.GenericRestClient;

/**
 * Need MicroService
 * 
 * @author Tim McMaster
 */

/**
 * Controller
 */

@RestController
@RequestMapping("${domain.need.mapping:/need}")
@ConditionalOnExpression("${domain.need.enabled:true}")
class NeedController extends GenericController<Need, NeedRepository, NeedFactory>
{
    @Autowired
    private NeedService needService;

    @RequestMapping(method = RequestMethod.GET, value = "/review/{documentId}")
    public void review(@PathVariable String documentId)
    {
        needService.review(documentId);
    }
}

/**
 * Service
 */
@Service
class NeedService
{
    public static final RethinkDB r = RethinkDB.r;
    
    public void review(String documentId)
    {
        System.out.println("About to process document: " + documentId);
        Connection conn = r.connection().port(32770).connect();
        conn.use("example_app");
        Map<String, Object> objectMap = r.table("messages").get(documentId).run(conn);
        MapObject updateMap = CommonUtils.generateUpdateMap(objectMap, objectMap);
        r.table("messages").get(documentId).update(updateMap).run(conn);
        conn.close();
    }
}

/**
 * Health Indicator
 */

@Component
class NeedHealth implements HealthIndicator
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

interface NeedRepository extends MongoRepository<Need, String>
{
}

/**
 * Factory
 */

@Component
class NeedFactory extends DomainObjectFactory<Need>
{
    @Override
    public Need example()
    {
        Need need = new Need();
        need.setTitle("Title");
        need.setDescription("Description");
        return need;
    }
}

/**
 * Client
 */

@Component
class NeedClient extends GenericRestClient<Need>
{
    @Autowired
    public NeedClient(@Value("${gateway.uri}") final String gatewayUri,
            @Value("${domain.need.mapping:/need}") final String mapping)
    {
        super(gatewayUri, mapping);
    }
}

/**
 * Domain
 */

@XmlRootElement
class Need extends DomainRef
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