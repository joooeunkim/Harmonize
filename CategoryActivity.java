package com.project.team8;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity {


    private String cus_Id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        Intent r = getIntent();
        cus_Id = r.getExtras().getString("cus_id");

        TextView t1 = (TextView) findViewById(R.id.drinks);
        TextView t2 = (TextView) findViewById(R.id.fast);
        TextView t3 = (TextView) findViewById(R.id.bread);
        TextView t4 = (TextView) findViewById(R.id.icecream);
        TextView t5 = (TextView) findViewById(R.id.movie);
        TextView t6 = (TextView) findViewById(R.id.out);
        TextView t7 = (TextView) findViewById(R.id.coupon);
        TextView t8 = (TextView) findViewById(R.id.beauty);
        TextView t9 = (TextView) findViewById(R.id.store);


    }



    public void sell(View view) {
    }

    public void home(View view) {
        Intent i = new Intent(getApplicationContext(), WalletActivity.class);
        i.putExtra("cus_id",cus_Id);
        startActivity(i);
        finish();
    }

    public void mypage(View view) {
        Intent i = new Intent(getApplicationContext(),MypageActivity.class);
        i.putExtra("cus_id",cus_Id);
        startActivity(i);
        finish();
    }


    public void drinks(View view) {
        Intent intent = new Intent(getApplicationContext(),conlistActivity.class);
        intent.putExtra("category","drinks");
        intent.putExtra("cus_id",cus_Id);
        startActivity(intent);
    }
    public void fast(View view) {
        Intent intent = new Intent(getApplicationContext(),conlistActivity.class);
        intent.putExtra("category","fastfood");
        intent.putExtra("cus_id",cus_Id);
        startActivity(intent);
    }
    public void bread(View view) {
        Intent intent = new Intent(getApplicationContext(),conlistActivity.class);
        intent.putExtra("category","bread");
        intent.putExtra("cus_id",cus_Id);
        startActivity(intent);
    }
    public void icecream(View view) {
        Intent intent = new Intent(getApplicationContext(),conlistActivity.class);
        intent.putExtra("category","icecream");
        intent.putExtra("cus_id",cus_Id);
        startActivity(intent);
    }
    public void movie(View view) {
        Intent intent = new Intent(getApplicationContext(),conlistActivity.class);
        intent.putExtra("category","movie");
        intent.putExtra("cus_id",cus_Id);
        startActivity(intent);
    }
    public void outeat(View view) {
        Intent intent = new Intent(getApplicationContext(),conlistActivity.class);
        intent.putExtra("category","outeat");
        intent.putExtra("cus_id",cus_Id);
        startActivity(intent);
    }public void coupon(View view) {
        Intent intent = new Intent(getApplicationContext(),conlistActivity.class);
        intent.putExtra("category","coupon");
        intent.putExtra("cus_id",cus_Id);
        startActivity(intent);
    }
    public void beauty(View view) {
        Intent intent = new Intent(getApplicationContext(),conlistActivity.class);
        intent.putExtra("category","beauty");
        intent.putExtra("cus_id",cus_Id);
        startActivity(intent);
    }
    public void store(View view) {
        Intent intent = new Intent(getApplicationContext(),conlistActivity.class);
        intent.putExtra("category","store");
        intent.putExtra("cus_id",cus_Id);
        startActivity(intent);
    }

}
