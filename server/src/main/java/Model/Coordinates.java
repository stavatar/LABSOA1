package Model;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Table;

import javax.persistence.*;

@Entity
@Table(appliesTo = "Coordinates")
public  class Coordinates
{
    public Coordinates(){

    }
    public Coordinates(Double  x, Double  y) {
        this.x = x;
        this.y = y;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name="x")
    private Double  x; //Поле не может быть null
    @Column(name="y")
    private Double  y; //Максимальное значение поля: 369

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Double  getX() {
        return x;
    }

    public void setX(Double  x) {
        this.x = x;
    }

    public Double  getY() {
        return y;
    }

    public void setY(Double  y) {
        this.y = y;
    }
}
