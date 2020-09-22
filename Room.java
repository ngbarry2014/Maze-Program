package WuNg.cs146.project3;

import java.util.ArrayList;

public class Room {
	private int number;
	private ArrayList<Room> neighbors;
	private ArrayList<Room> path;
	private int color;
	private String print;
	//initializes instance variables
	//0 is white, 1 is grey, 2 is black
	public Room(int num) {
		number = num;
		neighbors = new ArrayList<>();
		color = 0;
		path = new ArrayList<>();
		print = " ";
	}
	//Returns number
	public int getNumber() {
		return number;
	}
	//Adds r to neighbors
	public void addNeighbor(Room r) {
		neighbors.add(r);
	}
	//returns neighbors
	public ArrayList<Room> getNeighbors(){
		return neighbors;
	}
	//returns color
	public int getColor() {
		return color;
	}
	//sets color to n
	public void setColor(int n) {
		color = n;
	}
	//adds r to path
	public void addPath(Room r) {
		path.add(r);
	}
	//returns path
	public ArrayList<Room> getPath(){
		return path;
	}
	//sets print to n
	public void setPrint(String n) {
		print = n;
	}
	//returns print
	public String getPrint() {
		return print;
	}
}
