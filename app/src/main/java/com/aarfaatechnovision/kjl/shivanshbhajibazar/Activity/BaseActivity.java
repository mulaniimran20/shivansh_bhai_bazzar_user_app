package com.aarfaatechnovision.kjl.shivanshbhajibazar.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.aarfaatechnovision.kjl.shivanshbhajibazar.R;


public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

    }

    public abstract void initComponents();

    @Override
    public void onResume() {
        super.onResume();


    }

    @Override
    public void onClick(View v) {


    }


}