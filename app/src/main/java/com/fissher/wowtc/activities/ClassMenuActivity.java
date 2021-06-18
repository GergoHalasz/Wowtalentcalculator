package com.fissher.wowtc.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.fissher.wowtc.R;
import com.fissher.wowtc.manager.StateManager;
import com.fissher.wowtc.utils.Constants;
import com.fissher.wowtc.utils.Utilities;

public class ClassMenuActivity extends AppCompatActivity implements View.OnTouchListener {
    private InterstitialAd mInterstitialAd;
    int interstitialAdCounter = 1;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(!isConnected(this)) {
            showCustomDialog();
        }





        switch (StateManager.CURRENT_EXPANSION) {
            case 0:
                setContentView(R.layout.activity_vanilla_menu);
                Utilities.setMenuBackground(this, R.id.vanillaMenu);
                break;
            case 1:
                setContentView(R.layout.activity_tbc_menu);
                Utilities.setMenuBackground(this, R.id.tbcMenu);
                break;
            case 2:
                setContentView(R.layout.activity_wotlk_menu);
                Utilities.setMenuBackground(this, R.id.wotlkMenu);
                break;
        }

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-8156706115088392/1810780046");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                // Load the next interstitial.
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }

        });
        Utilities.initAndLoadAd(this);

        ImageButton druid = findViewById(R.id.classDruid);
        druid.setOnTouchListener(this);

        ImageButton hunter = findViewById(R.id.classHunter);
        hunter.setOnTouchListener(this);

        ImageButton mage = findViewById(R.id.classMage);
        mage.setOnTouchListener(this);

        ImageButton paladin = findViewById(R.id.classPaladin);
        paladin.setOnTouchListener(this);

        ImageButton priest = findViewById(R.id.classPriest);
        priest.setOnTouchListener(this);

        ImageButton rogue = findViewById(R.id.classRogue);
        rogue.setOnTouchListener(this);

        ImageButton shaman = findViewById(R.id.classShaman);
        shaman.setOnTouchListener(this);

        ImageButton warlock = findViewById(R.id.classWarlock);
        warlock.setOnTouchListener(this);

        ImageButton warrior = findViewById(R.id.classWarrior);
        warrior.setOnTouchListener(this);

        if (StateManager.CURRENT_EXPANSION == 2) {
            ImageButton dk = findViewById(R.id.classDk);
            dk.setOnTouchListener(this);
        }

        ImageButton load = findViewById(R.id.loadButton);
        load.setOnTouchListener(this);
    }

    private void showCustomDialog() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(ClassMenuActivity.this);

        builder.setMessage("Please connect to the internet to proceed further. Click on the \"Connected\" button if you did connect to the internet.")
                .setCancelable(false)
                .setPositiveButton("Connected", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(!isConnected(ClassMenuActivity.this)) {
                            Toast.makeText(getApplicationContext(), "You are not connected to the internet.", Toast.LENGTH_SHORT).show();
                            builder.show();
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent myIntent = new Intent(ClassMenuActivity.this, MainMenuActivity.class);
                        ClassMenuActivity.this.startActivity(myIntent);
                    }
                });
        builder.show();
    }

    private boolean isConnected(ClassMenuActivity classMenuActivity) {
        ConnectivityManager connectivityManager = (ConnectivityManager) classMenuActivity.getSystemService((Context.CONNECTIVITY_SERVICE));

        NetworkInfo wifiConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileConn= connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if((wifiConn != null && wifiConn.isConnected()) || mobileConn != null && mobileConn.isConnected())
        {
            return true;
        }
        else
            return false;

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!isConnected(this)) {
            showCustomDialog();
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.classDruid: {
                StateManager.CURRENT_CLASS = Constants.Classes.DRUID.ordinal();
                StateManager.CURRENT_TALENT_TREE = 0;
                if (mInterstitialAd.isLoaded()) {
                    if(interstitialAdCounter == 3) {
                        mInterstitialAd.show();
                        interstitialAdCounter = 0;
                    }
                else
                        interstitialAdCounter++;
                }
                Intent intent = new Intent(ClassMenuActivity.this, TalentTreeActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.classHunter: {
                StateManager.CURRENT_CLASS = Constants.Classes.HUNTER.ordinal();
                StateManager.CURRENT_TALENT_TREE = 0;
                if (mInterstitialAd.isLoaded()) {
                    if(interstitialAdCounter == 3) {
                        mInterstitialAd.show();
                        interstitialAdCounter = 0;
                    }
                    else
                        interstitialAdCounter++;
                }
                Intent intent = new Intent(ClassMenuActivity.this, TalentTreeActivity.class);
                startActivity(intent);

                break;
            }
            case R.id.classMage: {
                StateManager.CURRENT_CLASS = Constants.Classes.MAGE.ordinal();
                StateManager.CURRENT_TALENT_TREE = 0;
                if (mInterstitialAd.isLoaded()) {
                    if(interstitialAdCounter == 3) {
                        mInterstitialAd.show();
                        interstitialAdCounter = 0;
                    }
                    else
                        interstitialAdCounter++;
                }
                Intent intent = new Intent(ClassMenuActivity.this, TalentTreeActivity.class);
                startActivity(intent);

                break;
            }
            case R.id.classPaladin: {
                StateManager.CURRENT_CLASS = Constants.Classes.PALADIN.ordinal();
                StateManager.CURRENT_TALENT_TREE = 0;
                if (mInterstitialAd.isLoaded()) {
                    if(interstitialAdCounter == 3) {
                        mInterstitialAd.show();
                        interstitialAdCounter = 0;
                    }
                    else
                        interstitialAdCounter++;
                }
                Intent intent = new Intent(ClassMenuActivity.this, TalentTreeActivity.class);
                startActivity(intent);

                break;
            }
            case R.id.classPriest: {
                StateManager.CURRENT_CLASS = Constants.Classes.PRIEST.ordinal();
                StateManager.CURRENT_TALENT_TREE = 0;
                if (mInterstitialAd.isLoaded()) {
                    if(interstitialAdCounter == 3) {
                        mInterstitialAd.show();
                        interstitialAdCounter = 0;
                    }
                    else
                        interstitialAdCounter++;
                }
                Intent intent = new Intent(ClassMenuActivity.this, TalentTreeActivity.class);
                startActivity(intent);

                break;
            }
            case R.id.classRogue: {
                StateManager.CURRENT_CLASS = Constants.Classes.ROGUE.ordinal();
                StateManager.CURRENT_TALENT_TREE = 0;
                if (mInterstitialAd.isLoaded()) {
                    if(interstitialAdCounter == 3) {
                        mInterstitialAd.show();
                        interstitialAdCounter = 0;
                    }
                    else
                        interstitialAdCounter++;
                }
                Intent intent = new Intent(ClassMenuActivity.this, TalentTreeActivity.class);
                startActivity(intent);

                break;
            }
            case R.id.classShaman: {
                StateManager.CURRENT_CLASS = Constants.Classes.SHAMAN.ordinal();
                StateManager.CURRENT_TALENT_TREE = 0;
                if (mInterstitialAd.isLoaded()) {
                    if(interstitialAdCounter == 3) {
                        mInterstitialAd.show();
                        interstitialAdCounter = 0;
                    }
                    else
                        interstitialAdCounter++;
                }
                Intent intent = new Intent(ClassMenuActivity.this, TalentTreeActivity.class);
                startActivity(intent);

                break;
            }
            case R.id.classWarlock: {
                StateManager.CURRENT_CLASS = Constants.Classes.WARLOCK.ordinal();
                StateManager.CURRENT_TALENT_TREE = 0;
                if (mInterstitialAd.isLoaded()) {
                    if(interstitialAdCounter == 3) {
                        mInterstitialAd.show();
                        interstitialAdCounter = 0;
                    }
                    else
                        interstitialAdCounter++;
                }
                Intent intent = new Intent(ClassMenuActivity.this, TalentTreeActivity.class);
                startActivity(intent);

                break;
            }
            case R.id.classWarrior: {
                StateManager.CURRENT_CLASS = Constants.Classes.WARRIOR.ordinal();
                StateManager.CURRENT_TALENT_TREE = 0;
                if (mInterstitialAd.isLoaded()) {
                    if(interstitialAdCounter == 3) {
                        mInterstitialAd.show();
                        interstitialAdCounter = 0;
                    }
                    else
                        interstitialAdCounter++;
                }
                Intent intent = new Intent(ClassMenuActivity.this, TalentTreeActivity.class);
                startActivity(intent);

                break;
            }
            case R.id.classDk: {
                StateManager.CURRENT_CLASS = Constants.Classes.DK.ordinal();
                StateManager.CURRENT_TALENT_TREE = 0;
                if (mInterstitialAd.isLoaded()) {
                    if(interstitialAdCounter == 3) {
                        mInterstitialAd.show();
                        interstitialAdCounter = 0;
                    }
                    else
                        interstitialAdCounter++;
                }
                Intent intent = new Intent(ClassMenuActivity.this, TalentTreeActivity.class);
                startActivity(intent);

                break;
            }
            case R.id.loadButton: {
                showLoadDialog();
                break;
            }
        }
    }

    private void showLoadDialog() {
        final SharedPreferences preferences = getSharedPreferences(Constants.SHARED_PREFERENCE_NAMES[StateManager.CURRENT_EXPANSION], MODE_PRIVATE);

        AlertDialog.Builder builder = new AlertDialog.Builder(ClassMenuActivity.this);
        builder.setTitle("Select build to load");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(ClassMenuActivity.this, android.R.layout.select_dialog_singlechoice);

        for (String build : preferences.getAll().keySet()) {
            arrayAdapter.add(build);
        }

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String build = arrayAdapter.getItem(which);

                if (build == null || build.length() == 0) {
                    dialog.dismiss();
                    return;
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(ClassMenuActivity.this)
                        .setTitle(build)
                        .setMessage("What do you want to do with the build?")
                        .setNeutralButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                preferences.edit().remove(build).commit();
                                Toast.makeText(getApplicationContext(), "Build has been deleted.", Toast.LENGTH_SHORT).show();
                                dialogInterface.dismiss();
                                if (mInterstitialAd.isLoaded())
                                    mInterstitialAd.show();
                            }
                        })
                        .setPositiveButton("View", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int whichButton) {
                                String buildString = preferences.getString(build, "0");
                                int classBuild = Integer.parseInt(buildString.toCharArray()[0] + "");

                                StateManager.CURRENT_CLASS = classBuild;
                                StateManager.CURRENT_TALENT_TREE = 0;

                                int counter = 1;

                                for (int i = 0; i < StateManager.TALENT_TREES_STATE[StateManager.CURRENT_EXPANSION][classBuild].length; i++) {
                                    for (int j = 0; j < StateManager.TALENT_TREES_STATE[StateManager.CURRENT_EXPANSION][classBuild][i].length; j++) {
                                        StateManager.TALENT_TREES_STATE[StateManager.CURRENT_EXPANSION][classBuild][i][j] = Integer.parseInt(buildString.charAt(counter) + "");
                                        counter++;
                                    }
                                }
                                Intent intent = new Intent(ClassMenuActivity.this, TalentTreeActivity.class);
                                intent.putExtra("method","View");
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("Edit", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                String buildString = preferences.getString(build, "0");
                                int classBuild = Integer.parseInt(buildString.toCharArray()[0] + "");

                                StateManager.CURRENT_CLASS = classBuild;
                                StateManager.CURRENT_TALENT_TREE = 0;

                                int counter = 1;

                                for (int i = 0; i < StateManager.TALENT_TREES_STATE[StateManager.CURRENT_EXPANSION][classBuild].length; i++) {
                                    for (int j = 0; j < StateManager.TALENT_TREES_STATE[StateManager.CURRENT_EXPANSION][classBuild][i].length; j++) {
                                        StateManager.TALENT_TREES_STATE[StateManager.CURRENT_EXPANSION][classBuild][i][j] = Integer.parseInt(buildString.charAt(counter) + "");
                                        counter++;
                                    }
                                }
                                Intent intent = new Intent(ClassMenuActivity.this, TalentTreeActivity.class);
                                intent.putExtra("method","Edit");
                                intent.putExtra("build",build);
                                startActivity(intent);
                            }
                        });
                final AlertDialog alertDialog = builder.create();
                alertDialog.show();
                dialog.dismiss();
            }
        });
        builder.show();
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
