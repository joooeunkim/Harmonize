package com.project.team8;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup.LayoutParams;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;

import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class conlistActivity extends Activity {

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference;
    private DatabaseReference databaseReference2;
    private ChildEventListener mChild;
    private TextView t;


    private ArrayList<String> id = new ArrayList<>();
    private ArrayList<String> store = new ArrayList<>();
    private ArrayList<String> menu = new ArrayList<>();
    private ArrayList<String> expire_date = new ArrayList<>();
    private ArrayList<Integer> rprice = new ArrayList<>();
    private ArrayList<Integer> hprice = new ArrayList<>();
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
    static ArrayList<String>send = new ArrayList<>();
    static ArrayList<String>textlist = new ArrayList<>();
    private ListView listView1;
    private ArrayAdapter<String> adapter1;
    List<Object> Array1 = new ArrayList<>();

    private String sid,sstore,smenu,sexpire_date,cus_id,itemid,selling, itemprimary,cate;
    private String cus_Id;
    int shprice = 0;
    int srprice= 0;
    String top;
    String category;
    String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conlist);
        t = (TextView) findViewById(R.id.category);
        adapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, new ArrayList<String>());
        listView1 = (ListView)findViewById(R.id.parents);
        listView1.setAdapter(adapter1);
        id.clear();
        store.clear();
        menu.clear();
        expire_date.clear();
        hprice.clear();
        rprice.clear();
        //눌렀을 때 넘어가도록 어디로? ;buy로
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent in = new Intent(getApplicationContext(),buyActivity.class);
                String send_id = id.get(i);
                in.putExtra("con_id",send_id.toString());
                in.putExtra("cus_id",cus_Id);
                in.putExtra("text",textlist.get(i));
                in.putExtra("idlist",idlist);
                in.putExtra("storelist",storelist);
                in.putExtra("menulist",menulist);
                in.putExtra("itemprimarylist",itemprimarylist);
                in.putExtra("expire_datelist",expire_datelist);
                in.putExtra("rpricelist",rpricelist);
                in.putExtra("hpricelist",hpricelist);
                in.putExtra("itemidlist",itemidlist);
                in.putExtra("sellinglist",sellinglist);
                in.putExtra("catelist",catelist);
                in.putExtra("cus_idlist",cus_idlist);
                startActivity(in);
            }
        });
        Intent intent = getIntent();
        category = intent.getExtras().getString("category");
        cus_Id = intent.getExtras().getString("cus_id");

        initDatabase();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                idlist.clear();
                cus_idlist.clear();
                expire_datelist.clear();
                hpricelist.clear();
                itemidlist.clear();
                sellinglist.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    sid = snapshot.getKey();
                    idlist.add(sid);
                    cus_id = snapshot.child("cus_id").getValue(String.class);
                    cus_idlist.add(cus_id);
                    sexpire_date = snapshot.child("expire_date").getValue(String.class);
                    expire_datelist.add(sexpire_date);
                    shprice = snapshot.child("hprice").getValue(Integer.class);
                    hpricelist.add(shprice);
                    itemid = snapshot.child("itemid").getValue(String.class);
                    itemidlist.add(itemid);
                    selling = snapshot.child("selling").getValue(String.class);
                    sellinglist.add(selling);
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
                    itemprimary = snapshot.getKey();
                    itemprimarylist.add(itemprimary);
                    smenu = snapshot.child("menu").getValue(String.class);
                    menulist.add(smenu);
                    srprice = snapshot.child("rprice").getValue(Integer.class);
                    rpricelist.add(srprice);
                    sstore = snapshot.child("store").getValue(String.class);
                    storelist.add(sstore);
                    cate = snapshot.child("category").getValue(String.class);
                    catelist.add(cate);
                }

                switch (category) {
                    case "drinks":
                        top = "커피/음료";
                        break;
                    case "fastfood":
                        top = "치킨/피자/햄버거";
                        break;
                    case "bread":
                        top = "베이커리/도넛";
                        break;
                    case "icecream":
                        top = "아이스크림";
                        break;
                    case "movie":
                        top = "영화";
                        break;
                    case "outeat":
                        top = "외식";
                        break;
                    case "coupon":
                        top = "상품권";
                        break;
                    case "beauty":
                        top = "뷰티";
                        break;
                    case "store":
                        top = "편의점";
                        break;

                }
                t.setText(top);

                for(int i=0;i<idlist.size();i++)
                {
                    //전체 giftcon중
                    if(sellinglist.get(i).equals("true")){
                        //판매중 상태의 giftcon
                        for(int j=0;j<itemprimarylist.size();j++)
                        {
                            if(itemprimarylist.get(j).equals(itemidlist.get(i)))
                            {
                                if(catelist.get(j).equals(category)){
                                    //선택한 category에 해당하는 giftcon
                                    //giftcon의 itemid 에 해당하는 기프티콘 번호, 매장명, 메뉴, 유효기간, 희망가, 정가 저장
                                    id.add(idlist.get(i));
                                    store.add(storelist.get(j));
                                    menu.add(menulist.get(j));
                                    expire_date.add(expire_datelist.get(i));
                                    hprice.add(hpricelist.get(i));
                                    rprice.add(rpricelist.get(j));
                                }

                            }
                        }
                    }


                }
                textlist.clear();
                for(int i=0;i<id.size();i++)
                {
                    text = "[" + store.get(i) + "] " + menu.get(i) + "\n유효기간 : " + expire_date.get(i) + " 까지\n" + hprice.get(i) +
                            "(/" + rprice.get(i) + ")원 ";
                    Array1.add(text);
                    adapter1.add(text);
                    textlist.add(text);
                }
                adapter1.notifyDataSetChanged();
                listView1.setSelection(adapter1.getCount()-1);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

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

    protected void onDestroy() {
        super.onDestroy();
        databaseReference.removeEventListener(mChild);
        databaseReference2.removeEventListener(mChild);
    }

}
