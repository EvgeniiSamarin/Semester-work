package ru.kpfu.itis.semester.BarSamCo;

import java.util.concurrent.TimeUnit;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Tests {

	public static final int NUMBER_OF_METHODS = 6;
	
	public static void main(String[] args) {
		int[][] matrix = Tests.createNewMatrix();
		//Shows your random matrix
//		for (int i = 0; i < matrix.length; i++) {
//			for (int j = 0; j < matrix[i].length; j++) {
//				System.out.print(matrix[i][j] + "\t");
//			}
//			System.out.println();
//		}
		long time;
		List<String[]> results = new ArrayList<>();

		int count;
		for (int i = 0; i < 15; i++) {
			String[] values = new String[NUMBER_OF_METHODS];
			count = 0;
			time = timer(() -> {
				GraphCode graph = new GraphCode(matrix);
			}, TimeUnit.NANOSECONDS);
			//Shows your time in nanosecs
			System.out.println(time);
			values[count] = String.valueOf(time);
			count++;
			
			GraphCode graph = new GraphCode(matrix);
			time = timer(() -> {
				graph.getMI();
			}, TimeUnit.NANOSECONDS);
			//Shows your time in nanosecs
			System.out.println(time);
			values[count] = String.valueOf(time);
			count++;
			
			Random generator = new Random();
			int rand1 = generator.nextInt(graph.getNumNod());
			time = timer(() -> {
				graph.modify(rand1);
			}, TimeUnit.NANOSECONDS);
			//Shows your time in nanosecs
			System.out.println(time);
			values[count] = String.valueOf(time);
			count++;
			
			
			int rand2 = generator.nextInt(graph.getNumNod());
			int rand3 = generator.nextInt(graph.getNumNod());
			time = timer(() -> {
				graph.insert(rand2, rand3);
			}, TimeUnit.NANOSECONDS);
			//Shows your time in nanosecs
			System.out.println(time);
			values[count] = String.valueOf(time);
			count++;
			
			time = timer(() -> {
				graph.delete(rand2, rand3);
			}, TimeUnit.NANOSECONDS);
			//Shows your time in nanosecs
			System.out.println(time);
			values[count] = String.valueOf(time);
			count++;
			
			int rand4 = generator.nextInt(graph.getMI().length);
			time = timer(() -> {
				graph.show(rand4);
			}, TimeUnit.NANOSECONDS);
			//Shows your time in nanosecs
			System.out.println(time);
			values[count] = String.valueOf(time);
			count++;
			results.add(values);

		}
		Tests.writeToCsvFile(results, ", ", "temp.csv");
	}

	private static int[][] createNewMatrix() {
		Random generator = new Random();
		byte iCapacity = (byte) Math.abs((byte) generator.nextInt());
		byte jCapacity = (byte) Math.abs((byte) generator.nextInt());
		//Shows random cols and rows
//		System.out.println(iCapacity + " " + jCapacity);
		int[][] matrix = new int[iCapacity][jCapacity];
		int count;
		for (int i = 0; i < iCapacity; i++) {
			count = 0;
			for (int j = 0; j < jCapacity; j++) {
				matrix[i][j] = Math.abs(generator.nextInt() % 2);
				if (matrix[i][j] == 1) count++;
				if (count == 2) break; 
			}
		}
		return matrix;
	}
	
	private static long timer(Runnable method, TimeUnit timeUnit) {
	    long time = System.nanoTime();
	    method.run();
	    time = System.nanoTime() - time;
	    return TimeUnit.NANOSECONDS.convert(time, timeUnit);
	}
	
	private static void writeToCsvFile(List<String[]> thingsToWrite, String separator, String fileName){
	    try (FileWriter writer = new FileWriter(fileName)){
	        for (String[] strings : thingsToWrite) {
	            for (int i = 0; i < strings.length; i++) {
	                writer.append(strings[i]);
	                if(i < (strings.length - 1))
	                    writer.append(separator);
	            }
	            writer.append(System.lineSeparator());
	        }
	        writer.flush();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
}
