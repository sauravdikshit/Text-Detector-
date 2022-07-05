package com.weirdinventor.textdetector;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;


public class Gallery extends AppCompatActivity {
    ImageView captureimg;
    Button selectimage;
    EditText displaytext;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        captureimg = findViewById(R.id.captureimg);
        selectimage = findViewById(R.id.selectimage);
        displaytext =findViewById(R.id.displaytext);

        selectimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             startCropActivity();
            }
        });
    }
    private void startCropActivity(){

        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(this);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == RESULT_OK) {
            Uri resultUri = result.getUri();
            captureimg.setImageURI(resultUri);

                BitmapDrawable bitmapDrawable = (BitmapDrawable) captureimg.getDrawable();
            Bitmap bitmap = bitmapDrawable.getBitmap();

            TextRecognizer recognizer = new TextRecognizer.Builder(getApplicationContext()).build();


                if (! recognizer.isOperational()){
                    Toast.makeText(this,"Error",Toast.LENGTH_LONG).show();
                }

                else {
                    Frame frame =new Frame.Builder().setBitmap(bitmap).build();
                    SparseArray<TextBlock> items = recognizer.detect(frame);
                    StringBuilder sb =new StringBuilder();
                    for (int i =0; i<items.size();i++){
                        TextBlock myItem =items.valueAt(i);
                        sb.append(myItem.getValue());



                    }
                    displaytext.setText(sb.toString());


                }

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }


}
