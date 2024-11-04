package dat.restassured;

import dat.config.ApplicationConfig;
import dat.config.HibernateConfig;

import dat.config.Populator;

import dat.controllers.DoctorControllerDB;
import dat.dtos.DoctorDTO;
import dat.entities.Appointment;
import dat.entities.Doctor;
import dat.enums.Specialty;
import io.javalin.Javalin;
import io.restassured.common.mapper.TypeRef;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RestAssuredTest {

    private final static EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryForTest();
    private static DoctorDAO doctorDAO = DoctorDAO.getInstance(emf);
    private static DoctorControllerDB doctorControllerDB = new DoctorControllerDB();
    private static Javalin app;
    private static List<Doctor> doctors;
    private static Doctor doctor1, doctor2, doctor3, doctor4, doctor5, doctor6, doctor7;
    private static List<Appointment> appointments;
    private static Appointment appointment1, appointment2, appointment3, appointment4, appointment5, appointment6, appointment7, appointment8, appointment9, appointment10, appointment11, appointment12, appointment13, appointment14;

    private static final String BASE_URL = "http://localhost:7070/api";

    @BeforeAll
    void setUpAll() {
        HibernateConfig.setTest(true);

        // Start api
        app = ApplicationConfig.startServer(7070);
    }

    @BeforeEach
    void setUp() {
        // Populate the database with hotels and rooms
        System.out.println("Populating database with doctors and appointments");
        Populator populator = new Populator();
        populator.populate();


    }

    @AfterEach
    void tearDown() {
        try (EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Appointment ").executeUpdate();
            em.createQuery("DELETE FROM Doctor ").executeUpdate();
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
    void readAll() {

        List<DoctorDTO> doctorDTOS =
                given()
                        .when()
                        .get(BASE_URL + "/doctors")
                        .then()
                        .statusCode(200)
                        .body("size()", is(7))
                        .log().all()
                        .extract()
                        .as(new TypeRef<List<DoctorDTO>>() {});

        assertThat(doctorDTOS.size(), is(7));

        assertThat(doctorDTOS.get(0).getName(), is("Dr. Alice Smith"));
        assertThat(doctorDTOS.get(1).getName(), is("Dr. Bob Johnson"));
        assertThat(doctorDTOS.get(2).getName(), is("Dr. Clara Lee"));
        assertThat(doctorDTOS.get(3).getName(), is("Dr. David Park"));
        assertThat(doctorDTOS.get(4).getName(), is("Dr. Emily White"));
        assertThat(doctorDTOS.get(5).getName(), is("Dr. Fiona Martinez"));
        assertThat(doctorDTOS.get(6).getName(), is("Dr. George Kim"));
    }

    @Test
    void populate() {
        String responseBody =
                given()
                        .when()
                        .get(BASE_URL + "/doctors/populate")
                        .then()
                        .statusCode(201)
                        .log().all()
                        .extract()
                        .asString();

        assertThat(responseBody, is("Database populated"));
    }




    @Test
    void readOneDoctor() {
        DoctorDTO doctorDTO =
                given()
                        .pathParam("id", 1)
                        .when()
                        .get(BASE_URL + "/doctors/{id}")
                        .then()
                        .statusCode(200)
                        .log().all()
                        .extract()
                        .as(DoctorDTO.class);

        assertThat(doctorDTO.getName(), is("Dr. Alice Smith"));
    }

    @Test
    void doctorBySpecialty() {
        List<DoctorDTO> doctorDTOS =
                given()
                        .pathParam("specialty", "FAMILY_MEDICINE")
                        .when()
                        .get(BASE_URL + "/doctors/specialty/{specialty}")
                        .then()
                        .statusCode(200)
                        .log().all()
                        .extract()
                        .as(new TypeRef<List<DoctorDTO>>() {});

        assertThat(doctorDTOS.size(), is(2));

        assertThat(doctorDTOS.get(0).getName(), is("Dr. Alice Smith"));
        assertThat(doctorDTOS.get(1).getName(), is("Dr. George Kim"));
    }

    @Test
    void doctorByBirthdateRange() {
        List<DoctorDTO> doctorDTOS =
                given()
                        .queryParam("from", "1980-01-01")
                        .queryParam("to", "1985-01-01")
                        .when()
                        .get(BASE_URL + "/doctors/birthdate/range")
                        .then()
                        .statusCode(200)
                        .log().all()
                        .extract()
                        .as(new TypeRef<List<DoctorDTO>>() {});

        assertThat(doctorDTOS.size(), is(3));

        assertThat(doctorDTOS.get(0).getName(), is("Dr. Bob Johnson"));
        assertThat(doctorDTOS.get(1).getName(), is("Dr. Clara Lee"));
        assertThat(doctorDTOS.get(2).getName(), is("Dr. Emily White"));

    }




    @Test
    void createDoctor() {
        DoctorDTO doctorDTO = new DoctorDTO("Dr. Namjoon Kim", LocalDate.of(1994, 9, 12), 2020, "Bangtan Lab", Specialty.SURGERY);

        DoctorDTO createdDoctor =
                given()
                        .contentType("application/json")
                        .body(doctorDTO)
                        .when()
                        .post(BASE_URL + "/doctors")
                        .then()
                        .statusCode(201)
                        .log().all()
                        .extract()
                        .as(DoctorDTO.class);

        assertThat(createdDoctor.getName(), is("Dr. Namjoon Kim"));
    }

    @Test
    void updateDoctor() {

        DoctorDTO fetchedDoctor = doctorDAO.read(1);
        fetchedDoctor.setSpecialty(Specialty.SURGERY);
        fetchedDoctor.setDateOfBirth(LocalDate.of(1994, 9, 12));
        fetchedDoctor.setYearOfGraduation(2020);
        fetchedDoctor.setNameOfClinic("Bangtan Lab");

        DoctorDTO updatedDoctor =
                given()
                        .pathParam("id", 1)
                        .contentType("application/json")
                        .body(fetchedDoctor)
                        .when()
                        .put(BASE_URL + "/doctors/{id}")
                        .then()
                        .statusCode(200)
                        .log().all()
                        .extract()
                        .as(DoctorDTO.class);

        assertThat(updatedDoctor.getSpecialty(), is(Specialty.SURGERY));
        assertThat(updatedDoctor.getDateOfBirth(), is(LocalDate.of(1994, 9, 12)));
        assertThat(updatedDoctor.getYearOfGraduation(), is(2020));
        assertThat(updatedDoctor.getNameOfClinic(), is("Bangtan Lab"));

    }

}