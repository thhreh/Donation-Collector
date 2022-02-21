package com.laioffer.donationcollector.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "item_image")
public class ItemImage implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private String url;

    @ManyToOne
    @JoinColumn(name = "item_id")
    @JsonIgnore
    private Item item;

    public ItemImage() {
    }

    public ItemImage(String url, Item item) {
        this.url = url;
        this.item = item;
    }

    public String getUrl() {
        return url;
    }

    public ItemImage setUrl(String url) {
        this.url = url;
        return this;
    }

    public Item getItem() {
        return item;
    }

    public ItemImage setItem(Item item) {
        this.item = item;
        return this;
    }
}
