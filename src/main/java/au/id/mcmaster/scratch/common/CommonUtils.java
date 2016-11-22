package au.id.mcmaster.scratch.common;

import java.util.Map;

import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import com.rethinkdb.model.MapObject;

public class CommonUtils
{
    @SuppressWarnings("unchecked")
    public static MapObject generateUpdateMap(Map<String, Object> map, Map<String, Object> stateMap) {
        MapObject resultMap = new MapObject();
        
        for (Object key : map.keySet())
        {
            Object value = map.get(key);
            if ("relevant".equals(key))
            {
                if (value instanceof String)
                {
                    String relevant = (String) value;
                    boolean visible = calculateVisible(relevant, stateMap);
                    resultMap.put("visible", visible);
                }
            }
            else
            {
                if (value instanceof Map)
                {
                    Map<String,Object> subMap = (Map<String,Object>)value;
                    MapObject subResultMap = generateUpdateMap(subMap, stateMap);
                    if (subResultMap != null)
                    {
                        resultMap.with(key, subResultMap);
                    }
                }
            }
        }
        
        return (resultMap.entrySet().size() > 0 ? resultMap : null);
    }
    
    private static boolean calculateVisible(String expression, Map<String,Object> contextMap)
    {
        if (expression == null || expression.length() == 0)
        {
            return true;
        }
        
        StandardEvaluationContext context = new StandardEvaluationContext();
        context.setVariables(contextMap);
        ExpressionParser expressionParser = new SpelExpressionParser();

        Object result = expressionParser.parseExpression(expression).getValue(context);

        if (result instanceof Boolean)
        {
           return (Boolean) result; 
        }
        else if (result == null)
        {
            return false;
        }
        else
        {
            return true;
        }
    }
}
