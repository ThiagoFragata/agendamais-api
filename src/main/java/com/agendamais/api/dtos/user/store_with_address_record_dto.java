package com.agendamais.api.dtos.user;


public record store_with_address_record_dto(
        store_record_dto store,
        address_record_dto address
) {}
