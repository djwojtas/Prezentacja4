package pl.edu.agh.prezentacja4;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    IntentFilter intentFilter = new IntentFilter("android.intent.action.AIRPLANE_MODE");

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            boolean isAirplaneModeOn = intent.getBooleanExtra("state", false);
            String displayText = isAirplaneModeOn ? "włączony" : "wyłączony";
            setText(displayText);
        }
    };

    private void setText(String text) {
        TextView textView = (TextView) findViewById(R.id.enabled);
        textView.setText(text);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.registerReceiver(receiver, intentFilter);
    }
}
