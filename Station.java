/**
 * Class which rapresents the stations
 * @author Lorenzo Radice
 * @version 0.0.1
 */
public class Station {
    private short line = -1;
    private short stat = -1;
    private String StationName = null;
    private short transshipment_time = 0;
    /**
     * Index of metro line
     */
    private static final short i_line = 0;
    /**
     * Index of metro station
     */
    private static final short i_stat = 1;
    /**
     * Set of metro lines
     */
    private static final class line_num {
        private static final short A = 0;
        private static final short B = 1;
        private static final short C = 2;
        private static final short D = 3;
    }
    /**
     * Set of transshipment
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
     * List of metro stations
     */
    private static final String[][] station = {
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
     */
    private static final short[][] time = {
        { 1, 1, 2, 1, 2, 2, 1, 2, 1, 2, 2, 2, 2 },
        { 2, 1, 2, 2, 2, 2, 2, 1, 3 },
        { 2, 2, 3, 2 },
        { 1, 2, 3, 2, 1, 2, 1, 3, 1, 2, 1, 2, 2, 2 }
    };
    /**
     *  Time of transshipment
    */
    private static final short tras_time = 3;

    public Station( String station_name ) {
        short [] indexes = getStationIndexes(station_name);
        // If there are no indexes do not initialize the station
        if (indexes != null && indexes.length == 2) {
            this.line = indexes[i_line];
            this.stat = indexes[i_stat ];
            this.StationName = station[this.line][this.stat];
            this.transshipment_time = TrasTime(this.StationName);
        } else {
            this.StationName = null;
        }
    }
    /**
     * Build a Station object knowing the line and the station number.
     * @param line line
     * @param stat station
     */
    public Station( short line, short stat ) {
        this.line = line;
        this.stat = stat;
        this.StationName = station[this.line][this.stat];
        this.transshipment_time = TrasTime(this.StationName);
    }
    /**
     * Check if the station is a transshipment one.
     * If it is a transshipment station the method returns the transshipment time.
     * If it is not a transshipment station the method returns 0.
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
     * @param stat station
     * @return short[2] indexes of the station
     */
    private static short[] getStationIndexes( String stat ) {
        for (short i = 0; i < station.length; i++) {
            for (short j = 0; j < station[i].length; j++) {
                if ( stat.equals(station[i][j])) {
                    short [] r = {i, j};
                    return r;
                }
            }
        }
        return null;
    }
    /**
     * Get line
     * @return line
     */
    public short getLine() {
        return line;
    }
    /**
     * Get station
     * @return station
     */
    public short getStat() {
        return stat;
    }
    /**
     * Get time of transshipment
     * @return transshipment_time
     */
    public short getTransshipment_time() {
        return transshipment_time;
    }
    /**
     * Check for station existance
     * @return true if station exist
     */
    public boolean Exist() {
        return this.StationName != null;
    }
    /**
     * Check if is a transshipment station
     * @return true if is a transshipment station
     */
    public boolean isTransshipmentStation() {
        return this.transshipment_time > 0;
    }
    /**
     * Check if an other station is on the same line
     * @param other_stat other station
     * @return true if the station is near
     */
    public boolean sameLine( Station other_stat ) {
        return getLine() == other_stat.getLine();
    }
    public boolean hasSameCoordinates( short o_line, short o_stat ) {
        return ((this.line == o_line) && (this.stat == o_stat));
    }
    /**
     * Calculate the distance between a near station.
     * In case of error return a negative number.
     * @param near_stat near station
     * @return distance
     */
    public short NearStationTime( Station near_stat ) {
        // TODO
        // Check for stations existance
        if ( ! ( near_stat != null && near_stat.Exist() && Exist()) ) {
            // Return error
            return -2;
        }
        short[] coo1 = new short[2];
        short[] coo2 = new short[2];
        short i = 0;
        short time = 0;
        // If the station are not on the same line check if one of them is a transshipment station
        if ( ! sameLine(near_stat) ) {
            short [][] o_coor = null;
            i = 0;
            // Check if the station is a transshipment one
            if ( isTransshipmentStation() ) {
                coo1[i_line] = near_stat.getLine();
                coo1[i_stat] = near_stat.getStat();
                o_coor = otherCoordinates();
                if ( ! hasSameCoordinates(o_coor[i_line][i], o_coor[i_stat][i]) ) {
                    coo2[i_line] = o_coor[i_stat][i];
                    coo2[i_stat] = o_coor[i_stat][i];
                } else {
                    i++;
                    coo2[i_line] = o_coor[i_stat][i];
                    coo2[i_stat] = o_coor[i_stat][i];
                }
            } else if ( near_stat.isTransshipmentStation() ) {
                coo1[i_line] = getLine();
                coo1[i_stat] = getStat();
                o_coor = otherCoordinates();
                if ( ! near_stat.hasSameCoordinates(o_coor[i_line][i], o_coor[i_stat][i]) ) {
                    coo2[i_line] = o_coor[i_stat][i];
                    coo2[i_stat] = o_coor[i_stat][i];
                } else {
                    i++;
                    coo2[i_line] = o_coor[i_stat][i];
                    coo2[i_stat] = o_coor[i_stat][i];
                }
                o_coor = near_stat.otherCoordinates();
            } else {
                // The stations are not near
                return -1;
            }
        }
        return 0;
    }
}
