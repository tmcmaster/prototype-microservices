package au.id.mcmaster.scratch.needsanalysis.domain;

public class NeedsAnalysisTelco
{
    private String currentProvider;
    private String currentUsage;
    private double currentBillAmount;
    
    public String getCurrentProvider()
    {
        return currentProvider;
    }
    public void setCurrentProvider(String currentProvider)
    {
        this.currentProvider = currentProvider;
    }
    public String getCurrentUsage()
    {
        return currentUsage;
    }
    public void setCurrentUsage(String currentUsage)
    {
        this.currentUsage = currentUsage;
    }
    public double getCurrentBillAmount()
    {
        return currentBillAmount;
    }
    public void setCurrentBillAmount(double currentBillAmount)
    {
        this.currentBillAmount = currentBillAmount;
    }
}
