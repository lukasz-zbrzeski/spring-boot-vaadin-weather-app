package com.example.springbootvaadinweatherapp.gui;

import com.example.springbootvaadinweatherapp.model.WeatherApi;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@Route("weather")
@StyleSheet("/style.css")
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
        Image weatherIcon = new Image();
        Label labelTemperature = new Label();
        Label labelPressure = new Label();
        Label labelWind = new Label();
        Label labelWindSpeed = new Label();
        Label labelWindDegrees = new Label();
        Label labelLocation = new Label();
        Label labelLocationLongitude = new Label();
        Label labelLocationLatitude = new Label();

        buttonCheckWeather.addClickShortcut(Key.ENTER);

        textFieldSetCity.getClassNames().add("textFieldSetCity");
        buttonCheckWeather.getClassNames().add("buttonCheckWeather");

        buttonCheckWeather.addClickListener(clickEvent -> {
            this.city = textFieldSetCity.getValue();

            labelCityAndCountry.setText(connectToApi().getBody().getName() + ", " + connectToApi().getBody().getSys().getCountry());
            weatherIcon.setSrc("http://openweathermap.org/img/wn/" + connectToApi().getBody().getWeather().get(0).getIcon() + "@2x.png");
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

        add(
                textFieldSetCity,
                buttonCheckWeather,
                labelCityAndCountry,
                weatherIcon,
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
