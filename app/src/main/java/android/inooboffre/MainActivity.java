package android.inooboffre;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.res.ResourcesCompat;

import android.Manifest;
import android.app.DownloadManager;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import com.google.android.material.snackbar.Snackbar;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.button);
        EditText editText = findViewById(R.id.editTextTextPersonName);
        CheckBox checkBox = findViewById(R.id.checkBox);
        Switch switch3 = findViewById(R.id.switch3);
        checkBox.setTypeface(ResourcesCompat.getFont(MainActivity.this, R.font.roboto));
        switch3.setTypeface(ResourcesCompat.getFont(MainActivity.this, R.font.roboto));
        View getCurrentView;
        switch3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (switch3.isChecked()) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                }
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
                        String CodiceProdottoAmazon = linkProdotto.substring(linkProdotto.indexOf("/dp/"));
                        Log.d("c", CodiceProdottoAmazon);
                        CodiceProdottoAmazon = CodiceProdottoAmazon.replace("/dp/", "");
                        CodiceProdottoAmazon = CodiceProdottoAmazon.substring(0, 10);
                        String promoCodeApplied = "https://www.amazon.it/dp/" + CodiceProdottoAmazon + "/?tag=inoob02-21";
                        Log.d("a", promoCodeApplied);
                        Log.d("a", CodiceProdottoAmazon);
                        // Preparo testo da copiare
                        String ParteASoli = "";
                        if (checkBox.isChecked()) {
                            ParteASoli = "👀 A Soli **" + PrezzoNormale + "**";
                        } else {
                            ParteASoli = "👀 A Soli **\"" + PrezzoNormale + "\"** invece di **\"" + PrezzoConsigliato + "\"**";
                        }
                        String TestoClipboard = "⭐ **" + TitoloProdotto + "**\n\n" + ParteASoli + "\n➡️" + promoCodeApplied + "⬅️\n\n";
                        Log.d("a", TestoClipboard);
                        // Configurazione iniziale per il download dell'immagine
                        DownloadManager downloadmanager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                        Uri uri = Uri.parse(LinkImmagineAmazon);
                        Log.d("ciao", LinkImmagineAmazon);
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
                            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "/iNoobOffre/" + CodiceProdottoAmazon + ".jpg");
                            downloadmanager.enqueue(request);
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
                                    }                                }
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

                    }
                });
                thread.start();

            }
        });
    }
}