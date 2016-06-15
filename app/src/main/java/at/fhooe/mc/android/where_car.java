package at.fhooe.mc.android;


import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

public class where_car extends FragmentActivity implements View.OnClickListener {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    public boolean SAVE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_where_car);

        SAVE = false;

        ActionBar actionBar = getActionBar();
        actionBar.setTitle("Your Car");
        actionBar.setHomeButtonEnabled(true);

        ImageView car_photo = (ImageView) findViewById(R.id.car_photo);
        car_photo.setOnClickListener(this);

    }

    @Override
    public void onClick(View _v) {
        if (_v.getId() == R.id.car_photo) {
            Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(i, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete : {
                fragment_car.SAVE = false;
                fragment_car.userLocation.setVisible(false);
                fragment_car.carLocation.setVisible(true);
            } break;

            case R.id.action_save : {
                fragment_car.SAVE = true;
            } break;

            default : {

            }
        }

        return super.onOptionsItemSelected(item);
    }
}
