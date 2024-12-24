package src.MIS;

import org.jgrapht.Graph;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.graph.DefaultEdge;

import java.util.Set;
import java.util.HashSet;

public class JgraphT_KiemThu {

    // Phương thức chuyển đổi đồ thị thành đồ thị đối ngẫu
    public Graph<String, DefaultEdge> createComplementGraph(Graph<String, DefaultEdge> graph) {
        Graph<String, DefaultEdge> complementGraph = new SimpleGraph<>(DefaultEdge.class);
        
        // Thêm các đỉnh vào đồ thị đối ngẫu
        for (String vertex : graph.vertexSet()) {
            complementGraph.addVertex(vertex);
        }

        // Thêm các cạnh vào đồ thị đối ngẫu (kết nối hai đỉnh nếu không có cạnh giữa chúng trong đồ thị ban đầu)
        for (String vertex1 : graph.vertexSet()) {
            for (String vertex2 : graph.vertexSet()) {
                if (!vertex1.equals(vertex2) && !graph.containsEdge(vertex1, vertex2)) {
                    complementGraph.addEdge(vertex1, vertex2);
                }
            }
        }

        return complementGraph;
    }

    // Phương thức tìm Maximum Independent Set từ Maximum Clique trong đồ thị đối ngẫu
    public Set<String> findMaximumIndependentSet(Graph<String, DefaultEdge> graph) {
        // Chuyển đồ thị thành đồ thị đối ngẫu
        Graph<String, DefaultEdge> complementGraph = createComplementGraph(graph);

        // Tìm các clique cực đại trong đồ thị đối ngẫu
        Set<Set<String>> cliques = bronKerbosch(complementGraph);

        // Nếu không có clique nào, trả về một tập rỗng
        if (cliques.isEmpty()) {
            return new HashSet<>();
        }

        // Chọn clique lớn nhất làm MIS
        Set<String> maxClique = new HashSet<>();
        for (Set<String> clique : cliques) {
            if (clique.size() > maxClique.size()) {
                maxClique = clique;
            }
        }

        return maxClique;
    }

    // Thuật toán Bron-Kerbosch đệ quy để tìm các clique cực đại
    private Set<Set<String>> bronKerbosch(Graph<String, DefaultEdge> graph) {
        Set<String> P = new HashSet<>(graph.vertexSet());  // Tập các đỉnh có thể trở thành thành viên của clique
        Set<String> R = new HashSet<>();  // Tập đỉnh đã là thành viên của clique
        Set<String> X = new HashSet<>();  // Tập đỉnh không thể tham gia clique

        Set<Set<String>> cliques = new HashSet<>();
        bronKerboschRecursive(graph, P, R, X, cliques);
        return cliques;
    }

    // Phương thức đệ quy thực thi thuật toán Bron-Kerbosch
    private void bronKerboschRecursive(Graph<String, DefaultEdge> graph, Set<String> P, Set<String> R, Set<String> X, Set<Set<String>> cliques) {
        if (P.isEmpty() && X.isEmpty()) {
            cliques.add(new HashSet<>(R));  // Nếu P và X đều rỗng, R là một clique cực đại
            return;
        }

        Set<String> PCopy = new HashSet<>(P);
        for (String vertex : PCopy) {
            Set<String> newP = new HashSet<>(getNeighbors(graph, vertex));
            newP.retainAll(P);
            Set<String> newR = new HashSet<>(R);
            newR.add(vertex);
            Set<String> newX = new HashSet<>(getNeighbors(graph, vertex));
            newX.retainAll(X);

            bronKerboschRecursive(graph, newP, newR, newX, cliques);

            P.remove(vertex);
            X.add(vertex);
        }
    }

    // Lấy các đỉnh kề với một đỉnh trong đồ thị
    private Set<String> getNeighbors(Graph<String, DefaultEdge> graph, String vertex) {
        Set<String> neighbors = new HashSet<>();
        for (DefaultEdge edge : graph.edgesOf(vertex)) {
            neighbors.add(graph.getEdgeSource(edge).equals(vertex) ? graph.getEdgeTarget(edge) : graph.getEdgeSource(edge));
        }
        return neighbors;
    }

    public static void main(String[] args) {
        // Bước 1: Tạo đồ thị
        Graph<String, DefaultEdge> myGraph = new SimpleGraph<>(DefaultEdge.class);

     // Thêm các đỉnh vào đồ thị
        myGraph.addVertex("A");
        myGraph.addVertex("B");
        myGraph.addVertex("C");
        myGraph.addVertex("D");
        myGraph.addVertex("E");
        myGraph.addVertex("F");
        myGraph.addVertex("G");

        // Thêm các cạnh giữa các đỉnh
        try {
            myGraph.addEdge("A", "B");
            myGraph.addEdge("A", "C");
            myGraph.addEdge("B", "D");
            myGraph.addEdge("C", "D");
            myGraph.addEdge("C", "E");
            myGraph.addEdge("E", "F");
            myGraph.addEdge("E", "G");
        } catch (IllegalArgumentException e) {
            System.out.println("Lỗi khi thêm cạnh: " + e.getMessage());
            return;
        }
        // Gọi phương thức tìm tập độc lập lớn nhất (Maximum Independent Set) từ Maximum Clique
        JgraphT_KiemThu jGraphT = new JgraphT_KiemThu();
        
        Set<String> maxIndependentSet = jGraphT.findMaximumIndependentSet(myGraph);

        System.out.println("Tập độc lập lớn nhất: " + maxIndependentSet);
    }
}
