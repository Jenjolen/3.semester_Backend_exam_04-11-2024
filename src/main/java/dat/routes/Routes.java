package dat.routes;

import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

public class Routes {

    private final DoctorRoutes doctorRoutes = new DoctorRoutes();

    public EndpointGroup getRoutes() {
        return () -> {
            path("/doctors", doctorRoutes.getRoutes());


        };
    }
}