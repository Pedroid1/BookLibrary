package com.example.bookapp.ui;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.bookapp.R;
import com.example.bookapp.adapter.BookAdapter;
import com.example.bookapp.database.MyDatabaseHelper;
import com.example.bookapp.databinding.FragmentHomeBinding;
import com.example.bookapp.model.Book;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding = null;
    private MyDatabaseHelper myDB;
    private BookAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        myDB = new MyDatabaseHelper(requireActivity());
        displayData();

        binding.floatingButton.setOnClickListener(view1 -> {
            goToAddBookScreen();
        });

        binding.removeImg.setOnClickListener(view12 -> {
            myDB.deleteAllData();
            refreshDisplay();
        });
    }

    private void displayData() {
        List<Book> bookList = getData();

        if (!bookList.isEmpty()) {
            haveData();
            adapter = new BookAdapter(requireActivity(), bookList, NavHostFragment.findNavController(this));
            binding.recyclerBook.setLayoutManager(new LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false));
            binding.recyclerBook.setAdapter(adapter);
        } else {
            haveNoData();
        }
    }

    private List<Book> getData() {
        Cursor cursor = myDB.readAllData();
        List<Book> bookList = new ArrayList<>();

        if (cursor != null) {
            while (cursor.moveToNext()) {
                Integer id = cursor.getInt(0);
                String tittle = cursor.getString(1);
                String author = cursor.getString(2);
                Integer pages = cursor.getInt(3);
                Book book = new Book(id, tittle, author, pages);
                bookList.add(book);
            }
        }
        return bookList;
    }

    private void goToAddBookScreen() {
        NavHostFragment.findNavController(this).navigate(R.id.action_homeFragment_to_addBookFragment);
    }

    private void haveNoData() {
        binding.dataImg.setVisibility(View.VISIBLE);
        binding.dataTxt.setVisibility(View.VISIBLE);
    }

    private void haveData() {
        binding.dataImg.setVisibility(View.INVISIBLE);
        binding.dataTxt.setVisibility(View.INVISIBLE);
    }

    private void refreshDisplay() {
        NavHostFragment.findNavController(this).navigate(R.id.action_homeFragment_self);
    }
}