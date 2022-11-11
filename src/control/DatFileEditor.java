package control;

import entity.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import boundary.*;
public class DatFileEditor implements MainControl {
    @Override
    public void begin() {
        boolean end = true;
        while (end) {
            int option = MenuView.getMenuOption(
                    "Enter your choice: ",
                    "Add Cineplex",
                    "Add Cinemas",
                    "View Cineplex",
                    "Add Movie Time",
                    "Add Ticket Price",
                    "Add Staff",
                    "Delete Cineplex",
                    "Delete Cinemas",
                    "Delete Staff",
                    "Delete Customer",
                    "View Staff",
                    "View Customer",
                    "QUIT");
            Scanner sc = new Scanner(System.in);
            List<Movie> movieList = DatabaseManager.getDataBase().getMovieList();
            List<Cineplex> cineplexList = DatabaseManager.getDataBase().getCineplexList();
            TicketPrice ticketPrice = DatabaseManager.getDataBase().getTicketPrice();

            switch (option) {
                case 1:
                    System.out.println("Add Cineplex");
                    System.out.println("Enter Cineplex Name: ");
                    try{
                        String name = sc.nextLine();
                        Cineplex cineplex = new Cineplex(name);
                        DatabaseManager.getDataBase().getCineplexList().add(cineplex);
                    } catch(Exception e){
                        System.out.println("Invalid input. Please try again.");
                    }
                    break;

                case 2:
                    System.out.println("Select Cineplex:");
                    for (int i = 0; i < cineplexList.size(); i++) {
                        System.out.println((i + 1) + ". " + cineplexList.get(i).getName());
                    }
                    int cineplexIndex = sc.nextInt();
                    sc.nextLine();
                    Cineplex cineplex2 = cineplexList.get(cineplexIndex - 1);
                    System.out.println("Add Cinema");
                    System.out.println("Enter Cinema Code: ");
                    String cinemaCode = sc.nextLine();
                    System.out.println("Enter Cinema Type: ");
                    boolean[][] seat;
                    CinemaClass cinemaClass = MenuView.getItemName("Cinema Code: ", CinemaClass.values());
                    if(cinemaClass == CinemaClass.PLAT_MOVIE_SUITES){
                        seat = new boolean[10][10];
                        for(int i = 0; i < 10; i++){
                            for(int j = 0; j < 10; j++){
                                seat[i][j] = true;
                            }
                        }
                    }
                    else {
                        seat = new boolean[10][21];
                        for (int i = 0; i < 10; i++) {
                            for (int j = 0; j < 21; j++) {
                                if (j == 10) {
                                    seat[i][j] = false;
                                } else {
                                    seat[i][j] = true;
                                }
                            }
                        }
                    }
                    
                    cineplex2.addCinema(cinemaCode, cinemaClass, seat);
                    break;

                case 3:
                    System.out.println("View Cineplex");
                    for (int i = 0; i < cineplexList.size(); i++) {
                        System.out.println((i + 1) + ". " + cineplexList.get(i).getName());
                    }
                    int cineplexIndex2 = sc.nextInt();
                    sc.nextLine();
                    Cineplex cineplex3 = cineplexList.get(cineplexIndex2 - 1);
                    ArrayList<Cinema> cinemaList = cineplex3.getCinemas();
                    for (int i = 0; i < cinemaList.size(); i++) {
                        System.out.println((i + 1) + ". " + cinemaList.get(i).getCinemaCode());
                        boolean[][] seatLayouts = cinemaList.get(i).getSeatLayout();
                        for (int j = 0; j < 10; j++) {
                            for (int k = 0; k < 10; k++) {
                                if (seatLayouts[j][k] == true) {
                                    System.out.print("O ");
                                } else {
                                    System.out.print("X ");
                                }
                            }
                            System.out.println();
                        }
                    }
                    break;

                case 4:
                    System.out.println("Add Movie Time");

                    for (int i = 0; i < cineplexList.size(); i++) {
                        System.out.println((i + 1) + ". " + cineplexList.get(i).getName());
                    }
                    int cineplexIndex3 = sc.nextInt();
                    sc.nextLine();
                    ArrayList<Cinema> cinemaList2 = cineplexList.get(cineplexIndex3 - 1).getCinemas();
                    int cinemaIndex = 0;
                    System.out.println("Select Cinema:");
                    for (int i = 0; i < cinemaList2.size(); i++) {
                        System.out.println((i + 1) + ". " + cinemaList2.get(i).getCinemaCode());
                    }
                    cinemaIndex = sc.nextInt();
                    sc.nextLine();

                    System.out.println("Enter Movie Title: ");
                    Movie movie = MenuView.getItemName("Choose a movie", movieList);
                    MovieView.getMovieView(movie);

                    System.out.println("Enter Movie Date and Time as dd/MM/yyyy HH:mm: ");
                    // LocalDateTime movieDateTime = LocalDateTime.parse(sc.nextLine(),
                    // DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));

                    System.out.println("Enter movie start date and time (dd/MM/yyyy HH:mm): ");
                    DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

                    LocalDateTime movieTime;
                    while (true) {
                        try {
                            movieTime = LocalDateTime.parse(sc.nextLine(), format);
                            break;
                        } catch (DateTimeParseException e) {
                            System.out.println("Invalid date and time format. Please try again.");
                        }
                    }
                    Cinema cinema = cinemaList2.get(cinemaIndex - 1);

                    cinema.addMovieTime(movieTime, movie);
                    break;

                case 5:
                    System.out.println("Adding Ticket Price");
                    System.out.println("Enter price of normal ticket: ");
                    double normalTicketPrice = sc.nextDouble();
                    sc.nextLine();
                    ticketPrice.setNormalPrice(normalTicketPrice);

                    System.out.println("Enter price of Standard ticket: ");
                    double cinemaClassTicketPrice = sc.nextDouble();
                    sc.nextLine();
                    ticketPrice.setCinemaClassPrice(CinemaClass.STANDARD, cinemaClassTicketPrice);

                    System.out.println("Enter price of Platinum ticket: ");
                    cinemaClassTicketPrice = sc.nextDouble();
                    sc.nextLine();
                    ticketPrice.setCinemaClassPrice(CinemaClass.PLAT_MOVIE_SUITES, cinemaClassTicketPrice);

                    System.out.println("Enter price of SENIOR_CITIZEN ticket: ");
                    cinemaClassTicketPrice = sc.nextDouble();
                    sc.nextLine();
                    ticketPrice.setAgePrice(AgeGroup.SENIOR_CITIZEN, cinemaClassTicketPrice);

                    System.out.println("Enter price of ADULT ticket: ");
                    cinemaClassTicketPrice = sc.nextDouble();
                    sc.nextLine();
                    ticketPrice.setAgePrice(AgeGroup.ADULT, cinemaClassTicketPrice);

                    System.out.println("Enter price of CHILD ticket: ");
                    cinemaClassTicketPrice = sc.nextDouble();
                    sc.nextLine();
                    ticketPrice.setAgePrice(AgeGroup.CHILD, cinemaClassTicketPrice);

                    System.out.println("Enter price of 3D ticket: ");
                    cinemaClassTicketPrice = sc.nextDouble();
                    sc.nextLine();
                    ticketPrice.setMovieTypePrice(MovieType._3D, cinemaClassTicketPrice);

                    System.out.println("Enter price of Blockbuster ticket: ");
                    cinemaClassTicketPrice = sc.nextDouble();
                    sc.nextLine();
                    ticketPrice.setMovieTypePrice(MovieType.BLOCKBUSTER, cinemaClassTicketPrice);

                    System.out.println("Enter price of Regular ticket: ");
                    cinemaClassTicketPrice = sc.nextDouble();
                    sc.nextLine();
                    ticketPrice.setMovieTypePrice(MovieType.REGULAR, cinemaClassTicketPrice);

                    System.out.println("Enter price of Weekday ticket: ");
                    cinemaClassTicketPrice = sc.nextDouble();
                    sc.nextLine();
                    ticketPrice.setDatePrice(DateGroup.WEEKDAY, cinemaClassTicketPrice);

                    System.out.println("Enter price of Weekend ticket: ");
                    cinemaClassTicketPrice = sc.nextDouble();
                    sc.nextLine();
                    ticketPrice.setDatePrice(DateGroup.WEEKEND, cinemaClassTicketPrice);

                    System.out.println("Enter price of Holiday ticket: ");
                    cinemaClassTicketPrice = sc.nextDouble();
                    sc.nextLine();
                    ticketPrice.setDatePrice(DateGroup.HOLIDAY, cinemaClassTicketPrice);

                    System.out.println("Enter holiday dates (dd/MM/yyyy), enter empty string to break: ");
                    ArrayList<LocalDate> holidayDates = new ArrayList<LocalDate>();
                    while (true) {
                        String date = sc.nextLine();
                        if (date.equals("")) {
                            break;
                        }
                        try {
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                            LocalDate holidayDate = LocalDate.parse(date, formatter);
                            holidayDates.add(holidayDate);
                        } catch (DateTimeParseException e) {
                            System.out.println("Invalid date format. Please try again.");
                        }
                    }
                    break;

                case 6:
                    System.out.println("Add Staff Details: ");
                    System.out.println("Enter Staff Name: ");
                    String staffName = sc.nextLine();
                    System.out.println("Enter Staff Password: ");
                    String staffPassword = sc.nextLine();

                    CinemaStaff staff = new CinemaStaff(staffName, staffPassword);
                    DatabaseManager.getDataBase().addCinemaStaff(staff);
                    break;

                case 7:
                    System.out.println("Delete Cineplex ");
                    System.out.println("Enter Cineplex Name: ");
                    String cineplexName = sc.nextLine();
                    boolean confirm = false;
                    try {
                        System.out.println("Confirm booking (y/n): ");
                        String input = sc.nextLine();

                        if (input.equals("y")) {
                            confirm = true;
                        } else if (input.equals("n")) {
                            confirm = false;
                        } else {
                            System.out.println("Please enter a valid input");
                        }
                    } catch (Exception e) {
                        System.out.println("Please enter a valid input");
                    }

                    if (confirm == true)
                        DatabaseManager.getDataBase().deleteCineplex(cineplexName);
                    break;

                case 8:
                    System.out.println("Delete Cinema ");
                    for(int i = 0; i < cineplexList.size(); i++){
                        System.out.println((i+1) + ". " + cineplexList.get(i).getName());
                    }
                    cineplexIndex = sc.nextInt();
                    sc.nextLine();
                    Cineplex cineplex = cineplexList.get(cineplexIndex - 1);
                    System.out.println("Enter Cinema Name: ");
                    for(int i = 0; i < cineplex.getCinemas().size(); i++){
                        System.out.println((i+1) + ". " + cineplex.getCinemas().get(i).getCinemaCode());
                    }
                    String cinemaName = sc.nextLine();
                    confirm = false;
                    try {
                        System.out.println("Confirm booking (y/n): ");
                        String input = sc.nextLine();

                        if (input.equals("y")) {
                            confirm = true;
                        } else if (input.equals("n")) {
                            confirm = false;
                        } else {
                            System.out.println("Please enter a valid input");
                        }
                    } catch (Exception e) {
                        System.out.println("Please enter a valid input");
                    }

                    if (confirm == true)
                        DatabaseManager.getDataBase().deleteCinema(cinemaName);
                    break;

                case 9:
                    System.out.println("Delete Staff ");
                    System.out.println("Enter Staff Name: ");
                    String staffNameToBeDeleted = sc.nextLine();
                    confirm = false;
                    try {
                        System.out.println("Confirm booking (y/n): ");
                        String input = sc.nextLine();

                        if (input.equals("y")) {
                            confirm = true;
                        } else if (input.equals("n")) {
                            confirm = false;
                        } else {
                            System.out.println("Please enter a valid input");
                        }
                    } catch (Exception e) {
                        System.out.println("Please enter a valid input");
                    }

                    if (confirm == true)
                        DatabaseManager.getDataBase().deleteStaff(staffNameToBeDeleted);
                    break;

                case 10:
                    System.out.println("Delete Staff ");
                    System.out.println("Enter Staff Name: ");
                    String customerNameToBeDeleted = sc.nextLine();
                    confirm = false;
                    try {
                        System.out.println("Confirm booking (y/n): ");
                        String input = sc.nextLine();

                        if (input.equals("y")) {
                            confirm = true;
                        } else if (input.equals("n")) {
                            confirm = false;
                        } else {
                            System.out.println("Please enter a valid input");
                        }
                    } catch (Exception e) {
                        System.out.println("Please enter a valid input");
                    }

                    if (confirm == true) {
                        try {
                            DatabaseManager.getDataBase().deleteCustomer(customerNameToBeDeleted);
                        } catch (Exception e) {
                            System.out.println("Please enter the correct staff name");
                        }
                    }
                    break;

                case 11:
                    ArrayList<String> staffList = DatabaseManager.getDataBase().getCinemaStaffs();
                    int i = 1;
                    for (String username : staffList) {
                        System.out.println("Staff #" + i + ": " + username);
                        i++;
                    }
                    break;

                case 12:
                    ArrayList<String> customerList = DatabaseManager.getDataBase().getCustomers();
                    i = 1;
                    for (String username : customerList) {
                        System.out.println("Customer #" + i + ": " + username);
                        i++;
                    }
                    break;

                case 13:
                    end = false;
                    break;

            }
        }
    }

    public static void main(String[] args) {
        DatabaseManager.read();

        DatFileEditor controller = new DatFileEditor();
        controller.begin();

        DatabaseManager.write();
    }
}