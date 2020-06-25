package com.example.restaurantapp.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.restaurantapp.Helpers.Drink;
import com.example.restaurantapp.Helpers.Pizza;
import com.example.restaurantapp.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class MenuItemDetailsActivity extends AppCompatActivity {    //problem on going back to fragment that lunches this activity


    private FirebaseAuth firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_item_details);

        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        TextView nameOfItem=findViewById(R.id.nameOfItem);
        TextView quantityOfItem=findViewById(R.id.quantityOfItem);
        TextView priceOfItem=findViewById(R.id.priceOfItem);
        TextView ingredientsOfItem=findViewById(R.id.ingredientsOfItem);

        TextView quantityLabel=findViewById(R.id.quantityLabel);

        if(getIntent().getSerializableExtra("pizza") instanceof Pizza) {

            Pizza pizza = (Pizza) getIntent().getSerializableExtra("pizza");

            nameOfItem.setText(pizza.getName());
            quantityOfItem.setText("123");
            priceOfItem.setText(pizza.getPrice());
            ingredientsOfItem.setText(pizza.getIngredients());
        }else{
            Drink drink = (Drink) getIntent().getSerializableExtra("drink");

            nameOfItem.setText(drink.getName());
            quantityOfItem.setText("123");
            priceOfItem.setText(drink.getPrice());
            ingredientsOfItem.setText(drink.getIngredients());
            quantityLabel.setText(R.string.quantityLabelDrink);
        }
        firebaseUser= FirebaseAuth.getInstance();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_action_button,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.logoutActionButton){

            firebaseUser.signOut();
            Intent intent=new Intent(MenuItemDetailsActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();

        }
        else if(item.getItemId()==R.id.cartActionButton){
            Intent intent=new Intent(MenuItemDetailsActivity.this, OrderActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }


    public void onClickAddToCart(View view){

        if(getIntent().getSerializableExtra("pizza") instanceof Pizza) {

            Pizza pizza = (Pizza) getIntent().getSerializableExtra("pizza");

            OrderActivity.orderList.add(pizza);
        }else{
            Drink drink = (Drink) getIntent().getSerializableExtra("drink");

            OrderActivity.orderList.add(drink);
        }
    }
}