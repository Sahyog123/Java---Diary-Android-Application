package com.example.diarymobileapp;

public class Notes {

    String Date, Notes;

    /**
     * Method to save the notes
     * @param date - the date for the diary entry
     * @param notes - the actual note for the date
     */
    public Notes(String date, String notes) {
        Date = date;
        Notes = notes;
    }
}
