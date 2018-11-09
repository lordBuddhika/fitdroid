package lk.sliit.mad.fitdroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import app.Route;
import app.RouteDatabase;
import app.RouteListAdapter;
import app.Sleep;
import app.SleepListAdapter;
import app.SleepTrackerDatabase;

public class SleepTrackerActivity extends AppCompatActivity {

    static Thread tTimer;
    static SleepTrackerDatabase sleep_database;
    static ListView lvSleep;
    static int duration;
    static boolean SleepStatus;
    static String startT;
    static String endT;

    Button btnSleep;
    Button btnWakeup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep_tracker);

        lvSleep = findViewById(R.id.lvSleep);
        btnSleep = findViewById(R.id.btnSleep);
        btnWakeup = findViewById(R.id.btnWakeup);

        if(SleepStatus) {
            btnSleep.setEnabled(false);
            btnWakeup.setEnabled(true);
        } else {
            btnSleep.setEnabled(true);
            btnWakeup.setEnabled(false);
        }

        btnSleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startClock();
            }
        });

        btnWakeup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                endClock();
            }
        });

        lvSleep.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Sleep sleep = (Sleep)parent.getItemAtPosition(position);
                Intent intent = new Intent(getApplicationContext(), SleepTrackerUpdateActivity.class);
                intent.putExtra("id", String.valueOf(sleep.getId()));
                intent.putExtra("startt", String.valueOf(sleep.getStart()));
                intent.putExtra("endt", String.valueOf(sleep.getEnd()));
                startActivity(intent);
            }
        });
        updateList();
    }

    protected void updateList() {
        ArrayList<Sleep> sleep_list = new ArrayList<>();

        sleep_database = new SleepTrackerDatabase(getApplicationContext());

        ArrayList<Sleep> sql_sleeps = sleep_database.getSleeps();

        for (Sleep sleep: sql_sleeps) {
            sleep_list.add( new Sleep(sleep.getId(), sleep.getStart(), sleep.getEnd() ));
        }

        SleepListAdapter rla = new SleepListAdapter(this, R.layout.sleep_list_layout, sleep_list);
        try {
            lvSleep.setAdapter(rla);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startClock(){

        btnSleep = findViewById(R.id.btnSleep);
        btnWakeup = findViewById(R.id.btnWakeup);

        if(!SleepStatus) {
            Toast.makeText(getApplicationContext(), "Sleep tracking started!", Toast.LENGTH_SHORT).show();

            btnSleep.setEnabled(false);
            btnWakeup.setEnabled(true);
            SleepStatus = true;

            startT = GetDateTime();
        }

    }

    private void endClock() {
        if(SleepStatus) {

            Toast.makeText(getApplicationContext(), "Sleep tracking finished!", Toast.LENGTH_SHORT).show();

            btnSleep.setEnabled(true);
            btnWakeup.setEnabled(false);
            SleepStatus = false;
            endT = GetDateTime();

            sleep_database.newSleep(startT, endT);

            updateList();
        }
    }

    private String GetDateTime(){
        Date dt = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(dt);
    }
}
