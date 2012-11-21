package Watershed;

/*
 * Watershed algorithm
 *
 * Copyright (c) 2003 by Christopher Mei (christopher.mei@sophia.inria.fr)
 *
 * This plugin is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 2 
 * as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this plugin; if not, write to the Free Software
 * Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 */

import java.lang.*;
import java.util.*;

import ij.process.*;
import ij.*;
import image.Matrix;
import image.Pixel;

import java.awt.*;

/**
 * WatershedStructure contains the pixels of the image ordered according to
 * their grayscale value with a direct access to their neighbours.
 * 
 **/

public class WatershedStruct {
	private Vector<WatershedPixel> watershedStructure;
	private Vector<WatershedPixel> minimaPixels;      // pixels at minima inside basins
	private Vector<WatershedPixel> watershedPixels; 
	private ByteProcessor minimaImage;
	private ByteProcessor watershedImage;
	private ImagePlus minImPlus;
	private ImagePlus watImPlus;

	public                                 		WatershedStruct(ImageProcessor ip) {
		byte[] pixels = (byte[]) ip.getPixels();
		Rectangle r = ip.getRoi();
		int width = ip.getWidth();
		int offset, topOffset, bottomOffset, i;

		watershedStructure = new Vector(r.width * r.height);
		watershedPixels = new Vector();
		minimaPixels = new Vector();
		minimaImage = new ByteProcessor(width, ip.getHeight());
		watershedImage = new ByteProcessor(width, ip.getHeight());
		minImPlus = new ImagePlus("Minima", minimaImage);
		watImPlus = new ImagePlus("Watershed", watershedImage);

		/** The structure is filled with the pixels of the image. **/
		for (int y = r.y; y < (r.y + r.height); y++) {
			offset = y * width;

			System.out.println("Progress: "+(0.1 + 0.3 * (y - r.y) / (r.height)));

			for (int x = r.x; x < (r.x + r.width); x++) {
				i = offset + x;

				int indiceY = y - r.y;
				int indiceX = x - r.x;

				watershedStructure.add(new WatershedPixel(indiceX, indiceY,
						pixels[i]));
			}
		}

		/**
		 * The WatershedPixels are then filled with the reference to their
		 * neighbours.
		 **/
		for (int y = 0; y < r.height; y++) {

			offset = y * width;
			topOffset = offset + width;
			bottomOffset = offset - width;

			System.out.println("Progress: "+(0.4 + 0.3 * (y - r.y) / (r.height)));

			for (int x = 0; x < r.width; x++) {
				WatershedPixel currentPixel = (WatershedPixel) watershedStructure
						.get(x + offset);

				if (x + 1 < r.width) {
					currentPixel
							.addNeighbour((WatershedPixel) watershedStructure
									.get(x + 1 + offset));

					if (y - 1 >= 0)
						currentPixel
								.addNeighbour((WatershedPixel) watershedStructure
										.get(x + 1 + bottomOffset));

					if (y + 1 < r.height)
						currentPixel
								.addNeighbour((WatershedPixel) watershedStructure
										.get(x + 1 + topOffset));
				}

				if (x - 1 >= 0) {
					currentPixel
							.addNeighbour((WatershedPixel) watershedStructure
									.get(x - 1 + offset));

					if (y - 1 >= 0)
						currentPixel
								.addNeighbour((WatershedPixel) watershedStructure
										.get(x - 1 + bottomOffset));

					if (y + 1 < r.height)
						currentPixel
								.addNeighbour((WatershedPixel) watershedStructure
										.get(x - 1 + topOffset));
				}

				if (y - 1 >= 0)
					currentPixel
							.addNeighbour((WatershedPixel) watershedStructure
									.get(x + bottomOffset));

				if (y + 1 < r.height)
					currentPixel
							.addNeighbour((WatershedPixel) watershedStructure
									.get(x + topOffset));
			}
		}

		Collections.sort(watershedStructure);
		// IJ.showProgress(0.8);
	}
	
	public void                 	            addMinimaPixel(WatershedPixel p) {
		//p.removeNeighbours();
		minimaPixels.add(p);
		watershedImage.putPixel(p.getX(), p.getY(), 0);
		minimaImage.putPixel(p.getX(), p.getY(), 255);
	}
	
