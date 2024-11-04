package dat.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TripInfoDTO {

    @JsonProperty("LocationName")
    private String locationName;

    @JsonProperty("CurrentData")
    private CurrentDataDTO currentData;



    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CurrentDataDTO {

        private double temperature;

        private String skyText;

        private String humidity;

        private String windText;
    }
}
