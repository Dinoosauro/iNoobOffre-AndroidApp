package android.inooboffre.ui.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.inooboffre.R;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.inooboffre.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        EditText introduzione = root.findViewById(R.id.introduzione);
        SharedPreferences preferences = getActivity().getSharedPreferences("iNoobOffre", Context.MODE_PRIVATE);
        if (preferences.contains("firstEdit")) {
            introduzione.setText(preferences.getString("firstEdit", null), TextView.BufferType.EDITABLE);
        } else {
            introduzione.setText("⭐ **$NomeProdotto**\n\n", TextView.BufferType.EDITABLE);
        }
        introduzione.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    SharedPreferences.Editor editor = preferences.edit();
                    if (!preferences.contains("resetJustHappened") || !preferences.getBoolean("resetJustHappened", true)) {
                        editor.putString("firstEdit", introduzione.getText().toString());
                        editor.apply();
                    }
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