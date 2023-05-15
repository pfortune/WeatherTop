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

    public double getMaxTemperature() {
        if (readings.size() > 0) {
            double maxTemp = readings.get(0).temperature;
            for (Reading currentReading : readings) {
                if (currentReading.temperature > maxTemp) {
                    maxTemp = currentReading.temperature;
                }
            }
            return maxTemp;
        } else {
            return 0;
        }
    }

    public double getMinTemperature() {
        if (readings.size() > 0) {
            double minTemp = readings.get(0).temperature;
            for (Reading currentReading : readings) {
                if (currentReading.temperature < minTemp) {
                    minTemp = currentReading.temperature;
                }
            }
            return minTemp;
        } else {
            return 0;
        }
    }

    public double getMaxWindSpeed() {
        if (readings.size() > 0) {
            double maxWind = readings.get(0).windSpeed;
            for (Reading currentReading : readings) {
                if (currentReading.windSpeed > maxWind) {
                    maxWind = currentReading.windSpeed;
                }
            }
            return maxWind;
        } else {
            return 0;
        }
    }

    public double getMinWindSpeed() {
        if (readings.size() > 0) {
            double minWind = readings.get(0).windSpeed;
            for (Reading currentReading : readings) {
                if (currentReading.windSpeed < minWind) {
                    minWind = currentReading.windSpeed;
                }
            }
            return minWind;
        } else {
            return 0;
        }
    }

    public double getMaxPressure() {
        if (readings.size() > 0) {
            double maxPressure = readings.get(0).pressure;
            for (Reading currentReading : readings) {
                if (currentReading.pressure > maxPressure) {
                    maxPressure = currentReading.pressure;
                }
            }
            return maxPressure;
        } else {
            return 0;
        }
    }

    public double getMinPressure() {
        if (readings.size() > 0) {
            double minPressure = readings.get(0).pressure;
            for (Reading currentReading : readings) {
                if (currentReading.pressure < minPressure) {
                    minPressure = currentReading.pressure;
                }
            }
            return minPressure;
        } else {
            return 0;
        }
    }


}
