package dat.controllers;

import dat.config.HibernateConfig;
import dat.daos.DoctorMockDAO;
import dat.dtos.DoctorDTO;
import dat.enums.Specialty;

import dat.exceptions.ApiException;
import io.javalin.http.BadRequestResponse;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;
import jakarta.persistence.EntityManagerFactory;

import java.time.LocalDate;
import java.util.List;

public class DoctorMockController {

    private final DoctorMockDAO doctorMockDAO = new DoctorMockDAO();

    public void readAll(Context ctx) throws ApiException {
        try {
            List<DoctorDTO> doctors = doctorMockDAO.readAll();
            ctx.res().setStatus(200);
            ctx.json(doctors);
        } catch (Exception e) {
            throw new ApiException(500, e.getMessage());
        }

    }

    public void read(Context ctx) throws ApiException {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            DoctorDTO doctor = doctorMockDAO.read(id);
            ctx.res().setStatus(200);
            ctx.json(doctor);
        } catch (Exception e) {
            throw new ApiException(404, e.getMessage());
        }
    }

    public void doctorBySpecialty(Context ctx) {
        Specialty specialty = Specialty.valueOf(ctx.pathParam("specialty"));
        List<DoctorDTO> doctors = doctorMockDAO.doctorBySpecialty(specialty);
        ctx.res().setStatus(200);
        ctx.json(doctors);
    }

    public void doctorByBirthdateRange(Context ctx) {
        LocalDate from = LocalDate.parse(ctx.queryParam("from"));
        LocalDate to = LocalDate.parse(ctx.queryParam("to"));
        List<DoctorDTO> doctors = doctorMockDAO.doctorByBirthdateRange(from, to);
        ctx.res().setStatus(200);
        ctx.json(doctors);
    }

    public void create(Context ctx) {
        DoctorDTO jsonRequest = ctx.bodyAsClass(DoctorDTO.class);
        DoctorDTO doctor = doctorMockDAO.create(jsonRequest);
        ctx.res().setStatus(201);
        ctx.json(doctor);
    }

    public void update(Context ctx) {
    int id = Integer.parseInt(ctx.pathParam("id"));
    DoctorDTO jsonRequest = ctx.bodyAsClass(DoctorDTO.class);
    DoctorDTO doctor = doctorMockDAO.update(id, jsonRequest);
    ctx.res().setStatus(200);
    ctx.json(doctor);
    }


}
