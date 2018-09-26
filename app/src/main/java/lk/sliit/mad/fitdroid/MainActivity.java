package lk.sliit.mad.fitdroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton btnAlarmLauncher = findViewById(R.id.btnAlarm);
        ImageButton btnSleepLauncher = findViewById(R.id.btnSleepTracker);
        ImageButton btnRouteLauncher = findViewById(R.id.btnRouteTracker);
        ImageButton btnWeightLauncher = findViewById(R.id.btnWeightTracker);

        btnRouteLauncher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RouteActivity.class);
                startActivity(intent);
            }
        });

        btnSleepLauncher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(MainActivity.this, SleepTrackerActivity.class);
                //startActivity(intent);
            }
        });

        btnAlarmLauncher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(MainActivity.this, AlarmActivity.class);
                //startActivity(intent);
            }
        });

        btnWeightLauncher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(MainActivity.this, BMIActivity.class);
                //startActivity(intent);
            }
        });
    }
}
