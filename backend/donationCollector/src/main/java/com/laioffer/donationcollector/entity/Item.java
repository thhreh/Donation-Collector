package com.laioffer.donationcollector.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.laioffer.donationcollector.entity.Donor;
@Entity
@Table(name = "item")
@JsonDeserialize(builder = Item.Builder.class)
public class Item implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String description;
    private String category;
    private String address;
    private double weight;

    @ManyToOne
    @JoinColumn(name="donor_id")
    private Donor donor;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ItemImage> images;

    public Item(){};

    private Item(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.description = builder.description;
        this.category = builder.category;
        this.address = builder.address;
        this.weight = builder.weight;
        this.donor = builder.donor;
        this.images = builder.images;
    }
    public Long getId() {
        return id;
    }

    public Item setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Item setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Item setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getCategory() {
        return category;
    }

    public Item setCategory(String category) {
        this.category = category;
        return this;
    }
    public String getAddress(){
        return address;
    }
    public Item setAddress(String address){
        this.address = address;
        return this;
    }

    public double getWeight() {
        return weight;
    }

    public Item setWeight(double weight) {
        this.weight = weight;
        return this;
    }

    public Donor getDonor() {
        return donor;
    }

    public Item setDonor(Donor donor) {
        this.donor = donor;
        return this;
    }

    public List<ItemImage> getImages() {
        return images;
    }

    public Item setImages(List<ItemImage> images) {
        this.images = images;
        return this;
    }

    public static class Builder {

        @JsonProperty("id")
        private Long id;

        @JsonProperty("name")
        private String name;

        @JsonProperty("description")
        private String description;

        @JsonProperty("category")
        private String category;

        @JsonProperty("address")
        private String address;

        @JsonProperty("weight")
        private double weight;

        @JsonProperty("donor")
        private Donor donor;

        @JsonProperty("images")
        private List<ItemImage> images;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setCategory(String category) {
            this.category = category;
            return this;
        }

        public Builder setWeight(double weight) {
            this.weight = weight;
            return this;
        }

        public Builder setAddress(String address) {
            this.address = address;
            return this;
        }

        public Builder setDonor(Donor donor) {
            this.donor = donor;
            return this;
        }


        public Builder setImages(List<ItemImage> images) {
            this.images = images;
            return this;
        }

        public Item build() {
            return new Item(this);
        }
    }

}
