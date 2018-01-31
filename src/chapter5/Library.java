package chapter5;

import java.util.ArrayList;
import java.util.List;

/**
 * 图书馆，暂时先只有一个书架。 <br/>
 * Library, currently with only one bookshelf.
 * 
 * @author Faithful-Mind
 */
public class Library {
    int count; // 增加图书次数，与图书ID有关，只增不减。the added book count, associated with id in Book. Can't
               // decrease.

    public Library() {
        super();
        this.count = 0;
    }

    public static void main(String[] args) {
        Library lib = new Library();
        Bookshelf bs = new Bookshelf(lib);

        Reader xiaom = new Reader("小明"); // 读者小明
        Reader y = new Reader("小袁");
        Reader l = new Reader("小刘");

        Book hfPython = new Book("Head First Python", "O'Reilly", "9781449382674", Category.T);
        bs.add(hfPython);
        Book algs = new Book("算法", "人民邮电出版社", "9787115271464", Category.T);
        bs.add(algs);

        xiaom.borrow(hfPython);
        xiaom.returnBack(hfPython);

        y.borrow(algs);
        l.borrow(algs);

    }

}

/**
 * 书架<br/>
 * The bookshelf to store books.
 * 
 * @author Faithful-Mind
 */
class Bookshelf {
    ArrayList<Book> books;
    // boolean sorted; // TODO sort the bookshelf
    Library lib;

    public Bookshelf(Library lib) {
        super();
        this.books = new ArrayList<Book>();
        this.lib = lib;
        // this.sorted = true;

        System.out.println(lib + "的书架已经装好");
    }

    /**
     * 向书架添加一本书。 Add a book to the bookshelf.
     * 
     * @param b
     *            要添加的书。 book to add.
     * @return 成功则返回 <b>true</b>，如果失败（书架满了）则返回 <b>false</b>。<br/>
     *         <b>true</b> if success, <b>false</b> if failed (when bookshelf is
     *         full).
     */
    boolean add(Book b) {
        if (books.add(b)) {
            lib.count++;
            b.setId(lib.count);

            System.out.println("添加了图书：\n\t" + b);
            return true;
        } else {
            System.out.println("图书添加失败“");
            return false;
        }
    }

    public int size() {
        return books.size();
    }

    // void sort() {
    // Arrays.parallelSort(this.books);
    // this.sorted = true;
    // }
}

/**
 * 中国图书馆分类法的22个基本大类。 <br/>
 * 22 main category of Chinese Library Classification.
 * 
 * @author Faithful-Mind
 *
 */
enum Category {
    A, B, C, D, E, F, G, H, I, J, K, N, O, P, Q, R, S, T, U, V, X, Z
}

class Book implements Comparable<Book> {
    private String name;
    // TODO add publication date, author, pages etc.
    // private LocalDate publicationDate;
    // private Person author;
    // private int pages;
    private String press;
    private String isbn;
    private Category category;
    private int id; // it has an id only after it's added to a bookshelf. Id starts form 1.
    private boolean lent;

    public Book(String name, String press, String isbn, Category category) {
        super();
        this.name = name;
        this.press = press;
        this.isbn = isbn.replace(" ", "").replace("-", "");
        this.category = category;
        this.lent = false;
    }

    /**
     * 用于排序,分类相同比较id。
     * For sorting in a bookshelf. Compare its id if same category.
     */
    @Override
    public int compareTo(Book o) {
        if (this.category.compareTo(o.category) == 0) {
            return this.id - o.id;
        } else {
            return this.category.compareTo(o.category);
        }
    }

    @Override
    public String toString() {
        return "Book [name=" + name + ", press=" + press + ", isbn=" + isbn + ", category=" + category + ", id=" + id
                + ", isLent=" + lent + "]";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isLent() {
        return lent;
    }

    public void setLent(boolean lent) {
        this.lent = lent;
    }

    public String getName() {
        return name;
    }

    public String getPress() {
        return press;
    }

    public String getIsbn() {
        return isbn;
    }

    public Category getCategory() {
        return category;
    }
}

class Person {
    private String name;

    public Person(String name) {
        super();
        this.name = name;
    }

    @Override
    public String toString() {
        return "Person [name=" + name + "]";
    }

    public String getName() {
        return name;
    }

}

class Reader extends Person {
    private boolean isQualified; // true by default
    private List<Book> borrowedBooks;

    public Reader(String name) {
        super(name);
        this.isQualified = true;
        this.borrowedBooks = new ArrayList<Book>();
    }

    public Reader(String name, boolean isQualified) {
        this(name);
        this.isQualified = isQualified;
    }

    /**
     * Borrow a book
     * 
     * @param b
     * @return <b>true</b> if success<br/>
     *         <b>false</b> if failed (when not qualified).
     */
    public boolean borrow(Book b) {
        if (!b.isLent()) {
            if (this.isQualified) {
                System.out.println(this + "\n\t借走了图书：\n\t\t" + b);

                borrowedBooks.add(b);
                b.setLent(true);
                return true;
            }
        }
        System.out.println(this + "\n\t没能借走图书：\n\t\t" + b);
        return false;
    }

    public boolean returnBack(Book b) {
        System.out.println(this + "\n\t归还了图书：\n\t\t" + b);

        borrowedBooks.remove(b);
        b.setLent(false);
        return true;
    }

    @Override
    public String toString() {
        return "读者 " + super.getName() + " [" + (isQualified ? "有" : "没有") + "资格借书" + ", 在借图书=" + borrowedBooks + " ]";
    }

    public boolean isQualified() {
        return isQualified;
    }

    public void setQualified(boolean isQualified) {
        this.isQualified = isQualified;
    }
}
