package dat.routes;


import dat.controllers.TripController;
import dat.security.enums.Role;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

public class TripRoutes {

    private final TripController tripController = new TripController();

    protected EndpointGroup getRoutes() {

        return () -> {

            post("/populate", tripController::populate, Role.ANYONE);
            get("/category/{category}", tripController::getTripsByCategory, Role.ANYONE);
            get("/", tripController::getAll, Role.ANYONE);
            get("/{id}", tripController::getById, Role.ANYONE);
            post("/", tripController::create, Role.USER, Role.ADMIN);
            put("/{id}", tripController::update, Role.ADMIN);
            delete("/{id}", tripController::delete, Role.ADMIN);
            get("/guides/totalprice", tripController::getGuidesTotalPrice, Role.ADMIN);
            put("/{tripId}/guides/{guideId}", tripController::addGuideToTrip, Role.ADMIN);
            get("/guides/{guideId}", tripController::getTripsByGuide, Role.ANYONE);



        };
    }
}