	public void                		            addMinimaPixelNeighbour(WatershedPixel p, WatershedPixel p_aux) {
		WatershedPixel min_p;
		int label = p_aux.getLabel();
		Vector neighbours;
		int index;
		//boolean exists = false;
		
		for(int i=0; i < minimaPixels.size(); i++) {
			min_p = (WatershedPixel) minimaPixels.get(i);
			neighbours = min_p.getNeighbours();
			index = neighbours.indexOf(p);
			if(index != -1) {
				neighbours.remove(index);
			}
		}
		
		//if(!exists) {
			if(label != 0) {
				for(int i=0; i < minimaPixels.size(); i++) {
					min_p = (WatershedPixel) minimaPixels.get(i);
					if(min_p.getLabel() == label)
						min_p.addNeighbour(p);
				} 
			} else {
				label = -1;
				for(int i=0; i < minimaPixels.size(); i++) {
					min_p = (WatershedPixel) minimaPixels.get(i);
					neighbours = min_p.getNeighbours();
					index = neighbours.indexOf(p_aux);
					if(index != -1) {
						min_p.addNeighbour(p);
						break;
					}
				}
			}
			
			buildMinimaImage();
		//}
		
	}
	
	public void                        			addMinimaPixelNeighbour(WatershedPixel p, int label) {
		WatershedPixel min_p = null, cur_min_p = null, nextWat_p;
		Vector neighbours;
		double dist, min_dist = 10000;
		int index;
		//boolean exists = false;
		
		for(int i=0; i < minimaPixels.size(); i++) {
			min_p = (WatershedPixel) minimaPixels.get(i);
			neighbours = min_p.getNeighbours();
			index = neighbours.indexOf(p);
			if(index != -1) {
				neighbours.remove(index);
			}
		}
		
		//if(!exists) {
		if(label != -1) {
			for(int i=0; i < minimaPixels.size(); i++) {
				min_p = (WatershedPixel) minimaPixels.get(i);
				if(min_p.getLabel() == label) {
					min_p.addNeighbour(p);
					p.setMinimaPixelAssociated(min_p);
					break;
				}
			} 
		} else {
			nextWat_p = p.searchNearestWatershedPixel();
			min_p = nextWat_p.getMinimaPixelAssociated();
			/*for(int i=0; i < minimaPixels.size(); i++) {
				min_p = (WatershedPixel) minimaPixels.get(i);
				dist = Math.pow(min_p.getX() - p.getX(),2) + Math.pow(min_p.getY() - p.getY(),2);
				if(dist < min_dist) {
					min_dist = dist;
					cur_min_p = min_p;
				}
			}*/
			min_p.addNeighbour(p);
			p.setMinimaPixelAssociated(min_p);
		}
		minimaImage.putPixel(p.getX(), p.getY(), (256/50)*min_p.getLabel());
		
		//showMinimaImage();
		//}
		
	}
	
	public void                        			addWatershedPixel(WatershedPixel p) {
		if(!watershedPixels.contains(p)) {
			//p.setHeight(255);
			watershedPixels.add(p);
			watershedImage.putPixel(p.getX(), p.getY(), 255);
		}
	}

	public ByteProcessor               		    buildMinimaImage() {
		//ByteProcessor minIm = new ByteProcessor(width,height);
		WatershedPixel min_p, neig;
		Vector neighbours;
		
		/*for(int i=0; i < watershedPixels.size(); i++) {
			neig = watershedPixels.get(i);
			min_p = neig.getMinimaPixelAssociated();
			minimaImage.putPixel(neig.getX(), neig.getY(), (255/minimaPixels.size())*(min_p.getLabel()+1));
		}*/
		
		for(int i=0; i < minimaPixels.size(); i++) {
			min_p = (WatershedPixel) minimaPixels.get(i);
			minimaImage.putPixel(min_p.getX(), min_p.getY(), 255);
			
			neighbours = min_p.getNeighbours();
			
			for(int j=0; j < neighbours.size(); j++) {
				neig = (WatershedPixel)neighbours.get(j);
				minimaImage.putPixel(neig.getX(), neig.getY(), (255/minimaPixels.size())*(min_p.getLabel()+1));
			}
			
		}
		
		return minimaImage;
	}
	
