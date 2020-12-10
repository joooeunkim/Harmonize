package com.project.team8;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Join extends Activity {

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference("USER");
    private String category;
    private ChildEventListener mChild;
    static ArrayList<String> idlist = new ArrayList<>();
    public String id;
    public String name;
    public String password;
    public String sex;
    public String phone;
    public String email;
    public String money;
    EditText eid,ename,epw,epw2,ephone,eemail;
    RadioButton rm, rf;
    boolean dkey=true;
    boolean key = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        eid = findViewById(R.id.id);
        ename = findViewById(R.id.name);
        epw = findViewById(R.id.pw);
        epw2 = findViewById(R.id.pw2);
        rm = findViewById(R.id.m);
        rf = findViewById(R.id.f);
        ephone = findViewById(R.id.phone);
        eemail = findViewById(R.id.email);
        initDatabase();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    String idstr = snapshot.getKey();
                    idlist.add(idstr);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void duple_check(View view) {

        dkey = true;
        id = eid.getText().toString();
        for(int i=0;i<idlist.size();i++)
        {
            if(idlist.get(i).toString().equals(id))
            {
                Toast.makeText(getApplicationContext(),"이미 있는 ID 입니다.",Toast.LENGTH_SHORT).show();
                eid.setText("");
                dkey = false;
            }
        }
        if(dkey)
        {
            Toast.makeText(getApplicationContext(),"사용 가능한 ID 입니다.",Toast.LENGTH_SHORT).show();
            key = true;
        }

    }

    public void storeinfo(View view) {
        name = ename.getText().toString();
        password = epw.getText().toString();
        phone = ephone.getText().toString();
        email = eemail.getText().toString();
        if(key)
        {
            if(epw.getText().toString().equals(epw2.getText().toString()))
            {
                if(rm.isSelected())
                    sex = "male";
                else
                     sex = "female";
                databaseReference.child(id).child("name").setValue(name);
                databaseReference.child(id).child("password").setValue(password);
                databaseReference.child(id).child("sex").setValue(sex);
                databaseReference.child(id).child("phone").setValue(phone);
                databaseReference.child(id).child("email").setValue(email);
                databaseReference.child(id).child("money").setValue(0);
                Toast.makeText(getApplicationContext(),"회원가입이 완료되었습니다.",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
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

