package au.id.mcmaster.scratch.needsanalysis;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import au.id.mcmaster.scratch.common.GenericController;
import au.id.mcmaster.scratch.needsanalysis.domain.NeedsAnalysis;

@RestController @RequestMapping("/needsAnalysis")
@ConditionalOnExpression("${domain.needsanalysis.enabled:false}")
public class NeedsAnalysisController extends GenericController<NeedsAnalysis, NeedsAnalysisRepository, NeedsAnalysisFactory> {}