	public double 								calculateRBCDiameter(Pixel minimaPixel) {
		int x, min_x = 10000, max_x = 0;
		int y, min_y = 10000, max_y = 0;
		double h_diam, v_diam;
		//int y, min_y = 10000, max_y = 0;
		Vector neigs = minimaPixel.getNeighbours();
		Pixel p, left_p = null, right_p = null, top_p = null, bottom_p = null;
		
		for(int i=0; i < neigs.size(); i++) {
			p = (Pixel) neigs.get(i);
			x = p.getX();
			y = p.getY();
			if(x < min_x) {
				min_x = x;
				left_p = p;
			}
			if(x > max_x) {
				max_x = x;
				right_p = p;
			}
			if(y < min_y) {
				min_y = y;
				top_p = p;
			}
			if(y > max_y) {
				max_y = y;
				bottom_p = p;
			}
		}
		
		h_diam = Math.sqrt( Math.pow(right_p.getX() - left_p.getX(), 2) + Math.pow(right_p.getY() - left_p.getY(), 2) );
		v_diam = Math.sqrt( Math.pow(bottom_p.getX() - top_p.getX(), 2) + Math.pow(bottom_p.getY() - top_p.getY(), 2) );
		
		if(h_diam>v_diam)
			return h_diam;
		else 
			return v_diam;
	}
	
	public ArrayList<ArrayList<WatershedPixel>> findConnectedComponents() {
		int w = watershedImage.getWidth(), h =  watershedImage.getHeight();
		//byte pixelValue;
		WatershedPixel currentPixel;
		Vector<WatershedPixel> watPixels = (Vector<WatershedPixel>) watershedPixels.clone(); 
		int offset, curPixelValue, label = 0, count;
		int[] neighboursLabels = new int[4];
		int neigL, curL = 0;
		Matrix equivalenceMatrix = null; 
		ArrayList<WatershedPixel> connectedComp;
		//ImageProcessor labelIm = imageproc.createProcessor(w, h);
		ArrayList<ArrayList<WatershedPixel>> connectedComps = new ArrayList<ArrayList<WatershedPixel>>();
		
		//ArrayList<Pixel> imagePixels = imagePixels(imageproc);
		
		// find connected pixels
		//new ImagePlus("Labels Image", labelIm).show();
		//for (int y = 0; y < h; y++) {

			//offset = y * w;

			//for (int x = 0; x < w; x++) {
		int i = 0;
		while(watPixels.size() > 0) {
			//currentPixel = imagePixels.get(x + offset);
			currentPixel  = watPixels.get(0);
			
			connectedComp = new ArrayList<WatershedPixel>(); 
			connectedComps.add(connectedComp);
			searchWatershedNeighbours(currentPixel, connectedComp, watPixels);
			
//			curPixelValue = currentPixel.getIntHeight();
			
			/*count = 0;
			
			Pixel topLeftNeig   = currentPixel.getTopLeftNeighbour();
			Pixel topNeig       = currentPixel.getTopNeighbour();
			Pixel topRightNeig  = currentPixel.getTopRightNeighbour();
			Pixel leftNeig      = currentPixel.getLeftNeighbour();
			
			if(topLeftNeig != null)  neighboursLabels[0] = topLeftNeig.getLabel();
			else neighboursLabels[0] = WatershedPixel.INIT;
			if(topNeig != null)      neighboursLabels[1] = topNeig.getLabel();
			else neighboursLabels[1] = WatershedPixel.INIT;
			if(topRightNeig != null) neighboursLabels[2] = topRightNeig.getLabel();
			else neighboursLabels[2] = WatershedPixel.INIT;
			if(leftNeig != null)     neighboursLabels[3] = leftNeig.getLabel();
			else neighboursLabels[3] = WatershedPixel.INIT;
			
			for(int i=0; i<4; i++) {
				neigL = neighboursLabels[i]; 
			
				if(neigL != WatershedPixel.INIT) {
					count++;
					curL = neigL;
					for(int j=0; j<i; j++) 
						if(neigL == neighboursLabels[j]) {
							count--;
							break;
						}
				}
			}	
			
			if(count == 0) {                                 // all neighbours are from background
				currentPixel.setLabel(label);
				connectedComp = new ArrayList<Pixel>();
				connectedComp.add(currentPixel);
				connectedComps.add(label, connectedComp);
				//labelIm.putPixelValue(x, y, (label+1)*10);
				label++;
			} /*else if(count == 1) {                          // only one neighbour is labeled
				currentPixel.setLabel(curL);
				labelIm.putPixelValue(x, y, (curL+1)*10);
			}*/ /*else {
				currentPixel.setLabel(curL);
				//labelIm.putPixelValue(x, y, (curL+1)*10);
				
				connectedComp = connectedComps.get(curL);
				if(connectedComp==null) {
					connectedComp = new ArrayList<Pixel>();
				}
				connectedComp.add(currentPixel);
				
				if(count > 1) {
					if(equivalenceMatrix == null)
						equivalenceMatrix = new Matrix(label, label, false);
					
					for(int i=0; i<4; i++) {
						neigL = neighboursLabels[i];
						
						if(neigL != WatershedPixel.INIT)
							for(int j=0; j<4; j++) 
								if(neighboursLabels[j] != WatershedPixel.INIT)
									//set equivalence Matrix 
									equivalenceMatrix.set(neigL, neighboursLabels[j], true);
					}
				}
			}*/
		}
		//}
		
	//	showConnectedComponents(connectedComps,w,h);
		
		//add reflexivity
		/*for(int i=0; i<equivalenceMatrix.getHeight(); i++)
			equivalenceMatrix.set(i, i, true);
		
		//add transitive closure
		for(int i=0; i<equivalenceMatrix.getHeight(); i++) {
			for(int j=0; j<equivalenceMatrix.getHeight(); j++) {
				if(equivalenceMatrix.get(j,i).equals(true)) 
					for(int k=0; k<equivalenceMatrix.getHeight(); k++)
						equivalenceMatrix.set(j, k, (Boolean) equivalenceMatrix.get(i, k) || (Boolean) equivalenceMatrix.get(j,k));
			}
		}
		
		//resolve equivalences
		int eqL = -1;
		/*for (int y = 0; y < h; y++) {

			offset = y * w;

			for (int x = 0; x < w; x++) {	
				eqL = -1;
				currentPixel = imagePixels.get(x + offset);
				curL = currentPixel.getLabel();
				for(int i=curL-1; i>=0; i--)
					if(equivalenceMatrix.get(curL, i).equals(true))
						eqL = i;
				if(eqL!=-1) {
					currentPixel.setLabel(eqL);
					labelIm.putPixelValue(x, y, (eqL+1)*10);
				}
			}
		}*/
		/*for(int k = 0; k < connectedComps.size(); k++) {
			eqL = -1;
			connectedComp = connectedComps.get(k); 
			if(connectedComp != null) {
				curL = connectedComp.get(0).getLabel();
				for(int i = curL-1; i >= 0; i--)
					if(equivalenceMatrix.get(curL, i).equals(true))
						eqL = i;
			}
			if(eqL!=-1) {
				connectedComps.get(eqL).addAll(connectedComp);
				connectedComps.set(k,null);
			} 
		}
		
		// organize labels
		int k = 0;
		while( k < connectedComps.size() ) {
			connectedComp = connectedComps.get(k);
			if(connectedComp == null)
				connectedComps.remove(k);
			else {
				for(int i=0; i < connectedComp.size(); i++) {
					currentPixel = connectedComp.get(i);
					currentPixel.setLabel(k);
				}
				k++;
			}
		}*/
		
		showConnectedComponents(connectedComps,w,h);
		
		return connectedComps;
	}
	
