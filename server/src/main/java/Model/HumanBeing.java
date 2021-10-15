package Model;

import Model.util.Mood;
import Model.util.WeaponType;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Table;

import javax.persistence.*;
import java.time.LocalDate;


@javax.persistence.Entity(name = "HumanBeing")
@Table(appliesTo = "HumanBeing")
public class HumanBeing
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "name")
    private String name;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn (name="coordinates_id")
    private Coordinates coordinates;
    @Column(name = "creationDate")
    private java.time.LocalDate creationDate;
    @Column(name = "realHero")
    private boolean realHero;
    @Column(name = "hasToothpick")
    private boolean hasToothpick;
    @Column(name = "impactSpeed")
    private Float impactSpeed;
    @Column(name = "weaponType")
    @Enumerated(EnumType.STRING)
    private WeaponType weaponType;
    @Column(name = "mood")
    @Enumerated(EnumType.STRING)
    private Mood mood;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn (name="car_id")
    private Car car;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public boolean isRealHero() {
        return realHero;
    }

    public void setRealHero(boolean realHero) {
        this.realHero = realHero;
    }

    public boolean isHasToothpick() {
        return hasToothpick;
    }

    public void setHasToothpick(boolean hasToothpick) {
        this.hasToothpick = hasToothpick;
    }

    public Float getImpactSpeed() {
        return impactSpeed;
    }

    public void setImpactSpeed(Float impactSpeed) {
        this.impactSpeed = impactSpeed;
    }

    public WeaponType getWeaponType() {
        return weaponType;
    }

    public void setWeaponType(WeaponType weaponType) {
        this.weaponType = weaponType;
    }

    public Mood getMood() {
        return mood;
    }

    public void setMood(Mood mood) {
        this.mood = mood;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }
}


