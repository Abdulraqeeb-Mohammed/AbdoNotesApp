package com.abdulraqeeb.abdonotesapp;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Abdulraqeeb on 28/06/2019.
 */

@Entity (tableName = "note_table")  // by Default the table name will be note but we can change it , to meet sqlite naming convention
public class Note {


    // additional annotasions :
    // @Ignore : to not create the column in the database
    // @columnInfo(name= "priority_column") : in case you don't want the column name to match the property name

    //Entity documentation: https://developer.android.com/training/data-storage/room/defining-data

    @PrimaryKey (autoGenerate = true)
    private  int id;

    private  String title ;
    private  String description;
    private  int priority;

    // Room Needs a constructor , to recreate the objects from databse
    // we dont include ID because it is Auto generated , instead we make setter method for it
    public Note(String title, String description, int priority) {
        this.title = title;
        this.description = description;
        this.priority = priority;
    }

    // we make this setter for the ID , so Room can recreate it in the databse , cus its not included in the constructor
    public void setId(int id) {
        this.id = id;
    }
    // we have to create getter methods for the above field , so ROOM can make this values in Sqlite DB
 // for Encapsulation its better to use getter methods , otherwise We can make the above variables Public.

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getPriority() {
        return priority;
    }


}
