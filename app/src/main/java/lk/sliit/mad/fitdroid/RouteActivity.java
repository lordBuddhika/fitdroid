package lk.sliit.mad.fitdroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import app.Route;
import app.RouteDatabase;
import app.RouteHelper;
import app.RouteListAdapter;

public class RouteActivity extends AppCompatActivity {

    // Initiate a thread for GPS logging
    Thread t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);
        startClock();

        ArrayList<Route> routesArr = new ArrayList<>();
        ArrayList<Route> db_routes;
        RouteDatabase rd = new RouteDatabase(getApplicationContext());
        //rd.newRoute(11.11, 22.22, 33.33, "2018-12-12 23:59:59");
        db_routes = rd.getRoutes();

        for (Route dbr: db_routes) {
            routesArr.add( new Route(Integer.valueOf(dbr.getId()), Double.valueOf(dbr.getDistance()), Double.valueOf(dbr.getTopSpeed()), Double.valueOf(dbr.getDuration()), String.valueOf(dbr.getTimeStart()), String.valueOf(dbr.getTimeEnd()) ));
        }

        final RouteListAdapter rla = new RouteListAdapter(this, R.layout.route_list_layout, routesArr);

        ListView lvRoute = findViewById(R.id.lvRoute);
        lvRoute.setAdapter(rla);

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
                //Toast.makeText(getApplicationContext(), String.valueOf(app.getId()),Toast.LENGTH_SHORT).show();
            }
        });

        final Switch swtRouteActivate = findViewById(R.id.swtRouteActivate);
        swtRouteActivate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                boolean switchState = swtRouteActivate.isChecked();
                if(switchState) {
                    startClock();
                }
            }
        });

/*
        ListView.OnClickListener listViewListener = new ListView.OnItemClickListener() {
            @Override

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                lk.sliit.mad.fitdroid.app.Route app = rlaRoutes.get(position);
                Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                intent.putExtra("distance", app.getDistance());
                intent.putExtra("distance", app.getTopSpeed());
                intent.putExtra("distance", app.getDuration());
                startActivityForResult(intent, 1000);
            }
        };
        //set the listener to the list view
        lvRoute.setOnItemClickListener((AdapterView.OnItemClickListener) listViewListener);
*/




    }






    @Override
    protected void onPause() {
        super.onPause();
/*
        app.RouteHelper routeHelper = app.RouteHelper.getRouterInstance(getApplicationContext());
        routeHelper.destroyGPS();
        t.interrupt();
*/
    }

    @Override
    protected void onStop() {
        super.onStop();
/*
        app.RouteHelper routeHelper = app.RouteHelper.getRouterInstance(getApplicationContext());
        routeHelper.destroyGPS();
*/
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
/*
        app.RouteHelper routeHelper = app.RouteHelper.getRouterInstance(getApplicationContext());
        routeHelper.destroyGPS();
*/
    }

    private void startClock(){

        Switch swtRouteActivate = findViewById(R.id.swtRouteActivate);
        final boolean switchState = swtRouteActivate.isChecked();

        t = new Thread() {

            @Override
            public void run() {
                try {
                    while (!isInterrupted() && switchState) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                RouteHelper routeHelper = RouteHelper.getRouterInstance(getApplicationContext());
                                TextView tvDist = findViewById(R.id.tvDistance);
                                tvDist.setText(String.valueOf(routeHelper.getDistance()));
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };

        t.start();
    }
}
