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
	
	public Pixel getTopLeftNeighbour() {
		Pixel neighbour = (Pixel) neighbours.get(0);
		boolean found = false;
		
		if( neighbour.getX() != (x-1) || neighbour.getY() != (y-1) ) {
			for(int i=1; i<neighbours.size(); i++) {
				neighbour = (Pixel) neighbours.get(i);
				if( neighbour.getX() == (x-1) && neighbour.getY() == (y-1) ) {
					found = true;
					break; 
				}
			}
			if(!found)
				return null;
		}
		
		return neighbour;
	}
	
	public Pixel getTopNeighbour() {
		Pixel neighbour = (Pixel) neighbours.get(1);
		boolean found = false;
		
		if( neighbour.getX() != x || neighbour.getY() != (y-1) ) {
			for(int i=0; i<neighbours.size() && i!=1; i++) {
				neighbour = (Pixel) neighbours.get(i);
				if( neighbour.getX() == x && neighbour.getY() == (y-1) ) {
					found = true;
					break; 
				}
			}
			if(!found)
				return null;
		}
		
		return neighbour;
	}
	
	public Pixel getTopRightNeighbour() {
		Pixel neighbour = (Pixel) neighbours.get(2);
		boolean found = false;
		
		if( neighbour.getX() != (x+1) || neighbour.getY() != (y-1) ) {
			for(int i=0; i<neighbours.size() && i!=2; i++) {
				neighbour = (Pixel) neighbours.get(i);
				if( neighbour.getX() == (x+1) && neighbour.getY() == (y-1) ) {
					found = true;
					break; 
				}
			}
			if(!found)
				return null;
		}
		
		return neighbour;
	}
	
	public Pixel getLeftNeighbour() {
		Pixel neighbour;
		boolean found = false;
		
		if(neighbours.size()>3)
			neighbour = (Pixel) neighbours.get(3);
		else
			neighbour = (Pixel) neighbours.lastElement();
		
		if( neighbour.getX() != (x-1) || neighbour.getY() != y ) {
			for(int i=0; i<neighbours.size() && i!=3; i++) {
				neighbour = (Pixel) neighbours.get(i);
				if( neighbour.getX() == (x-1) && neighbour.getY() == y ) {
					found = true;
					break; 
				}
			}
			if(!found)
				return null;
		}
		
		return neighbour;
	}

	public Pixel getRightNeighbour() {
		Pixel neighbour;
		boolean found = false;
		
		if(neighbours.size()>4)
			neighbour = (Pixel) neighbours.get(4);
		else
			neighbour = (Pixel) neighbours.lastElement();
		
		if( neighbour.getX() != (x+1) || neighbour.getY() != y ) {
			for(int i=0; i<neighbours.size() && i!=4; i++) {
				neighbour = (Pixel) neighbours.get(i);
				if( neighbour.getX() == (x+1) && neighbour.getY() == y ) {
					found = true;
					break; 
				}
			}
			if(!found)
				return null;
		}
		
		return neighbour;
	}

	public Pixel getBottomLeftNeighbour() {
		Pixel neighbour;
		boolean found = false;
		
		if(neighbours.size()>5)
			neighbour = (Pixel) neighbours.get(5);
		else
			neighbour = (Pixel) neighbours.lastElement();
		
		if( neighbour.getX() != (x-1) || neighbour.getY() != (y+1) ) {
			for(int i=0; i<neighbours.size() && i!=5; i++) {
				neighbour = (Pixel) neighbours.get(i);
				if( neighbour.getX() == (x-1) && neighbour.getY() == (y+1) ) {
					found = true;
					break; 
				}
			}
			if(!found)
				return null;
		}
		
		return neighbour;
	}

	public Pixel getBottomNeighbour() {
		Pixel neighbour;
		boolean found = false;
		
		if(neighbours.size()>6)
			neighbour = (Pixel) neighbours.get(6);
		else
			neighbour = (Pixel) neighbours.lastElement();
		
		if( neighbour.getX() != x || neighbour.getY() != (y+1) ) {
			for(int i=0; i<neighbours.size() && i!=6; i++) {
				neighbour = (Pixel) neighbours.get(i);
				if( neighbour.getX() == x && neighbour.getY() == (y+1) ) {
					found = true;
					break; 
				}
			}
			if(!found)
				return null;
		}
		
		return neighbour;
	}

	public Pixel getBottomRightNeighbour() {
		Pixel neighbour;
		boolean found = false;
		
		if(neighbours.size()>7)
			neighbour = (Pixel) neighbours.get(7);
		else
			neighbour = (Pixel) neighbours.lastElement();
		
		if( neighbour.getX() != (x+1) || neighbour.getY() != (y+1) ) {
			for(int i=0; i<neighbours.size() && i!=7; i++) {
				neighbour = (Pixel) neighbours.get(i);
				if( neighbour.getX() == (x+1) && neighbour.getY() == (y+1) ) {
					found = true;
					break; 
				}
			}
			if(!found)
				return null;
		}
		
		return neighbour;
	}
	
	public void removeNeighbours() {
		neighbours.clear();
	}
	
	public void resetNeighbours() {
		Pixel n;
		
		for(int i=0; i < neighbours.size(); i++) { 
			n = (Pixel) neighbours.get(i); 
			n.setHeight((byte)0);
		}
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
	
	public final void setHeight(int height) {
		this.height = (byte) height;
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
