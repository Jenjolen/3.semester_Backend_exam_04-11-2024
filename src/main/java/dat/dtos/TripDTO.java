package dat.dtos;

import dat.entities.Guide;
import dat.entities.Trip;
import dat.enums.Category;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TripDTO {

    private int id;

    private LocalTime startTime;

    private LocalTime endTime;

    private String startPosition;

    private String name;

    private double price;

    private Category category;

    private GuideDTO guide;

    public TripDTO(int id, LocalTime startTime, LocalTime endTime, String startPosition, String name, double price, Category category) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.startPosition = startPosition;
        this.name = name;
        this.price = price;
        this.category = category;
    }

    public TripDTO(LocalTime startTime, LocalTime endTime, String startPosition, String name, double price, Category category) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.startPosition = startPosition;
        this.name = name;
        this.price = price;
        this.category = category;
    }

    public TripDTO(LocalTime startTime, LocalTime endTime, String startPosition, String name, double price, Category category, GuideDTO guide) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.startPosition = startPosition;
        this.name = name;
        this.price = price;
        this.category = category;
        this.guide = guide;
    }

    public TripDTO (Trip trip){
        this.id = trip.getId();
        this.startTime = trip.getStartTime();
        this.endTime = trip.getEndTime();
        this.startPosition = trip.getStartPosition();
        this.name = trip.getName();
        this.price = trip.getPrice();
        this.category = trip.getCategory();
        if (trip.getGuide() != null) {
            Guide guide = trip.getGuide();
            this.guide = new GuideDTO();
            this.guide.setId(guide.getId());
            this.guide.setFirstName(guide.getFirstName());
            this.guide.setLastName(guide.getLastName());
            this.guide.setEmail(guide.getEmail());
            this.guide.setPhone(guide.getPhone());
            this.guide.setYearsOfExperience(guide.getYearsOfExperience());
            // pga. stackoverflow fejl, så er trips ikke med her
        }
    }

}
