package com.example.logreg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ChangeAfterLogAcvitity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_after_log_acvitity);
    }
    public void GoDriver(View view)
    {
        startActivity(new Intent(this,DriverActivity.class));
    }

    public void GoPassenger(View view)
    {
        startActivity(new Intent(this,PassengerActivity.class));
    }
}