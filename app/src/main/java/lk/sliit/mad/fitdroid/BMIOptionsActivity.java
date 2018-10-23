package lk.sliit.mad.fitdroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

import app.BMI;
import app.BMIDatabase;

public class BMIOptionsActivity extends AppCompatActivity {

    Button btnSave;
    Button btnCalculate;
    Button btnSearch;
    EditText etDate;
    EditText etWeight;
    EditText etHeight;
    TextView result;
    TextView msg;

    String finalMsg;
    Double res;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi_options);

        btnSave = findViewById(R.id.btnSave);
        btnCalculate = findViewById(R.id.btnCalc);
        btnSearch = findViewById(R.id.btnSearch);

        etDate = findViewById(R.id.Inputdate);
        etWeight = findViewById(R.id.inputweight);
        etHeight = findViewById(R.id.inputheight);

        result = findViewById(R.id.result);
        msg = findViewById(R.id.msg);

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Double wei = Double.parseDouble(etWeight.getText().toString());
                Double hei = Double.parseDouble(etHeight.getText().toString());

                res = Calculate(wei, hei);

                if(res < 18.5) {
                    finalMsg = "Under Weight";
                } else if (res >= 18.5 && res <= 24.9) {
                    finalMsg = "Normal Weight";
                } else if (res > 25) {
                    finalMsg = "Over Weight";
                }

                result.setText(String.valueOf(String.format("%.2f", res)));
                msg.setText(finalMsg);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dte = etDate.getText().toString();
                Double wei = Double.parseDouble(etWeight.getText().toString());
                Double hei = Double.parseDouble(etHeight.getText().toString());

                if(!dte.equals("") && !wei.equals("") && !hei.equals("")) {
                    BMIDatabase bmi_db = new BMIDatabase(getApplicationContext());
                    bmi_db.newBMI(dte, wei, hei);
                    Toast.makeText(BMIOptionsActivity.this, "Success! Data added!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(BMIOptionsActivity.this, "Error! Data not added!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    public double Calculate(double weight, double height) {
        Double res = (weight) / (height * height);
        return res;
    }
}
