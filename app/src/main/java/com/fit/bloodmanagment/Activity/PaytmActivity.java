package com.fit.bloodmanagment.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.fit.bloodmanagment.R;

public class PaytmActivity extends AppCompatActivity {
Button payment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paytm);
        payment = (Button) findViewById(R.id.payment_paytm);
        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),WebViewActivity.class);
                startActivity(i);
            }
        });
    }
}
