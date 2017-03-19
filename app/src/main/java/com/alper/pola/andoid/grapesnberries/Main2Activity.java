package com.alper.pola.andoid.grapesnberries;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.squareup.picasso.Picasso;

public class Main2Activity extends AppCompatActivity {

    String image;
    ImageView imageview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        TextView txtdetail;
        imageview = (ImageView) findViewById(R.id.imageView);

        txtdetail = (TextView) findViewById(R.id.textviewdetail);

        Bundle bundle = getIntent().getExtras();
        String desc = bundle.getString("desc");
        image = bundle.getString("image");
        txtdetail.setText(desc);
        Picasso.with(getApplicationContext()).load(image).into(imageview);


    }

}
