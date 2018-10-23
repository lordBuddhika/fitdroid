package lk.sliit.mad.fitdroid;

import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import app.Route;
import app.RouteDatabase;
import app.RouteGPSHelper;
import app.RouteListAdapter;

public class RouteActivity extends AppCompatActivity {

    static Thread tClock;
    static Thread tTimer;

    static RouteDatabase route_database;
    static RouteGPSHelper route_gps_helper;

    public static String time_start;
    public static double distance;
    public static double top_speed;
    public static double duration;

    DecimalFormat precision_two;
    SimpleDateFormat date_format;

    static ListView lvRoute;
    static Switch swtRouteActivate;
    static TextView tvDistance;
    static TextView tvTopspeed;
    static TextView tvDuration;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);

        lvRoute = findViewById(R.id.lvRoute);
        swtRouteActivate = findViewById(R.id.swtRouteActivate);
        tvDistance = findViewById(R.id.tvDistance);
        tvTopspeed = findViewById(R.id.tvHighestSpeed);
        tvDuration = findViewById(R.id.tvDuration);
        precision_two = new DecimalFormat("0.00");

        updateList();

        if (route_gps_helper != null && route_gps_helper.getGPSStatus()) {
            swtRouteActivate.setChecked(true);
        }

        swtRouteActivate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                boolean switchState = swtRouteActivate.isChecked();
                if(switchState) {
                    Date presentTime_Date = Calendar.getInstance().getTime();
                    date_format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    time_start = date_format.format(presentTime_Date);

                    route_gps_helper = RouteGPSHelper.getRouterInstance(getApplicationContext());
                    if (route_gps_helper.getGPSStatus()) {
                        route_gps_helper.vibrate(100);
                        startClock();
                    } else {
                        if (swtRouteActivate.isChecked()) {
                            swtRouteActivate.setChecked(false);
                        }
                    }
                } else {
                    if (route_gps_helper.getGPSStatus()) {
                        route_gps_helper.destroyGPS();
                        route_gps_helper.vibrate(100);
                        route_gps_helper = null;
                        route_database.newRoute(distance, Double.parseDouble(precision_two.format(top_speed)), Double.parseDouble(precision_two.format(duration/60)), time_start);
                    }
                    updateList();

                    tClock.interrupt();
                    tTimer.interrupt();
                }
            }
        });

        lvRoute.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Route route = (Route)parent.getItemAtPosition(position);
                Intent intent = new Intent(getApplicationContext(), RouteUpdateActivity.class);
                intent.putExtra("id", String.valueOf(route.getId()));
                intent.putExtra("distance", String.valueOf(route.getDistance()));
                intent.putExtra("topspeed", String.valueOf(route.getTopSpeed()));
                intent.putExtra("duration", String.valueOf(route.getDuration()));
                startActivity(intent);
            }
        });
    }

    protected void updateList() {
        ArrayList<Route> route_list = new ArrayList<>();

        route_database = new RouteDatabase(getApplicationContext());
        ArrayList<Route> sql_routes = route_database.getRoutes();

        for (Route route: sql_routes) {
            route_list.add( new Route(Integer.valueOf(route.getId()), Double.valueOf(route.getDistance()), Double.valueOf(route.getTopSpeed()), Double.valueOf(route.getDuration()), String.valueOf(route.getTimeStart()), String.valueOf(route.getTimeEnd()) ));
        }

        RouteListAdapter rla = new RouteListAdapter(this, R.layout.route_list_layout, route_list);
        lvRoute.setAdapter(rla);

        tvDistance.setText(String.valueOf(distance));
        tvTopspeed.setText(String.valueOf(precision_two.format(top_speed)));
        tvDuration.setText(precision_two.format(duration/60));

    }

    public void notification() {

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, "test")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("My notification")
                .setContentText("Much longer text that cannot fit one line...")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("Much longer text that cannot fit one line..."))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(3262346, mBuilder.build());
    }

    private void startClock(){

        swtRouteActivate = findViewById(R.id.swtRouteActivate);
        final boolean switchState = swtRouteActivate.isChecked();

        distance = 0.0;
        top_speed = 0.0;
        duration = 0.0;

        Toast.makeText(getApplicationContext(), "Route tracking started!", Toast.LENGTH_SHORT).show();

        tClock = new Thread() {

            @Override
            public void run() {
                try {
                    while (!isInterrupted() && switchState) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                route_gps_helper = RouteGPSHelper.getRouterInstance(getApplicationContext());

                                if (route_gps_helper.getGPSStatus()) {
                                    distance = route_gps_helper.getDistance();
                                    top_speed = route_gps_helper.getTopSpeed();

                                    tvDistance.setText(String.valueOf(distance));
                                    tvTopspeed.setText(String.valueOf(precision_two.format(top_speed)));
                                    tvDuration.setText(precision_two.format(duration/60));
                                } else {
                                    route_gps_helper.vibrate(100);
                                    Toast.makeText(getApplicationContext(), "GPS unavailable, tracking disabled!", Toast.LENGTH_SHORT).show();
                                    tClock.interrupt();
                                }
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };
        tClock.start();

        tTimer = new Thread() {

            @Override
            public void run() {
                try {
                    while (!isInterrupted() && switchState) {
                        Thread.sleep(100);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                duration += 0.1;
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };

        tTimer.start();
    }
}
