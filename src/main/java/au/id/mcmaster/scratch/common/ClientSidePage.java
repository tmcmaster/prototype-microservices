package au.id.mcmaster.scratch.common;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ClientSidePage<T>
{
    private List<T> content;
    private boolean last;
    private boolean first;
    private long totalPages;
    private long totalElements;
    private long size;
    private long number;
    private long numberOfElements;
    
    public List<T> getContent()
    {
        return content;
    }
    public void setContent(List<T> content)
    {
        this.content = content;
    }
    public boolean isLast()
    {
        return last;
    }
    public void setLast(boolean last)
    {
        this.last = last;
    }
    public boolean isFirst()
    {
        return first;
    }
    public void setFirst(boolean first)
    {
        this.first = first;
    }
    public long getTotalPages()
    {
        return totalPages;
    }
    public void setTotalPages(long totalPages)
    {
        this.totalPages = totalPages;
    }
    public long getTotalElements()
    {
        return totalElements;
    }
    public void setTotalElements(long totalElements)
    {
        this.totalElements = totalElements;
    }
    public long getSize()
    {
        return size;
    }
    public void setSize(long size)
    {
        this.size = size;
    }
    public long getNumber()
    {
        return number;
    }
    public void setNumber(long number)
    {
        this.number = number;
    }
    public long getNumberOfElements()
    {
        return numberOfElements;
    }
    public void setNumberOfElements(long numberOfElements)
    {
        this.numberOfElements = numberOfElements;
    }
}
