package com.example.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ActivityChooserView;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static android.database.sqlite.SQLiteDatabase.CREATE_IF_NECESSARY;
import static android.database.sqlite.SQLiteDatabase.findEditTable;

public class MainActivity extends Activity {
    public class myDBHelper extends SQLiteOpenHelper {
        public myDBHelper(Context context)
        {
            super(context, "group",null,1);
        }

        public void onCreate(SQLiteDatabase db){
            //테이블 생성
        }
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
            //테이블 드롭
        }
    }

    myDBHelper myHelper;
    SQLiteDatabase sqlDB;
    EditText eid,epw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button gojoin = (Button)findViewById(R.id.joinbtn);
        myHelper = new myDBHelper(this);
        gojoin.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(),Join.class);
                startActivity(intent);


            }
        });
    }


    public void login(View view) {
        sqlDB = openOrCreateDatabase("my.db",MODE_PRIVATE,null);
        eid = findViewById(R.id.idfield);
        epw = findViewById(R.id.pwfield);
        Cursor cursor;
        String sql = "SELECT * FROM USER WHERE ID = '"+eid.getText().toString()+"';";
        cursor = sqlDB.rawQuery(sql,null);
        if(cursor.moveToNext()){
            sql = "SELECT password FROM USER WHERE ID = '"+eid.getText().toString()+"';";
            cursor = sqlDB.rawQuery(sql,null);
            if(cursor.moveToNext())
            {
                if(cursor.getString(0).equals(epw.getText().toString()))
                {
                    Toast.makeText(getApplicationContext(),"로그인 성공.",Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(),Login.class);
                    i.putExtra("ID",eid.getText().toString());
                    startActivity(i);
                }
                else {
                    Toast.makeText(getApplicationContext(),"아이디 또는 비밀번호가 틀립니다.",Toast.LENGTH_SHORT).show();
                    eid.setText("");
                    epw.setText("");
                }
            }

        }else{
            Toast.makeText(getApplicationContext(),"아이디 또는 비밀번호가 틀립니다.",Toast.LENGTH_SHORT).show();
            eid.setText("");
            epw.setText("");
        }
        cursor.close();
    }


}
