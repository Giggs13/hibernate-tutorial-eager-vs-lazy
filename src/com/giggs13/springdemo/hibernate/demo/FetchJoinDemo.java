package com.giggs13.springdemo.hibernate.demo;

import com.giggs13.springdemo.hibernate.entity.Course;
import com.giggs13.springdemo.hibernate.entity.Instructor;
import com.giggs13.springdemo.hibernate.entity.InstructorDetail;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

public class FetchJoinDemo {

    public static void main(String[] args) {
        try (SessionFactory sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Instructor.class)
                .addAnnotatedClass(InstructorDetail.class)
                .addAnnotatedClass(Course.class)
                .buildSessionFactory();
             Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();

            int id = 1;
            Query<Instructor> query = session.createQuery("select i from Instructor i " +
                            "join fetch i.courses " +
                            "where i.id = :id",
                    Instructor.class);
            query.setParameter("id", id);
            Instructor instructor = query.getSingleResult();
            System.out.println("Instructor: " + instructor);
            session.getTransaction().commit();
            System.out.println("Courses: " + instructor.getCourses());

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
