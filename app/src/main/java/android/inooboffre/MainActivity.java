package android.inooboffre;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.core.content.res.ResourcesCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.ClipData;
import android.content.ClipboardManager;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.color.DynamicColors;

import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.Html;
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
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Method;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    String appVersion = "2.2.1";
    String CodiceProdottoAmazon = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.button);
        DynamicColors.applyToActivitiesIfAvailable(getApplication());
        EditText editText = findViewById(R.id.editTextTextPersonName);
        CheckBox checkBox = findViewById(R.id.checkBox);
        LinearLayout linearLayout = findViewById(R.id.linklayout);
        Button button2 = findViewById(R.id.button2);
        final Snackbar[] wow = new Snackbar[1];
        String[] lineMore = {"", ""};
        SharedPreferences impostazioni = getApplicationContext().getSharedPreferences("iNoobOffre", 0);
        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_view);
        Button button4 = findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = impostazioni.edit();
                MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(MainActivity.this);
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
        TextView textView11 = findViewById(R.id.textView11);
        FloatingActionButton fab = findViewById(R.id.deleteButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editText.getText().toString().equals("")) {
                    fab.setVisibility(View.VISIBLE);
                }
            }
        });
        Switch switch5 = findViewById(R.id.switch5);
        TextView textView18 = findViewById(R.id.textView18);
        Switch switch6 = findViewById(R.id.switch6);
        TextView textView24 = findViewById(R.id.textView24);
        textView24.setText("Version: " + appVersion);
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

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialAlertDialogBuilder(MainActivity.this)
                        .setNegativeButton(R.string.Cancel, null)
                        .setTitle(R.string.ChangeReferralTitle)
                        .setMessage(R.string.ChangeReferralDescription)
                        .setPositiveButton(R.string.Save, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
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
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewScript.class);
                startActivity(intent);

            }
        });
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url2 = new URL("https://raw.githubusercontent.com/Dinoosauro/UpdateVersion/main/2e141d0254e3dd4117b9450c92118ec280f4b935-updatecode");
                    BufferedReader reader2 = new BufferedReader(new InputStreamReader(url2.openStream()));

                    String line2 = "";
                    while ((line2 = reader2.readLine()) != null) {
                        line2 = line2.replace(".", "");
                        appVersion = appVersion.replace(".", "");
                        if (Integer.parseInt(line2) > Integer.parseInt(appVersion)) {
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
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getTitle().toString().equals("Link")) {
                    linearLayout.setVisibility(View.VISIBLE);
                    linearLayout1.setVisibility(View.GONE);
                    textView8.setText(R.string.app_name);
                    if (!editText.getText().toString().equals("")) {
                        fab.setVisibility(View.VISIBLE);
                    }
                } else {
                    linearLayout.setVisibility(View.GONE);
                    linearLayout1.setVisibility(View.VISIBLE);
                    textView8.setText(R.string.Settings);
                    fab.setVisibility(View.INVISIBLE);

                }
                return true;
            }
        });
        textView18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialAlertDialogBuilder(MainActivity.this)
                        .setTitle(R.string.OpenSourceLicense)
                        .setMessage(R.string.OpenSourceDescription)
                        .setPositiveButton(R.string.Libraries, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                    MaterialAlertDialogBuilder material = new MaterialAlertDialogBuilder(MainActivity.this);
                                    View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.opensourcewebview, null);
                                    WebView localWebView = view.findViewById(R.id.getLocalWeb);
                                localWebView.loadUrl("file:///android_asset/opensource.html");
                                localWebView.setWebViewClient(new WebViewClient() {
                                    @Override
                                    public boolean shouldOverrideUrlLoading(WebView v, String url) {
                                        v.loadUrl(url);
                                        return true;
                                    }
                                });
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
        Switch switch2 = findViewById(R.id.switch2);
        Switch switch3 = findViewById(R.id.switch3);

        Switch switch1 = findViewById(R.id.switch1);
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
            switch2.setChecked(true);
        }
        if (impostazioni.contains("OldDownloadPicker")) {
            if (impostazioni.getBoolean("OldDownloadPicker", true)) {
                switch5.setChecked(true);
            }
        }
        if (impostazioni.contains("AppTheme")) {
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
        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (switch1.isChecked()) {
                    if (!impostazioni.contains("bitlyConfigurato")) {
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
        switch3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (switch3.isChecked()) {
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                    }
                    SharedPreferences appSettings = getApplicationContext().getSharedPreferences("iNoobOffre", 0);
                    SharedPreferences.Editor editor = appSettings.edit();
                    editor.putBoolean("DownloadFoto", true);
                    editor.apply();
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
        switch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SharedPreferences appSettings = getApplicationContext().getSharedPreferences("iNoobOffre", 0);
                SharedPreferences.Editor editor = appSettings.edit();
                editor.putBoolean("FotoShare", switch2.isChecked());
                editor.apply();
            }
        });
        switch6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SharedPreferences appSettings = getApplicationContext().getSharedPreferences("iNoobOffre", 0);
                SharedPreferences.Editor editor = appSettings.edit();
                editor.putBoolean("CopyIntoClipboard", switch6.isChecked());
                editor.apply();
            }
        });
        switch5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SharedPreferences appSettings = getApplicationContext().getSharedPreferences("iNoobOffre", 0);
                SharedPreferences.Editor editor = appSettings.edit();
                editor.putBoolean("OldDownloadPicker", switch5.isChecked());
                editor.apply();
            }
        });
        final View[] view = new View[1];
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String linkProdotto = lineMore[1];
                String line = lineMore[0];
                try {
                    wow[0].dismiss();
                } catch (Exception ex) {
                    // null
                }
                Snackbar hello = Snackbar.make(view[0], R.string.AmazonConnection, BaseTransientBottomBar.LENGTH_INDEFINITE);
                hello.show();
                // Scrive la pagina HTML in un file di testo se l'opzione è attivata.
                Switch switch4 = findViewById(R.id.switch4);
                if (switch4.isChecked()) {
                    File newFileSave = new File(Environment.getExternalStorageDirectory() + "/iNoobOffre/" + "debugpage.txt");
                    File newFileSave2 = new File(Environment.getExternalStorageDirectory() + "/iNoobOffre");
                    try {
                        newFileSave2.mkdirs();
                        newFileSave.createNewFile();
                        int count;
                        InputStream input = new ByteArrayInputStream(line.getBytes(StandardCharsets.UTF_8));
                        FileOutputStream out = new FileOutputStream(newFileSave, true);
                        byte data[] = new byte[1024];

                        long total = 0;

                        while ((count = input.read(data)) != -1) {
                            total += count;
                            out.write(data, 0, count);
                        }
                        out.flush();
                        out.close();
                        input.close();

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                // Dichiarazione delle variabili utili all'applicazione ed inizializzazione della classe Web Scraper.
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
                boolean onlyFirstStar = false;

                    // Scraping della pagina Amazon Mobile


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
                if (linkProdotto.contains("/dp/")) {
                    CodiceProdottoAmazon = linkProdotto.substring(linkProdotto.indexOf("/dp/"));
                    CodiceProdottoAmazon = CodiceProdottoAmazon.replace("/dp/", "");
                } else if (linkProdotto.contains("gp/aw/d/")) {
                    CodiceProdottoAmazon = linkProdotto.substring(linkProdotto.indexOf("gp/aw/d/"));
                    CodiceProdottoAmazon = CodiceProdottoAmazon.replace("gp/aw/d/", "");

                }
                CodiceProdottoAmazon = CodiceProdottoAmazon.substring(0, 10);
                String getAmazonDomain = lineMore[1].substring(lineMore[1].indexOf("amazon"));
                getAmazonDomain = getAmazonDomain.replace("amazon.", "");
                getAmazonDomain = getAmazonDomain.substring(0, getAmazonDomain.indexOf("/"));
                String promoCodeApplied = "https://www.amazon." + getAmazonDomain + "/dp/" + CodiceProdottoAmazon + "/?tag=" + impostazioni.getString("ReferralLink", null);
                // Chiamata alle API Bitly
                if (switch1.isChecked()) {
                    try {
                        OkHttpClient client = new OkHttpClient();
                        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                        String JsonRequest = "{ \"long_url\": \"" + promoCodeApplied + "\" }";
                        RequestBody formBody = RequestBody.create(JSON, JsonRequest);
                        SharedPreferences preferences = MainActivity.this.getSharedPreferences("iNoobOffre", Context.MODE_PRIVATE);
                        String ApiToken = preferences.getString("bitlyAPI", null);
                        String AuthorizationGet = "Bearer " + ApiToken;
                        Request request = new Request.Builder()
                                .url("https://api-ssl.bitly.com/v4/shorten")
                                .addHeader("Content-Type", "application/json; utf-8")
                                .addHeader("Accept", "application/json")
                                .addHeader("Authorization", AuthorizationGet)
                                .post(formBody)
                                .build();
                        Response response = client.newCall(request).execute();
                        String getResponse = response.body().string();
                        String ShortenedAmazonLink = getResponse.substring(getResponse.indexOf("\"link\":\""));
                        ShortenedAmazonLink = ShortenedAmazonLink.replace("\"link\":\"", "");
                        ShortenedAmazonLink = ShortenedAmazonLink.substring(0, ShortenedAmazonLink.indexOf("\""));
                        promoCodeApplied = ShortenedAmazonLink;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Snackbar.make(view[0], R.string.BitlyPositive, 4000).show();
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Snackbar.make(view[0], R.string.BitlyNegative, 4000).show();
                            }
                        });
                    }
                }
                File cacheSaveFile = new File(getApplicationContext().getExternalCacheDir().getPath(), "/iNoobOffre/Cache/" + CodiceProdottoAmazon + ".jpg");
                File cacheSaveFile1 = new File(getApplicationContext().getExternalCacheDir().getPath() + "/iNoobOffre/Cache/");
                // Preparo testo da copiare
                String ParteASoli = "";
                String ottieniTemplate = impostazioni.getString("TemplateText", null);
                Log.d("ciao", ottieniTemplate);
                ottieniTemplate = ottieniTemplate.replace("$NomeProdotto", TitoloProdotto);

                if (checkBox.isChecked()) {
                    String dataToReplace = ottieniTemplate.substring(ottieniTemplate.indexOf("↻"));
                    dataToReplace = dataToReplace.substring(0, dataToReplace.lastIndexOf("↻"));
                    dataToReplace = dataToReplace + "↻";
                    ottieniTemplate = ottieniTemplate.replace(dataToReplace, "");
                    ottieniTemplate = ottieniTemplate.replace("$PrezzoNormale", PrezzoNormale);
                } else {
                    String dataToReplace = ottieniTemplate.substring(ottieniTemplate.indexOf("↺"));
                    dataToReplace = dataToReplace.substring(0, dataToReplace.lastIndexOf("↺"));
                    dataToReplace = dataToReplace + "↺";
                    ottieniTemplate = ottieniTemplate.replace(dataToReplace, "");
                    ottieniTemplate = ottieniTemplate.replace("$PrezzoConsigliato", PrezzoConsigliato);
                    ottieniTemplate = ottieniTemplate.replace("$PrezzoNormale", PrezzoNormale);
                }
                ottieniTemplate = ottieniTemplate.replace("↻", "");
                ottieniTemplate = ottieniTemplate.replace("↺", "");
                ottieniTemplate = ottieniTemplate.replace("$Link", promoCodeApplied);
                ottieniTemplate = ottieniTemplate.replace("\\n", "\n");
                if (isAmazonChoice) {
                    if (ottieniTemplate.contains("◘")) {
                        ottieniTemplate = ottieniTemplate.replace("◘", "");
                        ottieniTemplate = ottieniTemplate.replace("$AmazonChoice", AmazonChoice);
                    } else {
                        // null
                    }
                } else if (ottieniTemplate.contains("◘")) {
                    String testoDaTogliere = ottieniTemplate.substring(ottieniTemplate.indexOf("◘") + 1);
                    testoDaTogliere = testoDaTogliere.substring(0, testoDaTogliere.indexOf("◘"));
                    testoDaTogliere = "◘" + testoDaTogliere + "◘";
                    ottieniTemplate = ottieniTemplate.replace(testoDaTogliere, "");
                }
                ottieniTemplate = ottieniTemplate.replace("$StelleProdotto", StelleProdotto);
                ottieniTemplate = ottieniTemplate.replace("$StelleRappresentazione", StelleRappresentazione);
                ottieniTemplate = ottieniTemplate.replace("$NumberOfReviews", NumberOfReviews);
                ottieniTemplate = ottieniTemplate.replace("&nbsp;", " ");
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
                // Configurazione iniziale per il download dell'immagine
                DownloadManager downloadmanager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                Uri uri = Uri.parse(LinkImmagineAmazon);
                try {
                    DownloadManager.Request request = new DownloadManager.Request(uri);
                    request.setTitle(String.valueOf(R.string.ProductPicture));
                    request.setDescription(String.valueOf(R.string.DownloadingPhoto));
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "iNoobOffre_SavedPhotos/" + CodiceProdottoAmazon + ".jpg");
                    if (switch5.isChecked()) {
                        downloadmanager.enqueue(request);
                    }
                } catch (Exception ex) {
                    // handling in the future
                }
                // Copio negli appunti
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

                if (switch3.isChecked()) {
                    try {
                        int count;
                        URL url = new URL(LinkImmagineAmazon);
                        URLConnection connection = url.openConnection();
                        connection.connect();
                        InputStream input = new BufferedInputStream(url.openStream(),
                                8192);
                        File saveFile = new File(impostazioni.getString("SaveDirectory", null));
                        saveFile.mkdirs();
                        File saveFile1 = new File(impostazioni.getString("SaveDirectory", null) + "/" + CodiceProdottoAmazon + ".jpg");
                        saveFile1.createNewFile();
                        FileOutputStream output = new FileOutputStream(saveFile1, false);

                        byte data[] = new byte[1024];

                        long total = 0;

                        while ((count = input.read(data)) != -1) {
                            total += count;
                            output.write(data, 0, count);
                        }
                        output.flush();
                        output.close();
                        input.close();

                    } catch (Exception e) {
                        Log.e("Error: ", e.getMessage());
                    }
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
                        editText.setFocusable(true);
                        switch3.setFocusable(true);
                        checkBox.setSelected(false);
                        checkBox.setFocusable(true);
                    }
                });
                if (switch2.isChecked()) {
                    try {
                        int count;
                        URL url = new URL(LinkImmagineAmazon);
                        URLConnection connection = url.openConnection();
                        connection.connect();
                        InputStream input = new BufferedInputStream(url.openStream(),
                                8192);
                        cacheSaveFile1.mkdirs();
                        cacheSaveFile.createNewFile();
                        FileOutputStream output = new FileOutputStream(new File(cacheSaveFile.getAbsolutePath().toString()), false);

                        byte data[] = new byte[1024];

                        long total = 0;

                        while ((count = input.read(data)) != -1) {
                            total += count;
                            output.write(data, 0, count);
                        }
                        output.flush();
                        output.close();
                        input.close();

                    } catch (Exception e) {
                        // Log.e("Error: ", e.getMessage());
                    }

                    Intent sharingIntent = new Intent(Intent.ACTION_VIEW);
                    sharingIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    sharingIntent.setAction(Intent.ACTION_SEND);
                    sharingIntent.setType("image/jpeg");
                    sharingIntent.setType("text/plain");
                    Uri uri2 = FileProvider.getUriForFile(MainActivity.this, BuildConfig.APPLICATION_ID + ".provider", cacheSaveFile);
                    sharingIntent.putExtra(Intent.EXTRA_STREAM, uri2);
                    sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, ottieniTemplate);
                    startActivity(Intent.createChooser(sharingIntent, MainActivity.this.getResources().getString(R.string.ShareWithTelegram)));

                }


            }
        });
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
        final boolean[] webPageInfo = {false, false, false};
        web.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 300));
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
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View newview) {
                //editText.setFocusable(false);
                switch3.setFocusable(false);
                checkBox.setFocusable(false);
                wow[0] = Snackbar.make(newview, R.string.DownloadingWebpageInfo, BaseTransientBottomBar.LENGTH_INDEFINITE);
                view[0] = newview;
                loadWeb.run();
            }
        });
        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {
                editText.setText(intent.getStringExtra(Intent.EXTRA_TEXT), TextView.BufferType.EDITABLE);
                //editText.setFocusable(false);
                switch3.setFocusable(false);
                checkBox.setFocusable(false);
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
        //Clear the Activity's bundle of the subsidiary fragments' bundles.
        outState.clear();
    }




}