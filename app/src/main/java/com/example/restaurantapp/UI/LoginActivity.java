package com.example.restaurantapp.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.restaurantapp.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity implements OnMapReadyCallback {

    private FirebaseAuth auth;
    private GoogleMap map;
    private AlertDialog.Builder alertDialogBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        auth=FirebaseAuth.getInstance();
        alertDialogBuilder=new AlertDialog.Builder(LoginActivity.this);

        SupportMapFragment mapFragment= (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(this);
    }


    public void onClickLogin(View view){

        final EditText email=findViewById(R.id.emailField);
        EditText pass=findViewById(R.id.passField);

        if(email.getText().toString().isEmpty()||pass.getText().toString().isEmpty()){

            alertDialogBuilder.setTitle("Email or password field is empty");

            alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });

            AlertDialog alertDialog=alertDialogBuilder.create();
            alertDialog.show();

        }else {

            auth.signInWithEmailAndPassword(email.getText().toString(), pass.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {
                        Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                        startActivity(intent);
                    }else{

                        alertDialogBuilder.setTitle(Objects.requireNonNull(task.getException()).getMessage());
                        alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });

                        AlertDialog alertDialog=alertDialogBuilder.create();
                        alertDialog.show();
                    }
                }
            });
        }
    }

    public void onClickRegister(View view){

        Intent intent=new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        map=googleMap;
        map.setMinZoomPreference(16);
        LatLng location=new LatLng(46.547890,24.568380);
        map.addMarker(new MarkerOptions().position(location).title("Restaurant"));
        map.moveCamera(CameraUpdateFactory.newLatLng(location));
    }
}
