package com.pratik.springboot.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.pratik.springboot.rest.entity.ProductEntity;
import com.pratik.springboot.rest.repository.ProductRepo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest
public class ProductRestControllerMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductRepo repository;

    @Test
    public void testFindAll() throws Exception {
        //fail("Not yet implemented");
        ProductEntity product = buildProduct();

        List<ProductEntity> products = Arrays.asList(product);
        when(repository.findAll()).thenReturn(products);

        /*mockMvc.perform(get("/pratik/products").contextPath("/pratik")).andExpect(status().isOk())
                .andExpect(content().json("[{'id':1, 'name':'Product 1 Name', 'description':'Product 1 Desc', 'price':1000}]"));*/

        // Using JSON Object Writer
        ObjectWriter writer = new ObjectMapper().writer().withDefaultPrettyPrinter();
        mockMvc.perform(get("/pratik/products").contextPath("/pratik")).andExpect(status().isOk())
                .andExpect(content().json(writer.writeValueAsString(products)));

    }

    private ProductEntity buildProduct() {
        ProductEntity product = new ProductEntity();
        product.setId(1);
        product.setName("Product 1 Name");
        product.setDescription("Product 1 Desc");
        product.setPrice(1000);

        return product;
    }
}
