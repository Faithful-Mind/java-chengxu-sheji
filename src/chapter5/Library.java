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
    int size;
    boolean sorted;
    boolean isFull;

    public Bookshelf() {
        super();
        this.books = new Book[100];
    }
    
    /**
     * 向书架添加一本书。
     * Add a book to the bookshelf.
     * @param b 要添加的书。 book to add.
     * @return 成功则返回 <b>true</b>，如果失败（书架满了）则返回 <b>false</b>。<b>true</b> if success, false if failed (when bookshelf is full).
     */
    boolean add(Book b) {
        if (size >= this.books.length) {
            return false;
        } else {
            this.books[size] = b;
            size++;
            return true;
        }
    }

    void sort() {
        Arrays.parallelSort(this.books);
    }
}

/**
 * 中国图书馆分类法的22个基本大类。
 * Chinese Library Classification
 * @author Faithful-Mind
 *
 */
enum Category {
    A, B, C, D, E, F, G, H, I, J, K, N, O, P, Q, R, S, T, U, V, X, Z
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
        return "Press: [" + name + "]";
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
