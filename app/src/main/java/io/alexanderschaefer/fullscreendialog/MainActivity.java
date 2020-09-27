package io.alexanderschaefer.fullscreendialog;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.handpoint.api.paymentsdk.dialogs.TipDialog;
import com.handpoint.api.paymentsdk.dialogs.TipDialogResultListener;
import com.handpoint.api.shared.TipConfiguration;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;

import io.reactivex.Single;

public class MainActivity extends AppCompatActivity implements TipDialogResultListener, Serializable {

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
        tipPercentages.add(45);
        tipPercentages.add(50);
        TipConfiguration config = new TipConfiguration(
                new BigInteger("1230"),
                "Add Tipping",
                tipPercentages,
                Boolean.TRUE,
                Boolean.TRUE,
                "Footer"
        );

        this.askForTipping(config);

    }

    private void askForTipping(TipConfiguration config) {
        TipDialog.display(this.getSupportFragmentManager(), config, this);
    }

    @Override
    public void addTip(int tip) {
        Toast.makeText(getApplicationContext(),
                "Tip is " + tip,
                Toast.LENGTH_SHORT).show();
    }
}