package com.example.restaurantapp.Helpers;

public interface IMenuClickListener {

    void onItemClick(Pizza item);
    void onItemClick(Drink item);
    void addButtonClick(int position);
}
