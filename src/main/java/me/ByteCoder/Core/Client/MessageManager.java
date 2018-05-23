package me.ByteCoder.Core.Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import me.ByteCoder.Core.Connections.Secure.SPacket;
import me.ByteCoder.Core.Connections.Secure.SPacket.SPacketType;

public class MessageManager {
	
private ObjectOutputStream out;
private ObjectInputStream in;
private Client client;
private boolean authrized;
private boolean connected;
private String name;
private String password;

/*
 * Creating Streams connection.
 */

public MessageManager(Client c) {
	this.client = c;
	try {
		this.out = new ObjectOutputStream(client.getSocket().getOutputStream());
		this.in = new ObjectInputStream(client.getSocket().getInputStream());
		this.connected = true;
	} catch (IOException e) {
		this.connected = false;
	}
}

/*
 * Authorize Core protocol.
 */

public void autorize(String name, String password) {
	if(this.authrized == false) {
		try {
			this.out.writeUTF("B_IO");
			this.out.writeUTF(name);
			this.out.writeUTF(password);
			this.out.flush();
			this.authrized = true;
			this.name = name;
			this.password = password;
		} catch (IOException e) {
			this.connected = true;
		}
	}
}

public String getName() {
	return this.name;
}

public String getPassword() {
	return this.password;
}

public boolean isAuthrized() {
	return this.authrized;
}

public boolean isConnected() {
	return this.connected;
}

/*
 * Disconnecting from Core
 */

public void disconnect() {
	if(this.authrized == true) {
		this.write(new SPacket("B.IO", "DISCONNECT", "EXECUTE", this.name, SPacketType.valueOf(this.client.getType().toString())));
		try {
			this.client.getSocket().close();
		} catch (IOException e) {
			this.connected = false;
		}
		this.connected = false;
	}
}

/*
 * Writing packet to Core
 */

public void write(SPacket packet) {
	if(this.isConnected()) {
		try {
			this.out.writeObject(packet);
			this.out.flush();
			System.out.println("Packet - " + packet.toJson() + ". Sendet to server.");
		} catch (IOException e) {
			this.connected = false;
		}
	}
}

/*
 * Reading packet from Core
 */

public SPacket read() {
	SPacket local = new SPacket();
	
	if(this.isConnected()) {
		try {
			local = (SPacket) this.in.readObject();
		} catch (ClassNotFoundException e) {
			this.connected = false;
		} catch (IOException e) {
			this.connected = false;
		}
	}
	
	return local;
}

}
