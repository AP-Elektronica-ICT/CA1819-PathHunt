package com.example.pathhunt.pathhunt;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class TeamBuilder extends AppCompatActivity {

    EditText inputTeam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_builder);

        inputTeam = findViewById(R.id.ETxtTeamname);
        Button BtnContinue = (Button) findViewById(R.id.BtnContinue);

        BtnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BtnContinueClicked();
            }
        });
    }

    private void BtnContinueClicked(){

    }

    private void Continue(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
