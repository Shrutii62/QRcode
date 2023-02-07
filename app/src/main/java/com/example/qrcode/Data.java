package com.example.qrcode;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.Gravity;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

public class Data extends AppCompatActivity {
    TextView TVscandata;
    AppCompatButton copy, open;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
         TVscandata = findViewById(R.id.TVscandata);
         copy = findViewById(R.id.copy);
        open = findViewById(R.id.open);

        Intent intent = getIntent();
        String data = intent.getStringExtra("data");
        TVscandata.setText(data);






        TVscandata.setMovementMethod(LinkMovementMethod.getInstance());

        if (URLUtil.isValidUrl(data)){
            open.setVisibility(View.VISIBLE);
            open.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent myWebLink = new Intent(android.content.Intent.ACTION_VIEW);
                    myWebLink.setData(Uri.parse(data));
                    startActivity(myWebLink);

                }
            });
        }else{

            open.setVisibility(View.GONE);
        }







        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("data", data);
                clipboard.setPrimaryClip(clip);

                Toast.makeText(Data.this, "Text Copied...", Toast.LENGTH_SHORT).show();
            }
        });



    }
}