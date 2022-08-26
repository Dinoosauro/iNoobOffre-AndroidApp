package android.inooboffre.ui.notifications;

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

import android.inooboffre.databinding.FragmentNotificationsBinding;

import com.google.android.material.elevation.SurfaceColors;

public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        root.findViewById(R.id.endScroll).setBackgroundColor(SurfaceColors.SURFACE_1.getColor(getContext()));
        root.findViewById(R.id.getThisColor3).setBackgroundColor(SurfaceColors.SURFACE_2.getColor(getContext()));
        EditText introduzione = root.findViewById(R.id.ParteFinale);
        SharedPreferences preferences = getActivity().getSharedPreferences("iNoobOffre", Context.MODE_PRIVATE);
        SaveDraft saveDraft = new SaveDraft();
        // Load the draft or the previously saved value
        saveDraft.load("fourthEdit", preferences, introduzione, "👀 A Soli **$PrezzoNormale** invece di **$PrezzoConsigliato**");
        introduzione.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                // Save the input as a draft
                saveDraft.save("fourthEdit", preferences, introduzione);
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