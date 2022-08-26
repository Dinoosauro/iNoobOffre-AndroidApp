package android.inooboffre;

import android.os.Environment;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

// This class write the HTML webpage to a debug file.
public class DebugPageWriter {
    public void DebugPage(String line)

    {
        File newFileSave = new File(Environment.getExternalStorageDirectory() + "/iNoobOffre/" + "debugpage.txt");
        File newFileSave2 = new File(Environment.getExternalStorageDirectory() + "/iNoobOffre");
        try {
            // Create the folder if it doesn't exist, create a new file and then write the HTML content into the file.
            newFileSave2.mkdirs();
            newFileSave.createNewFile();
            int count;
            InputStream input = new ByteArrayInputStream(line.getBytes(StandardCharsets.UTF_8));
            FileOutputStream out = new FileOutputStream(newFileSave, false);
            byte data[] = new byte[1024];

            long total = 0;

            while ((count = input.read(data)) != -1) {
                total += count;
                out.write(data, 0, count);
            }
            out.flush();
            out.close();
            input.close();

        } catch (
                FileNotFoundException e) {
            e.printStackTrace();
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }
}
