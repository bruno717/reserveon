package br.com.reserveon.reserveon.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.orm.SugarRecord;

/**
 * Created by Bruno on 17/03/2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)

public class Profile extends SugarRecord {

    @JsonProperty("Id")
    private Long id;

    @JsonProperty("Name")
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
