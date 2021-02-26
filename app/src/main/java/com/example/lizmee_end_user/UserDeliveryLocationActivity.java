package com.example.lizmee_end_user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SharedMemory;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class UserDeliveryLocationActivity extends AppCompatActivity {

    TextView userName, userLocation;
    Button update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_delivery_location);

        userName = (TextView) findViewById(R.id.userName);
        userLocation = (TextView) findViewById(R.id.userLocation);
        update = (Button) findViewById(R.id.update);




        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // get the value which input by user in EditText
                // and convert it to string
                String str = userName.getText().toString();


                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(UserDeliveryLocationActivity.this);
                preferences.edit().putString("message_key", str).apply();


                // Create the Intent object of this class Context() to Second_activity class
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);

                // now by putExtra method put the value in key, value pair
                // key is message_key by this key we will receive the value, and put the string

                intent.putExtra("message_key", str);

                // start the Intent
                startActivity(intent);


            }
        });

    }
}