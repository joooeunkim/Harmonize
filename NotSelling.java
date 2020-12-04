package com.project.team8;

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

public class NotSelling extends AppCompatActivity {

    private String con_id;
    private String cus_id;
    private String category;
    private String selling;
    private String hpricee;
    private String rprice;
    private String text;
    SQLiteDatabase sqlDB;
    private Drawable d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_not_selling);

        Intent intent = getIntent();
        con_id = intent.getExtras().getString("con_id");
        cus_id = intent.getExtras().getString("cus_id");
        text = intent.getExtras().getString("text");

        TextView t1 = (TextView)findViewById(R.id.textfield);
        ImageView c = (ImageView)findViewById(R.id.cat);
        t1.setText(text);
        ImageView v1 = new ImageView(getBaseContext());
        v1.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
        sqlDB = openOrCreateDatabase("gift.db",MODE_PRIVATE,null);
        Cursor cursor;

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


    public void addsell(View view) {
        Intent intent = new Intent(getApplicationContext(),AddsellActivity.class);
        intent.putExtra("cus_id",cus_id);
        intent.putExtra("con_id",con_id);
        intent.putExtra("text",text);
        startActivity(intent);
    }
}
