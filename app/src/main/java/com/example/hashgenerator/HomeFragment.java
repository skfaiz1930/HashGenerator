package com.example.hashgenerator;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.hashgenerator.databinding.FragmentHomeBinding;
import com.google.android.material.snackbar.Snackbar;


public class HomeFragment extends Fragment {
    public String hash;
    public ClipboardManager myClipboard;
    private ClipData myClip;
    private FragmentHomeBinding binding;
    private NavController navController;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        setHasOptionsMenu(true);

        android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);

        binding.generateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hash = HashGenerator.generateHash(String.valueOf(binding.textInputLayout.getEditText().getText()), binding.planeText.getText().toString());
                Log.e("Testing 2", hash);
                binding.hashText.setText(hash);
//                String testing = binding.textInputLayout.getEditText().getText().toString();
//                Log.d("Testing",testing);
                onGenerateClicked();
            }
        });
        binding.copyButton.setEnabled(false);
        binding.copyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = hash;
                Log.e("Test 4", text);
                myClip = ClipData.newPlainText("text", text);
                clipboard.setPrimaryClip(myClip);
                Toast.makeText(getActivity(), "Hash Copied",
                        Toast.LENGTH_SHORT).show();
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.home_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        String[] hashAlgorithms;
        hashAlgorithms = getResources().getStringArray(R.array.hash_algorithms);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(), R.layout.drop_down_item, R.id.text, hashAlgorithms);
        binding.autoCompleteTextView.setAdapter(arrayAdapter);

    }

    private void onGenerateClicked() {
        //Converting to Hash
        if (binding.planeText.getText().toString().trim().length() != 0 && binding.textInputLayout.getEditText().getText().length() != 0) {
            applyAnimation();
            applySecondAnimation();
        } else {
            showSnackBar("Field Empty");
        }

    }

    private void applyAnimation() {
        binding.copyButton.setEnabled(true);
        binding.generateButton.setEnabled(false);
        binding.textInputLayout.setEnabled(false);
        binding.titleTextView.animate().alpha(0f).setDuration(700);
        binding.generateButton.animate().alpha(0f).setDuration(700);
        binding.textInputLayout.animate().alpha(0f).translationXBy(1200f).setDuration(700);
        binding.planeText.animate().alpha(0f).translationXBy(-200f).setDuration(700);

        binding.successBackground.animate().alpha(1f).setDuration(600).setStartDelay(1000);
        binding.successBackground.animate().rotationBy(720f).setDuration(600).setStartDelay(1000);
        binding.successBackground.animate().scaleXBy(900f).setDuration(800).setStartDelay(1000);
        binding.successBackground.animate().scaleYBy(900f).setDuration(800).setStartDelay(1000);
        binding.hashText.animate().alpha(1f).setDuration(800).setStartDelay(5000);
        binding.successImageView.animate().alpha(1).setDuration(1000).setStartDelay(1500);


    }

    private void applySecondAnimation() {

        binding.hashBackground.animate().alpha(1f).setDuration(600).setStartDelay(4000);
        binding.hashBackground.animate().rotationBy(720f).setDuration(600).setStartDelay(4000);
        binding.hashBackground.animate().scaleXBy(900f).setDuration(800).setStartDelay(4000);
        binding.hashBackground.animate().scaleYBy(900f).setDuration(800).setStartDelay(4000);
        binding.successImageView.animate().alpha(0).setDuration(700).setStartDelay(4000);

        binding.hashText.animate().alpha(1).setDuration(800).setStartDelay(5000);
        binding.copyButton.animate().alpha(1).setDuration(800).setStartDelay(5000);
    }

    private void showSnackBar(String message) {
        Snackbar snackbar = Snackbar.make(binding.rootLayout, message,
                Snackbar.LENGTH_SHORT).setAction("Okay", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        snackbar.show();
    }
}