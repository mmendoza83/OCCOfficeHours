package edu.orangecoastcollege.cs273.occofficehours;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class EmailActivity extends AppCompatActivity {

    private EditText senderEditText ;
    private EditText recipientEditText;
    private EditText subjectEditText;
    private EditText contentsEditText;

    private String sender;
    private String recipient;
    private String contents;
    private String monday;
    private String tuesday;
    private String wednesday;
    private String thursday;
    private String friday;
    private String mon;
    private String tues;
    private String wed;
    private String thurs;
    private String fri;

    private List<String> emails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);

        senderEditText = (EditText) findViewById(R.id.emailSenderEditText);
        recipientEditText = (EditText) findViewById(R.id.emailRecipientEditText);
        subjectEditText = (EditText)findViewById(R.id.emailSubjectEditText);
        contentsEditText = (EditText) findViewById(R.id.emailMessageBodyEditText);

        Intent detailsIntent = getIntent();
        recipient = detailsIntent.getStringExtra("Recipient");
        mon = detailsIntent.getStringExtra("Monday");
        tues = detailsIntent.getStringExtra("Tuesday");
        wed = detailsIntent.getStringExtra("Wednesday");
        thurs = detailsIntent.getStringExtra("Thursday");
        fri = detailsIntent.getStringExtra("Friday");

        if (!mon.equals("NA"))
            monday = detailsIntent.getStringExtra("monday");

        if ( !tues.equals("NA"))
            tuesday = detailsIntent.getStringExtra("tuesday");

        if (!wed.equals("NA"))
            wednesday = detailsIntent.getStringExtra("wednesday");

        if (!thurs.equals("NA"))
            thursday = detailsIntent.getStringExtra("thursday");

        if (!fri.equals("NA"))
            friday = detailsIntent.getStringExtra("friday");

        contents = getResources().getString(R.string.body_message);
        if (!monday.equals("null"))
            contents += " Monday at " + monday;
        if (!tuesday.equals("null"))
            contents += " Tuesday at " + tuesday;
        if (!wednesday.equals("null"))
            contents += " Wednesday at " + wednesday;
        if (!thursday.equals("null"))
            contents += " Thurday at " +  thursday;
        if (!friday.equals("null"))
            contents += " Friday at " + friday;

        contents += " /nThank you.";

        sender = emails.remove(0);
        senderEditText.setText(sender);
        recipientEditText.setText(recipient);

        subjectEditText.setText(R.string.subject_content);
        contentsEditText.setText(contents);

        Button startBtn = (Button) findViewById(R.id.emailSendButton);
        startBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                sendEmail();
            }
        });
    }


    protected void sendEmail() {
        String TO = recipient;
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, R.string.subject_content);
        emailIntent.putExtra(Intent.EXTRA_TEXT, contents);

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }
}
