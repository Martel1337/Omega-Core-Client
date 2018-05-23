package me.ByteCoder.Core.Client.Types;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import me.ByteCoder.Core.Client.Client;
import me.ByteCoder.Core.Client.MessageContainer;
import me.ByteCoder.Core.Client.MessageManager;
import me.ByteCoder.Core.Connections.Secure.SPacket;

public class ClientListener extends Client {

private String h;
private Integer p;
private Socket s;
private MessageManager mm;
private List<MessageContainer> listeners;

public ClientListener(String host, Integer port) {
	this.h = host;
	this.p = port;
	this.listeners = new ArrayList<MessageContainer>();
}

public Socket getSocket() {
	return this.s;
}

public boolean isConnected() {
	return this.s.isClosed();
}

public void disconnect() {
	if(this.mm != null) {
		this.mm.disconnect();
	}
}

public void makeListener(MessageContainer mess) {
	if(mess != null && !this.listeners.contains(mess)) {
		this.listeners.add(mess);
	}
}

public void startReadingThread() {
	new Thread(new Runnable() {

		public void run() {
			while(mm.isConnected()) {
				SPacket packet = new SPacket();
				if(!s.isClosed() && (packet = read()) != null) {
					for(MessageContainer messages : listeners) {
						messages.onReceive(packet);
					}
				}
			}
		}
		
	}).start();
}

@Override
public void write(SPacket packet) {
	this.mm.write(packet);
}

@Override
public SPacket read() {
	if(this.mm == null) {
		return new SPacket();
	}else {
		return this.mm.read();
	}
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
	return Type.LISTENER;
}

}
