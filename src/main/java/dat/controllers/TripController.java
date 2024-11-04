package dat.controllers;

import dat.config.HibernateConfig;
import dat.config.Populator;
import dat.daos.DoctorDAO;
import dat.daos.GuideDAO;
import dat.daos.TripDAO;
import dat.dtos.DoctorDTO;
import dat.dtos.TripDTO;
import dat.enums.Specialty;
import dat.exceptions.ApiException;
import io.javalin.http.Context;
import jakarta.persistence.EntityManagerFactory;

import java.time.LocalDate;
import java.util.List;

public class TripController {
    private final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
    private final TripDAO tripDAO = TripDAO.getInstance(emf);
    private final GuideDAO guideDAO = GuideDAO.getInstance(emf);


    public void populate(Context ctx) {
        Populator populator = new Populator();
        populator.populate();
        ctx.res().setStatus(201);
        ctx.json("Database populated");
    }

    public void getAll(Context ctx) throws ApiException {
        List<TripDTO> trips = tripDAO.getAll();
        ctx.res().setStatus(200);
        ctx.json(trips);

    }

    public void getById(Context ctx) throws ApiException {
        int id = Integer.parseInt(ctx.pathParam("id"));
        TripDTO trip = tripDAO.getById(id);
        ctx.res().setStatus(200);
        ctx.json(trip);
        if (trip == null) {
            ctx.res().setStatus(404);
            throw new ApiException(404, "Trip does not exist");
        }


    }

    public void create(Context ctx) {
        TripDTO jsonRequest = ctx.bodyAsClass(TripDTO.class);
        TripDTO trip = tripDAO.create(jsonRequest);
        ctx.res().setStatus(201);
        ctx.json(trip);
    }

    public void update(Context ctx) throws ApiException {
        int id = Integer.parseInt(ctx.pathParam("id"));
        TripDTO jsonRequest = ctx.bodyAsClass(TripDTO.class);
        TripDTO trip = tripDAO.update(id, jsonRequest);
        ctx.res().setStatus(200);
        ctx.json(trip);
        if (trip == null) {
            ctx.res().setStatus(404);
            throw new ApiException(404, "Trip does not exist");
        }
    }

    public void delete(Context ctx) throws ApiException {
        int id = Integer.parseInt(ctx.pathParam("id"));
        try {
            tripDAO.delete(id);
            ctx.res().setStatus(200);
            ctx.json("Trip deleted");
        } catch (Exception e) {
            ctx.res().setStatus(404);
            throw new ApiException(404, "Trip does not exist");
        }


    }

    public void addGuideToTrip(Context ctx) throws ApiException {
        int tripId = Integer.parseInt(ctx.pathParam("tripId"));
        int guideId = Integer.parseInt(ctx.pathParam("guideId"));

        if (tripDAO.getById(tripId) == null || guideDAO.getById(guideId) == null) {
            ctx.res().setStatus(404);
            throw new ApiException(404, "Trip or guide does not exist");
        }

        tripDAO.addGuideToTrip(tripId, guideId);
        ctx.res().setStatus(200);
        ctx.json("Guide added to trip");

    }


}
