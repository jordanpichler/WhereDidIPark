package at.fhooe.mc.android;

<<<<<<< HEAD
import android.content.Intent;
import android.location.Location;
=======
>>>>>>> 892a6ea85f5ef4ca25af1ff94e4eb403ff3a9a8f
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
<<<<<<< HEAD
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
=======
>>>>>>> 892a6ea85f5ef4ca25af1ff94e4eb403ff3a9a8f

public class where_car extends FragmentActivity implements View.OnClickListener {
    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_where_car);

        ImageView car_photo = (ImageView) findViewById(R.id.car_photo);
        car_photo.setOnClickListener(this);

    }
<<<<<<< HEAD

    @Override
    public void onClick(View _v) {
        if (_v.getId() == R.id.car_photo) {
            Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(i, REQUEST_IMAGE_CAPTURE);

        }
    }


    /*
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }*/
=======
>>>>>>> 892a6ea85f5ef4ca25af1ff94e4eb403ff3a9a8f
}
