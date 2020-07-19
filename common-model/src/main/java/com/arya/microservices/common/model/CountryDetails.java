package com.arya.microservices.common.model;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Data
@ToString
public class CountryDetails implements Serializable {

    private String name;
    private String capital;
    private String region;
    private String subregion;
    private long population;
    private String description;

    private List<CountryCurrency> currencies;


    private int status;
    private String message;


}
