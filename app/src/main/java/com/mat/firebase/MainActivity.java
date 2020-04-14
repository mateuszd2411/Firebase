package com.mat.firebase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private static final int REQUES_CODE_IMAGE = 101;
    private ImageView imageViewAdd;
    private EditText inputImageName;
    private TextView textViewProgress;
    private ProgressBar progressBar;
    private Button btnUpload;

    Uri imageUri;
    boolean isImageAdded = false;

    DatabaseReference dataRef;
    StorageReference storageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageViewAdd = findViewById(R.id.imageViewAdd);
        inputImageName = findViewById(R.id.inputImageViewAdd);
        textViewProgress = findViewById(R.id.textViewProgress);
        progressBar = findViewById(R.id.progressBar);
        btnUpload = findViewById(R.id.btnUpload);

        textViewProgress.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);

        dataRef = FirebaseDatabase.getInstance().getReference().child("Car");
        storageRef = FirebaseStorage.getInstance().getReference().child("CarImage");

        imageViewAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,REQUES_CODE_IMAGE);
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String imageName = inputImageName.getText().toString();

                if (isImageAdded != false && imageName != null){
                    uploadImage(imageName);
                }
            }
        });
    }

    private void uploadImage(final String imageName) {
        textViewProgress.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);

        final String key = dataRef.push().getKey();
        storageRef.child(key + ".jpg").putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                storageRef.child(key + ".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        HashMap hashMap = new HashMap();
                        hashMap.put("CarName",imageName);
                        hashMap.put("ImageUrl",uri.toString());

                        dataRef.setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(MainActivity.this, "Data Successfully Uploaded!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });


            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                double progress = (taskSnapshot.getBytesTransferred() * 100) / taskSnapshot.getTotalByteCount();
                progressBar.setProgress((int)progress);
                textViewProgress.setText(progress + " %");
            }
        });
    }

    @Override
    public boolean showAssist(Bundle args) {
        return super.showAssist(args);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUES_CODE_IMAGE && data != null){
            imageUri = data.getData();
            isImageAdded = true;
            imageViewAdd.setImageURI(imageUri);
        }
    }
}
