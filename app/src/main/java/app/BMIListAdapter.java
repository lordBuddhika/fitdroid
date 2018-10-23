package app;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import lk.sliit.mad.fitdroid.R;

public class BMIListAdapter extends ArrayAdapter<BMI> {

    private Context rlaContext;
    private ArrayList<BMI> rlaBMIs;
    int rlaResource;

    public BMIListAdapter(Context context, int resource, ArrayList<BMI> objects) {
        super(context, resource, objects);
        rlaContext = context;
        rlaResource = resource;
        rlaBMIs = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        BMI bmi = rlaBMIs.get(position);
        LayoutInflater inflater = (LayoutInflater) rlaContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.bmi_list_layout, null);

        TextView tvDistance = v.findViewById(R.id.tvDistance);
        TextView tvTopspeed = v.findViewById(R.id.tvTopSpeed);
        TextView tvDuration = v.findViewById(R.id.tvDuration);
        TextView tvDate = v.findViewById(R.id.tvDate);

        tvDistance.setText(String.valueOf(bmi.getDate()));
        tvTopspeed.setText(String.valueOf(bmi.getHeight()));
        tvDuration.setText(String.valueOf(bmi.getId()));
        tvDate.setText(String.valueOf(bmi.getWeight()));

        return v;
    }
}
