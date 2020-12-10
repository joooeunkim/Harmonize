package com.project.team8;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.icu.util.ULocale;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends Activity {

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference ;
    private ChildEventListener mChild;
    static ArrayList<String> idlist = new ArrayList<>();
    static ArrayList<String> pwlist = new ArrayList<>();
    public String id;
    public String name;
    public String password;
    EditText eid,epw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initDatabase();

        databaseReference = firebaseDatabase.getReference("USER");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    String idstr = snapshot.getKey();
                    idlist.add(idstr);
                    String pwstr = snapshot.child("password").getValue(String.class);
                    pwlist.add(pwstr);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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
        boolean key = true;

        for(int i = 0;i<idlist.size();i++)
        {
            if(idlist.get(i).equals(id))
            {
                if(pwlist.get(i).equals(password)) {
                    Toast.makeText(getApplicationContext(), "로그인 성공.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), CategoryActivity.class);
                    intent.putExtra("cus_id", id);
                    startActivity(intent);
                    key = false;
                }
            }
        }
        if(key)
        {
            Toast.makeText(getApplicationContext(), "아이디 또는 비밀번호가 틀립니다.", Toast.LENGTH_SHORT).show();
            eid.setText("");
            epw.setText("");
        }

    }
    private void initDatabase() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("USER");


        mChild = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        databaseReference.addChildEventListener(mChild);

    }

    protected void onDestroy() {
        super.onDestroy();
        databaseReference.removeEventListener(mChild);
    }

}

