package dat.controllers;

import dat.config.HibernateConfig;
import dat.config.Populator;
import dat.daos.GuideDAO;
import dat.daos.TripDAO;
import dat.dtos.GuideDTO;
import dat.dtos.GuideTotalPriceDTO;
import dat.dtos.TripDTO;
import dat.enums.Category;
import dat.exceptions.ApiException;
import io.javalin.http.Context;
import jakarta.persistence.EntityManagerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
        tripDAO.delete(id);
        ctx.status(200).json("Trip deleted");
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

    public void getTripsByGuide(Context ctx) throws ApiException {
        int guideId = Integer.parseInt(ctx.pathParam("guideId"));
        Set<TripDTO> trips = tripDAO.getTripsByGuide(guideId);
        if (trips.isEmpty()) {
            ctx.res().setStatus(404);
            throw new ApiException(404, "No trips found");
        }
        ctx.res().setStatus(200);
        ctx.json(trips);
    }

    public void getTripsByCategory(Context ctx) throws ApiException {
        Category category = Category.valueOf(ctx.pathParam("category"));
        List<TripDTO> trips = tripDAO.getAll();
        if (trips.isEmpty()) {
            ctx.res().setStatus(404);
            throw new ApiException(404, "No trips found");
        }
        Set<TripDTO> categorizedTrips = trips.stream().filter(trip -> trip.getCategory().equals(category)).collect(Collectors.toSet());
        ctx.res().setStatus(200);
        ctx.json(categorizedTrips);
    }

    // Get each trip guide's total price, so that we will have a list of guides with their total price

    public void getGuidesTotalPrice(Context ctx) throws ApiException {
        List<GuideDTO> guides = guideDAO.getAll();
        if (guides.isEmpty()) {
            throw new ApiException(404, "No guides found");
        }

        List<GuideTotalPriceDTO> result = new ArrayList<>();
        for (GuideDTO guide : guides) {
            double totalPrice = 0.0;
            if (guide.getTrips() != null) {
                for (TripDTO trip : guide.getTrips()) {
                    totalPrice += trip.getPrice();
                }
            }
            GuideTotalPriceDTO guideTotalPriceDTO = new GuideTotalPriceDTO(guide, totalPrice);
            result.add(guideTotalPriceDTO);
        }

        ctx.status(200).json(result);
    }





}
