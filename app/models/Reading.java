package models;

import javax.persistence.Entity;
import java.util.Date;
import play.db.jpa.Model;

@Entity
public class Reading extends Model
{
    public int code;
    public double temperature;
    public double windSpeed;
    public int pressure;
    public int windDirection;
    public Date date;

    public Reading(int code, double temperature, double windSpeed, int pressure, int windDirection)
    {
        this.windSpeed = windSpeed;
        this.code = code;
        this.temperature = temperature;
        this.pressure = pressure;
        this.windDirection = windDirection;
        this.date = new Date();
    }
}