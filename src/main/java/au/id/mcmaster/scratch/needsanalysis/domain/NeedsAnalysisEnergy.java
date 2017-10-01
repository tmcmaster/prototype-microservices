package au.id.mcmaster.scratch.needsanalysis.domain;

public class NeedsAnalysisEnergy
{
    private String currentProvider;
    private double currentBillAmount;
    
    public String getCurrentProvider()
    {
        return currentProvider;
    }
    public void setCurrentProvider(String currentProvider)
    {
        this.currentProvider = currentProvider;
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
