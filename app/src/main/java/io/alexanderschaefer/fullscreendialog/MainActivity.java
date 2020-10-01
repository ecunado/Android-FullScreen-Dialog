package io.alexanderschaefer.fullscreendialog;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.handpoint.api.paymentsdk.dialogs.PadDialog;
import com.handpoint.api.paymentsdk.dialogs.PadDialogResultListener;
import com.handpoint.api.paymentsdk.dialogs.TipDialog;
import com.handpoint.api.paymentsdk.dialogs.TipDialogResultListener;
import com.handpoint.api.shared.Currency;
import com.handpoint.api.shared.TipConfiguration;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements PadDialogResultListener, TipDialogResultListener, Serializable {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MaterialButton button = findViewById(R.id.button);
        button.setOnClickListener(v -> openTipDialog());

        MaterialButton button2 = findViewById(R.id.button2);
        button2.setOnClickListener(v -> openPadDialog());
    }

    private void openTipDialog() {
        ArrayList<Integer> tipPercentages = new ArrayList<Integer>();
        tipPercentages.add(10);
        tipPercentages.add(15);
        tipPercentages.add(25);
        TipConfiguration config = new TipConfiguration(
                new BigInteger("1230"),
                "Add tip",
                tipPercentages,
                Boolean.TRUE,
                Boolean.TRUE,
                "Thanks for your business"
        );

        this.askForTipping(config);

    }

    private void openPadDialog() {
        PadDialog.display(this.getSupportFragmentManager(), Currency.EUR, this);
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

    @Override
    public void amount(int amount) {
        Toast.makeText(getApplicationContext(),
                "Amount is " + amount,
                Toast.LENGTH_SHORT).show();
    }
}