import java.util.ArrayList;
import java.util.List;

/**
 * Class which rapresents the stations
 * @author Lorenzo Radice
 * @version 0.0.2
 */
public class Station {
    // Line number
    private short line = -1;
    // Station number
    private short stat = -1;
    // Station name
    private String StationName = null;
    // Time of transshipment
    private short transshipment_time = 0;
    /**
     * Index of metro line
     * @author Lorenzo Radice
     */
    private static final short i_line = 0;
    /**
     * Index of metro station
     * @author Lorenzo Radice
     */
    private static final short i_stat = 1;
    /**
     * Set of metro lines
     * @author Lorenzo Radice
     */
    private static final class line_num {
        private static final short A = 0;
        private static final short B = 1;
        private static final short C = 2;
        private static final short D = 3;
    }
    /**
     * Set of transshipment
     * @author Lorenzo Radice
     */
    private final static class transshipment {
        private final static String Charpennes = "Charpennes - Charles Hernu";
        private final static String HoteldeVille = "Hôtel de Ville - Louis Pradel";
        private final static String Bellecour = "Bellecour";
        private final static String SaxeGambetta = "Saxe - Gambetta";
        private final static short[][] Charpennes_coor = { {line_num.A, line_num.B}, {7, 0} };
        private final static short[][] HoteldeVille_coor = { {line_num.A, line_num.C}, {4, 0} };
        private final static short[][] Bellecour_coor = { {line_num.A, line_num.D}, {2, 4} };
        private final static short[][] SaxeGambetta_coor = { {line_num.B, line_num.D}, {4, 6} };
    }
    /**
     * List of metro stations names
     * @author Lorenzo Radice
     */
    private static final String[][] station_names = {
        {       
            "Perrache",
            "Ampère - Victor Hugo",
            transshipment.Bellecour,
            "Cordeliers",
            transshipment.HoteldeVille,
            "Foch",
            "Masséna",
            transshipment.Charpennes,
            "République - Villeurbanne",
            "Gratte-Ciel",
            "Flachet - Alain Gilles",
            "Cusset",
            "Laurent Bonnevay - Astroballe",
            "Vaulx-en-Velin - La Soie"
        },
        {
            transshipment.Charpennes,
            "Brotteaux",
            "Gare Part-Dieu - Vivier Merle",
            "Place Guichard - Bourse du Travail",
            transshipment.SaxeGambetta,
            "Jean Macé",
            "Place Jean Jaurès",
            "Debourg",
            "Stade de Gerland",
            "Oullins Gare"
        },
        {
            transshipment.HoteldeVille,
            "Croix-Paquet",
            "Croix-Rousse",
            "Hénon",
            "Cuire"
        },
        {
            "Gare de Vaise",
            "Valmy",
            "Gorge de Loup",
            "Vieux Lyon - Cathédrale Saint-Jean",
            transshipment.Bellecour,
            "Guillotière - Gabriel Péri",
            "Saxe - Gambetta",
            "Garibaldi",
            "Sans Souci",
            "Monplaisir - Lumière",
            "Grange Blanche",
            "Laënnec",
            "Mermoz - Pinel",
            "Parilly",
            "Gare de Vénissieux4 Vénissieux station"
        }
    };
    /**
     * Time between metro stations
     * @author Lorenzo Radice
     */
    private static final short[][] time = {
        { 1, 1, 2, 1, 2, 2, 1, 2, 1, 2, 2, 2, 2 },
        { 2, 1, 2, 2, 2, 2, 2, 1, 3 },
        { 2, 2, 3, 2 },
        { 1, 2, 3, 2, 1, 2, 1, 3, 1, 2, 1, 2, 2, 2 }
    };
    /**
     *  Time of transshipment
     * @author Lorenzo Radice
    */
    private static final short tras_time = 3;
    /**
     * Object constructor by station name
     * @author Lorenzo Radice
     * @param station_name name of the station
     */
    public Station( String station_name ) {
        short [] indexes = getStationIndexes(station_name);
        // If there are no indexes do not initialize the station
        if (indexes != null && indexes.length == 2) {
            this.line = indexes[i_line];
            this.stat = indexes[i_stat ];
            this.StationName = station_names[this.line][this.stat];
            this.transshipment_time = TrasTime(this.StationName);
        } else {
            System.err.println("Error Station: Invalid name\nStation not created.");
            this.StationName = null;
        }
    }
    /**
     * Build a Station object knowing the line and the station number.
     * @author Lorenzo Radice
     * @param line line
     * @param stat station
     */
    public Station( short line, short stat ) {
        if (validCoordinates(line, stat)) {
            this.line = line;
            this.stat = stat;
            this.StationName = station_names[this.line][this.stat];
            this.transshipment_time = TrasTime(this.StationName);
        } else {
            this.line = -1;
            this.stat = -1;
            System.err.println("Error Station: Not valid coordinates\nStation not created.");
        }
    }
    @Override
    public String toString() {
        String str = "";
        str += "Name:\t\t\t" + this.StationName + "\n";
        str += "Line:\t\t\t" + (this.line + 1) + "\n";
        str += "Station:\t\t" + (this.stat + 1) + "\n";
        str += "Transshipment:\t\t";
        if (isTransshipmentStation()) {
            str += "Yes\n";
            str += "Transshipment Time:\t" + this.transshipment_time + " min\n";
        } else {
            str += "No\n";
        }
        return str;
    }
    /**
     * Check if coordinates are valid
     * @param line line
     * @param stat station
     * @return true if the coordinates are valid
     */
    public static boolean validCoordinates( short line, short stat ) {
        boolean b = false;
        b = line >= 0 && stat >= 0;
        b = b && line < station_names.length && stat < station_names[line].length;
        return b;
    }
    /**
     * Check if the station is a transshipment one.
     * If it is a transshipment station the method returns the transshipment time.
     * If it is not a transshipment station the method returns 0.
     * @author Lorenzo Radice
     * @param station station to check
     * @return transshipment time if it'a a transshipment station
     */
    private static short TrasTime( String station) {
        switch (station) {
            case transshipment.Charpennes:
            case transshipment.HoteldeVille:
            case transshipment.Bellecour:
            case transshipment.SaxeGambetta:
                return tras_time;
            default:
                return 0;
        }
    }
    /**
     * Return the two possible coordinates which the transshipment station can have.
     * @author Lorenzo Radice
     * @return coordinates
     */
    public short[][] otherCoordinates() {
        if ( ! isTransshipmentStation() ) {
            return null;
        } else {
            switch (this.StationName) {
                case transshipment.Charpennes:
                    return transshipment.Charpennes_coor;
                case transshipment.HoteldeVille:
                    return transshipment.HoteldeVille_coor;
                case transshipment.Bellecour:
                    return transshipment.Bellecour_coor;
                case transshipment.SaxeGambetta:
                    return transshipment.SaxeGambetta_coor;
                default:
                    return null;
            }
        }
    }
    /**
     * This method returns an array of two shorts.
     * First number is the index of the line.
     * Second number is the index of the station.
     * @author Lorenzo Radice
     * @param stat station
     * @return short[2] indexes of the station
     */
    private static short[] getStationIndexes( String stat ) {
        for (short i = 0; i < station_names.length; i++) {
            for (short j = 0; j < station_names[i].length; j++) {
                if ( stat.equals(station_names[i][j])) {
                    short [] r = {i, j};
                    return r;
                }
            }
        }
        return null;
    }
    /**
     * Get Station Name
     * @author Lorenzo Radice
     * @return StationName
     */
    public String getStationName() {
        return StationName;
    }
    /**
     * Get line
     * @author Lorenzo Radice
     * @return line
     */
    public short getLine() {
        return line;
    }
    /**
     * Get station
     * @author Lorenzo Radice
     * @return station
     */
    public short getStat() {
        return stat;
    }
    /**
     * Get time of transshipment
     * @author Lorenzo Radice
     * @return transshipment_time
     */
    public short getTransshipment_time() {
        return transshipment_time;
    }
    /**
     * Check for station existance
     * @author Lorenzo Radice
     * @return true if station exist
     */
    public boolean Exist() {
        return this.StationName != null;
    }
    /**
     * Check if is a transshipment station
     * @author Lorenzo Radice
     * @return true if is a transshipment station
     */
    public boolean isTransshipmentStation() {
        return this.transshipment_time > 0;
    }
    /**
     * Check if an other station is on the same line
     * @author Lorenzo Radice
     * @param other_stat other station
     * @return true if the station is near
     */
    public boolean sameLine( Station other_stat ) {
        return getLine() == other_stat.getLine();
    }
    /**
     * Check if the station has the coordinates of the arguments
     * @author Lorenzo Radice
     * @param o_line line
     * @param o_stat station
     * @return true if the station has got the coordinates of the arguments
     */
    public boolean hasSameCoordinates( short o_line, short o_stat ) {
        return ((this.line == o_line) && (this.stat == o_stat));
    }
    /**
     * Calculate the distance between a near station.
     * In case of error return a negative number.
     * @author Lorenzo Radice
     * @param near_stat near station
     * @return distance
     */
    public short NearStationTime( Station near_stat ) {
        // Check for stations existance
        if ( ! ( near_stat != null && near_stat.Exist() && Exist()) ) {
            System.err.println("Error NearStationTime: Station does not exist.");
            // Return error
            return -2;
        }
        // Coordinates 1
        short[] coo1 = new short[2];
        // Coordinates 1
        short[] coo2 = new short[2];
        // Index
        short i = 0;
        // TOBE returned
        short return_time = -1;
        // minimum station
        int min_stat = -1;
        // If the station are not on the same line check if one of them is a transshipment station
        if ( ! sameLine(near_stat) ) {
            // Other Coordinates
            short [][] o_coor = null;
            // Set index
            i = 0;
            // Check if current the station is a transshipment one
            if ( isTransshipmentStation() ) {
                // Assign the coordinates of the near station to coo1
                coo1[i_line] = near_stat.getLine();
                coo1[i_stat] = near_stat.getStat();
                // Get other coordinates for transshipment station
                o_coor = otherCoordinates();
                // Use the alternative coordinates
                if ( ! hasSameCoordinates(o_coor[i_line][i], o_coor[i_stat][i]) ) {
                    // If the current coordinates are different from i coordinates use them
                    coo2[i_line] = o_coor[i_line][i];
                    coo2[i_stat] = o_coor[i_stat][i];
                } else {
                    // Increment i to get different coordinates
                    i++;
                    coo2[i_line] = o_coor[i_line][i];
                    coo2[i_stat] = o_coor[i_stat][i];
                }
            // Check if near the station is a transshipment one
            } else if ( near_stat.isTransshipmentStation() ) {
                // Assign the coordinates of the current station to coo1
                coo1[i_line] = getLine();
                coo1[i_stat] = getStat();
                // Get other coordinates for transshipment station
                o_coor = near_stat.otherCoordinates();
                // Use the alternative coordinates
                if ( ! near_stat.hasSameCoordinates(o_coor[i_line][i], o_coor[i_stat][i]) ) {
                    // If the current coordinates are different from i coordinates use them
                    coo2[i_line] = o_coor[i_line][i];
                    coo2[i_stat] = o_coor[i_stat][i];
                } else {
                    // Increment i to get different coordinates
                    i++;
                    coo2[i_line] = o_coor[i_line][i];
                    coo2[i_stat] = o_coor[i_stat][i];
                }
            } else {
                // The stations are not near -> Error
                System.err.println(" Error NearStationTime: Time between no near station is not computable.");
                // The stations are not near
                return -1;
            }
        } else {
            // No station is a transshipment one so assign the current coordinates
            coo1[i_line] = getLine();
            coo2[i_line] = near_stat.getLine();
            coo1[i_stat] = getStat();
            coo2[i_stat] = near_stat.getStat();
        }
        // Check if the lines are the same
        if (coo1[i_line] == coo2[i_line]) {
            // Calculate the minimum between the two stations
            min_stat = Math.min(coo1[i_stat], coo2[i_stat]);
            // Manage Exceptions
            try {
            // Return time is the time of the minimum station
            return_time = time[coo1[i_line]][min_stat];   
            } catch (IndexOutOfBoundsException e) {
                // Error
                System.err.println("Error: time not found.");
                return -2;
            } catch ( Exception e ) {
                System.err.println("Error: time calculation failed.");
                return -3;
            }
        } else {
            // Error
            System.err.println("Error NearStationTime: Stations not on the same line.");
            return -2;
        }
        // Return time
        return return_time;
    }
    /**
     * Calc the time of the path throw the stations.
     * @author Lorenzo Radice
     * @param stations stations
     * @return sum
     */
    public static short timePath( Station[] stations ) {
        short sum = 0;
        boolean transshipment = false;
        // For each station
        for (short i = 0; i < stations.length - 1; i++) {
            // If preavious station was a transshipment one check
            if (transshipment) {
                /* If the station before and the station after the transshipment one
                 * are not on the same line add the transshipment time.
                */
                if (! stations[i+1].sameLine(stations[i-1])) {
                    // Add the transshipment time
                    sum += stations[i].getTransshipment_time();
                }
            }
            if (stations[i].NearStationTime(stations[i+1]) < 0) {
                System.err.println("Error timePath: Two stations are not near.");
            }
            // Add the time to reach the next station
            sum += stations[i].NearStationTime(stations[i+1]);
            // If the next station is a transshipment one keep in mind
            transshipment = stations[i+1].isTransshipmentStation();
        }
        // Return the time
        return sum;
    }
    /**
     * Get all the near stations
     * @author Lorenzo Radice
     * @return array of stations
     */
    public Station[] getNearStations() {
        // TOBE returned
        Station[] stations = null;
        // Arraylist of stations
        ArrayList<Station> stats = new ArrayList<Station>();
        // Index
        short i = 0;
        // Line
        short line = 0;
        // Station
        short stat = 0;
        // Set line
        line = this.line;
        // Set Station
        stat = (short) (this.stat - 1);
        // Check existance
        if (validCoordinates(line, stat)) {
            stats.add(new Station(line, stat));
        }
        // Set Station
        stat = (short) (this.stat + 1);
        // Check existance
        if (validCoordinates(line, stat)) {
            stats.add(new Station(line, stat));
        }
        // Check if the station is a transshipment one
        if (isTransshipmentStation()) {
            // Get other coordinates
            short[][] o_coor = otherCoordinates();
            // Choose the others
            if (hasSameCoordinates(o_coor[i_line][i], o_coor[i_stat][i])) {
                // Increment if the current coordinates are already been used
                i++;
            }
            // Set Line
            line = o_coor[i_line][i];
            // Set Station
            stat = (short) (o_coor[i_stat][i] - 1);
            // Check existance
            if (validCoordinates(line, stat)) {
                stats.add(new Station(line, stat));
            }
            // Set Station
            stat = (short) (o_coor[i_stat][i] + 1);
            // Check existance
            if (validCoordinates(line, stat)) {
                stats.add(new Station(line, stat));
            }
        }
        // Get an array of stations
        stations = stats.toArray( new Station[0] );
        // Return the array
        return stations;
    }
    /**
     * Check if is the same station
     * @author Lorenzo Radice
     * @param o other station
     * @return true if is the same station
     */
    public boolean equals( Station o ) {
        return this.StationName.equals(o.getStationName());
    }
    /**
     * Return the next stations.
     * If the station is not a transshipment one the result is only one next station.
     * @author Lorenzo Radice
     * @param preavious preavious stations
     * @return next stations
     */
    public Station[] getNextStations( Station previous ) {
        // Near Stations
        Station[] near = getNearStations();
        // Return this
        Station[] next = null;
        // Indexes
        short i = 0, j = 0;
        // Check
        if (near == null) {
            return null;
        }
        // Assign next station 
        next = new Station[near.length -1];
        // Find the preavious
        for ( i = 0; i < near.length; i++) {
            // If near station is the previous one discard it
            if ( ! near[i].equals(previous) ) {
                // If j index is out of bound 
                if ( j >= next.length ) {
                    // Error
                    System.err.println("Error: The previous station is not near to the current one.");
                    return null;
                }
                // Assign and increment j index
                next[j++] = near[i];
            }
        }
        // Return next stations
        return next;
    }

public static String getStationName(int i, int j) {
    	return station_names[i][j];
    }
	
	
    public static List<String> camino(Station origen, Station destino) {

    	if(origen.equals(destino))return null;
    	List<String> camino = new ArrayList<>();
		int cont = 0;
		
		if (origen.getLine()==destino.getLine()){
			
			int x = origen.getLine();
			int y = origen.getCol(origen);
			int z = destino.getCol(destino);
			if (y<z) {
				for(int i=y;i<=z;i++) {
					camino.add(cont, getStationName(x,i));
					cont++;
				}// for
			}
			else {
				for(int i=y;i>=z;i--) {
					camino.add(cont, getStationName(x,i));
					cont++;
				}// for
			}// else
		}// if (misma linea)
		
		else {
			camino = null;
		}// else (distinta linea)

		return camino;
	}// camino
    
