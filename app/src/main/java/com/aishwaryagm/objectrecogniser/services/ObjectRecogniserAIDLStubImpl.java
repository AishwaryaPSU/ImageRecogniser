package com.aishwaryagm.objectrecogniser.services;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.util.Log;

import com.aishwaryagm.objectrecogniser.CallBackInterface;
import com.aishwaryagm.objectrecogniser.MainActivity;
import com.aishwaryagm.objectrecogniser.ObjectRecogniserAIDL;
import com.aishwaryagm.objectrecogniser.ParcelableImageBytes;
import com.aishwaryagm.objectrecogniser.listeners.ImageEventListener;
import com.aishwaryagm.objectrecogniser.utility.InMemoryHashmap;
import com.aishwaryagm.objectrecogniser.utility.SingletonHashMap;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.UUID;

import static android.app.PendingIntent.getActivity;

/**
 * Created by aishwaryagm on 5/28/18.
 */

public class ObjectRecogniserAIDLStubImpl extends ObjectRecogniserAIDL.Stub {
    Context context;

    public ObjectRecogniserAIDLStubImpl(Context context){
        Log.i("INFO",String.format("Context in ObjectRecogniserAIDLStubImpl: %s ",context));
        this.context=context;

    }
    @Override
    public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {
            //do nothing
    }

    @Override
    public String[] analyzeImage(byte[] inputImageInBytes) throws RemoteException {

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference imageRecogniserStorageRef = storage.getReference();
        StorageReference imagesFolderRef = imageRecogniserStorageRef.child("images");
        StorageReference imageRef = imagesFolderRef.child(".jpg");
        UploadTask uploadImageTask = imageRef.putBytes(inputImageInBytes);
        uploadImageTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                Log.e("ERROR",String.format("Error uploading the image %s",exception.getMessage()));
                exception.printStackTrace();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                // ...
                Log.i("INFO",String.format("upload successful %s",taskSnapshot.getMetadata()));
            }
        });
        return new String[0];
    }

    @Override
    public String[] analyzeImageImpr(ParcelableImageBytes parcelableImageBytes) {
        try {
            return analyzeImage(parcelableImageBytes.getImageBytes());
        }catch(RemoteException exception){
            Log.e("ERROR",String.format("Analyzing image failed %s",exception.getMessage()));
            exception.printStackTrace();
        }
        return new String[0];
    }

    @Override
    public void analyzeImageByPath(String filePath, CallBackInterface callbackObject) throws RemoteException {
        Log.i("INFO",String.format("FilePath : %s ",filePath));
        String[] filePathTokens = filePath.split("/");
        String fileName = filePathTokens[filePathTokens.length-1].replaceAll("-","").replace(".jpg","");
        Log.i("INFO",String.format("fileName : %s , callbackObject : %s",fileName, callbackObject));
       // InMemoryHashmap.putValue(fileName, callbackObject);
        HashMap<String, CallBackInterface> singletonHashMap = SingletonHashMap.getHashMap();
        singletonHashMap.put(fileName, callbackObject);
        Log.i("INFO",String.format("SingletonHashMap %s ", SingletonHashMap.getHashMap()));
        Bitmap bitmapImage = BitmapFactory.decodeFile(filePath);
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        bitmapImage.compress(Bitmap.CompressFormat.PNG,100,byteStream);
        byte[] byteArray = byteStream.toByteArray();
        analyzeImage2(byteArray,fileName);
    }
    private void analyzeImage2(byte[] inputImageInBytes , String filename){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference imageRecogniserStorageRef = storage.getReference();
        StorageReference imagesFolderRef = imageRecogniserStorageRef.child("images");
        StorageReference imageRef = imagesFolderRef.child(filename+".jpg");
        UploadTask uploadImageTask = imageRef.putBytes(inputImageInBytes);
        uploadImageTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                Log.e("ERROR",String.format("Error uploading the image %s",exception.getMessage()));
                exception.printStackTrace();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                // ...
                Log.i("INFO",String.format("upload successful %s",taskSnapshot.getMetadata()));
            }
        });
    }
}
