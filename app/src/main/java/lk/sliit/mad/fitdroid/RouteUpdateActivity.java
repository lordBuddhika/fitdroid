package lk.sliit.mad.fitdroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import app.RouteDatabase;

public class RouteUpdateActivity extends AppCompatActivity {

    protected EditText et_routeid;
    protected EditText et_distance;
    protected EditText et_topspeed;
    protected EditText et_duration;
    protected Button btn_delete;
    protected Button btn_update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_update);

        et_routeid = findViewById(R.id.et_routeid);
        et_distance = findViewById(R.id.et_distance);
        et_topspeed = findViewById(R.id.et_topspeed);
        et_duration = findViewById(R.id.et_duration);

        btn_delete = findViewById(R.id.btn_delete);
        btn_update = findViewById(R.id.btn_update);

        et_routeid.setFocusable(false);
        et_routeid.setClickable(false);

        Intent update_intent = getIntent();
        et_routeid.setText(update_intent.getStringExtra("id"));
        et_distance.setText(update_intent.getStringExtra("distance"));
        et_topspeed.setText(update_intent.getStringExtra("topspeed"));
        et_duration.setText(update_intent.getStringExtra("duration"));

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update();
            }
        });

    }

    protected boolean update() {
        RouteDatabase rd = new RouteDatabase(getApplicationContext());
        Boolean update_status = rd.updateRoute(Integer.parseInt(et_routeid.getText().toString()), Double.parseDouble(et_distance.getText().toString()), Double.parseDouble(et_topspeed.getText().toString()), Double.parseDouble(et_duration.getText().toString()));

        if(update_status) {
            Toast.makeText(getApplicationContext(), "Route has been successfully updated!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(RouteUpdateActivity.this, RouteActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            return true;
        } else {
            return false;
        }
    }
}
