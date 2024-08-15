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

public class Volume extends AppCompatActivity {
    private EditText editFrom, editTo;
    private TextView txtfromUnit, txttoUnit;
    private RelativeLayout rlfromunit, rltounit;
    private CardView convertBtn;

    private final Map<String, Double> volumeUnits = new HashMap<>();
    {
        // Conversion factors relative to Cubic meter (m³)
        volumeUnits.put("Cubic meter (m³)", 1.0);
        volumeUnits.put("Cubic centimeter (cm³)", 1e-6);
        volumeUnits.put("Cubic millimeter (mm³)", 1e-9);
        volumeUnits.put("Cubic decimeter (dm³)", 0.001);
        volumeUnits.put("Cubic inch (in³)", 1.6387064e-5);
        volumeUnits.put("Cubic foot (ft³)", 0.0283168466);
        volumeUnits.put("Cubic yard (yd³)", 0.764554858);
        volumeUnits.put("Hectoliter (hl)", 0.1);
        volumeUnits.put("Liter (l)", 0.001);
        volumeUnits.put("Deciliter (dl)", 1e-4);
        volumeUnits.put("Centiliter (cl)", 1e-5);
        volumeUnits.put("Milliliter (ml)", 1e-6);
    }

    private String fromUnit = "Cubic meter (m³)";
    private String toUnit = "Cubic centimeter (cm³)";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vol);

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
                double result = convertVolume(inputValue, fromUnit, toUnit);
                editTo.setText(String.format("%.5f", result));
            }
        });
    }

    // Dialog to select units
    private void showUnitSelectionDialog(TextView unitTextView, boolean isFromUnit) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Volume.this);
        builder.setTitle("Choose Unit");
        builder.setItems(volumeUnits.keySet().toArray(new String[0]), (dialog, which) -> {
            String selectedUnit = volumeUnits.keySet().toArray(new String[0])[which];
            String abbreviation = selectedUnit.substring(selectedUnit.indexOf("(") + 1, selectedUnit.indexOf(")"));
            unitTextView.setText(abbreviation);
            if (isFromUnit) {
                fromUnit = selectedUnit;
            } else {
                toUnit = selectedUnit;}
        });
        builder.show();
    }

    private double convertVolume(double value, String fromUnit, String toUnit) {
        double fromFactor = volumeUnits.getOrDefault(fromUnit, 1.0);
        double toFactor = volumeUnits.getOrDefault(toUnit, 1.0);
        return value * (fromFactor / toFactor);
    }
}