package com.abdulraqeeb.abdonotesapp;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

/**
 * Created by Abdulraqeeb on 28/06/2019.
 */

public class NoteRepository {

    // member variables
    private NoteDao noteDao;
    private LiveData<List<Note>> allNotes;


    // constructor
    public  NoteRepository(Application application){
        // since Application is a subclass of Context, we can use it as a context to create our database instance

        // Creating a Database instance
        NoteDatabase database = NoteDatabase.getInstance(application);

        noteDao  = database.noteDao();
        allNotes = noteDao.getAllNotes(); // the method in our DAO interface
    }

    // # the next 4 method are the API that the Repository exposes to the outside
    // so viewModel later only has to call them
    public void insert (Note note){
        //instance of InsertAsyncTask
        new InsertNoteAsyncTask(noteDao).execute(note);
    }

    public void update (Note note){
        new  UpdateNoteAsyncTask(noteDao).execute(note);
    }

    public void delete (Note note){
        new DeleteNoteAsyncTask(noteDao).execute(note);
    }

    public void deleteAllNotes (){
        new DeleteAllNotesAsyncTask(noteDao).execute();
    }

    // Room will execute database operation that returns the liveData on the background thread, so we don't have to take care of this...
    //but for our other DB operations , we have to execute the code on the background thread ourselves.
    public  LiveData<List<Note>> getAllNotes (){
        return  allNotes;
    }

    // # ROOm does'nt allow DB operation in the main thread , since this could freeze our app...
    // so we will use AsyncTask
    //Inner Class
    private  static class InsertNoteAsyncTask extends AsyncTask<Note,Void,Void>{
    /*it has to be static so it does not reference to the Repository itself, otherwise it could cause a memory leak*/

        private NoteDao noteDao;  // to make DB operations

        // since this class is static , we cant access the noteDao of our repository Directly , so we have to pass it over a constructor
        // #Constructor.
        private  InsertNoteAsyncTask (NoteDao noteDao ){
            this.noteDao = noteDao;
        }
        @Override // mandatory to override here
        protected Void doInBackground(Note... notes) {
            // we put index to notes[] becaus it is not a single not , and we want only the first index
            noteDao.insert(notes[0]);
            return null;
        }
    }

    /* Write AsyncTask Classes for each single operation*/

    private  static class UpdateNoteAsyncTask extends AsyncTask<Note,Void,Void>{

        private NoteDao noteDao;

        private  UpdateNoteAsyncTask (NoteDao noteDao ){
            this.noteDao = noteDao;
        }
        @Override
        protected Void doInBackground(Note... notes) {

            noteDao.update(notes[0]);
            return null;
        }
    }

    private  static class DeleteNoteAsyncTask extends AsyncTask<Note,Void,Void>{

        private NoteDao noteDao;

        private  DeleteNoteAsyncTask (NoteDao noteDao ){
            this.noteDao = noteDao;
        }
        @Override
        protected Void doInBackground(Note... notes) {

            noteDao.delete(notes[0]);
            return null;
        }
    }

    private  static class DeleteAllNotesAsyncTask extends AsyncTask<Void,Void,Void>{

        private NoteDao noteDao;

        private  DeleteAllNotesAsyncTask (NoteDao noteDao ){
            this.noteDao = noteDao;
        }
        @Override
        protected Void doInBackground(Void... voids ) {

            noteDao.deleteAllNotes();
            return null;
        }
    }

}
