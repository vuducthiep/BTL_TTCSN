package com.BTL_TTCSN.MIS.Greedy;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import java.util.ArrayList;
import java.util.List;

public class MIS_Greedy {
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
        myGraph.addEdge("A", "B");
        myGraph.addEdge("A", "C");
        myGraph.addEdge("B", "D");
        myGraph.addEdge("C", "D");
        myGraph.addEdge("C", "E");
        myGraph.addEdge("E", "F");
        myGraph.addEdge("E", "G");

        // Bước 2: Gọi phương thức tìm tập độc lập lớn nhất
        List<String> tapDocLapLonNhat = timTapDocLapLonNhat(myGraph);

        // Hiển thị kết quả tập độc lập lớn nhất
        System.out.println("Tập độc lập lớn nhất: " + tapDocLapLonNhat);
    }

    // Phương thức tìm tập độc lập lớn nhất từ đồ thị
    private static List<String> timTapDocLapLonNhat(Graph<String, DefaultEdge> myGraph) {
        List<String> MIS = new ArrayList<>();  //Tập độc lập lớn nhất
        List<String> daChon = new ArrayList<>(); // Tập hợp các đỉnh đã chọn

        // Bước 3: Lặp lại cho đến khi không còn đỉnh nào
        while (true) {
            // Kiểm tra xem có đỉnh nào trong đồ thị không
            String dinhCoBacNhoNhat = timDinhCoBacNhoNhat(myGraph, daChon);
            if (dinhCoBacNhoNhat == null) break; // Không còn đỉnh nào để chọn

            // Bước 4: Thêm đỉnh vào tập độc lập
            MIS.add(dinhCoBacNhoNhat);
            daChon.add(dinhCoBacNhoNhat); // Đánh dấu đỉnh đã chọn

            // Cập nhật đồ thị bằng cách loại bỏ đỉnh và các đỉnh kề
            for (DefaultEdge canh : myGraph.edgesOf(dinhCoBacNhoNhat)) {
                String dinhKe = myGraph.getEdgeTarget(canh) == dinhCoBacNhoNhat ?
                		myGraph.getEdgeSource(canh) :
                		myGraph.getEdgeTarget(canh);
                daChon.add(dinhKe); // Loại bỏ đỉnh kề
            }
        }
        return MIS; // Trả về tập độc lập tìm được
    }

    // Phương thức tìm đỉnh có bậc nhỏ nhất chưa được chọn
    private static String timDinhCoBacNhoNhat(Graph<String, DefaultEdge> myGraph, List<String> daChon) {
        String dinhNhoNhat = null;
        int bacNhoNhat = Integer.MAX_VALUE;

        for (String dinh : myGraph.vertexSet()) {
            if (!daChon.contains(dinh)) { // Nếu đỉnh chưa được chọn
                int bac = myGraph.degreeOf(dinh); // Lấy bậc của đỉnh
                if (bac < bacNhoNhat) { // So sánh bậc
                    bacNhoNhat = bac;
                    dinhNhoNhat = dinh; // Cập nhật đỉnh có bậc nhỏ nhất
                }
            }
        }
        return dinhNhoNhat; // Trả về đỉnh có bậc nhỏ nhất
    }
}
