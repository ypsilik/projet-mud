package main;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import chat.ChatServerImpl;
import fight.ServerFightImpl;
import maze.ServerGameImplementation;

// Main

public class Main{
public static void main(String[] args) {
		try{
			ServerGameImplementation gameSrvImpl = new ServerGameImplementation(); // Start game
			ChatServerImpl chatSrvImpl = new ChatServerImpl(gameSrvImpl); // Start chat
			ServerFightImpl fightSrvImpl = new ServerFightImpl(); // Start Fight

			Registry registry = LocateRegistry.createRegistry(1099); // Create registry
			Naming.rebind("Game", gameSrvImpl); // Expose game
			Naming.rebind("ChatServer", chatSrvImpl); // Expose chat
			Naming.rebind("FightServer", fightSrvImpl); // Expose fight
			System.err.println("Servers ready");
		} catch (Exception e) {
			System.err.println("Server exception: " + e.toString());
			e.printStackTrace();
		}
	}
  }
