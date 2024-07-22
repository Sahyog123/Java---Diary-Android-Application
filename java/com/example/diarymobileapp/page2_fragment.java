package com.example.diarymobileapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public class page2_fragment extends Fragment {

    TextView dateView2;
    DatabaseAdapter DB;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_page2_fragment, container, false);

        DB = new DatabaseAdapter(getContext());

        dateView2 = (TextView) view.findViewById(R.id.dateView2);
        String date = MainActivity.getDate();
        dateView2.setText(date);


        //When back button is clicked
        Button BackBtn = (Button) view.findViewById(R.id.Backbtn);
        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> Arr= MainActivity.getArrayList();
                Arr.remove(Arr.size()-1);
                MainActivity.setArrayList(Arr);
                replaceFragment(new page1_fragment());
            }
        });

        //When the clear text button is clicked
        Button ClearBtn = (Button) view.findViewById(R.id.Clearbtn);
        ClearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText EditText_Note = (EditText) view.findViewById(R.id.EditText_Note);
                EditText_Note.setText("");

            }
        });

        //When the save button is clicked
        Button saveBtn = (Button) view.findViewById(R.id.Savebtn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText EditText_Note = (EditText) view.findViewById(R.id.EditText_Note);

                String note = EditText_Note.getText().toString();
                String date = MainActivity.getDate();

                Boolean checkSavedata = DB.insertNotes(date,note);

                //If the entry have been inserted into the database then send message to confirm
                if (checkSavedata == true)
                {
                    Toast.makeText(getContext(),"Your Entry Has Been Saved", Toast.LENGTH_SHORT).show();
                }

                replaceFragment(new page3_fragment());
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