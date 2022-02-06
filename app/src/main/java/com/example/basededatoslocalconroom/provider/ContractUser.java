package com.example.basededatoslocalconroom.provider;

import android.net.Uri;

public class ContractUser {
    public static final Uri CONTENT_URI = Uri.parse("content://com.example.basededatoslocalconroom.provider/user");
    public static final String[] COLUMNS_NAMES = new String[]{
            "uid",
            "first_name",
            "last_name"
    };
    public static final String COLUMN_ID = "uid";
    public static final String COLUMN_FIRSTNAME = "first_name";
    public static final String COLUMN_LASTNAME = "last_name";
}
