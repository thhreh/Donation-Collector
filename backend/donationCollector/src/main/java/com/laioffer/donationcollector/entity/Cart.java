package com.laioffer.donationcollector.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name ="cart")
public class Cart implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(unique = true)
    private NGO ngo;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, fetch=FetchType.EAGER)
    private List<CartItem> cartItemList;

    private double totalWeight;

    public int getId() {
        return id;
    }

    public Cart setId(int id) {
        this.id = id;
        return this;
    }

    public List<CartItem> getCartItemList() {
        return cartItemList;
    }

    public Cart setCartItemList(List<CartItem> cartItemList) {
        this.cartItemList = cartItemList;
        return this;
    }

    public double getTotalWeight() {
        return totalWeight;
    }

    public Cart setTotalWeight(double totalWeight) {
        this.totalWeight = totalWeight;
        return this;
    }
}