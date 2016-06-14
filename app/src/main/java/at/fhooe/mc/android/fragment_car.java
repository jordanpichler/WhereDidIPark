package at.fhooe.mc.android;

import android.app.Fragment;
import android.location.Location;
import android.os.Bundle;/*
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;*/
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
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class fragment_car extends Fragment implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {
    MapView mView;
    GoogleMap mMap;
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    public static Marker carLocation;
    public static Marker userLocation;
    public static boolean SAVE;

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

        mLocationRequest = new LocationRequest().setInterval(1000);
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
        mMap = googleMap;

        LatLng latLng = new LatLng(0, 0);
        MarkerOptions userOptions = new MarkerOptions().position(latLng).title("My Position");
        MarkerOptions carOptions = new MarkerOptions().position(latLng).title("Car Position");
        userLocation = mMap.addMarker(userOptions);
        userLocation.setVisible(false);
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
        if(!SAVE) {
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18));
            carLocation.setVisible(true);
            carLocation.setPosition(latLng);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        }

        else {
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            userLocation.setVisible(true);
            userLocation.setPosition(latLng);
        }
    }
}
