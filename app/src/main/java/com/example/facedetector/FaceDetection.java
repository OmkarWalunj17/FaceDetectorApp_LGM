package com.example.facedetector;

import android.app.Application;

import com.google.firebase.FirebaseApp;

public class FaceDetection extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this);
    }
}



























































// Prepare the input image
//    FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(bitmap);
//
//    // Run the face detector
//    FirebaseVisionFaceDetector detector = FirebaseVision.getInstance()
//            .getVisionFaceDetector(highAccuracyOpts);
//    Task<List<FirebaseVisionFace>> result =
//            detector.detectInImage(image)
//                    .addOnSuccessListener(faces -> {
//                        // Task completed successfully
//                        // Process the detected faces
//                    })
//                    .addOnFailureListener(e -> {
//                        // Handle any errors
//                    });

//    private void detectFace(Bitmap bitmap) {
//        // High-accuracy landmark detection and face classification
//        FaceDetectorOptions highAccuracyOpts =
//                new FaceDetectorOptions.Builder()
//                        .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_ACCURATE)
//                        .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_ALL)
//                        .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)
//                        .setMinFaceSize(0.15f)
//                        .enableTracking()
//                        .build();

//        try {
//            //To create an InputImage object from a Bitmap object
//            //The image is represented by a Bitmap object together with rotation degrees.
//            image = InputImage.fromBitmap(bitmap,0);
//
//            //Get an instance of FaceDetector
//            detector = FaceDetection.getClient(highAccuracyOpts);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        detector.process(image).addOnSuccessListener(new OnSuccessListener<List<Face>>() {
//            @Override
//            public void onSuccess(List<Face> faces) {
//                String resultText = "";
//
//                //Integer i if there are more than 1 faces in the List
//                int i = 1;
//
//                //Looping thru the list named faces using for each loop
//                for (Face face:faces) {
//                    resultText = resultText.concat("\n"+i+".")
//                            .concat("\nSmile: "+face.getSmilingProbability()*100+"%")
//                            .concat("\nLeft Eye: "+face.getLeftEyeOpenProbability()*100+"%")
//                            .concat("\nRight Eye: "+face.getRightEyeOpenProbability()*100+"%");
//                    i++;
//                }
//
//                if (faces.size() == 0)
//                    Toast.makeText(MainActivity.this, "NO FACES", Toast.LENGTH_SHORT).show();
//                else{
//                    Bundle bundle = new Bundle();
//                    bundle.putString(com.example.facedetector.FaceDetection.RESULT_TEXT,resultText);
//                    // Assuming you have a Context object named 'context' and a Bitmap object named 'bitmap'
//                    ResultDialog resultDialog = new ResultDialog(context, bitmap);
//
//                    resultDialog.setArguments(bundle);
//                    resultDialog.setCancelable(false);
//                    resultDialog.show(getSupportFragmentManager(), com.example.facedetector.FaceDetection.RESULT_DIALOG);
//                }
//            }
//        });
//    }