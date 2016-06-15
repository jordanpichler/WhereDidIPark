package at.fhooe.mc.android;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class fragment_car extends Fragment implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {
    MapView mView;
    GoogleMap mMap;
    LocationRequest mLocationRequest;
    boolean zoom;
    GoogleApiClient mGoogleApiClient;
    public static Marker carLocation;
    public static SharedPreferences sp;
    public static String KEY_CAR_LONGITUDE = "WhereDidIParkCARKEYlongi";
    public static String KEY_CAR_LATITUDE = "WhereDidIParkCARKEYlati";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate and return the layout
        View v = inflater.inflate(R.layout.layout_fragment_car, container, false);
        mView = (MapView) v.findViewById(R.id.mapView);

        // Create an instance of GoogleAPIClient.
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(mView.getContext())
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        mLocationRequest = new LocationRequest().setInterval(1000).setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mView.onCreate(savedInstanceState);
        mView.getMapAsync(this);
        return v;
    }

    @Override
    public void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mView.onLowMemory();

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        zoom = false;
        mMap = googleMap;

        sp = getActivity().getPreferences(Context.MODE_PRIVATE);

        float lati = sp.getFloat(KEY_CAR_LATITUDE, -1);
        float longi = sp.getFloat(KEY_CAR_LONGITUDE, -1);

        MarkerOptions carOptions;

        if (lati != -1 && longi != -1) {
            zoom = true;
            carOptions = new MarkerOptions()
                    .position(new LatLng(lati, longi))
                    .title("Car Position");
        } else {
            zoom = false;
            carOptions = new MarkerOptions()
                    .position(new LatLng(0, 0))
                    .title("Car Position");
        }

        carLocation = mMap.addMarker(carOptions);
        carLocation.setVisible(false);
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(/*@Nullable*/ Bundle bundle) {
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }



    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(/*@NonNull*/ ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        float lati = sp.getFloat(KEY_CAR_LATITUDE, -1);
        float longi = sp.getFloat(KEY_CAR_LONGITUDE, -1);

        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

        if(lati == -1 && longi == -1) { // Not saved
            carLocation.setVisible(false);
            carLocation.setPosition(latLng);

            if(!zoom) {
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18));
                zoom = true;
            }

            else {
                mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
            }
        }

        else { // saved
            LatLngBounds.Builder latLngBuilder = new LatLngBounds.Builder();
            latLngBuilder.include(new LatLng(lati, longi));
            latLngBuilder.include(new LatLng(location.getLatitude(), location.getLongitude()));
            LatLngBounds bounds = latLngBuilder.build();
            mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 50));
            carLocation.setVisible(true);
        }

        mMap.setMyLocationEnabled(true);
    }
}
