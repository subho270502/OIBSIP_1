package com.sd.unitconverter;import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.util.HashMap;
import java.util.Map;

public class Area extends AppCompatActivity {

    private EditText editFrom, editTo;
    private TextView txtfromUnit, txttoUnit;
    private RelativeLayout rlfromunit, rltounit;
    private CardView convertBtn;

    private final Map<String, Double> areaUnits = new HashMap<>();
    {
        areaUnits.put("Square Kilometer (km²)", 1.0);
        areaUnits.put("Square Meter (m²)", 1_000_000.0);
        areaUnits.put("Square Decimeter (dm²)", 100_000_000.0);
        areaUnits.put("Square Centimeter (cm²)", 1_000_000_000.0);
        areaUnits.put("Square Millimeter (mm²)", 1_000_000_000_000.0);
        areaUnits.put("Square Mile (mi²)", 2.589988110336);
        areaUnits.put("Square Yard (yd²)", 1_195_990.04630108);
        areaUnits.put("Square Foot (ft²)", 10_763_910.41671);
        areaUnits.put("Square Inch (in²)", 1.5500031e+9);
        areaUnits.put("Hectare (ha)", 100.0);
        areaUnits.put("Acre (ac)", 247.105381467165);
    }

    private String fromUnit = "Square Kilometer (km²)";
    private String toUnit = "Square Meter (m²)";

    @SuppressLint({"MissingInflatedId", "DefaultLocale"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculation); //Make sure this matches your XML filename

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

        rlfromunit.setOnClickListener(v -> showUnitSelectionDialog(txtfromUnit, true));
        rltounit.setOnClickListener(v -> showUnitSelectionDialog(txttoUnit, false));

        convertBtn.setOnClickListener(v -> {
            String input = editFrom.getText().toString();
            if (input.isEmpty()) {
                editFrom.setError("Please enter a value");
            } else {
                double inputValue = Double.parseDouble(input);
                double result = convertArea(inputValue, fromUnit, toUnit);
                editTo.setText(String.format("%.5f", result));
            }
        });
    }

    private void showUnitSelectionDialog(TextView unitTextView, boolean isFromUnit) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Area.this);
        builder.setTitle("Choose Unit");
        builder.setItems(areaUnits.keySet().toArray(new String[0]), (dialog, which) -> {
            String selectedUnit = areaUnits.keySet().toArray(new String[0])[which];
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

    private double convertArea(double value,String fromUnit, String toUnit) {
        double fromFactor = areaUnits.getOrDefault(fromUnit, 1.0);
        double toFactor = areaUnits.getOrDefault(toUnit, 1.0);
        return value * (toFactor / fromFactor);
    }
}