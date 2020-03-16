package Utiles;

public class Titulos {
    String nombre, autor, lanzamiento, precio;

    public Titulos(String nombre, String autor, String lanzamiento, String precio) {
        this.nombre = nombre;
        this.autor = autor;
        this.lanzamiento = lanzamiento;
        this.precio = precio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getLanzamiento() {
        return lanzamiento;
    }

    public void setLanzamiento(String lanzamiento) {
        this.lanzamiento = lanzamiento;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }
}
