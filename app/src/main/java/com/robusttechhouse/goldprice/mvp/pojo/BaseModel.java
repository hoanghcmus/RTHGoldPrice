package com.robusttechhouse.goldprice.mvp.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

/**
 * @author Nguyen Ngoc Hoang (www.hoangvnit.com)
 */
public class BaseModel implements Parcelable {
    @Expose
    private String error = "";

    @Expose
    private String message = "";

    /**
     * Need implement method in subclass
     */
    public BaseModel(Parcel in) {

    }

    public BaseModel() {
    }

    public static final Creator<BaseModel> CREATOR = new Creator<BaseModel>() {
        @Override
        public BaseModel createFromParcel(Parcel in) {
            return new BaseModel(in);
        }

        @Override
        public BaseModel[] newArray(int size) {
            return new BaseModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Need implement method in subclass
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
