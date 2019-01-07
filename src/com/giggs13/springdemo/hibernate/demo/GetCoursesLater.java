package com.giggs13.springdemo.hibernate.demo;

import com.giggs13.springdemo.hibernate.entity.Course;
import com.giggs13.springdemo.hibernate.entity.Instructor;
import com.giggs13.springdemo.hibernate.entity.InstructorDetail;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.List;

public class GetCoursesLater {

    public static void main(String[] args) {
        try (SessionFactory sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Instructor.class)
                .addAnnotatedClass(InstructorDetail.class)
                .addAnnotatedClass(Course.class)
                .buildSessionFactory()) {
            try (Session session = sessionFactory.getCurrentSession()) {
                session.beginTransaction();

                int id = 1;
                Instructor instructor = session.get(Instructor.class, id);
                System.out.println("Instructor: " + instructor);

                session.getTransaction().commit();
            }
            try (Session session = sessionFactory.getCurrentSession()) {
                session.beginTransaction();

                Query<Course> query = session.createQuery("select c from Course c "
                                + "where c.instructor.id=:instructorId",
                        Course.class);
                query.setParameter("instructorId", 1);
                List<Course> courses = query.getResultList();
                System.out.println("Courses: " + courses);

                session.getTransaction().commit();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
