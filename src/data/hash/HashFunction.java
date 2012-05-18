package data.hash;

import java.util.Random;

public class HashFunction {
	private static Random generator = new Random();
	private int exponent;
	private int m;
	private int capacityMask;
	
	
	public int getExponent(){
		return exponent;
	}
	
	public int getModulo(){
		return m;
	}

	public int hash(Object key){
		long base = key.hashCode();
	    long h = 1;
	    int b = exponent;
	    while (b>0){
	    	if ((b&1)==1){
	    		h = (h*base) % m;
	    	}
	    	b = b >> 1;
	    	base = (base * base) % m;
	    }
//	    System.out.println("base:"+key.hashCode()+" exponent:"+exponent+" b:"+b+" m:"+m+" mask:"+capacityMask);
//	    System.out.println("h:"+h+" ret:"+(((int) h) & capacityMask));
	    return ((int) h) & capacityMask;
	}
	
	public static int nextInt(){
		int i = Math.abs(generator.nextInt());
		
		i &= (1 << 31) - 1; //Make sure it's positive.
		i |= 1 << 30; //Make sure it's large.
		i |= 1; //Make sure it's odd.
		
		return i;
	}
	
	public HashFunction(int c){
		this(nextInt(),nextInt(), c);
	}

	public HashFunction(int b, int m, int capacity) {
		this.exponent = b;
		this.m = m;
		this.capacityMask = capacity-1;
	}
	
	public String toString(){
		return "exponent="+exponent+
				", m="+m+
				", mask="+capacityMask;
	}
	
	public static void main(String[] args){
		System.out.println(nextInt());
		System.out.println(nextInt());
		System.out.println(nextInt());
	}
}
