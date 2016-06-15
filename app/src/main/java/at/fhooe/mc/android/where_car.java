package at.fhooe.mc.android;


import android.app.ActionBar;
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
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class where_car extends FragmentActivity implements View.OnClickListener {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    public boolean SAVE;
    private String TAG = "Where is my Car";
    private ImageView mCarImage;
    private Bitmap mImageBitmap;
    private static File mImage = null;
    private static String mCurrentPhotoPath = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_where_car);

        SAVE = false;

        ActionBar actionBar = getActionBar();
        actionBar.setTitle("Your Car");
        actionBar.setHomeButtonEnabled(true);

        Button car_take_photo = (Button) findViewById(R.id.car_take_photo);
        car_take_photo.setOnClickListener(this);

        this.mCarImage = (ImageView) findViewById(R.id.car_photo);
        this.mCarImage.setOnClickListener(this);

    }

    @Override
    public void onClick(View _v) {
        switch (_v.getId()) {
            case R.id.car_take_photo: {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
               /* Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (cameraIntent.resolveActivity(getPackageManager()) != null) {
                    // Create the File where the photo should go
                    if (mImage == null) {
                        try {
                            mImage = createImageFile();
                        } catch (IOException ex) {
                            // Error occurred while creating the File
                            Log.i(TAG, "IOException while creating File");
                        }
                    }
                    // Continue only if the File was successfully created
                        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mImage));
                        startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);

                }*/
            }
            break;
            case R.id.car_photo: {
                //TODO: View FUll Size Image
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
                fragment_car.carLocation.setVisible(true);

                SharedPreferences.Editor edit = fragment_car.sp.edit();
                edit.clear();
                edit.commit();
            } break;

            case R.id.action_save : {
                SharedPreferences.Editor edit = fragment_car.sp.edit();
                edit.putFloat(fragment_car.KEY_CAR_LONGITUDE, (float) fragment_car.carLocation.getPosition().longitude);
                edit.putFloat(fragment_car.KEY_CAR_LATITUDE, (float) fragment_car.carLocation.getPosition().latitude);
                edit.commit();
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
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mCarImage.setImageBitmap(imageBitmap);
        }

//                mCarImage.setImageBitmap(
//                        decodeSampledBitmapFromFile(300, 500));
//                mImageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.parse(mCurrentPhotoPath));
//                int nh = (int) ( mImageBitmap.getHeight() * (512.0 / mImageBitmap.getWidth()) );
//                Bitmap scaled = Bitmap.createScaledBitmap(mImageBitmap, 512, nh, true);
//                mCarImage.setImageBitmap(scaled);
//                //mCarImage.setImageBitmap(mImageBitmap);
    }



    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(null);
        File image = File.createTempFile(
                imageFileName,  // prefix
                ".jpg",         // suffix
                storageDir      // directory
        );

        // Save a file: path for use with ACTION_VIEW intents
        this.mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }

    public static Bitmap decodeSampledBitmapFromFile(int reqWidth, int reqHeight) {
        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(mCurrentPhotoPath, options);
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }


}
