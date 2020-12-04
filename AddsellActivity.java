package com.project.team8;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class AddsellActivity extends AppCompatActivity {
    private String con_id;
    private String cus_id;
    private String category;
    private String selling;
    private String rprice;
    private String text;
    private int hp;
    private EditText e;
    SQLiteDatabase sqlDB;
    private Drawable d;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addsell);

        Intent intent = getIntent();
        con_id = intent.getExtras().getString("con_id");
        cus_id = intent.getExtras().getString("cus_id");
        text = intent.getExtras().getString("text");

        TextView t1 = (TextView)findViewById(R.id.textfield);
        ImageView c = (ImageView)findViewById(R.id.cat);
        e = (EditText)findViewById(R.id.hopeprice);
        t1.setText(text);
        ImageView v1 = new ImageView(getBaseContext());
        v1.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
        sqlDB = openOrCreateDatabase("gift.db",MODE_PRIVATE,null);


        String sql ="select category from item where itemid in " +
                "(select itemid from giftcon where id = '"+con_id+"');";
        cursor = sqlDB.rawQuery(sql,null);
        while(cursor.moveToNext())
        {
            category = cursor.getString(0);
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

    public void addselling(View view) {
        String sql ="select rprice from item where itemid in " +
                "(select itemid from giftcon where id = '"+con_id+"');";

        hp = Integer.parseInt(e.getText().toString());
        cursor = sqlDB.rawQuery(sql,null);
        while(cursor.moveToNext())
        {
            rprice = cursor.getString(0);
        }

        int rp = Integer.parseInt(rprice);
        if(rp < hp)
        {
            Toast.makeText(getApplicationContext(),"희망가격이 정가보다 높습니다.",Toast.LENGTH_SHORT).show();
            e.setText("");
        }
        else
        {
            sqlDB = openOrCreateDatabase("gift.db",MODE_PRIVATE,null);
            sql ="update GIFTCON set selling = 'true' , hprice = "+hp+" where id = '"+con_id+"';";
            sqlDB.execSQL(sql);
            Toast.makeText(getApplicationContext(),"판매등록 되었습니다.",Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(getApplicationContext(),WalletActivity.class);
            intent.putExtra("cus_id",cus_id);
            startActivity(intent);
        }

    }
}
