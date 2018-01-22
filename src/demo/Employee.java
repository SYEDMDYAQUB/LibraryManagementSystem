package demo;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

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
@Entity(name = "employee")
public class Employee implements Serializable {

	static SessionFactory factory = new AnnotationConfiguration().addAnnotatedClass(Employee.class).configure().buildSessionFactory();
	static Session session=factory.openSession();
	static Transaction tx=session.beginTransaction();
	private static final long serialVersionUID=1L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	private boolean type;
	private String name;
	private String password;
	private String username;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
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
	public String checkuser(String username,String password)
	{
		String pass;
	try
	{	
		factory = new AnnotationConfiguration().addAnnotatedClass(Employee.class).configure().buildSessionFactory();
		session=factory.openSession();
	Transaction tx=session.beginTransaction();
		String query1="select * from employee where username='"+username+"'";
		System.out.println("The query is" + query1);
		SQLQuery query=session.createSQLQuery(query1);
		List list=query.list();
		System.out.println("near iterator");
		Iterator it = list.iterator();
		if (it.hasNext()) {
			Object o[] = (Object[]) it.next();

			System.out.println(Arrays.toString(o));

			setId(Integer.parseInt(o[0].toString()));
			setType(Boolean.parseBoolean(o[1].toString()));
			setName(o[2].toString());
			setPassword(o[3].toString());
			pass=o[3].toString();
			setUsername(o[4].toString());
		if((pass).equals(password))
			return "success??faces-redirect=true";
		else
			return "index??faces-redirect=true";
		}
		
	}catch(Exception ex)
	{
		System.out.println(ex); 
	}
	session.close();
	return "false";	
	}
	public static void insert(String name,String username,String pass,Boolean type) {
		/*factory = new AnnotationConfiguration().addAnnotatedClass(Employee.class).configure().buildSessionFactory();
		session=factory.openSession();
		Transaction tx=session.beginTransaction();*/
		Employee c=new Employee();
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