package GFX;

import java.util.HashMap;
import java.util.Map;

public class EstacionData {
    private final double x;
    private final double y;
    private final HashMap<String, Integer> conexiones;

    public EstacionData(double x, double y) {
        this.x = x;
        this.y = y;
        this.conexiones = new HashMap<>();
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void addConexion(String estacionVecina, int peso) {
        conexiones.put(estacionVecina, peso);
    }

    public Map<String, Integer> getPesosConexiones() {
        return conexiones;
    }

    public int getPesoHacia(String estacionVecina) {
        return conexiones.get(estacionVecina);
    }
}

