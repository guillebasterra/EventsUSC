package com.example.eventsusc;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.eventsusc.databinding.FragmentFirstBinding;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;

    private FirebaseDatabase root;
    private DatabaseReference reference;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonFirst.setOnClickListener(v ->{
            //get the latest instance of firebase
            root = FirebaseDatabase.getInstance("https://eventsusc-38901-default-rtdb.firebaseio.com/");
            //get the key,value we want to edit in our database
            reference = root.getReference("textKey");
            //save the current edittext value to the key,value
            reference.setValue(binding.textviewFirst.getText().toString());

            //move to second fragment
            NavHostFragment.findNavController(FirstFragment.this)
                    .navigate(R.id.action_FirstFragment_to_SecondFragment);

        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;

        root = null;
        reference = null;
    }

}