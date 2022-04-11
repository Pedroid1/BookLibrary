package com.example.bookapp.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.bookapp.database.MyDatabaseHelper;
import com.example.bookapp.R;
import com.example.bookapp.databinding.FragmentAddBookBinding;

public class AddBookFragment extends Fragment {

    private FragmentAddBookBinding binding = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddBookBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnAdd.setOnClickListener(view1 -> {
            String tittle = binding.edtTittle.getText().toString().trim();
            String author = binding.edtAuthor.getText().toString().trim();
            String pages = binding.edtPages.getText().toString().trim();

            if(tittle.isEmpty() || author.isEmpty() || pages.isEmpty()) {
                Toast.makeText(requireContext(),"Preencha todos os campos", Toast.LENGTH_SHORT).show();
            } else {
                MyDatabaseHelper db = new MyDatabaseHelper(requireContext());
                db.addBook(tittle, author, Integer.parseInt(pages));
                backToHome();
            }
        });
    }

    private void backToHome() {
        NavHostFragment.findNavController(this).navigate(R.id.action_addBookFragment_to_homeFragment);
    }
}