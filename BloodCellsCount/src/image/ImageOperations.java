package image;

import ij.ImagePlus;
import ij.process.ByteProcessor;
import ij.process.ColorProcessor;
import ij.process.ImageProcessor;

import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ComponentColorModel;
import java.util.ArrayList;
import java.util.Vector;

import org.ietf.jgss.Oid;

import net.sf.ij_plugins.color.ColorSpaceConvertion;

public class ImageOperations {
	private static ImagePlus imagep = new ImagePlus();

	public static ByteProcessor[] convertfromRGBtoYCbCr(BufferedImage rgb) {
		ColorProcessor rgpCP = new ColorProcessor(rgb);
		return ColorSpaceConvertion.rgbToYCbCr(rgpCP);
	}
	
	public static ByteProcessor doThresholding(ImageProcessor image, int threshold) {
		byte[] pixels = (byte[]) image.getPixels();
		int[] pixels_int = new int[pixels.length];
		byte[] pixels_out = pixels;
		
		for(int i=0; i<pixels.length; i++)
			pixels_int[i] = Integer.parseInt(""+pixels[i]);
		
		for(int i=0; i<pixels.length; i++) {
			int value = pixels[i] & 0xff; 
			if(value < threshold)
				pixels_out[i] = 0;
			else
				pixels_out[i] = pixels[i];
		}
		
		ColorModel colorModel = new ComponentColorModel(
				ColorSpace.getInstance(ColorSpace.CS_GRAY), false, false, 1, 0);
		
		return new ByteProcessor(image.getWidth(),image.getHeight(),pixels_out,colorModel);
	}

