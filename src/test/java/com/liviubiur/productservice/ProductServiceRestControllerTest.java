package com.liviubiur.productservice;

import com.liviubiur.productservice.product.persistence.entity.Product;
import com.liviubiur.productservice.product.rest.ProductRestController;
import com.liviubiur.productservice.product.rest.exception.ProductNotFoundAdvice;
import com.liviubiur.productservice.product.service.ProductService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.EntityModel;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(ProductRestController.class)
public class ProductServiceRestControllerTest {

  private MockMvc mockMvc;

  @MockBean
  private ProductService productService;

  private static final String BASE_PATH = "http://localhost/products";
  private static final long ID = 1L;
  private Product product;

  @Before
  public void setup() {
    mockMvc =
            MockMvcBuilders.standaloneSetup(new ProductRestController(productService))
                    .setControllerAdvice(new ProductNotFoundAdvice())
                    .build();
    setupProduct();
  }

  private void setupProduct() {
    product = new Product();
    product.setId(ID);
    product.setName("iPhone S8");
    product.setCreatedAt(LocalDateTime.now());
    product.setPrice(699.99);
  }

  private static final DateTimeFormatter formatter =
          DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

  @Test
  public void getCorrectResponseById() throws Exception {
    given(productService.getById(ID)).willReturn(new EntityModel<>(product));
    
    final ResultActions result = mockMvc.perform(get(BASE_PATH + "/" + ID));
    result.andExpect(status().isOk());
    verifyJson(result);
  }

  @Test
  public void allReturnsCorrectResponse() throws Exception {
    given(productService.getAll()).willReturn(Collections.singletonList(new EntityModel<>(product)));
    
    final ResultActions result = mockMvc.perform(get(BASE_PATH));
    result.andExpect(status().isOk());
    result
            .andExpect(jsonPath("links[0].rel", is("self")))
            .andExpect(jsonPath("links[0].href", is(BASE_PATH)))
            .andExpect(jsonPath("content[0].id", is((int) ID)))
            .andExpect(jsonPath("content[0].name", is(product.getName())))
            .andExpect(jsonPath("content[0].price", is(product.getPrice())))
            .andExpect(jsonPath("content[0].createdAt", is(product.getCreatedAt().format(formatter))));
  }

  @Test
  public void deleteReturnsCorrectResponse() throws Exception {
    given(productService.getById(ID)).willReturn(new EntityModel<>(product));
    
    mockMvc
            .perform(delete(BASE_PATH + "/" + ID))
            .andExpect(status().isNoContent())
            .andExpect(content().string(""));
  }

  private void verifyJson(final ResultActions action) throws Exception {
    action
            .andExpect(jsonPath("id", is((int) product.getId())))
            .andExpect(jsonPath("name", is(product.getName())))
            .andExpect(jsonPath("price", is(product.getPrice())))
            .andExpect(jsonPath("createdAt", is(product.getCreatedAt().format(formatter))));
  }
}
