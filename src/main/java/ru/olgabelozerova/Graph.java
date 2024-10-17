package ru.olgabelozerova;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph {
    // Класс для представления вершины графа

    public Map<Integer, Vertex> getVertices() {
        return vertices;
    }

    public Map<Integer, List<Integer>> getAdjacencyList() {
        return adjacencyList;
    }

    // Карта для хранения списка вершин графа по их ID
    private  Map<Integer, Vertex> vertices;
    // Карта для хранения списка смежности (рёбра графа)
    private  Map<Integer, List<Integer>> adjacencyList;

    public Graph() {
        this.vertices = new HashMap<>();
        this.adjacencyList = new HashMap<>();
    }

    // Конструктор по списку вершин и рёбер
    public Graph(Map<Integer, Vertex> vertices, Map<Integer, List<Integer>> adjacencyList) {
        this.vertices = vertices;
        this.adjacencyList = adjacencyList;
    }

    // Метод добавления вершины
    public void addVertex(int id, double x, double y, double z) {
        Vertex vertex = new Vertex(id, x, y, z);
        vertices.put(id, vertex);
        adjacencyList.putIfAbsent(id, new ArrayList<>());
    }

    // Метод добавления ребра
    public void addEdge(int id1, int id2) {
        if (vertices.containsKey(id1) && vertices.containsKey(id2)) {
            adjacencyList.get(id1).add(id2);
            adjacencyList.get(id2).add(id1); // Так как граф неориентированный
        } else {
            System.out.println("Ошибка: одна или обе вершины не существуют.");
        }
    }

    // Метод для отображения вершин и их координат
    public void displayVertices() {
        System.out.println("Vertices:");
        for (Vertex vertex : vertices.values()) {
            System.out.println(vertex);
        }
    }

    // Метод для отображения списка смежности
    public void displayAdjacencyList() {
        System.out.println("\nAdjacency List:");
        for (Map.Entry<Integer, List<Integer>> entry : adjacencyList.entrySet()) {
            System.out.print("Vertex " + entry.getKey() + " -> ");
            for (Integer neighbor : entry.getValue()) {
                System.out.print(neighbor + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        // Создание графа
        Graph graph = new Graph();

        // Добавление вершин (id, x, y, z)
        graph.addVertex(1, 0.0, 0.0, 0.0);
        graph.addVertex(2, 0.0, 0.0, 1);
        graph.addVertex(3, 0.0, 1, 0.0);
        graph.addVertex(4, 0, 1.0, 1.0);
        graph.addVertex(5, 1.0, 0.0, 0.0);
        graph.addVertex(6, 1.0, 0.0, 1.0);
        graph.addVertex(7, 1.0, 1.0, 0.0);
        graph.addVertex(8, 1.0, 1.0, 1.0);

        // Добавление рёбер (список смежности)
        graph.addEdge(1, 2);
        graph.addEdge(1, 3);
        graph.addEdge(1, 5);
        graph.addEdge(2, 4);
        graph.addEdge(2, 6);
        graph.addEdge(3, 4);
        graph.addEdge(3, 7);
        graph.addEdge(4, 8);
        graph.addEdge(5, 6);
        graph.addEdge(5, 7);
        graph.addEdge(6, 8);
        graph.addEdge(7, 8);

        // Отображение вершин и списка смежности
        graph.displayVertices();
        graph.displayAdjacencyList();


    }
}
