package com.example.eventsusc;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.eventsusc.databinding.FragmentSecondBinding;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;

    private FirebaseDatabase root;
    private DatabaseReference reference;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /* When view is created, fetch the latest user message from the database */

        /* Same thing as FirstFragment */
        root = FirebaseDatabase.getInstance("https://eventsusc-38901-default-rtdb.firebaseio.com/");
        reference = root.getReference("textKey"); // Get some (key, value) we want to edit in our database

        // Now, read from DB with a ValueEventListener
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Create a String with the value from the DataSnapshot that references our "textKey"
                String value = snapshot.getValue(String.class);

                // Set this String as the text inside our TextView
                if (binding != null) { // Sanity check
                    binding.textviewSecond.setText(value);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // If there is error with the Database, display some error message to user
                if (binding != null) { // Sanity check
                    binding.textviewSecond.setText("Error encountered in fetching textKey from the database");
                }
            }
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