package route;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import java.text.DecimalFormat;

public class RouteHelper implements LocationListener {
    private static Context context;
    private static double distance = 0.0;
    private static double speed;
    private static RouteHelper instance;
    private static double latp = 999.0;
    private static double lonp = 999.0;
    private static LocationManager lm;

    private RouteHelper() {}

    public static RouteHelper getRouterInstance(Context c) {
        context = c;

        if(instance == null) {
            instance = new RouteHelper();
            return instance;
        } else {
            return instance;
        }

    }

    private Location getLocation() {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(context, "Permission not granted",Toast.LENGTH_SHORT).show();
            return null;
        }

        lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean isGPSEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (isGPSEnabled) {
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, (float) 0.01, this);
            Location last_location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            return last_location;
        } else {
            Toast.makeText(context, "Please turn on GPS",Toast.LENGTH_LONG).show();
            return null;
        }
    }

    public double getDistance() {
        getLocation();
        return distance;
    }

    public double getSpeed() {
        getLocation();
        return speed;
    }

    public double CalculationByDistance(double lat1, double lon1, double lat2, double lon2) {
        double rad = 6371.00;
        double latd = Math.toRadians(lat2-lat1);
        double lond = Math.toRadians(lon2-lon1);
        double dist = rad * 2 * Math.asin(Math.sqrt(Math.sin(latd/2) * Math.sin(latd/2) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.sin(lond/2) * Math.sin(lond/2)));

        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        return Double.parseDouble(df.format(dist));
    }

    public void destroyGPS() {
        lm.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(Location location) {
        location = instance.getLocation();
        double latn = location.getLatitude();
        double lonn = location.getLongitude();
        if(latp == 999.0 && lonp == 999.0) {
            latp = latn;
            lonp = lonn;
            distance = 0;
        } else {
            distance = distance + CalculationByDistance(latp, lonp, latn, lonn);
        }

        //Toast.makeText(context, Double.toString(distance),Toast.LENGTH_LONG).show();
        latp = latn;
        lonp = lonn;

        speed = ((location.getSpeed()*3600)/1000);
        //Toast.makeText(context, Double.toString(speed),Toast.LENGTH_LONG).show();

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
        //Toast.makeText(context, "GPS Status Changed",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onProviderEnabled(String s) {
        Toast.makeText(context, "GPS Activated",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onProviderDisabled(String s) {
        Toast.makeText(context, "GPS Deactivated",Toast.LENGTH_LONG).show();
    }
}
