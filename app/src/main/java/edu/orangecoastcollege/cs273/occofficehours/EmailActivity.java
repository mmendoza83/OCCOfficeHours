package edu.orangecoastcollege.cs273.occofficehours;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

/*
    EmailActivity class that build an email framework
 */

public class EmailActivity extends AppCompatActivity {

    // Initialize the EditText variables
    private EditText recipientEditText;
    private EditText subjectEditText;
    private EditText contentsEditText;

    // Declare the variables
    private String recipient;
    private String subject;
    private String bodyText;
    private String monday;
    private String tuesday;
    private String wednesday;
    private String thursday;
    private String friday;

    // Declare the Animation that is used for shaking the envelope ImageView
    private Animation evelopeAnim;
    private ImageView envelopImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);

        recipientEditText = (EditText) findViewById(R.id.emailRecipientEditText);
        subjectEditText = (EditText)findViewById(R.id.emailSubjectEditText);
        contentsEditText = (EditText) findViewById(R.id.emailMessageBodyEditText);
        envelopImageView = (ImageView) findViewById(R.id.envelopImageView);

        // Create an intent for the email
        Intent detailsIntent = getIntent();
        recipient = detailsIntent.getStringExtra("Recipient");

        monday = detailsIntent.getStringExtra("Monday");
        tuesday = detailsIntent.getStringExtra("Tuesday");
        wednesday = detailsIntent.getStringExtra("Wednesday");
        thursday = detailsIntent.getStringExtra("Thursday");
        friday = detailsIntent.getStringExtra("Friday");

        // Generate the body of the email messages
        bodyText = getResources().getString(R.string.body_message);

        // If the Monday, Tuesday, Wednesday, Thursday, or Friday is selected
        if ((monday != null))
            bodyText += " Monday at " + monday;
        else if ((tuesday != null) )
            bodyText += " Tuesday at " + tuesday;
        else if ((wednesday != null) )
            bodyText += " Wednesday at " + wednesday;
        else if ((thursday != null) )
            bodyText += " Thurday at " +  thursday;
        else if ((friday != null))
            bodyText += " Friday at " + friday;

        bodyText += ". \nThank you.";

        // Display the email of the recipient
        recipientEditText.setText(recipient);

        // Display the subject
        subject = getResources().getString(R.string.subject_content);
        subjectEditText.setText(subject);

        // Display the contents
        contentsEditText.setText(bodyText);

        // The button to compose the email
        Button startBtn = (Button) findViewById(R.id.emailSendButton);
        startBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                sendEmail();
            }
        });
    }


    /**
     * This function is used to generate an intent to send the composed email
     */
    protected void sendEmail() {

        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", recipient, null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, bodyText);
        startActivity(Intent.createChooser(emailIntent, "Send email"));
    }


    /**
     * This function is used to shake the envelope Image if the image is clicked
     * @param view
     */
    public void setEvelopeShake(View view) {
        evelopeAnim = AnimationUtils.loadAnimation(this, R.anim.shake_custom);

        if (!evelopeAnim.hasStarted() || evelopeAnim.hasEnded())
            envelopImageView.startAnimation(evelopeAnim);    }

}



