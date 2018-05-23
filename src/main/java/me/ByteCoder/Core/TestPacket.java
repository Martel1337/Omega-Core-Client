package me.ByteCoder.Core;

import me.ByteCoder.Core.Client.MessageContainer;
import me.ByteCoder.Core.Client.MessageManager;
import me.ByteCoder.Core.Client.Types.ClientHandshake;
import me.ByteCoder.Core.Client.Types.ClientListener;
import me.ByteCoder.Core.Connections.Secure.SPacket;
import me.ByteCoder.Core.Connections.Secure.SPacket.SPacketType;

public class TestPacket {

	public static void main(String[] args) {
		ClientHandshake client = new ClientHandshake("localhost", 8888);
		
		MessageManager mm = client.getMessageManager();
		
		mm.autorize("TestClient-Handshake", "05jaFpJizPse");
		
		System.out.println(client.makeHandshake(new SPacket("DATABASE", "GET Profiles ByteCoder_ skin", "GET", "EXECUTE", SPacketType.HANDSHAKE)).getData());
	
	
		ClientListener cl = new ClientListener("localhost", 8888);
		
		MessageManager ml = cl.getMessageManager();
		
		ml.autorize("TestClient-Handshake", "05jaFpJizPse");
		
		cl.makeListener(new MessageContainer() {

			public void onReceive(SPacket packet) {
				if(packet.getType().equals("HELLO")) {
					if(packet.getAction().equals("WORLD")) {
						System.out.println("Data: " + packet.getData());
					}
				}
			}
			
		});
		
		cl.startReadingThread();
	}

}
