package com.example.ashohet.xmixdrix;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class Login extends Activity {

    public final static String EXTRA_MESSAGE = "com.example.ashohet.xmixdrix.MESSAGE";
    public final static String EXTRA_MESSAGE2 = "com.example.ashohet.xmixdrix.MESSAGE2";
    //Intent intent;
    //Intent intent2;

    EditText participant1Edit;
    EditText participant2Edit;
    Button startPlayButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        participant1Edit = (EditText)findViewById(R.id.participant1Edit);
        participant2Edit = (EditText)findViewById(R.id.participant2Edit);
        startPlayButton = (Button)findViewById(R.id.startPlayButton);

        startPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPlaying();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }



    public void startPlaying() {

        Intent intent = new Intent(this, Main.class);
        String message = participant1Edit.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);

        String message2 = participant2Edit.getText().toString();
        intent.putExtra(EXTRA_MESSAGE2, message2);

        startActivity(intent);

   }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
