package edu.orangecoastcollege.cs273.occofficehours;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class SearchByInstructorActivity extends AppCompatActivity {

    private DBHelper db;
    private List<Instructor> allInstructorsList;
    private List<Offering> allOfferingsList;
    private List<Offering> filteredOfferingsList;
    private OfferingListAdapter offeringListAdapter;

    private Spinner instructorSpinner;
    private ListView offeringsListView;

    // Shake animation, used when the user clicks the reset button
    private Animation shakeAnim;

    // Sensor variables
    private SensorManager mSensorManager;
    private Sensor accelerometer;
    private ShakeDetector mShakeDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_by_instructor);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeDetector = new ShakeDetector(new ShakeDetector.OnShakeListener() {
            @Override
            public void onShake() {
                reset(findViewById(R.id.offeringsListView));
            }
        });

        deleteDatabase(DBHelper.DATABASE_NAME);
        db = new DBHelper(this);
        db.importCoursesFromCSV("courses.csv");
        db.importInstructorsFromCSV("instructors.csv");
        db.importOfferingsFromCSV("offerings.csv");

        allOfferingsList = db.getAllOfferings();
        filteredOfferingsList = new ArrayList<>(allOfferingsList);
        allInstructorsList = db.getAllInstructors();

        instructorSpinner = (Spinner) findViewById(R.id.instructorSpinner);

        offeringsListView = (ListView) findViewById(R.id.offeringsListView);

        offeringListAdapter =
                    new OfferingListAdapter(this, R.layout.offering_list_item, filteredOfferingsList);
            offeringsListView.setAdapter(offeringListAdapter);

        ArrayAdapter<String> instructorSpinnerAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getInstructorNames());
        instructorSpinner.setAdapter(instructorSpinnerAdapter);
        instructorSpinner.setOnItemSelectedListener(instructorSpinnerListener);
    }

    /**
     * Collects all instructor names into an array.
     * Used to populate the spinner.
     *
     * @return
     */
    private String[] getInstructorNames()
    {
        String[] instructorNames = new String[allInstructorsList.size() + 1];
        instructorNames[0] = "[Select Instructor]";
        for (int i = 1; i < instructorNames.length; ++i)
            instructorNames[i] = allInstructorsList.get(i - 1).getFullName();

        return instructorNames;
    }

    /**
     * Resets the filter of the list to show all possible items in the list.
     * Plays a shake animation on the list.
     *
     * @param v
     */
    public void reset(View v)
    {
        toggleShakeAnim(v);
        // Set spinner back to position 0
        instructorSpinner.setSelection(0);
        // Clear out the list adapter
        offeringListAdapter.clear();
        // Repopulate it from allOfferingsList
        offeringListAdapter.addAll(allOfferingsList);
    }

    public AdapterView.OnItemSelectedListener instructorSpinnerListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> spinner, View view, int i, long l) {
            // Retrieve the instructor name
            String instructorName = String.valueOf(spinner.getItemAtPosition(i));
            // Clear the adapter
            offeringListAdapter.clear();

            if (instructorName.equals("[Select Instructor]"))
                offeringListAdapter.addAll(allOfferingsList);
            else
                for (Offering offering : allOfferingsList)
                    if (offering.getInstructor().getFullName().equals(instructorName))
                        offeringListAdapter.add(offering);
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };

    /**
     * Plays the animation from shake_anim.xml
     * Shakes the image horizontally.
     * Used in reset function.
     *
     * @param v
     */
    public void toggleShakeAnim(View v)
    {
        shakeAnim = AnimationUtils.loadAnimation(this, R.anim.shake_anim);
        offeringsListView.startAnimation(shakeAnim);
    }

    /**
     * Starts the InstructorDetailsActivity.
     * Sends the selected instructor's details to be displayed for the user.
     *
     * @param v
     */

    public void viewInstructorDetails(View v)
    {
        LinearLayout selectedLayout = (LinearLayout) v;
        Offering selectedOffering = (Offering) selectedLayout.getTag();
        Instructor selectedInstructor = (Instructor) selectedOffering.getInstructor();
        Intent detailsIntent = new Intent(this, InstructorDetailsActivity.class);
        detailsIntent.putExtra("Name", selectedInstructor.getFullName());
        detailsIntent.putExtra("Email", selectedInstructor.getEmail());
        detailsIntent.putExtra("Department", selectedInstructor.getDepartment());
        detailsIntent.putExtra("Building", selectedInstructor.getBuilding());
        detailsIntent.putExtra("Room", selectedInstructor.getRoom());
        detailsIntent.putExtra("Monday", selectedInstructor.getMonday());
        detailsIntent.putExtra("Tuesday", selectedInstructor.getTuesday());
        detailsIntent.putExtra("Wednesday", selectedInstructor.getWednesday());
        detailsIntent.putExtra("Thursday", selectedInstructor.getThursday());
        detailsIntent.putExtra("Friday", selectedInstructor.getFriday());
        startActivity(detailsIntent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(mShakeDetector, accelerometer, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mSensorManager.unregisterListener(mShakeDetector, accelerometer);
    }
}
