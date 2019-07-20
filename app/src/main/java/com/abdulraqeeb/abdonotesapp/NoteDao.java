package com.abdulraqeeb.abdonotesapp;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by Abdulraqeeb on 28/06/2019.
 */



@Dao
public interface NoteDao {
    // In Dao Classes , We don't provide method body (interface class), We just create a method and annotate it, ...
    // and then room makes all the necessary  code files.
    // Generally it is best practice to create one Dao for each Entity.

    // in this interface we create methods for All the operations we want to do on out Note_table
    @Insert
    void insert(Note note);

    @Update
    void update(Note note);

    //DAO documentation: https://developer.android.com/training/data-storage/room/accessing-data

    @Delete
    void delete(Note note);

    // Room does not have convenient annotations for all the DB operations, instead we can use @Query annotations
    @Query("DELETE FROM note_table")
    void  deleteAllNotes();

    @Query("SELECT * FROM NOTE_TABLE ORDER BY PRIORITY desc")
    LiveData<List<Note>> getAllNotes();

    //with LiveData we can observe the List<Note> Object , so as soon as there is any changes ...
    //in our note_table the List<Note> will automatically be updated and our activity will be notified
}
