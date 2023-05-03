package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import play.db.jpa.Model;

@Entity
public class Station extends Model {
    public String name;
    @OneToMany(cascade = CascadeType.ALL)
    public List<Reading> readings = new ArrayList<Reading>();

    @Transient
    public String latestWeatherDescription;
    @Transient
    public double latestTemperatureFahrenheit;
    @Transient
    public String latestWindBeaufort;

    public Station(String name) {
        this.name = name;
    }

    public Reading getLatestReading() {
        return readings.get(readings.size() - 1);
    }
}