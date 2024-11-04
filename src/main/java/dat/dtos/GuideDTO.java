package dat.dtos;

import dat.entities.Guide;
import dat.entities.Trip;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GuideDTO {

    private int id;

    private String firstName;

    private String lastName;

    private String email;

    private String phone;

    private int yearsOfExperience;

    private List<TripDTO> trips = new ArrayList<>();

    public GuideDTO(int id, String firstName, String lastName, String email, String phone, int yearsOfExperience) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.yearsOfExperience = yearsOfExperience;
    }

    public GuideDTO(String firstName, String lastName, String email, String phone, int yearsOfExperience) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.yearsOfExperience = yearsOfExperience;
    }

//    public GuideDTO(String firstName, String lastName, String email, String phone, int yearsOfExperience, List<TripDTO> trips) {
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.email = email;
//        this.phone = phone;
//        this.yearsOfExperience = yearsOfExperience;
//        this.trips = trips;
//    }


    public GuideDTO (Guide guide){
        this.id = guide.getId();
        this.firstName = guide.getFirstName();
        this.lastName = guide.getLastName();
        this.email = guide.getEmail();
        this.phone = guide.getPhone();
        this.yearsOfExperience = guide.getYearsOfExperience();
        if (guide.getTrips() != null) {
            for (Trip trip : guide.getTrips()) {
                this.trips.add(new TripDTO(trip));
            }
        }

    }


}
