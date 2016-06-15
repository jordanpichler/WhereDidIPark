package at.fhooe.mc.android;


import android.app.ActionBar;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class where_car extends FragmentActivity implements View.OnClickListener {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private String TAG = "Where is my Car";
    private ImageView mCarImage;
    private EditText mNotes;
    private Bitmap mCarBitmap;
    public String KEY_IMG_PATH = "WhereDidIParkCARKEYImgPath";
    public String KEY_NOTES = "WhereDidIParkCARKEYNotes";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_where_car);;

        ActionBar actionBar = getActionBar();
        actionBar.setTitle("Your Car");
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);

        Button car_take_photo = (Button) findViewById(R.id.car_take_photo);
        car_take_photo.setOnClickListener(this);

        mCarImage = (ImageView) findViewById(R.id.car_photo);

        mNotes = (EditText) findViewById(R.id.car_notes);

        //Recreate Saved Instance
        SharedPreferences sp = getPreferences(Context.MODE_PRIVATE);

        //--Image
        String imgPath = sp.getString(KEY_IMG_PATH, null);
        mCarBitmap = loadImageFromStorage(imgPath);
        mCarImage.setImageBitmap(mCarBitmap);

        //--Notes
        String notesContent = sp.getString(KEY_NOTES, null);
        mNotes.setText(notesContent);
    }

    @Override
    public void onClick(View _v) {
        switch (_v.getId()) {
            case R.id.car_take_photo: {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            }
            break;

            default: {
                Log.e(TAG, "unexpected onClick ID encountered");
            }

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
                fragment_car.carLocation.setVisible(false);

                SharedPreferences.Editor edit = fragment_car.sp.edit();
                edit.clear();
                edit.commit();
                Log.i(TAG, "cleared Shared Prefs");

                mCarImage.setImageBitmap(null);
                mNotes.setText(null);


            } break;

            case R.id.action_save : {
                SharedPreferences.Editor edit = fragment_car.sp.edit();
                edit.putFloat(fragment_car.KEY_CAR_LONGITUDE, (float) fragment_car.carLocation.getPosition().longitude);
                edit.putFloat(fragment_car.KEY_CAR_LATITUDE, (float) fragment_car.carLocation.getPosition().latitude);
                fragment_car.carLocation.setVisible(true);

                String imagePath = saveToInternalStorage(mCarBitmap);
                edit.putString(KEY_IMG_PATH, imagePath);

                String notesContent = mNotes.getText().toString();
                edit.putString(KEY_NOTES, notesContent);

                Log.i(TAG, "saved to Shared Prefs");

                edit.commit();
                mCarBitmap = null;
                Toast.makeText(this, "All Saved! :)", Toast.LENGTH_SHORT).show();
            } break;

            default: {

            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            mCarBitmap = (Bitmap) extras.get("data");
            mCarImage.setImageBitmap(mCarBitmap);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private String saveToInternalStorage(Bitmap bitmapImage){
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath=new File(directory,"recoveredPhoto.jpg");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Log.i(TAG, "created Path String for Photo (Shared Prefs)");
        return directory.getAbsolutePath();
    }

    private Bitmap loadImageFromStorage(String path)
    {
        try {
            File f = new File(path, "recoveredPhoto.jpg");
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            Log.i(TAG, "retrieved Photo Path (Shared Prefs): " + path);
            return b;
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
            return null;
        }

    }
}
