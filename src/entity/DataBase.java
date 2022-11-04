package entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.io.Serializable;

/**
 * This class contains all the data for MOBLIMA
 */
public class DataBase implements Serializable{
	/**
	 * The serialisation version number
	 */
	private static final long serialVersionUID = -9165821480078981092L;
	
	/**
	 * Cinema staff list, username of the cinema staff is used as the key
	 */
	private HashMap<String, CinemaStaff> cinemaStaffList = new HashMap<String, CinemaStaff>();
	
	/**
	 * Customer list, username of the Customer is used as the key
	 */
	private HashMap<String, Customer> customerList = new HashMap<String, Customer>();

	/**
	 * Ticket Price for the Movie
	 */
	private TicketPrice ticketPrice = new TicketPrice();

	/**
	 * List of Cineplexes
	 */
	private ArrayList<Cineplex> cineplexList = new ArrayList<Cineplex>();

	/**
	 * List of Movies
	 */
	private ArrayList<Movie> movieList = new ArrayList<Movie>();

	/**
	 * Check if Staff username exists in DataBase
	 * @param username check if username belongs to a Cinema Staff
	 * @return true if Cinema Staff username exists, else false
	 */
	public boolean checkStaffUsername(String username) {
		return cinemaStaffList.containsKey(username);
	}

	/**
	 * Check if Staff password is correct and returns the {@code CinemaStaff} object if valid
	 * @param username the username input by the Cinema Staff
	 * @param password the password input by the Cinema Staff
	 * @return the {@code CinemaStaff} object. Null if the username or password is invalid
	 */
	public CinemaStaff getCinemaStaff(String username, String password) {
		CinemaStaff cinemaStaff = cinemaStaffList.get(username);

		if (cinemaStaff != null && cinemaStaff.login(password))
			return cinemaStaff;
		else
			return null;
	}

	/**
	 * Add new CinemaStaff to DataBase
	 * @param cinemaStaff the new Cinema Staff to be added to DataBase
	 * @return true if new Cinema Staff was added, else false
	 */
	public boolean addCinemaStaff(CinemaStaff cinemaStaff) {
		String username = cinemaStaff.getUsername();

		if (cinemaStaffList.containsKey(username)) {
			return false;

		} else {
			cinemaStaffList.put(username, cinemaStaff);
			return true;
		}
	}

	/**
	 * Check if Customer username exists
	 * @param username the username of the Customer being checked
	 * @return true if username exists in DataBase, else false
	 */
	public boolean checkCustomerUsername(String username) {
		return customerList.containsKey(username);
	}

	/**
	 * Returns the {@code Customer} object if the password is correct
	 * @param username the username input by the Customer
	 * @param password the password input by the Customer
	 * @return the {@code Customer} object. Null if the username or password is invalid
	 */ 
	public Customer getCustomer(String username, String password) {
		Customer customer = customerList.get(username);

		if (customer != null && customer.login(password))
			return customer;
		else
			return null;
	}

	/**
	 * Add new Customer to DataBase
	 * @param customer the new Customer to be added to DataBase
	 * @return true if new Customer was added, else false
	 */
	public boolean addCustomer(Customer customer) {
		String username = customer.getUsername();

		if (customerList.containsKey(username)) {
			return false;

		} else {
			customerList.put(username, customer);
			return true;
		}
	}

	/**
	 * This function returns the Ticket Price of the Movie
	 * @return Ticket Price of the Movie
	 */
	public TicketPrice getTicketPrice() {
		return ticketPrice;
	}

	/**
	 * This function returns the list of Cineplexes
	 * @return Cinplex List
	 */
	public ArrayList<Cineplex> getCineplexList() {
		return cineplexList;
	}

	/**
	 * This function returns the list of Movies
	 * @return Movie List
	 */
	public ArrayList<Movie> getMovieList() {
		return movieList;
	}
}