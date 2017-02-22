package com.example.samsung.detectapp;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class NdkActivity extends Activity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ndk);

        TextView tv = (TextView) findViewById(R.id.textv);
        tv.setText(stringFromJNI());

    }


    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();

}
