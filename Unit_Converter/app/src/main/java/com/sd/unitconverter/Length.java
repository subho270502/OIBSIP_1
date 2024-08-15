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

public class Length extends AppCompatActivity {

    private EditText editFrom, editTo;
    private TextView txtfromUnit, txttoUnit;
    private RelativeLayout rlfromunit, rltounit;
    private CardView convertBtn;

    private final Map<String, Double> lengthUnits = new HashMap<>();
    {
        // Conversion factors relative to Meter (m)
        lengthUnits.put("Kilometer (km)", 1000.0);
        lengthUnits.put("Meter (m)", 1.0);
        lengthUnits.put("Decimeter (dm)", 0.1);
        lengthUnits.put("Centimeter (cm)", 0.01);
        lengthUnits.put("Millimeter (mm)", 0.001);
        lengthUnits.put("Micrometer (μm)", 1e-6);
        lengthUnits.put("Nanometer (nm)", 1e-9);
        lengthUnits.put("Pico meter (pm)", 1e-12);
        lengthUnits.put("Mile (mi)", 1609.344);
        lengthUnits.put("Yard (yd)", 0.9144);
        lengthUnits.put("Foot (ft)", 0.3048);
        lengthUnits.put("Inch (in)", 0.0254);
    }

    private String fromUnit = "Meter (m)";
    private String toUnit = "Centimeter (cm)";

    @SuppressLint("MissingInflatedId")
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.length);

        editFrom= findViewById(R.id.et_from);
        editTo = findViewById(R.id.et_to);
        txtfromUnit = findViewById(R.id.tv_fromUnit);
        txttoUnit = findViewById(R.id.tv_toUnit);
        rlfromunit = findViewById(R.id.rl_fromUnit);
        rltounit = findViewById(R.id.rl_toUnit);
        convertBtn = findViewById(R.id.convert_Btn);

        txtfromUnit.setText(fromUnit.substring(fromUnit.indexOf("(") + 1, fromUnit.indexOf(")")));
        txttoUnit.setText(toUnit.substring(toUnit.indexOf("(") + 1, toUnit.indexOf(")")));

        rlfromunit.setOnClickListener(v -> showUnitSelectionDialog(txtfromUnit, true));
        rltounit.setOnClickListener(v -> showUnitSelectionDialog(txttoUnit, false));


        convertBtn.setOnClickListener(v -> {
            String input = editFrom.getText().toString();
            if (input.isEmpty()) {
                editFrom.setError("Please enter a value");
            } else {
                double inputValue = Double.parseDouble(input);
                double result = convertLength(inputValue, fromUnit, toUnit);
                editTo.setText(String.format("%.5f", result));
            }
        });
    }

    // Dialog to select units
    private void showUnitSelectionDialog(TextView unitTextView, boolean isFromUnit) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Length.this);
        builder.setTitle("Choose Unit");
        builder.setItems(lengthUnits.keySet().toArray(new String[0]), (dialog, which) -> {
            String selectedUnit = lengthUnits.keySet().toArray(new String[0])[which];
            String abbreviation = selectedUnit.substring(selectedUnit.indexOf("(") + 1, selectedUnit.indexOf(")"));
            unitTextView.setText(abbreviation);
            if (isFromUnit) {
                fromUnit = selectedUnit;
            } else {
                toUnit = selectedUnit;
            }
        });
        builder.show();}

    private double convertLength(double value, String fromUnit, String toUnit) {
        double fromFactor = lengthUnits.getOrDefault(fromUnit, 1.0);
        double toFactor = lengthUnits.getOrDefault(toUnit, 1.0);
        return value * (fromFactor / toFactor);
    }
}