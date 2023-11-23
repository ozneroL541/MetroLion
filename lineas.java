

public class lineas {
    private static final class line_num {
        private static final short A = 0;
        private static final short B = 1;
        private static final short C = 2;
        private static final short D = 3;
    }
    private static final String[][] linea = {
        {       
            "Perrache",
            "Ampère - Victor Hugo",
            "Bellecour",
            "Cordeliers",
            "Hôtel de Ville - Louis Pradel",
            "Foch",
            "Masséna",
            "Charpennes - Charles Hernu",
            "République - Villeurbanne",
            "Gratte-Ciel",
            "Flachet - Alain Gilles",
            "Cusset",
            "Laurent Bonnevay - Astroballe",
            "Vaulx-en-Velin - La Soie"
        },
        {
            "Charpennes - Charles Hernu",
            "Brotteaux",
            "Gare Part-Dieu - Vivier Merle",
            "Place Guichard - Bourse du Travail",
            "Saxe - Gambetta",
            "Jean Macé",
            "Place Jean Jaurès",
            "Debourg",
            "Stade de Gerland",
            "Oullins Gare"
        },
        {
            "Hôtel de Ville - Louis Pradel",
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
            "Bellecour",
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
    private final static class tras {
        private final static short[][] Charpennes = { {line_num.A, line_num.B}, {7, 0} };
        private final static short[][] HoteldeVille = { {line_num.A, line_num.C}, {4, 0} };
        private final static short[][] Bellecour = { {line_num.A, line_num.D}, {2, 4} };
        private final static short[][] SaxeGambetta = { {line_num.B, line_num.D}, {4, 6} };
    }
    private static final short tras_time = 3;
    
    public static short TrasTime( String station, short linea ) {
        switch (station) {
            case "Cordeliers":
            case "Hôtel de Ville - Louis Pradel":
            case "Bellecour":
            case "Saxe - Gambetta":
                return tras_time;
            default:
                return 0;
        }
    }
}