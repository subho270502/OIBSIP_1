package com.sd.unitconverter;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private LinearLayout Area, Length, Mass, Time, Temp, Volume, Speed, Data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Area = findViewById(R.id.area);
        Length = findViewById(R.id.length);
        Mass = findViewById(R.id.mass);
        Time = findViewById(R.id.time);
        Temp = findViewById(R.id.temp);
        Volume = findViewById(R.id.vol);
        Speed = findViewById(R.id.speed);
        Data = findViewById(R.id.data);

        Area.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, Area.class)));

        Length.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, Length.class)));

        Mass.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, Mass.class)));

        Time.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, Time.class)));

        Temp.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, Temperature.class)));

        Volume.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, Volume.class)));

        Speed.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, Speed.class)));

        Data.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, Data.class)));

    }
}