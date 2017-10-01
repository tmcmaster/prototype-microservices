package au.id.mcmaster.scratch.needsanalysis;

import org.springframework.stereotype.Component;

import au.id.mcmaster.scratch.common.DomainObjectFactory;
import au.id.mcmaster.scratch.needsanalysis.domain.NeedsAnalysis;

@Component
public class NeedsAnalysisFactory extends DomainObjectFactory<NeedsAnalysis>
{
    @Override
    public NeedsAnalysis example()
    {
        NeedsAnalysis needsAnalysis = new NeedsAnalysis();
        return needsAnalysis;
    }
}
