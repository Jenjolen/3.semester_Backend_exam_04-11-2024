package dat.restassured;

import dat.config.ApplicationConfig;
import dat.config.HibernateConfig;
import dat.config.Populator;
import dat.controllers.TripController;
import dat.daos.TripDAO;
import dat.dtos.GuideDTO;
import dat.dtos.GuideTotalPriceDTO;
import dat.dtos.TripDTO;
import dat.enums.Category;
import io.javalin.Javalin;
import io.restassured.common.mapper.TypeRef;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RestAssuredTest {

    private static final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryForTest();
    private static final TripDAO tripDAO = TripDAO.getInstance(emf);
    private static final TripController tripController = new TripController();
    private static Javalin app;

    private static final String BASE_URL = "http://localhost:7070/api/trips";

    @BeforeAll
    void setUpAll() {
        HibernateConfig.setTest(true);
        // Start the API
        app = ApplicationConfig.startServer(7070);
    }

    @BeforeEach
    void setUp() {
        System.out.println("Populating database with trips and guides");
        Populator populator = new Populator();
        populator.populate();
    }

    @AfterEach
    void tearDown() {
        try (EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Trip").executeUpdate();
            em.createQuery("DELETE FROM Guide").executeUpdate();
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterAll
    void tearDownAll() {
        ApplicationConfig.stopServer(app);
    }

    @Test
    void testPopulate() {
        String responseBody =
                given()
                        .when()
                        .post(BASE_URL + "/populate")
                        .then()
                        .statusCode(201)
                        .log().all()
                        .extract()
                        .asString();

        assertEquals("Database populated", responseBody);
    }

    @Test
    void testGetAllTrips() {
        List<TripDTO> tripDTOS =
                given()
                        .when()
                        .get(BASE_URL + "/")
                        .then()
                        .statusCode(200)
                        .log().all()
                        .extract()
                        .as(new TypeRef<List<TripDTO>>() {});

        assertEquals(7, tripDTOS.size());
    }

    @Test
    void testGetTripById() {
        TripDTO trip =
                given()
                        .when()
                        .get(BASE_URL + "/1")
                        .then()
                        .statusCode(200)
                        .log().all()
                        .extract()
                        .as(TripDTO.class);

        assertNotNull(trip);
        assertEquals(1, trip.getId());
        assertEquals("Budapest", trip.getStartPosition());
    }

    @Test
    void testCreateTrip() {
        TripDTO newTrip = new TripDTO();
        newTrip.setStartTime(LocalTime.of(11, 0));
        newTrip.setEndTime(LocalTime.of(19, 0));
        newTrip.setStartPosition("Seoul");
        newTrip.setName("Hongdae Shopping");
        newTrip.setPrice(240.59);
        newTrip.setCategory(Category.CITY);

        TripDTO createdTrip =
                given()
                        .contentType("application/json")
                        .body(newTrip)
                        .when()
                        .post(BASE_URL + "/")
                        .then()
                        .statusCode(201)
                        .log().all()
                        .extract()
                        .as(TripDTO.class);

        assertNotNull(createdTrip);
        assertNotNull(createdTrip.getId());
        assertEquals("Seoul", createdTrip.getStartPosition());
    }

    @Test
    void testUpdateTrip() {
        TripDTO updatedTrip = new TripDTO();
        updatedTrip.setStartTime(LocalTime.of(21, 0));
        updatedTrip.setEndTime(LocalTime.of(23, 0));
        updatedTrip.setStartPosition("Reykjavik");
        updatedTrip.setName("Spotting Northern Lights");
        updatedTrip.setPrice(300.59);
        updatedTrip.setCategory(Category.FOREST);

        TripDTO responseTrip =
                given()
                        .contentType("application/json")
                        .body(updatedTrip)
                        .when()
                        .put(BASE_URL + "/1")
                        .then()
                        .statusCode(200)
                        .log().all()
                        .extract()
                        .as(TripDTO.class);

        assertNotNull(responseTrip);
        assertEquals("Reykjavik", responseTrip.getStartPosition());
        assertEquals("Spotting Northern Lights", responseTrip.getName());
    }

    @Test
    void testDeleteTrip() {
        String response =
                given()
                        .when()
                        .delete(BASE_URL + "/3")
                        .then()
                        .statusCode(200)
                        .log().all()
                        .extract()
                        .asString();

        assertEquals("Trip deleted", response);
    }

    @Test
    void testAddGuideToTrip() {
        // Assuming trip with id=1 and guide with id=1 exist
        String response =
                given()
                        .when()
                        .put(BASE_URL + "/1/guides/1")
                        .then()
                        .statusCode(200)
                        .log().all()
                        .extract()
                        .asString();

        assertEquals("Guide added to trip", response);

        // Verify that the guide is added to the trip
        TripDTO trip =
                given()
                        .when()
                        .get(BASE_URL + "/1")
                        .then()
                        .statusCode(200)
                        .extract()
                        .as(TripDTO.class);

        assertNotNull(trip.getGuide());
        assertEquals(1, trip.getGuide().getId());
    }

    @Test
    void testGetGuidesTotalPrice() {
        List<GuideTotalPriceDTO> guides =
                given()
                        .when()
                        .get(BASE_URL + "/guides/totalprice")
                        .then()
                        .statusCode(200)
                        .log().all()
                        .extract()
                        .as(new TypeRef<List<GuideTotalPriceDTO>>() {});

        assertNotNull(guides);
        assertTrue(guides.size() > 0);


    }

    @Test
    void testGetTripsByGuide() {
        Set<TripDTO> trips =
                given()
                        .when()
                        .get(BASE_URL + "/guides/1")
                        .then()
                        .statusCode(200)
                        .log().all()
                        .extract()
                        .as(new TypeRef<Set<TripDTO>>() {});

        assertNotNull(trips);
        assertTrue(trips.size() > 0);

        // Ensure that all trips belong to guide with ID 1
        for (TripDTO trip : trips) {
            assertNotNull(trip.getGuide());
            assertEquals(1, trip.getGuide().getId());
        }
    }

    @Test
    void testGetTripsByCategory() {
        Set<TripDTO> trips =
                given()
                        .when()
                        .get(BASE_URL + "/category/CITY")
                        .then()
                        .statusCode(200)
                        .log().all()
                        .extract()
                        .as(new TypeRef<Set<TripDTO>>() {});

        assertNotNull(trips);
        assertTrue(trips.size() > 0);

        // Ensure that all trips have category CITY
        for (TripDTO trip : trips) {
            assertEquals(Category.CITY, trip.getCategory());
        }
    }
}
