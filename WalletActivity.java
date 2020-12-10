package com.project.team8;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextClock;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class WalletActivity extends AppCompatActivity {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private DatabaseReference databaseReference2;
    private ChildEventListener mChild;

    static ArrayList<String> idlist = new ArrayList<>();
    static ArrayList<String> storelist = new ArrayList<>();
    static ArrayList<String> menulist = new ArrayList<>();
    static ArrayList<String> itemprimarylist = new ArrayList<>();
    static ArrayList<String> expire_datelist = new ArrayList<>();
    static ArrayList<Integer> rpricelist = new ArrayList<>();
    static ArrayList<Integer> hpricelist = new ArrayList<>();
    static ArrayList<String> cus_idlist = new ArrayList<>();
    static ArrayList<String> itemidlist = new ArrayList<>();
    static ArrayList<String> selllist = new ArrayList<>();
    static ArrayList<String> catelist = new ArrayList<>();


    private ArrayList<String> sellid = new ArrayList<>();
    private ArrayList<String> nonsellid = new ArrayList<>();
    private ArrayList<String> selltext = new ArrayList<>();
    private ArrayList<String> nonselltext = new ArrayList<>();

    private ArrayList<String> id = new ArrayList<>();
    private ArrayList<String> store = new ArrayList<>();
    private ArrayList<String> menu = new ArrayList<>();
    private ArrayList<String> expire_date = new ArrayList<>();
    private ArrayList<Integer> rprice = new ArrayList<>();
    private ArrayList<Integer> hprice = new ArrayList<>();
    private ArrayList<String> selling = new ArrayList<>();


    private String sid,sstore,smenu,sexpire_date,scus_id,sitemid,sselling, sitemprimary,scate;
    int srprice =0;
    int shprice = 0;
    private String cus_Id;


    private ListView listView1, listview2;
    private ArrayAdapter<String> adapter1, adapter2;
    List<Object>Array1 = new ArrayList<>();
    List<Object>Array2 = new ArrayList<>();

    String text = "";
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
        Intent ii = getIntent();
        cus_Id = ii.getExtras().getString("cus_id");


        listView1 = (ListView)findViewById(R.id.parent1);
        listview2 = (ListView)findViewById(R.id.parent2);
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent in = new Intent(getApplicationContext(),NotSelling.class);
                String send_id = nonsellid.get(i);

                in.putExtra("con_id",send_id.toString());
                in.putExtra("cus_id",cus_Id);
                in.putExtra("text",nonselltext.get(i));
                startActivity(in);
            }
        });

       listview2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               Intent in = new Intent(getApplicationContext(),Selling.class);
               String send_id = sellid.get(i);
               in.putExtra("con_id",send_id.toString());
               in.putExtra("cus_id",cus_Id);
               in.putExtra("text",selltext.get(i));
               startActivity(in);
           }
       });


        initDatabase();

        adapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, new ArrayList<String>());
        listView1.setAdapter(adapter1);
        adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,new ArrayList<String>());
        listview2.setAdapter(adapter2);

        databaseReference = firebaseDatabase.getReference("GIFTCON");
        databaseReference2 = firebaseDatabase.getReference("ITEM");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                idlist.clear();
                cus_idlist.clear();
                expire_datelist.clear();
                hpricelist.clear();
                itemidlist.clear();
                selllist.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    sid = snapshot.getKey();
                    idlist.add(sid);
                    scus_id = snapshot.child("cus_id").getValue(String.class);
                    cus_idlist.add(scus_id);
                    sexpire_date = snapshot.child("expire_date").getValue(String.class);
                    expire_datelist.add(sexpire_date);
                    shprice = snapshot.child("hprice").getValue(Integer.class);
                    hpricelist.add(shprice);
                    sitemid = snapshot.child("itemid").getValue(String.class);
                    itemidlist.add(sitemid);
                    sselling = snapshot.child("selling").getValue(String.class);
                    selllist.add(sselling);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                itemprimarylist.clear();
                menulist.clear();
                rpricelist.clear();
                storelist.clear();
                catelist.clear();

                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {


                    sitemprimary = snapshot.getKey();
                    itemprimarylist.add(sitemprimary);
                    smenu = snapshot.child("menu").getValue(String.class);
                    menulist.add(smenu);
                    srprice = snapshot.child("rprice").getValue(Integer.class);
                    rpricelist.add(srprice);
                    sstore = snapshot.child("store").getValue(String.class);
                    storelist.add(sstore);
                    scate = snapshot.child("category").getValue(String.class);
                    catelist.add(scate);
                }

                for(int i=0;i<idlist.size();i++)
                {
                    //giftcon 중에 소유자가 cus_Id인 giftcon들만
                    if(cus_idlist.get(i).toString().equals(cus_Id))
                    {
                        for(int j=0;j<itemprimarylist.size();j++)
                        {
                            if(itemprimarylist.get(j).toString().equals(itemidlist.get(i)))
                            {
                                //giftcon 의 item id를 찾아
                                //giftcon 번호, 유효기간, 희망가, 매장, 메뉴, 정가, 판매여부 저장
                                id.add(idlist.get(i));
                                expire_date.add(expire_datelist.get(i));
                                hprice.add(hpricelist.get(i));
                                store.add(storelist.get(j));
                                menu.add(menulist.get(j));
                                rprice.add(rpricelist.get(j));
                                selling.add(selllist.get(i));

                            }
                        }
                    }
                }

                for (int i = 0; i < id.size(); i++) {
                    if (selling.get(i).equals("true")) {
                        sellid.add(id.get(i));
                    } else
                        nonsellid.add(id.get(i));
                }

                for (int i = 0; i < id.size(); i++) {
                    if (selling.get(i).equals("true")) {
                        //판매중이면
                        text = "<<판매중>>\n[" + store.get(i) + "] " + menu.get(i) + "\n유효기간 : " + expire_date.get(i) + " 까지\n" + hprice.get(i) +
                                "(/" + rprice.get(i) + ")원 ";

                        Array2.add(text);
                        adapter2.add(text);
                        selltext.add(text);                 //listview click시 식별해주기위한 text
                                   //listview click시 넘겨줄 con_id
                    } else {
                        text = "[" + store.get(i) + "] " + menu.get(i) + "\n유효기간 : " + expire_date.get(i) + " 까지\n" +
                                rprice.get(i) + "원 ";

                        Array1.add(text);
                        adapter1.add(text);
                        nonselltext.add(text);              //listview click시 식별해주기위한 text
                                //listview click시 넘겨줄 con_id
                    }



                }
                adapter1.notifyDataSetChanged();
                listView1.setSelection(adapter1.getCount()-1);
                adapter2.notifyDataSetChanged();
                listview2.setSelection(adapter2.getCount()-1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

        private void initDatabase() {
            firebaseDatabase = FirebaseDatabase.getInstance();
            databaseReference = firebaseDatabase.getReference("GIFTCON");
            databaseReference2 = firebaseDatabase.getReference("ITEM");

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

        protected void onDestroy(){
            super.onDestroy();
            databaseReference.removeEventListener(mChild);
            databaseReference2.removeEventListener(mChild);
        }

    public void sell(View view) {
        Intent intent = new Intent(getApplicationContext(),CategoryActivity.class);
        intent.putExtra("cus_id",cus_Id);
        startActivity(intent);
        finish();
    }

    public void home(View view) {

    }

    public void mypage(View view) {
        Intent i = new Intent(getApplicationContext(),MypageActivity.class);
        i.putExtra("cus_id",cus_Id);
        startActivity(i);
        finish();
    }

    public void goadd(View view) {
        Intent intent = new Intent(getApplicationContext(),AddconActivity.class);
        intent.putExtra("cus_id",cus_Id);
        startActivity(intent);
    }
}
