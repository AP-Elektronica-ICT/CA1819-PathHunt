package com.example.pathhunt.pathhunt;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class TeamBuilder extends AppCompatActivity {

    EditText inputTeam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_builder);

        inputTeam = findViewById(R.id.ETxtTeamname);

        findViewById(R.id.BtnContinue).setOnClickListener(Click);
    }

    View.OnClickListener Click = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.BtnContinue:

                    break;
            }
        }
    };
}
