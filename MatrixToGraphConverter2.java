import java.util.*;

class MatrixToGraphConverter<T> {
    private Map<T, List<T>> graph;
    private int rows;
    private int cols;

    public MatrixToGraphConverter(T[][] matrix) {
        graph = new HashMap<>();
        rows = matrix.length;
        cols = matrix[0].length;
    }

    public void addEdge(T node1, T node2) {
        graph.computeIfAbsent(node1, k -> new ArrayList<>()).add(node2);
    }

    public void convertToGraph(T[][] matrix) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                T node = matrix[i][j];
                List<T> neighbors = new ArrayList<>();

                if (i > 0) {
                    neighbors.add(matrix[i - 1][j]);  // Up
                }
                if (i < rows - 1) {
                    neighbors.add(matrix[i + 1][j]);  // Down
                }
                if (j > 0) {
                    neighbors.add(matrix[i][j - 1]);  // Left
                }
                if (j < cols - 1) {
                    neighbors.add(matrix[i][j + 1]);  // Right
                }

                graph.put(node, neighbors);
            }
        }
    }

    public void printGraph() {
        for (Map.Entry<T, List<T>> entry : graph.entrySet()) {
            T node = entry.getKey();
            List<T> neighbors = entry.getValue();
            System.out.println("Node " + node + " is connected to: " + neighbors);
        }
    }

    public void bfs(T startNode) {
        Queue<T> queue = new LinkedList<>();
        Set<T> visited = new HashSet<>();

        queue.offer(startNode);
        visited.add(startNode);

        while (!queue.isEmpty()) {
            T currentNode = queue.poll();
            System.out.println("Visited: " + currentNode);

            List<T> neighbors = graph.get(currentNode);
            if (neighbors != null) {
                for (T neighbor : neighbors) {
                    if (!visited.contains(neighbor)) {
                        queue.offer(neighbor);
                        visited.add(neighbor);
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of rows: ");
        int numRows = scanner.nextInt();
        System.out.print("Enter the number of columns: ");
        int numCols = scanner.nextInt();

        Object[][] matrix = new Object[numRows][numCols];
        System.out.println("Enter the matrix elements:");

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                if (scanner.hasNextInt()) {
                    matrix[i][j] = scanner.nextInt();
                } else {
                    matrix[i][j] = scanner.next();
                }
            }
        }

        System.out.print("Enter the starting node: ");
        Object startNode;
        if (scanner.hasNextInt()) {
            startNode = scanner.nextInt();
        } else {
            startNode = scanner.next();
        }

        MatrixToGraphConverter<Object> converter = new MatrixToGraphConverter<>(matrix);
        converter.convertToGraph(matrix);
        converter.printGraph();

        System.out.println("BFS Traversal:");
        converter.bfs(startNode);

        scanner.close();
    }
}