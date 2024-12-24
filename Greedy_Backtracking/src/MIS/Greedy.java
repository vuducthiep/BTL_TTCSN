package src.MIS;
import src.MIS.MyGraph;

import java.util.*;

public class Greedy {
    public static void main(String[] args) {
        // Bước 1: Tạo đồ thị
        MyGraph graph = new MyGraph();

     // Thêm các đỉnh vào đồ thị
        String[] vertices = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
        for (String vertex : vertices) {
            graph.addVertex(vertex);
        }

        try {
        // Thêm các cạnh vào đồ thị
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
	    } catch (IllegalArgumentException e) {
	        System.out.println("Lỗi khi thêm cạnh: " + e.getMessage());
	        return;
	    }


        // Bước 2: Gọi phương thức tìm tập độc lập lớn nhất
        List<String> tapDocLapLonNhat = timTapDocLapLonNhat(graph);

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