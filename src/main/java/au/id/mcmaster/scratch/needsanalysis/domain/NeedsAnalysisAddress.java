package au.id.mcmaster.scratch.needsanalysis.domain;

public class NeedsAnalysisAddress
{
    private String addressLine;
    private String postcode;
    private String suburb;
    private String state;
    
    public String getAddressLine()
    {
        return addressLine;
    }
    public void setAddressLine(String addressLine)
    {
        this.addressLine = addressLine;
    }
    public String getPostcode()
    {
        return postcode;
    }
    public void setPostcode(String postcode)
    {
        this.postcode = postcode;
    }
    public String getSuburb()
    {
        return suburb;
    }
    public void setSuburb(String suburb)
    {
        this.suburb = suburb;
    }
    public String getState()
    {
        return state;
    }
    public void setState(String state)
    {
        this.state = state;
    }
}
