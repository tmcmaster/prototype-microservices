package au.id.mcmaster.scratch.spel;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.api.json.JSONJAXBContext;
import com.sun.jersey.api.json.JSONUnmarshaller;

import au.id.mcmaster.scratch.product.domain.Product;

public class TestSPEL
{
    /**
     * Predicate example: "#a.b.?[bb>3 and bb<5][0].bb"
     * 
     * @param args
     */
    public static void main(String[] args)
    {
        try
        {
            testJAXB();
        }
        catch (JAXBException e)
        {
            e.printStackTrace();
        }
    }
    
    public static void testJAXB() throws JAXBException
    {
        JSONJAXBContext context = new JSONJAXBContext(JSONConfiguration.natural().build(), Product.class);
        
//        Map<String, Object> properties = new HashMap<String, Object>(1);
//        properties.put("eclipselink.media-type", "application/json");
//        JAXBContext context = JAXBContext.newInstance(new Class[] {Product.class});
//        System.out.println(context.getClass().getName());
//        Unmarshaller unmarshaller = context.createUnmarshaller();
//        System.out.println(unmarshaller.getClass().getName());
//        Product person = (Product) unmarshaller.unmarshal(new StringReader("{'id':'sdfsd'}"));
//        System.out.println(person.getId());
        
        JSONUnmarshaller unmarshaller = (JSONUnmarshaller) context.createUnmarshaller();
        Product product = unmarshaller.unmarshalFromJSON(new StringReader("{\"id\":\"DDDD\"}"), Product.class);
        System.out.println(product.getId());
    }
    
    @SuppressWarnings("unchecked")
    public static void testJSON()
    {
        String jsonString = "{contact:{name:{first:'Nikola',last:'Tesla'},dob:{day:10,month:'July',year:1856}}}";
        
        ExpressionParser expressionParser = new SpelExpressionParser();
        Map<String,Object> data = (Map<String,Object>)expressionParser.parseExpression(jsonString).getValue();
        StandardEvaluationContext context = new StandardEvaluationContext();
        context.setVariables(data);
        Object result = expressionParser.parseExpression("#contact['dob']['month']").getValue(context);
        System.out.println(result);
    }
    
    public static void testPredicateFiltering()
    {
        Map<String,Object> data = new HashMap<String,Object>();
        data.put("aa", 5);
        data.put("bb", 5);
        data.put("a", new A());
        StandardEvaluationContext context = new StandardEvaluationContext();
        context.setVariables(data);
        ExpressionParser expressionParser = new SpelExpressionParser();
        Object result = expressionParser.parseExpression("#a.b.?[bb>3 and bb<5][0].bb").getValue(context);
        System.out.println(result);
    }
 
    static class A {
        public int aa = 4;
        private int bb = 4;
        public List<B> b = new ArrayList<B>();
        public A() {
            b.add(new B(3));
            b.add(new B(4));
            b.add(new B(5));
        }
        public int getBb()
        {
            return bb;
        }
        public void setBb(int bb)
        {
            this.bb = bb;
        } 
    }
    
    static class B {
        public int bb = 4;
        public B(int b) {
            this.bb = b;
        }
    }
}


