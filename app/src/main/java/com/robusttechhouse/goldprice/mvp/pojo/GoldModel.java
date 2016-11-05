package com.robusttechhouse.goldprice.mvp.pojo;

import android.os.Parcel;

import com.google.gson.annotations.Expose;
import com.robusttechhouse.goldprice.common.Setting;
import com.robusttechhouse.goldprice.utils.LogUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Nguyen Ngoc Hoang (www.hoangvnit.com)
 */
public class GoldModel extends BaseModel implements Comparable<GoldModel> {
    @Expose
    private float amount;

    @Expose
    private Date date;

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(this.amount);
        dest.writeString(this.date.toString());
    }

    protected GoldModel(Parcel in) {
        this.amount = in.readFloat();
        DateFormat df = new SimpleDateFormat(Setting.GSON_DATE_FORMAT);
        try {
            this.date = df.parse(in.readString());
        } catch (ParseException e) {
            LogUtils.e("Read data from Parcel failed: " + e.getMessage());
        }
    }

    public static final Creator<GoldModel> CREATOR = new Creator<GoldModel>() {
        @Override
        public GoldModel createFromParcel(Parcel source) {
            return new GoldModel(source);
        }

        @Override
        public GoldModel[] newArray(int size) {
            return new GoldModel[size];
        }
    };

    @Override
    public int compareTo(GoldModel another) {
        int result = date.compareTo(another.getDate());
        if (result == 0)
            return 0;
        else if (result > 0)
            return -1;
        else return 1;
    }
}
