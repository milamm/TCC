package image;

import java.util.Vector;

import Watershed.WatershedPixel;

public class Pixel {
	/** Value used to initialise the image */
	final static int INIT = -1;

	/** x coordinate of the pixel **/
	protected int x;
	/** y coordinate of the pixel **/
	protected int y;
	/** grayscale value of the pixel **/
	protected byte height;
	
	protected int label;
	
	/** Neighbours **/
	protected Vector neighbours;

	public Pixel() {}
	
	public Pixel(int x, int y, byte height) {
		this.x = x;
		this.y = y;
		this.height = height;
		label = INIT;
		neighbours = new Vector(8);
	}

	public void addNeighbour(Pixel neighbour) {
		/*
		 * IJ.write("In Pixel, adding :"); IJ.write(""+neighbour);
		 * IJ.write("Add done");
		 */
		neighbours.add(neighbour);
	}

	public Vector getNeighbours() {
		return neighbours;
	}

	public String toString() {
		return new String("(" + x + "," + y + "), height : " + getIntHeight()
				+ ", label : " + label);
	}

	public final byte getHeight() {
		return height;
	}

	public final int getIntHeight() {
		return (int) height & 0xff;
	}
	
	public final void setHeight(byte height) {
		this.height = height;
	}

	public final int getX() {
		return x;
	}

	public final int getY() {
		return y;
	}

	/** Method to be able to use the Collections.sort static method. **/
	/*public int compareTo(Object o) {
		if (!(o instanceof WatershedPixel))
			throw new ClassCastException();

		WatershedPixel obj = (WatershedPixel) o;

		if (obj.getIntHeight() < getIntHeight())
			return 1;

		if (obj.getIntHeight() > getIntHeight())
			return -1;

		return 0;
	}*/

	public void setLabel(int label) {
		this.label = label;
	}

	public void setLabelToINIT() {
		label = INIT;
	}

	public boolean isLabelINIT() {
		return label == INIT;
	}

	public int getLabel() {
		return label;
	}

}
