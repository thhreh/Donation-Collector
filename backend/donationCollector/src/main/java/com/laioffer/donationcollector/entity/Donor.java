package com.laioffer.donationcollector.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "donor")
@JsonDeserialize(builder = Donor.Builder.class)
public class Donor implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private String username; //email

    @JsonIgnore
    private String password;

    @JsonIgnore
    private boolean enabled;

    @JsonIgnore
    private String name;

    @JsonIgnore
    private String address;
    @JsonIgnore
    private String contact;

    public String getName() {
        return name;
    }

    public Donor setName(String name) {
        this.name = name;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public Donor setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getContact() {
        return contact;
    }

    public Donor setContact(String contact) {
        this.contact = contact;
        return this;
    }

    public Donor(){}

    public Donor(Builder builder) {
        this.username = builder.username;
        this.password = builder.password;
        this.enabled = builder.enabled;
        this.name = builder.name;
        this.address = builder.address;
        this.contact = builder.contact;
    }

    public String getUsername() {
        return username;
    }

    public Donor setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public Donor setPassword(String password) {
        this.password = password;
        return this;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public Donor setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public static class Builder {
        @JsonProperty("username")
        private String username;

        @JsonProperty("password")
        private String password;

        @JsonProperty("name")
        private String name;

        @JsonProperty("address")
        private String address;
        @JsonProperty("contact")
        private String contact;

        @JsonProperty("enabled")
        private boolean enabled;

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setAddress(String address) {
            this.address = address;
            return this;
        }

        public Builder setContact(String contact) {
            this.contact = contact;
            return this;
        }

        public Builder setUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder setEnabled(boolean enabled) {
            this.enabled = enabled;
            return this;
        }

        public Donor build() {
            return new Donor(this);
        }
    }
}
