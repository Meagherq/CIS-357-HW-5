package edu.gvsu.cis.unitconvertor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnFocusChangeListener {

    EditText calcFromField;
    EditText calcToField;
    TextView calcFromText;
    TextView calcToText;

    Button calc;
    Button clear;
    Button mode;

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch(v.getId()){
            case R.id.calcFromField:
                calcToField.setText("");
                break;
            case R.id.calcToField:
                calcFromField.setText("");
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
            if(!calcFromField.getText().toString().equals("") ^ !calcToField.getText().toString().equals("")) {
                if (!calcFromField.getText().toString().equals("") && calcToField.getText().toString().equals("")){
                    Double resFrom = Double.parseDouble(calcFromField.getText().toString());
                    calcToField.setText(Double.toString(UnitsConverter.convert(resFrom, UnitsConverter.LengthUnits.Meters, UnitsConverter.LengthUnits.Yards)));
                }
                if(!calcToField.getText().toString().equals("") && calcFromField.getText().toString().equals("")){
                    Double resTo = Double.parseDouble(calcToField.getText().toString());
                    calcFromField.setText(Double.toString(UnitsConverter.convert(resTo, UnitsConverter.LengthUnits.Meters, UnitsConverter.LengthUnits.Yards)));
                }
            }
        });

        clear.setOnClickListener(v -> {
            calcFromField.setText(null);
            calcToField.setText(null);
        });

        mode.setOnClickListener(v -> {
            System.out.println("Click");
        });
    }
}
