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
 * Use the {@link DrinkFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DrinkFragment extends Fragment {   //creeaza dupa cum e in mzgren link. fiecare fragment cu alt numar de iteme in lista
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private static final String FRAG_NAME="Drink";

    public DrinkFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DrinkFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DrinkFragment newInstance(String param1, String param2) {
        DrinkFragment fragment = new DrinkFragment();
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
        RecyclerView recyclerView=(RecyclerView) inflater.inflate(R.layout.fragment_drink, container, false);
        setupRecyclerView(recyclerView);
        return recyclerView;
    }

    private void setupRecyclerView(RecyclerView recyclerView){

        String[]drinkNames=new String[Drink.drinks.length];

        for(int i=0;i<drinkNames.length;i++){
            drinkNames[i]=Drink.drinks[i].getName();

        }

        int[] drinkImages = new int[Drink.drinks.length];
        for (int i = 0; i < drinkImages.length; i++) {
            drinkImages[i] = Drink.drinks[i].getImageResourceId();
        }


        String[] drinkPrices = new String[Drink.drinks.length];
        for (int i = 0; i < drinkPrices.length; i++) {
            drinkPrices[i] = Drink.drinks[i].getPrice();
        }

        ArrayList<Drink> drinkList=new ArrayList<>();

        for(int i=0;i<drinkNames.length;i++){
            drinkList.add(Drink.drinks[i]);

        }

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        // RecyclerAdapter recyclerAdapter=new RecyclerAdapter(pizzaImages,pizzaNames,toString(),pizzaPrices);

        RecyclerAdapter recyclerAdapter=new RecyclerAdapter(toString(),drinkList,"");
        recyclerView.setAdapter(recyclerAdapter);

        recyclerAdapter.setClickListener(new IMenuClickListener() {
            @Override
            public void onItemClick(Pizza item) {

            }

            @Override
            public void onItemClick(Drink item) {
                Intent intent=new Intent(getActivity(), MenuItemDetailsActivity.class);
                intent.putExtra("drink",item);
                Toast.makeText(getActivity(), item.getName(), Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }

            @Override
            public void addButtonClick(int position) {

                OrderActivity.orderList.add(Drink.drinks[position]);
                Toast.makeText(getActivity(), Drink.drinks[position].getName()+" added to cart", Toast.LENGTH_SHORT).show();
            }

        });
    }

    @Override
    public String toString() {
        return FRAG_NAME;
    }
}
