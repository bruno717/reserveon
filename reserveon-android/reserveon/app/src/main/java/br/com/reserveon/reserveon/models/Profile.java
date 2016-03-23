package br.com.reserveon.reserveon.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Bruno on 17/03/2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)

public class Profile {

    @JsonProperty("Id")
    private Integer id;

    @JsonProperty("Name")
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
