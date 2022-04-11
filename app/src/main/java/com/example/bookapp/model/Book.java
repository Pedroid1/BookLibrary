package com.example.bookapp.model;

public class Book {
    private Integer id;
    private String tittle, author;
    private Integer pages;

    public Book() {
    }

    public Book(Integer id, String tittle, String author, Integer pages) {
        this.id = id;
        this.tittle = tittle;
        this.author = author;
        this.pages = pages;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }
}
