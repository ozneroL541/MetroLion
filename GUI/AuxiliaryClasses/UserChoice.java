package GUI.AuxiliaryClasses;

import Alg.Station;

/**
 * The UserChoice class represents the stations selected by the user.
 * It contains the departure and destination stations.
 * @author Lorenzo Radice
 */
public class UserChoice {
    /** Departure Station */
    private Station departureStation;
    /** Destination Station */
    private Station destinationStation;
    /**
     * Constructs a UserChoice object with the specified departure and destination station objects.
     * @param departure station from which depart
     * @param destination station to which travel
     */
    public UserChoice(Station departure, Station destination) {
        this.departureStation = departure;
        this.destinationStation = destination;
    }
    /**
     * Constructs a UserChoice object with the specified departure and destination station names.
     * @param departure name of the station from which depart
     * @param destination name of the station to which travel
     */
    public UserChoice(String departure, String destination) {
        this.departureStation = new Station(departure);
        this.destinationStation = new Station(destination);
    }
    /**
     * Returns the departure station.
     * @return departure station
     */
    public Station getDepartureStation() {
        return this.departureStation;
    }
    /**
     * Returns the destination station.
     * @return destination station
     */
    public Station getDestinationStation() {
        return this.destinationStation;
    }
    /**
     * Returns a string representation of the UserChoice object.
     * @return string representation of the UserChoice object
     */
    @Override
    public String toString() {
        return "UserChoice: from " + this.departureStation.getStationName() + " to " + this.destinationStation.getStationName();
    }
    /**
     * Returns the String name of the departure station.
     * @return name of the departure station
     */
    public String DepartureName() {
        return this.departureStation.getStationName();
    }
    /**
     * Returns the String name of the destination station.
     * @return name of the destination station
     */
    public String DestinationName() {
        return this.destinationStation.getStationName();
    }
}
