package dat.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import dat.dtos.GuideDTO;
import dat.dtos.TripDTO;
import dat.enums.Category;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "trip")
public class Trip {

    @Id
    @GeneratedValue (strategy = jakarta.persistence.GenerationType.IDENTITY)
    private int id;

    @Column (name = "start_time", nullable = false)
    private LocalTime startTime;

    @Column (name = "end_time", nullable = false)
    private LocalTime endTime;


    @Column (name = "start_position", nullable = false)
    private String startPosition;

    @Column (name = "name", nullable = false)
    private String name;

    @Column (name = "price", nullable = false)
    private double price;


    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private Category category;



    @ManyToOne
    @JoinColumn(name = "guide")
    @JsonBackReference
    private Guide guide;

    public Trip(int id, LocalTime startTime, LocalTime endTime, String startPosition, String name, double price, Category category) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.startPosition = startPosition;
        this.name = name;
        this.price = price;
        this.category = category;
    }

    public Trip(LocalTime startTime, LocalTime endTime, String startPosition, String name, double price, Category category) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.startPosition = startPosition;
        this.name = name;
        this.price = price;
        this.category = category;
    }

    public Trip(LocalTime startTime, LocalTime endTime, String startPosition, String name, double price, Category category, Guide guide) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.startPosition = startPosition;
        this.name = name;
        this.price = price;
        this.category = category;
        this.guide = guide;
    }

    public Trip (TripDTO tripDTO){
        this.id = tripDTO.getId();
        this.startTime = tripDTO.getStartTime();
        this.endTime = tripDTO.getEndTime();
        this.startPosition = tripDTO.getStartPosition();
        this.name = tripDTO.getName();
        this.price = tripDTO.getPrice();
        this.category = tripDTO.getCategory();
        if (tripDTO.getGuide() != null) {
            GuideDTO guideDTO = tripDTO.getGuide();
            this.guide = new Guide();
            this.guide.setId(guide.getId());
            this.guide.setFirstName(guide.getFirstName());
            this.guide.setLastName(guide.getLastName());
            this.guide.setEmail(guide.getEmail());
            this.guide.setPhone(guide.getPhone());
            this.guide.setYearsOfExperience(guide.getYearsOfExperience());
            // pga. stackoverflow fejl er trips ikke med her
        }
    }

}
