package com.project.team8;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class NotSelling extends AppCompatActivity {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ChildEventListener mChild;
    private String con_id;
    private String cus_id;
    private String category;
    private String selling;
    private String hpricee;
    private String rprice;
    private String text;
    SQLiteDatabase sqlDB;
    private Drawable d;
    private String itemid;
    private TextView t1;
    private ImageView c;
    private ImageView v1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_not_selling);

        Intent intent = getIntent();
        con_id = intent.getExtras().getString("con_id");
        cus_id = intent.getExtras().getString("cus_id");
        text = intent.getExtras().getString("text");

        t1 = (TextView)findViewById(R.id.textfield);
        c = (ImageView)findViewById(R.id.cat);
        t1.setText(text);
        v1 = new ImageView(getBaseContext());
        v1.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));

        initDatabase();

        t1 = (TextView)findViewById(R.id.textfield);
        c = (ImageView)findViewById(R.id.cat);
        t1.setText(text);
        v1 = new ImageView(getBaseContext());
        v1.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));

        databaseReference = firebaseDatabase.getReference("GIFTCON");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    if(snapshot.getKey().toString().equals(con_id))
                    {
                        itemid = snapshot.child("itemid").getValue().toString();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        databaseReference = firebaseDatabase.getReference("ITEM");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    if(snapshot.getKey().toString().equals(itemid))
                        category = snapshot.child("category").getValue(String.class);
                }

                switch (category)
                {
                    case "drinks":
                        c.setImageResource(R.drawable.drinks);
                        break;

                    case "fastfood":
                        c.setImageResource(R.drawable.fastfood);
                        break;

                    case "bread":
                        c.setImageResource(R.drawable.bread);
                        break;

                    case "icecream":
                        c.setImageResource(R.drawable.icecream);
                        break;

                    case "movie":
                        c.setImageResource(R.drawable.movie);
                        break;

                    case "outeat":
                        c.setImageResource(R.drawable.outeat);
                        break;

                    case "coupon":
                        c.setImageResource(R.drawable.coupon);
                        break;

                    case "beauty":
                        c.setImageResource(R.drawable.beauty);
                        break;

                    case "store":
                        c.setImageResource(R.drawable.store);
                        break;

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }

    public void addsell(View view) {
        Intent intent = new Intent(getApplicationContext(),AddsellActivity.class);
        intent.putExtra("cus_id",cus_id);
        intent.putExtra("con_id",con_id);
        intent.putExtra("text",text);
        startActivity(intent);
    }

    private void initDatabase() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("GIFTCON");


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
