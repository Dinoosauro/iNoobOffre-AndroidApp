package android.inooboffre.ui.dashboard;

import android.content.Context;
import android.content.SharedPreferences;
import android.inooboffre.R;
import android.inooboffre.SaveDraft;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.inooboffre.databinding.FragmentDashboardBinding;

import com.google.android.material.elevation.SurfaceColors;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        root.findViewById(R.id.discountScroll).setBackgroundColor(SurfaceColors.SURFACE_1.getColor(getContext()));
        root.findViewById(R.id.getThisColor3).setBackgroundColor(SurfaceColors.SURFACE_2.getColor(getContext()));

        // Get EditText for discounted items
        EditText introduzione = root.findViewById(R.id.prezzoScontato);
        SharedPreferences currentPreferences = getActivity().getSharedPreferences("iNoobOffre", Context.MODE_PRIVATE);
        SaveDraft saveDraft = new SaveDraft();
        // Load the draft or the previously saved value
        saveDraft.load("secondEdit", currentPreferences, introduzione, "👀 A Soli **$PrezzoNormale** invece di **$PrezzoConsigliato**");
        introduzione.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                // Save the input as a draft
                saveDraft.save("secondEdit", currentPreferences, introduzione);

            }
        });
        EditText introduzione2 = root.findViewById(R.id.prezzoNonScontato);
        // Load the draft or the previously saved value
        saveDraft.load("thirdEdit", currentPreferences, introduzione2, "👀 A Soli**$PrezzoNormale**");
        introduzione2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                // Save the input as a draft
                saveDraft.save("thirdEdit", currentPreferences, introduzione2);
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