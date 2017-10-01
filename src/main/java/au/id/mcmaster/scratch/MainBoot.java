package au.id.mcmaster.scratch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class MainBoot
{
    public static void main(String[] args)
    {
        SpringApplication.run(MainBoot.class);
    }
}


/**
 *
 * Start Kong API Gateway
 * docker start kong-database
 * docker start kong
 * docker start kong-dashboard
 *
 * Kong API Gateway Dashboard
 * http://localhost:8080/
 *
 * Gateway Endpoints
 * http://localhost:8000/product
 * http://localhost:8000/recommendation
 *
 * Log file for Kong API Gateway
 * log/api-calls.log
 *
 * Start Mongo CLI
 * docker exec -it 255d3236a576 mongo
 * 
 * http://localhost:8080/v2/api-docs
 * http://localhost:8080/env
 * http://localhost:8080/swagger-ui.html
 * 
 * Elastic Search
 * http://localhost:9200/
 * 
 * Kibana
 * http://localhost:5601/
 * 
 * input {
 *   udp {
 *     type => json
 *     port => 5000
 *     codec => json
 *   }
 * }
 * 
 * output {
 * elasticsearch {
 *   hosts => ["localhost"]
 *   sniffing => true
 *   manage_template => false
 *   index => "logstash"
 *   document_type => "log"
 * }
}
 * 
 * 
 */
