package au.id.mcmaster.scratch.product;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import au.id.mcmaster.scratch.MainBoot;
import au.id.mcmaster.scratch.common.ClientSidePage;
import au.id.mcmaster.scratch.product.domain.Product;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes=MainBoot.class)
public class TestProductClient
{
    @Autowired
    private ProductClient productClient;
    
    @Test
    public void test()
    {
        ClientSidePage<Product> page = productClient.list();
        Assert.assertNotNull(page);
        Assert.assertTrue((page.getTotalPages() > 0));
        System.out.println("Number of products: " + page.getTotalElements());
    }
    
    @Test
    public void testGet()
    {
        Product product = productClient.get("57f832718149b3d36517c65f");
        Assert.assertNotNull(product);
        System.out.println("Product title: " + product.getTitle());
    }
}
