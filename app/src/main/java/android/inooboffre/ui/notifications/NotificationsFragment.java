package android.inooboffre.ui.notifications;

import android.content.Context;
import android.content.SharedPreferences;
import android.inooboffre.R;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.inooboffre.databinding.FragmentNotificationsBinding;

public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        EditText introduzione = root.findViewById(R.id.ParteFinale);
        SharedPreferences preferences = getActivity().getSharedPreferences("iNoobOffre", Context.MODE_PRIVATE);
        if (preferences.contains("fourthEdit")) {
            introduzione.setText(preferences.getString("fourthEdit", null), TextView.BufferType.EDITABLE);
        } else {
            introduzione.setText(" \n➡   $Link   ️️⬅️\n\n", TextView.BufferType.EDITABLE);
        }
        introduzione.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                SharedPreferences.Editor editor = preferences.edit();
                if (!preferences.contains("resetJustHappened") || !preferences.getBoolean("resetJustHappened", true)) {
                    editor.putString("fourthEdit", introduzione.getText().toString());
                    editor.apply();
                }
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}