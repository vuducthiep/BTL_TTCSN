package backtracking.maximalindependentset;

import java.util.HashSet;
import java.util.Set;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

/**
 *
 * @author PC
 */
public class main {
	static Set<String> maxIndependentSet = new HashSet<>();
    static int maxSize = 0;

    public static void main(String[] args) {
        // Khởi tạo đồ thị đơn giản
        Graph<String, DefaultEdge> graph = new SimpleGraph<>(DefaultEdge.class);

        // Thêm các đỉnh vào đồ thị
        String[] vertices = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
        for (String vertex : vertices) {
            graph.addVertex(vertex);
        }

        // Thêm các cạnh
        graph.addEdge("A", "B");
        graph.addEdge("A", "C");
        graph.addEdge("B", "C");
        graph.addEdge("B", "E");
        graph.addEdge("C", "E");
        graph.addEdge("C", "F");
        graph.addEdge("D", "F");
        graph.addEdge("D", "G");
        graph.addEdge("E", "H");
        graph.addEdge("F", "J");
        graph.addEdge("H", "I");
        graph.addEdge("I", "J");

        // Tìm tập hợp độc lập lớn nhất bằng quay lui
        findMaxIndependentSet(graph, new HashSet<>(), new HashSet<>(graph.vertexSet()));
        
        // In kết quả
        System.out.println("Tập hợp độc lập lớn nhất: " + maxIndependentSet);
        System.out.println("Kích thước lớn nhất: " + maxSize);
    }

    // Hàm quay lui để tìm tập hợp độc lập lớn nhất
    private static void findMaxIndependentSet(Graph<String, DefaultEdge> graph, Set<String> currentSet, Set<String> remainingVertices) {
        if (remainingVertices.isEmpty()) {
            if (currentSet.size() > maxSize) {
                maxSize = currentSet.size();
                maxIndependentSet = new HashSet<>(currentSet);
            }
            return;
        }

        String vertex = remainingVertices.iterator().next();
        remainingVertices.remove(vertex);

        // Lựa chọn không chọn đỉnh hiện tại
        findMaxIndependentSet(graph, currentSet, new HashSet<>(remainingVertices));

        // Lựa chọn chọn đỉnh hiện tại, loại bỏ các đỉnh kề
        Set<String> neighbors = new HashSet<>();
        for (DefaultEdge edge : graph.edgesOf(vertex)) {
            neighbors.add(graph.getEdgeSource(edge));
            neighbors.add(graph.getEdgeTarget(edge));
        }
        neighbors.remove(vertex);

        if (currentSet.stream().noneMatch(neighbors::contains)) {
            currentSet.add(vertex);
            remainingVertices.removeAll(neighbors);
            findMaxIndependentSet(graph, currentSet, remainingVertices);
            currentSet.remove(vertex);
        }
        remainingVertices.add(vertex);
    }
}
