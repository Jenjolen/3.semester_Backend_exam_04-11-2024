package dat.daos;

import dat.dtos.TripDTO;
import dat.entities.Trip;
import dat.entities.Guide;
import dat.security.exceptions.ApiException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TripDAO implements iDAO<TripDTO, Integer>, ITripGuideDAO {

    private static TripDAO instance;
    private static EntityManagerFactory emf;

    public static TripDAO getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new TripDAO();
        }
        return instance;
    }

    @Override
    public TripDTO getById(Integer integer) {
        try (EntityManager em = emf.createEntityManager()) {
            Trip trip = em.find(Trip.class, integer);
            if (trip == null) {
                throw new ApiException(404, "Trip does not exist");
            }
            return new TripDTO(trip);

        }
    }

    @Override
    public List<TripDTO> getAll() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Trip> query = em.createQuery("SELECT t FROM Trip t", Trip.class);
            List<Trip> trips = query.getResultList();
            return trips.stream().map(TripDTO::new).collect(Collectors.toList());
        }
    }

    @Override
    public TripDTO create(TripDTO tripDTO) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Trip trip = new Trip(tripDTO);
            em.persist(trip);
            em.getTransaction().commit();
            return new TripDTO(trip);
        }
    }

    @Override
    public TripDTO update(Integer integer, TripDTO tripDTO) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Trip trip = em.find(Trip.class, integer);
            if (trip == null) {
                throw new ApiException(404, "Trip does not exist");
            }

            trip.setStartTime(tripDTO.getStartTime());
            trip.setEndTime(tripDTO.getEndTime());
            trip.setStartPosition(tripDTO.getStartPosition());
            trip.setName(tripDTO.getName());
            trip.setPrice(tripDTO.getPrice());
            trip.setCategory(tripDTO.getCategory());

            if (tripDTO.getGuide() != null) {
                trip.setGuide(new Guide(tripDTO.getGuide()));
            }
            em.merge(trip);
            em.getTransaction().commit();
            return new TripDTO(trip);
        }
    }

    @Override
    public TripDTO delete(Integer id) throws ApiException {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Trip trip = em.find(Trip.class, id);
            if (trip == null) {
                em.getTransaction().rollback();
                throw new ApiException(404, "Trip not found");
            }
            em.remove(trip);
            em.getTransaction().commit();
            return new TripDTO(trip);
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace(); // Log the exception
            throw e;
        } finally {
            em.close();
        }
    }




    @Override
    public void addGuideToTrip(int tripId, int guideId) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Trip trip = em.find(Trip.class, tripId);
            Guide guide = em.find(Guide.class, guideId);
            if (trip == null || guide == null) {
                throw new ApiException(404, "Trip or Guide does not exist");
            }
                trip.setGuide(guide);
                guide.getTrips().add(trip);
                em.merge(trip);
                em.merge(guide);
                em.getTransaction().commit();

            }
        }

        @Override
        public Set<TripDTO> getTripsByGuide ( int guideId){
            try (EntityManager em = emf.createEntityManager()) {
                TypedQuery<TripDTO> query = em.createQuery("SELECT new dat.dtos.TripDTO(t) FROM Trip t WHERE t.guide.id = :guideId", TripDTO.class);
                query.setParameter("guideId", guideId);
                return query.getResultList().stream().collect(Collectors.toSet());

            }
        }
    }
