package com.allen.mapple;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class User implements Parcelable {

    protected String email;
    protected String authToken;

    public User(String email, String authToken) {
        this.email = email;
        this.authToken = authToken;
    }

    private User(Parcel in) {
        email  = in.readString();
        authToken = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<User> CREATOR
            = new Parcelable.Creator<User>() {
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(email);
        out.writeString(authToken);
    }

    public JSONObject toJson() {
        JSONObject user = new JSONObject();
        try {
            user.put("email", email);
            user.put("authentication_token", authToken);
            return user;
        } catch (JSONException e) {
            return null;
        }
    }

    public static User fromJson(JSONObject object) {
        try {
            User user = new User(object.getString("email"),
                    object.getString("authentication_token"));
            return user;
        } catch (JSONException e) {
            return null;
        }
    }

    @Override
    public String toString() {
        return String.format("User(%s)", email);
    }
}