    public static int coste(List<String> camino){ // devuelve el coste de un camino
    	int coste = 0;
    	int trasbordos = 0;
    	for(int i=0;i<camino.size();i++) {
    		int x = StringAStation(camino.get(i)).get(0);// linea
    		int y = StringAStation(camino.get(i)).get(1);// estación
    		coste += time[x][y];
    	}
    	return coste + (trasbordos * tras_time);
    }
    
    public static List<Integer> StringAStation(String nombre) {// devuelve una lista [i,j]con las coordenadas de la estación a partir de su nombre
    	List<Integer> coordenadas = new ArrayList<>();
    	
    	for(int i=0;i<4;i++) {
    		for(int j=0; j<station_names[i].length;j++) {
    			if (station_names[i][j] == nombre) {
    				coordenadas.add(i);
    				coordenadas.add(j);
    				return coordenadas;
    			}
    		}
    	}
    	return null;// si no existe estación con ese nombre, devuelve null
    }
    
    // TODO Remove test main
    public static void main(String[] args) {
        Station a = new Station("Brotteaux");
        Station b = new Station("Charpennes - Charles Hernu");
        Station[] c = {
            new Station("Brotteaux"),
            new Station("Gare Part-Dieu - Vivier Merle"),
            new Station("Place Guichard - Bourse du Travail"),
        };
        
        System.out.println(a.getStationName());
        System.out.println(b.getStationName());
        System.out.println();
        System.out.println(a.NearStationTime(b));
        System.out.println("Near");
        for (Station s : a.getNearStations()) {
            System.out.println(s);
        }
        System.out.println("Next");
        for (Station s : b.getNextStations(a)) {
            System.out.println(s);
        }
        System.out.println("Path");
        System.out.println( Station.timePath(c) );
    }
}
