package com.harmoniz.enjoyapp;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class MainActivity extends Activity {
    public String place;

    public void loadDB(){
        SQLiteDatabase db = openOrCreateDatabase("company.db", SQLiteDatabase.CREATE_IF_NECESSARY, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS company (name STRING PRIMARY KEY);");

        Cursor c = db.rawQuery("SELECT * From companyname where name = '스타벅스';", null);
        startManagingCursor(c);

        place =c.getString(0);

        if(db!=null){
            db.close();
        }

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void onClick(View view) {
        Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
        intent.putExtra("SHOP",place);
        startActivity(intent);
    }


}
