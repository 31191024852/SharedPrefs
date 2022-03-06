package com.example.sharedprefs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private int count = 0;
    private int color;
    private TextView show;

    private final String Count_Key = "count";
    private final String Color_key = "color";

    private SharedPreferences preferences;
    private final String sharedPref = "com.example.sharedprefs";

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        show = findViewById(R.id.show);
        color = 0xFFA1A1A1;
        preferences = getSharedPreferences(sharedPref, MODE_PRIVATE);

        count = preferences.getInt(Count_Key, 0);
        show.setText(String.format("%s",count));
        color = preferences.getInt(Color_key, color);
        show.setBackgroundColor(color);

        if(savedInstanceState != null){
            count = savedInstanceState.getInt(Count_Key);
            if(count !=0){
                show.setText(String.format("%s",count));
            }

            color = savedInstanceState.getInt(Color_key);
            show.setBackgroundColor(color);
        }
    }

    public void changeBackground(View view) {
        int color =((ColorDrawable) view.getBackground()).getColor();
        show.setBackgroundColor(color);
        this.color = color;
    }

    public void countUp(View view) {
        count++;
        show.setText(String.format("%s",count));
    }

    public void reset(View view) {
        this.count =0;
        show.setText(String.format("%s",count));
        color = 0xFFA1A1A1;
        show.setBackgroundColor(color);
        SharedPreferences.Editor preferencesEditor = preferences.edit();
        preferencesEditor.clear();
        preferencesEditor.apply();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(Color_key, color);
        outState.putInt(Count_Key, count);
    }

    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences.Editor preferencesEditor =preferences.edit();
        preferencesEditor.putInt(Count_Key, count);
        preferencesEditor.putInt(Color_key, color);
        preferencesEditor.apply();
    }
}