package com.project.team8;

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
import android.widget.LinearLayout;

import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;

public class conlistActivity extends Activity {


    private ArrayList<String> id = new ArrayList<>();
    private ArrayList<String> store = new ArrayList<>();
    private ArrayList<String> menu = new ArrayList<>();
    private ArrayList<String> expire_date = new ArrayList<>();
    private ArrayList<String> rprice = new ArrayList<>();
    private ArrayList<String> hprice = new ArrayList<>();
    private ArrayList<TextView> list = new ArrayList<>();
    private LinearLayout container;
    private String cus_id;
    SQLiteDatabase sqlDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conlist);

        Intent intent = getIntent();
        String category = intent.getExtras().getString("category");
        cus_id=intent.getExtras().getString("cus_id");
        String top ="";
        TextView t = (TextView)findViewById(R.id.category);

        switch (category)
        {
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


        sqlDB = openOrCreateDatabase("gift.db",MODE_PRIVATE,null);
        Cursor cursor;
        String sql ="select g.id, g.expire_date, g.hprice, i.store, i.menu, i.rprice"
                +" from giftcon g, item i"
                +" where g.cus_id != '"+cus_id+"' and g.selling ='true' and "
                +"g.itemid = i.itemid and i.category = '"+category+"';";
        cursor = sqlDB.rawQuery(sql,null);
        while(cursor.moveToNext())
        {
            id.add(cursor.getString(0));
            expire_date.add(cursor.getString(1));
            hprice.add(cursor.getString(2));
            store.add(cursor.getString(3));
            menu.add(cursor.getString(4));
            rprice.add(cursor.getString(5));

        }

        container = (LinearLayout)findViewById(R.id.parent);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);



       for(int i=0;i<id.size();i++)
       {
           TextView t1 = new TextView(this);
           t1.setText("["+store.get(i)+"] "+menu.get(i)+"\n유효기간 : "+expire_date.get(i)+" 까지\n"+hprice.get(i)+
                   "(/"+rprice.get(i)+")원 ");
           t1.setLayoutParams(lp);
           t1.setLinksClickable(true);
            t1.setBackgroundResource(R.drawable.textborder);
            list.add(t1);
           container.addView(t1);

       }

        View.OnClickListener listener = new View.OnClickListener() {
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(),buyActivity.class);
                String send_id = "";
                for(int i=0;i<id.size();i++)
                {
                    if(v.equals(list.get(i)))
                    {
                        send_id = id.get(i);
                    }
                }
                in.putExtra("con_id",send_id.toString());
                in.putExtra("cus_id",cus_id);
                startActivity(in);
            }
        };

        for(int i=0;i<id.size();i++)
        {
            list.get(i).setOnClickListener(listener);
        }

    }


    public void sell(View view) {
    }

    public void home(View view) {
        Intent i = new Intent(getApplicationContext(), WalletActivity.class);
        i.putExtra("cus_id",cus_id);
        startActivity(i);
    }

    public void mypage(View view) {
    }
}
