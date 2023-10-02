package com.example.labfouraccelerometer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private ImageView movableObject;
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private float lastX, lastY;
    private boolean firstTime = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movableObject = findViewById(R.id.movableObject);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (accelerometer != null) {
            sensorManager.registerListener((SensorEventListener) this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (sensorManager != null) {
            sensorManager.unregisterListener(this);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = event.values[0];
            float y = event.values[1];

            if (firstTime) {
                lastX = x;
                lastY = y;
                firstTime = false;
            }

            float deltaX = x - lastX;
            float deltaY = y - lastY;

            float newX = movableObject.getX() - deltaX;
            float newY = movableObject.getY() + deltaY;

            // Ограничьте перемещение объекта в пределах экрана
            newX = Math.max(0, Math.min(newX, getWindowManager().getDefaultDisplay().getWidth() - movableObject.getWidth()));
            newY = Math.max(0, Math.min(newY, getWindowManager().getDefaultDisplay().getHeight() - movableObject.getHeight()));

            movableObject.setX(newX);
            movableObject.setY(newY);

            lastX = x;
            lastY = y;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Ничего не делаем при изменении точности акселерометра
    }

}