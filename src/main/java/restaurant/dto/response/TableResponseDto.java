package restaurant.dto.response;

public class TableResponseDto {
    private Long id;
    private int seats;
    private boolean reserved;

    public TableResponseDto(Long id, int seats, boolean reserved) {
        this.id = id;
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
}
