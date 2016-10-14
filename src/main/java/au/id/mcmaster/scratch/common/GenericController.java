package au.id.mcmaster.scratch.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

public abstract class GenericController<T extends DomainRef, R extends MongoRepository<T, String>, F extends DomainObjectFactory<T>>
{
    @Autowired
    R repository;
    @Autowired
    F factory;
    
    @RequestMapping(method = RequestMethod.PUT, value = "/")
    public DomainRef create(@RequestBody T object)
    {
        return (DomainRef) repository.save(object);        
    }

    @RequestMapping(method = RequestMethod.GET)
    public Page<T> list(@RequestParam(required=false, defaultValue="0") int page,
                        @RequestParam(required=false, defaultValue="10") int size)
    {
        Pageable pageable = new PageRequest(page, size);
        return repository.findAll(pageable);
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "/{objectId}")
    public ResponseEntity<T> get(@PathVariable String objectId)
    {
        T object = repository.findOne(objectId);
        if (object == null)
        {
            return new ResponseEntity<T>(object,HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<T>(object,HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{objectId}")
    public void delete(@PathVariable String objectId)
    {
        repository.delete(objectId);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Page<T> search(@RequestBody T example, @RequestParam(required=false, defaultValue="0") int page,
                                                  @RequestParam(required=false, defaultValue="10") int size)
    {
        Pageable pageable = new PageRequest(page, size);
        return repository.findAll(Example.of(example), pageable);
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "/example")
    public T example()
    {
        return factory.example();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/ping")
    public ResponseEntity<Void> ping()
    {
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}