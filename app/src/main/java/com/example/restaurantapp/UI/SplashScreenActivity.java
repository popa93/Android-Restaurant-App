package com.example.restaurantapp.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.restaurantapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreenActivity extends AppCompatActivity {

    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Handler handler=new Handler();
        user= FirebaseAuth.getInstance().getCurrentUser();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if(user==null){
                    Intent intent=new Intent(SplashScreenActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Intent intent=new Intent(SplashScreenActivity.this, MenuActivity.class);
                    startActivity(intent);
                    finish();

                }
            }
        },1500);

    }
}
