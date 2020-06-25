package com.example.restaurantapp.Helpers;

import com.example.restaurantapp.R;

import java.io.Serializable;

public class Pizza implements Serializable {

    private String name;
    private int imageResourceId;
    private String price;
    private String ingredients;

    private Pizza(String name, int imageResourceId,String price,String ingredients) {
        this.name = name;
        this.imageResourceId = imageResourceId;
        this.price=price;
        this.ingredients=ingredients;
    }

    public static final Pizza[] pizzas = {
            new Pizza("Diavolo", R.drawable.diavolo,"25 lei","ingredient 1, ingredient 2"),
            new Pizza("Funghi", R.drawable.funghi,"35 lei", "ingredient 1, ingredient 2"),
            new Pizza("Funghi", R.drawable.funghi,"35 lei","ingredient 1, ingredient 2"),
            new Pizza("Funghi", R.drawable.funghi,"35 lei","ingredient 1, ingredient 2"),
            new Pizza("Funghi", R.drawable.funghi,"35 lei","ingredient 1, ingredient 2"),
            new Pizza("Funghi", R.drawable.funghi,"35 lei", "ingredient 1, ingredient 2"),
    };

    public String getName() {
        return name;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public String getPrice() {
        return price;
    }

    public String getIngredients() {
        return ingredients;
    }
}