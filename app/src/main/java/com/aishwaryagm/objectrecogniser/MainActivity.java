package com.aishwaryagm.objectrecogniser;

import android.*;
import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.aishwaryagm.objectrecogniser.listeners.ImageEventListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_ID_FOR_LOCAL_STORAGE =1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String[] permissions={Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE};
        this.requestPermissions(permissions,REQUEST_ID_FOR_LOCAL_STORAGE);

    }

    private void initializeFirebaseListener(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference imageRef = database.getReference("images");
        Log.i("INFO",String.format("Image reference %s",imageRef));
        ImageEventListener imageEventListener = new ImageEventListener();
        imageRef.addValueEventListener(imageEventListener);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_ID_FOR_LOCAL_STORAGE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Log.i("INFO","PERMISSION GRANTED");
                FirebaseApp.initializeApp(this);
                initializeFirebaseListener();
            }else{
                Log.i("INFO","PERMISSION DENIED");
            }
        }
    }
}
