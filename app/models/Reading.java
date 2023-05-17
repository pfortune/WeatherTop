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

  public Reading(int code, double temperature, double windSpeed, int pressure, int windDirection, Date date) {
    this.code = code;
    this.temperature = temperature;
    this.windSpeed = windSpeed;
    this.pressure = pressure;
    this.windDirection = windDirection;
    this.date = date;
  }

  public Reading(int code, double temperature, double windSpeed, int pressure, int windDirection) {
    this(code, temperature, windSpeed, pressure, windDirection, new Date());
  }
}
