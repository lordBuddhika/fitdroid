package lk.sliit.mad.fitdroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class AlarmActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        Button btnAdd = findViewById(R.id.btnAdd);
        Intent IntAddBtn = new Intent(AlarmActivity.this, AddAlarmActivity.class);
        startActivity(IntAddBtn);
    }
}
