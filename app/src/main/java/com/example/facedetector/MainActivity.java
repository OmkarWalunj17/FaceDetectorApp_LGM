package com.example.facedetector;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.face.Face;
import com.google.mlkit.vision.face.FaceDetection;
import com.google.mlkit.vision.face.FaceDetector;
import com.google.mlkit.vision.face.FaceDetectorOptions;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final static int REQUEST_IMAGE_CAPTURE = 124;
    public static final int Camera_Req_Code = 102;

    ImageView capturedImage;
    Button cameraButton;
    InputImage image;

    private FaceDetector detector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);

        cameraButton = findViewById(R.id.camera_button);
        capturedImage = findViewById(R.id.imageViewResult);

        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                askCameraPermission();
            }
        });
    }

    private void askCameraPermission() {
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, REQUEST_IMAGE_CAPTURE);
        } else {
            openCamera();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_IMAGE_CAPTURE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            } else {
                Toast.makeText(this, "Camera Permission Granted", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void openCamera() {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, Camera_Req_Code);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Camera_Req_Code && resultCode == RESULT_OK && data != null) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            capturedImage.setImageBitmap(bitmap);
            detectFace(bitmap);
        }
    }

    private void detectFace(Bitmap bitmap) {
        FaceDetectorOptions highAccuracyOpts =
                new FaceDetectorOptions.Builder()
                        .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_ACCURATE)
                        .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_ALL)
                        .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)
                        .setMinFaceSize(0.15f)
                        .enableTracking()
                        .build();

        try {
            image = InputImage.fromBitmap(bitmap,0);
            detector = FaceDetection.getClient(highAccuracyOpts);
        } catch (Exception e) {
            Toast.makeText(this, "Exception Occurred", Toast.LENGTH_SHORT).show();
            return;
        }

        detector.process(image)
                .addOnSuccessListener(new OnSuccessListener<List<Face>>() {
                    @Override
                    public void onSuccess(List<Face> faces) {
                        if (faces.isEmpty()) {
                            Toast.makeText(MainActivity.this, "No Faces Detected", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        for (Face face:faces) {
                            Toast.makeText(MainActivity.this, "Face Found", Toast.LENGTH_SHORT).show();
                            float smilingProbability = face.getSmilingProbability();
                            float leftEyeOpenProbability = face.getLeftEyeOpenProbability();
                            float rightEyeOpenProbability = face.getRightEyeOpenProbability();

                            // Process face detection results as needed
                            // Example: display face attributes in TextViews or draw bounding boxes on the image
                            Toast.makeText(MainActivity.this, "Smile : " + smilingProbability + "\n" +
                                    "Left Eye : " + leftEyeOpenProbability + "\n"
                                    + "Right Eye : " + rightEyeOpenProbability, Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(MainActivity.this, "Face detection failed: ", Toast.LENGTH_SHORT).show();
                });
    }
}
