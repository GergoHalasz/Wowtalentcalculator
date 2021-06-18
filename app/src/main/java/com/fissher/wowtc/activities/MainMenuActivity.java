package com.fissher.wowtc.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.fissher.wowtc.R;
import com.fissher.wowtc.manager.StateManager;
import com.fissher.wowtc.utils.Constants;
import com.fissher.wowtc.utils.Utilities;

import hotchemi.android.rate.AppRate;

public class MainMenuActivity extends AppCompatActivity implements View.OnTouchListener {

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        AppRate.with(this)
                .setInstallDays(1)
                .setLaunchTimes(2)
                .setRemindInterval(2)
                .monitor();
        AppRate.showRateDialogIfMeetsConditions(this);

        ImageButton vanilla = findViewById(R.id.vanillaButton);
        vanilla.setOnTouchListener(this);

        ImageButton tbc = findViewById(R.id.tbcButton);
        tbc.setOnTouchListener(this);

        ImageButton wotlk = findViewById(R.id.wotlkButton);
        wotlk.setOnTouchListener(this);

        Utilities.setMenuBackground(this, R.id.mainMenu);
        Utilities.initAndLoadAd(this);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.vanillaButton: {
                    StateManager.CURRENT_EXPANSION = Constants.Expansions.CLASSIC.ordinal();
                    Intent intent = new Intent(MainMenuActivity.this, ClassMenuActivity.class);
                    startActivity(intent);
                break;
            }
            case R.id.tbcButton: {
                    StateManager.CURRENT_EXPANSION = Constants.Expansions.TBC.ordinal();
                    Intent intent = new Intent(MainMenuActivity.this, ClassMenuActivity.class);
                    startActivity(intent);
                break;
            }
            case R.id.wotlkButton: {
                    StateManager.CURRENT_EXPANSION = Constants.Expansions.WOTLK.ordinal();
                    Intent intent = new Intent(MainMenuActivity.this, ClassMenuActivity.class);
                    startActivity(intent);
                break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                ((ImageButton)view).setColorFilter(Color.argb(150, 0, 0, 0));
                return true;
            case MotionEvent.ACTION_UP:
                ((ImageButton)view).clearColorFilter();
                onClick(view);
                return true;
        }

        return false;
    }
}
