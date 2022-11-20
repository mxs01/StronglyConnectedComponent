package graphAlgorithms;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SCC {
	// variables for csv input 
	protected String line;
	protected String[] splitLine;
	protected int counter = 0;
	protected BufferedReader br;
	protected static String filePath = "src/graphAlgorithms/big_graph.csv";
	
	
	// variables for algorithm
	static int[][] adjMatrix;
	static int[][] transposedMatrix;
	
	static int[]finishingTimes;
	static int timeCounter;
	static Color[] visited;
	
	static boolean[] inSCC;
	
	
	
	static int vertices = 1000;
	
	// initializes arrays with length n: n = amount vertices
	static public void initVariables(){
		adjMatrix = new int[vertices][vertices];
		transposedMatrix = new int[vertices][vertices];
		finishingTimes = new int[vertices];
		inSCC = new boolean[vertices];
		visited = new Color[vertices];
		
		
	}
	
	// 
	enum Color {
		WHITE, BLACK, GREY
	}
	
	
	// dfs algorithm which is used for the DFS on the adjacencyMatrix
	static public void DFS(int[][] matrix) {
		setColorWhite();
		timeCounter = 0;
		for (int node = 0; node < vertices; node++) {
			if (!inSCC[node] && visited[node] == Color.WHITE) {
				DFSVisit(matrix, node);
			}
		}
	}
	
	// dfs algorithm which is used for the second dfs on the transposed graph
	static public void DFSTransposed(int[][] matrix, int node) {
		setColorWhite();
		timeCounter = 0;
		DFSVisit(matrix, node);
		finishingTimes[node] = -1;
	}
	
	
	// method which is recursively visits nodes in DFS
	static public void DFSVisit(int[][]matrix, int node) {
		visited[node] = Color.GREY;
		
		for (int dest = 0; dest < matrix[node].length; dest++) {
			if (matrix[node][dest] ==1) { // vertices connected
				if (!inSCC[dest] && visited[dest] == Color.WHITE) {
					DFSVisit(matrix, dest);
				}
			}
		}
		visited[node] = Color.BLACK;
		finishingTimes[node] = timeCounter;
		timeCounter++;
	}
	
	
	 
	
	// finds maximum finish time 
	static public int findMaxTime() {
		int max = Integer.MIN_VALUE;
		int maxIndex = Integer.MIN_VALUE;
		
		for (int i = 0; i < finishingTimes.length; i++){
			if (finishingTimes[i] > max) {
				max = finishingTimes[i];
				maxIndex = i;
			}
		}
		finishingTimes[maxIndex] = Integer.MIN_VALUE;
		return maxIndex;
	}
	
	
	
	
	// transposes the adjacency matrix
	static public void transposeMatrix(){
		for (int row = 0; row < adjMatrix.length; row++) {
			for (int col = 0; col < adjMatrix[row].length; col++) {
				
				transposedMatrix[row][col] = adjMatrix[col][row];
			}
		}
		
	}
	
	
	// checks if we have already visited all vertices
	static public boolean notCheckedVertices() {
		for (int i = 0; i < inSCC.length; i++) {
			if (!inSCC[i]) {
				return true;
			}
		}
		return false; 
	}
	
	
	
	
	// sets all entries to color white
	static public void setColorWhite() {
		for (int i = 0; i < visited.length;i++) {
			visited[i] = Color.WHITE;
		}
	}
	
	
	// restores graph out of color and returns it
	static public List<Integer> restoreGraph(){
		List<Integer> path = new ArrayList();
		for (int node = 0; node < visited.length; node++) {
			if (visited[node] == Color.BLACK) {
				inSCC[node] = true;
				path.add(node);
				
			}
		}
		return path;
	}
	
	
	// main algorithm which finds SSC in graph
	public List<List<Integer>> findSSC(){
		List<List<Integer>> foundSSC = new ArrayList();
		
		initVariables();
		
		
		
		scanner(vertices); // create adjacency matrix out of csv file
		
		
		transposeMatrix();
		// generate transposed matrix
		
		for (int i = 0; i < inSCC.length; i++) {
			inSCC[i] = false;
		}
		
		
		while (notCheckedVertices()) {
			DFS(adjMatrix);
			DFSTransposed(transposedMatrix, findMaxTime());
			foundSSC.add(restoreGraph());
		}
		return foundSSC;
		
		
		
		
		
		
	}
	
	// the scanner is used for the translation of csv files to adjacencyMatrix
	public void scanner(int vertices){
		try {
			br = new BufferedReader(new FileReader(filePath));
			while (br.ready()) {
				line = br.readLine();
				splitLine = line.split(",");
				for (int i = 0; i < vertices; i++) {
					adjMatrix[counter][i] = Integer.parseInt(splitLine[i]);
				}
				counter++;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// prettyPrint is responsible for the formatted output of the SCC
	static public void prettyPrint(List<List<Integer>> SCC) { 
		for (List<Integer> path: SCC) {
			String str = "[";
			for (Integer node: path) {
				str += node + " ";	
			}
			str += "]";
			System.out.println(str);
			System.out.println();
			System.out.println();
			System.out.println();
			
		}
	}
	
	
	
	 

}
