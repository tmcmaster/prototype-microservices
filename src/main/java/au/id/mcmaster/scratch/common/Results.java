package au.id.mcmaster.scratch.common;

import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

@XmlRootElement
public class Results<T>
{
    @XmlElementRefs({
        @XmlElementRef(type=PageImpl.class)
      })
    Page<T> page;
    
    public Page<T> getPage()
    {
        return page;
    }


    public void setPage(Page<T> page)
    {
        this.page = (Page<T>)page;
    }


    public static final <T> Results<T> create(Page<T> page) {
        System.out.println("**** " + page.getClass().getName());
        Results<T> results = new Results<T>();
        results.setPage(page);
        return results;
    }
}
