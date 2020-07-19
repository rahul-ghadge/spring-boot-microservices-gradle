package com.arya.microservices.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@AllArgsConstructor
@ToString
public class CountryCurrency implements Serializable {

    private String code;
    private String name;
    private String symbol;

}
