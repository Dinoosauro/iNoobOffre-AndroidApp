package android.inooboffre;

import android.content.SharedPreferences;
import android.widget.EditText;
import android.widget.TextView;

public class SaveDraft {
    public void save(String keyID, SharedPreferences preferences, EditText edit) {
        SharedPreferences.Editor editor = preferences.edit();
        if (!preferences.contains("resetJustHappened") || !preferences.getBoolean("resetJustHappened", true)) {
            editor.putString(keyID, edit.getText().toString());
            editor.apply();
        }
    }
    public void load(String keyID, SharedPreferences preferences, EditText edit, String defaultString) {
        if (preferences.contains(keyID)) {
            // This means that this EditText has been modified before, so the text will be changed.
            edit.setText(preferences.getString(keyID, null), TextView.BufferType.EDITABLE);
        } else {
            // Write the default EditText text.
            edit.setText(defaultString, TextView.BufferType.EDITABLE);
        }
    }

}
