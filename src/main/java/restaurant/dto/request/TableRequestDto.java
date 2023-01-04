package restaurant.dto.request;

import javax.validation.constraints.Min;

public class TableRequestDto {
    @Min(value = 1)
    private int seats;
    private boolean reserved;

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
}
