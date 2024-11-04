package dat.routes;


import dat.controllers.DoctorControllerDB;
import dat.controllers.TripController;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

public class TripRoutes {

    private final TripController tripController = new TripController();

    protected EndpointGroup getRoutes() {

        return () -> {

            post("/populate", tripController::populate);
            get("/", tripController::getAll);
            get("/{id}", tripController::getById);
            post("/", tripController::create);
            put("/{id}", tripController::update);
            delete("/{id}", tripController::delete);
            put("/{tripId}/guides/{guideId}", tripController::addGuideToTrip);


        };
    }
}