package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import play.data.validation.Required;

import play.db.jpa.Model;
import utils.Conversion;

@Entity
public class Station extends Model {
    @Required
    public String name;
    @Required
    public double latitude;
    @Required
    public double longitude;

    @ManyToOne
    public User user;
    @OneToMany(cascade = CascadeType.ALL)
    public List<Reading> readings = new ArrayList<Reading>();

    public Station(String name, double latitude, double longitude, User user) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.user = user;
    }

    public Station() {

    }

    public Reading getLatestReading() {
        return readings.get(readings.size() - 1);
    }

    public String getLatestWeatherDescription() {
        return Conversion.weatherCodeToCondition(getLatestReading().code);
    }

    public double getLatestTemperatureFahrenheit() {
        return Conversion.celsiusToFahrenheit(getLatestReading().temperature);
    }

    public double getLatestTemperatureCelcius() {
        return getLatestReading().temperature;
    }

    public int getLatestWindBeaufort() {
        return Conversion.kmhToBeaufort(getLatestReading().windSpeed);
    }

    public int getLatestPressure() {
        return getLatestReading().pressure;
    }

    public String getLatestWindDirectionCompass() {
        return Conversion.windDirectionToCompass(getLatestReading().windDirection);
    }

    public double getLatestWindChill() {
        return Conversion.calculateWindChill(getLatestReading().temperature, getLatestReading().windSpeed);
    }

}
