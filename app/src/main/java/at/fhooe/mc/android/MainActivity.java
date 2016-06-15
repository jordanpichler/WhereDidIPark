package at.fhooe.mc.android;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView iv = (ImageView) findViewById(R.id.main_where_is_car);
        iv.setOnClickListener(this);
        iv = (ImageView) findViewById(R.id.main_where_is_bike);
        iv.setOnClickListener(this);
    }

    @Override
    public void onClick(View _v) {
        switch(_v.getId())   {
            case R.id.main_where_is_car: {
                Intent i = new Intent(this, where_car.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            } break;
            case R.id.main_where_is_bike: {
                Intent i = new Intent(this, where_bike.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            } break;
            default: {

            } break;
        }
    }
}
