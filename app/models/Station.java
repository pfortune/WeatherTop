package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import play.db.jpa.Model;
import utils.Conversion;

@Entity
public class Station extends Model {
    public String name;
    public double latitude;
    public double longitude;
    @OneToMany(cascade = CascadeType.ALL)
    public List<Reading> readings = new ArrayList<Reading>();

    public Station(String name, double latitude, double longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
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
