package com.sd.unitconverter;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.util.HashMap;
import java.util.Map;

public class Time extends AppCompatActivity {

    private EditText editFrom, editTo;
    private TextView txtfromUnit, txttoUnit;
    private RelativeLayout rlfromunit, rltounit;
    private CardView convertBtn;

    private final Map<String, Double> timeUnits = new HashMap<>();
    {
        // Conversion factors relative to Second (sec)
        timeUnits.put("Second (sec)", 1.0);
        timeUnits.put("Minute (min)", 0.0166666667);
        timeUnits.put("Hour (hr)",0.000277777778);
        timeUnits.put("Day (d)", 1.15740741e-5);
        timeUnits.put("Week (wk)", 1.65343913e-6);
        timeUnits.put("Year (yr)", 3.1709792e-8);
        timeUnits.put("Microsecond (µs)", 1000000.0);
        timeUnits.put("Millisecond (ms)", 1000.0);
    }

    private String fromUnit = "Minute (min)";
    private String toUnit = "Second (sec)";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.time);

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
                double result = convertTime(inputValue, fromUnit, toUnit);
                editTo.setText(String.format("%.5f", result));
            }
        });
    }

    // Dialog to select units
    private void showUnitSelectionDialog(TextView unitTextView, boolean isFromUnit) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Time.this);
        builder.setTitle("Choose Unit");
        builder.setItems(timeUnits.keySet().toArray(new String[0]), (dialog, which) -> {
            String selectedUnit = timeUnits.keySet().toArray(new String[0])[which];
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

    private double convertTime(double value, String fromUnit, String toUnit) {
        double fromFactor = timeUnits.getOrDefault(fromUnit, 1.0);
        double toFactor = timeUnits.getOrDefault(toUnit, 1.0);
        return value * (fromFactor / toFactor);
    }
}