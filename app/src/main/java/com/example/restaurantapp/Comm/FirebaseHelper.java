package com.example.restaurantapp.Comm;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.restaurantapp.UI.OrderActivity;
import com.example.restaurantapp.Helpers.Drink;
import com.example.restaurantapp.Helpers.Order;
import com.example.restaurantapp.Helpers.Pizza;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;



public
class FirebaseHelper {

    private FirebaseDatabase database= FirebaseDatabase.getInstance();
    private FirebaseUser currentUser= FirebaseAuth.getInstance().getCurrentUser();
   // private DatabaseReference dataReference;
    //private String userEmail=currentUser.getEmail();
    private String food;
    private String drink;
    private String total;
    private String date;



    public void write() {

        String userEmail=currentUser.getEmail();
        String foodString=foodString();
        String drinkString=drinkString();
        total=getTotalPrice();
        date=getDate();

        DatabaseReference dataReference = database.getReference(formatUserEmail(userEmail));

        dataReference.child(formatDate(date)).setValue(new Order(foodString, drinkString, total, date), new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                if(databaseError==null)
                    OrderActivity.firebaseStatus=1;
                else
                    OrderActivity.firebaseStatus=0;
            }
        });

    }



    String formatDate(String date){


        String formatedDate;
        formatedDate=date.replace(date.charAt(4),'s');
        return formatedDate;
    }




    String formatUserEmail(String userEmail){

       String formatedUserEmail=userEmail.replace(userEmail.charAt(14), 'a');
       formatedUserEmail=userEmail.replace(userEmail.charAt(userEmail.length()-4), 'p');
        return formatedUserEmail;
    }

    private String foodString(){
        StringBuilder stringBuilderFood=new StringBuilder();
       // StringBuilder stringBuilderDrink=new StringBuilder();

        for(Object item:OrderActivity.orderList){

            if(item instanceof Pizza){
                stringBuilderFood.append(((Pizza) item).getName());
                stringBuilderFood.append(" ");
            }
           /* else if(item instanceof Drink){
                stringBuilderDrink.append(((Drink) item).getName());
                stringBuilderDrink.append(" ");

            }*/
        }

        return stringBuilderFood.toString();
        //drink=stringBuilderDrink.toString();


    }

    private String drinkString(){
       // StringBuilder stringBuilderFood=new StringBuilder();
        StringBuilder stringBuilderDrink=new StringBuilder();

        for(Object item:OrderActivity.orderList){

           /* if(item instanceof Pizza){
                stringBuilderFood.append(item);
                stringBuilderFood.append(" ");
            }
            else*/ if(item instanceof Drink){
                stringBuilderDrink.append(((Drink) item).getName());
                stringBuilderDrink.append(" ");

            }
        }

        //return stringBuilderFood.toString();
        return stringBuilderDrink.toString();


    }

    private String getDate(){
        SimpleDateFormat dateFormat=new SimpleDateFormat("dd/M//yyyy hh:mm:ss");

        return dateFormat.format(new Date());
    }

    public String tokenizer(String result,String delim,String troll){
        StringTokenizer tokenizer=new StringTokenizer(result,delim);




        return  tokenizer.nextToken();
    }


    public String tokenizer(String result,String delim){
        StringTokenizer tokenizer=new StringTokenizer(result,delim);

        tokenizer.nextToken();


        return  tokenizer.nextToken();
    }


    public float tokenizer(String price){

        StringTokenizer tokenizer=new StringTokenizer(price);

        String aux= tokenizer.nextToken();

        float productPrice=Float.parseFloat(aux);

        return productPrice;
    }

    public String getTotalPrice(){

        float total=0;

        for(Object item: OrderActivity.orderList){

            if(item instanceof Pizza)
                total+=tokenizer(((Pizza) item).getPrice());
            else if(item instanceof Drink)
                total+=tokenizer(((Drink) item).getPrice());
        }

        return total+"lei";
    }


}
