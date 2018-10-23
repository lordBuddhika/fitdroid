package lk.sliit.mad.fitdroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import app.BMI;
import app.BMIDatabase;
import app.BMIListAdapter;
import app.Route;
import app.RouteDatabase;
import app.RouteListAdapter;

import static lk.sliit.mad.fitdroid.RouteActivity.tvDistance;

public class BMIActivity extends AppCompatActivity {

    static BMIDatabase bmi_database;
    static ListView lvBMI;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi);

        updateList();

        Button bmiOptions = findViewById(R.id.btnOpt);
        bmiOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent itn = new Intent(BMIActivity.this, BMIOptionsActivity.class);
                startActivity(itn);
            }
        });
    }

    protected void updateList() {
        ArrayList<BMI> bmi_list = new ArrayList<>();

        bmi_database = new BMIDatabase(getApplicationContext());
        ArrayList<BMI> sql_routes = bmi_database.getBMIs();

        for (BMI bmi: sql_routes) {
            bmi_list.add( new BMI(Integer.valueOf(bmi.getId()), bmi.getDate(), Double.valueOf(bmi.getWeight()), Double.valueOf(bmi.getHeight()) ));
        }

        Toast.makeText(this, String.valueOf(bmi_database.getBMIRows()), Toast.LENGTH_SHORT).show();

        BMIListAdapter bla = new BMIListAdapter(this, R.layout.bmi_list_layout, bmi_list);
        //lvBMI.setAdapter(bla);

    }

}
