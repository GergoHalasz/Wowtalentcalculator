package com.fissher.wowtc.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.fissher.wowtc.R;
import com.fissher.wowtc.manager.StateManager;
import com.fissher.wowtc.model.TalentTree;
import com.fissher.wowtc.utils.Constants;
import com.fissher.wowtc.utils.Utilities;

public class TalentTreeActivity extends AppCompatActivity implements View.OnTouchListener {

    private TalentTree talentTree;
    private InterstitialAd mInterstitialAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(!isConnected(this)) {
            showCustomDialog();
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

        switch (StateManager.CURRENT_EXPANSION) {
            case 0:
                switch(StateManager.CURRENT_CLASS) {
                    case 0: setContentView(R.layout.activity_druid); break;
                    case 1: setContentView(R.layout.activity_hunter); break;
                    case 2: setContentView(R.layout.activity_mage); break;
                    case 3: setContentView(R.layout.activity_paladin); break;
                    case 4: setContentView(R.layout.activity_priest); break;
                    case 5: setContentView(R.layout.activity_rogue); break;
                    case 6: setContentView(R.layout.activity_shaman); break;
                    case 7: setContentView(R.layout.activity_warlock); break;
                    case 8: setContentView(R.layout.activity_warrior); break;
                }
                break;
            case 1:
                switch(StateManager.CURRENT_CLASS) {
                    case 0: setContentView(R.layout.activity_druid_tbc); break;
                    case 1: setContentView(R.layout.activity_hunter_tbc); break;
                    case 2: setContentView(R.layout.activity_mage_tbc); break;
                    case 3: setContentView(R.layout.activity_paladin_tbc); break;
                    case 4: setContentView(R.layout.activity_priest_tbc); break;
                    case 5: setContentView(R.layout.activity_rogue_tbc); break;
                    case 6: setContentView(R.layout.activity_shaman_tbc); break;
                    case 7: setContentView(R.layout.activity_warlock_tbc); break;
                    case 8: setContentView(R.layout.activity_warrior_tbc); break;
                }
                break;
            case 2:
                switch(StateManager.CURRENT_CLASS) {
                    case 0: setContentView(R.layout.activity_druid_wotlk); break;
                    case 1: setContentView(R.layout.activity_hunter_wotlk); break;
                    case 2: setContentView(R.layout.activity_mage_wotlk); break;
                    case 3: setContentView(R.layout.activity_paladin_wotlk); break;
                    case 4: setContentView(R.layout.activity_priest_wotlk); break;
                    case 5: setContentView(R.layout.activity_rogue_wotlk); break;
                    case 6: setContentView(R.layout.activity_shaman_wotlk); break;
                    case 7: setContentView(R.layout.activity_warlock_wotlk); break;
                    case 8: setContentView(R.layout.activity_warrior_wotlk); break;
                    case 9: setContentView(R.layout.activity_dk_wotlk); break;
                }
                break;
        }

        init();
    }

    private void showCustomDialog() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(TalentTreeActivity.this);

