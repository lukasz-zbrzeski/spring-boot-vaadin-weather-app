package com.example.springbootvaadinweatherapp.gui;

import com.example.springbootvaadinweatherapp.model.WeatherApi;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@Route("weather")
public class WeatherGui extends VerticalLayout {
    private String city;

    private ResponseEntity<WeatherApi> connectToApi() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<WeatherApi> exchange = restTemplate.exchange(
                "http://api.openweathermap.org/data/2.5/weather?q=" + city + "&units=metric&APPID=9f1c9d5001a81c74d30f19a75218c891",
                HttpMethod.GET,
                HttpEntity.EMPTY,
                com.example.springbootvaadinweatherapp.model.WeatherApi.class
        );

        return exchange;
    }

    public WeatherGui() {
        TextField textFieldSetCity = new TextField("Enter a city:");
        Button buttonCheckWeather = new Button("Check weather!");

        Label labelCityAndCountry = new Label();
        Label labelTemperature = new Label();
        Label labelPressure = new Label();
        Label labelWind = new Label();
        Label labelWindSpeed = new Label();
        Label labelWindDegrees = new Label();
        Label labelLocation = new Label();
        Label labelLocationLongitude = new Label();
        Label labelLocationLatitude = new Label();

        buttonCheckWeather.addClickListener(clickEvent -> {
            this.city = textFieldSetCity.getValue();

            labelCityAndCountry.setText(connectToApi().getBody().getName() + ", " + connectToApi().getBody().getSys().getCountry());
            labelTemperature.setText("Temperature: " + connectToApi().getBody().getMain().getTemp().intValue() + "°C");
            labelPressure.setText("Pressure: " + connectToApi().getBody().getMain().getPressure() + " hPa");
            labelWind.setText("Wind:");
            labelWindSpeed.setText("Speed: " + connectToApi().getBody().getWind().getSpeed() + " m/s");
            labelWindDegrees.setText("Degrees: " + connectToApi().getBody().getWind().getDeg() + "°");
            labelLocation.setText("Location of city:");
            labelLocationLongitude.setText("Longitude: " + connectToApi().getBody().getCoord().getLon());
            labelLocationLatitude.setText("Latitude: " + connectToApi().getBody().getCoord().getLat());

            textFieldSetCity.setValue("");
        });

        buttonCheckWeather.addClickShortcut(Key.ENTER);

        add(
                textFieldSetCity,
                buttonCheckWeather,
                labelCityAndCountry,
                labelTemperature,
                labelPressure,
                labelWind,
                labelWindSpeed,
                labelWindDegrees,
                labelLocation,
                labelLocationLongitude,
                labelLocationLatitude
        );
    }
}
