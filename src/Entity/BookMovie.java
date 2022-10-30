package Entity;

/**
* This is the interface for booking movies
*/
public interface BookMovie {
    /**
    * Gets all seat statuses for the showtime
    * @return 2D array of SeatStatus
    */
    public SeatStatus[][] getAvailableSeats();
    
    /**
    * This method checks the availability of the seats selected by customer
    * @param selectedSeat a 2D boolean array indicating selected seats
    * @return true if all seats are available, otherwise false
    */
    public boolean checkAvailability(boolean[][] selectedSeat);
    
    /**
    * Returns the layout of the cinema of the show time
    * @return the layout of the cinema of the show time
    */
    public boolean[][] getSeatLayout();
    
    /**
    * !!!
    * Edit this comment Eddy
    */
    public boolean checkFull();
}
