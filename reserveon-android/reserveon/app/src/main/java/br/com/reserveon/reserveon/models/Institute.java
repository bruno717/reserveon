package br.com.reserveon.reserveon.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Bruno on 17/04/2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Institute {

    @JsonProperty("Id")
    private Long id;

    @JsonProperty("Name")
    private String name;

    @JsonProperty("Address")
    private String Address;

    @JsonProperty("Register")
    private String register;

    @JsonProperty("Image")
    private String image;

    @JsonProperty("Expedient")
    private String expedient;

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

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getRegister() {
        return register;
    }

    public void setRegister(String register) {
        this.register = register;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getExpedient() {
        return expedient;
    }

    public void setExpedient(String expedient) {
        this.expedient = expedient;
    }
}
