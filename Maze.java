package WuNg.cs146.project3;

import java.util.*;

public class Maze {
	private ArrayList<Room> adList;
	public int rows;
	public int size;
	//Constructor
 	public Maze(int rows) {
		this.rows = rows;
		size = rows*rows;
		adList = new ArrayList<>();
		//adds size rooms into adlist
		for (int i = 0; i < size; i++) {
			adList.add(new Room(i));
		}
		//sets the neighbors for each room
		for (int i = 0; i < size; i++) {
			Room temp = adList.get(i);
			//adds a neighbor if there is a room to the left
			if (i % rows != 0) {
				temp.addNeighbor(adList.get(i-1));
			}
			//adds a neighbor if there is a room above
			if (i - rows >= 0) {
				temp.addNeighbor(adList.get(i-rows));
			}
			//adds a neighbor if there is a room to the right
			if (i % rows != rows - 1) {
				temp.addNeighbor(adList.get(i+1));
			}
			//adds a neighbor if there is a room below
			if (i + rows < size) {
				temp.addNeighbor(adList.get(i+rows));
			}
		}
	}
 	//makes every room in adlist become white and print " "
	public void makeBlank() {
		for (int i = 0; i < size; i++) {
			adList.get(i).setPrint(" ");
			adList.get(i).setColor(0);
		}
	}
	//generates maze
	public void Generate(int seed) {
		Random r = new Random();
		r.setSeed(seed);
		//Stack for DFS 
		Stack<Room> rooms = new Stack<>();
		int visitedRooms = 1;
		Room currentRoom = adList.get(0);
		//while stops when all rooms are visited
		while (visitedRooms < size) {
			//sets current room to grey
			currentRoom.setColor(1);
			ArrayList<Room> neighbors = currentRoom.getNeighbors();
			ArrayList<Room> whiteNeighbors = new ArrayList<>();
			//finds the neighbors that are white
			for (int i = 0; i < neighbors.size(); i++) {
				if (neighbors.get(i).getColor() == 0) {
					whiteNeighbors.add(neighbors.get(i));
				}
			}
			//there is at least 1 white neighbor
			if (whiteNeighbors.size() > 0) {
				//number is generated from 0 to size of whiteNeighbors
				int ranNum = r.nextInt(whiteNeighbors.size());
				Room random = whiteNeighbors.get(ranNum);
				//adds path from currentROom to random
				currentRoom.addPath(random);
				random.addPath(currentRoom);
				//Push current room into stack
				rooms.push(currentRoom);
				currentRoom = random;
				visitedRooms++;
			}
			//no white neighbors
			else {
				//sets currentRoom to black
				currentRoom.setColor(2);
				//pops stack to backtrack
				currentRoom = rooms.pop();
			}
		}
	}
	//solving maze with BFS 
	public String BFS() {
		//makes every room white again
		makeBlank();
		Room currentRoom = adList.get(0);
		currentRoom.setColor(1);
		//queue for BFS
		Queue<Room> rooms = new LinkedList<>();
		//map needed to find shortest path
		Map<Room, Room> shortPath = new HashMap<>();
		//adds currentRoom to queue
		rooms.add(adList.get(0));
		int counter = 0;
		//sets first room to print 0
		currentRoom.setPrint(0 +"");
		currentRoom.setColor(1);
		//loop exits when currentRoom reaches final room
		while (currentRoom != adList.get(size-1)) {
			//sets current room to first element in queue
			currentRoom = rooms.remove();
			ArrayList<Room> path = currentRoom.getPath();
			for (int i = 0; i < path.size(); i++) {
				//checks for white path
				if (path.get(i).getColor() == 0) {
					counter++;
					Room nextRoom = path.get(i);
					//checks to see if nextRoom is final room
					if (nextRoom.getNumber() == size-1) {
						//sets print for nextRoom
						nextRoom.setPrint((counter%10) + "");
						shortPath.put(nextRoom, currentRoom);
						//exits loop with break and leaves while by setting currentRoom to final room
						currentRoom = path.get(i);
						break;
					}
					else {
						//sets print as the counter
						nextRoom.setPrint((counter%10) + "");
						//makes room grey
						nextRoom.setColor(1);
						rooms.add(nextRoom);
						//makes the currentRoom the parent of nextRoom
						shortPath.put(nextRoom, currentRoom);
					}
				}
			}
			//makes currentRoom black
			currentRoom.setColor(2);
		}
		//prints the order in which BFS solved the maze
		String print = "BFS: \n";
		print += printMaze();
		print += "\n\n";
		//make blank to reset print for every room
		makeBlank();
		int shortCounter = 0;
		Room tempRoom = adList.get(size - 1);
		//traverses the map by finding the parents of the final room until it reaches the first room
		while(tempRoom != adList.get(0)) {
			tempRoom.setPrint("#");
			tempRoom = shortPath.get(tempRoom);
			shortCounter++;
		}
		//prints shortest path
		adList.get(0).setPrint("#");
		print += printMaze();
		print += "\nVisited cells: " + (counter + 1) + "\n";
		print += "Length of path: " + (shortCounter + 1);
		return print;
	}
	//solves maze with DFS
	public String DFS() {
		//makes all rooms white
		makeBlank();
		Room currentRoom = adList.get(0);
		currentRoom.setColor(1);
		//stack for DFS
		Stack<Room> rooms = new Stack<>();
		int counter = 0;
		//loop ends when reaches final room
		while (currentRoom != adList.get(size-1)) {
			currentRoom.setColor(1);
			//checks to see if print is blank in case it gets backtracked to
			if (currentRoom.getPrint() == " ") {
				currentRoom.setPrint((counter%10) + "");
			}
			ArrayList<Room> path = currentRoom.getPath();
			Room nextRoom = null;
			//finds next white room
			for (int i = 0; i < path.size(); i++) {
				if (path.get(i).getColor() == 0) {
					nextRoom = path.get(i);
					break;
				}
			}
			//white room is found
			if (nextRoom != null) {
				//pushes currentRoom into stack
				rooms.push(currentRoom);
				//sets currentRoom to nextRoom
				currentRoom = nextRoom;
				counter++;
			}
			//no white room is found
			else {
				//sets color to black and pops from stack
				currentRoom.setColor(2);
				currentRoom = rooms.pop();
			}
		}
		//sets print of final room to counter
		adList.get(size-1).setPrint((counter%10) + "");
		//prints how DFS searched for final room
		String print = "DFS: \n";
		print += printMaze();
		print += "\n\n";
		//makes print of every room blank
		makeBlank();
		int shortCounter = 0;
		//sets the shortest path to "#"
		//shortest path is in the stack 
		for (Room r : rooms) {
			r.setPrint("#");
			shortCounter++;
		}
		//prints shortest path
		adList.get(size-1).setPrint("#");
		print += printMaze();
		print += "\nVisited cells: " + (counter + 1) + "\n";
		print += "Length of path: " + (shortCounter + 1);
		return print;
	}
	public void printAdList() {
		for (int i = 0; i < size; i++) {
			Room temp = adList.get(i);
			System.out.print(i + ": ");
			for (int j = 0; j < temp.getPath().size(); j++) {
				System.out.print(temp.getPath().get(j).getNumber() + " ");
			}
			System.out.println();
		}
	}
	public String printMaze() {
		//prints first row of walls
		//There is opening in the first room
		String maze = "+ ";
		for (int i = 0; i < rows - 1; i++){
			maze = maze + "+-";
		}
		maze = maze + "+\n";
		//prints the rest of the maze
		for (int i = 0; i < rows; i++) {
			//prints first wall of the line
			maze += "|";
			for (int j = i*rows; j < i*rows + rows; j++) {
				Room currentRoom = adList.get(j);
				String printChar = currentRoom.getPrint();
				ArrayList<Room> paths = currentRoom.getPath();
				//checks if there is a path to the right of the room
				boolean foundPath = false;
				for (int k = 0; k < paths.size(); k++) {
					if (paths.get(k).getNumber() == currentRoom.getNumber() + 1) {
						foundPath = true;
						k = paths.size();
					}
				}
				//if there is path then blank space
				if (foundPath) {
					maze += printChar + " ";
				}
				//prints wall if there is no path
				else  {
					maze += printChar + "|";
				}
			}
			maze += "\n";
			//checks to see if its very last set of corners and walls
			if (i == rows - 1) {
				for (int j = 0; j < rows - 1; j++) {
					maze += "+-";
				}
				maze += "+ +";
			}
			else {
				for (int j = i*rows; j < i*rows + rows; j++) {
					Room currentRoom = adList.get(j);
					ArrayList<Room> paths = currentRoom.getPath();
					//checks if there is path below the room
					boolean foundPath = false;
					for (int k = 0; k < paths.size(); k++) {
						if (paths.get(k).getNumber() == currentRoom.getNumber() + rows) {
							foundPath = true;
							k = paths.size();
						}
					}
					//if there is a path then there is blank space below room
					if (foundPath) {
						maze += "+ ";
					}
					//if no path then there is a wall
					else  {
						maze += "+-";
					}
				}
				maze += "+\n";
			}
		}
		return maze;
	}
	public static void main(String [] args) {
		Maze m = new Maze(4);
		m.Generate(0);
		System.out.println(m.printMaze());
		System.out.println(m.BFS());
		System.out.println(m.DFS());
	}
}
