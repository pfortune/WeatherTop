package models;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
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

  /**
   * A list of readings for this station.
   * CascadeType.ALL means changes on Station will cascade to readings.
   */
  @OneToMany(cascade = CascadeType.ALL)
  public List<Reading> readings = new ArrayList<Reading>();

  /**
   * Constructor for the Station class.
   *
   * @param name      The name of the station.
   * @param latitude  The latitude of the station.
   * @param longitude The longitude of the station.
   */
  public Station(String name, double latitude, double longitude) {
    this.name = name;
    this.latitude = latitude;
    this.longitude = longitude;
  }

  /**
   * Gets the latest reading for this station.
   *
   * @return The latest Reading object.
   */
  public Reading getLatestReading() {
    return readings.get(readings.size() - 1);
  }

  /**
   * Gets the latest weather code for this station.
   *
   * @return The latest weather code.
   */
  public int getLatestWeatherCode() {
    return getLatestReading().code;
  }

  /**
   * Gets the latest weather description for this station.
   *
   * @return A string representing the latest weather condition.
   */
  public String getLatestWeatherDescription() {
    return Conversion.weatherCodeToCondition(getLatestReading().code);
  }

  /**
   * Gets the latest temperature in Fahrenheit for this station.
   *
   * @return The latest temperature in Fahrenheit.
   */
  public double getLatestTemperatureFahrenheit() {
    return Conversion.celsiusToFahrenheit(getLatestReading().temperature);
  }

  /**
   * Gets the latest temperature in Celsius for this station.
   *
   * @return The latest temperature in Celsius.
   */
  public double getLatestTemperaturecelsius() {
    return getLatestReading().temperature;
  }

  /**
   * Gets the latest wind speed in kmh for this station.
   *
   * @return The latest wind speed in kmh.
   */
  public double getLatestWindSpeed() {
    return getLatestReading().windSpeed;
  }

  /**
   * Gets the latest wind speed in Beaufort scale for this station.
   *
   * @return The latest wind speed in Beaufort scale.
   */
  public int getLatestWindBeaufort() {
    return Conversion.kmhToBeaufort(getLatestReading().windSpeed);
  }

  /**
   * Gets the latest pressure reading for this station.
   *
   * @return The latest pressure reading.
   */
  public int getLatestPressure() {
    return getLatestReading().pressure;
  }

  /**
   * Gets the latest wind direction in compass notation for this station.
   *
   * @return The latest wind direction in compass notation.
   */
  public String getLatestWindDirectionCompass() {
    return Conversion.windDirectionToCompass(getLatestReading().windDirection);
  }

  /**
   * Gets the latest wind chill factor for this station.
   *
   * @return The latest wind chill factor.
   */
  public double getLatestWindChill() {
    return Conversion.calculateWindChill(getLatestReading().temperature, getLatestReading().windSpeed);
  }

  /**
   * Gets the date of the latest reading for this station.
   *
   * @return The date of latest reading.
   */
  public String getLatestDate() {
    return getLatestReading().date.toString();
  }

  /**
   * Gets the pressure trend for this station.
   *
   * @return A string representing the pressure trend.
   */
  public String getPressureTrend() {
    if (readings.size() < 3) {
      return "";
    } else {
      int lastReading = readings.get(readings.size() - 1).pressure;
      int secondLastReading = readings.get(readings.size() - 2).pressure;
      int thirdLastReading = readings.get(readings.size() - 3).pressure;

      if ((lastReading - secondLastReading) > 0 && (secondLastReading - thirdLastReading) > 0) {
        return "Increasing";
      } else if ((lastReading - secondLastReading) < 0 && (secondLastReading - thirdLastReading) < 0) {
        return "Decreasing";
      } else {
        return "Steady";
      }
    }
  }

  /**
   * Gets the temperature trend for this station.
   *
   * @return A string representing the temperature trend.
   */
  public String getTemperatureTrend() {
    if (readings.size() < 3) {
      return "";
    } else {
      double lastReading = readings.get(readings.size() - 1).temperature;
      double secondLastReading = readings.get(readings.size() - 2).temperature;
      double thirdLastReading = readings.get(readings.size() - 3).temperature;

      if ((lastReading - secondLastReading) > 0 && (secondLastReading - thirdLastReading) > 0) {
        return "Increasing";
      } else if ((lastReading - secondLastReading) < 0 && (secondLastReading - thirdLastReading) < 0) {
        return "Decreasing";
      } else {
        return "Steady";
      }
    }
  }

  /**
   * Gets the wind speed trend for this station.
   *
   * @return A string representing the wind speed trend.
   */
  public String getWindTrend() {
    if (readings.size() < 3) {
      return "";
    } else {
      double lastReading = readings.get(readings.size() - 1).windSpeed;
      double secondLastReading = readings.get(readings.size() - 2).windSpeed;
      double thirdLastReading = readings.get(readings.size() - 3).windSpeed;

      if ((lastReading - secondLastReading) > 0 && (secondLastReading - thirdLastReading) > 0) {
        return "Increasing";
      } else if ((lastReading - secondLastReading) < 0 && (secondLastReading - thirdLastReading) < 0) {
        return "Decreasing";
      } else {
        return "Steady";
      }
    }
  }

  /**
   * Gets the maximum temperature recorded at this station.
   *
   * @return The maximum temperature.
   */
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

  /**
   * Gets the minimum temperature recorded at this station.
   *
   * @return The minimum temperature.
   */
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

  /**
   * Gets the maximum wind speed recorded at this station.
   *
   * @return The maximum wind speed.
   */
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

  /**
   * Gets the minimum wind speed recorded at this station.
   *
   * @return The minimum wind speed.
   */
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

  /**
   * Gets the maximum pressure recorded at this station.
   *
   * @return The maximum pressure.
   */
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

  /**
   * Gets the minimum pressure recorded at this station.
   *
   * @return The minimum pressure.
   */
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

  /**
   * Gets the name of this station.
   *
   * @return The station name.
   */
  public String getName() {
    return name;
  }
}
