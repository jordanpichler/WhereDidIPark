package at.fhooe.mc.android;

import android.app.Activity;
import android.os.Bundle;

public class where_bike extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_where_bike);
        this.getActionBar().setDisplayShowTitleEnabled(true);
    }
}
