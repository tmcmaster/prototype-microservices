package au.id.mcmaster.scratch.product;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import au.id.mcmaster.scratch.common.GenericController;

@RestController @RequestMapping("/product")
@ConditionalOnExpression("${domain.product.enabled:false}")
public class ProductController extends GenericController<Product, ProductRepository, ProductFactory> {}