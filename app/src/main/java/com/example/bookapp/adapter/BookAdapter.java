package com.example.bookapp.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookapp.R;
import com.example.bookapp.model.Book;
import com.example.bookapp.ui.UpdateBookFragment;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.MyViewHolder> {
    private Context context;
    private List<Book> dataset;
    private NavController navController;

    public BookAdapter(Context context, List<Book> dataset, NavController navController) {
        this.context = context;
        this.dataset = dataset;
        this.navController = navController;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(context).inflate(R.layout.book_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.idTxt.setText((String.valueOf(dataset.get(position).getId())));
        holder.tittleTxt.setText(dataset.get(position).getTittle());
        holder.authorTxt.setText(dataset.get(position).getAuthor());
        holder.pagesTxt.setText(String.valueOf(dataset.get(position).getPages()));

        holder.layout.setOnClickListener(view -> {
            goToUpdateBookScreen(dataset.get(position).getId());
        });
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView idTxt, tittleTxt, authorTxt, pagesTxt;
        private CardView layout;
        public MyViewHolder(@NonNull View item) {
            super(item);
            idTxt = item.findViewById(R.id.book_id_txt);
            tittleTxt = item.findViewById(R.id.book_tittle_txt);
            authorTxt = item.findViewById(R.id.book_author_txt);
            pagesTxt = item.findViewById(R.id.book_pages_txt);
            layout = item.findViewById(R.id.main_layout);
        }
    }

    private void goToUpdateBookScreen(Integer id) {
        Bundle args = new Bundle();
        args.putInt(UpdateBookFragment.ID_TAG, id);
        navController.navigate(R.id.action_homeFragment_to_updateBookFragment, args);
    }
}
