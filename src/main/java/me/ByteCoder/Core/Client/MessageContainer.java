package me.ByteCoder.Core.Client;

import me.ByteCoder.Core.Connections.Secure.SPacket;

public abstract interface MessageContainer
{
public abstract void onReceive(SPacket packet);
}