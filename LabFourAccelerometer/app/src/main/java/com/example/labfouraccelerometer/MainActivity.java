package com.example.labfouraccelerometer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager _sensorManager;
    private TextView _square;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        _square = findViewById(R.id.square);

        setUpSensorStuff();
    }

    private void setUpSensorStuff() {
        _sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        _sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        _sensorManager.registerListener(
                this,
                _sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_FASTEST,
                SensorManager.SENSOR_DELAY_FASTEST
        );
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        // Checks for the sensor we have registered
        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float sides = sensorEvent.values[0];
            float upDown = sensorEvent.values[1];

            _square.setRotationX(upDown * 3f);
            _square.setRotationY(sides * 3f);
            _square.setRotation(-sides);
            _square.setTranslationX(sides * (-10));
            _square.setTranslationY(upDown * 10);

            // Changes the colour of the square if it's completely flat
            int color = (Math.round(upDown) == 0 && Math.round(sides) == 0) ? Color.GREEN : Color.RED;
            _square.setBackgroundColor(color);

            _square.setText("up/down " + Math.round(upDown) + "\n left/right " + Math.round(sides));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }
}