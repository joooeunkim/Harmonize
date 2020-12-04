package com.project.team8;



import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.icu.util.ULocale;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends Activity {

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    public String id;
    public String name;
    public String password;
    public String sex;
    public String phone;
    public String email;
    public String money;


    EditText eid,epw;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button gojoin = (Button)findViewById(R.id.joinbtn);
        gojoin.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(),Join.class);
                startActivity(intent);
            }
        });


    }


    public void login(View view) {
        eid = findViewById(R.id.idfield);
        epw = findViewById(R.id.pwfield);
        id = eid.getText().toString();
        password = epw.getText().toString();


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               for(DataSnapshot postSnapshot: dataSnapshot.getChildren()){
                   String key = postSnapshot.getKey();
                   HashMap<String, HashMap<String,Object>> userInfo = (HashMap<String, HashMap<String, Object>>)postSnapshot.getValue();
                   String [] getData = {userInfo.get("id").get("id").toString(),userInfo.get("id").get("password").toString()};
                   System.out.println(getData[0]);
               }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        Query selectid = FirebaseDatabase.getInstance().getReference().child("USER");
        


        if (firebaseDatabase.getInstance().getReference().child("USER").child(id) != null) {
            if (firebaseDatabase.getInstance().getReference().child("USER").child(id).child("password").child(password) != null) {
                Toast.makeText(getApplicationContext(), "로그인 성공.", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(), CategoryActivity.class);
                i.putExtra("cus_id", id);
                startActivity(i);
            }
            else {
                Toast.makeText(getApplicationContext(), "아이디 또는 비밀번호가 틀립니다.", Toast.LENGTH_SHORT).show();
                eid.setText("");
                epw.setText("");
            }
        } else {
            Toast.makeText(getApplicationContext(), "아이디 또는 비밀번호가 틀립니다.", Toast.LENGTH_SHORT).show();
            eid.setText("");
            epw.setText("");
        }

    }

      /*  Cursor cursor;
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
                    Intent i = new Intent(getApplicationContext(), CategoryActivity.class);
                    i.putExtra("cus_id",eid.getText().toString());
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

*/

}

