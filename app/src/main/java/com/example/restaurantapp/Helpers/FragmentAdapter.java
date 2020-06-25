package com.example.restaurantapp.Helpers;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.restaurantapp.UI.DrinkFragment;
import com.example.restaurantapp.UI.FoodFragment;

public class FragmentAdapter extends FragmentStatePagerAdapter {


    private FoodFragment foodFragment;
    private DrinkFragment drinkFragment;

    public FragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            foodFragment = new FoodFragment();
            return  foodFragment;
        } else if (position == 1)
            drinkFragment = new DrinkFragment();
        return drinkFragment;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
