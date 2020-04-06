package ru.kpfu.itis.semester.BarSamCo;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class GraphCode {
	private List<Edge> edges;
	private int numNod;
	
	private class Edge {
		private int i;
		private int j;
		
		public Edge(int i, int j) {
			this.i = i;
			this.j = j;
		}
		
		public int getI() {
			return i;
		}
		public int getJ() {
			return j;
		}
	}
	
	public GraphCode(int[][] mI) {
		if (mI.length == 0) throw new NoSuchElementException("Matrix has no edge");
		edges = new ArrayList<>();
		numNod = mI[0].length;
		int count, i, j;
		for (int col = 0; col < mI.length; col++) {
			if (numNod != mI[col].length) throw new IllegalArgumentException("Matrix Incidence must be a rectangle matrix with fixed size");
			count = 0; i = 0; j = 0;
			for (int row = 0; row < mI[col].length; row++) {
				if (mI[col][row] != 0) {
					i = i == 0 ? row : i;
					j = row;
					count++;
				}
				if (count > 2) throw new IllegalArgumentException("Edge " + col + " have more than 2 incident nodes");
				if (i != 0) edges.add(new Edge(i, j));
			}
		}
	}
	
	public int[][] getMI() {
		if (edges.isEmpty()) throw new NoSuchElementException("Graph has no edge");
		int[][] mI = new int[numNod][edges.size() - 1];
		for (Edge edge : edges) {
			mI[edge.getI()][edge.getJ()] = 1;
		}
		return mI;
	}
	
	public void insert(int i, int j) {
		edges.add(new Edge(i, j));
	}
	
	public void delete(int i, int j) {
		if (i > numNod || j > numNod) throw new IllegalArgumentException("Indexes are out of matrix's bounds");
		int index = 0;
		int toRemove = -1;
		for (Edge edge : edges) {
			if (edge.getI() == i && edge.getJ() == j) {
				toRemove = index;
			}
			index++;
		}
		if (toRemove == -1) throw new NoSuchElementException("Matrix already hasn't this edge");
		edges.remove(toRemove);
	}
	
	public void modify(int i) {
		if (i > numNod) throw new IllegalArgumentException("Matrix alredy hasn't this node");
		Integer index = 0;
		List<Integer> toRemove = new ArrayList<>();
		for (Edge edge : edges) {
			if (edge.getI() == i || edge.getJ() == i) {
				toRemove.add(index);
			}
		}
		int shift = 0;
		for (Integer ind : toRemove) {
			edges.remove(ind - shift);
			shift++;
		}
	}
	
	public void alternativeModify(int i) {
		if (i > numNod) throw new IllegalArgumentException("Matrix alredy hasn't this node");
		List<Edge> toRemove = new ArrayList<>();
		for (Edge edge : edges) {
			if (edge.getI() == i || edge.getJ() == i) {
				toRemove.add(edge);
			}
		}
		for (Edge edge : toRemove) {
			edges.remove(edge);
		}
	}
	
	public ArrayList<Integer> show(int m) {
		if (edges.isEmpty()) throw new NoSuchElementException("Graph has no edge");
		ArrayList<Integer> nodes = new ArrayList<>();
		int[] multiplicity = new int[numNod];
		for (Edge edge : edges) {
			multiplicity[edge.getI() - 1]++;
			multiplicity[edge.getJ() - 1]++;
		}
		for (int i = 0; i < numNod; i++) {
			if (multiplicity[i] > m) nodes.add(i);
		}
		return nodes;
	}

	public int getNumNod() {
		return numNod;
	}
	
}