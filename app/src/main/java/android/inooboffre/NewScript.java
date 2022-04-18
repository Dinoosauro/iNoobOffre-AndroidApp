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
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

public class NewScript extends AppCompatActivity {

    private ActivityNewScriptBinding binding;
    private int getHeight = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     //   setSupportActionBar(findViewById(R.id.toolbar));
        binding = ActivityNewScriptBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        getHeight = navView.getHeight();
        SharedPreferences impostazioni = getApplicationContext().getSharedPreferences("iNoobOffre", 0);
        SharedPreferences.Editor editor = impostazioni.edit();
        editor.putBoolean("resetJustHappened", false);
        editor.apply();
        Snackbar snackbar = Snackbar.make(findViewById(R.id.nav_host_fragment_activity_new_script), R.string.InstructionWeb, 8000)
                .setAction(R.string.RestoreScript, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        editor.putString("TemplateText", "⭐ **$NomeProdotto**\n\n↻👀 A Soli **$PrezzoNormale** invece di **$PrezzoConsigliato**↻↺👀 A Soli**$PrezzoNormale**↺ \n➡   $Link   ️️⬅️\n\n");
                        editor.putString("firstEdit", "⭐ **$NomeProdotto**\n\n");
                        editor.putString("secondEdit", "👀 A Soli **$PrezzoNormale** invece di **$PrezzoConsigliato**");
                        editor.putString("thirdEdit", "👀 A Soli**$PrezzoNormale**");
                        editor.putString("fourthEdit"," \n➡   $Link   ️️⬅️\n\n");
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
                    isClickedOnce[0] = false;
                    floatingActionButton.setImageResource(R.drawable.ic_baseline_help_24);
                    floatingActionButton1.setVisibility(View.VISIBLE);

                } else {
                    isClickedOnce[0] = true;
                    floatingActionButton1.setVisibility(View.INVISIBLE);
                    floatingActionButton.setImageResource(R.drawable.ic_baseline_add_24);
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse("https://github.com/Lorenzo-Effe/iNoobOffre-AndroidApp/blob/main/CustomTemplateVariable.md"));
                    startActivity(i);
                    snackbar.show();
                }
            }
        });
        floatingActionButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Toglie il focus dall'ultima casella rimasta
                try {
                    EditText editText = findViewById(R.id.introduzione);
                    editText.clearFocus();
                } catch (Exception ex) {
                    // that's fine.
                }
                try {
                    EditText editText = findViewById(R.id.prezzoNonScontato);
                    editText.clearFocus();
                } catch (Exception ex) {
                    // that's fine.
                }
                try {
                    EditText editText = findViewById(R.id.prezzoScontato);
                    editText.clearFocus();
                } catch (Exception ex) {
                    // that's fine.
                }
                try {
                    EditText editText = findViewById(R.id.ParteFinale);
                    editText.clearFocus();
                } catch (Exception ex) {
                    // that's fine.
                }
                String firstEdit = "";
                if (impostazioni.contains("firstEdit")) {
                    firstEdit = impostazioni.getString("firstEdit", null);
                }
                String secondEdit = "";
                if (impostazioni.contains("secondEdit")) {
                    secondEdit = impostazioni.getString("secondEdit", null);
                }
                String thirdEdit = "";
                if (impostazioni.contains("thirdEdit")) {
                    thirdEdit = impostazioni.getString("thirdEdit", null);
                }
                String fourthEdit = "";
                if (impostazioni.contains("fourthEdit")) {
                    fourthEdit = impostazioni.getString("fourthEdit", null);
                }
                floatingActionButton1.setVisibility(View.INVISIBLE);
                String united = firstEdit + secondEdit + thirdEdit + fourthEdit;
                MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(NewScript.this);
                materialAlertDialogBuilder.setTitle(R.string.UnsavableScript);
                materialAlertDialogBuilder.setMessage(R.string.NotSupportedCharacters);
                if (united.contains("↻")) {
                    materialAlertDialogBuilder.show();
                } else if (united.contains("↺")) {
                    materialAlertDialogBuilder.show();
                } else {
                    String combineEverything = firstEdit + "\n↻" + secondEdit + "↻\n↺" + thirdEdit + "↺\n" + fourthEdit;
                    combineEverything = combineEverything.replace("\n", "\\n");
                    SharedPreferences impostazioni = getApplicationContext().getSharedPreferences("iNoobOffre", 0);
                    SharedPreferences.Editor editor = impostazioni.edit();
                    editor.putString("TemplateText", combineEverything);
                    Log.d("ciao", combineEverything);
                    editor.apply();
                    finish();
                }
            }
        });
    }
    public int getMyData() {
        return getHeight;
    }

}