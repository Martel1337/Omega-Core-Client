package me.ByteCoder.Core.Client;

import java.net.Socket;

import me.ByteCoder.Core.Connections.Secure.SPacket;

public abstract class Client {

public abstract Socket getSocket();
public abstract void write(SPacket packet);
public abstract SPacket read();
public abstract MessageManager getMessageManager();
public abstract Type getType();

public static enum Type{HANDSHAKE, LISTENER;}

}
