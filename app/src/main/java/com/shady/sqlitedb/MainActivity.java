package com.shady.sqlitedb;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.util.List;

import data.DatabaseHandler;
import model.Contact;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseHandler db = new DatabaseHandler(this);
        Log.d("DBCount", String.valueOf(db.getContactCounts()));

        //Insert Contacts
//        Log.d("insert", "inserting...: ");
//        db.addContact(new Contact("shady", "123213123"));
//        db.addContact(new Contact("alpha", "0292384038"));
//        db.addContact(new Contact("flooki", "209238403284"));
//        db.addContact(new Contact("naughty", "209238403284"));

        //Getting single contact
//        Contact singleContct = db.getContact(3);

        //Updating contact
//        singleContct.setName("shadymaverik");
//        singleContct.setPhoneNumber("9823928");

//        int updatingContact = db.updateContact(singleContct);
//        Log.d("singleContact", "UpdatedContact "+String.valueOf(updatingContact)+" Name: "+singleContct.getName()+
//                " Phone: "+singleContct.getPhoneNumber());

        //deleting contact
//        db.deleteContact(singleContct);

        //Reading data back
        Log.d("reading", "Reading.... ");
        List<Contact> contactList = db.getAllContact();
        for (Contact c : contactList) {
            String data = "userID "+c.getId() +", user Name "+c.getName()+", user Phone Number "+c.getPhoneNumber();
            Log.d("userdata ", data);

        }
    }
}
