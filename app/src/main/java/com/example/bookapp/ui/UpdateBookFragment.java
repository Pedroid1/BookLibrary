package com.example.bookapp.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.bookapp.R;
import com.example.bookapp.database.MyDatabaseHelper;
import com.example.bookapp.databinding.FragmentUpdateBookBinding;
import com.example.bookapp.model.Book;

public class UpdateBookFragment extends Fragment {

    public static final String ID_TAG = "ID";
    private FragmentUpdateBookBinding binding = null;
    private MyDatabaseHelper myDB;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUpdateBookBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle args = getArguments();

        if(args != null) {
            Integer id = args.getInt(ID_TAG);
            Book book = getBook(id);

            binding.edtTittle.setText(book.getTittle());
            binding.edtAuthor.setText(book.getAuthor());
            binding.edtPages.setText(String.valueOf(book.getPages()));

            binding.btnUpdate.setOnClickListener(view1 -> {
                String tittle = binding.edtTittle.getText().toString().trim();
                String author = binding.edtAuthor.getText().toString().trim();
                String pages = binding.edtPages.getText().toString().trim();

                if(tittle.isEmpty() || author.isEmpty() || pages.isEmpty()) {
                    Toast.makeText(requireContext(),"Preencha todos os campos", Toast.LENGTH_SHORT).show();
                } else {
                    Book updateBook = new Book(id, tittle, author, Integer.parseInt(pages));
                    confirmDialog(updateBook);
                    goToHomeScreen();
                }
            });

            binding.deleteBtn.setOnClickListener(view1 -> {
                myDB.deleteBookById(book.getId());
                goToHomeScreen();
            });
        }

    }

    private Book getBook(Integer id) {
        myDB = new MyDatabaseHelper(requireContext());
        Cursor cursor = myDB.findBookById(id);
        Book book = new Book();

        if(cursor != null) {
            while(cursor.moveToNext()) {
                Integer currentId = cursor.getInt(0);
                String tittle = cursor.getString(1);
                String author = cursor.getString(2);
                Integer pages = cursor.getInt(3);
                book.setId(currentId);
                book.setTittle(tittle);
                book.setAuthor(author);
                book.setPages(pages);
            }
        }
        return book;
    }

    private void goToHomeScreen() {
        NavHostFragment.findNavController(this).navigate(R.id.action_updateBookFragment_to_homeFragment);
    }

    private void confirmDialog(Book updateBook) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Deletar livro");
        builder.setMessage("VOcê tem certeza que deseja deletar " + updateBook.getTittle() + " ?");
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                myDB = new MyDatabaseHelper(requireActivity());
                myDB.updateBook(updateBook);
            }
        });
        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
    }
}