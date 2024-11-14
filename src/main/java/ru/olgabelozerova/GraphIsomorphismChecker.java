package ru.olgabelozerova;

import org.jgrapht.Graph;
import org.jgrapht.alg.isomorphism.VF2GraphIsomorphismInspector;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GraphIsomorphismChecker {

    public static void main(String[] args) {
        // Создаем 3 графа, представляющие гиперкуб размерности 3
        Graph<Integer, DefaultEdge> cubeGraph1 = createCubeGraph();
        Graph<Integer, DefaultEdge> cubeGraph2 = createCubeGraph();

        Graph<Integer, DefaultEdge> cubeGraph3 = createCubeGraph();
        Graph<Integer, DefaultEdge> cubeGraph4 = createCubeGraph();


//         Проверяем, изоморфны ли графы
        boolean areIsomorphic = checkIsomorphism(cubeGraph1, cubeGraph2);
        System.out.println("Are the graphs 1 and 2 isomorphic? " + areIsomorphic);


        // Удаляем рёбра из графов для создания различных версий графа
        cubeGraph1.removeEdge(0, 1);
        cubeGraph1.removeEdge(1, 3);

        cubeGraph2.removeEdge(0, 2);
        cubeGraph2.removeEdge(1, 3);
        cubeGraph2.removeEdge(1, 5);

        System.out.println("Удалили рёбра из графов для создания различных версий графа");
        areIsomorphic = checkIsomorphism(cubeGraph1, cubeGraph2);
        System.out.println("Are the graphs 1 and 2 isomorphic? " + areIsomorphic);


        //проверка изоморфизма графов из 11 ребер

        // удаляем 12-ю способами 1 ребро
        Set<Graph<Integer, DefaultEdge>> modifiedGraphs1 = removeEdges1(createCubeGraph());
        //проверяем изоморфны ли получившиеся графы из 11 ребер
        Set<Graph<Integer, DefaultEdge>> nonIsomorphicGraphs = checkIsomorphismSet(modifiedGraphs1);
        //печатаем все неизоморфные графы
        int count = 1;
        for(Graph<Integer, DefaultEdge> g: nonIsomorphicGraphs){
            System.out.println("graph " + count);
            printGraph(g);
            count++;
        }

        //проверка изоморфизма графов из 10 ребер
        //удаляем по 1 ребру всеми возможными способами из 11-реберного подграфа куба
        cubeGraph4.removeEdge(0, 1);
        Set<Graph<Integer, DefaultEdge>> modifiedGraphs2 = removeEdges1(cubeGraph4);
        Set<Graph<Integer, DefaultEdge>> modifiedGraphs3 = new HashSet<>();

        //ищем неизоморфные графы
        Set<Graph<Integer, DefaultEdge>> nonIsomorphicGraphs2 = checkIsomorphismSet(modifiedGraphs2);
        Set<Graph<Integer, DefaultEdge>> nonIsomorphicGraphs2upd = new HashSet<>();
        //печатаем неизоморфные графы
        count = 1;
        for(Graph<Integer, DefaultEdge> g: nonIsomorphicGraphs2){
            System.out.println("graph " + count);
            printGraph(g);
            count++;
        }

        count =0;

        //TODO: зеркальные отражения ???


        count = 1;

        for(Graph<Integer, DefaultEdge> gr: nonIsomorphicGraphs2upd){
            System.out.println("graph mirror" + count);
            printGraph(gr);
            count++;
        }


    }



    // Метод для создания графа, представляющего гиперкуб размерности 3
    private static Graph<Integer, DefaultEdge> createCubeGraph() {
        Graph<Integer, DefaultEdge> graph  = new SimpleGraph<>(DefaultEdge.class);
        // Добавляем вершины
        for (int i = 0; i < 8; i++) {
            graph.addVertex(i);
        }

        // Добавляем рёбра для 3-мерного куба
        Set<int[]> edges = new HashSet<>();
        edges.add(new int[]{0, 1});
        edges.add(new int[]{0, 2});
        edges.add(new int[]{0, 4});
        edges.add(new int[]{1, 3});
        edges.add(new int[]{1, 5});
        edges.add(new int[]{2, 3});
        edges.add(new int[]{2, 6});
        edges.add(new int[]{3, 7});
        edges.add(new int[]{4, 5});
        edges.add(new int[]{4, 6});
        edges.add(new int[]{5, 7});
        edges.add(new int[]{6, 7});

        for (int[] edge : edges) {
            graph.addEdge(edge[0], edge[1]);
        }

        return graph;
    }

    // Метод для проверки изоморфизма графов с использованием VF2 алгоритма
    private static boolean checkIsomorphism(Graph<Integer, DefaultEdge> graph1, Graph<Integer, DefaultEdge> graph2) {
        VF2GraphIsomorphismInspector<Integer, DefaultEdge> inspector =
                new VF2GraphIsomorphismInspector<>(graph1, graph2);

        return inspector.isomorphismExists();
    }

    // Метод для удаления рёбер по 1 ребру и создания новых графов
    private static Set<Graph<Integer, DefaultEdge>> removeEdges1(Graph<Integer, DefaultEdge> graph) {
        Set<Graph<Integer, DefaultEdge>> modifiedGraphs = new HashSet<>();
        Set<DefaultEdge> edges = new HashSet<>(graph.edgeSet());

        // Перебираем все рёбра и создаем новые графы с удалённым ребром
        for (DefaultEdge edge : edges) {
            Graph<Integer, DefaultEdge> tempGraph = new SimpleGraph<>(DefaultEdge.class);

            // Копируем вершины и рёбра
            for (Integer vertex : graph.vertexSet()) {
                tempGraph.addVertex(vertex);
            }
            for (DefaultEdge e : graph.edgeSet()) {
                Integer source = graph.getEdgeSource(e);
                Integer target = graph.getEdgeTarget(e);
                if (!e.equals(edge)) { // Не добавляем текущее ребро
                    tempGraph.addEdge(source, target);
//                    System.out.println("не добавляем " + graph.getEdgeSource(e) + " " + graph.getEdgeTarget(e));
                }
            }
            if (!modifiedGraphs.contains(tempGraph)) modifiedGraphs.add(tempGraph);
        }

        return modifiedGraphs;
    }

    //

    // Метод для проверки изоморфизма всех модифицированных графов между собой
//    private static Set<Graph<Integer, DefaultEdge>> checkIsomorphismSet(Set<Graph<Integer, DefaultEdge>> modifiedGraphs) {
//        System.out.println("Checking isomorphism between all modified graphs:");
//
//        Set<Graph<Integer, DefaultEdge>> nonIsomorphicGraphs = new HashSet<>();
//        // Преобразуем Set в List для удобства перебора
//        List<Graph<Integer, DefaultEdge>> graphList = new ArrayList<>(modifiedGraphs);
//
//        boolean areIsomorphic;
//        //если все графы изоморфны, в неизоморфных графах должен быть 1 граф
//        nonIsomorphicGraphs.add(graphList.get(0));
//
//        int counter = 0;
//
//
//        // Проверяем каждый граф на изоморфизм с каждым другим графом
//        for (int i = 0; i < graphList.size(); i++) {
//            for (int j = i + 1; j < graphList.size(); j++) {
////
//                areIsomorphic = checkIsomorphism(graphList.get(i), graphList.get(j));
//                System.out.println("Graph " + (i + 1) + " and Graph " + (j + 1) + " are isomorphic? " + !areIsomorphic);
//                if(!areIsomorphic) counter++;
//
//                if (!areIsomorphic) {
//                    if (!graphList.contains(graphList.get(i))) nonIsomorphicGraphs.add(graphList.get(i));
//                    if (!graphList.contains(graphList.get(j))) nonIsomorphicGraphs.add(graphList.get(j));
//                }
//
//            }
//        }
//        System.out.println("counter: " + counter);
//        return nonIsomorphicGraphs;
//    }

    private static Set<Graph<Integer, DefaultEdge>> checkIsomorphismSet(Set<Graph<Integer, DefaultEdge>> modifiedGraphs) {
        System.out.println("Checking isomorphism between all modified graphs:");

        Set<Graph<Integer, DefaultEdge>> nonIsomorphicGraphs = new HashSet<>();
        List<Graph<Integer, DefaultEdge>> graphList = new ArrayList<>(modifiedGraphs);

                nonIsomorphicGraphs.add(graphList.get(0));


        for (Graph<Integer, DefaultEdge> candidateGraph : graphList) {
            boolean isUnique = true;
            for (Graph<Integer, DefaultEdge> existingGraph : nonIsomorphicGraphs) {
                if (checkIsomorphism(candidateGraph, existingGraph)) {
                    isUnique = false;
                    break;
                }
            }
            if (isUnique) {
                nonIsomorphicGraphs.add(candidateGraph);
            }
        }
        return nonIsomorphicGraphs;
    }

    // Метод для печати графа
    private static void printGraph(Graph<Integer, DefaultEdge> graph) {
//        System.out.println("Graph structure:");
//        for (Integer vertex : graph.vertexSet()) {
//            System.out.print("Vertex " + vertex + " is connected to: ");
//            for (DefaultEdge edge : graph.edgesOf(vertex)) {
//                Integer source = graph.getEdgeSource(edge);
//                Integer target = graph.getEdgeTarget(edge);
//                // Определяем соседнюю вершину
//                Integer neighbor = vertex.equals(source) ? target : source;
//                System.out.print(neighbor + " ");
//            }
//            System.out.println();
//        }


        System.out.println("Graph structure:");
        for (Integer vertex : graph.vertexSet()) {
            //System.out.println("Vertex " + vertex + " is connected to: ");
            for (DefaultEdge edge : graph.edgesOf(vertex)) {
                Integer source = graph.getEdgeSource(edge);
                Integer target = graph.getEdgeTarget(edge);
                // Определяем соседнюю вершину
                Integer neighbor = vertex.equals(source) ? target : source;
                System.out.println( vertex + " " + neighbor + " ");
            }
            System.out.println();
        }




    }
}
