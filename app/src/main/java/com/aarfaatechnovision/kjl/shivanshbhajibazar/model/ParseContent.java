package com.aarfaatechnovision.kjl.shivanshbhajibazar.model;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.aarfaatechnovision.kjl.shivanshbhajibazar.Activity.HomeActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ParseContent {
    private final String KEY_SUCCESS = "status";
    private final String KEY_MSG = "message";
    private Activity activity;

    ArrayList<HashMap<String, String>> arraylist;

    public ParseContent(Activity activity) {
        this.activity = activity;
    }

    public boolean isSuccess(String response) {

        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.optString(KEY_SUCCESS).equals("true")) {
                return true;
            } else {

                return false;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getErrorCode(String response) {

        try {
            JSONObject jsonObject = new JSONObject(response);
            return jsonObject.getString(KEY_MSG);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "No data";
    }

    public String getURL(String response) {
        String url="https://shivanshbhajibazzar.com/api/paymentimages/1582267537.jpg";
        try {
            JSONObject jsonObject = new JSONObject(response);
            jsonObject.toString().replace("\\\\","");
            if (jsonObject.getString(KEY_SUCCESS).equals("true")) {
                Toast.makeText(activity, "Registration Successful, We will approve you soon...", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(activity, HomeActivity.class);
                activity.startActivity(i);
                activity.finish();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return url;
    }
}
