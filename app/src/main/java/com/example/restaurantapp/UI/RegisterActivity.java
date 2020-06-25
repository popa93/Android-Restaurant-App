package com.example.restaurantapp.UI;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.restaurantapp.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {
    //creates account
    //sends data to server and server creates client in db

    private FirebaseAuth authentication;
    private EditText email;
    private EditText password;
    private EditText confirmPassword;
    private AlertDialog.Builder alertDialogBuilder;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        alertDialogBuilder=new AlertDialog.Builder(RegisterActivity.this);

        authentication=FirebaseAuth.getInstance();
        email=findViewById(R.id.registerEmail);
        password=findViewById(R.id.registerPass);
        confirmPassword=findViewById(R.id.registerConfirmPass);
    }

    public void onClickRegister(View view){

        if(email.getText().toString().isEmpty()||password.getText().toString().isEmpty()||confirmPassword.getText().toString().isEmpty()){

            alertDialogBuilder.setTitle("All fields must be completed");
            alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });

            AlertDialog alertDialog=alertDialogBuilder.create();
            alertDialog.show();


        }else if(!(password.getText().toString().equals(confirmPassword.getText().toString()))){

            alertDialogBuilder.setTitle("Password fields must be identical");
            alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });

            AlertDialog alertDialog=alertDialogBuilder.create();
            alertDialog.show();
        }
        else if(!(email.getText().toString().matches(emailPattern))){

            alertDialogBuilder.setTitle("Email field bad format");
            alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });

            AlertDialog alertDialog=alertDialogBuilder.create();
            alertDialog.show();
        }
        else{
            int letterCountPass=password.getText().toString().length();
           // int letterCountConfPass=confirmPassword.getText().toString().length();

            if(letterCountPass>=6) {
                authentication.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString());

                Intent intent = new Intent(RegisterActivity.this, MenuActivity.class);
                startActivity(intent);
            }else{

                alertDialogBuilder.setTitle("Password must contain at least 6 characters");
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

       /* if(password.getText().toString().equals(confirmPassword.getText().toString())){

            authentication.createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString());

            Intent intent=new Intent(RegisterActivity.this, MenuActivity.class);
            startActivity(intent);

        }*/
    }

}
