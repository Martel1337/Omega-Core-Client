package me.ByteCoder.Core.Client.Types;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import me.ByteCoder.Core.Client.Client;
import me.ByteCoder.Core.Client.MessageManager;
import me.ByteCoder.Core.Connections.Secure.SPacket;

public class ClientHandshake extends Client {

private String h;
private Integer p;
private Socket s;
private MessageManager mm;
	
public ClientHandshake(String host, Integer port) {
	this.h = host;
	this.p = port;
}

@Override
public Socket getSocket() {
	return this.s;
}

public SPacket makeHandshake(SPacket packet) {
	this.write(packet);
	
	return this.read();
}

@Override
public void write(SPacket packet) {
	this.mm.write(packet);
}

@Override
public SPacket read() {
	return this.mm.read();
}

@Override
public MessageManager getMessageManager() {
	if(this.mm == null) {
		try {
			this.s = new Socket(this.h, this.p);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		MessageManager m = new MessageManager(this);
		
		this.mm = m;
	}
		
	return this.mm;
}

@Override
public Type getType() {
	return Type.HANDSHAKE;
}
	
}
