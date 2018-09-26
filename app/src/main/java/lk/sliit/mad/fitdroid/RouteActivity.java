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

import java.util.ArrayList;

import route.Route;
import route.RouteHelper;
import route.RouteListAdapter;

public class RouteActivity extends AppCompatActivity {

    // Initiate a thread for GPS logging
    Thread t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);
        startClock();

        Route r1 = new Route(1,504.34,7.52,470);
        Route r2 = new Route(2,497.80,7.23,485);
        Route r3 = new Route(3,422.66,7.67,464);
        Route r4 = new Route(4,275.47,7.95,415);
        Route r5 = new Route(5,686.41,7.03,436);
        Route r6 = new Route(6,854.40,7.63,448);
        Route r7 = new Route(7,504.34,7.52,470);
        Route r8 = new Route(8,497.80,7.23,485);
        Route r9 = new Route(9,422.66,7.67,464);
        Route r10 = new Route(10,275.47,7.95,415);
        Route r11 = new Route(11,686.41,7.03,436);
        Route r12 = new Route(12,854.40,7.63,448);
        Route r13 = new Route(13,422.66,7.67,464);
        Route r14 = new Route(14,504.34,7.52,470);
        Route r15 = new Route(15,422.66,7.67,464);

        ArrayList<Route> routesArr = new ArrayList<>();
        routesArr.add(r1);
        routesArr.add(r2);
        routesArr.add(r3);
        routesArr.add(r4);
        routesArr.add(r5);
        routesArr.add(r6);
        routesArr.add(r7);
        routesArr.add(r8);
        routesArr.add(r9);
        routesArr.add(r10);
        routesArr.add(r11);
        routesArr.add(r12);
        routesArr.add(r13);
        routesArr.add(r14);
        routesArr.add(r15);

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
                //Toast.makeText(getApplicationContext(), String.valueOf(route.getId()),Toast.LENGTH_SHORT).show();
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
                lk.sliit.mad.fitdroid.route.Route route = rlaRoutes.get(position);
                Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                intent.putExtra("distance", route.getDistance());
                intent.putExtra("distance", route.getTopSpeed());
                intent.putExtra("distance", route.getDuration());
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
        route.RouteHelper routeHelper = route.RouteHelper.getRouterInstance(getApplicationContext());
        routeHelper.destroyGPS();
        t.interrupt();
*/
    }

    @Override
    protected void onStop() {
        super.onStop();
/*
        route.RouteHelper routeHelper = route.RouteHelper.getRouterInstance(getApplicationContext());
        routeHelper.destroyGPS();
*/
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
/*
        route.RouteHelper routeHelper = route.RouteHelper.getRouterInstance(getApplicationContext());
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
