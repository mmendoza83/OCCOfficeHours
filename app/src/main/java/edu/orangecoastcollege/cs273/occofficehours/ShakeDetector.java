package edu.orangecoastcollege.cs273.occofficehours;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * A ShakeDetector, determines when the device is shook.
 *
 * Created by mmendoza on 12/7/2017.
 */
public class ShakeDetector implements SensorEventListener {

    private static final long ELAPSED_TIME = 1000L;
    public static final float THRESHOLD = 20;
    private long previousShake;

    private OnShakeListener mListener;

    /**
     * Constructs a shake listener.
     *
     * @param listener
     */
    public ShakeDetector(OnShakeListener listener)
    {
        mListener = listener;
    }

    /**
     *
     * @param sensorEvent
     */
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
        {
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];

            // Removes any effect of gravity from the calculations.
            float gForceX = x - SensorManager.GRAVITY_EARTH;
            float gForceY = y - SensorManager.GRAVITY_EARTH;
            float gForceZ = z - SensorManager.GRAVITY_EARTH;
            float netForce = (float) Math.sqrt(Math.pow(gForceX, 2) + Math.pow(gForceY, 2) + Math.pow(gForceZ, 2));

            // The device has moved past the shake threshold
            if (netForce >= THRESHOLD)
            {
                long currentTime = System.currentTimeMillis();

                // See if enough time has passed to register another shake event.
                if (currentTime > previousShake + ELAPSED_TIME)
                {
                    // Reset the previous shake and register the new shake.
                    previousShake = currentTime;
                    mListener.onShake();
                }
            }
        }
    }

    // Never used
    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        // Unused method
    }

    /**
     * Interface for a shake listener. Calls the onShake function.
     */
    public interface OnShakeListener
    {
        void onShake();
    }
}
