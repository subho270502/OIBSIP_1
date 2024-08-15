package com.sd.unitconverter;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class Temperature extends AppCompatActivity {
    private EditText editFrom, editTo;
    private TextView txtfromUnit, txttoUnit;
    private RelativeLayout rlfromunit, rltounit;
    private CardView convertBtn;

    private final Map<String, Function<Double, Double>> toCelsius = new HashMap<>();
    {
        toCelsius.put("Celsius (°C)", celsius -> celsius);
        toCelsius.put("Fahrenheit (°F)", fahrenheit -> (fahrenheit - 32) * 5 /9);
        toCelsius.put("Kelvin (K)", kelvin -> kelvin - 273.15);
    }

    private final Map<String, Function<Double, Double>> fromCelsius = new HashMap<>();
    {
        fromCelsius.put("Celsius (°C)", celsius -> celsius);
        fromCelsius.put("Fahrenheit (°F)", celsius -> celsius * 9 / 5 + 32);
        fromCelsius.put("Kelvin (K)", celsius -> celsius + 273.15);
    }

    private String fromUnit = "Celsius (°C)";
    private String toUnit = "Fahrenheit (°F)";

    @SuppressLint("MissingInflatedId")
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.temperature);

        editFrom = findViewById(R.id.et_from);
        editTo = findViewById(R.id.et_to);
        txtfromUnit = findViewById(R.id.tv_fromUnit);
        txttoUnit = findViewById(R.id.tv_toUnit);
        rlfromunit = findViewById(R.id.rl_fromUnit);
        rltounit = findViewById(R.id.rl_toUnit);
        convertBtn = findViewById(R.id.convert_Btn);

        // Set initial unit abbreviations
        txtfromUnit.setText(fromUnit.substring(fromUnit.indexOf("(") + 1, fromUnit.indexOf(")")));
        txttoUnit.setText(toUnit.substring(toUnit.indexOf("(") + 1, toUnit.indexOf(")")));

        // Set up click listeners for unit selection
        rlfromunit.setOnClickListener(v -> showUnitSelectionDialog(txtfromUnit, true));
        rltounit.setOnClickListener(v -> showUnitSelectionDialog(txttoUnit, false));

        // Conversion button click listener
        convertBtn.setOnClickListener(v -> {
            String input = editFrom.getText().toString();
            if (input.isEmpty()) {
                editFrom.setError("Please enter a value");
            } else {
                double inputValue = Double.parseDouble(input);
                double result = convertTem(inputValue, fromUnit, toUnit);
                editTo.setText(String.format("%.3f", result));
            }
        });
    }

    // Dialog to select units
    private void showUnitSelectionDialog(TextView unitTextView, boolean isFromUnit) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Temperature.this);
        builder.setTitle("Choose Unit");
        String[] unitKeys = toCelsius.keySet().toArray(new String[0]);
        builder.setItems(unitKeys, (dialog, which) -> {
            String selectedUnit = unitKeys[which];
            String abbreviation = selectedUnit.substring(selectedUnit.indexOf("(") + 1, selectedUnit.indexOf(")"));
            unitTextView.setText(abbreviation);
            if (isFromUnit) {
                fromUnit = selectedUnit;
            } else {
                toUnit = selectedUnit;
            }
        });
        builder.show();
    }

    private double convertTem(double value, String fromUnit, String toUnit) {
        // Convert to Celsius first
        double celsiusValue = toCelsius.getOrDefault(fromUnit, celsius -> 0.0).apply(value);

        // Then convert from Celsiusto the target unit
        return fromCelsius.getOrDefault(toUnit, celsius -> 0.0).apply(celsiusValue);
    }
}