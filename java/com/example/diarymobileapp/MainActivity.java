package com.example.diarymobileapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity{

    Fragment fragment;
    static String date;
    static ArrayList<String> arrayList= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Load the first fragment
        replaceFragment(new page1_fragment());

        //When show all entries button is clicked
        Button ShowAllEntries = findViewById(R.id.ShowAllEntries);
        ShowAllEntries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Load the third fragment
                replaceFragment(new page3_fragment());
            }
        });





    }

    /**
     * This method is called to load the fragment
     * @param fragment - The fragment to load
     */
    private void replaceFragment(Fragment fragment)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainerView,fragment);
        fragmentTransaction.commit();

    }

    //Set the date which was picked by the user
    public static void setDate(String Date){
        date = Date;
    }
    //Get the user picked date
    public static String getDate(){
        return date;
    }

    //Get the arraylist containing all the dates which already have been entered in the app
    public static ArrayList<String> getArrayList(){
        return arrayList;
    }
    //Set the array list
    public static void setArrayList(ArrayList<String> arr){
        arrayList = arr;
    }

}