package MIS;


import java.util.*;

public class Greedy {
    public static void main(String[] args) {
        // Bước 1: Tạo đồ thị
        MyGraph myGraph = new MyGraph();

        // Thêm các đỉnh vào đồ thị
        myGraph.addVertex("A");
        myGraph.addVertex("B");
        myGraph.addVertex("C");
        myGraph.addVertex("D");
        myGraph.addVertex("E");
        myGraph.addVertex("F");
        myGraph.addVertex("G");

        // Thêm các cạnh giữa các đỉnh
        myGraph.addEdge("A", "B");
        myGraph.addEdge("A", "C");
        myGraph.addEdge("B", "D");
        myGraph.addEdge("C", "D");
        myGraph.addEdge("C", "E");
        myGraph.addEdge("E", "F");
        myGraph.addEdge("E", "G");

        // Bước 2: Gọi phương thức tìm tập độc lập lớn nhất
        List<String> tapDocLapLonNhat = timTapDocLapLonNhat(myGraph);

        System.out.println("Tập độc lập lớn nhất: " + tapDocLapLonNhat);
    }

    // Phương thức tìm tập độc lập lớn nhất từ đồ thị
    private static List<String> timTapDocLapLonNhat(MyGraph myGraph) {
        List<String> MIS = new ArrayList<>();  // Tập độc lập lớn nhất
        Set<String> daChon = new HashSet<>(); // Tập hợp các đỉnh đã chọn

        // Tạo bản sao đồ thị để thao tác mà không thay đổi đồ thị gốc
        MyGraph graph = new MyGraph();
        for (String vertex : myGraph.vertexSet()) {
            graph.addVertex(vertex);
        }
        for (String vertex : myGraph.vertexSet()) {
            for (String neighbor : myGraph.neighborsOf(vertex)) {
                graph.addEdge(vertex, neighbor);
            }
        }

        // Bước 3: Lặp lại cho đến khi không còn đỉnh nào
        while (!graph.vertexSet().isEmpty()) {
            // Tìm đỉnh có bậc nhỏ nhất chưa được chọn
            String dinhCoBacNhoNhat = timDinhCoBacNhoNhat(graph);
            if (dinhCoBacNhoNhat == null) break;

            // Bước 4: Thêm đỉnh vào tập độc lập
            MIS.add(dinhCoBacNhoNhat);
            daChon.add(dinhCoBacNhoNhat);

            // Loại bỏ đỉnh và các đỉnh kề của nó khỏi đồ thị
            Set<String> dinhKe = new HashSet<>(graph.neighborsOf(dinhCoBacNhoNhat));
            graph.removeVertex(dinhCoBacNhoNhat);
            for (String dinh : dinhKe) {
                graph.removeVertex(dinh);
            }
        }
        return MIS;
    }

    // Phương thức tìm đỉnh có bậc nhỏ nhất
    private static String timDinhCoBacNhoNhat(MyGraph graph) {
        String dinhNhoNhat = null;
        int bacNhoNhat = Integer.MAX_VALUE;

        for (String dinh : graph.vertexSet()) {
            int bac = graph.degreeOf(dinh);
            if (bac < bacNhoNhat) {
                bacNhoNhat = bac;
                dinhNhoNhat = dinh;
            }
        }
        return dinhNhoNhat;
    }
}