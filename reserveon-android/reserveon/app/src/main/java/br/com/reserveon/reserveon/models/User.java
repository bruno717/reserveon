package br.com.reserveon.reserveon.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Bruno on 17/03/2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

    @JsonProperty("Id")
    private Integer id;

    @JsonProperty("Name")
    private String name;

    @JsonProperty("Email")
    private String email;

    @JsonProperty("Password")
    private String password;

    @JsonProperty("ProfileId")
    private Integer profileId;

    @JsonProperty("Profile")
    private Profile profile;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getProfileId() {
        return profileId;
    }

    public void setProfileId(Integer profileId) {
        this.profileId = profileId;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
}
