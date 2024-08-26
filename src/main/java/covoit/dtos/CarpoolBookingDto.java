package covoit.dtos;

public class CarpoolBookingDto {
    private int userId;
    private int carpoolId;

    // Getters and Setters

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCarpoolId() {
        return carpoolId;
    }

    public void setCarpoolId(int carpoolId) {
        this.carpoolId = carpoolId;
    }
}
