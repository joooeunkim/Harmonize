package com.project.team8;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class WalletActivity extends AppCompatActivity {

    private ArrayList<String> id = new ArrayList<>();
    private ArrayList<String> sellid = new ArrayList<>();
    private ArrayList<String> nonsellid = new ArrayList<>();
    private ArrayList<String> store = new ArrayList<>();
    private ArrayList<String> menu = new ArrayList<>();
    private ArrayList<String> expire_date = new ArrayList<>();
    private ArrayList<String> rprice = new ArrayList<>();
    private ArrayList<String> hprice = new ArrayList<>();
    private ArrayList<TextView> sellinglist = new ArrayList<>();
    private ArrayList<TextView> notsellinglist = new ArrayList<>();
    private ArrayList<String> selling = new ArrayList<>();
    private LinearLayout container;
    private String cus_id;
    String text = "";
    SQLiteDatabase sqlDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
        Intent ii = getIntent();
        cus_id = ii.getExtras().getString("cus_id");


        sqlDB = openOrCreateDatabase("gift.db",MODE_PRIVATE,null);
        Cursor cursor;
        String sql ="select g.id, g.expire_date, g.hprice, i.store, i.menu, i.rprice, g.selling"
                +" from giftcon g, item i"
                +" where g.itemid = i.itemid and g.cus_id = '"+cus_id+"';";

        cursor = sqlDB.rawQuery(sql,null);

        while(cursor.moveToNext())
        {
            id.add(cursor.getString(0));
            expire_date.add(cursor.getString(1));
            hprice.add(cursor.getString(2));
            store.add(cursor.getString(3));
            menu.add(cursor.getString(4));
            rprice.add(cursor.getString(5));
            selling.add(cursor.getString(6));

        }
        for(int i=0;i<id.size();i++)
        {
            if(selling.get(i).equals("true"))
            {
                sellid.add(id.get(i));
            }
            else
                nonsellid.add(id.get(i));
        }
        container = (LinearLayout)findViewById(R.id.parent);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        for(int i=0;i<id.size();i++)
        {
            TextView t1 = new TextView(this);

            t1.setLayoutParams(lp);
            t1.setLinksClickable(true);

            if(selling.get(i).equals("true"))
            {
                //판매중이면
                text = "<<판매중>>\n["+store.get(i)+"] "+menu.get(i)+"\n유효기간 : "+expire_date.get(i)+" 까지\n"+hprice.get(i)+
                        "(/"+rprice.get(i)+")원 ";
                t1.setText(text);
                t1.setBackgroundResource(R.drawable.selling);
                sellinglist.add(t1);
            }
            else
            {
                text= "["+store.get(i)+"] "+menu.get(i)+"\n유효기간 : "+expire_date.get(i)+" 까지\n"+
                        rprice.get(i)+"원 ";
                t1.setText(text);
                t1.setBackgroundResource(R.drawable.textborder);
                notsellinglist.add(t1);
            }
            container.addView(t1);

        }

        //판매중인 리스트 눌렀을 때
        View.OnClickListener selllistener = new View.OnClickListener() {
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(),Selling.class);
                String send_id = "";
                for(int i=0;i<sellinglist.size();i++)
                {
                    if(v.equals(sellinglist.get(i)))
                    {
                        send_id = sellid.get(i);
                        text = sellinglist.get(i).getText().toString();
                    }
                }
                in.putExtra("con_id",send_id.toString());
                in.putExtra("cus_id",cus_id);
                in.putExtra("text",text);
                startActivity(in);
            }
        };

        for(int j=0;j<sellinglist.size();j++)
        {
            sellinglist.get(j).setOnClickListener(selllistener);
        }

        //판매중 아닌 리스트 눌렀을 때
        View.OnClickListener listener = new View.OnClickListener() {
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(),NotSelling.class);
                String send_id = "";
                for(int i=0;i<notsellinglist.size();i++)
                {
                    if(v.equals(notsellinglist.get(i)))
                    {
                        send_id = nonsellid.get(i);
                        text = notsellinglist.get(i).getText().toString();
                    }
                }
                in.putExtra("con_id",send_id.toString());
                in.putExtra("cus_id",cus_id);
                in.putExtra("text",text);
                startActivity(in);
            }
        };

        for(int j=0;j<notsellinglist.size();j++)
        {
            notsellinglist.get(j).setOnClickListener(listener);
        }

    }


    public void sell(View view) {
        Intent intent = new Intent(getApplicationContext(),CategoryActivity.class);
        intent.putExtra("cus_id",cus_id);
        startActivity(intent);
    }

    public void home(View view) {
    }

    public void mypage(View view) {
    }

    public void goadd(View view) {
        Intent intent = new Intent(getApplicationContext(),AddconActivity.class);
        intent.putExtra("cus_id",cus_id);
        startActivity(intent);
    }
}
