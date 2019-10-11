package edu.gvsu.cis.unitconvertor;

import android.os.Bundle;
import android.content.Intent;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.view.View;
import android.widget.AdapterView;
import java.util.ArrayList;
import java.util.Arrays;

public class SettingsActivity extends AppCompatActivity {

    ArrayAdapter<String> adapter;
    ArrayList<String> lengthList;
    ArrayList<String> volumeList;
    String current;
    String toSpinnerSelection;
    String fromSpinnerSelection;
    String prevFromUnit;
    String prevToUnit;
    int toTextSelection;
    int fromTextSelection;
    Spinner fromSpinner;
    Spinner toSpinner;

    TextView fromUnit;
    TextView toUnit;

    static final String VOLUME = "Volume";
    static final String LENGTH = "Length";

    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fromSpinner = findViewById(R.id.fromSpinner);
        toSpinner = findViewById(R.id.toSpinner);

        lengthList = new ArrayList<>(Arrays.asList("Yards", "Meters", "Miles"));
        volumeList = new ArrayList<>(Arrays.asList("Liters", "Gallons", "Quarts"));

        fab = findViewById(R.id.fab);
        fromUnit = findViewById(R.id.fromUnitTextSettings);
        toUnit = findViewById(R.id.toUnitTextSettings);

        Intent payload = getIntent();
        if (payload.hasExtra("current")) {
            current = payload.getStringExtra("current");
        }
        if (payload.hasExtra("fromUnit")) {
            prevFromUnit = payload.getStringExtra("fromUnit");
            fromUnit.setText(prevFromUnit);
        }
        if (payload.hasExtra("toUnit")) {
            prevToUnit = payload.getStringExtra("toUnit");
            toUnit.setText(prevToUnit);
        }

        switch (current) {
            case VOLUME:
                adapter = new ArrayAdapter<>(this,
                        android.R.layout.simple_spinner_item, volumeList);
                fromTextSelection = volumeList.indexOf(prevToUnit);
                toTextSelection = volumeList.indexOf(prevFromUnit);
                break;
            case LENGTH:
                adapter = new ArrayAdapter<>(this,
                        android.R.layout.simple_spinner_item, lengthList);
                fromTextSelection = lengthList.indexOf(prevToUnit);
                toTextSelection = lengthList.indexOf(prevFromUnit);
                break;
        }

        fromSpinner.setAdapter(adapter);
        fromSpinner.setSelection(fromTextSelection);

        toSpinner.setAdapter(adapter);
        toSpinner.setSelection(toTextSelection);

        fromSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                fromSpinnerSelection = (String) adapterView.getItemAtPosition(i);
                fromUnit.setText(fromSpinnerSelection);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        toSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                toSpinnerSelection = (String) adapterView.getItemAtPosition(i);
                toUnit.setText(toSpinnerSelection);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        fab.setOnClickListener(e -> {
            Intent switchToMain = new Intent(SettingsActivity.this, MainActivity.class);
            switchToMain.putExtra("toUnitText", toUnit.getText());
            switchToMain.putExtra("fromUnitText", fromUnit.getText());
            switchToMain.putExtra("current", current);

            setResult(MainActivity.UNITS_SECTION, switchToMain);
            finish();
        });

    }

}
