package MIS;

import java.util.*;

public class MyGraph {
    private final Map<String, Set<String>> adjacencyList; // Danh sách kề để lưu các đỉnh và cạnh

    public MyGraph() {
        adjacencyList = new HashMap<>();
    }

    // Thêm đỉnh vào đồ thị
    public void addVertex(String vertex) {
        adjacencyList.putIfAbsent(vertex, new HashSet<>());
    }

    // Thêm cạnh giữa hai đỉnh
    public void addEdge(String vertex1, String vertex2) {
        adjacencyList.putIfAbsent(vertex1, new HashSet<>());
        adjacencyList.putIfAbsent(vertex2, new HashSet<>());
        adjacencyList.get(vertex1).add(vertex2);
        adjacencyList.get(vertex2).add(vertex1);
    }

    // Loại bỏ đỉnh và tất cả các cạnh kề
    public void removeVertex(String vertex) {
        if (adjacencyList.containsKey(vertex)) {
            for (String neighbor : adjacencyList.get(vertex)) {
                adjacencyList.get(neighbor).remove(vertex);
            }
            adjacencyList.remove(vertex);
        }
    }

    // Lấy bậc của đỉnh
    public int degreeOf(String vertex) {
        return adjacencyList.getOrDefault(vertex, Collections.emptySet()).size();
    }

    // Lấy tập hợp các đỉnh
    public Set<String> vertexSet() {
        return adjacencyList.keySet();
    }

    // Lấy danh sách các đỉnh kề của một đỉnh
    public Set<String> neighborsOf(String vertex) {
        return adjacencyList.getOrDefault(vertex, Collections.emptySet());
    }

    // Kiểm tra danh sách kề
    public boolean hasVertex(String vertex) {
        return adjacencyList.containsKey(vertex);
    }
}