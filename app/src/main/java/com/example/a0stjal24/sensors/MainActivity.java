package com.example.a0stjal24.sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    Sensor accel, magField;

    float[] accelValues, magFieldValues, orientationMatrix, orientations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magField = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        sensorManager.registerListener(this, accel, SensorManager.SENSOR_DELAY_UI);
        sensorManager.registerListener(this, magField,SensorManager.SENSOR_DELAY_UI);

        accelValues = new float[3];
        magFieldValues = new float[3];
        orientationMatrix = new float[16];// combination of rotation and translation need 4 by 4 matrix
        orientations = new float[3];
    }

    public void onSensorChanged(SensorEvent ev){
        DecimalFormat df = new DecimalFormat("#.##");
        TextView xacc = (TextView)findViewById(R.id.xacc);
        TextView yacc = (TextView)findViewById(R.id.yacc);
        TextView zacc = (TextView)findViewById(R.id.zacc);

        //values is an array of the acceleration values in the axes
//        xacc.setText(df.format(ev.values[0]));
//        yacc.setText(df.format(ev.values[1]));
//        zacc.setText(df.format(ev.values[2]));
        if(ev.sensor == accel)
        {
            for (int i = 0; i<3; i++){
                accelValues[i] = ev.values [0];//ev.values.clone();//.clone() makes a copy of array but is memory inefficient
            }

        }
        else if (ev.sensor == magField)
        {
            for (int i = 0; i<3; i++){
                magFieldValues[i] = ev.values [0];//ev.values.clone();//.clone() makes a copy of array but is memory inefficient
            }
        }

        SensorManager.getRotationMatrix(orientationMatrix, null, accelValues, magFieldValues);
        SensorManager.getOrientation(orientationMatrix, orientations);

        xacc.setText(df.format(ev.values[0] * 180/Math.PI));
        yacc.setText(df.format(ev.values[1] * 180/Math.PI));
        zacc.setText(df.format(ev.values[2] * 180/Math.PI));

    }

    public void onAccuracyChanged(Sensor sensor, int acc){

    }
}

//matrix is a grid of numbers that represents a transformation e.g rotation, skew, translation or combination of both
