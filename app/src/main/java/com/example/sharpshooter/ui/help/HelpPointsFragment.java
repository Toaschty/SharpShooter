package com.example.sharpshooter.ui.help;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.sharpshooter.R;
import com.example.sharpshooter.databinding.FragmentHelpPointsBinding;

public class HelpPointsFragment extends Fragment {

    private FragmentHelpPointsBinding binding;

    private TextView score;
    private RadioGroup rg;
    private CheckBox zeroButton;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        binding = FragmentHelpPointsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        score = binding.currentGamePlayerPointSum;
        rg = binding.currentGamePlayerPoints;
        zeroButton = binding.currentGamePlayerPoint0;

        // Setup dummy button logic
        rg.setOnCheckedChangeListener((radioGroup, i) -> {
            // Set score to selected button
            if (i == R.id.currentGamePlayerPoint20) {
                score.setText(R.string.current_game_points_20);
            } else if (i == R.id.currentGamePlayerPoint16) {
                score.setText(R.string.current_game_points_16);
            } else if(i == R.id.currentGamePlayerPoint14) {
                score.setText(R.string.current_game_points_14);
            } else if(i == R.id.currentGamePlayerPoint10) {
                score.setText(R.string.current_game_points_10);
            } else if(i == R.id.currentGamePlayerPoint8) {
                score.setText(R.string.current_game_points_8);
            } else if(i == R.id.currentGamePlayerPoint4) {
                score.setText(R.string.current_game_points_4);
            }

            // Reset zero button if checked
            if (zeroButton.isChecked())
                zeroButton.toggle();
        });

        zeroButton.setOnClickListener(view -> {
            rg.clearCheck();
            zeroButton.setChecked(true);

            score.setText("0");
        });

        // Setup close button
        Button btnClose = binding.btnDone;
        btnClose.setOnClickListener(click -> requireActivity().onBackPressed());

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
