package dat.routes;


import dat.controllers.DoctorControllerDB;
import dat.controllers.DoctorMockController;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

public class DoctorRoutes {

    private final DoctorControllerDB doctorControllerDB = new DoctorControllerDB();

    protected EndpointGroup getRoutes() {

        return () -> {

            get("/populate", doctorControllerDB::populate);
            get("/", doctorControllerDB::readAll);
            get("/{id}", doctorControllerDB::read);
            get("/specialty/{specialty}", doctorControllerDB::doctorBySpecialty);
            get("/birthdate/range", doctorControllerDB::doctorByBirthdateRange);
            post("/", doctorControllerDB::create);
            put("/{id}", doctorControllerDB::update);


        };
    }
}