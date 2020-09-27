package io.alexanderschaefer.fullscreendialog;

import android.os.Bundle;

import com.google.android.material.button.MaterialButton;
import com.handpoint.api.paymentsdk.dialogs.TipDialog;
import com.handpoint.api.shared.TipConfiguration;

import androidx.appcompat.app.AppCompatActivity;

import java.math.BigInteger;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MaterialButton button = findViewById(R.id.button);
        button.setOnClickListener(v -> openDialog());
    }

    private void openDialog() {
        ArrayList<Integer> tipPercentages = new ArrayList<Integer>();
        tipPercentages.add(10);
        tipPercentages.add(15);
        tipPercentages.add(25);
        TipConfiguration config = new TipConfiguration(
                new BigInteger("100"),
                "Add Tipping",
                tipPercentages,
                Boolean.TRUE,
                Boolean.TRUE,
                "Footer"

        );
        TipDialog.display(getSupportFragmentManager(), config);
    }

}