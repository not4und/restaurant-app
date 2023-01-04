package restaurant.model;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@javax.persistence.Table(name = "tables")
public class Table {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int seats;
    private boolean reserved;

    public Table() {
    }

    public Table(int seats, boolean reserved) {
        this.seats = seats;
        this.reserved = reserved;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public boolean isReserved() {
        return reserved;
    }

    public void setReserved(boolean reserved) {
        this.reserved = reserved;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Table table = (Table) o;
        return seats == table.seats
                && reserved == table.reserved
                && Objects.equals(id, table.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, seats, reserved);
    }

    @Override
    public String toString() {
        return "Table{"
                + " id=" + id
                + ", seats=" + seats
                + ", reserved=" + reserved
                + '}';
    }
}
