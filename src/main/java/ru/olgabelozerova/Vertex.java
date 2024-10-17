package ru.olgabelozerova;

public class Vertex {

    int id;
    double x, y, z;

    public Vertex(int id, double x, double y, double z) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public String toString() {
        return String.format("Vertex %d: (%.2f, %.2f, %.2f)", id, x, y, z);
    }
}
