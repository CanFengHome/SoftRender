package com.sharp;

public class Color3B {
	private byte r;
	private byte g;
	private byte b;
	
	public Color3B(byte r, byte g, byte b) {
		this.r = r;
		this.g = g;
		this.b = b;
	}
	
	public byte getR() {
		return r;
	}
	public byte getG() {
		return g;
	}
	public byte getB() {
		return b;
	}
	
	public static final Color3B WHITE = new Color3B((byte)255, (byte)255, (byte)255);
	public static final Color3B BLACK = new Color3B((byte)0, (byte)0, (byte)0);
	public static final Color3B RED = new Color3B((byte)255, (byte)0, (byte)0);
	public static final Color3B GREEN = new Color3B((byte)0, (byte)255, (byte)0);
	public static final Color3B BLUE = new Color3B((byte)0, (byte)0, (byte)255);
}
