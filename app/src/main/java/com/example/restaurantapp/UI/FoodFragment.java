package com.example.restaurantapp.UI;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.example.restaurantapp.Helpers.Drink;
import com.example.restaurantapp.Helpers.IMenuClickListener;
import com.example.restaurantapp.Helpers.Pizza;
import com.example.restaurantapp.R;
import com.example.restaurantapp.Helpers.RecyclerAdapter;

import java.util.ArrayList;



/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FoodFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FoodFragment extends Fragment {                //ca trb implementat in xml pe langa imageview si textView si un
                                                            //buton care sa adauge in carucior produsul pentru a putea fi comandat

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private static final String FRAG_NAME="Food";

    public FoodFragment() {
        // Required empty public constructor
    }

    public static FoodFragment newInstance(String param1, String param2) {
        FoodFragment fragment = new FoodFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        RecyclerView recyclerView=(RecyclerView) inflater.inflate(R.layout.fragment_food, container, false);
        setupRecyclerView(recyclerView);
        return recyclerView;
    }


    private void setupRecyclerView(RecyclerView recyclerView){

        String[]pizzaNames=new String[Pizza.pizzas.length];

        for(int i=0;i<pizzaNames.length;i++){
            pizzaNames[i]=Pizza.pizzas[i].getName();

        }

        int[] pizzaImages = new int[Pizza.pizzas.length];
        for (int i = 0; i < pizzaImages.length; i++) {
            pizzaImages[i] = Pizza.pizzas[i].getImageResourceId();
        }


        String[] pizzaPrices = new String[Pizza.pizzas.length];
        for (int i = 0; i < pizzaImages.length; i++) {
            pizzaPrices[i] = Pizza.pizzas[i].getPrice();
        }

        ArrayList<Pizza> pizzaList=new ArrayList<>();

        for(int i=0;i<pizzaNames.length;i++){
            pizzaList.add(Pizza.pizzas[i]);

        }

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
       // RecyclerAdapter recyclerAdapter=new RecyclerAdapter(pizzaImages,pizzaNames,toString(),pizzaPrices);
        RecyclerAdapter recyclerAdapter=new RecyclerAdapter(toString(),pizzaList);
        recyclerView.setAdapter(recyclerAdapter);

        recyclerAdapter.setClickListener(new IMenuClickListener() {
            @Override
            public void onItemClick(Pizza item) {
                Intent intent=new Intent(getActivity(), MenuItemDetailsActivity.class);
                intent.putExtra("pizza",item);
                Toast.makeText(getActivity(), item.getName(), Toast.LENGTH_SHORT).show();
                startActivity(intent);

            }

            @Override
            public void onItemClick(Drink item) {

            }

            @Override
            public void addButtonClick(int position) {
                OrderActivity.orderList.add(Pizza.pizzas[position]);
                Toast.makeText(getActivity(), Pizza.pizzas[position].getName()+" added to cart", Toast.LENGTH_SHORT).show();
            }

        });


    }


    @Override
    public String toString() {
        return FRAG_NAME;
    }
}
