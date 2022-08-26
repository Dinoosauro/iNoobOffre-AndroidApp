package android.inooboffre;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class ModernSaveImage {
    // Downloads an image from an URL and saves it in the requested directory.
    public boolean SaveImage(String LinkImmagineAmazon, String directory, String CodiceProdottoAmazon) {
        try {
            int count;
            URL url = new URL(LinkImmagineAmazon);
            URLConnection connection = url.openConnection();
            connection.connect();
            InputStream input = new BufferedInputStream(url.openStream(),
                    8192);
            File saveFile = new File(directory);
            saveFile.mkdirs();
            File saveFile1 = new File(directory + "/" + CodiceProdottoAmazon + ".jpg");
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
            return true;

        } catch (Exception e) {
            Log.e("Error: ", e.getMessage());
            return false;
        }
    }
}
