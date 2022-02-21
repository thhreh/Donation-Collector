package com.laioffer.donationcollector.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ngo")
@JsonDeserialize(builder = NGO.Builder.class)
public class NGO implements Serializable {
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

    @JsonIgnore
    private String distance;

    @JsonIgnore
    private String prefCategory;

    @JsonIgnore
    private double prefWeight;

    @JsonIgnore
    private double currentWeight;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(unique = true)
    private Cart cart;

    public NGO(){}

    public NGO(Builder builder) {
        this.username = builder.username;
        this.password = builder.password;
        this.enabled = builder.enabled;
        this.name = builder.name;
        this.address = builder.address;
        this.contact = builder.contact;
        this.distance = builder.distance;
        this.prefCategory = builder.prefCategory;
        this.prefWeight = builder.prefWeight;
        this.currentWeight = builder.currentWeight;
        this.cart = builder.cart;
    }
    public String getUsername() {
        return username;
    }

    public NGO setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public NGO setPassword(String password) {
        this.password = password;
        return this;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public NGO setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public String getName() {
        return name;
    }

    public NGO setName(String name) {
        this.name = name;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public NGO setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getContact() {
        return contact;
    }

    public NGO setContact(String contact) {
        this.contact = contact;
        return this;
    }

    public String getDistance() {
        return distance;
    }

    public NGO setDistance(String distance) {
        this.distance = distance;
        return this;
    }

    public String getPrefCategory() {
        return prefCategory;
    }

    public NGO setPrefCategory(String prefCategory) {
        this.prefCategory = prefCategory;
        return this;
    }

    public double getPrefWeight() {
        return prefWeight;
    }

    public NGO setPreferredWeight(double prefWeight) {
        this.prefWeight = prefWeight;
        return this;
    }

    public double getCurrentWeight() {
        return currentWeight;
    }

    public NGO setCurrentWeight(double currentWeight) {
        this.currentWeight = currentWeight;
        return this;
    }

    public Cart getCart() {
        return cart;
    }

    public NGO setCart(Cart cart) {
        this.cart = cart;
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

        @JsonProperty("distance")
        private String distance;

        @JsonProperty("prefCategory")
        private String prefCategory;

        @JsonProperty("prefWeight")
        private double prefWeight;

        @JsonProperty("currentWeight")
        private double currentWeight;

        @JsonProperty("cart")
        private Cart cart;

        public Builder setDistance(String distance) {
            this.distance = distance;
            return this;
        }

        public Builder setPrefCategory(String prefCategory) {
            this.prefCategory = prefCategory;
            return this;
        }

        public Builder setPreferredWeight(double prefWeight) {
            this.prefWeight = prefWeight;
            return this;
        }

        public Builder setCurrentWeight(double currentWeight) {
            this.currentWeight = currentWeight;
            return this;
        }

        public Builder setCart(Cart cart) {
            this.cart = cart;
            return this;
        }

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

        public NGO build() {
            return new NGO(this);
        }
    }
}
