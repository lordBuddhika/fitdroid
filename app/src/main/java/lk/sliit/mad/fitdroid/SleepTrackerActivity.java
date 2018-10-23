package lk.sliit.mad.fitdroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class SleepTrackerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep_tracker);

        Button btnUpdate = findViewById(R.id.btnUpdate);
        Intent intSlpUpdt = new Intent(SleepTrackerActivity.this, SleepTrackerUpdateActivity.class);
        startActivity(intSlpUpdt);
    }
}
