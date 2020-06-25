package com.example.restaurantapp.Helpers;

import com.example.restaurantapp.R;

import java.io.Serializable;

public
class Drink implements Serializable {

    private String name;
    private String price;
    private int imageResourceId;
    private String ingredients;

    public Drink(String name, int imageResourceId, String price,String ingredients) {
        this.name = name;
        this.price = price;
        this.imageResourceId = imageResourceId;
        this.ingredients=ingredients;
    }


    public static final Drink[] drinks = {
            new Drink("Cappuccino", R.drawable.cappuccino,"25 lei","ingredient 1, ingredient 2"),
            new Drink("Latte", R.drawable.latte,"35 lei","ingredient 1, ingredient 2"),
            new Drink("Filter", R.drawable.filter,"35 lei","ingredient 1, ingredient 2"),
            new Drink("Filter", R.drawable.filter,"35 lei", "ingredient 1, ingredient 2"),
            new Drink("Filter", R.drawable.filter,"35 lei", "ingredient 1, ingredient 2"),

    };

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public String getIngredients() {
        return ingredients;
    }
}