	public static int countRBC(ImageProcessor im, int diameter) {
		
		//double diameter = 31;
		int w = im.getWidth();
		int h= im.getHeight();
		int offset, bottomOffset, topOffset, i, label=0;
		byte[] pixelsSrc = (byte[]) im.getPixels(); 
		byte[] pixels = new byte[pixelsSrc.length];
		System.arraycopy(pixelsSrc, 0, pixels, 0, pixelsSrc.length);
		ColorModel colorModel = new ComponentColorModel(
				ColorSpace.getInstance(ColorSpace.CS_GRAY), false, false, 1, 0);
		ByteProcessor image = new ByteProcessor(w, h, pixels, colorModel);
		ArrayList<Pixel> pixelArray = new ArrayList<Pixel>();
		ArrayList<Pixel> referencePixels = new ArrayList<Pixel>();
		referencePixels.add(new Pixel());
		
		ByteProcessor outputIm = new ByteProcessor(w, h);
		ImagePlus outputImPlus = new ImagePlus("Labeling", outputIm);
		
		//*********************
		//remove cells that are not completely included on the image
		imagep.setProcessor(image);
		for(int x = 0; x < w; x++) {
			Pixel pixel = new Pixel(x, 0, (byte) image.getPixel(x, 0));
			if(pixel.getIntHeight()==255) {
				System.out.println("\nBegin");
				removeNeighbours(pixel,image,pixel,diameter);
				//outputImPlus.show();
			}
		}
		
		for(int x = 0; x < w; x++) {
			Pixel pixel = new Pixel(x, h-1, (byte) image.getPixel(x, h-1));
			if(pixel.getIntHeight()==255) {
				System.out.println("\nBegin");
				removeNeighbours(pixel,image,pixel,diameter);
				//outputImPlus.show();
			}
		}
		
		for(int y = 1; y < h-1; y++) {
			Pixel pixel = new Pixel(0, y, (byte) image.getPixel(0, y));
			if(pixel.getIntHeight()==255) {
				System.out.println("\nBegin");
				removeNeighbours(pixel,image,pixel,diameter);
				//outputImPlus.show();
			}
		}
		
		for(int y = 1; y < h-1; y++) {
			Pixel pixel = new Pixel(w-1, y, (byte) image.getPixel(w-1, y));
			if(pixel.getIntHeight()==255) {
				System.out.println("\nBegin");
				removeNeighbours(pixel,image,pixel,diameter);
				//outputImPlus.show();
			}
		}
		//*********************
		
		for (int y = 0; y < h; y++) {
			offset = y * w;

			System.out.println("Progress: "+(0.1 + 0.3 * y / h));

			for (int x = 0; x < w; x++) {
				i = offset + x;
				pixelArray.add(new Pixel(x, y, pixels[i]));
			}
		}
		
		//add neighbors
		
		for (int y = 0; y < h; y++) {
			offset = y * w;
			topOffset = offset + w;
			bottomOffset = offset - w;

			//ByteProcessor outputIm = new ByteProcessor(w, h);
			
			System.out.println("Progress: "+(0.1 + 0.3 * y / h));
			
			for (int x = 0; x < w; x++) {
				Pixel currentPixel = pixelArray.get(x + offset);
				//outputIm.putPixel(currentPixel.getX(), currentPixel.getY(), 255);
				Pixel neighbour = new Pixel();
				
				if (x - 1 >= 0) {
					neighbour = pixelArray.get(x - 1 + offset);
					currentPixel.addNeighbour(neighbour);
					//outputIm.putPixel(neighbour.getX(), neighbour.getY(), 255);

					if (y - 1 >= 0) {
						neighbour = pixelArray.get(x - 1 + bottomOffset);
						currentPixel.addNeighbour(neighbour);
						//outputIm.putPixel(neighbour.getX(), neighbour.getY(), 255);
					}
					
					if (y + 1 < h) {
						neighbour = pixelArray.get(x - 1 + topOffset);
						currentPixel.addNeighbour(neighbour);
						//outputIm.putPixel(neighbour.getX(), neighbour.getY(), 255);
					}
				}
				/*if (x - 2 >= 0) {
					neighbour = pixelArray.get(x - 2 + offset);
					currentPixel.addNeighbour(neighbour);
				//	outputIm.putPixel(neighbour.getX(), neighbour.getY(), 255);
					
					if (y - 1 >= 0) {
						neighbour = pixelArray.get(x - 2 + bottomOffset);
						currentPixel.addNeighbour(neighbour);
					//	outputIm.putPixel(neighbour.getX(), neighbour.getY(), 255);
					}
				}
				if (x - 3 >= 0) {
					neighbour = pixelArray.get(x - 3 + offset);
					currentPixel.addNeighbour(neighbour);
				//	outputIm.putPixel(neighbour.getX(), neighbour.getY(), 255);
					
					if (y - 1 >= 0) {
						neighbour = pixelArray.get(x - 3 + bottomOffset);
						currentPixel.addNeighbour(neighbour);
					//	outputIm.putPixel(neighbour.getX(), neighbour.getY(), 255);
					}
				}
				if (x - 4 >= 0) {
					neighbour = pixelArray.get(x - 4 + offset);
					currentPixel.addNeighbour(neighbour);
//					outputIm.putPixel(neighbour.getX(), neighbour.getY(), 255);
					
					if (y - 1 >= 0) {
						neighbour = pixelArray.get(x - 4 + bottomOffset);
						currentPixel.addNeighbour(neighbour);
					//	outputIm.putPixel(neighbour.getX(), neighbour.getY(), 255);
					}
				}*/
				if (x + 1 < w) {
					neighbour = pixelArray.get(x + 1 + offset);
					currentPixel.addNeighbour(neighbour);
					//outputIm.putPixel(neighbour.getX(), neighbour.getY(), 255);
					
					if (y - 1 >= 0) {
						neighbour = pixelArray.get(x + 1 + bottomOffset);
						currentPixel.addNeighbour(neighbour);
						//outputIm.putPixel(neighbour.getX(), neighbour.getY(), 255);
					}
					
					if (y + 1 < h) {
						neighbour = pixelArray.get(x + 1 + topOffset);
						currentPixel.addNeighbour(neighbour);
						//outputIm.putPixel(neighbour.getX(), neighbour.getY(), 255);
					}
				}
				/*if(x + 2 < w) {
					if (y - 1 >= 0) {
						neighbour = pixelArray.get(x + 2 + bottomOffset);
						currentPixel.addNeighbour(neighbour);
						//outputIm.putPixel(neighbour.getX(), neighbour.getY(), 255);
					}
				}
				if(x + 3 < w) {
					if (y - 1 >= 0) {
						neighbour = pixelArray.get(x + 3 + bottomOffset);
						currentPixel.addNeighbour(neighbour);
						//outputIm.putPixel(neighbour.getX(), neighbour.getY(), 255);
					}
				}
				if(x + 4 < w) {
					if (y - 1 >= 0) {
						neighbour = pixelArray.get(x + 4 + bottomOffset);
						currentPixel.addNeighbour(neighbour);
						//outputIm.putPixel(neighbour.getX(), neighbour.getY(), 255);
					}
				}*/
				if (y - 1 >= 0) {
					neighbour = pixelArray.get(x + bottomOffset);
					currentPixel.addNeighbour(neighbour);
					//outputIm.putPixel(neighbour.getX(), neighbour.getY(), 255);
				}
				
				if (y + 1 < h) {
					neighbour = pixelArray.get(x + topOffset);
					currentPixel.addNeighbour(neighbour);
					//outputIm.putPixel(neighbour.getX(), neighbour.getY(), 255);
				}
					
				//outputImPlus.setProcessor(outputIm);
				//outputImPlus.show();
			}
		}
		
		//*************************
		// Find red blood cells
		Vector<ArrayList<Pixel>> labeledPixelsSet = new Vector<ArrayList<Pixel>>(); 
		labeledPixelsSet.add(new ArrayList<Pixel>());
		
		for(int k = 0; k < pixelArray.size(); k++) {
			Pixel currentPixel = pixelArray.get(k);
			int x = currentPixel.getX();
			int y = currentPixel.getY();
			
			if((image.getPixel(x, y) & 0xff)==255) {
				label++;
				ArrayList<Pixel> labeledPixels = new ArrayList<Pixel>();
				labelRBC(currentPixel,labeledPixels,label);
				labeledPixelsSet.add(label, labeledPixels);
				
				//remove set from Image
				for(int n = 0; n < labeledPixels.size(); n++) {
					Pixel pixel = labeledPixels.get(n);
					image.putPixel(pixel.getX(), pixel.getY(), 0);
				}
			}
		}
		//************************
		
		//************************
		// Check for overlying RBC
		ArrayList<Pixel> labeledPixels, labeledPixels_aux;
		Pixel currentPixel;
		int passedNumberofSets = labeledPixelsSet.size();
		double distance;
		
		for(int k = 1; k < passedNumberofSets; k++) {
			labeledPixels = labeledPixelsSet.get(k);
			labeledPixels_aux = (ArrayList<Pixel>) labeledPixels.clone();
			ArrayList<Pixel> new_labeledPixels = new  ArrayList<Pixel>();
			Pixel reference = labeledPixels.get(0); 
			int count = 0;
			
			for(int n = 1; n < labeledPixels_aux.size(); n++) {
				currentPixel = labeledPixels_aux.get(n);
				distance = pixelsDistance(currentPixel, reference);
				outputIm.putPixel(currentPixel.getX(), currentPixel.getY(), (currentPixel.getLabel()*50)%256);
				outputImPlus.show();
				
				if(distance > diameter) {
					if(count==0)
						label++;
					labeledPixels.remove(currentPixel);
					currentPixel.setLabel(label);
					new_labeledPixels.add(currentPixel);
					count++;
					
					outputIm.putPixel(currentPixel.getX(), currentPixel.getY(), (currentPixel.getLabel()*50)%256);
					outputImPlus.show();
				}
			}
			
			if(count>0)
				labeledPixelsSet.add(label,new_labeledPixels);
		}
		
		//second check
		for(int k = passedNumberofSets; k < labeledPixelsSet.size(); k++) {
			labeledPixels = labeledPixelsSet.get(k);
			labeledPixels_aux = (ArrayList<Pixel>) labeledPixels.clone();
			ArrayList<Pixel> new_labeledPixels = new  ArrayList<Pixel>();
			Pixel reference = labeledPixels.get(0); 
			int count = 0;
			
			for(int n = 1; n < labeledPixels_aux.size(); n++) {
				currentPixel = labeledPixels_aux.get(n);
				distance = pixelsDistance(currentPixel, reference);
							
				if(distance > diameter) {
					if(count==0)
						label++;
					labeledPixels.remove(currentPixel);
					currentPixel.setLabel(label);
					new_labeledPixels.add(currentPixel);
					count++;
					
					outputIm.putPixel(currentPixel.getX(), currentPixel.getY(), (currentPixel.getLabel()*50)%256);
					outputImPlus.show();
				}
			}
			
			if(count>0)
				labeledPixelsSet.add(label,new_labeledPixels);
		}
		//*****************************
		
		/*for(int k = 0; k < pixelArray.size(); k++) {
			Pixel currentPixel = pixelArray.get(k);
			System.out.println(currentPixel.toString());
			if(currentPixel.getIntHeight()==255) {
				Vector arrayNeighbours = currentPixel.getNeighbours();
				Pixel referencePixel;
				int passedLabel = Pixel.INIT, neighbourLabel, count = 0;
				double distance, min_distance=200;
				
				for(int n = 0; n < arrayNeighbours.size(); n++) {
					Pixel neighbour = (Pixel) arrayNeighbours.get(n);
					neighbourLabel = neighbour.getLabel();					
					
					if(neighbourLabel!=Pixel.INIT) {
						if(neighbourLabel != passedLabel) {
							referencePixel = referencePixels.get(neighbourLabel);
							distance = pixelsDistance(currentPixel.getX(),currentPixel.getY(), referencePixel.getX(), referencePixel.getY());
							if(distance < min_distance) {
								min_distance = distance;
								currentPixel.setLabel(neighbourLabel);
								outputIm.putPixel(currentPixel.getX(), currentPixel.getY(), (currentPixel.getLabel()*50)%256);
								outputImPlus.show();
							}
						}
						passedLabel = neighbourLabel;
						count++;
					}
				}
				
				if(count==0 || min_distance>diameter) {
					label++;
					currentPixel.setLabel(label);
					referencePixels.add(label, currentPixel);
					
					outputIm.putPixel(currentPixel.getX(), currentPixel.getY(), (currentPixel.getLabel()*50)%256);
					outputImPlus.show();
				}
				System.out.println(currentPixel.toString());
			}
		}*/
		return label;
	}
	
