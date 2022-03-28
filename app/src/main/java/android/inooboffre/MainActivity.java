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
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.snackbar.Snackbar;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    String appVersion = "1.3.0";
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
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        LinearLayout linearLayout1 = findViewById(R.id.optionlayout);
        TextView textView8 = findViewById(R.id.titleText);
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
                                            .setTitle("Aggiornamento disponibile")
                                            .setMessage("È disponibile un aggiornamento dell'app.")
                                            .setPositiveButton("Aggiorna", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    Intent intent = new Intent(Intent.ACTION_VIEW);
                                                    intent.setData(Uri.parse("https://github.com/Lorenzo-Effe/iNoobOffre-AndroidApp/releases/"));
                                                    startActivity(intent);
                                                }
                                            })
                                            .setNegativeButton("Ignora", null)
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
        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_view);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getTitle().toString().equals("Link")) {
                    linearLayout.setVisibility(View.VISIBLE);
                    linearLayout1.setVisibility(View.GONE);
                    textView8.setText("iNoobOffre");
                } else {
                    linearLayout.setVisibility(View.GONE);
                    linearLayout1.setVisibility(View.VISIBLE);
                    textView8.setText("Opzioni");
                }
                return true;
            }
        });
        textView8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialAlertDialogBuilder(MainActivity.this)
                        .setTitle("Licenza open source")
                        .setMessage("Questo software utilizza software open source. Vedere le condizioni?")
                        .setPositiveButton("OkHttp", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                intent.setData(Uri.parse("https://github.com/square/okhttp/blob/master/LICENSE.txt"));
                                startActivity(intent);
                            }
                        })
                        .setNeutralButton("Gist", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                intent.setData(Uri.parse("https://gist.github.com/asifmujteba/4878d98258ada6d1f1ac91bb4dbcce4b"));
                                startActivity(intent);

                            }
                        })
                        .setNegativeButton("Chiudi", null)
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
        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (switch1.isChecked()) {
                    if (!impostazioni.contains("bitlyConfigurato")) {
                        new MaterialAlertDialogBuilder(MainActivity.this)
                                .setNeutralButton("Ottieni token", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Intent intent = new Intent(Intent.ACTION_VIEW);
                                        switch1.setChecked(false);
                                        intent.setData(Uri.parse("https://app.bitly.com/settings/api/"));
                                        startActivity(intent);
                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        switch1.setChecked(false);
                                    }
                                })
                                .setTitle("Integrazione Bitly")
                                .setMessage("Genera link accorciati con Bitly. Per generare il link accorciato, è necessario ottenere un token dell'account, che permette la creazione di nuovi link.")
                                .setPositiveButton("Salva", new DialogInterface.OnClickListener() {
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
                String linkProdotto = editText.getText().toString();
                editText.setFocusable(false);
                switch3.setFocusable(false);
                checkBox.setFocusable(false);
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Snackbar hello = Snackbar.make(view, "Connessione ad Amazon. Questa opereazione potrebbe chiedere del tempo.", 25000);
                        hello.show();
                        String line;
                        String LinkGeneraleAmazon = "";
                        String TitoloProdotto = "";
                        String LinkImmagineAmazon = "";
                        String PrezzoScontato = "";
                        String PrezzoConsigliato = "";
                        String PrezzoNormale = "";
                        try {
                            // Ottiene HTML della pagina
                            URL url = new URL(editText.getText().toString());
                            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));

                            int FirsThing = 0;
                            int InterruptThis = 0;
                            while ((line = reader.readLine()) != null) {
                                // Scraping della pagina Amazon Mobile
                                if (InterruptThis == 0) {
                                    if (line.contains("<div alt=\"\" class=\"a-image-wrapper a-lazy-loaded a-manually-loaded immersive-carousel-img-manual-load\" data-a-image-source=\"https://m.media-amazon.com/images/I/")) {
                                        LinkImmagineAmazon = line.substring(line.indexOf("<div alt=\"\" class=\"a-image-wrapper a-lazy-loaded a-manually-loaded immersive-carousel-img-manual-load\" data-a-image-source=\"https://m.media-amazon.com/images/I/"));
                                        LinkImmagineAmazon = LinkImmagineAmazon.replace("<div alt=\"\" class=\"a-image-wrapper a-lazy-loaded a-manually-loaded immersive-carousel-img-manual-load\" data-a-image-source=\"https://m.media-amazon.com/images/I/", "");
                                        LinkImmagineAmazon = LinkImmagineAmazon.substring(0, LinkImmagineAmazon.indexOf("\" data-a-hires=\""));
                                        LinkImmagineAmazon = LinkImmagineAmazon.substring(0, LinkImmagineAmazon.indexOf("."));
                                        LinkImmagineAmazon = "https://m.media-amazon.com/images/I/" + LinkImmagineAmazon + "._AC_SY780_.jpg";
                                        InterruptThis = 1;
                                    }
                                }
                                if (line.contains("<meta name=\"title\" content=\"")) {
                                    TitoloProdotto = line.substring(line.indexOf("<meta name=\"title\" content=\""));
                                    TitoloProdotto = TitoloProdotto.replace("<meta name=\"title\" content=\"", "");
                                    TitoloProdotto = Html.fromHtml(TitoloProdotto).toString();
                                    TitoloProdotto = TitoloProdotto.substring(0, TitoloProdotto.lastIndexOf(": Amazon.it"));
                                }
                                if (line.contains("data-a-color=\"base\"><span class=\"a-offscreen\">")) {
                                    if (FirsThing == 0) {
                                        FirsThing = 1;
                                    } else {
                                        PrezzoScontato = line.substring(line.indexOf("data-a-color=\"base\"><span class=\"a-offscreen\">"));
                                        PrezzoScontato = PrezzoScontato.replace("data-a-color=\"base\"><span class=\"a-offscreen\">", "");
                                        PrezzoScontato = PrezzoScontato.substring(0, PrezzoScontato.indexOf("</span>"));
                                    }
                                }
                                if (line.contains("<span class=\"a-size-small a-color-secondary aok-align-center basisPrice\">Prezzo consigliato: <span class=\"a-price a-text-price\" data-a-size=\"s\" data-a-strike=\"true\" data-a-color=\"secondary\"><span class=\"a-offscreen\">")) {
                                    PrezzoConsigliato = line.substring(line.indexOf("<span class=\"a-size-small a-color-secondary aok-align-center basisPrice\">Prezzo consigliato: <span class=\"a-price a-text-price\" data-a-size=\"s\" data-a-strike=\"true\" data-a-color=\"secondary\"><span class=\"a-offscreen\">"));
                                    PrezzoConsigliato = PrezzoConsigliato.replace("<span class=\"a-size-small a-color-secondary aok-align-center basisPrice\">Prezzo consigliato: <span class=\"a-price a-text-price\" data-a-size=\"s\" data-a-strike=\"true\" data-a-color=\"secondary\"><span class=\"a-offscreen\">", "");
                                    PrezzoConsigliato = PrezzoConsigliato.substring(0, PrezzoConsigliato.indexOf("</span>"));
                                }
                                if (line.contains("Prezzo precedente:</td><td class=\"a-color-secondary a-size-base\">")) {
                                    PrezzoConsigliato = line.substring(line.indexOf("Prezzo precedente:</td><td class=\"a-color-secondary a-size-base\">"));
                                    PrezzoConsigliato = PrezzoConsigliato.replace("Prezzo precedente:</td><td class=\"a-color-secondary a-size-base\">", "");
                                    PrezzoConsigliato = PrezzoConsigliato.substring(0, PrezzoConsigliato.indexOf("</span>"));
                                    PrezzoConsigliato = PrezzoConsigliato.substring(PrezzoConsigliato.lastIndexOf("<span class=\"a-offscreen\">"));
                                    PrezzoConsigliato = PrezzoConsigliato.replace("<span class=\"a-offscreen\">", "");
                                }
                                if (line.contains("<span class=\"a-size-medium a-color-base price-strikethrough inline-show-experience margin-spacing aok-hidden notranslate\">")) {
                                    PrezzoConsigliato = line.substring(line.indexOf("<span class=\"a-size-medium a-color-base price-strikethrough inline-show-experience margin-spacing aok-hidden notranslate\">"));
                                    PrezzoConsigliato = PrezzoConsigliato.replace("<span class=\"a-size-medium a-color-base price-strikethrough inline-show-experience margin-spacing aok-hidden notranslate\">", "");
                                    PrezzoConsigliato = PrezzoConsigliato.substring(0, PrezzoConsigliato.indexOf("</span>"));
                                }
                                if (line.contains("\",\"priceAmount\":")) {
                                    PrezzoNormale = line.substring(0, line.indexOf("\",\"priceAmount\":"));
                                    PrezzoNormale = PrezzoNormale.substring(PrezzoNormale.indexOf("\"displayPrice\":\""));
                                    PrezzoNormale = PrezzoNormale.replace("\"displayPrice\":\"", "");
                                }

                            }
                        } catch (MalformedURLException e) {
                            // L'eccezione sarà gestita in un futuro update
                        } catch (IOException e) {
                            // L'eccezione sarà gestita in un futuro update
                        }
                        if (linkProdotto.contains("/dp/")) {
                            CodiceProdottoAmazon = linkProdotto.substring(linkProdotto.indexOf("/dp/"));
                            CodiceProdottoAmazon = CodiceProdottoAmazon.replace("/dp/", "");
                        } else if (linkProdotto.contains("gp/aw/d/")) {
                            CodiceProdottoAmazon = linkProdotto.substring(linkProdotto.indexOf("gp/aw/d/"));
                            CodiceProdottoAmazon = CodiceProdottoAmazon.replace("gp/aw/d/", "");

                        }
                        CodiceProdottoAmazon = CodiceProdottoAmazon.substring(0, 10);
                        String promoCodeApplied = "https://www.amazon.it/dp/" + CodiceProdottoAmazon + "/?tag=inoob02-21";
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
                                        Snackbar.make(view, "Connessione alle API Bitly riuscita con successo.", 4000).show();
                                    }
                                });
                            } catch (Exception e) {
                                e.printStackTrace();
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Snackbar.make(view, "Errore nella connessione alle API Bitly.", 4000).show();
                                    }
                                });
                            }
                        }
                        File cacheSaveFile = new File(getApplicationContext().getExternalCacheDir().getPath(), "/iNoobOffre/Cache/" + CodiceProdottoAmazon + ".jpg");
                        File cacheSaveFile1 = new File(getApplicationContext().getExternalCacheDir().getPath() + "/iNoobOffre/Cache/");
                        // Preparo testo da copiare
                        String ParteASoli = "";

                        if (checkBox.isChecked()) {
                            ParteASoli = "👀 A Soli **" + PrezzoNormale + "**";
                        } else {
                            ParteASoli = "👀 A Soli **" + PrezzoNormale + "** invece di **" + PrezzoConsigliato + "**";
                        }
                        String TestoClipboard = "⭐ **" + TitoloProdotto + "**\n\n" + ParteASoli + "\n➡️" + promoCodeApplied + "⬅️\n\n";
                        // Configurazione iniziale per il download dell'immagine
                        DownloadManager downloadmanager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                        Uri uri = Uri.parse(LinkImmagineAmazon);
                        DownloadManager.Request request = new DownloadManager.Request(uri);
                        request.setTitle("Foto prodotto");
                        request.setDescription("Download in corso...");
                        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
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
                                Log.d("ciao", impostazioni.getString("SaveDirectory", null));
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
                                    hello.setText("Foto scaricata. Testo copiato negli appunti");
                                    try {
                                        hello.dismiss();
                                        Thread.sleep(3000);
                                        Snackbar.make(view, "Foto scaricata. Testo copiato negli appunti", 3000).show();
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
                                        Snackbar.make(view, "Testo copiato negli appunti", 3000).show();
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
                            startActivity(Intent.createChooser(sharingIntent, "Condividi con Telegram"));

                        }


                    }
                });
                thread.start();

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