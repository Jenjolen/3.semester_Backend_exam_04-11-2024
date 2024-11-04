package dat.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dat.dtos.TripInfoDTO;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class FetchData {

    public static TripInfoDTO fetchCategoryInfo (String category) {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        try {
            // Create an HttpClient instance
            HttpClient client = HttpClient.newHttpClient();

            // Create a request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("https://packingapi.cphbusinessapps.dk/packinglist/" + category))
                    .GET()
                    .build();

            // Send the request and get the response
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Check the status code and print the response
            if (response.statusCode() == 200) {
                TripInfoDTO tripInfoDTO = objectMapper.readValue(response.body(), TripInfoDTO.class);
                return tripInfoDTO;
            } else {
                System.out.println("GET request failed. Status code: " + response.statusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new TripInfoDTO();

    }

}
