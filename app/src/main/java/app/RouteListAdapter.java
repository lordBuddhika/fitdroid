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

public class RouteListAdapter extends ArrayAdapter<Route> {

    private Context rlaContext;
    private ArrayList<Route> rlaRoutes;
    int rlaResource;

    public RouteListAdapter(Context context, int resource, ArrayList<Route> objects) {
        super(context, resource, objects);
        rlaContext = context;
        rlaResource = resource;
        rlaRoutes = objects;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Route route = rlaRoutes.get(position);
        LayoutInflater inflater = (LayoutInflater) rlaContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.route_list_layout, null);

        TextView tvDistance = v.findViewById(R.id.tvDistance);
        TextView tvTopspeed = v.findViewById(R.id.tvTopSpeed);
        TextView tvDuration = v.findViewById(R.id.tvDuration);

        tvDistance.setText(String.valueOf(route.getDistance() + "m"));
        tvTopspeed.setText(String.valueOf(route.getTopSpeed() + "km/h"));
        tvDuration.setText(String.valueOf(route.getDuration() + " mins"));

        return v;
    }
}
