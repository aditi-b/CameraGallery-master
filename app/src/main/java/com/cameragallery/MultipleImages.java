package com.cameragallery;

import android.content.ClipData;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MultipleImages extends AppCompatActivity implements View.OnClickListener {

    int PICK_IMAGE_MULTIPLE = 1;
    ImageView image1, image2, image3, image4;
    Button btnMultiple;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_images);
        btnMultiple = findViewById(R.id.button2);
        image1 = findViewById(R.id.imageView2);
        image2 = findViewById(R.id.imageView3);
        image3 = findViewById(R.id.imageView4);
        image4 = findViewById(R.id.imageView5);
        btnMultiple.setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub

        if(requestCode==PICK_IMAGE_MULTIPLE){

            if(resultCode==RESULT_OK){
                //data.getParcelableArrayExtra(name);
                //If Single image selected then it will fetch from Gallery
                if(data.getData()!=null){

                    Uri mImageUri=data.getData();
                }
                else{
                    if(data.getClipData()!=null){
                        ClipData mClipData=data.getClipData();
                        ArrayList<Uri> mArrayUri=new ArrayList<Uri>();
                        for(int i=0;i<mClipData.getItemCount();i++){

                            ClipData.Item item = mClipData.getItemAt(i);
                            Uri uri = item.getUri();
                            mArrayUri.add(uri);

                        }
                        for(int i=0; i<mArrayUri.size();i++)
                        {
                            Bitmap bitmap = null;
                            try {
                                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), mArrayUri.get(i));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            switch (i)
                            {
                                case 0:
                                    image1.setImageBitmap(bitmap);
                                    break;

                                case 1:
                                    image2.setImageBitmap(bitmap);
                                    break;

                                case 2:
                                    image3.setImageBitmap(bitmap);
                                    break;

                                case 3:
                                    image4.setImageBitmap(bitmap);
                                    break;
                            }
                        }
                        Log.v("LOG_TAG", "Selected Images"+ mArrayUri.size());
                    }

                }

            }

        }

        super.onActivityResult(requestCode, resultCode, data);
    }
    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.button2:
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Picture"), PICK_IMAGE_MULTIPLE);
                break;

        }
    }
}
