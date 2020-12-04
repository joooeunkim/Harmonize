package com.project.team8;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class buyActivity extends AppCompatActivity {

    SQLiteDatabase sqlDB;
    private String store;
    private String menu;
    private String expire_date;
    private String hprice;
    private String rprice;
    private String itemid;
    private String con_id;
    private String cus_id;
    private String sell_id;
    private Cursor cursor;
    private Integer sell_money;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);
        Intent intent = getIntent();
        con_id = intent.getExtras().getString("con_id");
        cus_id = intent.getExtras().getString("cus_id");

        TextView t = (TextView)findViewById(R.id.id);
        TextView infot = (TextView)findViewById(R.id.info);


        sqlDB = openOrCreateDatabase("gift.db",MODE_PRIVATE,null);

        String sql = "SELECT  itemid, expire_date,  hprice, cus_id  FROM GIFTCON WHERE id = '"+con_id+"';";
        cursor = sqlDB.rawQuery(sql,null);
        while(cursor.moveToNext())
        {
            itemid = cursor.getString(0);
           expire_date = cursor.getString(1);
           hprice = cursor.getString(2);
           sell_id = cursor.getString(3);
        }

        sql = "SELECT store, menu, rprice FROM ITEM WHERE itemid ="+itemid;
        cursor = sqlDB.rawQuery(sql,null);
        while(cursor.moveToNext())
        {
            store = cursor.getString(0);
            menu =  cursor.getString(1);
            rprice = cursor.getString(2);
        }


        sql = "SELECT money FROM USER WHERE id = '"+sell_id+"';";
        cursor = sqlDB.rawQuery(sql,null);
        while(cursor.moveToNext())
        {
           sell_money = cursor.getInt(0);
        }
        String info = "["+store+"] "+menu+"\n"+"유효기간 : "+expire_date+"까지\n"+"가격 : "+hprice+"(/"+rprice+")원";
        t.setText("<<상세보기>>");
        infot.setText(info);


    }



    public void buynow(View view) {

        String sql = "SELECT money FROM USER WHERE id = '"+cus_id+"';";
        cursor = sqlDB.rawQuery(sql,null);
        int money = 0;
        while(cursor.moveToNext())
        {
            money = cursor.getInt(0);
        }

        if(Integer.parseInt(hprice)>money)
        {
            Toast.makeText(getApplicationContext(),"잔액이 부족합니다.\n 충전 후 이용해주세요.",Toast.LENGTH_SHORT).show();
        }
        else
        {
            int balance = money - (Integer.parseInt(hprice));
            int price2 = Integer.parseInt(hprice);
            Toast.makeText(getApplicationContext(),"구매완료 되었습니다.",Toast.LENGTH_SHORT).show();
            sql = "UPDATE GIFTCON SET cus_id ='"+cus_id+"', selling ='false' "
                    +"WHERE id = '"+con_id+"';";
            sqlDB.execSQL(sql);
            sql ="UPDATE USER SET money = "+balance+" WHERE id = '"+cus_id+"';";
            sqlDB.execSQL(sql);
            int change = sell_money+price2;
            sql = "UPDATE USER SET money = "+change+" WHERE id = '"+sell_id+"';";
            sqlDB.execSQL(sql);
            sqlDB.close();

        }
    }
}
