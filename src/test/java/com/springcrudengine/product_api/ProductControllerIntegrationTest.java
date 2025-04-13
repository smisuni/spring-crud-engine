package com.springcrudengine.product_api;

import com.springcrudengine.product_api.dto.ProductDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String baseUrl() {
        return "http://localhost:" + port + "/api/products";
    }

    private ProductDTO createValidProduct(String name) {
        return new ProductDTO(null, name, "Description", 99.99, true);
    }

    @Test
    public void testCreateAndGetProduct() {
        ProductDTO request = createValidProduct("TestPhone");

        ResponseEntity<ProductDTO> createResponse = restTemplate.postForEntity(baseUrl(), request, ProductDTO.class);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        ProductDTO created = createResponse.getBody();
        assertThat(created).isNotNull();
        assertThat(created.getId()).isNotNull();

        ResponseEntity<ProductDTO> getResponse = restTemplate.getForEntity(baseUrl() + "/" + created.getId(), ProductDTO.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody().getName()).isEqualTo("TestPhone");
    }

    @Test
    public void testUpdateProduct() {
        ProductDTO request = createValidProduct("OldName");

        ProductDTO created = restTemplate.postForEntity(baseUrl(), request, ProductDTO.class).getBody();
        assertThat(created).isNotNull();

        created.setName("NewName");
        created.setPrice(123.45);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<ProductDTO> entity = new HttpEntity<>(created, headers);

        ResponseEntity<ProductDTO> response = restTemplate.exchange(baseUrl() + "/" + created.getId(), HttpMethod.PUT, entity, ProductDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getName()).isEqualTo("NewName");
        assertThat(response.getBody().getPrice()).isEqualTo(123.45);
    }

    @Test
    public void testDeleteProduct() {
        ProductDTO created = restTemplate.postForEntity(baseUrl(), createValidProduct("ToDelete"), ProductDTO.class).getBody();

        restTemplate.delete(baseUrl() + "/" + created.getId());

        ResponseEntity<String> getResponse = restTemplate.getForEntity(baseUrl() + "/" + created.getId(), String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void testValidationErrors() {
        ProductDTO invalid = new ProductDTO(null, "ab", "Invalid", -10.0, null);

        ResponseEntity<String> response = restTemplate.postForEntity(baseUrl(), invalid, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).contains("Product name cannot be null", "Price must be atleast 0", "Available status cannot be null");
    }

    @Test
    public void testGetProductNotFound() {
        UUID fakeId = UUID.randomUUID();
        ResponseEntity<String> response = restTemplate.getForEntity(baseUrl() + "/" + fakeId, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void testGetAllProducts() {
        restTemplate.postForEntity(baseUrl(), createValidProduct("Product1"), ProductDTO.class);
        restTemplate.postForEntity(baseUrl(), createValidProduct("Product2"), ProductDTO.class);

        ResponseEntity<ProductDTO[]> response = restTemplate.getForEntity(baseUrl(), ProductDTO[].class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().length).isGreaterThanOrEqualTo(2);
    }

    @Test
    public void testDeleteNonExistingProduct() {
        UUID nonExistentId = UUID.randomUUID();
        ResponseEntity<String> response = restTemplate.exchange(
                baseUrl() + "/" + nonExistentId,
                HttpMethod.DELETE,
                null,
                String.class
        );
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).contains("Product not found");
    }
}
