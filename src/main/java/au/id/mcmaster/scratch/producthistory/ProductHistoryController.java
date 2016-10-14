package au.id.mcmaster.scratch.producthistory;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import au.id.mcmaster.scratch.common.GenericController;

@RestController @RequestMapping("/producthistory")
@ConditionalOnExpression("${domain.producthistory.enabled:false}")
public class ProductHistoryController extends GenericController<ProductHistory, ProductHistoryRepository, ProductHistoryFactory>
{
}