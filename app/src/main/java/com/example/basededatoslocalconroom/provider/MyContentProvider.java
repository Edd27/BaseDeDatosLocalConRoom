package com.example.basededatoslocalconroom.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.basededatoslocalconroom.data.AppDatabase;
import com.example.basededatoslocalconroom.data.User;
import com.example.basededatoslocalconroom.data.UserDao;

import java.util.List;

public class MyContentProvider extends ContentProvider {
    /*

    URI Structure:
    content://com.example.basededatoslocalconroom.provider/user -> INSERT y QUERY
    content://com.example.basededatoslocalconroom.provider/user/# -> UPDATE, QUERY y DELETE
    content://com.example.basededatoslocalconroom.provider/user/* -> UPDATE, QUERY y DELETE

     */

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI("com.example.basededatoslocalconroom.provider", "user", 1);
        uriMatcher.addURI("com.example.basededatoslocalconroom.provider", "user/#", 2);
        uriMatcher.addURI("com.example.basededatoslocalconroom.provider", "user/*", 3);
    }

    private Cursor toCursor(List<User> users) {
        MatrixCursor matrixCursor = new MatrixCursor(new String[]{
                "uid",
                "first_name",
                "lastname"
        });

        for (User user : users) {
            matrixCursor.newRow().add("uid", user.uid).add("first_name", user.firstName).add("last_name", user.lastName);
        }

        return matrixCursor;
    }

    @Override
    public boolean onCreate() {
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        AppDatabase db = AppDatabase.getDatabaseInstance(getContext());
        Cursor cursor = null;
        UserDao dao = db.userDao();

        switch (uriMatcher.match(uri)) {
            case 1:
                cursor = toCursor(dao.getAll());
                break;
        }

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        String mimeType = "";

        switch (uriMatcher.match(uri)) {
            case 1:
                mimeType = "vnd.android.crusor.dir/vnd.com.example.basededatoslocalconroom.provider.user";
                break;
            case 2:
                mimeType = "vnd.android.crusor.item/vnd.com.example.basededatoslocalconroom.provider.user";
                break;
            case 3:
                mimeType = "vnd.android.crusor.dir/vnd.com.example.basededatoslocalconroom.provider.user";
                break;
        }

        return mimeType;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
