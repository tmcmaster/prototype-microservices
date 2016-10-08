package au.id.mcmaster.scratch.recommendation;

import org.springframework.stereotype.Component;

import au.id.mcmaster.scratch.common.DomainObjectFactory;

@Component
public class RecommendationFactory extends DomainObjectFactory<Recommendation>
{
    @Override
    public Recommendation example()
    {
        Recommendation recommendation = new Recommendation();
        return recommendation;
    }
}