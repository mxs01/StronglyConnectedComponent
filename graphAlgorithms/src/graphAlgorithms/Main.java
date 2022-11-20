package graphAlgorithms;

import java.util.List;

public class Main {
	
	public static void printArray(int[][] array) {
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array[i].length; j++) {
				System.out.print(array[i][j] + " ");
			}
			System.out.println();
		}
		
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		SCC g1 = new SCC();
		
		g1.prettyPrint(g1.findSSC());
		
		
		
	
		
		

	}

}
