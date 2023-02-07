package com.example.qrcode;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Display;
import android.view.Gravity;
import android.view.PointerIcon;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.zxing.WriterException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class GenerateQRCode extends AppCompatActivity {

    TextView  TVgenerateHD;
    ImageView imGnrtQR, savetogallary;
    AppCompatButton  btgenerate;
    TextInputEditText inputData;
    QRGEncoder qrgEncoder;
    Bitmap bitmap;
    View bar;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_qrcode);

        TVgenerateHD= findViewById(R.id.TVgenerateHD);
        imGnrtQR= findViewById(R.id.imGnrtQR);
        btgenerate= findViewById(R.id.btgenerate);
        inputData= findViewById(R.id.inputData);
        savetogallary= findViewById(R.id.savetogallary);



        btgenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String  Data = inputData.getText().toString();
                if (Data.isEmpty()){
                    Toast.makeText(GenerateQRCode.this, "Please Enter Some Data for generating code", Toast.LENGTH_SHORT).show();
                }else{
                    WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
                    Display display = manager.getDefaultDisplay();
                    Point point = new Point();
                    display.getSize(point);
                    int width = point.x;
                    int height = point.y;
                    int dimen = width<height ? width:height;
                    dimen = dimen*3/4;

                    qrgEncoder = new QRGEncoder(inputData.getText().toString(),null, QRGContents.Type.TEXT,dimen);
                    Bitmap bitmap = qrgEncoder.getBitmap();
                    TVgenerateHD.setVisibility(View.GONE);
                    imGnrtQR.setImageBitmap(bitmap);

                    savetogallary.setVisibility(View.VISIBLE);


                }
            }
        });


        ActivityCompat.requestPermissions(GenerateQRCode.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        ActivityCompat.requestPermissions(GenerateQRCode.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);


        savetogallary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {






//                BitmapDrawable draw = (BitmapDrawable) imGnrtQR.getDrawable();
//                Bitmap bitmap = draw.getBitmap();
//
//                FileOutputStream outStream = null;
//                File sdCard = Environment.getExternalStorageDirectory();
//                File dir = new File(sdCard.getAbsolutePath() + "/YourFolderName");
//                dir.mkdirs();
//                String fileName = String.format("%d.jpg", System.currentTimeMillis());
//                File outFile = new File(dir, fileName);
//                Toast.makeText(GenerateQRCode.this, "done", Toast.LENGTH_SHORT).show();
//
//                try {
//                    outStream = new FileOutputStream(outFile);
//                    Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
//                    intent.setData(Uri.fromFile(outFile));
//                    sendBroadcast(intent);
//
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }
//
//                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
//                try {
//                    outStream.flush();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//                try {
//                    outStream.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }



                BitmapDrawable bitmapDrawable = (BitmapDrawable) imGnrtQR.getDrawable();
                Bitmap bitmap = bitmapDrawable.getBitmap();

                FileOutputStream outputStream = null;
                File file = Environment.getExternalStorageDirectory();

                File dir = new File(file.getAbsolutePath() + "/QrGenerator");
                dir.mkdir();

                String filename  = String.format("%d.png",System.currentTimeMillis());
                File outFiles = new File(dir, filename);
                Toast.makeText(GenerateQRCode.this, "QR Code has been saved to the Gallery", Toast.LENGTH_SHORT).show();

                try {
                        outputStream = new FileOutputStream(outFiles);
                    Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    intent.setData(Uri.fromFile(outFiles));
                    sendBroadcast(intent);


                }catch (Exception e ){e.printStackTrace();}
                bitmap.compress(Bitmap.CompressFormat.PNG, 100,outputStream);


                try {
                    outputStream.flush();

                }catch (Exception e ){e.printStackTrace();}

                try {
                    outputStream.close();

                }catch (Exception e ){e.printStackTrace();}

            }
        });
    }
}