package com.laioffer.donationcollector.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "cart_item")
public class CartItem {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private double weight;

    @OneToOne(cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Item item;

    @ManyToOne
    @JsonIgnore
    private Cart cart;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }
}
