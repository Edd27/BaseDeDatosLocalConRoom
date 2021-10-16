package com.example.basededatoslocalconroom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.app.Application;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.example.basededatoslocalconroom.data.AppDatabase;
import com.example.basededatoslocalconroom.data.User;
import com.example.basededatoslocalconroom.data.UserDao;

public class MainActivity extends AppCompatActivity {

    AppDatabase db;
    Button btnInsert;
    AsyncTask<Integer,Integer,Integer> tareaAsincrona = new AsyncTask<Integer, Integer, Integer>(){
        @Override
        protected Integer doInBackground(Integer... integers) {
            db = AppDatabase.getDatabaseInstance(getApplication());
            return null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        tareaAsincrona.execute();

        btnInsert = findViewById(R.id.btnInsert);

        btnInsert.setOnClickListener(view -> {
            UserDao dao = db.userDao();

            AppDatabase.databaseWriteExecutor.execute(() -> {
                User user = new User();
                user.firstName = "Juan";
                user.lastName = "Lopez";
                dao.insertAll(user);
            });

            for(User u : dao.getAll()){
                Log.d("DBUsuario", u.firstName + " " + u.lastName);
            }

        });

    }

}