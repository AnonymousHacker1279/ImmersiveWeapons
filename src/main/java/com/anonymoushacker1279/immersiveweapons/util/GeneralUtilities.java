package com.anonymoushacker1279.immersiveweapons.util;

public class GeneralUtilities {

	public static float getRandomNumber(float min, float max) {
	    return (float) ((Math.random() * (max - min)) + min);
	}
	
	public static double getRandomNumber(double min, double max) {
	    return (Math.random() * (max - min)) + min;
	}
	
	public static int getRandomNumber(int min, int max) {
	    return (int) ((Math.random() * (max - min)) + min);
	}
}
