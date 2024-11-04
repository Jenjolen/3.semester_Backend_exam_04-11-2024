package dat.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import dat.dtos.GuideDTO;
import dat.dtos.TripDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "guide")
public class Guide {

    @Id
    @GeneratedValue (strategy = jakarta.persistence.GenerationType.IDENTITY)
    private int id;

    @Column (name = "first_name", nullable = false)
    private String firstName;

    @Column (name = "last_name", nullable = false)
    private String lastName;

    @Column (name = "email", nullable = false)
    private String email;

    @Column (name = "phone", nullable = false)
    private String phone;

    @Column (name = "years_of_experience", nullable = false)
    private int yearsOfExperience;



    @ToString.Exclude
    @JsonManagedReference
    @OneToMany(mappedBy = "guide")
    private Set<Trip> trips = new HashSet<>();


    public Guide(int id, String firstName, String lastName, String email, String phone, int yearsOfExperience) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.yearsOfExperience = yearsOfExperience;
    }

    public Guide(String firstName, String lastName, String email, String phone, int yearsOfExperience) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.yearsOfExperience = yearsOfExperience;
    }

//    public Guide(String firstName, String lastName, String email, String phone, int yearsOfExperience, Set<Trip> trips) {
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.email = email;
//        this.phone = phone;
//        this.yearsOfExperience = yearsOfExperience;
//        this.trips = trips;
//    }

    public Guide (GuideDTO guideDTO){
        this.id = guideDTO.getId();
        this.firstName = guideDTO.getFirstName();
        this.lastName = guideDTO.getLastName();
        this.email = guideDTO.getEmail();
        this.phone = guideDTO.getPhone();
        this.yearsOfExperience = guideDTO.getYearsOfExperience();
//        if (guideDTO.getTrips() != null) {
//            for (TripDTO tripDTO : guideDTO.getTrips()) {
//                this.trips.add(new Trip(tripDTO));
//            }
//        }
    }


}
