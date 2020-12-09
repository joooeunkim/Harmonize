package com.example.testocr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.googlecode.tesseract.android.TessBaseAPI;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {
    Bitmap image; //사용되는 이미지
    private TessBaseAPI mTess; //Tess API reference
    String datapath = "" ; //언어데이터가 있는 경로
    private final int GET_GALLERY_IMAGE = 200;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView)findViewById(R.id.imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent. setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, GET_GALLERY_IMAGE);
            }
        });

        //이미지 디코딩을 위한 초기화
        //image = BitmapFactory.decodeResource(getResources(), R.drawable.imageView); //샘플이미지파일
        //언어파일 경로
        datapath = getFilesDir()+ "/tesseract/";

        //한글&영어 데이터 체크
        checkFile(new File(datapath + "tessdata/"), "kor");
        checkFile(new File(datapath + "tessdata/"), "eng");

        //Tesseract API
        String lang = "kor+eng";

        mTess = new TessBaseAPI();
        mTess.init(datapath, lang);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == GET_GALLERY_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            //Uri selectedImageUri = data.getData();
            //imageView.setImageURI(selectedImageUri);
            try {
                // 선택한 이미지에서 비트맵 생성
                InputStream in = getContentResolver().openInputStream(data.getData());
                image = BitmapFactory.decodeStream(in);
                in.close();
                // 이미지뷰에 세팅
                imageView.setImageBitmap(image);
                //mPhotoCircleImageView.setImageBitmap(image);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //Process an Image
    public void processImage(View view) {
        Toast.makeText(getApplicationContext(), "잠시만 기다려주세요", Toast.LENGTH_LONG).show();

        String OCRresult = null;
        mTess.setImage(image);
        OCRresult = mTess.getUTF8Text();
        TextView OCRTextView = (TextView) findViewById(R.id.OCRTextView);

        OCRTextView.setText(OCRresult);
    }

    //copy file to device
    private void copyFiles(String lang) {
        try{
            //location we want the file to be at
            String filepath = datapath + "/tessdata/"+lang+".traineddata";

            //get access to AssetManager
            AssetManager assetManager = getAssets();

            //Open byte streams for reading/writing
            InputStream instream = assetManager.open("tessdata/"+lang+".traineddata");
            OutputStream outstream = new FileOutputStream(filepath);

            //Copy the file to the location specified by filepath
            byte[] buffer = new byte[1024];
            int read;
            while ((read = instream.read(buffer)) != -1) {
                outstream.write(buffer, 0, read);
            }
            outstream.flush();
            outstream.close();
            instream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //check file on the device
    private void checkFile(File dir, String lang) {
        //directory does not exist, but we can successfully create it
        if(!dir.exists()&& dir.mkdirs()) {
            copyFiles(lang);
        }
        //The directory exists, but there is no data file in it
        if(dir.exists()) {
            String datafilepath = datapath+ "/tessdata/"+lang+".traineddata";
            File datafile = new File(datafilepath);
            if(!datafile.exists()) {
                copyFiles(lang);
            }
        }
    }
}