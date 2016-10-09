package au.id.mcmaster.scratch.product;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Product extends ProductRef
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
