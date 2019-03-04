package com.pavel_katsuba.talks.dto;

import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.annotation.JsonView;
import com.pavel_katsuba.talks.beans.Views;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonView(Views.Id.class)
public class WsEventDto {
    private ObjectType objectType;
    private EventType eventType;
    @JsonRawValue
    private String body;
}