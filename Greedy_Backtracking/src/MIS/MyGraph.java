package src.MIS;

import java.util.*;

public class MyGraph {
    private Map<String, Set<String>> adjacencyList;

    public MyGraph() {
        adjacencyList = new HashMap<>();
    }

    // Thêm đỉnh vào đồ thị
    public void addVertex(String vertex) {
        adjacencyList.putIfAbsent(vertex, new HashSet<>());
    }

    // Thêm cạnh vào đồ thị
    public void addEdge(String vertex1, String vertex2) throws IllegalArgumentException {
        // Kiểm tra nếu các đỉnh không tồn tại trong đồ thị
        if (!adjacencyList.containsKey(vertex1)) {
            throw new IllegalArgumentException("Đỉnh " + vertex1 + " không tồn tại trong đồ thị.");
        }
        if (!adjacencyList.containsKey(vertex2)) {
            throw new IllegalArgumentException("Đỉnh " + vertex2 + " không tồn tại trong đồ thị.");
        }

        // Thêm cạnh nếu cả hai đỉnh đều tồn tại
        adjacencyList.putIfAbsent(vertex1, new HashSet<>());
        adjacencyList.putIfAbsent(vertex2, new HashSet<>());
        adjacencyList.get(vertex1).add(vertex2);
        adjacencyList.get(vertex2).add(vertex1); // Vì đồ thị vô hướng
    }

    // Lấy các đỉnh của đồ thị
    public Set<String> vertexSet() {
        return adjacencyList.keySet();
    }

    // Lấy các đỉnh kề của một đỉnh
    public Set<String> neighborsOf(String vertex) {
        return adjacencyList.getOrDefault(vertex, Collections.emptySet());
    }

    // Lấy bậc của đỉnh
    public int degreeOf(String vertex) {
        return adjacencyList.getOrDefault(vertex, Collections.emptySet()).size();
    }

    // Xóa đỉnh khỏi đồ thị
    public void removeVertex(String vertex) {
        adjacencyList.remove(vertex);
        for (Set<String> neighbors : adjacencyList.values()) {
            neighbors.remove(vertex);
        }
    }
}
