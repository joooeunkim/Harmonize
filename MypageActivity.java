package com.project.team8;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MypageActivity extends AppCompatActivity {

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference ;
    private ChildEventListener mChild;
    String cus_id;
    String name, pw, sex, phone,email;
    int money =0;
    TextView idText, nameText, pwText, sexText, phoneText, emailText, moneyText;
    TextView nameText2, pwText2, sexText2, phoneText2, emailText2, moneyText2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);
        initDatabase();
        Intent r = getIntent();
        cus_id = r.getExtras().getString("cus_id");

        idText = (TextView)findViewById(R.id.idinfoDB);
        nameText = (TextView)findViewById(R.id.nameinfo);
        nameText2 = (TextView)findViewById(R.id.nameinfoDB);
        pwText = (TextView)findViewById(R.id.pwinfo);
        pwText2 = (TextView)findViewById(R.id.pwinfoDB);
        sexText = (TextView)findViewById(R.id.sexinfo);
        sexText2 = (TextView)findViewById(R.id.sexinfoDB);
        phoneText = (TextView)findViewById(R.id.phoneinfo);
        phoneText2 = (TextView)findViewById(R.id.phoneinfoDB);
        emailText = (TextView)findViewById(R.id.emailinfo);
        emailText2 = (TextView)findViewById(R.id.emailinfoDB);
        moneyText = (TextView)findViewById(R.id.moneyinfo);
        moneyText2 = (TextView)findViewById(R.id.moneyinfoDB);

        idText.setText(cus_id);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                   if(snapshot.getKey().toString().equals(cus_id))
                   {
                       name=snapshot.child("name").getValue(String.class);
                       pw=snapshot.child("password").getValue(String.class);
                       sex=snapshot.child("sex").getValue(String.class);
                       phone=snapshot.child("phone").getValue(String.class);
                       email=snapshot.child("email").getValue(String.class);
                       money=snapshot.child("money").getValue(Integer.class);
                   }
                }
                nameText2.setText(name);
                pwText2.setText(pw);
                sexText2.setText(sex);
                phoneText2.setText(phone);
                emailText2.setText(email);
                moneyText2.setText(Integer.toString(money));
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

    public void charge(View view) {
        Toast.makeText(getApplicationContext(), "버튼을 눌렀습니다.", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getApplicationContext(), ChargeActivity.class);
        intent.putExtra("cus_id", cus_id);
        startActivity(intent);
    }

    public void takeoff(View view) {
        Toast.makeText(getApplicationContext(), "버튼을 눌렀습니다.", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getApplicationContext(), MinusActivity.class);
        intent.putExtra("cus_id", cus_id);
        startActivity(intent);
    }

    public void reservation(View view) {
        Toast.makeText(getApplicationContext(), "버튼을 눌렀습니다.", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getApplicationContext(), ReservationActivity.class);
        intent.putExtra("cus_id", cus_id);
        startActivity(intent);
    }

    public void sell(View view) {
        Intent intent = new Intent(getApplicationContext(),CategoryActivity.class);
        intent.putExtra("cus_id",cus_id);
        startActivity(intent);
        finish();
    }

    public void home(View view) {
        Intent i = new Intent(getApplicationContext(), WalletActivity.class);
        i.putExtra("cus_id",cus_id);
        startActivity(i);
        finish();
    }

    public void mypage(View view) {

    }
}
