package com.example.sharpshooter.ui.help;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
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
            switch (i)
            {
                case R.id.currentGamePlayerPoint20: score.setText("20"); break;
                case R.id.currentGamePlayerPoint16: score.setText("16"); break;
                case R.id.currentGamePlayerPoint14: score.setText("14"); break;
                case R.id.currentGamePlayerPoint10: score.setText("10"); break;
                case R.id.currentGamePlayerPoint8: score.setText("8"); break;
                case R.id.currentGamePlayerPoint4: score.setText("4"); break;
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
