package Model;

import Model.util.Mood;
import Model.util.WeaponType;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Entity;
import org.hibernate.annotations.Table;

import javax.persistence.*;
import java.time.LocalDate;

@DynamicUpdate
@Table(appliesTo = "HumanBeing")
public class HumanBeing
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    @Column(name = "name")
    private String name; //Поле не может быть null, Строка не может быть пустой
    @OneToOne(cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn (name="coordinates_id")
    private Coordinates coordinates; //Поле не может быть null
    @Column(name = "creationDate")
    private java.time.LocalDate creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    @Column(name = "realHero")
    private boolean realHero;
    @Column(name = "hasToothpick")
    private boolean hasToothpick;
    @Column(name = "impactSpeed")
    private Float impactSpeed; //Поле не может быть nul
    @Column(name = "weaponType")
    @Enumerated(EnumType.STRING)
    private WeaponType weaponType; //Поле не может быть null
    @Column(name = "mood")
    @Enumerated(EnumType.STRING)
    private Mood mood; //Поле может быть null
    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn (name="car_id")
    private Car car; //Поле не может быть null

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
@DynamicUpdate
@Table(appliesTo = "Coordinates")
class Coordinates
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name="x")
    private Integer x; //Поле не может быть null
    @Column(name="y")
    private int y; //Максимальное значение поля: 369

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
@DynamicUpdate
@Table(appliesTo = "Car")
class Car
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name="cool")
    private Boolean cool; //Поле не может быть null

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Boolean getCool() {
        return cool;
    }

    public void setCool(Boolean cool) {
        this.cool = cool;
    }
}