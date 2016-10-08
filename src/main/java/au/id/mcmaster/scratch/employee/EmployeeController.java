package au.id.mcmaster.scratch.employee;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employee")
@ConditionalOnExpression("${domain.employee.enabled:false}")
public class EmployeeController
{
    @Autowired
    EmployeeRepository employeeRepository;

    @RequestMapping(method = RequestMethod.PUT)
    public Employee create(@RequestBody Employee employee)
    {
        return employeeRepository.save(employee);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Employee> list()
    {
        return employeeRepository.findAll();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{employeeId}")
    public Employee get(@PathVariable String employeeId)
    {
        return employeeRepository.findOne(employeeId);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{employeeId}")
    public ResponseEntity<Employee> delete(@PathVariable String employeeId)
    {
        employeeRepository.delete(employeeId);

        return new ResponseEntity<Employee>(HttpStatus.ACCEPTED);
    }

    @RequestMapping(method = RequestMethod.POST)
    public List<Employee> search(@RequestBody Employee example)
    {
        return employeeRepository.findAll(Example.of(example));
    }
}