package com.example.basededatoslocalconroom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.app.Application;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.example.basededatoslocalconroom.data.AppDatabase;
import com.example.basededatoslocalconroom.data.User;
import com.example.basededatoslocalconroom.data.UserDao;

public class MainActivity extends AppCompatActivity {

    AppDatabase db;
    Button btnInsert, btnRead;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnInsert = findViewById(R.id.btnInsert);
        btnRead = findViewById(R.id.btnRead);

        btnInsert.setOnClickListener(view -> {
            AppDatabase db = AppDatabase.getDatabaseInstance(getApplication());
            UserDao dao = db.userDao();

            AppDatabase.databaseWriteExecutor.execute(() -> {
                User user = new User();
                user.firstName = "Juan";
                user.lastName = "Lopez";
                dao.insertAll(user);
            });

            Toast.makeText(this, "Insertado", Toast.LENGTH_LONG).show();
        });

        btnRead.setOnClickListener(view -> {
            AppDatabase db = AppDatabase.getDatabaseInstance(getApplication());
            UserDao dao = db.userDao();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                AppDatabase.databaseWriteExecutor.execute(() -> {
                    dao.getAll().stream().forEach(user -> {
                        Log.i("Consulta de usuarios", user.uid + " " + user.firstName + " " + user.lastName);
                    });
                });
            } else {
                AppDatabase.databaseWriteExecutor.execute(() -> {
                    for (User user : dao.getAll()) {
                        Log.d("Consulta de usuarios", user.firstName + " " + user.lastName);
                    }
                });
            }

        });

    }

}