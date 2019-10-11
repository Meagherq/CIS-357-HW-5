package edu.gvsu.cis.unitconvertor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button calc = (Button) findViewById(R.id.Calculate);
        calc.setOnLongClickListener(v -> {System.out.println("Long Click");});


    }
}
