package demo;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.faces.bean.*;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;

@ManagedBean(name = "accountController")
@SessionScoped
@Entity(name = "employee")
public class AccountController implements Serializable {
	/**
		 * 
		 */
	private static final long serialVersionUID = 1L;
	static SessionFactory factory;
	static Session session;
	static Transaction tx;

	private String username;
	private String password;
	@javax.persistence.Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int Id;
	private String name;
	private boolean type;

	public boolean isType() {
		return type;
	}

	public void setType(boolean type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String register() {
		return "success??faces-redirect=true";
	}

	public String checkuser(String username, String password) {
		String pass;
		try {
			String query1 = "select * from employee where username='" + username + "'";
			System.out.println("The query is" + query1);
			SQLQuery query = session.createSQLQuery(query1);
			List list = query.list();
			System.out.println("near iterator");
			Iterator it = list.iterator();
			if (it.hasNext()) {
				Object o[] = (Object[]) it.next();

				System.out.println(Arrays.toString(o));

				setId(Integer.parseInt(o[0].toString()));
				setType(Boolean.parseBoolean(o[1].toString()));
				setName(o[2].toString());
				setPassword(o[3].toString());
				pass = o[3].toString();
				setUsername(o[4].toString());
				if ((pass).equals(password))
					return "success??faces-redirect=true";
				else
					return "index??faces-redirect=true";
			}

		} catch (Exception ex) {
			System.out.println(ex);
			ex.printStackTrace();
		}
		return "false";
	}

	public static void create() {
		factory = new AnnotationConfiguration().addAnnotatedClass(AccountController.class).configure()
				.buildSessionFactory();
		session = factory.openSession();
		Transaction tx = session.beginTransaction();
	}

	public static void insert(String name, String username, String pass, Boolean type) {
		session.flush();
		System.out.println(name);
		System.out.println(username);
		System.out.println(pass);
		System.out.println(type);
		AccountController c = new AccountController();
		c.setId(2);
		c.setName(name);
		c.setUsername(username);
		c.setPassword(pass);
		c.setType(type);
		session.save(c);
		session.flush();
		tx.commit();
		session.close();
	}
}
