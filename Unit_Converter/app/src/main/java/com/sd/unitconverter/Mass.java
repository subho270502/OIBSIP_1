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

public class Mass extends AppCompatActivity{
    private EditText editFrom, editTo;
    private TextView txtfromUnit, txttoUnit;
    private RelativeLayout rlfromunit, rltounit;
    CardView convertBtn;

    private final Map<String, Double> massUnits = new HashMap<>();
    {
        // Conversion factors relative to Kilogram (kg)
        massUnits.put("Kilogram (kg)", 1.0);
        massUnits.put("Tonne (t)", 0.001);
        massUnits.put("Gram (g)", 1000.0);
        massUnits.put("Milligram (mg)", 1000000.0);
        massUnits.put("Pound (lb)", 2.20462262);
        massUnits.put("Ounce (oz)", 35.2739619);
        massUnits.put("Quintal (q)", 0.01);
        massUnits.put("Micrograms (μg)", 1e9);
    }

    private String fromUnit = "Kilogram (kg)";
    private String toUnit = "Gram (g)";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mass);

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
                double result = convertMass(inputValue, fromUnit, toUnit);
                editTo.setText(String.format("%.5f", result));
            }
        });
    }

    private void showUnitSelectionDialog(TextView unitTextView, boolean isFromUnit) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Mass.this);
        builder.setTitle("Choose Unit");
        builder.setItems(massUnits.keySet().toArray(new String[0]), (dialog, which) -> {
            String selectedUnit = massUnits.keySet().toArray(new String[0])[which];
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

    private double convertMass(double value, String fromUnit, String toUnit) {
        double fromFactor = massUnits.getOrDefault(fromUnit, 1.0);
        double toFactor = massUnits.getOrDefault(toUnit, 1.0);
        return value * (fromFactor / toFactor);
    }
}