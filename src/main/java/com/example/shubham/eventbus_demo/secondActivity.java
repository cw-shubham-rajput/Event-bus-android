package com.example.shubham.eventbus_demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.Subscribe;

public class secondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

    }

    @Override
    protected void onStart() {
        super.onStart();
        // Register this fragment to listen to event.
        GlobalBus.getBus().register(this);
    }

    public void getDataFromDB(View view) {
        EditText etMessage = (EditText) findViewById(R.id.activityData1);
        Events.AppConstants appconstant = new Events.AppConstants(secondActivity.this);
        appconstant.CallDB(secondActivity.this, "secondActivity");

    }

    @Subscribe
    public void getMessage(Events.AppConstants appconstants) {
        TextView messageView = (TextView) findViewById(R.id.message1);
        TextView title = (TextView) findViewById(R.id.title1);
        title.setText("Message from DB");
        messageView.setText(getString(R.string.message_received) + " " + appconstants.getResponce());
        Toast.makeText(getApplicationContext(),
                getString(R.string.message_second_activity) + " " + appconstants.getAction(), Toast.LENGTH_SHORT).show();
        Toast.makeText(getApplicationContext(),
                getString(R.string.message_second_activity) + " " + appconstants.getResponce(), Toast.LENGTH_SHORT).show();
        Log.d("srr",appconstants.getResponce());
    }

    @Override
    protected void onStop() {
        super.onStop();
        GlobalBus.getBus().unregister(this);
    }
}
