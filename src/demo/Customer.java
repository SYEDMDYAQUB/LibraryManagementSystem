package demo;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.faces.bean.*;
import javax.persistence.Entity;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;

@ManagedBean(name = "customer")
@SessionScoped
@Entity(name = "customer")
public class Customer implements Serializable {

	private static final long serialVersionUID = 1L;
	private String username;
	private String password;
	private String name;
	@javax.persistence.Id
	private int Id;
	private int age;
	private int type;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String register() {
		return "success??faces-redirect=true";
	}

	public String checkuser(String username, String password) {
		String pass;
		try {
			System.out.println(password);
			System.out.println(username);
			SessionFactory sessionFactory = (SessionFactory) new AnnotationConfiguration()
					.addAnnotatedClass(AccountController.class).configure().buildSessionFactory();
			Session session = sessionFactory.openSession();
			Transaction tx = session.beginTransaction();
			String query1 = "select * from customer where username='" + username + "'";
			System.out.println("The query is" + query1);
			SQLQuery query = session.createSQLQuery(query1);
			List list = query.list();

			Iterator it = list.iterator();
			if (it.hasNext()) {
				Object o[] = (Object[]) it.next();

				System.out.println(Arrays.toString(o));

				setId(Integer.parseInt(o[0].toString()));

				setName(o[1].toString());
				setAge(Integer.parseInt(o[2].toString()));
				setUsername(o[3].toString());
				setType(Integer.parseInt(o[5].toString()));
				setPassword(o[4].toString());
				pass = o[4].toString();

				if ((pass).equals(password))
					return "customerhome??faces-redirect=true";
				else
					return "customer??faces-redirect=true";
			}
		} catch (Exception ex) {
			System.out.println(ex);
		}
		return "false";
	}
}
