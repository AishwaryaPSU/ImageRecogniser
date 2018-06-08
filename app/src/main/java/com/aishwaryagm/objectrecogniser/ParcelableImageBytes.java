package com.aishwaryagm.objectrecogniser;

import android.os.Parcel;
import android.os.Parcelable;


public class ParcelableImageBytes implements Parcelable{

    private byte[] imageBytes;

    public byte[] getImageBytes() {
        return imageBytes;
    }

    public ParcelableImageBytes(byte[] imageBytes){
        this.imageBytes=imageBytes;
    }

    protected ParcelableImageBytes(Parcel in) {
        imageBytes = in.createByteArray();
    }

    public static final Parcelable.Creator<ParcelableImageBytes> CREATOR = new Parcelable.Creator<ParcelableImageBytes>() {
        @Override
        public ParcelableImageBytes createFromParcel(Parcel in) {
            return new ParcelableImageBytes(in);
        }

        @Override
        public ParcelableImageBytes[] newArray(int size) {
            return new ParcelableImageBytes[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByteArray(imageBytes);
    }
}
