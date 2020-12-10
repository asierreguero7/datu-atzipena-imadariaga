package exekutagarriak;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import model.Artist;

public class OinarrizkoEragiketak {

	public static SessionFactory sf = new Configuration().configure().buildSessionFactory();

	public static void main(String[] args) {

		datuaGorde(new Artist(276, "Markel"));
                datuaGorde(new Artist(277, "Asier"));
		datuakIkusi();
                datuakEzabatu(new Artist(277, "Asier"));
                datuakIkusi();
	}

	public static void datuaGorde(Artist c) {

		Session saioa = sf.openSession();
		saioa.beginTransaction();
		saioa.save(c);
		saioa.getTransaction().commit();
		saioa.close();

	}

	public static void datuakIkusi() {

		Session saioa = sf.openSession();
		saioa.beginTransaction();
		List result = saioa.createQuery("from Artist").list(); // HQL deitzen dan lengoaia idatziko dugu Querya
		for (Artist c : (List<Artist>) result) {
			System.out.println(c);
		}
		saioa.getTransaction().commit();
		saioa.close();
	}
        
        public static void datuakEzabatu(Artist c){
            Session saioa = sf.openSession();
            saioa.beginTransaction();
            saioa.delete(c);
            saioa.getTransaction().commit();
            saioa.close();
        }
}