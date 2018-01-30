package chapter5;

import java.time.LocalDate;
import java.util.Arrays;

public class Library {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Library lib = new Library();
    }

}

class Bookshelf {
    Book[] books;
    boolean sorted;

    void sort() {
        Arrays.parallelSort(books);
    }
}

enum Category {
    A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y, Z;
}

class Book implements Comparable<Book> {
    LocalDate publicationDate;
    Press press;
    Person author;
    int pages;
    String isbn;
    long id;
    Category category;
    boolean isLent;

    boolean returnBack() {
        this.isLent = false;
        return true;
    }

    @Override
    public int compareTo(Book o) {
        if (this.category.compareTo(o.category) == 0) //分类相同比较id。Compare its id if same category.
            return (int) (this.id - o.id);
        else
            return this.category.compareTo(o.category);
    }
}

class Press {
    String name;

    @Override
    public String toString() {
        return "Press [name=" + name + "]";
    }

}

class Person {
    String name;

    public Person(String name) {
        super();
        this.name = name;
    }

}

class Reader extends Person {
    boolean isQualified;

    public Reader(String name) {
        super(name);
        this.isQualified = true;
    }

    public Reader(String name, boolean isQualified) {
        super(name);
        this.isQualified = isQualified;
    }
}
