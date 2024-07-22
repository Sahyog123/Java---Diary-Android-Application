package com.example.diarymobileapp;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.diarymobileapp.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.Calendar;


public class page3_fragment extends Fragment {

    TextView dateView2;
    Button deletebtn, newEntrybtn, filterbtn, deleteallbtn;
    String date;

    ListView listView;
    ArrayList<String> arrayList,arrayList2;
    ArrayAdapter<String> adapter,adapter2;

    DatabaseAdapter DB;
    ListAdapter ListAdapter;

    String Entry;
    String Selectedposition;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_page3_fragment, container, false);

        DB = new DatabaseAdapter(getContext());
        Cursor res = DB.getAllNotes();
        //Check if there are notes saved
        if(res.getCount() == 0)
        {
            Toast.makeText(getContext(), "No Notes available", Toast.LENGTH_SHORT).show();
        }


        listView = (ListView) view.findViewById(R.id.listView);
        arrayList = new ArrayList<>();
        adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(adapter);
        //Add all the data saved in the sql to the arraylist to display in the listview
        while(res.moveToNext())
        {
            Entry = "Date: "+ res.getString(1)+" - "+res.getString(2);
            arrayList.add(Entry);
        }

        //If new entry button is clicked
        newEntrybtn = (Button) view.findViewById(R.id.NewEntBtn);
        newEntrybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //The user is taken back to the first page to pick a date for the new entry
                replaceFragment(new page1_fragment());
            }
        });


        //If the delete all button is clicked
        deleteallbtn = (Button) view.findViewById(R.id.DeleteAllBtn);
        deleteallbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Boolean deleteQuery = DB.deleteAllNotes();
                //If the delete query succeeds
                if (deleteQuery = true)
                {
                    //Reload the fragment
                    replaceFragment(new page3_fragment());
                    //Inform the user about the action performed
                    Toast.makeText(getContext(),"All Entries have been deleted", Toast.LENGTH_SHORT).show();
                    ArrayList<String> arr = new ArrayList<>();
                    MainActivity.setArrayList(arr);

                }
            }
        });

        //If the note in the list view is clicked
        AdapterView<?> selected;
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Inform the user about the action performed
                String itemselected = "You Selected" +  parent.getItemAtPosition(position).toString();
                Toast.makeText(getContext(),itemselected,Toast.LENGTH_SHORT).show();
                //Make set selected true
                view.setSelected(true);
                Selectedposition=parent.getItemAtPosition(position).toString();
            }
        });

        //If the delete button is clicked
        deletebtn = (Button) view.findViewById(R.id.DeleteBtn);
        deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = DB.getAllNotes();
                //If no notes are stored then inform user
                if(res.getCount() == 0)
                {
                    Toast.makeText(getContext(), "No Notes available", Toast.LENGTH_SHORT).show();
                }


                listView = (ListView) view.findViewById(R.id.listView);

                arrayList = new ArrayList<>();
                adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, arrayList);

                listView.setAdapter(adapter);

                //Get the selected entry and delete it from the database
                while(res.moveToNext())
                {
                    Entry = "Date: "+ res.getString(1)+" - "+res.getString(2);

                    if(Entry.equals(Selectedposition)){
                        String TheSelectedDate = res.getString(1);
                        Boolean deleteQuery = DB.deleteNote(TheSelectedDate);
                        replaceFragment(new page3_fragment());
                        MainActivity.setArrayList(arrayList);
                    }
                }

            }
        });

        //initialise calender variable
        Calendar calender = Calendar.getInstance();
        final int year = calender.get(Calendar.YEAR);
        final int month = calender.get(Calendar.MONTH);
        final int day = calender.get(Calendar.DAY_OF_MONTH);

        //If filter button is clicked
        filterbtn = (Button) view.findViewById(R.id.filterBtn);
        filterbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Open date picker dialog to select the date
                Cursor res = DB.getAllNotes();

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month = month+1;
                        date = day + "/" + month + "/" + year;

                        arrayList2 = new ArrayList<>();
                        adapter2 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, arrayList2);
                        listView.setAdapter(adapter2);

                        //Check if the selected date matches with the date of any of the note in the database
                        while(res.moveToNext())
                        {
                            Entry = res.getString(1);

                            if(Entry.equals(date)){
                                String TheSelectedDate = res.getString(1);
                                Cursor FilterQuery = DB.getFilteredNote(TheSelectedDate);

                                if(FilterQuery.moveToNext())
                                {
                                    String Add = FilterQuery.getString(1)+FilterQuery.getString(2);
                                    arrayList2.add(Add);
                                }

                            }
                        }


                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });



        return view;
        //return inflater.inflate(R.layout.fragment_page2_fragment, container, false);
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