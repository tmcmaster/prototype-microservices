package au.id.mcmaster.prototype.neo;

import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;

public class TestNeo4j
{
    public static void main(String[] args)
    {
        try
        {
            test();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void test()
    {
        Driver driver = GraphDatabase.driver( "bolt://localhost", AuthTokens.basic( "neo4j", "AllIsGood" ) );
        Session session = driver.session();

        session.run( "CREATE (a:Person {name:'Arthur', title:'King'})" );

        StatementResult result = session.run( "MATCH (a:Person) WHERE a.name = 'Arthur' RETURN a.name AS name, a.title AS title" );
        while ( result.hasNext() )
        {
            Record record = result.next();
            System.out.println( record.get( "title" ).asString() + " " + record.get("name").asString() );
        }

        session.close();
        driver.close();
    }
}
