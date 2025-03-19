package com.cinthya.crudiot;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.ui.AppBarConfiguration;

import com.cinthya.crudiot.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnCRUD, btnControl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnControl = findViewById(R.id.btnControl);
        btnCRUD = findViewById(R.id.btnCRUD);

        btnCRUD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goCRUD = new Intent(MainActivity.this, crud.class);
                startActivity(goCRUD);
            }
        });

        btnControl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goControl = new Intent(MainActivity.this, control.class);
                startActivity(goControl);
            }
        });
    }
}