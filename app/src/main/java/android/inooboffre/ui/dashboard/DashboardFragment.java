package android.inooboffre.ui.dashboard;

import android.content.Context;
import android.content.SharedPreferences;
import android.inooboffre.NewScript;
import android.inooboffre.R;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.inooboffre.databinding.FragmentDashboardBinding;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        EditText introduzione = root.findViewById(R.id.prezzoScontato);
        SharedPreferences preferences = getActivity().getSharedPreferences("iNoobOffre", Context.MODE_PRIVATE);
        if (preferences.contains("secondEdit")) {
            introduzione.setText(preferences.getString("secondEdit", null), TextView.BufferType.EDITABLE);
        } else {
            introduzione.setText("👀 A Soli **$PrezzoNormale** invece di **$PrezzoConsigliato**", TextView.BufferType.EDITABLE);
        }
        introduzione.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                SharedPreferences.Editor editor = preferences.edit();
                if (!preferences.contains("resetJustHappened") || !preferences.getBoolean("resetJustHappened", true)) {
                    editor.putString("secondEdit", introduzione.getText().toString());
                    editor.apply();
                }
            }
        });
        EditText introduzione2 = root.findViewById(R.id.prezzoNonScontato);
        if (preferences.contains("thirdEdit")) {
            introduzione2.setText(preferences.getString("thirdEdit", null), TextView.BufferType.EDITABLE);
        } else {
            introduzione2.setText("👀 A Soli**$PrezzoNormale**", TextView.BufferType.EDITABLE);

        }
        introduzione2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                SharedPreferences.Editor editor = preferences.edit();
                if (!preferences.contains("resetJustHappened") || !preferences.getBoolean("resetJustHappened", true)) {
                    editor.putString("thirdEdit", introduzione2.getText().toString());
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