package com.laioffer.donationcollector.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "cart_item")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private double weight;

    @OneToOne
    private Item item;

    @ManyToOne
    @JsonIgnore
    private Cart cart;
}
