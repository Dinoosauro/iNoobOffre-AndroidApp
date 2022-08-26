package android.inooboffre.ui.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.inooboffre.R;
import android.inooboffre.SaveDraft;
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

import com.google.android.material.elevation.SurfaceColors;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        root.findViewById(R.id.introductionScroll).setBackgroundColor(SurfaceColors.SURFACE_1.getColor(getContext()));
        root.findViewById(R.id.getThisColor3).setBackgroundColor(SurfaceColors.SURFACE_2.getColor(getContext()));

        EditText introduzione = root.findViewById(R.id.introduzione);
        SharedPreferences preferences = getActivity().getSharedPreferences("iNoobOffre", Context.MODE_PRIVATE);
        SaveDraft saveDraft = new SaveDraft();
        // Load the draft or the previously saved value
        saveDraft.load("firstEdit", preferences, introduzione, "⭐ **$NomeProdotto**\n\n");
        introduzione.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    // Save the input as a draft
                    saveDraft.save("firstEdit", preferences, introduzione);
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