	public WatershedPixel              			get(int i) {
		return (WatershedPixel) watershedStructure.get(i);
	}
	
	public WatershedPixel              			getMinimaPixel(int label) {
		WatershedPixel min_p;
		
		for(int i=0; i < minimaPixels.size(); i++) {
			min_p = (WatershedPixel) minimaPixels.get(i);
			
			if(min_p.getLabel() == label) {
				return min_p;
			}
		}
		return null;
	}
	
	public Vector         			   			getMinimaPixels() {
		return minimaPixels;
	}
	
	public ImageProcessor 		   	   			getWatershedImage() {
		return watershedImage;
	}
	
	public Vector           		   			getWatershedPixels() {
		return watershedPixels;
	}
	
	public Vector         			   			getWatershedStructureVector() {
		return watershedStructure;
	}
	
	public void									putPixelWatershedImage(WatershedPixel r) {
		watershedImage.putPixel(r.getX(), r.getY(), 128/*r.getLabel()*256/50*/);
		
	}
	
	/*public void putWatershedPixel(WatershedPixel p) {
		watershedImage.putPixel(p.getX(), p.getY(), 255);
		
	}*/
	
	public void 								removeMinimaPixel(int k) {
		Pixel minimaPixel = minimaPixels.get(k);
		Vector neigs = minimaPixel.getNeighbours();
		WatershedPixel p;
		
		for(int i=0; i < neigs.size(); i++) {
			p = (WatershedPixel) neigs.get(i);
			if(p.isLabelWSHED())
				removeWatershedPixel(p);
		}
		
		minimaPixels.remove(minimaPixel);
		minimaImage.putPixel(minimaPixel.getX(), minimaPixel.getY(), 0);
		
	}
	
