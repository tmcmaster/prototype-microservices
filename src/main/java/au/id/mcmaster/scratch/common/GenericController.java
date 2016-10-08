package au.id.mcmaster.scratch.common;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

public abstract class GenericController<T, R extends MongoRepository<T, String>, F extends DomainObjectFactory<T>>
{
    @Autowired
    R repository;
    @Autowired
    F factory;
    
    @RequestMapping(method = RequestMethod.PUT)
    public T create(@RequestBody T object)
    {
        System.out.println("Tobject Type: " + object.getClass().getName());
        return repository.save(object);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<T> list()
    {
        return repository.findAll();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{objectId}")
    public T get(@PathVariable String objectId)
    {
        return repository.findOne(objectId);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{objectId}")
    public void delete(@PathVariable String objectId)
    {
        repository.delete(objectId);
    }

    @RequestMapping(method = RequestMethod.POST)
    public List<T> search(@RequestBody T example)
    {
        return repository.findAll(Example.of(example));
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "/example")
    public T example()
    {
        return factory.example();
    }
}