package com.project.team8;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class buyActivity extends AppCompatActivity {

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference("USER");
    private DatabaseReference databaseReference2 = firebaseDatabase.getReference("GIFTCON");

    private ChildEventListener mChild;
    static ArrayList<String> useridlist = new ArrayList<>();
    static ArrayList<Integer> usermoneylist = new ArrayList<>();

    static ArrayList<String> idlist = new ArrayList<>();
    static ArrayList<String> storelist = new ArrayList<>();
    static ArrayList<String> menulist = new ArrayList<>();
    static ArrayList<String> itemprimarylist = new ArrayList<>();
    static ArrayList<String> expire_datelist = new ArrayList<>();
    static ArrayList<Integer> rpricelist = new ArrayList<>();
    static ArrayList<Integer> hpricelist = new ArrayList<>();
    static ArrayList<String> cus_idlist = new ArrayList<>();
    static ArrayList<String> itemidlist = new ArrayList<>();
    static ArrayList<String> sellinglist = new ArrayList<>();
    static ArrayList<String> catelist = new ArrayList<>();
    private String con_id;
    private String cus_id;
    private String text;

    int giftnum = 0;
    int itemnum = 0;
    int sellernum = 0;
    int buyernum=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);
        Intent intent = getIntent();
        con_id = intent.getExtras().getString("con_id");
        cus_id = intent.getExtras().getString("cus_id");
        idlist = intent.getExtras().getStringArrayList("idlist");
        storelist = intent.getExtras().getStringArrayList("storelist");
        cus_idlist = intent.getExtras().getStringArrayList("cus_idlist");
        menulist = intent.getExtras().getStringArrayList("menulist");
        itemprimarylist = intent.getExtras().getStringArrayList("itemprimarylist");
        expire_datelist = intent.getExtras().getStringArrayList("expire_datelist");
        rpricelist = intent.getExtras().getIntegerArrayList("rpricelist");
        hpricelist = intent.getExtras().getIntegerArrayList("hpricelist");
        itemidlist = intent.getExtras().getStringArrayList("itemidlist");
        sellinglist = intent.getExtras().getStringArrayList("sellinglist");
        catelist = intent.getExtras().getStringArrayList("catelist");
        initDatabase();
        text = intent.getExtras().getString("text");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    String idstr = snapshot.getKey();
                    useridlist.add(idstr);
                    int money = snapshot.child("money").getValue(Integer.class);
                    usermoneylist.add(money);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        TextView t = (TextView)findViewById(R.id.id);
        TextView infot = (TextView)findViewById(R.id.info);

        t.setText("<<상세보기>>");
        infot.setText(text);


    }


    public void buynow(View view) {
        for (int i = 0; i < idlist.size(); i++) {
            if (idlist.get(i).toString().equals(con_id)) {
                giftnum = i;
            }
        }

        for (int i = 0; i < itemprimarylist.size(); i++) {
            if (itemprimarylist.get(i).equals(itemidlist.get(giftnum))) {
                itemnum = i;
            }
        }
        for (int i = 0; i < useridlist.size(); i++) {
            if (useridlist.get(i).toString().equals(cus_id)) {
                buyernum = i;
            }
        }
        for (int i = 0; i < useridlist.size(); i++) {
            if (cus_idlist.get(giftnum).equals(useridlist.get(i))) {
                sellernum = i;
            }
        }
        int sellerm = usermoneylist.get(sellernum);
        int bal = usermoneylist.get(buyernum);
        int expense = hpricelist.get(giftnum);
        if (bal < expense) {
            Toast.makeText(getApplicationContext(),
                    "잔액이 부족합니다.\n 충전 후 이용해주세요.",
                    Toast.LENGTH_SHORT).show();
        } else {
            bal = bal - expense;
            sellerm = sellerm + expense;
            //구매자돈 출금, 판매자 돈 입금, 소유권 이전, 판매중 내리기
            Map<String,Object> taskMap = new HashMap<String, Object>();
            taskMap.put(cus_id+"/money",bal);
            //구매자 돈 출금
            taskMap.put(useridlist.get(sellernum).toString()+"/money",sellerm);
            //판매자 돈 입금
            databaseReference.updateChildren(taskMap);

            Map<String,Object> taskMap2 = new HashMap<String, Object>();
            taskMap2.put(con_id+"/cus_id",cus_id);
            //소유권 이전
            taskMap2.put(con_id+"/selling","false");
            //판매중 내리기

            databaseReference2.updateChildren(taskMap2);
            Toast.makeText(getApplicationContext(), "구매완료 되었습니다."
                    , Toast.LENGTH_SHORT).show();
            finish();
            Intent intent = new Intent(getApplicationContext(),CategoryActivity.class);
            intent.putExtra("cus_id",cus_id);
            startActivity(intent);
        }


    }
    private void initDatabase() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("USER");
        databaseReference2 = firebaseDatabase.getReference("GIFTCON");



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
        databaseReference2.addChildEventListener(mChild);

    }

    protected void onDestroy() {
        super.onDestroy();
        databaseReference.removeEventListener(mChild);
        databaseReference2.removeEventListener(mChild);
    }
}
