package com.example.pathhunt.pathhunt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TeamBuilder extends AppCompatActivity {

    private Button BtnContinue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_builder);

        BtnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BtnContinueClicked();
            }
        });
    }

    private void BtnContinueClicked(){
        =
    }
}
