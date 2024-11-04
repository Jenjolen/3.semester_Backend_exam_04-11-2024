package dat.controllers;

import dat.config.HibernateConfig;
import dat.config.Populator;
import dat.daos.DoctorDAO;
import dat.dtos.DoctorDTO;
import dat.enums.Specialty;
import dat.exceptions.ApiException;
import io.javalin.http.Context;
import jakarta.persistence.EntityManagerFactory;

import java.time.LocalDate;
import java.util.List;

public class DoctorControllerDB {
    private final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
    private final DoctorDAO doctorDAO = DoctorDAO.getInstance(emf);


    public void populate(Context ctx) {
        Populator populator = new Populator();
        populator.populate();
        ctx.res().setStatus(201);
        ctx.json("Database populated");
    }

    public void readAll(Context ctx) throws ApiException {

            List<DoctorDTO> doctors = doctorDAO.readAll();
            ctx.res().setStatus(200);
            ctx.json(doctors);

    }

    public void read(Context ctx) throws ApiException {
            int id = Integer.parseInt(ctx.pathParam("id"));
            DoctorDTO doctor = doctorDAO.read(id);
            ctx.res().setStatus(200);
            ctx.json(doctor);

    }

    public void doctorBySpecialty(Context ctx) {
        Specialty specialty = Specialty.valueOf(ctx.pathParam("specialty"));
        List<DoctorDTO> doctors = doctorDAO.doctorBySpecialty(specialty);
        ctx.res().setStatus(200);
        ctx.json(doctors);
    }

    public void doctorByBirthdateRange(Context ctx) {
        LocalDate from = LocalDate.parse(ctx.queryParam("from"));
        LocalDate to = LocalDate.parse(ctx.queryParam("to"));
        List<DoctorDTO> doctors = doctorDAO.doctorByBirthdateRange(from, to);
        ctx.res().setStatus(200);
        ctx.json(doctors);
    }

    public void create(Context ctx) {
        DoctorDTO jsonRequest = ctx.bodyAsClass(DoctorDTO.class);
        DoctorDTO doctor = doctorDAO.create(jsonRequest);
        ctx.res().setStatus(201);
        ctx.json(doctor);
    }

    public void update(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        DoctorDTO jsonRequest = ctx.bodyAsClass(DoctorDTO.class);
        DoctorDTO doctor = doctorDAO.update(id, jsonRequest);
        ctx.res().setStatus(200);
        ctx.json(doctor);
    }

}
