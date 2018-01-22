package demo;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;

@ManagedBean
@SessionScoped
@Entity(name = "book")
public class Book implements Serializable {
	static SessionFactory factory;
	static Session session;
	static Transaction tx;
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	private String type;
	private String name;
	private int copies;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCopies() {
		return copies;
	}

	public void setCopies(int copies) {
		this.copies = copies;
	}

/*	public static void create() {
		factory = new AnnotationConfiguration().addAnnotatedClass(Book.class).configure("hibernate.cfg.xml").buildSessionFactory();
		session = factory.openSession();
		Transaction tx = session.beginTransaction();
	}*/

	public static void insert(String name,String type,int copies) {
		factory = new AnnotationConfiguration().addAnnotatedClass(Book.class).configure("hibernate.cfg.xml").buildSessionFactory();
		session = factory.openSession();
		Transaction tx = session.beginTransaction();
		Book b=new Book();
		b.setName(name);
		b.setCopies(copies);
		b.setType(type);
		session.save(b);
		tx.commit();
		session.close();
	}
public static void delete(String name)
{
	factory = new AnnotationConfiguration().addAnnotatedClass(Book.class).configure("hibernate.cfg.xml").buildSessionFactory();
	session = factory.openSession();
	Transaction tx = session.beginTransaction();
	String query1="delete from book where name='"+name+"'";
	SQLQuery query=session.createSQLQuery(query1);
	query.executeUpdate();
	session.flush();
	tx.commit();
}
}
