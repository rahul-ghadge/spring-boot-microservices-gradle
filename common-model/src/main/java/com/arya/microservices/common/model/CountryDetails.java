package com.arya.microservices.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class CountryDetails implements Serializable {

   	private static final long serialVersionUID = 1L;
   	
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
