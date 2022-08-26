package android.inooboffre;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.ClipData;
import android.content.ClipboardManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.color.DynamicColors;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.android.material.elevation.SurfaceColors;
import com.google.android.material.materialswitch.MaterialSwitch;
import android.widget.TextView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    // Declare app version variable so that the app can notify the user of a new update
    String appVersion = "2.2.2";
    String CodiceProdottoAmazon = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Declare variables
        Button button = findViewById(R.id.button);
        // Ask Material You dynamic colors 
        DynamicColors.applyToActivitiesIfAvailable(getApplication());
        EditText editText = findViewById(R.id.editTextTextPersonName);
        LinearLayout linearLayout = findViewById(R.id.linklayout);
        Button button2 = findViewById(R.id.button2);
        // Create Snackbar for reading progress
        final Snackbar[] wow = new Snackbar[1];
        // Create string array that contains Amazon Link & HTML page
        String[] lineMore = {"", ""};
        // Get current app settings
        SharedPreferences impostazioni = getApplicationContext().getSharedPreferences("iNoobOffre", 0);
        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_view);
        bottomNavigationView.setBackgroundColor(SurfaceColors.SURFACE_3.getColor(this));
        findViewById(R.id.mainScroll).setBackgroundColor(SurfaceColors.SURFACE_1.getColor(this));
        findViewById(R.id.getThisColor2).setBackgroundColor(SurfaceColors.SURFACE_2.getColor(this));
        // button4 is the button that changes app theme (from light to dark mode and viceversa)
        Button button4 = findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = impostazioni.edit();
                // Create an alert dialog so that user can choose between light, dark and default mode
                MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(MainActivity.this);
                /*
                BUTTON ORDERS:
                Neutral button  | Default
                Positive button | Dark mode
                Negative button | Light mode
                */
                materialAlertDialogBuilder.setNeutralButton(R.string.SystemSettigns, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        editor.putString("AppTheme", "0");
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                        editor.apply();
                    }
                });
                materialAlertDialogBuilder.setPositiveButton(R.string.Dark, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        editor.putString("AppTheme", "1");
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                        editor.apply();
                    }
                });
                materialAlertDialogBuilder.setNegativeButton(R.string.Light, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        editor.putString("AppTheme", "2");
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                        editor.apply();

                    }
                });
                materialAlertDialogBuilder.setTitle(R.string.DarkTitle);
                materialAlertDialogBuilder.setMessage(R.string.DarkDescription);
                materialAlertDialogBuilder.show();
            }
        });
        // fab is the circular button that deletes input text.
        FloatingActionButton fab = findViewById(R.id.deleteButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the current text, so that the action can be undone.
                String recoveryText = editText.getText().toString();
                editText.setText("", TextView.BufferType.EDITABLE);
                fab.setVisibility(View.INVISIBLE);
                Snackbar deletedInfo = Snackbar.make(view, R.string.DeletedInput, 3000);
                deletedInfo.setAction(R.string.Undo, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        editText.setText(recoveryText, TextView.BufferType.EDITABLE);
                        deletedInfo.dismiss();
                        }
                });
                deletedInfo.show();

            }
        });
        // When something is written in the URL box, show the delete button.
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Make delete button visible if text isn't null.
                if (!editText.getText().toString().equals("")) {
                    fab.setVisibility(View.VISIBLE);
                }
            }
        });
        MaterialSwitch switch5 = findViewById(R.id.switch5);
        TextView textView18 = findViewById(R.id.textView18);
        MaterialSwitch switch6 = findViewById(R.id.switch6);
        // textView24 contains the version number of the application. 
        TextView textView24 = findViewById(R.id.textView24);
        textView24.setText("Version: " + appVersion);
        // if clicked, show the changelog
        textView24.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialAlertDialogBuilder(MainActivity.this)
                        .setView(R.layout.changelog)
                        .show();
            }
        });
        // Get intent, action and MIME type
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();
        // button2 permits to change the Amazon referral link
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Show Alert Dialog to change referral link
                new MaterialAlertDialogBuilder(MainActivity.this)
                        .setNegativeButton(R.string.Cancel, null)
                        .setTitle(R.string.ChangeReferralTitle)
                        .setMessage(R.string.ChangeReferralDescription)
                        .setPositiveButton(R.string.Save, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // Save referral link into SharedPreferences
                                SharedPreferences appSettings = getApplicationContext().getSharedPreferences("iNoobOffre", 0);
                                SharedPreferences.Editor editor = appSettings.edit();
                                TextView templateGet = ((androidx.appcompat.app.AlertDialog) dialogInterface).findViewById(android.R.id.text2);
                                editor.putString("ReferralLink", templateGet.getText().toString());
                                editor.apply();
                            }
                        })
                        .setView(R.layout.edit_text2)
                        .show();
            }
        });
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        LinearLayout linearLayout1 = findViewById(R.id.optionlayout);
        TextView textView8 = findViewById(R.id.titleText);
        Button button3 = findViewById(R.id.button3);
        // button3 permits to change the text template.
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open the new activity to change template.
                Intent intent = new Intent(MainActivity.this, NewScript.class);
                startActivity(intent);

            }
        });
        // Create a new thread that will check new updates.
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // Connect to GitHub to check if the version code is the same.
                    URL url2 = new URL("https://raw.githubusercontent.com/Dinoosauro/UpdateVersion/main/2e141d0254e3dd4117b9450c92118ec280f4b935-updatecode");
                    BufferedReader reader2 = new BufferedReader(new InputStreamReader(url2.openStream()));
                    String line2 = "";
                    while ((line2 = reader2.readLine()) != null) {
                        line2 = line2.replace(".", "");
                        appVersion = appVersion.replace(".", "");
                        if (Integer.parseInt(line2) > Integer.parseInt(appVersion)) {
                            // In this case, the app is old. A dialog will be shown so that the user can update the app via GitHub.
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    new MaterialAlertDialogBuilder(MainActivity.this)
                                            .setTitle(R.string.Update)
                                            .setMessage(R.string.UpdateDescription)
                                            .setPositiveButton(R.string.UpdatePositive, new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    Intent intent = new Intent(Intent.ACTION_VIEW);
                                                    intent.setData(Uri.parse("https://github.com/Dinoosauro/iNoobOffre-AndroidApp/releases/"));
                                                    startActivity(intent);
                                                }
                                            })
                                            .setNegativeButton(R.string.Ignore, null)
                                            .show();
                                }
                            });

                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    // Show changelog if the user has just updated the app.
                                    if (!impostazioni.contains("FirstTimeIn220")) {
                                        SharedPreferences.Editor editor = impostazioni.edit();
                                        editor.putBoolean("FirstTimeIn220", false);
                                        editor.apply();
                                        new MaterialAlertDialogBuilder(MainActivity.this)
                                                .setView(R.layout.changelog)
                                                .show();
                                    }
                                }
                            });
                        }
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
        thread2.start();
        // Edit visibility when the user change MainActivity section
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getTitle().toString().equals("Link")) {
                    // Show URL input UI
                    linearLayout.setVisibility(View.VISIBLE);
                    linearLayout1.setVisibility(View.GONE);
                    textView8.setText(R.string.app_name);
                    if (!editText.getText().toString().equals("")) {
                        fab.setVisibility(View.VISIBLE);
                    }
                } else {
                    // Show options UI
                    linearLayout.setVisibility(View.GONE);
                    linearLayout1.setVisibility(View.VISIBLE);
                    textView8.setText(R.string.Settings);
                    fab.setVisibility(View.INVISIBLE);

                }
                return true;
            }
        });
        // when textView18 is clicked, ask the user if he wants to see the source code or the used libraries.
        textView18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                    Positive | Show libraries
                    Negative | Show source code
                    Neutral  | Close dialog
                */
                new MaterialAlertDialogBuilder(MainActivity.this)
                        .setTitle(R.string.OpenSourceLicense)
                        .setMessage(R.string.OpenSourceDescription)
                        
                        .setPositiveButton(R.string.Libraries, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                    MaterialAlertDialogBuilder material = new MaterialAlertDialogBuilder(MainActivity.this);
                                    // Create a new view in the dialog, where a WebView is attacched.
                                    View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.opensourcewebview, null);
                                    WebView localWebView = view.findViewById(R.id.getLocalWeb);
                                    // Load local open source webpage into the WebView
                                localWebView.loadUrl("file:///android_asset/opensource.html");
                                localWebView.setWebViewClient(new WebViewClient() {
                                    @Override
                                    public boolean shouldOverrideUrlLoading(WebView v, String url) {
                                        v.loadUrl(url);
                                        return true;
                                    }
                                });
                                String getColor  = String.format("#%06X", (0xFFFFFF & SurfaceColors.SURFACE_4.getColor(MainActivity.this)));
                                localWebView.evaluateJavascript("function() { document.body.style.backgroundColor = " + getColor + ";}", null);
                                material.setView(view);
                                material.create().show();
                            }
                        })
                        .setNegativeButton(R.string.SourceCode, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                intent.setData(Uri.parse("https://github.com/Dinoosauro/iNoobOffre-AndroidApp"));
                                startActivity(intent);
                            }
                        })
                        .setNeutralButton(R.string.Close, null)
                        .show();
            }
        });
        MaterialSwitch switch2 = findViewById(R.id.switch2);
        MaterialSwitch switch3 = findViewById(R.id.switch3);

        MaterialSwitch switch1 = findViewById(R.id.switch1);
        // Set switches to checked if the user has edited settings.
        if (impostazioni.contains("DownloadFoto")) {
            if (impostazioni.getBoolean("DownloadFoto", true)) {
                switch3.setChecked(true);
            }
        }
        if (impostazioni.contains("CopyIntoClipboard")) {
            if (impostazioni.getBoolean("CopyIntoClipboard", true)) {
                switch6.setChecked(true);
            }
        }
        if (impostazioni.contains("BitlyAsDefault")) {
            if (impostazioni.getBoolean("BitlyAsDefault", true)) {
                switch1.setChecked(true);
            }
        }
        if (impostazioni.contains("FotoShare")) {
            if (impostazioni.getBoolean("FotoShare", true)) {
                switch2.setChecked(true);
            }
        } else {
            // Change the default value to true, even if the user hasn't configured it yet.
            switch2.setChecked(true);
        }
        if (impostazioni.contains("OldDownloadPicker")) {
            if (impostazioni.getBoolean("OldDownloadPicker", true)) {
                switch5.setChecked(true);
            }
        }
        if (impostazioni.contains("AppTheme")) {
            // Change the app theme to light or dark mode.
            if (impostazioni.getString("AppTheme", null).equals("1")) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else if (impostazioni.getString("AppTheme", null).equals("2")) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        }
        if (!impostazioni.contains("TemplateText")) {
            SharedPreferences.Editor editor = impostazioni.edit();
            editor.putString("TemplateText", "⭐ **$NomeProdotto**\n\n↻👀 A Soli **$PrezzoNormale** invece di **$PrezzoConsigliato**↻↺👀 A Soli**$PrezzoNormale**↺ \n➡️   $Link   ️️⬅️\n\n");
            editor.apply();
        }
        if (!impostazioni.contains("ReferralLink")) {
            SharedPreferences.Editor editor = impostazioni.edit();
            editor.putString("ReferralLink", "inoob02-21");
            editor.apply();
        }
        // switch1 configures Bitly as link shortener.
        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (switch1.isChecked()) {
                    if (!impostazioni.contains("bitlyConfigurato")) {
                        // The user hasn't put their Bitly API yet. A dialog will ask him to do that.
                        /*
                            Neutral  | Open the webpage where the user can get their dev API
                            Negative | Close the dialog without doing anything
                            Positive | Save the Bitly API 
                        */
                        new MaterialAlertDialogBuilder(MainActivity.this)
                                .setNeutralButton(R.string.ObtainToken, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Intent intent = new Intent(Intent.ACTION_VIEW);
                                        switch1.setChecked(false);
                                        intent.setData(Uri.parse("https://app.bitly.com/settings/api/"));
                                        startActivity(intent);
                                    }
                                })
                                .setNegativeButton(R.string.No, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        switch1.setChecked(false);
                                    }
                                })
                                .setTitle(R.string.BitlyIntegration)
                                .setMessage(R.string.BitlyDescription)
                                .setPositiveButton(R.string.Save, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        SharedPreferences appSettings = getApplicationContext().getSharedPreferences("iNoobOffre", 0);
                                        SharedPreferences.Editor editor = appSettings.edit();
                                        TextView editBitlyAPI = ((androidx.appcompat.app.AlertDialog) dialogInterface).findViewById(android.R.id.text1);
                                        editor.putString("bitlyAPI", editBitlyAPI.getText().toString());
                                        editor.putBoolean("bitlyConfigurato", true);
                                        editor.putBoolean("BitlyAsDefault", true);
                                        editor.apply();
                                    }
                                })
                                .setView(R.layout.edit_text)
                                .show();
                    }

                }

            }
        });
        // switch3 permits to save the product's photo into internal storage.
        switch3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (switch3.isChecked()) {
                    // Since from Android 10 the WRITE_EXTERNAL_STORAGE authorization isn't required, it'll be requested only on older devices.
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                    }
                    SharedPreferences appSettings = getApplicationContext().getSharedPreferences("iNoobOffre", 0);
                    SharedPreferences.Editor editor = appSettings.edit();
                    editor.putBoolean("DownloadFoto", true);
                    editor.apply();
                    // Ask the user where he wants the photo to be downloaded
                    Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                    startActivityForResult(intent, 300);
                } else {
                    SharedPreferences appSettings = getApplicationContext().getSharedPreferences("iNoobOffre", 0);
                    SharedPreferences.Editor editor = appSettings.edit();
                    editor.putBoolean("DownloadFoto", false);
                    editor.apply();
                }
            }
        });
        // switch2 permits to send the image and the description to Telegram
        switch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SharedPreferences appSettings = getApplicationContext().getSharedPreferences("iNoobOffre", 0);
                SharedPreferences.Editor editor = appSettings.edit();
                editor.putBoolean("FotoShare", switch2.isChecked());
                editor.apply();
            }
        });
        // switch6 permits to copy the product description into clipboard
        switch6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SharedPreferences appSettings = getApplicationContext().getSharedPreferences("iNoobOffre", 0);
                SharedPreferences.Editor editor = appSettings.edit();
                editor.putBoolean("CopyIntoClipboard", switch6.isChecked());
                editor.apply();
            }
        });
        // switch5 permits to use the old download picker if the new one doesn't work on the selected device.
        switch5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SharedPreferences appSettings = getApplicationContext().getSharedPreferences("iNoobOffre", 0);
                SharedPreferences.Editor editor = appSettings.edit();
                editor.putBoolean("OldDownloadPicker", switch5.isChecked());
                editor.apply();
            }
        });
        // Create View array, where the first view will be the one where the update Snackbar will be shown.
        final View[] view = new View[1];
        // The following thread will read the HTML file.
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String linkProdotto = lineMore[1];
                String line = lineMore[0];
                try {
                    // Dismiss the current snackbar before creating another one.
                    wow[0].dismiss();
                } catch (Exception ex) {
                    // null
                }
                Snackbar hello = Snackbar.make(view[0], R.string.AmazonConnection, BaseTransientBottomBar.LENGTH_INDEFINITE);
                hello.show();
                // Writes the HTML webpage in a .txt file when the option is enabled
                MaterialSwitch switch4 = findViewById(R.id.switch4);
                if (switch4.isChecked()) {
                    DebugPageWriter debugPageWriter = new DebugPageWriter();
                    debugPageWriter.DebugPage(line);
                }
                // These variables will be used to replace default values. 
                AmazonWebScraper amzn = new AmazonWebScraper();
                String TitoloProdotto = amzn.NomeProdotto(line);
                String LinkImmagineAmazon = amzn.ImmagineAmazon(line);
                String StelleProdotto = amzn.StelleDelProdotto(line);
                String StelleRappresentazione = "☆☆☆☆☆";
                String PrezzoConsigliato = amzn.PrezzoNonScontato(line);
                String PrezzoNormale = amzn.PrezzoCorrente(line);
                boolean isAmazonChoice = amzn.getIfAmazonChoice(line);
                String NumberOfReviews = amzn.NumeroRecensioni(line);
                String AmazonChoice = amzn.AmazonChoiceSearch(line);
                    // Create stars illustration
                    if (StelleProdotto.startsWith("1")) {
                        StelleRappresentazione = "★☆☆☆☆";
                    } else if (StelleProdotto.startsWith("2")) {
                        StelleRappresentazione = "★★☆☆☆";
                    } else if (StelleProdotto.startsWith("3")) {
                        StelleRappresentazione = "★★★☆☆";
                    } else if (StelleProdotto.startsWith("4")) {
                        StelleRappresentazione = "★★★★☆";
                    } else if (StelleProdotto.startsWith("5")) {
                        StelleRappresentazione = "★★★★★";
                    }

                    CodiceProdottoAmazon = linkProdotto;
                    // Get the Amazon product ID
                    if (line.contains("\\\"asin\\\":\\\"")) {
                        Log.d("ciao", "Using ASIN to get Amazon product code");
                        CodiceProdottoAmazon = line.substring(line.indexOf("\\\"asin\\\":\\\"")).replace("\\\"asin\\\":\\\"", "");
                        CodiceProdottoAmazon = CodiceProdottoAmazon.substring(0, CodiceProdottoAmazon.indexOf("\""));
                    } else if (linkProdotto.contains("/dp/")) {
                    CodiceProdottoAmazon = linkProdotto.substring(linkProdotto.indexOf("/dp/"));
                    CodiceProdottoAmazon = CodiceProdottoAmazon.replace("/dp/", "");
                    CodiceProdottoAmazon = CodiceProdottoAmazon.substring(0, 10);
                } else if (linkProdotto.contains("gp/aw/d/")) {
                    CodiceProdottoAmazon = linkProdotto.substring(linkProdotto.indexOf("gp/aw/d/"));
                    CodiceProdottoAmazon = CodiceProdottoAmazon.replace("gp/aw/d/", "");
                    CodiceProdottoAmazon = CodiceProdottoAmazon.substring(0, 10);
                }
                // Get the Amazon country webpage link
                String getAmazonDomain = lineMore[1].substring(lineMore[1].indexOf("amazon"));
                getAmazonDomain = getAmazonDomain.replace("amazon.", "");
                getAmazonDomain = getAmazonDomain.substring(0, getAmazonDomain.indexOf("/"));
                // Create a string with the link with the affiliation code applied.
                String promoCodeApplied = "https://www.amazon." + getAmazonDomain + "/dp/" + CodiceProdottoAmazon + "/?tag=" + impostazioni.getString("ReferralLink", null);
                // Calling Bitly API
                if (switch1.isChecked()) {
                    try {
                        // Get API Token
                        SharedPreferences preferences = MainActivity.this.getSharedPreferences("iNoobOffre", Context.MODE_PRIVATE);
                        String ApiToken = preferences.getString("bitlyAPI", null);
                        BitlyClient bitlyClient = new BitlyClient();
                        String bitlyResult = bitlyClient.BitlyConnection(promoCodeApplied, ApiToken);
                        if (!bitlyResult.equals("unvalid :(")) {
                            promoCodeApplied = bitlyResult;
                        // Since everything went as planned, the user will be informed.
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Snackbar.make(view[0], R.string.BitlyPositive, 4000).show();
                            }
                        });
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    // Inform the user that Bitly connection failed
                                    Snackbar.make(view[0], R.string.BitlyNegative, 4000).show();
                                }
                            });
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                // Create a cache file where the product image is stored
                File cacheSaveFile = new File(getApplicationContext().getExternalCacheDir().getPath(), "/iNoobOffre/Cache/" + CodiceProdottoAmazon + ".jpg");
                File cacheSaveFile1 = new File(getApplicationContext().getExternalCacheDir().getPath() + "/iNoobOffre/Cache/");
                // Get the description text, by replacing default values.
                String ParteASoli = "";
                String ottieniTemplate = impostazioni.getString("TemplateText", null);
                Log.d("ciao", ottieniTemplate);
                ottieniTemplate = ottieniTemplate.replace("$NomeProdotto", TitoloProdotto);
                // If there was no discount, choose the non-discounted format. 
                if (PrezzoConsigliato.equals("")) {
                    // Non-discounted format
                    String dataToReplace = ottieniTemplate.substring(ottieniTemplate.indexOf("↻"));
                    dataToReplace = dataToReplace.substring(0, dataToReplace.lastIndexOf("↻"));
                    dataToReplace = dataToReplace + "↻";
                    ottieniTemplate = ottieniTemplate.replace(dataToReplace, "");
                    ottieniTemplate = ottieniTemplate.replace("$PrezzoNormale", PrezzoNormale);
                } else {
                    // Discounted format
                    String dataToReplace = ottieniTemplate.substring(ottieniTemplate.indexOf("↺"));
                    dataToReplace = dataToReplace.substring(0, dataToReplace.lastIndexOf("↺"));
                    dataToReplace = dataToReplace + "↺";
                    ottieniTemplate = ottieniTemplate.replace(dataToReplace, "");
                    ottieniTemplate = ottieniTemplate.replace("$PrezzoConsigliato", PrezzoConsigliato);
                    ottieniTemplate = ottieniTemplate.replace("$PrezzoNormale", PrezzoNormale);
                }
                // Continue replacing other default values.
                ottieniTemplate = ottieniTemplate.replace("↻", "");
                ottieniTemplate = ottieniTemplate.replace("↺", "");
                ottieniTemplate = ottieniTemplate.replace("$Link", promoCodeApplied);
                ottieniTemplate = ottieniTemplate.replace("\\n", "\n");
                // If the product is Amazon Choice, load the Amazon Choice syntax
                if (isAmazonChoice) {
                    if (ottieniTemplate.contains("◘")) {
                        ottieniTemplate = ottieniTemplate.replace("◘", "");
                        ottieniTemplate = ottieniTemplate.replace("$AmazonChoice", AmazonChoice);
                    } else {
                        // null
                    }
                } else if (ottieniTemplate.contains("◘")) {
                    // Delete the Amazon Choice part if the item isn't Amazon's Choice
                    String testoDaTogliere = ottieniTemplate.substring(ottieniTemplate.indexOf("◘") + 1);
                    testoDaTogliere = testoDaTogliere.substring(0, testoDaTogliere.indexOf("◘"));
                    testoDaTogliere = "◘" + testoDaTogliere + "◘";
                    ottieniTemplate = ottieniTemplate.replace(testoDaTogliere, "");
                }
                // Continue replacing other default values
                ottieniTemplate = ottieniTemplate.replace("$StelleProdotto", StelleProdotto);
                ottieniTemplate = ottieniTemplate.replace("$StelleRappresentazione", StelleRappresentazione);
                ottieniTemplate = ottieniTemplate.replace("$NumberOfReviews", NumberOfReviews);
                ottieniTemplate = ottieniTemplate.replace("&nbsp;", " ");
                // Calc percentages if required by the template.
                if (ottieniTemplate.contains("$ScontoProdottoPercentuale")) {
                    if (!PrezzoNormale.equals("")&&!PrezzoConsigliato.equals("")) {
                        ottieniTemplate = ottieniTemplate.replace("$ScontoProdottoPercentuale", String.valueOf(100-(100 * Double.parseDouble(PrezzoNormale.replace(",", ".").replace("€", "").replace("&nbsp;", "")) / Double.parseDouble(PrezzoConsigliato.replace(",", ".").replace("€", "").replace("&nbsp;", "")))).substring(0, 2) + "%");
                    } else {
                        ottieniTemplate = ottieniTemplate.replace("$ScontoProdottoPercentuale", "0%");
                    }
                }
                if (ottieniTemplate.contains("$PercentualeProdottoScontatoSuProdotto")) {
                    if (!PrezzoNormale.equals("")&&!PrezzoConsigliato.equals("")) {
                        ottieniTemplate = ottieniTemplate.replace("$PercentualeProdottoScontatoSuProdotto", String.valueOf(100 * Double.parseDouble(PrezzoNormale.replace(",", ".").replace("€", "").replace("&nbsp;", "")) / Double.parseDouble(PrezzoConsigliato.replace(",", ".").replace("€", "").replace("&nbsp;", ""))).substring(0, 2) + "%");
                    } else {
                        ottieniTemplate = ottieniTemplate.replace("$PercentualeProdottoScontatoSuProdotto", "0%");
                    }

                }
                ottieniTemplate = ottieniTemplate.replace("\\\"", "\"");

                String TestoClipboard = ottieniTemplate;
                // Download the product image
                DownloadManager downloadmanager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                Uri uri = Uri.parse(LinkImmagineAmazon);
                try {
                    DownloadManager.Request request = new DownloadManager.Request(uri);
                    request.setTitle(String.valueOf(R.string.ProductPicture));
                    request.setDescription(String.valueOf(R.string.DownloadingPhoto));
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "iNoobOffre_SavedPhotos/" + CodiceProdottoAmazon + ".jpg");
                    if (switch5.isChecked()) {
                        // If the user required to save the image into external storage, download it now using DownloadMangaer
                        downloadmanager.enqueue(request);
                    }
                } catch (Exception ex) {
                    // handling in the future
                }
                // Copy into clipboard if requested by the user
                if (switch6.isChecked()) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                            ClipData testoDaCopiare = ClipData.newPlainText("iNoobOffre-Text", TestoClipboard);
                            clipboard.setPrimaryClip(testoDaCopiare);
                        }
                    });
                }
                // Save the image to the user's desired directory if he chose to.
                if (switch3.isChecked()) {
                    ModernSaveImage modernSaveImage = new ModernSaveImage();
                    modernSaveImage.SaveImage(LinkImmagineAmazon, impostazioni.getString("SaveDirectory", null), CodiceProdottoAmazon);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            hello.setText(R.string.ExitCase1);
                            try {
                                hello.dismiss();
                                Thread.sleep(3000);
                                Snackbar.make(view[0], R.string.ExitCase1, 3000).show();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    });


                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                hello.dismiss();
                                Thread.sleep(3000);
                                Snackbar.make(view[0], R.string.ExitCase2, 3000).show();
                                editText.setFocusable(true);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // Set focus of button & editText as their default ones
                        editText.setFocusable(true);
                        switch3.setFocusable(true);
                    }
                });
                // if the user should send the image to Telegram, a new file with the photo will be generated.
                if (switch2.isChecked()) {
                    ModernSaveImage modernSaveImage = new ModernSaveImage();
                    boolean continueSend = modernSaveImage.SaveImage(LinkImmagineAmazon, getApplicationContext().getExternalCacheDir().getPath() + "/iNoobOffre/Cache/", CodiceProdottoAmazon);
                    // Send photo and description to Telegram via intent
                    Intent sharingIntent = new Intent(Intent.ACTION_VIEW);
                    sharingIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    sharingIntent.setAction(Intent.ACTION_SEND);
                    sharingIntent.setType("text/plain");
                    if (continueSend) {
                        sharingIntent.setType("image/jpeg");
                        Uri uri2 = FileProvider.getUriForFile(MainActivity.this, BuildConfig.APPLICATION_ID + ".provider", cacheSaveFile);
                        sharingIntent.putExtra(Intent.EXTRA_STREAM, uri2);
                    }
                    sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, ottieniTemplate);
                    startActivity(Intent.createChooser(sharingIntent, MainActivity.this.getResources().getString(R.string.ShareWithTelegram)));

                }


            }
        });
        // The following thread will load the webpage in a WebView and then extract the HTML content
        Thread loadWeb = new Thread() {
            @Override
            public void run() {

        WebView web = new WebView(MainActivity.this);
        WebSettings webSettings = web.getSettings();
        webSettings.setJavaScriptEnabled(true);
        LinearLayout rootLayout = new LinearLayout(MainActivity.this);
        rootLayout.addView(web);
        // first boolean = page has finished its download
        // second boolean = user has selected the positive button
        // third boolean = the user has forced download | deprecated, doesn't work
        final boolean[] webPageInfo = {false, false, false};
        web.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 300));
        // If the user has selected the download button before the page finishes loading, wait the page to finish loading and then continue with the HTML extraction
        web.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
                lineMore[1] = url;
                Log.d("ciao", lineMore[1]);
                webPageInfo[0] = true;
                if (webPageInfo[1]) {
                    if (!webPageInfo[2])
                    web.evaluateJavascript(
                            "(function() { return ('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>'); })();",
                            new ValueCallback<String>() {
                                @Override
                                public void onReceiveValue(String html) {
                                    lineMore[0] = html;
                                    try {
                                        thread.start();
                                    } catch (Exception ex) {
                                        // will be handled
                                    }
                                }
                            });
                }
            }
        });
        // Stop loading the page and elaborate the HTML content.
        wow[0].setAction(R.string.ForceRead, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        webPageInfo[2] = true;
                        web.evaluateJavascript(
                                "(function() { return ('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>'); })();",
                                new ValueCallback<String>() {
                                    @Override
                                    public void onReceiveValue(String html) {
                                        lineMore[0] = html;
                                        try {
                                            thread.start();
                                        } catch (Exception ex) {
                                            ex.printStackTrace();
                                        }
                                    }
                                });

                    }
                });
        editText.setText(editText.getText().toString().substring(editText.getText().toString().indexOf("http")), TextView.BufferType.EDITABLE);
        // Load URL in WebView
        web.loadUrl(editText.getText().toString());
        new MaterialAlertDialogBuilder(MainActivity.this)
                .setNegativeButton(R.string.Cancel, null)
                .setTitle(R.string.AmazonPageLoading)
                .setMessage(R.string.AmazonPageDescription)
                .setPositiveButton(R.string.Continue, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        webPageInfo[1] = true;
                        if (webPageInfo[0]) {
                            if (!webPageInfo[2]) {
                                web.evaluateJavascript(
                                        "(function() { return ('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>'); })();",
                                        new ValueCallback<String>() {
                                            @Override
                                            public void onReceiveValue(String html) {
                                                lineMore[0] = html;
                                                thread.start();
                                            }
                                        });
                            }
                        } else {
                            wow[0].show();
                        }
                    }
                })
                .setView(rootLayout)
                .show();
            }
        };
        // when button is clicked, the HTML extraction process starts.
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View newview) {
                //editText.setFocusable(false);
                switch3.setFocusable(false);
                wow[0] = Snackbar.make(newview, R.string.DownloadingWebpageInfo, BaseTransientBottomBar.LENGTH_INDEFINITE);
                view[0] = newview;
                loadWeb.run();
            }
        });
        // If an app shared the link with iNoobOffre, get the link and start the HTML extraction process.
        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {
                editText.setText(intent.getStringExtra(Intent.EXTRA_TEXT), TextView.BufferType.EDITABLE);
                //editText.setFocusable(false);
                switch3.setFocusable(false);
                wow[0] = Snackbar.make(findViewById(R.id.linearLayout1), R.string.DownloadingWebpageInfo, BaseTransientBottomBar.LENGTH_INDEFINITE);
                view[0] = findViewById(R.id.linearLayout1);
                loadWeb.run();

            }
        }

    }



    @SuppressLint("WrongConstant")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 300) {
            // Get the directory where the user wants the product photo to be downloaded.
            final int takeFlags = data.getFlags()
                    & (Intent.FLAG_GRANT_READ_URI_PERMISSION
                    | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            getContentResolver().takePersistableUriPermission(data.getData(), takeFlags);
            SharedPreferences appSettings = getApplicationContext().getSharedPreferences("iNoobOffre", 0);
            SharedPreferences.Editor editor = appSettings.edit();
            editor.putString("SaveDirectory", getSelectedPathGist.getPath(MainActivity.this, DocumentsContract.buildDocumentUriUsingTree(data.getData(), DocumentsContract.getTreeDocumentId(data.getData()))));
            Log.d("Save", data.getData().getPath());
            editor.apply();
        }

    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Clear the Activity's bundle of the subsidiary fragments' bundles.
        outState.clear();
    }




}