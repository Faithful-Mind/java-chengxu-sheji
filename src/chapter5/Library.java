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
    String name;
    int count; // 增加图书次数，与图书ID有关，只增不减。 the added book count, associated with id in Book. Can't
               // decrease.
    List<Reader> readers;

    public Library(String name) {
        super();
        this.name = name;
        this.count = 0;
        this.readers = new ArrayList<Reader>();
    }

    public boolean addReader(Reader rd) {
        if (this.readers.add(rd)) {
            System.out.println("添加了读者：" + rd.getName() + "，\t个性签名：" + rd.getStatus());
            return true;
        }
        System.err.println("添加读者：" + rd.getName() + "失败！");
        return false;
    }
    
    /**
     * 建立图书馆。<br/>
     * Build the Library
     * 
     * @param institutionName
     *            建立图书馆的机构名称<br/>
     *            Name of the institution to build a library
     * @return 修建好的图书馆的<b>书架 (Bookshelf对象)</b><br/>
     *         <b>Bookshelf object</b> of the builded Library
     */
    public static Bookshelf build(String institutionName) {
        System.out.println(institutionName + "图书馆准备中……");
        String name = institutionName + "图书馆";
        Library lib = new Library(name);
        System.out.println("安装书架中……");
        Bookshelf bs = new Bookshelf(lib);
        System.out.println(name + "建立完成！");
        return bs;
    }

    public static void main(String[] args) {
        Bookshelf bs = build("XX大学");

        Reader xiaom = new Reader("小明"); // 读者小明
        Reader y = new Reader("小袁", () -> "这个人很勤快，签名都是手打的。");
        Reader l = new Reader("小刘", () -> "学问笃实生光辉");

        bs.lib.addReader(xiaom);
        bs.lib.addReader(y);
        bs.lib.addReader(l);

        Book hfPython = bs.add("Head First Python", "O'Reilly", "9781449382674", Category.T);
        Book algs = bs.add("算法", "人民邮电出版社", "9787115271464", Category.T);
        Book jzb = bs.add("颈椎病康复指南", "湖北科学技术出版社", "9787535249340", Category.R);

        xiaom.borrow(hfPython);
        xiaom.returnBack(hfPython);

        y.borrow(algs);
        l.borrow(algs);
        l.borrow(jzb);

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
    Library lib; // 书架所属图书馆。 The library this bookshelf belongs to.

    public Bookshelf(Library lib) {
        super();
        this.books = new ArrayList<Book>();
        this.lib = lib;
        // this.sorted = true;
    }

    /**
     * 向书架添加一本书。 Add a book to the bookshelf.
     * 
     * @param b
     *            要添加的书。 book to add.
     * @param name
     *            书名。 Book name
     * @param press
     *            出版社。 Press
     * @param isbn
     *            ISBN
     * @param category
     *            分类。 Category
     * @return 成功则返回 <b>添加的图书 (Book 对象）</b>，如果失败则返回 <b>null</b>。<br/>
     *         <b>The added Book object</b> if success, <b>null</b> if failed.
     */
    Book add(String name, String press, String isbn, Category category) {
        Book b = new Book(name, press, isbn, category, lib.count + 1); // 因为ID从1开始。 Because id starts from 1.
        if (books.add(b)) {
            lib.count++;
            System.out.println("添加了图书：\n\t" + b);
            return b;
        } else {
            System.err.println("图书添加失败“");
            return null;
        }
    }

    public int size() {
        return books.size();
    }

    // public void sort() {
    // Collections.sort(this.books);
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

/**
 * 加入图书馆的书。<br/>
 * The Book added to library.
 * 
 * @author Faithful-Mind
 */
class Book implements Comparable<Book> {
    private final String name;
    // TODO add publication date, author, pages etc.
    // private LocalDate publicationDate;
    // private Person author;
    // private int pages;
    private final String press;
    private final String isbn;
    private final Category category;
    private final int id; // Id starts form 1.
    private boolean lent;

    public Book(String name, String press, String isbn, Category category, int id) {
        super();
        this.name = name;
        this.press = press;
        this.isbn = isbn.replace(" ", "").replace("-", "");
        this.category = category;
        this.id = id;
        this.lent = false;
    }

    /**
     * 用于排序,分类相同比较id。 For sorting in a bookshelf. Compare its id if same category.
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
        return "[" + name + ", press=" + press + ", isbn=" + isbn + ", category=" + category + ", id=" + id + ", "
                + (lent ? "已借出" : "在库") + "]";
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

    public int getId() {
        return id;
    }
}

abstract class Person {
    private String name;
    private Status personalStatus;

    public Person(String name) {
        super();
        this.name = name;
        this.personalStatus = () -> "这个人很懒，什么都没有留下。"; // 默认个性签名 Default personal status
    }

    public Person(String name, Status personalStatus) {
        super();
        this.name = name;
        this.personalStatus = personalStatus;
    }

    public abstract String getJob();

    public String getStatus() {
        return personalStatus.getStatus();
    }

    @Override
    public String toString() {
        return "Person [name=" + name + "]";
    }

    public String getName() {
        return name;
    }

}

/**
 * 个性签名<br/>
 * Status
 * 
 * @author Faithful-Mind
 */
interface Status {
    String getStatus();
}

class Reader extends Person {
    private boolean isQualified = true; // 默认为真。 true by default
    private List<Book> borrowedBooks = new ArrayList<Book>();;

    public Reader(String name) {
        super(name);
    }

    public Reader(String name, boolean isQualified) {
        this(name);
        this.isQualified = isQualified;
    }

    public Reader(String name, Status personalStatus) {
        super(name, personalStatus);
    }

    public Reader(String name, boolean isQualified, Status personalStatus) {
        super(name, personalStatus);
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
                System.out.println(this + "\n\t借走了图书：");

                borrowedBooks.add(b);
                b.setLent(true);
                // 显示完成后的图书状态。 Show the book state after finished.
                System.out.println("\t\t" + b);
                return true;
            }
        }
        System.err.println(this + "\n\t没能借走图书：\n\t\t" + b);
        return false;
    }

    public boolean returnBack(Book b) {
        System.out.println(this + "\n\t归还了图书：");

        borrowedBooks.remove(b);
        b.setLent(false);
        // 显示完成后的图书状态。 Show the book state after finished.
        System.out.println("\t\t" + b);
        return true;
    }

    @Override
    public String getJob() {
        return "读者";
    }

    @Override
    public String toString() {
        return this.getJob() + " " + super.getName() + " " + (isQualified ? "有" : "没有") + "资格借书" + ", 在借图书=" + "{"
                + borrowedBooks.toString().substring(1, borrowedBooks.toString().length() - 1) + "}";
    }

    public boolean isQualified() {
        return isQualified;
    }

    public void setQualified(boolean isQualified) {
        this.isQualified = isQualified;
    }
}
