package com.abdulraqeeb.abdonotesapp;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

/**
 * Created by Abdulraqeeb on 28/06/2019.
 */

public class NoteViewModel extends AndroidViewModel {
    //member variables
    private  NoteRepository repository;
    private LiveData<List<Note>> allNotes;

    // Constructor
    public NoteViewModel(@NonNull Application application) {
        super(application);

        // instantiating the member variables
        repository = new NoteRepository(application);
        allNotes = repository.getAllNotes();
    }


    // # our activity later only have reference to our ViewModel using the following methods, not to the repository

    public  void  insert (Note note){
        repository.insert(note);
    }

    public  void  update (Note note){
        repository.update(note);
    }


    public  void  delete (Note note){ repository.delete(note); }


    public  void  deleteAllNotes (){
        repository.deleteAllNotes();
    }

    public  LiveData<List<Note>> getAllNotes(){
        return  allNotes;
    }
}
