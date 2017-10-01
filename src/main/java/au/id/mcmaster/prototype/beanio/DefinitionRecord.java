package au.id.mcmaster.prototype.beanio;

import org.beanio.annotation.Field;
import org.beanio.annotation.Record;


@Record(minOccurs=0, maxOccurs=-1)
public class DefinitionRecord
{
    @Field(at=0, required=false)
    private String vertical;   
    @Field(at=1, required=false)
    private String page;
    @Field(at=2, required=false)
    private String group;
    @Field(at=3, required=false)
    private String question;
    @Field(at=4, required=false)
    private String option;
    @Field(at=5, required=true)
    private String entity;
    @Field(at=6, required=true)
    private String name;
    @Field(at=7, required=false)
    private String type;
    @Field(at=8, required=false)
    private String relevant;
    @Field(at=9, required=false)
    private String required;
    @Field(at=10, required=false)
    private String readonly;
    @Field(at=11, required=false)
    private String contraint;
    @Field(at=12, required=false, minOccurs=0)
    private String calculate;
    
    public String getVertical()
    {
        return vertical;
    }
    public void setVertical(String vertical)
    {
        this.vertical = vertical;
    }

    public void setPage(String page)
    {
        this.page = page;
    }
    public String getGroup()
    {
        return group;
    }
    public void setGroup(String group)
    {
        this.group = group;
    }
    public String getQuestion()
    {
        return question;
    }
    public void setQuestion(String question)
    {
        this.question = question;
    }
    public String getOption()
    {
        return option;
    }
    public void setOption(String option)
    {
        this.option = option;
    }
    public String getEntity()
    {
        return entity;
    }
    public void setEntity(String entity)
    {
        this.entity = entity;
    }
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public String getType()
    {
        return type;
    }
    public void setType(String type)
    {
        this.type = type;
    }
    public String getRelevant()
    {
        return relevant;
    }
    public void setRelevant(String relative)
    {
        this.relevant = relative;
    }
    public String getRequired()
    {
        return required;
    }
    public void setRequired(String required)
    {
        this.required = required;
    }
    public String getReadonly()
    {
        return readonly;
    }
    public void setReadonly(String readonly)
    {
        this.readonly = readonly;
    }
    public String getContraint()
    {
        return contraint;
    }
    public void setContraint(String contraint)
    {
        this.contraint = contraint;
    }
    public String getCalculate()
    {
        return calculate;
    }
    public void setCalculate(String calculate)
    {
        this.calculate = calculate;
    } 
    
    public String getTitle()
    {
        return ("Questionnaire".equals(entity) ? vertical
             : ("Page".equals(entity) ? page
             : ("Group".equals(entity) ? group
             : ("Question".equals(entity) ? question
             : ("Option".equals(entity) ? option : "")))));
    }
    
    public int getDepth()
    {
        return ("Questionnaire".equals(entity) ? 0
             : ("Page".equals(entity) ? 1
             : ("Group".equals(entity) ? 2
             : ("Question".equals(entity) ? 3
             : ("Option".equals(entity) ? 4 : -1)))));
    }
    
    private String getIndent()
    {
        return ("Questionnaire".equals(entity) ? ""
             : ("Page".equals(entity) ? "  "
             : ("Group".equals(entity) ? "    "
             : ("Question".equals(entity) ? "      "
             : ("Option".equals(entity) ? "        " : "")))));
    }
    
    public String toString()
    {
        String item = String.format("%s%s(%s)", getIndent(), entity, getTitle()); 
        return String.format("%-60.60s %-30.30s %-10.10s %s", item, name, type, relevant);
    }
}
