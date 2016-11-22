package au.id.mcmaster.prototype.beanio;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.beanio.BeanReader;
import org.beanio.StreamFactory;
import org.beanio.builder.DelimitedParserBuilder;
import org.beanio.builder.StreamBuilder;
import org.junit.Assert;

public class BeanIO
{
    public static <T> List<T> load(String filePath, Class<T> type)
    {
        StreamFactory factory = StreamFactory.newInstance();
        
        InputStream is = TestBeanIO.class.getClassLoader().getResourceAsStream(filePath);
        Assert.assertNotNull("Could not find input file.", is);

        StreamBuilder builder = new StreamBuilder("definitions")
            .format("delimited")
            .parser(new DelimitedParserBuilder(','))
            .addRecord(DefinitionRecord.class);

        factory.define(builder);
        BeanReader in = factory.createReader("definitions", new InputStreamReader(is));
        
        List<T> list = new ArrayList<T>();
        Object record = null;
        while((record = in.read()) != null)
        {
            @SuppressWarnings("unchecked")
            T definitionRecord = (T) record;
            list.add(definitionRecord);
            System.out.println(definitionRecord);
        }
        
        in.close();
        
        return list;
    }
}
