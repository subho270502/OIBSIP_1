package com.sd.unitconverter;

import android.annotation.SuppressLint;import android.os.Bundle;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.util.HashMap;
import java.util.Map;

public class Data extends AppCompatActivity {
    private EditText editFrom, editTo;
    private TextView txtfromUnit, txttoUnit;
    private RelativeLayout rlfromunit, rltounit;
    private CardView convertBtn;

    private final Map<String, Double> dataUnits = new HashMap<>();
    {
        // Conversion factors relative to Byte (B)
        dataUnits.put("PetaByte (PB)", 1.0);
        dataUnits.put("Terabyte (TB)", 1024.0);
        dataUnits.put("Gigabyte (GB)", 1048576.0);
        dataUnits.put("Megabyte (MB)", 1073741824.0);
        dataUnits.put("Kilobyte (KB)", 1099511627776.0);
        dataUnits.put("Byte (B)", 1125899906842624.0);
    }

    private String fromUnit = "Megabyte (MB)";
    private String toUnit = "Kilobyte (KB)";

    @SuppressLint("MissingInflatedId")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data);editFrom = findViewById(R.id.et_from);
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
                double result = convertData(inputValue, fromUnit, toUnit);
                editTo.setText(String.format("%.5f", result));
            }
        });
    }

    // Dialog to select units
    private void showUnitSelectionDialog(TextView unitTextView, boolean isFromUnit) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Data.this);
        builder.setTitle("Choose Unit");
        builder.setItems(dataUnits.keySet().toArray(new String[0]), (dialog, which) -> {
            String selectedUnit = dataUnits.keySet().toArray(new String[0])[which];
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

    private double convertData(double value, String fromUnit, String toUnit) {
        double fromFactor = dataUnits.getOrDefault(fromUnit, 1.0);
        double toFactor = dataUnits.getOrDefault(toUnit, 1.0);
        return value * (toFactor / fromFactor);
    }
}