        builder.setMessage("Please connect to the internet to proceed further. Click on the \"Connected\" button if you did connect to the internet.")
                .setCancelable(false)
                .setPositiveButton("Connected", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(!isConnected(TalentTreeActivity.this)) {
                            Toast.makeText(getApplicationContext(), "You are not connected to the internet.", Toast.LENGTH_SHORT).show();
                            builder.show();
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent myIntent = new Intent(TalentTreeActivity.this, ClassMenuActivity.class);
                        TalentTreeActivity.this.startActivity(myIntent);
                    }
                });
        builder.show();
    }

    private boolean isConnected(TalentTreeActivity classMenuActivity) {
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
        Intent intent = getIntent();
        String method = intent.getStringExtra("method");
        if(method != null) {
            if (!method.equals("View")) {
                final String build = intent.getStringExtra("build");
                final SharedPreferences preferences = getSharedPreferences(Constants.SHARED_PREFERENCE_NAMES[StateManager.CURRENT_EXPANSION], MODE_PRIVATE);
                int remainingPoints = talentTree.getRemainingPoints();
                if (StateManager.CURRENT_EXPANSION == Constants.Expansions.CLASSIC.ordinal() && remainingPoints < 51
                        || StateManager.CURRENT_EXPANSION == Constants.Expansions.TBC.ordinal() && remainingPoints < 61
                        || StateManager.CURRENT_EXPANSION == Constants.Expansions.WOTLK.ordinal() && remainingPoints < 71) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this)
                            .setTitle(build)
                            .setMessage("Do you want to save the build that you are currently editing?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    StringBuilder classStateStringBuilder = new StringBuilder(StateManager.CURRENT_CLASS + "");

                                    for (int[] talentTree : StateManager.getCurrentTalentClassState()) {
                                        for (Integer talent : talentTree) {
                                            classStateStringBuilder.append(talent);
                                        }
                                    }
                                    preferences.edit().putString(build, classStateStringBuilder.toString()).apply();
                                    Toast.makeText(getApplicationContext(), "Build has been saved.", Toast.LENGTH_SHORT).show();
                                    StateManager.resetCurrentClass();
                                    finish();
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    dialog.dismiss();
                                    StateManager.resetCurrentClass();
                                    finish();
                                }
                            });

                    final AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                } else {
                    finish();
                }
            }
            else {
                StateManager.resetCurrentClass();
                finish();
            }
        }
        else {
            int remainingPoints = talentTree.getRemainingPoints();
            if (StateManager.CURRENT_EXPANSION == Constants.Expansions.CLASSIC.ordinal() && remainingPoints < 51
                    || StateManager.CURRENT_EXPANSION == Constants.Expansions.TBC.ordinal() && remainingPoints < 61
                    || StateManager.CURRENT_EXPANSION == Constants.Expansions.WOTLK.ordinal() && remainingPoints < 71) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this)
                        .setTitle("Editing")
                        .setMessage("Do you want to save the build?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                showSaveDialog();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.dismiss();
                                StateManager.resetCurrentClass();
                                finish();
                            }
                        });

                final AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
            else
                finish();
        }
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

                if (Utilities.viewIdToTalentId(((View)view.getParent()).getId()) != -1 && talentTree.getTalent(Utilities.viewIdToTalentId(((View)view.getParent()).getId())).isDisabled())
                    talentTree.getTalent(Utilities.viewIdToTalentId(((View)view.getParent()).getId())).disable(true);

                processTouch(view);
                return true;
        }
        return false;
    }

    public void processTouch(View view) {
        switch (view.getId()) {
            case R.id.saveButton: {
                Intent intent = getIntent();
                String method = intent.getStringExtra("method");
                if(method != null)
                {
                    if(method.contains("Edit"))
                    {
                        showEditDialog();
                    }
                    else
                        showSaveDialog();
                }
                else
                showSaveDialog();
                break;
            }
            case R.id.openOnlineButton: {
                Uri uri = Uri.parse(Utilities.getWebUrlForCurrentBuild());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                break;
            }
            case R.id.resetAllButton: {
                talentTree.resetAll();
                break;
            }
            case R.id.resetTreeButton: {
                talentTree.reset();
                break;
            }
            case R.id.tree1: {
                if (StateManager.CURRENT_TALENT_TREE == 0) break;
                else {
                    StateManager.CURRENT_TALENT_TREE = 0;
                    talentTree.resetArrows();
                    init();
                    break;
                }
            }
            case R.id.tree2: {
                if (StateManager.CURRENT_TALENT_TREE == 1) break;
                else {
                    StateManager.CURRENT_TALENT_TREE = 1;
                    talentTree.resetArrows();
                    init();
                    break;
                }
            }
            case R.id.tree3: {
                if (StateManager.CURRENT_TALENT_TREE == 2) break;
                else {
                    StateManager.CURRENT_TALENT_TREE = 2;
                    talentTree.resetArrows();
                    init();
                    break;
                }
            }
            default: {
                Intent intent = getIntent();
                String method = intent.getStringExtra("method");
                talentTree.showDialog(this, Utilities.viewIdToTalentId(((View)view.getParent()).getId()),method);
                break;
            }
        }
    }

    private void showEditDialog(){
        Intent intent = getIntent();
        final String build = intent.getStringExtra("build");
        final SharedPreferences preferences = getSharedPreferences(Constants.SHARED_PREFERENCE_NAMES[StateManager.CURRENT_EXPANSION], MODE_PRIVATE);
        int remainingPoints = talentTree.getRemainingPoints();
        if (StateManager.CURRENT_EXPANSION == Constants.Expansions.CLASSIC.ordinal() && remainingPoints < 51
                || StateManager.CURRENT_EXPANSION == Constants.Expansions.TBC.ordinal() && remainingPoints < 61
                || StateManager.CURRENT_EXPANSION == Constants.Expansions.WOTLK.ordinal() && remainingPoints < 71) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this)
                    .setTitle(build)
                    .setMessage("Do you want to save the build that you are currently editing?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            StringBuilder classStateStringBuilder = new StringBuilder(StateManager.CURRENT_CLASS + "");

                            for (int[] talentTree : StateManager.getCurrentTalentClassState()) {
                                for (Integer talent : talentTree) {
                                    classStateStringBuilder.append(talent);
                                }
                            }
                            preferences.edit().putString(build, classStateStringBuilder.toString()).apply();
                            Toast.makeText(getApplicationContext(), "Build has been saved.", Toast.LENGTH_SHORT).show();
                            StateManager.resetCurrentClass();
                            finish();
                            if (mInterstitialAd.isLoaded())
                            mInterstitialAd.show();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            dialog.dismiss();
                            StateManager.resetCurrentClass();
                            finish();
                        }
                    });

            final AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
        else  Toast.makeText(getApplicationContext(), "Use at least one talent point to save your build.", Toast.LENGTH_LONG).show();
    }
    private void showSaveDialog() {
        final SharedPreferences preferences = getSharedPreferences(Constants.SHARED_PREFERENCE_NAMES[StateManager.CURRENT_EXPANSION], MODE_PRIVATE);
        final EditText input = new EditText(this);

        AlertDialog.Builder builder = new AlertDialog.Builder(this)
            .setTitle("Save Build")
            .setMessage("Enter a name for this build.")
            .setView(input)
            .setPositiveButton("Done", null)
            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    dialog.dismiss();
                }
            });

        final AlertDialog alertDialog = builder.create();

        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button button = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        String inputText = input.getText().toString();
                        String className = new String();
                        switch(StateManager.CURRENT_CLASS) {
                            case 0: className = "Druid"; break;
                            case 1: className = "Hunter"; break;
                            case 2: className = "Mage"; break;
                            case 3: className = "Paladin"; break;
                            case 4: className = "Priest"; break;
                            case 5: className = "Rogue"; break;
                            case 6: className = "Shaman"; break;
                            case 7: className = "Warlock"; break;
                            case 8: className = "Warrior"; break;
                            case 9: className = "Death knight"; break;
                        }
                        inputText = inputText + " - " + className;
                        if (!preferences.contains(inputText)) {
                            StringBuilder classStateStringBuilder = new StringBuilder(StateManager.CURRENT_CLASS + "");

                            for (int[] talentTree : StateManager.getCurrentTalentClassState()) {
                                for (Integer talent : talentTree) {
                                    classStateStringBuilder.append(talent);
                                }
                            }

                            if(inputText.length() != 0)
                            {
                                char firstLetter = inputText.charAt(0);
                                boolean isWhitespace = input.getText().toString().matches("^\\s*$");
                                if (isWhitespace) {
                                    Toast.makeText(getApplicationContext(), "Use a correct title format.", Toast.LENGTH_LONG).show();
                                } else if (!Character.isUpperCase(firstLetter)) {
                                    Toast.makeText(getApplicationContext(), "Build name needs to begin with a capital letter.", Toast.LENGTH_LONG).show();
                                } else {
                                    preferences.edit().putString(inputText.trim(), classStateStringBuilder.toString()).apply();
                                    Toast.makeText(getApplicationContext(), "Build has been saved.", Toast.LENGTH_SHORT).show();
                                    alertDialog.dismiss();
                                    StateManager.resetCurrentClass();
                                    finish();
                                    if (mInterstitialAd.isLoaded())
                                    mInterstitialAd.show();
                                }
                            }
                            else
                                Toast.makeText(getApplicationContext(), "Use a correct title format.", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Build with this name already exists. Please enter a different build name.", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

        alertDialog.show();
    }

    private void init() {
        talentTree = new TalentTree(this);

        initListeners();
        Utilities.initAndLoadAd(this);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initListeners() {
        View treesNavi = findViewById(R.id.treesNavigation);

        ImageButton tree1Button = treesNavi.findViewById(R.id.tree1);
        ImageButton tree2Button = treesNavi.findViewById(R.id.tree2);
        ImageButton tree3Button = treesNavi.findViewById(R.id.tree3);
        ImageButton resetTreesButton = treesNavi.findViewById(R.id.resetAllButton);
        tree1Button.setOnTouchListener(this);
        tree2Button.setOnTouchListener(this);
        tree3Button.setOnTouchListener(this);
        resetTreesButton.setOnTouchListener(this);

        View treeOptions = findViewById(R.id.treeOptions);
        ImageButton resetTreeButton = treeOptions.findViewById(R.id.resetTreeButton);
        ImageButton webButton = treeOptions.findViewById(R.id.openOnlineButton);
        ImageButton saveButton = treeOptions.findViewById(R.id.saveButton);
        Intent intent = getIntent();
        String method = intent.getStringExtra("method");
        if(method != null)
        {
            if(method.equals("View"))
            {
                resetTreeButton.setVisibility(View.GONE);
                saveButton.setVisibility(View.GONE);
                resetTreesButton.setVisibility(View.GONE);
                webButton.setVisibility(View.GONE);
            }
        }
        resetTreeButton.setOnTouchListener(this);
        webButton.setOnTouchListener(this);
        saveButton.setOnTouchListener(this);
    }
}
