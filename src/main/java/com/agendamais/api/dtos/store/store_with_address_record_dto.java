package com.agendamais.api.dtos.store;


import com.agendamais.api.dtos.address.address_record_dto;

public record store_with_address_record_dto(
        store_record_dto store,
        address_record_dto address
) {}
