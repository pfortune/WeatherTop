package models;

import javax.persistence.Entity;
import java.util.Date;

import play.db.jpa.Model;

@Entity
public class Reading extends Model {
  public int code;
  public double temperature;
  public double windSpeed;
  public int pressure;
  public int windDirection;
  public Date date;

  /**
   * Primary constructor for Reading.
   * @param code Weather condition code.
   * @param temperature Temperature reading.
   * @param windSpeed Wind speed reading.
   * @param pressure Atmospheric pressure reading.
   * @param windDirection Wind direction reading.
   * @param date Date and time of the reading.
   */
  public Reading(int code, double temperature, double windSpeed, int pressure, int windDirection, Date date) {
    this.code = code;
    this.temperature = temperature;
    this.windSpeed = windSpeed;
    this.pressure = pressure;
    this.windDirection = windDirection;
    this.date = date;
  }

  /**
   * Secondary constructor for Reading, automatically sets the date to the current date and time.
   * @param code Weather condition code.
   * @param temperature Temperature reading.
   * @param windSpeed Wind speed reading.
   * @param pressure Atmospheric pressure reading.
   * @param windDirection Wind direction reading.
   */
  public Reading(int code, double temperature, double windSpeed, int pressure, int windDirection) {
    this(code, temperature, windSpeed, pressure, windDirection, new Date());
  }
}
