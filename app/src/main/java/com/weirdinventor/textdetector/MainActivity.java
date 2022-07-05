package com.weirdinventor.textdetector;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.makeText;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionCloudTextRecognizerOptions;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private static final String FILE_NAME = "TextDetector.text";

    ImageView imageView;
    EditText textView;
    Button texttospeak, savetext;
    TextToSpeech textToSpeech;


    @RequiresApi(api = Build.VERSION_CODES.M)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.captureimg);
        textView = findViewById(R.id.displaytext);
        texttospeak = findViewById(R.id.txttospeech);
        savetext = findViewById(R.id.savefile);

        //Start savetext


        // start Text to speech
        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i == TextToSpeech.SUCCESS) {
                    int lang = textToSpeech.setLanguage(Locale.ENGLISH);

                }
            }
        });
        texttospeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String s = textView.getText().toString();
                int speech = textToSpeech.speak(s, TextToSpeech.QUEUE_FLUSH, null);

            }
        });
        //End Text to speech

        //check app level permision is granted for camera
        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, 101);

        }
    }

    public void doProcess(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 101);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bundle bundle = data.getExtras();
        //from bundle , extract the image
        Bitmap bitmap = (Bitmap) bundle.get("data");
        //set image in imageview
        imageView.setImageBitmap(bitmap);
        //process the image extract text
        // 1 create a FirebaseVisionImage object from a Bitmap object
        FirebaseVisionImage firebaseVisionImage = FirebaseVisionImage.fromBitmap(bitmap);
        // 2. get instance of FirebaseVision
        FirebaseVision firebaseVision = FirebaseVision.getInstance();
        // 3. Create an instance of FirebaseVisionTextRecognizer
        FirebaseVisionTextRecognizer detector = FirebaseVision.getInstance().getCloudTextRecognizer();

        FirebaseVisionTextRecognizer firebaseVisionTextRecognizer = firebaseVision.getOnDeviceTextRecognizer();
        FirebaseVisionCloudTextRecognizerOptions options = new FirebaseVisionCloudTextRecognizerOptions.Builder()
                .setLanguageHints(Arrays.asList("hi","bn","ur","mr","ta","te","sa"))
                .build();

        // 4. create a task to process the image

        Task<FirebaseVisionText> task = firebaseVisionTextRecognizer.processImage(firebaseVisionImage);
       Task<FirebaseVisionText> result = detector.processImage(firebaseVisionImage).addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
                            @Override
                            public void onSuccess(FirebaseVisionText firebaseVisionText) {
                               // Task completed successfully
                                String s = firebaseVisionText.getText();
                                textView.setText(s);

                           }
                        })
                       .addOnFailureListener(
                                new OnFailureListener() {
                                   @Override
                                   public void onFailure(@NonNull Exception e) {
                                        // Task failed with an exception
                                        // ...
                                    }
                                });

        // 5. if task is success

        task.addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
            @Override
            public void onSuccess(FirebaseVisionText firebaseVisionText) {
                String s = firebaseVisionText.getText();
                textView.setText(s);

            }
        });



        // 6. if task is failure

        task.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                makeText(getApplicationContext(), e.getMessage(), LENGTH_LONG).show();

            }
        });


        //Save Text
        savetext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String svtxt = textView.getText().toString().trim();
                // creating a folder with name
                File folder = getExternalFilesDir("Text Detector");
                //creating a file with name
                File file = new File(folder,"detect.txt");
                writeTextData (file,svtxt);
                textView.setText("");


            }
        });


        }

    private void writeTextData(File file, String data) {
        FileOutputStream fileOutputStream =null;
        try {
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(data.getBytes());
            Toast.makeText(this,"Done"+file.getAbsolutePath(),Toast.LENGTH_LONG).show();

        }catch (Exception e){
            e.printStackTrace();

        }finally {
            if (fileOutputStream !=null){
                try {
                    fileOutputStream.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }

    }
}
