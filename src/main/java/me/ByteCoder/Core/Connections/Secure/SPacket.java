package me.ByteCoder.Core.Connections.Secure;

import java.io.Serializable;

import com.google.gson.Gson;

public class SPacket implements Serializable {

public static final long serialVersionUID = 7695L;
private final String type;
private final String action;
private final Object data;
private final String target;
private final SPacketType ptype;
private String cryptopass;
private String clientpass;
	
public SPacket(String type, String action, Object data, String target, SPacketType ptype) {
	this.type = type;
	this.action = action;
	this.data = data;
	this.target = target;
	this.ptype = ptype;
}

public SPacket() {
	this.type = null;
	this.action = null;
	this.data = null;
	this.target = null;
	this.ptype = null;
}

public String getType() {
	return this.type;
}

public String getAction() {
	return this.action;
}

public Object getData() {
	return this.data;
}

public String getTarget() {
	return this.target;
}

public SPacketType getPacketType() {
	return this.ptype;
}

public String toJson() {
	return new Gson().toJson(this);
}

public static enum SPacketType {HANDSHAKE, LISTENER};
}
