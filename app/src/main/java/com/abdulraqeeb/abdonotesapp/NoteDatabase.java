package com.abdulraqeeb.abdonotesapp;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

/**
 * Created by Abdulraqeeb on 28/06/2019.
 */

@Database(entities = {Note.class}, version = 1)
public abstract class NoteDatabase extends RoomDatabase {

    // we create the next variable because we need to turn this class into Singlton
    //Turning a class into a singleton is done by creating a variable that  its type is like calss name , and had to be static .

    //Singleton means that the class cannot be instantiated more than once , instead we use the same instance everywhere in the app ,
    // which we can access over the static variable

    private static NoteDatabase instance;

    // we use this method to access our NoteDao , and we don't have to provide body because Room take care of it.
    public abstract NoteDao noteDao();

    // Now we Create our Database

    // again we create a singlton , wich we will use to return the same object every time (instance)

    // synchronized means that only one thread at a time can access this method , ...
    // this way you dont accidentally create two instances of this database , when two different threads ...
    // tries to access this method at the same time  .
    public static synchronized NoteDatabase getInstance(Context context) {
        /* we do this check , because we only need to instantiate this DB if we don't already have an instance*/
        if (instance == null) {
            /*we cant say ( instance = new NoteDatabase ) because we are in an abstract class , instead we use a builder*/
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    NoteDatabase.class,"note_database")
                    .fallbackToDestructiveMigration() //To avoid app Crash in case we increment the version number of DB , which will throw(illegal state exception). (in that case this option will delete the database and create it from scratch)
                    .addCallback(roomCallback)
                    .build(); // returns the instance of the database
        }
        // return the database instance if it is already exists
        return instance;
    }


    private static  RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        // this is called only when the database is created
        // and we put it to add a test data to the DB when it is created
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static  class  PopulateDbAsyncTask extends AsyncTask<Void,Void,Void>{
        private  NoteDao noteDao;

        private  PopulateDbAsyncTask(NoteDatabase db){
            noteDao = db.noteDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {

            noteDao.insert(new Note("Title1","Description1",1));
            noteDao.insert(new Note("Title2","Description2",2));
            noteDao.insert(new Note("Title3","Description3",3));
            return null;
        }
    }
}
