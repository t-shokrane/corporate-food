package com.corporate.food.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.corporate.food.domain.enums.CompanyType;
import com.corporate.food.dto.CompanyRequest;
import com.corporate.food.dto.CompanyResponse;
import com.corporate.food.dto.PagedResponse;
import com.corporate.food.exception.GlobalExceptionHandler;
import com.corporate.food.service.CompanyService;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

@WebMvcTest(CompanyController.class)
@Import(GlobalExceptionHandler.class)
@AutoConfigureMockMvc(addFilters = false)
class CompanyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private CompanyService companyService;

    @Test
    void findAll_returnsPagedCompanies() throws Exception {
        CompanyResponse company = companyResponse(1L, "گروه الین", CompanyType.HOLDING, null);
        PagedResponse<CompanyResponse> paged = PagedResponse.<CompanyResponse>builder()
                .items(List.of(company))
                .page(0)
                .size(10)
                .totalElements(1)
                .totalPages(1)
                .build();

        when(companyService.findAll(any())).thenReturn(paged);

        mockMvc.perform(get("/api/v1/companies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.items[0].id").value(1))
                .andExpect(jsonPath("$.data.items[0].name").value("گروه الین"))
                .andExpect(jsonPath("$.data.totalElements").value(1));
    }

    @Test
    void findById_returnsCompany() throws Exception {
        CompanyResponse company = companyResponse(1L, "گروه الین", CompanyType.HOLDING, null);
        when(companyService.findById(1L)).thenReturn(company);

        mockMvc.perform(get("/api/v1/companies/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.companyType").value("HOLDING"));
    }

    @Test
    void create_returnsCreatedCompany() throws Exception {
        CompanyRequest request = new CompanyRequest();
        request.setName("شرکت جدید");
        request.setCompanyType(CompanyType.SUBSIDIARY);

        CompanyResponse created = companyResponse(5L, "شرکت جدید", CompanyType.SUBSIDIARY, null);
        when(companyService.create(any(CompanyRequest.class))).thenReturn(created);

        mockMvc.perform(post("/api/v1/companies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Resource created successfully"))
                .andExpect(jsonPath("$.data.id").value(5))
                .andExpect(jsonPath("$.data.name").value("شرکت جدید"));
    }

    @Test
    void create_withBlankName_returnsBadRequest() throws Exception {
        CompanyRequest request = new CompanyRequest();
        request.setName("");

        mockMvc.perform(post("/api/v1/companies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400));
    }

    @Test
    void update_returnsUpdatedCompany() throws Exception {
        CompanyRequest request = new CompanyRequest();
        request.setName("گروه الین");
        request.setCompanyType(CompanyType.HOLDING);

        CompanyResponse updated = companyResponse(1L, "گروه الین", CompanyType.HOLDING, null);
        when(companyService.update(eq(1L), any(CompanyRequest.class))).thenReturn(updated);

        mockMvc.perform(put("/api/v1/companies/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Company updated successfully"))
                .andExpect(jsonPath("$.data.id").value(1));
    }

    @Test
    void delete_returnsSuccessMessage() throws Exception {
        mockMvc.perform(delete("/api/v1/companies/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Resource deleted successfully"));

        verify(companyService).delete(1L);
    }

    private CompanyResponse companyResponse(Long id, String name, CompanyType type, Long parentId) {
        return CompanyResponse.builder()
                .id(id)
                .name(name)
                .companyType(type)
                .parentCompanyId(parentId)
                .createdOn(LocalDateTime.now())
                .updatedOn(LocalDateTime.now())
                .build();
    }
}
