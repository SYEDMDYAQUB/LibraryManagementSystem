package demo;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.classic.Session;

@ManagedBean
@SessionScoped
public class mainclass implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4110901742187134449L;
	static SessionFactory factory;
	static Session session;
	static org.hibernate.Transaction tx;

	public static void main(String[] args) {
		// System.out.println(create());
		System.out.println("In main class");
		// System.out.println(Cake.run());

	}

	public static String insert(int id, String name) {
		StringBuilder str = new StringBuilder();
		session = factory.openSession();
		tx = session.beginTransaction();
		Employee c = new Employee();
		
		session.save(c);
		session.flush();
		tx.commit();
		session.close();
		str.append("Session Closed ");
		return str.toString();
	}

	public static String sesscreate() {
		StringBuilder str = new StringBuilder();
		factory = new AnnotationConfiguration().addAnnotatedClass(Employee.class).configure().buildSessionFactory();
		str.append("Session Opened ");
		return str.toString();
	}
	
}