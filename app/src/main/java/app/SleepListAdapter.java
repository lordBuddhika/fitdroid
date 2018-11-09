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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import lk.sliit.mad.fitdroid.R;

public class SleepListAdapter extends ArrayAdapter<Sleep> {

    private Context rlaContext;
    private ArrayList<Sleep> rlaSleeps;
    int rlaResource;

    public Date date1, date2;

    public SleepListAdapter(Context context, int resource, ArrayList<Sleep> objects) {
        super(context, resource, objects);
        rlaContext = context;
        rlaResource = resource;
        rlaSleeps = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Sleep sleep = rlaSleeps.get(position);
        LayoutInflater inflater = (LayoutInflater) rlaContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.sleep_list_layout, null);

        TextView tvDuration = v.findViewById(R.id.tvDuration);
        TextView tvDate = v.findViewById(R.id.tvDate);
        TextView tvStartT = v.findViewById(R.id.tvStartTime);
        TextView tvEndT = v.findViewById(R.id.tvEndTIme);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            date1 = simpleDateFormat.parse(sleep.getStart());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            date2 = simpleDateFormat.parse(sleep.getEnd());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long difference = date2.getTime() - date1.getTime();
        int days = (int) (difference / (1000*60*60*24));
        int hours = (int) ((difference - (1000*60*60*24*days)) / (1000*60*60));
        int min = (int) (difference - (1000*60*60*24*days) - (1000*60*60*hours)) / (1000*60);

        String diff;
        if(days != 0) {
            diff = (days < 0 ? -days : days) + " days";
        } else if(hours != 0) {
            diff = (hours < 0 ? -hours : hours) + " hours";
        } else {
            diff = (min < 0 ? -min : min) + " mins";
        }

        tvDuration.setText(diff);
        tvStartT.setText(String.valueOf("Start: "+sleep.getStart()));
        tvEndT.setText(String.valueOf("End: "+sleep.getEnd()));

        return v;
    }
}
