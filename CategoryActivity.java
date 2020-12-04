package com.project.team8;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CategoryActivity extends AppCompatActivity {

    private String cus_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        Intent r = getIntent();
        cus_id = r.getExtras().getString("cus_id");

        TextView t1 = (TextView) findViewById(R.id.drinks);
        TextView t2 = (TextView) findViewById(R.id.fast);
        TextView t3 = (TextView) findViewById(R.id.bread);
        TextView t4 = (TextView) findViewById(R.id.icecream);
        TextView t5 = (TextView) findViewById(R.id.movie);
        TextView t6 = (TextView) findViewById(R.id.out);
        TextView t7 = (TextView) findViewById(R.id.coupon);
        TextView t8 = (TextView) findViewById(R.id.beauty);
        TextView t9 = (TextView) findViewById(R.id.store);


        View.OnClickListener listener = new View.OnClickListener(){
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(),conlistActivity.class);
                switch (v.getId())
                {
                    case R.id.drinks:
                        intent.putExtra("category","drinks");
                        break;
                    case R.id.fast:
                        intent.putExtra("category","fastfood");
                        break;
                    case R.id.bread:
                        intent.putExtra("category","bread");
                        break;
                    case R.id.icecream:
                        intent.putExtra("category","icecream");
                        break;
                    case R.id.movie:
                        intent.putExtra("category","movie");
                        break;
                    case R.id.out:
                        intent.putExtra("category","outeat");
                        break;
                    case R.id.coupon:
                        intent.putExtra("category","coupon");
                        break;
                    case R.id.beauty:
                        intent.putExtra("category","beauty");
                        break;
                    case R.id.store:
                        intent.putExtra("category","store");
                        break;


                }
                intent.putExtra("cus_id",cus_id);
                startActivity(intent);
            }

        };
        t1.setOnClickListener(listener);
        t2.setOnClickListener(listener);
        t3.setOnClickListener(listener);
        t4.setOnClickListener(listener);
        t5.setOnClickListener(listener);
        t6.setOnClickListener(listener);
        t7.setOnClickListener(listener);
        t8.setOnClickListener(listener);
        t9.setOnClickListener(listener);

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
