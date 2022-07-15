package com.example.sharpshooter.ui.help;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.sharpshooter.R;
import com.example.sharpshooter.databinding.FragmentHelpSavedGamesBinding;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class HelpSavedGamesFragment extends Fragment {

    private FragmentHelpSavedGamesBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        binding = FragmentHelpSavedGamesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Get references
        TextView gameDate = binding.idLastGameDate;
        ImageView gameImage = binding.idLastGameImage;

        // Set date
        LocalDate myDateObj = LocalDate.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        gameDate.setText(myDateObj.format(myFormatObj));

        // Set image
        gameImage.setImageResource(R.drawable.ic_no_image);

        // Setup close button
        Button btnClose = binding.btnDone;
        btnClose.setOnClickListener(click -> {
            requireActivity().onBackPressed();
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
