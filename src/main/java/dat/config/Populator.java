package dat.config;


import dat.daos.GuideDAO;
import dat.daos.TripDAO;
import dat.dtos.TripDTO;
import dat.entities.Guide;
import dat.entities.Trip;
import dat.enums.Category;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Populator {

    EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();

    public static void main(String[] args) {
        Populator populator = new Populator();
        populator.populate();
    }

    public void populate() {

        try (EntityManager em = emf.createEntityManager()) {


            TripDAO tripDAO = TripDAO.getInstance(emf);
            GuideDAO guideDAO = GuideDAO.getInstance(emf);

            // Adding trips to database

            Trip trip1 = new Trip(LocalTime.of(10, 0), LocalTime.of(12, 0), "Budapest", "Sightseeing", 150.99, Category.CITY);
            Trip trip2 = new Trip(LocalTime.of(14, 0), LocalTime.of(16, 0), "Balaton", "Sailing", 200.99, Category.LAKE);
            Trip trip3 = new Trip(LocalTime.of(8, 0), LocalTime.of(20, 0), "Zakopane", "Skiing", 300.99, Category.SNOW);
            Trip trip4 = new Trip(LocalTime.of(10, 0), LocalTime.of(15, 0), "Barcelona", "Beach", 250.99, Category.BEACH);
            Trip trip5 = new Trip(LocalTime.of(10, 0), LocalTime.of(18, 0), "Rome", "Sightseeing", 150.99, Category.CITY);
            Trip trip6 = new Trip(LocalTime.of(8, 0), LocalTime.of(18, 0), "Las Palmas", "Tour around Gran Canaria", 150.99, Category.SEA);
            Trip trip7 = new Trip(LocalTime.of(10, 0), LocalTime.of(16, 0), "Tatras", "Hiking", 150.99, Category.FOREST);

            em.getTransaction().begin();

            em.persist(trip1);
            em.persist(trip2);
            em.persist(trip3);
            em.persist(trip4);
            em.persist(trip5);
            em.persist(trip6);
            em.persist(trip7);

            em.getTransaction().commit();

            // Adding guides to database

            Guide guide1 = new Guide("Gertha", "Nerthoosen", "guidegertha@localtours", "2375790", 4);
            Guide guide2 = new Guide("Hans", "Müller", "guidehans@globaltours", "3257880020", 2);
            Guide guide3 = new Guide("Maria", "Garcia", "guidemaria@localtours", "9862048576", 10);
            Guide guide4 = new Guide("Józef", "Kowalski", "guidejozef@globaltours", "3257880020", 5);

            em.getTransaction().begin();

            em.persist(guide1);
            em.persist(guide2);
            em.persist(guide3);
            em.persist(guide4);

            em.getTransaction().commit();

            // Assigning guides to trips

            trip1.setGuide(guide1);
            trip2.setGuide(guide2);
            trip3.setGuide(guide3);
            trip4.setGuide(guide4);
            trip5.setGuide(guide1);
            trip6.setGuide(guide2);
            trip7.setGuide(guide3);

            guide1.setTrips(new HashSet<>(List.of(trip1, trip5)));
            guide2.setTrips(new HashSet<>(List.of(trip2, trip6)));
            guide3.setTrips(new HashSet<>(List.of(trip3, trip7)));
            guide4.setTrips(new HashSet<>(List.of(trip4)));

            em.getTransaction().begin();

            em.merge(trip1);
            em.merge(trip2);
            em.merge(trip3);
            em.merge(trip4);
            em.merge(trip5);
            em.merge(trip6);
            em.merge(trip7);

            em.getTransaction().commit();

            em.getTransaction().begin();

            em.merge(guide1);
            em.merge(guide2);
            em.merge(guide3);
            em.merge(guide4);

            em.getTransaction().commit();


        }

    }

    }
