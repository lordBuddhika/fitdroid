package lk.sliit.mad.fitdroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import app.SleepTrackerDatabase;

public class SleepTrackerUpdateActivity extends AppCompatActivity {

    SleepTrackerDatabase sleeptracker_database;

    TextView id;
    EditText start;
    EditText end;

    Button btnUpdate;
    Button btnDelete;

    private int sid;
    private String sStart, sEnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep_tracker_update);

        id = findViewById(R.id.tvID);
        start = findViewById(R.id.etStart);
        end = findViewById(R.id.etEnd);

        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);

        Intent update_intent = getIntent();
        sid = Integer.valueOf(update_intent.getStringExtra("id"));
        sStart = update_intent.getStringExtra("startt");
        sEnd = update_intent.getStringExtra("endt");

        id.setText(String.valueOf(sid));
        start.setText(sStart);
        end.setText(sEnd);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sleeptracker_database = new SleepTrackerDatabase(getApplicationContext());
                boolean update_status = sleeptracker_database.updateSleep(sid, start.getText().toString(), end.getText().toString());
                if(update_status) {
                    Toast.makeText(SleepTrackerUpdateActivity.this, "Update successful!", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(SleepTrackerUpdateActivity.this, SleepTrackerActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else {
                    Toast.makeText(SleepTrackerUpdateActivity.this, "Update failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sleeptracker_database = new SleepTrackerDatabase(getApplicationContext());
                Integer delete_status = sleeptracker_database.deleteSleep(Integer.valueOf(id.getText().toString()));

                if(delete_status == 1) {
                    Toast.makeText(getApplicationContext(), "Sleep track has been successfully deleted!", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(SleepTrackerUpdateActivity.this, SleepTrackerActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Failed!", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }
}
