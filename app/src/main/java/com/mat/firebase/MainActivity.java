package com.mat.firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private Button btnave;
    StorageReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnave = findViewById(R.id.btn);

        ref = FirebaseStorage.getInstance().getReference().child("Image");
        btnave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                InputStream inputStream = null;
                try {
                    inputStream = getAssets().open("image.jpg");
                    uploadImage(inputStream);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });


    }

    private void uploadImage(InputStream inputStream) {
        if (inputStream != null){
            ref.child("myImage.jpg").putStream(inputStream).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(MainActivity.this, "Image uploaded Successfully!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
