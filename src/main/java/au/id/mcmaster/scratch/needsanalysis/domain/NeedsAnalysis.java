package au.id.mcmaster.scratch.needsanalysis.domain;

import java.util.List;

public class NeedsAnalysis
{
    private String id;
    private NeedsAnalysisContact contact;
    private NeedsAnalysisAddress address;
    private NeedsAnalysisEnergy energy;
    private NeedsAnalysisAuto auto;
    private NeedsAnalysisTelco telco;
    
    public String getId()
    {
        return id;
    }
    public void setId(String id)
    {
        this.id = id;
    }
}
