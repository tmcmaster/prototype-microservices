package au.id.mcmaster.scratch.needsanalysis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import au.id.mcmaster.scratch.common.GenericRestClient;
import au.id.mcmaster.scratch.needsanalysis.domain.NeedsAnalysis;

@Component
public class NeedsAnalysisClient extends GenericRestClient<NeedsAnalysis>
{   
    @Autowired
    public NeedsAnalysisClient(@Value("${gateway.uri}") final String gatewayUri, @Value("${domain.needsanalysis.mapping}") final String mapping)
    {
        super(gatewayUri, mapping);
    }
}
