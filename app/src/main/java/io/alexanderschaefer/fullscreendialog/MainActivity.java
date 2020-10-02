package io.alexanderschaefer.fullscreendialog;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.handpoint.api.paymentsdk.dialogs.TipDialog;
import com.handpoint.api.paymentsdk.dialogs.TipDialogResultListener;
import com.handpoint.api.shared.Currency;
import com.handpoint.api.shared.TipConfiguration;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements TipDialogResultListener, Serializable {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MaterialButton button = findViewById(R.id.button);
        button.setOnClickListener(v -> openTipDialog());

    }

    private void openTipDialog() {
        ArrayList<Integer> tipPercentages = new ArrayList<Integer>();
        tipPercentages.add(10);
        tipPercentages.add(15);
        tipPercentages.add(20);
        tipPercentages.add(25);
        tipPercentages.add(30);
        tipPercentages.add(34);
        tipPercentages.add(40);
        TipConfiguration config = new TipConfiguration(
                new BigInteger("1000"),
                "ADD TIP",
                tipPercentages,
                Boolean.TRUE,
                Boolean.TRUE,
                "Thanks for your business"
        );

        this.askForTipping(config);

    }

    private void askForTipping(TipConfiguration config) {
        TipDialog.display(this.getSupportFragmentManager(), config, Currency.EUR,this);
    }

    @Override
    public void addTip(BigInteger tip) {
        Toast.makeText(getApplicationContext(),
                "Tip is " + tip,
                Toast.LENGTH_SHORT).show();
    }

}