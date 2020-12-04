package com.project.team8;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;



import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class Join extends Activity {




    SQLiteDatabase sqlDB;
    EditText eid,ename,epw,epw2,ephone,eemail;
    RadioButton rm, rf;
    String sex = "";
    boolean key=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        sqlDB = openOrCreateDatabase("gift.db",MODE_PRIVATE,null);



    }

    public void duple_check(View view) {


        eid = findViewById(R.id.id);
        Cursor cursor;
        String sql = "SELECT * FROM USER WHERE ID = '"+eid.getText().toString()+"';";
        cursor = sqlDB.rawQuery(sql,null);
        if(cursor.moveToNext()){
            Toast.makeText(getApplicationContext(),"이미 있는 ID 입니다.",Toast.LENGTH_SHORT).show();
            eid.setText("");
        }else{
            Toast.makeText(getApplicationContext(),"사용 가능한 ID 입니다.",Toast.LENGTH_SHORT).show();
            key = true;
        }
        cursor.close();
    }

    public void storeinfo(View view) {
        eid = findViewById(R.id.id);
        ename = findViewById(R.id.name);
        epw = findViewById(R.id.pw);
        epw2 = findViewById(R.id.pw2);
        rm = findViewById(R.id.m);
        rf = findViewById(R.id.f);
        ephone = findViewById(R.id.phone);
        eemail = findViewById(R.id.email);
        if(key)
        {
            if(epw.getText().toString().equals(epw2.getText().toString()))
            {
                if(rm.isSelected())
                {
                    sex = "male";
                }
                else
                {
                    sex = "female";
                }

                String sql = "INSERT INTO USER(ID,name,password,sex,phone,email,money) VALUES ('"+eid.getText().toString()+"','"+ename.getText().toString()+"','"+epw.getText().toString()+"','"+sex+"','"+ephone.getText().toString()+"','"+eemail.getText().toString()+"',0);";
                sqlDB = openOrCreateDatabase("gift.db",MODE_PRIVATE,null);
                sqlDB.execSQL(sql);
                sqlDB.close();
                Toast.makeText(getApplicationContext(),"회원가입이 완료되었습니다.",Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(getApplicationContext(),"비밀번호가 다릅니다.",Toast.LENGTH_SHORT).show();
                epw.setText("");
                epw2.setText("");
            }
        }else{
            Toast.makeText(getApplicationContext(),"ID 중복확인을 해주세요.",Toast.LENGTH_SHORT).show();
            eid.setText("");
        }
    }

}

