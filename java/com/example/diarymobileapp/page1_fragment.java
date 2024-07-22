package com.example.diarymobileapp;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.diarymobileapp.databinding.ActivityMainBinding;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class page1_fragment extends Fragment{

    TextView DateDisplay;
    DatePickerDialog.OnDateSetListener setListener;

    ArrayList<String> ChosenDates;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_page1_fragment, container, false);


        Button NextBtn = (Button) view.findViewById(R.id.Next);

        Button ViewAll = (Button) view.findViewById(R.id.ShowAllEntries);

        ChosenDates = MainActivity.getArrayList();



        DateDisplay = (TextView) view.findViewById(R.id.dateView);
        System.out.println(DateDisplay.getText());

        //Initialise calender variable
        Calendar calender = Calendar.getInstance();
        final int year = calender.get(Calendar.YEAR);
        final int month = calender.get(Calendar.MONTH);
        final int day = calender.get(Calendar.DAY_OF_MONTH);

        //When PickDate button is clicked
        Button PickDate = (Button) view.findViewById(R.id.PickDate);
        PickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Open a datePickerDialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month = month+1;
                        String date = day + "/" + month + "/" + year;
                        //Set the date in MainActivity class
                        MainActivity.setDate(date);
                        DateDisplay.setText(date);

                    }
                },year,month,day);
                datePickerDialog.show();

            }
        });

        //When next button is clicked
        NextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //If the user has not picked a date then do not proceed and send a msg back
                if (DateDisplay.getText().equals("Pick Date"))
                {
                   Toast.makeText(getActivity(), "Please pick a date!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // The following lines check if the date selected already has a note
                String Search = DateDisplay.getText().toString();

                int checkIndexOf = ChosenDates.indexOf(DateDisplay.getText().toString());

                if(ChosenDates.size() > 0) {
                    for (int i = 0; i <= ChosenDates.size()-1; i++) {
                        //Check if the selected date is in the arraylist
                        if (Search.equalsIgnoreCase(ChosenDates.get(i)))
                        {
                            Toast.makeText(getActivity(), "This date already contains an entry!", Toast.LENGTH_SHORT).show();
                            return;
                        }

                    }
                }
                //If chosen date is not in arraylist then proceed
                //Add the date to the arraylist to prevent duplicate data
                ChosenDates.add(ChosenDates.size(),Search);
                MainActivity.setArrayList(ChosenDates);

                replaceFragment(new page2_fragment());


            }
        });


        return view;
    }

    /**
     * Method to change the fragment view
     * @param fragment
     */
    private void replaceFragment(Fragment fragment){

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainerView,fragment);
        fragmentTransaction.commit();

    }
}