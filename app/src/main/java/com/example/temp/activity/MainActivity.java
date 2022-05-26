package com.example.temp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.repair.Repair;
import com.example.temp.R;
import com.example.temp.Test;
import com.example.temp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private final String TAG="MainActivity";

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        test("22");
        new Test().temp("123");

        Log.e("qqq","seven");

    }

    @Repair
    private String test(String str){
        Log.e("qqq",str);
        return str;
    }

    private boolean hasRepairMethods(){
        return true;
    }

    public void onClick(View view) {
        toActivity(SecondActivity.class);
    }

    private void toActivity(Class act){
        Intent intent=new Intent(MainActivity.this,act);
        Bundle bundle=new Bundle();
        bundle.putString("key",null);
        intent.putExtra("bundle",bundle);
        startActivity(intent);
    }







}