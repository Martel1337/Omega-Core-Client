package me.ByteCoder.Core.Connections.Secure;

import com.google.gson.Gson;

public class SPacketUtils {

public static boolean isValid(Object o) {
	return o.getClass().equals(SPacket.class);
}
	
public static SPacket fromStringToPacket(String p) {
	return new Gson().fromJson(p, SPacket.class);
}

public static SPacket emptySPacket() {
	return new SPacket();
}

}
