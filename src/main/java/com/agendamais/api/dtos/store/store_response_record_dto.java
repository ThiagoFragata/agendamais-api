package com.agendamais.api.dtos.store;

import com.agendamais.api.dtos.address.address_response_record_dto;
import com.agendamais.api.dtos.category.category_response_record_dto;
import com.agendamais.api.dtos.employee.employee_response_record_dto;
import com.agendamais.api.dtos.service.service_response_record_dto;
import com.agendamais.api.models.store_model;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

public record store_response_record_dto(
        @NotNull Long id,
        @NotBlank @Size(max = 255) String name,
        String about,
        @NotNull Integer phone,
        @NotNull address_response_record_dto address,
        List<category_response_record_dto> categories,
        List<service_response_record_dto> services,
        List<employee_response_record_dto> employees
) {
    public store_response_record_dto(store_model store) {
        this(
                store.getId(),
                store.getName(),
                store.getAbout(),
                store.getPhone(),
                new address_response_record_dto(store.getAddress()),
                store.getCategories().stream()
                        .map(category_response_record_dto::new)
                        .toList(),
                store.getServices().stream()
                        .map(service_response_record_dto::new)
                        .toList(),
                store.getEmployees().stream()
                        .map(employee_response_record_dto::new)
                        .toList()
        );
    }
}

