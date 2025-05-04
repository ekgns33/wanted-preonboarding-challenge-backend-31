package org.ekgns33.commerce.product.api;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.ekgns33.commerce.common.ControllerTest;
import org.ekgns33.commerce.product.api.dto.ProductRequestMapper;
import org.ekgns33.commerce.product.service.ProductCommandService;
import org.ekgns33.commerce.product.service.dto.ProductSaveResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ControllerTest
@WebMvcTest(ProductController.class)
class ProductControllerTest {

  @Autowired private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockitoBean
  private ProductCommandService productCommandService;

  @MockitoBean
  private ProductRequestMapper productRequestMapper;

  @Test
  void createProduct_shouldReturn201() throws Exception {
    String requestBody =
        """
            {
               "id": 123,
               "name": "슈퍼 편안한 소파",
               "slug": "super-comfortable-sofa",
               "created_at": "2025-04-14T09:30:00Z",
               "updated_at": "2025-04-14T09:30:00Z"
             }
        """;

    String request =
"""
    {
     "name": "슈퍼 편안한 소파",
     "slug": "super-comfortable-sofa",
     "short_description": "최고급 소재로 만든 편안한 소파",
     "full_description": "<p>이 소파는 최고급 소재로 제작되었으며...</p>",
     "seller_id": 1,
     "brand_id": 2,
     "status": "ACTIVE",
     "detail": {
       "weight": 25.5,
       "dimensions": {
         "width": 200,
         "height": 85,
         "depth": 90
       },
       "materials": "가죽, 목재, 폼",
       "country_of_origin": "대한민국",
       "warranty_info": "2년 품질 보증",
       "care_instructions": "마른 천으로 표면을 닦아주세요",
       "additional_info": {
         "assembly_required": true,
         "assembly_time": "30분"
       }
     },
     "price": {
       "base_price": 599000,
       "sale_price": 499000,
       "cost_price": 350000,
       "currency": "KRW",
       "tax_rate": 10
     },
     "categories": [
       {
         "category_id": 5,
         "is_primary": true
       },
       {
         "category_id": 8,
         "is_primary": false
       }
     ],
     "option_groups": [
       {
         "name": "색상",
         "display_order": 1,
         "options": [
           {
             "name": "브라운",
             "additional_price": 0,
             "sku": "SOFA-BRN",
             "stock": 10,
             "display_order": 1
           },
           {
             "name": "블랙",
             "additional_price": 0,
             "sku": "SOFA-BLK",
             "stock": 15,
             "display_order": 2
           }
         ]
       },
       {
         "name": "소재",
         "display_order": 2,
         "options": [
           {
             "name": "천연 가죽",
             "additional_price": 100000,
             "sku": "SOFA-LTHR",
             "stock": 5,
             "display_order": 1
           },
           {
             "name": "인조 가죽",
             "additional_price": 0,
             "sku": "SOFA-FAKE",
             "stock": 20,
             "display_order": 2
           }
         ]
       }
     ],
     "images": [
       {
         "url": "https://example.com/images/sofa1.jpg",
         "alt_text": "브라운 소파 정면",
         "is_primary": true,
         "display_order": 1,
         "option_id": null
       },
       {
         "url": "https://example.com/images/sofa2.jpg",
         "alt_text": "브라운 소파 측면",
         "is_primary": false,
         "display_order": 2,
         "option_id": null
       }
     ],
     "tags": [1, 4, 7]
    }""";
    when(productCommandService.createProduct(any()))
        .thenReturn(objectMapper.readValue(requestBody, ProductSaveResponse.class));

    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isCreated())
        .andExpect(MockMvcResultMatchers.jsonPath("$.data").exists());
  }
}
