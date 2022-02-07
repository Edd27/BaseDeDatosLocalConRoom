package com.example.basededatoslocalconroom.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
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

import java.util.ArrayList;
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
                "last_name"
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
            case 2:
                int userId = (int)ContentUris.parseId(uri);
                List<User> user = dao.loadAllByIds(new int[]{userId});
                cursor = toCursor(user);
                break;
            case 3:
                String name = uri.getLastPathSegment();
                cursor = toCursor(dao.getUsersByName(name));
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
        AppDatabase db = AppDatabase.getDatabaseInstance(getContext());
        Cursor cursor = null;
        UserDao dao = db.userDao();
        User user = new User();

        switch (uriMatcher.match(uri)) {
            case 1:
                user.firstName = contentValues.getAsString(ContractUser.COLUMN_FIRSTNAME);
                user.lastName = contentValues.getAsString(ContractUser.COLUMN_LASTNAME);
                long newId = dao.insert(user);
                return Uri.withAppendedPath(uri, String.valueOf(newId));
        }

        return Uri.withAppendedPath(uri, String.valueOf(-1));
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        int id = Integer.parseInt(uri.getLastPathSegment());
        AppDatabase db = AppDatabase.getDatabaseInstance(getContext());
        Cursor cursor = null;
        UserDao dao = db.userDao();
        List<User> usersToDelete = dao.loadAllByIds(new int[]{id});

        return dao.deleteUser(usersToDelete.get(0));
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        int id = Integer.parseInt(uri.getLastPathSegment());
        AppDatabase db = AppDatabase.getDatabaseInstance(getContext());
        Cursor cursor = null;
        UserDao dao = db.userDao();
        List<User> usersToUpdate = dao.loadAllByIds(new int[]{id});
        usersToUpdate.get(0).firstName = contentValues.getAsString(ContractUser.COLUMN_FIRSTNAME);
        usersToUpdate.get(0).lastName = contentValues.getAsString(ContractUser.COLUMN_LASTNAME);

        return dao.updateUser(usersToUpdate.get(0));
    }
}
