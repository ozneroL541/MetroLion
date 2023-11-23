

public class lineas {
    private static final class line_num {
        private static final short A = 0;
        private static final short B = 1;
        private static final short C = 2;
        private static final short D = 3;
    }
    private final static class transshipment {
        private final static String Charpennes = "Charpennes - Charles Hernu";
        private final static String HoteldeVille = "Hôtel de Ville - Louis Pradel";
        private final static String Bellecour = "Bellecour";
        private final static String SaxeGambetta = "Saxe - Gambetta";
    }
    private static final String[][] line = {
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
    private static final short[][] time = {
        {
            1, 1, 2, 1, 2, 2, 1, 2, 1, 2, 2, 2, 2
        },
        {
            2, 1, 2, 2, 2, 2, 2, 1, 3
        },
        {
            2, 2, 3, 2
        },
        {
            1, 2, 3, 2, 1, 2, 1, 3, 1, 2, 1, 2, 2, 2
        }
    };

    private static final short tras_time = 3;

    /**
     * Check if the station is a transshipment one.
     * If it is a transshipment station the method returns the transshipment time.
     * If it is not a transshipment station the method returns 0.
     * @param station station to check
     * @return transshipment time if it'a a transshipment station
     */
    public static short TrasTime( String station) {
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
}