package com.math;

public class MathUtil {
	public static float DegToRad(float angle) {
		return (float)(angle * Math.PI / 180.0);
	}
	
	public static float RadToDeg(float rads) {
		return (float)(rads * 180.0 / Math.PI);
	}
	
	public static final float EPSILON3 = (float)(1E-3);
	public static final float EPSILON4 = (float)(1E-4);
	public static final float EPSILON5 = (float)(1E-5);
	public static final float EPSILON6 = (float)(1E-6);
}
