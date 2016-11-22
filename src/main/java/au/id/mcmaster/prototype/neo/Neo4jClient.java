package au.id.mcmaster.prototype.neo;

import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.types.Node;
import org.springframework.stereotype.Component;

import au.id.mcmaster.prototype.beanio.DefinitionRecord;

@Component
public class Neo4jClient
{
    Driver driver;
    Session session;

    public Neo4jClient()
    {        
    }

    public void connect()
    {
        this.driver = GraphDatabase.driver( "bolt://localhost", AuthTokens.basic( "neo4j", "AllIsGood" ) );
        this.session = driver.session();
    }
    
    public void disconnect()
    {
        this.session.close();
        this.driver.close();
    }
    
    public void insertNodesAndLinks(DefinitionRecord parent, DefinitionRecord child) {
        insertNode(child);
        linkNodes(parent,child);
    }

    public void linkNodes(DefinitionRecord parent, DefinitionRecord child)
    {
    }

    public void insertNode(DefinitionRecord node)
    {
    }
}
