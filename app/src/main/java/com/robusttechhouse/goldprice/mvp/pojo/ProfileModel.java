package com.robusttechhouse.goldprice.mvp.pojo;

import android.os.Parcel;

/**
 * @author Nguyen Ngoc Hoang (www.hoangvnit.com)
 */
public class ProfileModel extends BaseModel {
    private String avatar;
    private String fullName;
    private String email;

    public ProfileModel() {
    }

    public ProfileModel(String avatar, String fullName, String email) {
        this.avatar = avatar;
        this.fullName = fullName;
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String username) {
        this.fullName = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.avatar);
        dest.writeString(this.fullName);
        dest.writeString(this.email);
    }

    protected ProfileModel(Parcel in) {
        this.avatar = in.readString();
        this.fullName = in.readString();
        this.email = in.readString();
    }

    public static final Creator<ProfileModel> CREATOR = new Creator<ProfileModel>() {
        @Override
        public ProfileModel createFromParcel(Parcel source) {
            return new ProfileModel(source);
        }

        @Override
        public ProfileModel[] newArray(int size) {
            return new ProfileModel[size];
        }
    };
}
