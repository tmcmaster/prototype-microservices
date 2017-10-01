package au.id.mcmaster.scratch.product.domain;

import javax.xml.bind.annotation.XmlRootElement;

import au.id.mcmaster.scratch.common.DomainRef;

@XmlRootElement
public class Product extends DomainRef
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
