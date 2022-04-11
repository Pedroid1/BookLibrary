package com.example.bookapp.database;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.bookapp.model.Book;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "BookLibrart.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "my_library";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TITLE = "book_title";
    private static final String COLUMN_AUTHOR = "book_author";
    private  static final String COLUMN_PAGES = "book_pages";

    public MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query =
        "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITLE + " TEXT, " +
                COLUMN_AUTHOR + " TEXT, " +
                COLUMN_PAGES + " INTEGER);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addBook(String tittle, String author, int pages) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_TITLE, tittle);
        cv.put(COLUMN_AUTHOR, author);
        cv.put(COLUMN_PAGES, pages);
        Long result = db.insert(TABLE_NAME, null, cv);

        if(result == -1) {
            Toast.makeText(context, "Erro ao salvar no banco de dados!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Livro salvo com sucesso!", Toast.LENGTH_SHORT).show();
        }
    }

    public Cursor readAllData() {
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    public Cursor findBookById(Integer id) {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = " + id;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    public void updateBook(Book book) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, book.getTittle());
        values.put(COLUMN_AUTHOR, book.getAuthor());
        values.put(COLUMN_PAGES, book.getPages());

        int result = db.update(TABLE_NAME, values, COLUMN_ID + "=?", new String[]{book.getId().toString()});
        if(result == -1) {
            Toast.makeText(context, "Erro ao atualizar", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Livro atualizado", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteBookById(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();

        int result = db.delete(TABLE_NAME,COLUMN_ID + "=?", new String[]{id.toString()});
        if(result == -1) {
            Toast.makeText(context, "Erro ao deletar", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Livro deletado", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteAllData() {
        String query = "DELETE FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(query);
    }
}
