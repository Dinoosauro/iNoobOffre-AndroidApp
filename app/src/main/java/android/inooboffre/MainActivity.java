package android.inooboffre;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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
import android.text.Html;
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
    String appVersion = "2.0.0";
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
        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_view);
        TextView textView11 = findViewById(R.id.textView11);
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
                    URL url2 = new URL("https://raw.githubusercontent.com/Lorenzo-Effe/UpdateVersion/main/2e141d0254e3dd4117b9450c92118ec280f4b935-updatecode");
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
                                                    intent.setData(Uri.parse("https://github.com/Lorenzo-Effe/iNoobOffre-AndroidApp/releases/"));
                                                    startActivity(intent);
                                                }
                                            })
                                            .setNegativeButton(R.string.Ignore, null)
                                            .show();
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
                } else {
                    linearLayout.setVisibility(View.GONE);
                    linearLayout1.setVisibility(View.VISIBLE);
                    textView8.setText(R.string.Settings);
                }
                return true;
            }
        });
        textView8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialAlertDialogBuilder(MainActivity.this)
                        .setTitle(R.string.OpenSourceLicense)
                        .setMessage(R.string.OpenSourceDescription)
                        .setPositiveButton(R.string.Yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                intent.setData(Uri.parse("https://github.com/Lorenzo-Effe/iNoobOffre-AndroidApp/blob/main/OpenSourceLicense.md"));
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton(R.string.No, null)
                        .show();
            }
        });
        Switch switch2 = findViewById(R.id.switch2);
        SharedPreferences impostazioni = getApplicationContext().getSharedPreferences("iNoobOffre", 0);
        Switch switch3 = findViewById(R.id.switch3);

        Switch switch1 = findViewById(R.id.switch1);
        if (impostazioni.contains("DownloadFoto")) {
            if (impostazioni.getBoolean("DownloadFoto", true)) {
                switch3.setChecked(true);
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
        }
        if (!impostazioni.contains("TemplateText")) {
            SharedPreferences.Editor editor = impostazioni.edit();
            editor.putString("TemplateText", "⭐ **$NomeProdotto**\n\n↻👀 A Soli **$PrezzoNormale** invece di **$PrezzoConsigliato**↻↺👀 A Soli**$PrezzoNormale**↺ \n➡   $Link   ️️⬅️\n\n");
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
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText.setFocusable(false);
                switch3.setFocusable(false);
                final String[] lineMore = {"", ""};
                checkBox.setFocusable(false);
                Snackbar wow = Snackbar.make(view, R.string.DownloadingWebpageInfo, BaseTransientBottomBar.LENGTH_INDEFINITE);

                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String linkProdotto = lineMore[1];
                        String line = lineMore[0];
                        try {
                            wow.dismiss();
                        } catch (Exception ex) {
                            // null
                        }
                        Snackbar hello = Snackbar.make(view, R.string.AmazonConnection, BaseTransientBottomBar.LENGTH_INDEFINITE);
                        hello.show();
                        String LinkGeneraleAmazon = "";
                        String TitoloProdotto = "";
                        String LinkImmagineAmazon = "";
                        String PrezzoScontato = "";
                        String StelleProdotto = "";
                        String StelleRappresentazione = "☆☆☆☆☆";
                        String PrezzoConsigliato = "";
                        String PrezzoNormale = "";
                        boolean isAmazonChoice = false;
                        String NumberOfReviews = "0";
                        String AmazonChoice = "";
                        boolean ChooseOnlyFirst = true;
                        String totalLine = "";
                        try {
                            // Ottiene HTML della pagina
                            int FirsThing = 0;
                            int SkipThis = 0;
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
                                // Scraping della pagina Amazon Mobile
                                    if (line.contains("\\\" data-zoom-hires=\\\"")) {
                                        if (SkipThis == 0) {
                                            LinkImmagineAmazon = line.substring(line.indexOf("\\\" data-zoom-hires=\\\""));
                                            LinkImmagineAmazon = LinkImmagineAmazon.replace("\\\" data-zoom-hires=\\\"", "");
                                            LinkImmagineAmazon = LinkImmagineAmazon.substring(0, LinkImmagineAmazon.indexOf("\\\""));
                                             if (LinkImmagineAmazon.contains("_AC_")) {
                                                 LinkImmagineAmazon = LinkImmagineAmazon.substring(0, LinkImmagineAmazon.lastIndexOf("."));
                                                 LinkImmagineAmazon = LinkImmagineAmazon.substring(0, LinkImmagineAmazon.length() - 1);
                                                 LinkImmagineAmazon = LinkImmagineAmazon + ".jpg";
                                            }
                                            SkipThis = 1;
                                        }
                                    }
                                if (line.contains("data-feature-name=\\\"title\\\" data-template-name=\\\"title\\\" class=\\\"a-size-small a-color-secondary a-text-normal\\\">    \\u003Cspan id=\\\"title\\\" class=\\\"a-size-small\\\">")) {
                                    TitoloProdotto = line.substring(line.indexOf("data-feature-name=\\\"title\\\" data-template-name=\\\"title\\\" class=\\\"a-size-small a-color-secondary a-text-normal\\\">    \\u003Cspan id=\\\"title\\\" class=\\\"a-size-small\\\">"));
                                    TitoloProdotto = TitoloProdotto.replace("data-feature-name=\\\"title\\\" data-template-name=\\\"title\\\" class=\\\"a-size-small a-color-secondary a-text-normal\\\">    \\u003Cspan id=\\\"title\\\" class=\\\"a-size-small\\\"> ", "");
                                    // TitoloProdotto = Html.fromHtml(TitoloProdotto).toString();
                                    TitoloProdotto = TitoloProdotto.substring(0, TitoloProdotto.indexOf("\\u003C/span>"));
                                }
                                if (line.contains("\\u003Ch1 id=\\\"title\\\" class=\\\"a-size-medium\\\">")) {
                                    TitoloProdotto = line.substring(line.indexOf("\\u003Ch1 id=\\\"title\\\" class=\\\"a-size-medium\\\">"));
                                    TitoloProdotto = TitoloProdotto.replace("\\u003Ch1 id=\\\"title\\\" class=\\\"a-size-medium\\\">", "");
                                    TitoloProdotto = TitoloProdotto.substring(0, TitoloProdotto.indexOf("\\u003Cspan class="));
                                }
                                if (line.contains("\\u003Ci class=\\\"a-icon a-icon-star-mini a-star-mini-4-5\\\">\\u003Cspan class=\\\"a-icon-alt\\\">")) {
                                    StelleProdotto = line.substring(line.indexOf("\\u003Ci class=\\\"a-icon a-icon-star-mini"));
                                    StelleProdotto = StelleProdotto.replace("\\u003Ci class=\\\"a-icon a-icon-star-mini", "");
                                    StelleProdotto = StelleProdotto.substring(StelleProdotto.indexOf("span class=\\\"a-icon-alt\\\">"));
                                    StelleProdotto = StelleProdotto.replace("span class=\\\"a-icon-alt\\\">", "");
                                    StelleProdotto = StelleProdotto.substring(0, StelleProdotto.indexOf("\\u003C/span>"));
                                    Log.d("ciao", StelleProdotto);
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
                                }
                                if (line.contains("data-a-color=\\\"base\\\">\\u003Cspan class=\\\"a-offscreen\\\">")) {
                                    if (FirsThing == 0) {
                                        FirsThing = 1;
                                    } else {
                                        PrezzoScontato = line.substring(line.indexOf("data-a-color=\\\"base\\\">\\u003Cspan class=\\\"a-offscreen\\\">"));
                                        PrezzoScontato = PrezzoScontato.replace("data-a-color=\\\"base\\\">\\u003Cspan class=\\\"a-offscreen\\\">", "");
                                        PrezzoScontato = PrezzoScontato.substring(0, PrezzoScontato.indexOf("\\u003C/span>"));
                                    }
                                }
                                if (line.contains("\\u003Cspan class=\\\"a-price a-text-price inlineBlock\\\" data-a-size=\\\"s\\\" data-a-strike=\\\"true\\\" data-a-color=\\\"tertiary\\\">\\u003Cspan class=\\\"a-offscreen\\\">")) {
                                    PrezzoConsigliato = line.substring(line.indexOf("\\u003Cspan class=\\\"a-price a-text-price inlineBlock\\\" data-a-size=\\\"s\\\" data-a-strike=\\\"true\\\" data-a-color=\\\"tertiary\\\">\\u003Cspan class=\\\"a-offscreen\\\">"));
                                    PrezzoConsigliato = PrezzoConsigliato.replace("\\u003Cspan class=\\\"a-price a-text-price inlineBlock\\\" data-a-size=\\\"s\\\" data-a-strike=\\\"true\\\" data-a-color=\\\"tertiary\\\">\\u003Cspan class=\\\"a-offscreen\\\">", "");
                                    PrezzoConsigliato = PrezzoConsigliato.substring(0, PrezzoConsigliato.indexOf("\\u003C/span>"));
                                }
                                if (line.contains("\\u003Cspan> \\u003Cspan class=\\\"a-size-small a-color-secondary aok-align-center basisPrice\\\">Prezzo consigliato: \\u003Cspan class=\\\"a-price a-text-price\\\" data-a-size=\\\"s\\\" data-a-strike=\\\"true\\\" data-a-color=\\\"secondary\\\">\\u003Cspan class=\\\"a-offscreen\\\">")) {
                                    PrezzoConsigliato = line.substring(line.indexOf("\\u003Cspan> \\u003Cspan class=\\\"a-size-small a-color-secondary aok-align-center basisPrice\\\">Prezzo consigliato: \\u003Cspan class=\\\"a-price a-text-price\\\" data-a-size=\\\"s\\\" data-a-strike=\\\"true\\\" data-a-color=\\\"secondary\\\">\\u003Cspan class=\\\"a-offscreen\\\">"));
                                    PrezzoConsigliato = PrezzoConsigliato.replace("\\u003Cspan> \\u003Cspan class=\\\"a-size-small a-color-secondary aok-align-center basisPrice\\\">Prezzo consigliato: \\u003Cspan class=\\\"a-price a-text-price\\\" data-a-size=\\\"s\\\" data-a-strike=\\\"true\\\" data-a-color=\\\"secondary\\\">\\u003Cspan class=\\\"a-offscreen\\\">", "");
                                    PrezzoConsigliato = PrezzoConsigliato.substring(0, PrezzoConsigliato.indexOf("\\u003C/span>"));
                                }
                                if (line.contains("\\u003Cspan> \\u003Cspan class=\\\"a-size-small a-color-secondary aok-align-center basisPrice\\\">Prezzo precedente: \\u003Cspan class=\\\"a-price a-text-price\\\" data-a-size=\\\"s\\\" data-a-strike=\\\"true\\\" data-a-color=\\\"secondary\\\">\\u003Cspan class=\\\"a-offscreen\\\">")) {
                                    PrezzoConsigliato = line.substring(line.indexOf("\\u003Cspan> \\u003Cspan class=\\\"a-size-small a-color-secondary aok-align-center basisPrice\\\">Prezzo precedente: \\u003Cspan class=\\\"a-price a-text-price\\\" data-a-size=\\\"s\\\" data-a-strike=\\\"true\\\" data-a-color=\\\"secondary\\\">\\u003Cspan class=\\\"a-offscreen\\\">"));
                                    PrezzoConsigliato = PrezzoConsigliato.replace("\\u003Cspan> \\u003Cspan class=\\\"a-size-small a-color-secondary aok-align-center basisPrice\\\">Prezzo precedente: \\u003Cspan class=\\\"a-price a-text-price\\\" data-a-size=\\\"s\\\" data-a-strike=\\\"true\\\" data-a-color=\\\"secondary\\\">\\u003Cspan class=\\\"a-offscreen\\\">", "");
                                    PrezzoConsigliato = PrezzoConsigliato.substring(0, PrezzoConsigliato.indexOf("\\u003C/span>"));
                                }
                                if (line.contains("<span class=\"a-size-medium a-color-base price-strikethrough inline-show-experience margin-spacing aok-hidden notranslate\">")) {
                                    PrezzoConsigliato = line.substring(line.indexOf("<span class=\"a-size-medium a-color-base price-strikethrough inline-show-experience margin-spacing aok-hidden notranslate\">"));
                                    PrezzoConsigliato = PrezzoConsigliato.replace("<span class=\"a-size-medium a-color-base price-strikethrough inline-show-experience margin-spacing aok-hidden notranslate\">", "");
                                    PrezzoConsigliato = PrezzoConsigliato.substring(0, PrezzoConsigliato.indexOf("</span>"));
                                }
                                if (line.contains("\\u003Cspan class=\\\"a-price pitchPriceCssOverride\\\" data-a-size=\\\"xxl\\\" data-a-color=\\\"base\\\">\\u003Cspan class=\\\"a-offscreen\\\">")) {
                                    PrezzoNormale = line.substring(line.indexOf("\\u003Cspan class=\\\"a-price pitchPriceCssOverride\\\" data-a-size=\\\"xxl\\\" data-a-color=\\\"base\\\">\\u003Cspan class=\\\"a-offscreen\\\">"));
                                    PrezzoNormale = PrezzoNormale.replace("\\u003Cspan class=\\\"a-price pitchPriceCssOverride\\\" data-a-size=\\\"xxl\\\" data-a-color=\\\"base\\\">\\u003Cspan class=\\\"a-offscreen\\\">", "");
                                    PrezzoNormale = PrezzoNormale.substring(0, PrezzoNormale.indexOf("\\u003C/span>"));
                                }
                                if (line.contains("\\u003Cspan class=\\\"a-price aok-align-center reinventPricePriceToPayMargin priceToPay\\\" data-a-size=\\\"xxl\\\" data-a-color=\\\"base\\\">\\u003Cspan class=\\\"a-offscreen\\\">")) {
                                    PrezzoNormale = line.substring(line.indexOf("\\u003Cspan class=\\\"a-price aok-align-center reinventPricePriceToPayMargin priceToPay\\\" data-a-size=\\\"xxl\\\" data-a-color=\\\"base\\\">\\u003Cspan class=\\\"a-offscreen\\\">"));
                                    PrezzoNormale = PrezzoNormale.replace("\\u003Cspan class=\\\"a-price aok-align-center reinventPricePriceToPayMargin priceToPay\\\" data-a-size=\\\"xxl\\\" data-a-color=\\\"base\\\">\\u003Cspan class=\\\"a-offscreen\\\">", "");
                                    PrezzoNormale = PrezzoNormale.substring(0, PrezzoNormale.indexOf("\\u003C/span>"));
                                }
                                if (line.contains("\\u003Ctr>\\u003Ctd class=\\\"a-span1 a-color-secondary a-size-base a-text-right a-nowrap\\\">Saldi:\\u003C/td>\\u003Ctd>       \\u003Cspan class=\\\"a-price a-text-price a-size-medium apexPriceToPay\\\" data-a-size=\\\"b\\\" data-a-color=\\\"price\\\">\\u003Cspan class=\\\"a-offscreen\\\">")) {
                                    PrezzoNormale = line.substring(line.indexOf("\\u003Ctr>\\u003Ctd class=\\\"a-span1 a-color-secondary a-size-base a-text-right a-nowrap\\\">Saldi:\\u003C/td>\\u003Ctd>       \\u003Cspan class=\\\"a-price a-text-price a-size-medium apexPriceToPay\\\" data-a-size=\\\"b\\\" data-a-color=\\\"price\\\">\\u003Cspan class=\\\"a-offscreen\\\">"));
                                    PrezzoNormale = PrezzoNormale.replace("\\u003Ctr>\\u003Ctd class=\\\"a-span1 a-color-secondary a-size-base a-text-right a-nowrap\\\">Saldi:\\u003C/td>\\u003Ctd>       \\u003Cspan class=\\\"a-price a-text-price a-size-medium apexPriceToPay\\\" data-a-size=\\\"b\\\" data-a-color=\\\"price\\\">\\u003Cspan class=\\\"a-offscreen\\\">", "");
                                    PrezzoNormale = PrezzoNormale.substring(0, PrezzoNormale.indexOf("\\u003C/span>"));
                                }
                                if (line.contains("u003Ctbody>\\u003Ctr>\\u003Ctd class=\\\"a-span1 a-color-secondary a-size-base a-text-right a-nowrap\\\">Prezzo:\\u003C/td>\\u003Ctd class=\\\"a-color-secondary a-size-base\\\"> \\u003Cspan class=\\\"a-price a-text-price a-size-base\\\" data-a-size=\\\"b\\\" data-a-strike=\\\"true\\\" data-a-color=\\\"secondary\\\">\\u003Cspan class=\\\"a-offscreen\\\">")) {
                                    PrezzoConsigliato = line.substring(line.indexOf("u003Ctbody>\\u003Ctr>\\u003Ctd class=\\\"a-span1 a-color-secondary a-size-base a-text-right a-nowrap\\\">Prezzo:\\u003C/td>\\u003Ctd class=\\\"a-color-secondary a-size-base\\\"> \\u003Cspan class=\\\"a-price a-text-price a-size-base\\\" data-a-size=\\\"b\\\" data-a-strike=\\\"true\\\" data-a-color=\\\"secondary\\\">\\u003Cspan class=\\\"a-offscreen\\\">"));
                                    PrezzoConsigliato = PrezzoConsigliato.replace("u003Ctbody>\\u003Ctr>\\u003Ctd class=\\\"a-span1 a-color-secondary a-size-base a-text-right a-nowrap\\\">Prezzo:\\u003C/td>\\u003Ctd class=\\\"a-color-secondary a-size-base\\\"> \\u003Cspan class=\\\"a-price a-text-price a-size-base\\\" data-a-size=\\\"b\\\" data-a-strike=\\\"true\\\" data-a-color=\\\"secondary\\\">\\u003Cspan class=\\\"a-offscreen\\\">", "");
                                    PrezzoConsigliato = PrezzoConsigliato.substring(0, PrezzoConsigliato.indexOf("\\u003C/span>"));
                                }
                                if (line.contains("data-a-sheet=\\\"{&quot;name&quot;:&quot;amazons_choice_bottom_sheet&quot;,&quot;sheetType&quot;:&quot;web&quot;,&quot;preloadDomId&quot;:&quot;amazons_choice_bottom_sheet_content&quot;,&quot;closeType&quot;:&quot;icon&quot;,&quot;height&quot;:&quot;210&quot;}\\\">  \\u003Cspan class=\\\"a-size-small aok-float-left ac-badge-rectangle\\\"> \\u003Cspan class=\\\"ac-badge-text-primary ac-white\\\">")) {
                                    isAmazonChoice = true;
                                    AmazonChoice = line.substring(line.indexOf("data-a-sheet=\\\"{&quot;name&quot;:&quot;amazons_choice_bottom_sheet&quot;,&quot;sheetType&quot;:&quot;web&quot;,&quot;preloadDomId&quot;:&quot;amazons_choice_bottom_sheet_content&quot;,&quot;closeType&quot;:&quot;icon&quot;,&quot;height&quot;:&quot;210&quot;}\\\">  \\u003Cspan class=\\\"a-size-small aok-float-left ac-badge-rectangle\\\"> \\u003Cspan class=\\\"ac-badge-text-primary ac-white\\\">"));
                                    AmazonChoice = AmazonChoice.replace("data-a-sheet=\\\"{&quot;name&quot;:&quot;amazons_choice_bottom_sheet&quot;,&quot;sheetType&quot;:&quot;web&quot;,&quot;preloadDomId&quot;:&quot;amazons_choice_bottom_sheet_content&quot;,&quot;closeType&quot;:&quot;icon&quot;,&quot;height&quot;:&quot;210&quot;}\\\">  \\u003Cspan class=\\\"a-size-small aok-float-left ac-badge-rectangle\\\"> \\u003Cspan class=\\\"ac-badge-text-primary ac-white\\\">", "");
                                    AmazonChoice = AmazonChoice.substring(AmazonChoice.indexOf("\\u003C/span> \\u003Cspan class=\\\"aok-float-left ac-badge-triangle\\\">\\u003C/span>  \\u003Cspan class=\\\"ac-mobile-for-text\\\">\\n\\u003Cspan>"));
                                    AmazonChoice = AmazonChoice.replace("\\u003C/span> \\u003Cspan class=\\\"aok-float-left ac-badge-triangle\\\">\\u003C/span>  \\u003Cspan class=\\\"ac-mobile-for-text\\\">\\n\\u003Cspan>", "");
                                    AmazonChoice = AmazonChoice.substring(0, AmazonChoice.indexOf("\\u003C/span>"));
                                    AmazonChoice = AmazonChoice.replace("\\\"", "\"");
                                }
                                if (line.contains("\\u003Cspan class=\\\"a-size-mini cm-cr-review-stars-text-sm\\\">")) {
                                    NumberOfReviews = line.substring(line.indexOf("\\u003Cspan class=\\\"a-size-mini cm-cr-review-stars-text-sm\\\">"));
                                    NumberOfReviews = NumberOfReviews.replace("\\u003Cspan class=\\\"a-size-mini cm-cr-review-stars-text-sm\\\">", "");
                                    NumberOfReviews = NumberOfReviews.substring(0, NumberOfReviews.indexOf("\\u003C/span>"));
                                }

                            } catch (Exception ex) {
                                // null
                            }
                        CodiceProdottoAmazon = linkProdotto;
                        if (linkProdotto.contains("/dp/")) {
                            CodiceProdottoAmazon = linkProdotto.substring(linkProdotto.indexOf("/dp/"));
                            CodiceProdottoAmazon = CodiceProdottoAmazon.replace("/dp/", "");
                        } else if (linkProdotto.contains("gp/aw/d/")) {
                            CodiceProdottoAmazon = linkProdotto.substring(linkProdotto.indexOf("gp/aw/d/"));
                            CodiceProdottoAmazon = CodiceProdottoAmazon.replace("gp/aw/d/", "");

                        }
                        Log.d("ciao", CodiceProdottoAmazon);

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
                                        Snackbar.make(view, R.string.BitlyPositive, 4000).show();
                                    }
                                });
                            } catch (Exception e) {
                                e.printStackTrace();
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Snackbar.make(view, R.string.BitlyNegative, 4000).show();
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
                            if (ottieniTemplate.contains("✬")) {
                                ottieniTemplate = ottieniTemplate.replace("✬", "");
                                ottieniTemplate = ottieniTemplate.replace("$AmazonChoice", AmazonChoice);
                            } else {
                                String testoDaTogliere = ottieniTemplate.substring(ottieniTemplate.indexOf("✬") + 1);
                                testoDaTogliere = testoDaTogliere.substring(0, testoDaTogliere.indexOf("✬"));
                                testoDaTogliere = "✬" + testoDaTogliere + "✬";
                                ottieniTemplate = ottieniTemplate.replace(testoDaTogliere, "");
                            }
                        } else {
                            String testoDaTogliere = ottieniTemplate.substring(ottieniTemplate.indexOf("✬") + 1);
                            testoDaTogliere = testoDaTogliere.substring(0, testoDaTogliere.indexOf("✬"));
                            testoDaTogliere = "✬" + testoDaTogliere + "✬";
                            ottieniTemplate = ottieniTemplate.replace(testoDaTogliere, "");
                        }
                        ottieniTemplate = ottieniTemplate.replace("$StelleProdotto", StelleProdotto);
                        ottieniTemplate = ottieniTemplate.replace("$StelleRappresentazione", StelleRappresentazione);
                        ottieniTemplate = ottieniTemplate.replace("$NumberOfReviews", NumberOfReviews);
                        String TestoClipboard = ottieniTemplate;
                        // Configurazione iniziale per il download dell'immagine
                        DownloadManager downloadmanager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                        Uri uri = Uri.parse(LinkImmagineAmazon);
                        try {
                            DownloadManager.Request request = new DownloadManager.Request(uri);
                            request.setTitle(String.valueOf(R.string.ProductPicture));
                            request.setDescription(String.valueOf(R.string.DownloadingPhoto));
                            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
                        } catch (Exception ex) {
                            // handling in the future
                        }
                        // Copio negli appunti
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                                ClipData testoDaCopiare = ClipData.newPlainText("iNoobOffre-Text", TestoClipboard);
                                clipboard.setPrimaryClip(testoDaCopiare);
                            }
                        });
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
                                        Snackbar.make(view, R.string.ExitCase1, 3000).show();
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
                                        Snackbar.make(view, R.string.ExitCase2, 3000).show();
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
                            Uri uri2 = FileProvider.getUriForFile(MainActivity.this, BuildConfig.APPLICATION_ID + ".provider",cacheSaveFile);
                            sharingIntent.putExtra(Intent.EXTRA_STREAM, uri2);
                            startActivity(Intent.createChooser(sharingIntent, String.valueOf(R.string.ShareWithTelegram)));

                        }


                    }
                });
                WebView web = new WebView(MainActivity.this);
                WebSettings webSettings = web.getSettings();
                webSettings.setJavaScriptEnabled(true);
                LinearLayout rootLayout = new LinearLayout(MainActivity.this);
                rootLayout.addView(web);
                // first boolean = page has finished its download
                // second boolean = user has selected the positive button
                final boolean[] webPageInfo = {false, false};
                web.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 300));
                web.setWebViewClient(new WebViewClient() {
                    public void onPageFinished(WebView view, String url) {
                        lineMore[1] = url;
                        Log.d("ciao", lineMore[1]);
                        webPageInfo[0] = true;
                        if (webPageInfo[1]) {
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
                                    web.evaluateJavascript(
                                            "(function() { return ('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>'); })();",
                                            new ValueCallback<String>() {
                                                @Override
                                                public void onReceiveValue(String html) {
                                                    lineMore[0] = html;
                                                    thread.start();
                                                }
                                            });
                                } else {
                                    wow.show();
                                }
                            }
                        })
                        .setView(rootLayout)
                        .show();
            }
        });
    }

    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = true;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
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
            editor.putString("SaveDirectory", getPath(MainActivity.this, DocumentsContract.buildDocumentUriUsingTree(data.getData(), DocumentsContract.getTreeDocumentId(data.getData()))));
            Log.d("Save", data.getData().getPath());
            editor.apply();
        }

    }



    }