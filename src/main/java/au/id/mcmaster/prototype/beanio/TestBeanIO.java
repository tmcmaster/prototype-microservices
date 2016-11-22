package au.id.mcmaster.prototype.beanio;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.neo4j.driver.v1.types.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import au.id.mcmaster.prototype.neo.Neo4jClient;

@RunWith(SpringRunner.class)
public class TestBeanIO
{
    //@Autowired
    //private Neo4jClient  neo4jClient;
    
    public static void main(String[] args)
    {
        new TestBeanIO().test();
    }

    @Test
    public void test()
    {
        List<DefinitionRecord> definitionRecords = BeanIO.load("data/questionnaire-energy.csv", DefinitionRecord.class);
        Neo4jClient  neo4jClient = new Neo4jClient();
        neo4jClient.connect();
        
        DefinitionRecord questionnaire = null;
        DefinitionRecord page = null;
        DefinitionRecord group = null;
        DefinitionRecord question = null;
        DefinitionRecord option = null;
        
        for (DefinitionRecord definitionRecord : definitionRecords)
        {
            System.out.println(definitionRecord);
            
            Node node = createNode(definitionRecord);
            
            if ("Questionnire".equals(definitionRecord.getEntity()))
            {
                questionnaire = definitionRecord;
                neo4jClient.insertNode(questionnaire);
            }
            else if ("Page".equals(definitionRecord.getEntity()))
            {
                page = definitionRecord;
                neo4jClient.insertNodesAndLinks(questionnaire,page);
            }
            else if ("Group".equals(definitionRecord.getEntity()))
            {
                group = definitionRecord;
                neo4jClient.insertNodesAndLinks(page,group);
            }
            else if ("Question".equals(definitionRecord.getEntity()))
            {
                question = definitionRecord;
                neo4jClient.insertNodesAndLinks(group,question);
            }
            else if ("Option".equals(definitionRecord.getEntity()))
            {
                option = definitionRecord;
                neo4jClient.insertNodesAndLinks(question,option);
            }
        }
        
        neo4jClient.disconnect();
    }

    private Node createNode(DefinitionRecord definitionRecord)
    {
       
        // TODO Auto-generated method stub
        return null;
    }
}
