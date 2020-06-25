package com.example.restaurantapp.UI;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.restaurantapp.Helpers.AsyncResponse;
import com.example.restaurantapp.Helpers.Drink;
import com.example.restaurantapp.Comm.FirebaseHelper;
import com.example.restaurantapp.Comm.MessageSender;
import com.example.restaurantapp.Helpers.Pizza;
import com.example.restaurantapp.R;
import com.example.restaurantapp.Helpers.RecyclerOrderAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Objects;

public class OrderActivity extends AppCompatActivity implements AsyncResponse {

    public static ArrayList<Object> orderList=new ArrayList<>();
    private String currentUser;
    private FirebaseHelper firebaseHelper =new FirebaseHelper();

    FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
    FirebaseDatabase database=FirebaseDatabase.getInstance();
    public static int firebaseStatus=1;       //if initialized to 0, first time will not set vaule to 1 in listener.???

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);


        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        final TextView totalPrice=findViewById(R.id.totalView);
        totalPrice.setText(firebaseHelper.getTotalPrice());
        RecyclerView recyclerViewOrder=findViewById(R.id.recyclerViewOrder);

        recyclerViewOrder.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        final RecyclerOrderAdapter recyclerOrderAdapter=new RecyclerOrderAdapter(orderList);
        recyclerViewOrder.setAdapter(recyclerOrderAdapter);

        recyclerOrderAdapter.setClickListener(new RecyclerOrderAdapter.OnItemClickListener() {
            @Override
            public void onDeleteClick(int position) {
                orderList.remove(position);
                recyclerOrderAdapter.notifyItemRemoved(position);
                totalPrice.setText(firebaseHelper.getTotalPrice());
            }
        });

    }



    @Override
    public void processFinish(String output) {      //tokenize the string in 2. 1 for ok and second for wait time of client

        String operationResult= firebaseHelper.tokenizer(output," "," ");
        String minutesToWait= firebaseHelper.tokenizer(output," ");
        if(operationResult.equals("1")){
            Log.e("tag",minutesToWait);
            orderNotification(minutesToWait);
        }
    }

    public void onClickPlaceOrder(View view) {
        
        if(user==null||database==null){
            MessageSender messageSender=new MessageSender();
            messageSender.delegate=this;    // sets delelegate/listener to this class
            messageSender.execute(concatMessageToServer(orderList));
        }
        else{
            firebaseHelper.write();
            if(firebaseStatus==1){
                orderNotification(waitTime());
            }
            else{
                AlertDialog.Builder alertDialogBuilder=new AlertDialog.Builder(this);
                alertDialogBuilder.setTitle("Something went wrong, please try again");

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
    }

    String waitTime(){

        int totalItems=orderList.size();

        if(totalItems<=2)
            return "4";
        else if(totalItems>2 && totalItems<=4)
            return "8";
        else if(totalItems>4&& totalItems<=8)
            return "13";
        else
            return "18";

    }

    private void orderNotification(String minutesToWait){

        Intent notificationIntent = new Intent(this, OrderActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationManager manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT >= 26)
        {
            //When sdk version is larger than26
            String id = "channel_1";
            String description = "143";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(id, description, importance);
                     //channel.enableLights(true);
                     //channel.enableVibration(true);//
            manager.createNotificationChannel(channel);
            Notification notification = new Notification.Builder(OrderActivity.this, id)
                    .setCategory(Notification.CATEGORY_MESSAGE)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("Thank you "+user.getEmail()+ "!")
                    .setContentText("Yor order will be at your table in "+minutesToWait+" min")         //set random time
                    .setContentIntent(contentIntent)
                    .setAutoCancel(true)
                    .build();

            manager.notify(1, notification);
        }

    }


    public String concatMessageToServer(ArrayList<Object> list){
        StringBuilder message=new StringBuilder();
        message.append(user.getEmail());
        message.append(" ");

        message.append("food");   //way to recognize drinks in server
        message.append(" ");

        for(Object item: list) {
            if(item instanceof Pizza) {
                message.append(((Pizza) item).getName());
                message.append(" ");
            }
           /* else if(item instanceof Drink){
                message.append(((Drink) item).getName());
                message.append(" ");

            }*/
        }

        message.append("drinks");   //way to recognize drinks in server
        message.append(" ");

        for(Object item: list){
            if(item instanceof Drink){
                message.append(((Drink) item).getName());
                message.append(" ");

            }

        }

        message.append("total");
        message.append(" ");

            message.append(firebaseHelper.getTotalPrice()+"\r\n");

        return String.valueOf(message);

    }
}