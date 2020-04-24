package data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import Util.Utils;
import model.Contact;

public class DatabaseHandler extends SQLiteOpenHelper {

    public DatabaseHandler(@Nullable Context context) {
        super(context, Utils.DATABASE_NAME, null, Utils.DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        //creating table
        String CREATE_CONTACT_TABLE = "CREATE TABLE " + Utils.TABLE_NAME + "("
                + Utils.KEY_ID + " INTEGER PRIMARY KEY," + Utils.KEY_NAME + " TEXT,"
                + Utils.KEY_PHONE_NUMBER + " TEXT"+ ")";

        db.execSQL(CREATE_CONTACT_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS "+Utils.TABLE_NAME);
        onCreate(db);

    }

    /**
     *
     * CRUD operations below
     */

    //Add a contact
    public void addContact(Contact contact){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Utils.KEY_NAME, contact.getName());
        values.put(Utils.KEY_PHONE_NUMBER, contact.getPhoneNumber());

        //Insert values
        db.insert(Utils.TABLE_NAME, null, values);
        db.close();
    }

    //Get a contact
    public Contact getContact(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Utils.TABLE_NAME, new String[]{Utils.KEY_ID,
                Utils.KEY_NAME, Utils.KEY_PHONE_NUMBER}, Utils.KEY_ID+"=?",
                new String[]{String.valueOf(id)},null, null, null, null);

        if(cursor != null){
            cursor.moveToFirst();
        }

        Contact contact = new Contact(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));
        return contact;
    }

    //Get all contacts

    public List<Contact> getAllContact(){
        SQLiteDatabase db = this.getReadableDatabase();

        List<Contact> contactList = new ArrayList<>();
        String selectAll = "SELECT * FROM "+ Utils.TABLE_NAME;
        Cursor cursor = db.rawQuery(selectAll, null);

        //Loop through our contacts
        if(cursor.moveToFirst()){
            do{
                Contact contact = new Contact();
                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setName(cursor.getString(1));
                contact.setPhoneNumber(cursor.getString(2));
                contactList.add(contact);

            }while (cursor.moveToNext());
        }
        return contactList;
    }

    //Udate data
    public int updateContact(Contact contact){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Utils.KEY_NAME, contact.getName());
        values.put(Utils.KEY_PHONE_NUMBER, contact.getPhoneNumber());

        //update row
        return db.update(Utils.TABLE_NAME, values, Utils.KEY_ID+"=?",
                new String[]{String.valueOf(contact.getId())});
    }

    //Deleting contact
    public void deleteContact(Contact contact){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Utils.TABLE_NAME, Utils.KEY_ID+"=?",
                new String[]{String.valueOf(contact.getId())});
        db.close();
    }

    //Count Items in database
    public int getContactCounts(){
        SQLiteDatabase db = this.getReadableDatabase();
        String countQuery = "SELECT * FROM "+Utils.TABLE_NAME;
        Cursor cursor = db.rawQuery(countQuery, null);
        //cursor.close();
        return cursor.getCount();
    }
}
