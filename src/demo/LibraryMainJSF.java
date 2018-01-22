package demo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.classic.Session;

@ManagedBean
@SessionScoped
public class LibraryMainJSF implements Serializable {

	private static final long serialVersionUID = 61237798274430960L;

	static SessionFactory factory;
	static Session session;
	static org.hibernate.Transaction trans;

	static ArrayList<Book> books = new ArrayList<Book>();

	public static ArrayList<Book> getBooks() {
		// displayBooks();
		return books;
	}

	public LibraryMainJSF() {
		// TODO Auto-generated constructor stub
		System.out.println("Constructor called");
	}

	public static void main(String[] args) {

		/*
		 * // TODO Auto-generated method stub System.out.println(Book.trail());
		 * System.out.println(create()); System.out.println(insert("utkarsh123"));
		 * System.out.println(displayBooks());
		 */

		System.out.println(create());
		System.out.println(searchBook(0, "", ""));
		System.out.println(searchBook(0, "", "type1"));
		System.out.println(searchBook(0, "cake123", ""));
		System.out.println(searchBook(0, "cake123", "type1"));
		System.out.println(searchBook(20, "", ""));
		System.out.println(searchBook(190, "", "type1"));
		System.out.println(searchBook(250, "cake123", ""));
		System.out.println(searchBook(260, "cake123", "type1"));

		// deleteBookByName("book123");
		// deleteBookById(20);

	}

	public static String redirectToAdd() {
		return "AddBook??faces-redirect=true";
	}

	public static String create() {
		StringBuilder str = new StringBuilder();
		factory = new AnnotationConfiguration().addAnnotatedClass(Book.class).addAnnotatedClass(BookIssue.class)
				.addAnnotatedClass(Customer.class).addAnnotatedClass(Employee.class).configure().buildSessionFactory();
		str.append("Session Factory Built");

		/*
		 * SessionFactory factory = new Configuration() .addClass(Book.class)
		 * .configure() .buildSessionFactory();
		 */
		// Books = new ArrayList<Book>();

		// Books.clear();
		return str.toString();
	}

	public static String insert(String name) {
		StringBuilder str = new StringBuilder();

		session = factory.openSession();
		trans = session.beginTransaction();
		str.append("Session Opened ");
		Book b = new Book();
		b.setBookName(name);
		b.setBookType("type1");
		b.setNoOfCopies(14);
		session.save(b);
		str.append("book inserted ");
		session.flush();
		trans.commit();
		session.close();
		str.append("Session Closed ");

		return str.toString();
	}

	public static String deleteBookByName(String bookName) {

		session = factory.openSession();
		trans = session.beginTransaction();
		SQLQuery del = session.createSQLQuery("update book set no_of_copies=0 where book_name ='" + bookName + "'");
		del.executeUpdate();
		session.flush();
		trans.commit();
		session.close();
		return ("Book" + bookName + " Deleted");
	}

	public static void deleteBookById(int bookId) {
		session = factory.openSession();
		trans = session.beginTransaction();

		SQLQuery del = session.createSQLQuery("update book set no_of_copies=0 where book_id=" + bookId);
		del.executeUpdate();
		session.flush();
		trans.commit();
		session.close();
	}

	public static String searchBook(int id, String name, String type) {
		StringBuilder str = new StringBuilder();

		session = factory.openSession();

		String str1, str2, str3;

		if (id == 0) {
			str1 = "";
		} else {
			str1 = "book_id = " + id;
			if (name != "" || type != "")
				str1 += " and ";

		}
		if (name == "") {
			str2 = "";
		} else {
			str2 = "book_name = '" + name + "' ";
			if (type != "")
				str2 += " and ";

		}
		if (type == "") {
			str3 = "";
		} else {

			str3 = "book_type = '" + type + "'";

		}
		if (str1 == "" && str2 == "" && str3 == "")
			return "Enter a value to search";
		SQLQuery select = session.createSQLQuery("select * from book where " + str1 + str2 + str3);
		books.clear();
		List l = select.list();
		System.out.println("Total Number Of Records : " + l.size());
		Iterator it = l.iterator();
		while (it.hasNext()) {
			Object o[] = (Object[]) it.next();
			Book b = new Book();

			b.setBookId(Integer.parseInt(o[0].toString()));
			b.setNoOfCopies(Integer.parseInt(o[1].toString()));
			b.setBookName(o[2].toString());
			b.setBookType(o[3].toString());
			books.add(b);
		}
		for (Book b : books) {
			// System.out.println(c.toString());
			str.append(b.toString()).append("\r\n");
		}

		session.close();
		return str.toString();
	}

	public static String displayBooks() {
		StringBuilder str = new StringBuilder();

		session = factory.openSession();

		SQLQuery select = session.createSQLQuery("select * from book ");
		books.clear();
		List l = select.list();
		System.out.println("Total Number Of Records : " + l.size());
		Iterator it = l.iterator();
		while (it.hasNext()) {
			Object o[] = (Object[]) it.next();
			Book b = new Book();

			b.setBookId(Integer.parseInt(o[0].toString()));
			b.setNoOfCopies(Integer.parseInt(o[1].toString()));
			b.setBookName(o[2].toString());
			b.setBookType(o[3].toString());
			books.add(b);
		}
		for (Book b : books) {
			// System.out.println(c.toString());
			str.append(b.toString()).append("\r\n");
		}

		session.close();
		return str.toString();
	}
}
