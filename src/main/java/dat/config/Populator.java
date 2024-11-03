package dat.config;

import dat.dtos.DoctorDTO;
import dat.entities.Appointment;
import dat.entities.Doctor;
import dat.enums.Specialty;
import jakarta.persistence.EntityManagerFactory;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class Populator {

    EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();

    public void populate() {

        // Doctors
        Doctor doctor1 = new Doctor("Dr. Alice Smith", LocalDate.of(1975, 4, 12), 2000, "City Health Clinic", Specialty.FAMILY_MEDICINE);
        Doctor doctor2 = new Doctor("Dr. Bob Johnson", LocalDate.of(1980, 8, 5), 2005, "Downtown Medical Center", Specialty.SURGERY);
        Doctor doctor3 = new Doctor("Dr. Clara Lee", LocalDate.of(1983, 7, 22), 2008, "Green Valley Hospital", Specialty.PEDIATRICS);
        Doctor doctor4 = new Doctor("Dr. David Park", LocalDate.of(1978, 11, 15), 2003, "Hillside Medical Practice", Specialty.PSYCHIATRY);
        Doctor doctor5 = new Doctor("Dr. Emily White", LocalDate.of(1982, 9, 30), 2007, "Metro Health Center", Specialty.GERIATRICS);
        Doctor doctor6 = new Doctor("Dr. Fiona Martinez", LocalDate.of(1985, 2, 17), 2010, "Riverside Wellness Clinic", Specialty.SURGERY);
        Doctor doctor7 = new Doctor("Dr. George Kim", LocalDate.of(1979, 5, 29), 2004, "Summit Health Institute", Specialty.FAMILY_MEDICINE);

        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(doctor1);
            em.persist(doctor2);
            em.persist(doctor3);
            em.persist(doctor4);
            em.persist(doctor5);
            em.persist(doctor6);
            em.persist(doctor7);
            em.getTransaction().commit();
        }

        // Appointments

        Appointment appointment1 = new Appointment("Clara Jones", LocalDate.of(2021, 10, 15), LocalTime.of(10,00), "City Health Clinic", doctor1);
        Appointment appointment2 = new Appointment("David Smith", LocalDate.of(2021, 10, 15), LocalTime.of(11,00), "City Health Clinic", doctor1);
        Appointment appointment3 = new Appointment("Emily Brown", LocalDate.of(2023, 10, 15), LocalTime.of(12,00), "Downtown Medical Center", doctor2);
        Appointment appointment4 = new Appointment("Fiona White", LocalDate.of(2021, 10, 15), LocalTime.of(13,00), "Downtown Medical Center", doctor2);
        Appointment appointment5 = new Appointment("Penelope Rose", LocalDate.of(2022, 8, 22), LocalTime.of(11,00), "Green Valley Hospital", doctor3);
        Appointment appointment6 = new Appointment("Quincy Black", LocalDate.of(2022, 8, 22), LocalTime.of(12,00), "Green Valley Hospital", doctor3);
        Appointment appointment7 = new Appointment("Roseanne Park", LocalDate.of(2020, 4, 10), LocalTime.of(12,00), "Hillside Medical Practice", doctor4);
        Appointment appointment8 = new Appointment("Samuel Green", LocalDate.of(2020, 4, 10), LocalTime.of(13,00), "Hillside Medical Practice", doctor4);
        Appointment appointment9 = new Appointment("Trevor Blue", LocalDate.of(2022, 1, 5), LocalTime.of(10,00), "Metro Health Center", doctor5);
        Appointment appointment10 = new Appointment("Ursula White", LocalDate.of(2023, 10, 5), LocalTime.of(10,00), "Metro Health Center", doctor5);
        Appointment appointment11 = new Appointment("Violet Black", LocalDate.of(2022, 10, 5), LocalTime.of(11,00), "Riverside Wellness Clinic", doctor6);
        Appointment appointment12 = new Appointment("Wendy Green", LocalDate.of(2022, 10, 5), LocalTime.of(12,00), "Riverside Wellness Clinic", doctor6);
        Appointment appointment13 = new Appointment("Xander Blue", LocalDate.of(2022, 12, 20), LocalTime.of(13,00), "Summit Health Institute", doctor7);
        Appointment appointment14 = new Appointment("Yvonne White", LocalDate.of(2023, 2, 5), LocalTime.of(14,00), "Summit Health Institute", doctor7);

        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(appointment1);
            em.persist(appointment2);
            em.persist(appointment3);
            em.persist(appointment4);
            em.persist(appointment5);
            em.persist(appointment6);
            em.persist(appointment7);
            em.persist(appointment8);
            em.persist(appointment9);
            em.persist(appointment10);
            em.persist(appointment11);
            em.persist(appointment12);
            em.persist(appointment13);
            em.persist(appointment14);
            em.getTransaction().commit();
        }
    }

}
