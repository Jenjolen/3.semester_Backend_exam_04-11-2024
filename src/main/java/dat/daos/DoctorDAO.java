package dat.daos;

import dat.config.HibernateConfig;
import dat.dtos.DoctorDTO;
import dat.entities.Doctor;
import dat.enums.Specialty;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.time.LocalDate;
import java.util.List;

public class DoctorDAO implements iDAO<DoctorDTO, Integer> {

    private static DoctorDAO instance;
    private static EntityManagerFactory emf;

    public static DoctorDAO getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new DoctorDAO() ;
        }
        return instance;
    }


    @Override
    public DoctorDTO read(Integer integer) {
        try (EntityManager em = emf.createEntityManager()) {
            Doctor doctor = em.find(Doctor.class, integer);
            return new DoctorDTO(doctor);

        }
    }

    @Override
    public List<DoctorDTO> readAll() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery <DoctorDTO> query = em.createQuery("SELECT new dat.dtos.DoctorDTO(d) FROM Doctor d", DoctorDTO.class);
            return query.getResultList();
        }
    }

    public List<DoctorDTO> doctorBySpecialty(Specialty specialty) {
    try (EntityManager em = emf.createEntityManager()) {
        TypedQuery <DoctorDTO> query = em.createQuery("SELECT new dat.dtos.DoctorDTO(d) FROM Doctor d WHERE d.specialty = :specialty", DoctorDTO.class);
        query.setParameter("specialty", specialty);
        return query.getResultList();

    }

    }

    public List<DoctorDTO> doctorByBirthdateRange(LocalDate from, LocalDate to) {
    try(EntityManager em = emf.createEntityManager()) {
        TypedQuery<DoctorDTO> query = em.createQuery("SELECT new dat.dtos.DoctorDTO(d) FROM Doctor d WHERE d.dateOfBirth BETWEEN :from AND :to", DoctorDTO.class);
        query.setParameter("from", from);
        query.setParameter("to", to);
        return query.getResultList();
    }
    }


    @Override
    public DoctorDTO create(DoctorDTO doctorDTO) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Doctor doctor = new Doctor(doctorDTO);
            em.persist(doctor);
            em.getTransaction().commit();
            return new DoctorDTO(doctor);
        }
    }

    @Override
    public DoctorDTO update(Integer integer, DoctorDTO doctorDTO) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Doctor doctor = em.find(Doctor.class, integer);
            doctor.setDateOfBirth(doctorDTO.getDateOfBirth());
            doctor.setYearOfGraduation(doctorDTO.getYearOfGraduation());
            doctor.setNameOfClinic(doctorDTO.getNameOfClinic());
            doctor.setSpecialty(doctorDTO.getSpecialty());
            Doctor mergedDoctor = em.merge(doctor);
            em.getTransaction().commit();
            return new DoctorDTO(mergedDoctor);

        }
    }
}
