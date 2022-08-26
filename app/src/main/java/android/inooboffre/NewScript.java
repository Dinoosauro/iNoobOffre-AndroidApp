package android.inooboffre;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.RecyclerView;

import android.inooboffre.databinding.ActivityNewScriptBinding;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.LinearLayout;

public class NewScript extends AppCompatActivity {

    private ActivityNewScriptBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     //   setSupportActionBar(findViewById(R.id.toolbar));
        binding = ActivityNewScriptBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        SharedPreferences impostazioni = getApplicationContext().getSharedPreferences("iNoobOffre", 0);
        SharedPreferences.Editor editor = impostazioni.edit();
        // The user clicked on the button, so no reset happened.
        editor.putBoolean("resetJustHappened", false);
        editor.apply();
        // Set up a snackbar that restores the default script.
        Snackbar snackbar = Snackbar.make(findViewById(R.id.nav_host_fragment_activity_new_script), R.string.InstructionWeb, 8000)
                .setAction(R.string.RestoreScript, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Rewrite every string to the default one.
                        editor.putString("TemplateText", "⭐ **$NomeProdotto**\n\n↻👀 A Soli **$PrezzoNormale** invece di **$PrezzoConsigliato**↻↺👀 A Soli**$PrezzoNormale**↺ \n➡️   $Link   ️️⬅️\n\n");
                        editor.putString("firstEdit", "⭐ **$NomeProdotto**\n\n");
                        editor.putString("secondEdit", "👀 A Soli **$PrezzoNormale** invece di **$PrezzoConsigliato**");
                        editor.putString("thirdEdit", "👀 A Soli**$PrezzoNormale**");
                        editor.putString("fourthEdit"," \n➡️   $Link   ️️⬅️\n\n");
                        editor.putBoolean("resetJustHappened", true);
                        editor.apply();
                        finish();
                    }
                });
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_new_script);
        // NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
        FloatingActionButton floatingActionButton = findViewById(R.id.floatingActionButton);
        final boolean[] isClickedOnce = {true};
        FloatingActionButton floatingActionButton1 = findViewById(R.id.floatingActionButton2);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isClickedOnce[0]) {
                    // Button is clicked once, so change the first floating button's icon for help resource and then make visible the save button.
                    isClickedOnce[0] = false;
                    floatingActionButton.setImageResource(R.drawable.ic_baseline_help_24);
                    floatingActionButton1.setVisibility(View.VISIBLE);

                } else {
                    // Button is clicked twice, so the user asked to see help resource.
                    isClickedOnce[0] = true;
                    floatingActionButton1.setVisibility(View.INVISIBLE);
                    floatingActionButton.setImageResource(R.drawable.ic_baseline_add_24);
                    // Show an AlertDialog where templeate indications are listed.
                    MaterialAlertDialogBuilder material = new MaterialAlertDialogBuilder(NewScript.this);
                    View view2 = LayoutInflater.from(NewScript.this).inflate(R.layout.opensourcewebview, null);
                    WebView localWebView = view2.findViewById(R.id.getLocalWeb);
                    localWebView.loadUrl("file:///android_asset/templateindication.html");
                    localWebView.setWebViewClient(new WebViewClient() {
                        @Override
                        public boolean shouldOverrideUrlLoading(WebView v, String url) {
                            v.loadUrl(url);
                            return true;
                        }
                    });
                    material.setView(view2);
                    material.create().show();
                    // Show the snackbar that permit to reset the script to the previous one
                    snackbar.show();
                }
            }
        });
        floatingActionButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Change focus to the various EditTexts
                clearFocusOf(findViewById(R.id.introduzione));
                clearFocusOf(findViewById(R.id.prezzoNonScontato));
                clearFocusOf(findViewById(R.id.prezzoScontato));
                clearFocusOf(findViewById(R.id.ParteFinale));
                // Get the template of various parts
                String firstEdit = getEdits("firstEdit", impostazioni);
                String secondEdit = getEdits("secondEdit", impostazioni);
                String thirdEdit = getEdits("thirdEdit", impostazioni);
                String fourthEdit = getEdits("fourthEdit", impostazioni);
                floatingActionButton1.setVisibility(View.INVISIBLE);
                // Create an alert dialog that notifies the user that illegal characters are present.
                String united = firstEdit + secondEdit + thirdEdit + fourthEdit;
                MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(NewScript.this);
                materialAlertDialogBuilder.setTitle(R.string.UnsavableScript);
                materialAlertDialogBuilder.setMessage(R.string.NotSupportedCharacters);
                if (united.contains("↻")) {
                    materialAlertDialogBuilder.show();
                } else if (united.contains("↺")) {
                    materialAlertDialogBuilder.show();
                } else {
                    // If everything is okay, save the script into a SharedPreference
                    String combineEverything = firstEdit + "\n↻" + secondEdit + "↻\n↺" + thirdEdit + "↺\n" + fourthEdit;
                    combineEverything = combineEverything.replace("\n", "\\n");
                    SharedPreferences.Editor editor = impostazioni.edit();
                    editor.putString("TemplateText", combineEverything);
                    Log.d("ciao", combineEverything);
                    editor.apply();
                    finish();
                }
            }
        });
    }
    public void clearFocusOf(EditText editText) {
        try {
            editText.clearFocus();
        } catch (Exception ex) {
            // that's fine.
        }
    }
    public String getEdits(String keyID, SharedPreferences impostazioni) {
        if (impostazioni.contains(keyID)) {
            return impostazioni.getString(keyID, null);
        } else {
            return "";
        }
    }


}