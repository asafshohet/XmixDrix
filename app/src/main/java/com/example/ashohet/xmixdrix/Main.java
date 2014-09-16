package com.example.ashohet.xmixdrix;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import java.util.*;


public class Main extends Activity {

    //All Instances
    Map <Integer, String> myMap = new HashMap<Integer, String>();
    String user1;
    String user2;
    Random rand = new Random();
    TextView turnText;
    Spinner[] spinners;
    ArrayAdapter<CharSequence> adapter;
    int currentTurn;
    TextView wrongSelectTextView;


    //Constructor
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //init objects and receive Intent's messages
        init();

        //Decides who starts first
        currentTurn = rand.nextInt(2);
        setTurnTextView();
        wrongSelectTextView.setText("");

    }

    private void init(){

        wrongSelectTextView = (TextView)findViewById(R.id.wrongSelectTextView);

        spinners = new Spinner[9];
        spinners[0] = (Spinner) findViewById(R.id.spinner0);
        spinners[1] = (Spinner) findViewById(R.id.spinner1);
        spinners[2] = (Spinner) findViewById(R.id.spinner2);
        spinners[3] = (Spinner) findViewById(R.id.spinner3);
        spinners[4] = (Spinner) findViewById(R.id.spinner4);
        spinners[5] = (Spinner) findViewById(R.id.spinner5);
        spinners[6] = (Spinner) findViewById(R.id.spinner6);
        spinners[7] = (Spinner) findViewById(R.id.spinner7);
        spinners[8] = (Spinner) findViewById(R.id.spinner8);


        adapter = ArrayAdapter.createFromResource(this, R.array.XO, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        for (int a = 0; a <9  ; a++) {
            spinners[a].setAdapter(adapter);
            spinners[a].setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    TextView textView = (TextView) view;
                    String selectedChar = textView.getText().toString();
                    if (validSelection(selectedChar)) {
                        disableActiveSpinners();
                        newTurn(selectedChar);
                        textView.setEnabled(false);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }

        //Receive the user names
        Intent intent = getIntent();
        user1 = intent.getStringExtra(Login.EXTRA_MESSAGE);
        user2 = intent.getStringExtra(Login.EXTRA_MESSAGE2);

        //Put user names in map of "0" and "1"
        myMap.put(new Integer(0), user1);
        myMap.put(new Integer(1), user2);


        turnText = (TextView)findViewById(R.id.turnText);
    }

    private void setTurnTextView(){
        turnText.setText(myMap.get(currentTurn) + getString(R.string.turns));
    }

    //Check if the user selected his character.
    private boolean validSelection(String selectedChar){
        //If it is the X turn
        if (currentTurn==0) {
            if (selectedChar.hashCode() == "X".hashCode()){
                wrongSelectTextView.setText("");
                return true;
            }
            wrongSelectTextView.setText(getString(R.string.selectX));
        }

        //If it is the Y turn
        else if (currentTurn==1){
            if (selectedChar.hashCode() == "O".hashCode()) {
                wrongSelectTextView.setText("");
                return true;
            }
            wrongSelectTextView.setText(getString(R.string.selectO));
        }
        return false;

    }

    // After selection - skip to next turn, unless there's a match
    private void newTurn(String selectedChar){
        if (! (matchFound(selectedChar))) {
            if (currentTurn==0)
                currentTurn=1;
            else
                currentTurn=0;

            setTurnTextView();
        }
        else {
            turnText.setText(myMap.get(currentTurn) +  ", You won!");
        }
    }

    private boolean matchFound(String selectedChar){
        //Case 1 - first row
        if (spinners[0].getSelectedItem().toString().hashCode() == selectedChar.hashCode() &&
                spinners[1].getSelectedItem().toString().hashCode() == selectedChar.hashCode() &&
                spinners[2].getSelectedItem().toString().hashCode() == selectedChar.hashCode())
                return true;
        //Case 2 - cross
        if (spinners[0].getSelectedItem().toString().hashCode() == selectedChar.hashCode() &&
                spinners[3].getSelectedItem().toString().hashCode() == selectedChar.hashCode() &&
                spinners[6].getSelectedItem().toString().hashCode() == selectedChar.hashCode())
                return true;
        //Case 3 - first column
        if (spinners[0].getSelectedItem().toString().hashCode() == selectedChar.hashCode() &&
                spinners[4].getSelectedItem().toString().hashCode() == selectedChar.hashCode() &&
                spinners[8].getSelectedItem().toString().hashCode() == selectedChar.hashCode())
            return true;
        //Case 4 - second column
        if (spinners[1].getSelectedItem().toString().hashCode() == selectedChar.hashCode() &&
                spinners[4].getSelectedItem().toString().hashCode() == selectedChar.hashCode() &&
                spinners[7].getSelectedItem().toString().hashCode() == selectedChar.hashCode())
                return true;
        //Case 5 - 3'rd column
        if (spinners[2].getSelectedItem().toString().hashCode() == selectedChar.hashCode() &&
                spinners[5].getSelectedItem().toString().hashCode() == selectedChar.hashCode() &&
                spinners[8].getSelectedItem().toString().hashCode() == selectedChar.hashCode())
            return true;
        //Case 6 - cross
        if (spinners[2].getSelectedItem().toString().hashCode() == selectedChar.hashCode() &&
                spinners[4].getSelectedItem().toString().hashCode() == selectedChar.hashCode() &&
                spinners[6].getSelectedItem().toString().hashCode() == selectedChar.hashCode())
            return true;
        //Case 7 - second row
        if (spinners[3].getSelectedItem().toString().hashCode() == selectedChar.hashCode() &&
                spinners[4].getSelectedItem().toString().hashCode() == selectedChar.hashCode() &&
                spinners[5].getSelectedItem().toString().hashCode() == selectedChar.hashCode())
            return true;
        //Case 8 - 3'rd row
        if (spinners[6].getSelectedItem().toString().hashCode() == selectedChar.hashCode() &&
                spinners[7].getSelectedItem().toString().hashCode() == selectedChar.hashCode() &&
                spinners[8].getSelectedItem().toString().hashCode() == selectedChar.hashCode())
            return true;

        //else...
        return false;

    }

    private void disableActiveSpinners() {
        for (int a = 0 ; a<9 ; a++)
            if (spinners[a].getSelectedItem().toString().hashCode() != "".hashCode())
                spinners[a].setEnabled(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
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
