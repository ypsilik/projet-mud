package main;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import maze.CopyOfMaze;
import maze.Direction;
import maze.Piece;
import maze.Player;
import maze.Position;

public class ServerImplementation extends UnicastRemoteObject implements ServerInterface {

	private static final int OUT_X = 4;
	private static final int OUT_Y = 3;
	private CopyOfMaze maze;
	
	protected ServerImplementation() throws RemoteException {
		super();
		maze = new CopyOfMaze();
	}
	
	public String game(String nomCli, String direction) {
		//maze.AllPlayerPiece();
		Player gamer = maze.getPlayer(nomCli);
		Piece p1 = maze.getPlayerPiece(gamer);
		int[] possibility = maze.getNeighbour(p1);
		if ((direction.equalsIgnoreCase("N") && possibility[0] == 0) || 
				(direction.equalsIgnoreCase("E") && possibility[1] == 0) || 
				(direction.equalsIgnoreCase("S") && possibility[2] == 0) ||	
				(direction.equalsIgnoreCase("O") && possibility[3] == 0)) {
			maze.walkPlayer(gamer,Direction.valueOf(direction));
			Piece piece2 = maze.getPlayerPiece(gamer);
			if (piece2.getPosition().getX() == OUT_X && piece2.getPosition().getY() == OUT_Y) {
				return "End of Maze";
			}
			return this.displayNeighbour(piece2) + "\n" + "Choose direction [N/E/S/O] : ";
		} else {
			Piece piece = maze.getPlayerPiece(gamer);
			return this.displayNeighbour(piece) + "\n" + "La direction choisie n'est pas possible" + "\n" + "Choose direction [N/E/S/O] : ";
		}
	}

	public void createUser(String username) {
		maze.addPlayer(username);
	} 
	
	private String displayNeighbour(Piece p1) {
		int[] possibility = maze.getNeighbour(p1);
		return "   " + possibility[0] + "\n" + possibility[3] + "     " + possibility[1] + "\n" + "   " + possibility[2];
	}
		
}
