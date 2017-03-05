package com.example.shubham.eventbus_demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.Subscribe;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addFragment();
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Register this fragment to listen to event.
        GlobalBus.getBus().register(this);
    }

    private void addFragment() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragmentContainer, new Userfragment())
                .commit();
    }

    public void sendMessageToFragment(View view) {
        EditText etMessage = (EditText) findViewById(R.id.activityData);
        Events.ActivityFragmentMessage activityFragmentMessageEvent =
                new Events.ActivityFragmentMessage(String.valueOf(etMessage.getText()));

        GlobalBus.getBus().post(activityFragmentMessageEvent);
    }

    public void Next_activity(View view) {
        Intent intent = new Intent(MainActivity.this,secondActivity.class);
        startActivity(intent);
    }

    public void getDataFromDB(View view) {
        EditText etMessage = (EditText) findViewById(R.id.activityData1);
        Events.AppConstants appconstant = new Events.AppConstants(MainActivity.this);
        appconstant.CallDB(MainActivity.this, "mainActivity");

    }

    @Subscribe
    public void getMessage(Events.FragmentActivityMessage fragmentActivityMessage) {
        TextView messageView = (TextView) findViewById(R.id.message);
        TextView title = (TextView) findViewById(R.id.title);
        title.setText("Message from Fragment");
        messageView.setText(getString(R.string.message_received) + " " + fragmentActivityMessage.getMessage());
        Toast.makeText(getApplicationContext(),
                getString(R.string.message_main_activity) + " " + fragmentActivityMessage.getMessage(),
                Toast.LENGTH_SHORT).show();
        Log.d("srr",fragmentActivityMessage.getMessage());
    }

    @Subscribe
    public void getMessage(Events.AppConstants appconstants) {
        TextView messageView = (TextView) findViewById(R.id.message1);
        TextView title = (TextView) findViewById(R.id.title1);
        title.setText("Message from DB");
        messageView.setText(getString(R.string.message_received) + " " + appconstants.getResponce());
        Toast.makeText(getApplicationContext(),
                getString(R.string.message_main_activity) + " " + appconstants.getAction(), Toast.LENGTH_SHORT).show();
        Toast.makeText(getApplicationContext(),
                getString(R.string.message_main_activity) + " " + appconstants.getResponce(), Toast.LENGTH_SHORT).show();
        Log.d("srr",appconstants.getResponce());
    }


    @Override
    protected void onStop() {
        super.onStop();
        GlobalBus.getBus().unregister(this);
    }
}