	public void  	         		   			removeMinimaPixel(WatershedPixel minimaPixel)  {
		Vector neigs = minimaPixel.getNeighbours();
		WatershedPixel p;
		
		for(int i=0; i < neigs.size(); i++) {
			p = (WatershedPixel) neigs.get(i);
			if(p.isLabelWSHED())
				removeWatershedPixel(p);
		}
		
		minimaPixels.remove(minimaPixel);
		minimaImage.putPixel(minimaPixel.getX(), minimaPixel.getY(), 0);
	}
	
	public void  	         		   			removeMinimaPixelsAtBorder(WatershedPixel minimaPixel) {
		WatershedPixel p;
		Vector neigs = minimaPixel.getNeighbours();
		//int count = 0;
		
		for(int i=0; i<neigs.size(); i++) {
 			p = (WatershedPixel) neigs.get(i); 
			if(p.isLabelWSHED()) {
				removeWatershedAndMinimaNeighbours(p/*,count*/);
				break;
			}
		}
		minimaPixels.remove(minimaPixel);		
		//return count;
	}

	public void         			   			removeWatershedAndMinimaNeighbours(WatershedPixel p/*,int count*/) {
		/*if(*/removeWatershedPixelAndMinimaPixelAss(p);
			//count++;
		
		Vector neigs = p.getNeighbours();
		for(int i=0; i < neigs.size(); i++) {
			WatershedPixel q = (WatershedPixel) neigs.get(i);
			if(watershedPixels.contains(q))
				removeWatershedAndMinimaNeighbours(q/*, count*/);
		}
	}
	
	private void 								removeWatershedPixel(WatershedPixel p) {
		watershedImage.putPixel(p.getX(), p.getY(), 0);
		minimaImage.putPixel(p.getX(), p.getY(), 0);
		watershedPixels.remove(p);
	}
	
	public void /*boolean*/           			removeWatershedPixelAndMinimaPixelAss(WatershedPixel p) {
		WatershedPixel min_p;
		
		removeWatershedPixel(p);
		
		min_p = p.getMinimaPixelAssociated();
		//if(minimaPixels.contains(min_p)) {
			removeMinimaPixel(min_p);
			/*return true;                            // minima pixel removed
		}
		return false; */                              // no minima pixel removed
	}

	private void 								searchWatershedNeighbours(WatershedPixel p, ArrayList<WatershedPixel> connectedComp, Vector<WatershedPixel> watPixels) {
		WatershedPixel n;
		Vector<WatershedPixel> neigs = p.getNeighbours();
		
		if(!connectedComp.contains(p)) {
			connectedComp.add(p);
			watPixels.remove(p);
		
			for(int i=0; i < neigs.size(); i++) {
				n = neigs.get(i);
				if(n.isLabelWSHED())
					searchWatershedNeighbours(n, connectedComp, watPixels);
			}
		}
	}
	
	private void 								showConnectedComponents(ArrayList<ArrayList<WatershedPixel>> connectedComps, int w, int h) {
		ArrayList<WatershedPixel> conComp;
		WatershedPixel curPixel;
		ImageProcessor watershedConComps = new ByteProcessor(watershedImage.getWidth(), watershedImage.getHeight());
		ImagePlus watConCompsIPlus = new ImagePlus("Watershed Connected Components", watershedConComps);
		
		for(int i=0; i < connectedComps.size(); i++) {
			conComp = connectedComps.get(i);
			
			for(int k=0; k < conComp.size(); k++) {
				curPixel = conComp.get(k);
				watershedConComps.putPixel(curPixel.getX(), curPixel.getY(), (i+1)*(256/connectedComps.size()));
			}
		}
		
		watConCompsIPlus.show();
	}
	
	public void           			   			showMinimaImage() {
		//minimaImage = buildMinimaImage();
		minImPlus.show();
	}
		
	public void           			   			showWatershedImage() {
		watImPlus.show();
	}
	
	public int           			   			size() {
		return watershedStructure.size();
	}
	
	public String        			   			toString() {
		StringBuffer ret = new StringBuffer();

		for (int i = 0; i < watershedStructure.size(); i++) {
			ret.append(((WatershedPixel) watershedStructure.get(i)).toString());
			ret.append("\n");
			/*ret.append("Neighbours :\n");

			Vector neighbours = ((WatershedPixel) watershedStructure.get(i))
					.getNeighbours();

			for (int j = 0; j < neighbours.size(); j++) {
				ret.append(((WatershedPixel) neighbours.get(j)).toString());
				ret.append("\n");
			}
			ret.append("\n");*/
		}
		return ret.toString();
	}

	
}