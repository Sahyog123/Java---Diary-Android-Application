package com.example.diarymobileapp;

//Import statements
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ListAdapter extends ArrayAdapter<Notes> {

    //Constructor
    public ListAdapter(Context context, ArrayList<Notes> NotesArrayList){

        super(context,R.layout.list_item,NotesArrayList);

    }

    /**
     * Method to get the view
     * @param position
     * @param convertView
     * @param parent
     * @return View
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Notes notes = getItem(position);

        if(convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
        }
        //Set the saved date in the text view
        TextView DateSave = convertView.findViewById(R.id.DateSaved);

        DateSave.setText(notes.Date);



        return super.getView(position, convertView, parent);
    }
}
