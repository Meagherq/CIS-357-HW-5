package edu.gvsu.cis.unitconvertor;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.content.Intent;

public class MainActivity extends AppCompatActivity implements View.OnFocusChangeListener {

    LinearLayout mainLayout;

    EditText calcFromField;
    EditText calcToField;
    TextView calcFromText;
    TextView calcToText;

    Button calc;
    Button clear;
    Button mode;

    static final String VOLUME = "Volume";
    static final String LENGTH = "Length";
    public static final int UNITS_SECTION = 1;
    public UnitsConverter.LengthUnits fromLen = UnitsConverter.LengthUnits.Yards;
    public UnitsConverter.LengthUnits toLen = UnitsConverter.LengthUnits.Meters;
    public UnitsConverter.VolumeUnits fromVol = UnitsConverter.VolumeUnits.Liters;
    public UnitsConverter.VolumeUnits toVol = UnitsConverter.VolumeUnits.Gallons;
    public String current = "Length";



    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch(v.getId()){
            case R.id.calcFromField:
                if(!hasFocus) {
                    hideKeyboard(v);

                }
                else {
                    calcToField.setText("");
                }
                break;
            case R.id.calcToField:
                if(hasFocus) {
                    calcFromField.setText("");
                }
                else{
                    hideKeyboard(v);
                }
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent payload = getIntent();
        try{
            calcFromText.setText(payload.getStringExtra("fromUnit"));
            calcToText.setText(payload.getStringExtra("toUnit"));

        } catch (Exception e){
            System.out.println("Error " + e.getMessage());
        }

        mainLayout = findViewById(R.id.calcMainLayout);

        calcFromField = findViewById(R.id.calcFromField);
        calcToField = findViewById(R.id.calcToField);
        calcFromText = findViewById(R.id.calcFromText);
        calcToText = findViewById(R.id.calcToText);

        calc = findViewById(R.id.Calculate);
        clear = findViewById(R.id.Clear);
        mode = findViewById(R.id.Mode);

        calcFromField.setOnFocusChangeListener(this);
        calcToField.setOnFocusChangeListener(this);

        calc.setOnClickListener(v -> {
            if(current.equals("Length")) {
                if(!calcFromField.getText().toString().equals("") ^ !calcToField.getText().toString().equals("")) {
                    if (!calcFromField.getText().toString().equals("") && calcToField.getText().toString().equals("")){
                        Double resFrom = Double.parseDouble(calcFromField.getText().toString());
                        toLen = UnitsConverter.LengthUnits.valueOf(calcToText.getText().toString());
                        fromLen = UnitsConverter.LengthUnits.valueOf(calcFromText.getText().toString());
                        calcToField.setText(Double.toString(UnitsConverter.convert(resFrom, fromLen, toLen)));
                    }
                    if(!calcToField.getText().toString().equals("") && calcFromField.getText().toString().equals("")){
                        Double resTo = Double.parseDouble(calcToField.getText().toString());
                        toLen = UnitsConverter.LengthUnits.valueOf(calcToText.getText().toString());
                        fromLen = UnitsConverter.LengthUnits.valueOf(calcFromText.getText().toString());
                        calcFromField.setText(Double.toString(UnitsConverter.convert(resTo, toLen, fromLen)));
                    }
                }
            }
            else {
                if(!calcFromField.getText().toString().equals("") ^ !calcToField.getText().toString().equals("")) {
                    if (!calcFromField.getText().toString().equals("") && calcToField.getText().toString().equals("")){
                        Double resFrom = Double.parseDouble(calcFromField.getText().toString());
                        toVol = UnitsConverter.VolumeUnits.valueOf(calcToText.getText().toString());
                        fromVol = UnitsConverter.VolumeUnits.valueOf(calcFromText.getText().toString());
                        calcToField.setText(Double.toString(UnitsConverter.convert(resFrom, fromVol, toVol)));
                    }
                    if(!calcToField.getText().toString().equals("") && calcFromField.getText().toString().equals("")){
                        Double resTo = Double.parseDouble(calcToField.getText().toString());
                        toVol = UnitsConverter.VolumeUnits.valueOf(calcToText.getText().toString());
                        fromVol = UnitsConverter.VolumeUnits.valueOf(calcFromText.getText().toString());
                        calcFromField.setText(Double.toString(UnitsConverter.convert(resTo, toVol, fromVol)));
                    }
                }
            }

        });

        clear.setOnClickListener(v -> {
            calcFromField.setText(null);
            calcToField.setText(null);
        });

        mode.setOnClickListener(e -> {
            switch (current) {
                case LENGTH:
                    current = VOLUME;
                    calcFromText.setText("Gallons");
                    calcToText.setText("Liters");
                    break;
                case VOLUME:
                    current = LENGTH;
                    calcFromText.setText("Yards");
                    calcToText.setText("Meters");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.navbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem settingsItem) {
        switch (settingsItem.getItemId()) {
            case R.id.settings:
                Intent switchToSettings = new Intent(MainActivity.this, SettingsActivity.class);
                switchToSettings.putExtra("fromUnit", calcFromText.getText());
                switchToSettings.putExtra("toUnit", calcToText.getText());
                switchToSettings.putExtra("current", current);

                startActivityForResult(switchToSettings, UNITS_SECTION);
        }
        return super.onOptionsItemSelected(settingsItem);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == UNITS_SECTION) {

            calcFromText.setText(data.getStringExtra("fromUnitText"));
            calcToText.setText(data.getStringExtra("toUnitText"));
        }

    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
