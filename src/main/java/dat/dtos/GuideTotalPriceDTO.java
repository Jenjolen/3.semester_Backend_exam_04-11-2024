package dat.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GuideTotalPriceDTO { // Har lavet en ny DTO klasse, som indeholder elementerne fra guideDTO og en totalPrice
    // fordi jeg tænkte det ville blive rodet at skulle indsætte totalPrice i guideDTO.
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private int yearsOfExperience;
    private double totalPrice;

    public GuideTotalPriceDTO(GuideDTO guideDTO, double totalPrice) {
        this.id = guideDTO.getId();
        this.firstName = guideDTO.getFirstName();
        this.lastName = guideDTO.getLastName();
        this.email = guideDTO.getEmail();
        this.phone = guideDTO.getPhone();
        this.yearsOfExperience = guideDTO.getYearsOfExperience();
        this.totalPrice = totalPrice;
    }
}
