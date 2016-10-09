package au.id.mcmaster.scratch.recommendation;


public class RecommendationRef
{
    private String id;
    private Status status = Status.PENDING;

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }
    
    public Status getStatus()
    {
        return status;
    }

    public void setStatus(Status status)
    {
        this.status = status;
    }

    public enum Status
    {
        PENDING,COMPLETED
    }    
}