	private static void labelRBC(Pixel pixel, ArrayList<Pixel> labeledPixels, int label) {
		System.out.println(pixel.toString());
		pixel.setHeight((byte)0);
		pixel.setLabel(label);
		labeledPixels.add(pixel);
		Vector neighbours = pixel.getNeighbours();
		
		for(int i = 0; i < neighbours.size(); i++) {
			Pixel neighbour = (Pixel) neighbours.get(i);  
			if(neighbour.getIntHeight()==255)
				labelRBC(neighbour, labeledPixels, label);
		}
		return;
	}
	
	private static void removeNeighbours(Pixel pixel, ImageProcessor image, Pixel reference, int diameter) {
		int x = pixel.getX();
		int y = pixel.getY();
		int width = image.getWidth();
		int height = image.getHeight();
		byte pixelHeight;
		Vector neighbours;
		//ArrayList<Pixel> pixelArray = new ArrayList<Pixel>();
		System.out.println(pixel.toString());
		//imagep.show();
		if (x + 1 < width) {
			pixelHeight = (byte) (image.getPixel(x+1, y));
			if((pixelHeight & 0xff)==255) {
				Pixel neighbour = new Pixel(x + 1, y, pixelHeight);
				if(pixelsDistance(neighbour, reference)<=diameter)
					pixel.addNeighbour(neighbour);
			}

			if (y - 1 >= 0) {
				pixelHeight = (byte) image.getPixel(x+1, y-1);
				if((pixelHeight & 0xff)==255) {
					Pixel neighbour = new Pixel(x + 1, y - 1, pixelHeight);
					if(pixelsDistance(neighbour, reference)<=diameter)
						pixel.addNeighbour(neighbour);
				}
			}

			if (y + 1 < height) {
				pixelHeight = (byte) image.getPixel(x+1, y+1);
				if((pixelHeight & 0xff)==255) {
					Pixel neighbour = new Pixel(x + 1, y + 1, pixelHeight);
					if(pixelsDistance(neighbour, reference)<=diameter)
						pixel.addNeighbour(neighbour);
				}
			}
		}

		if (x - 1 >= 0) {
			pixelHeight = (byte) image.getPixel(x-1, y);
			if((pixelHeight & 0xff)==255) {
				Pixel neighbour = new Pixel(x - 1, y, pixelHeight);
				if(pixelsDistance(neighbour, reference)<=diameter)
					pixel.addNeighbour(neighbour);
			}				

			if (y - 1 >= 0) {
				pixelHeight = (byte) image.getPixel(x-1, y-1);
				if((pixelHeight & 0xff)==255) {
					Pixel neighbour = new Pixel(x - 1, y - 1, pixelHeight);
					if(pixelsDistance(neighbour, reference)<=diameter)
						pixel.addNeighbour(neighbour);
				}
			}

			if (y + 1 < height) {
				pixelHeight = (byte) image.getPixel(x-1, y+1);
				if((pixelHeight & 0xff)==255) {
					Pixel neighbour = new Pixel(x - 1, y + 1, pixelHeight);
					if(pixelsDistance(neighbour, reference)<=diameter)
						pixel.addNeighbour(neighbour);
				}
			}
		}

		if (y - 1 >= 0) {
			pixelHeight = (byte) image.getPixel(x, y-1);
			if((pixelHeight & 0xff)==255) {
				Pixel neighbour = new Pixel(x, y - 1, pixelHeight);
				if(pixelsDistance(neighbour, reference)<=diameter)
					pixel.addNeighbour(neighbour);
			}
		}

		if (y + 1 < height) {
			pixelHeight = (byte) image.getPixel(x, y+1);
			if((pixelHeight & 0xff)==255) {
				Pixel neighbour = new Pixel(x, y + 1, pixelHeight);
				if(pixelsDistance(neighbour, reference)<=diameter)
					pixel.addNeighbour(neighbour);
			}
		}
		
		pixel.setHeight((byte) 0);
		image.putPixel(pixel.getX(), pixel.getY(), pixel.getHeight());
		//imagep.show();
		
		neighbours = pixel.getNeighbours();
		for(int i = 0; i < neighbours.size(); i++) {
			Pixel neighbour = (Pixel) neighbours.get(i);
			removeNeighbours(neighbour,image,reference,diameter);
			//neighbour.setHeight((byte) 0);
			//image.putPixel(neighbour.getX(), neighbour.getY(), neighbour.getIntHeight());
		}
		
		return;
	}
	

	public static double pixelsDistance(Pixel p1, Pixel p2) {
		int x1 = p1.getX();
		int y1 = p1.getY();
		int x2 = p2.getX();
		int y2 = p2.getY();
		return Math.pow(Math.pow(x2-x1, 2) + Math.pow(y2-y1, 2), 0.5);
	}
	
	/*public static void calculateFFT() {
		FFT fft = new FFT();
		fft.run("");
		Raster data = image.getData();
		int[] aux = null;
		int[] pixels = data.getPixels(0, 0, image.getWidth(),
				image.getHeight(), aux);
		FFT fft = new FFT(pixels, image.getWidth(), image.getHeight());
		ColorModel colorModel = new ComponentColorModel(
				ColorSpace.getInstance(ColorSpace.CS_GRAY), false, false, 1, 0);

		return new ByteProcessor(image.getWidth(), image.getHeight(),
				fft.getPixels(), colorModel);
	}*/
}
