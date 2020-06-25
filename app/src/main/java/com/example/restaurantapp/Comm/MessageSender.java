package com.example.restaurantapp.Comm;

import android.os.AsyncTask;
import android.util.Log;

import com.example.restaurantapp.Helpers.AsyncResponse;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public
class MessageSender extends AsyncTask<String,Void,String> {


    Socket socket;
    DataOutputStream outStream;
    PrintWriter printWriter;
    InputStreamReader isr;
    BufferedReader bufferedReader;
    public AsyncResponse delegate=null;

    @Override
    protected String doInBackground(String... arg) {

        String message=arg[0];

        try {
            socket = new Socket("192.168.0.102", 3001);
            printWriter=new PrintWriter(socket.getOutputStream());
            printWriter.write(message);
            /*for(Object item: list) {
                if(item instanceof Pizza)
                printWriter.write(((Pizza) item).getName() + "\r\n");//terminare msg
                else if(item instanceof Drink){
                    printWriter.write(((Drink) item).getName() + "\r\n");//terminare msg

                }
            }*/

            printWriter.flush();
           // printWriter.close();

        }catch (Exception e){

            e.printStackTrace();
        }

        try {


            isr = new InputStreamReader(socket.getInputStream());
            bufferedReader = new BufferedReader(isr);
            message = bufferedReader.readLine();
            Log.e("test", message);

            socket.close();

        }catch (Exception e){}

        return message;
    }

    @Override
    protected void onPostExecute(String s) {
        delegate.processFinish(s);

    }

}
