package com.project.team8;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class ChargeActivity extends AppCompatActivity {

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference ;
    private ChildEventListener mChild;
    String cus_id;
    int Addmoney = 0;
    int money = 0;
    EditText et;

    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charge);
        Intent r = getIntent();
        cus_id = r.getExtras().getString("cus_id");
        initDatabase();
        et = (EditText)findViewById(R.id.editmoney);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    if(snapshot.getKey().toString().equals(cus_id))
                    {
                      money = snapshot.child("money").getValue(Integer.class);
                    }
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


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

    public void charging(View view){
        Addmoney = Integer.parseInt(et.getText().toString());
        money = money+Addmoney;
        Map<String,Object> taskMap = new HashMap<String, Object>();
        taskMap.put(cus_id+"/money",money);
        databaseReference.updateChildren(taskMap);
        Toast.makeText(getApplicationContext(), "충전완료 되었습니다.", Toast.LENGTH_SHORT).show();
        finish();
    }
}

