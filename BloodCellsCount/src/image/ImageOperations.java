package image;

import ij.IJ;
import ij.ImagePlus;
import ij.ImageStack;
import ij.WindowManager;
import ij.gui.HistogramWindow;
import ij.gui.Plot;
import ij.process.ByteProcessor;
import ij.process.ColorProcessor;
import ij.process.ImageProcessor;
import ij.process.ImageConverter;

import java.awt.Image;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ComponentColorModel;
import java.awt.image.RenderedImage;
import java.util.ArrayList;
import java.util.Vector;

import javax.media.jai.JAI;
import javax.media.jai.KernelJAI;
import javax.media.jai.RenderedOp;
import javax.media.jai.operator.ErodeDescriptor;
import javax.swing.text.MaskFormatter;

import org.ietf.jgss.Oid;

import net.sf.ij_plugins.color.ColorSpaceConvertion;

public class ImageOperations {
	
	public static ImageProcessor copyImage(ImageProcessor image) {
		ImageProcessor image_copy = image.createProcessor(image.getWidth(), image.getHeight());
		image_copy.setPixels(image.getPixelsCopy());
		return image_copy;
	} 

	public static ImageProcessor switchBlackWhite(ImageProcessor image) {
		ImageProcessor switchedIm = copyImage(image);
		
		for(int y = 0; y < image.getHeight(); y++) {
			for(int x = 0; x < image.getWidth(); x++) {
				if(image.getPixel(x, y) == 0)
					switchedIm.putPixelValue(x, y, 255);
				else if(image.getPixel(x, y) == 255)
					switchedIm.putPixelValue(x, y, 0);
				else {
					IJ.error("Error", "Image is not binary.");
					return null;
				}
			}
		}
		
		return switchedIm;
	}
	
	public static ByteProcessor[] convertfromRGBtoYCbCr(BufferedImage rgb) {
		ColorProcessor rgpCP = new ColorProcessor(rgb);
		return ColorSpaceConvertion.rgbToYCbCr(rgpCP);
	}
	
	public static ByteProcessor[] convertfromRGBtoHSB(BufferedImage image) {
		ImagePlus imagePlus = new ImagePlus("", image);
		ImageProcessor imageP;
		ImageStack imageStack;
	    ByteProcessor[] imageHSB = new ByteProcessor[3];
	    
		new ImageConverter(imagePlus).convertToHSB();
		imageStack = imagePlus.getStack();
		
		//imageHSB = (ByteProcessor[]) imageStack.getImageArray();
		
		imageP = imageStack.getProcessor(1);
		imageHSB[0] = new ByteProcessor(imageP.getWidth(), imageP.getHeight(), (byte[]) imageP.getPixels(), imageP.getColorModel());
		imageP = imageStack.getProcessor(2);
		imageHSB[1] = new ByteProcessor(imageP.getWidth(), imageP.getHeight(), (byte[]) imageP.getPixels(), imageP.getColorModel());
		imageP = imageStack.getProcessor(3);
		imageHSB[2] = new ByteProcessor(imageP.getWidth(), imageP.getHeight(), (byte[]) imageP.getPixels(), imageP.getColorModel());
		
		return imageHSB;
	}
	
	public static double[] normalizedHistogram(ImageProcessor image) {
		//ImagePlus imagePlus = new ImagePlus("", image);
		int[] hist;
		double[] hist_norm = new double[256];
		int N = image.getWidth()*image.getHeight();
		
		//hist = new HistogramWindow(imagePlus).getHistogram();
		hist = image.getHistogram();
		//Histogram Normalization
		/*for (int i = 0; i < hist.length; i++) {
			if(hist_max < hist[i]) {
				hist_max = hist[i]; 
	        }
	    }*/
		for (int i = 0; i < hist.length; i++) {
			hist_norm[i] = (float) hist[i]/N; 
	    }
		
		return hist_norm;
	}
	
	public static ByteProcessor doThresholding(ImageProcessor image, int threshold) {
		byte[] pixels = (byte[]) image.getPixels();
		int[] pixels_int = new int[pixels.length];
		byte[] pixels_out = pixels.clone();
		
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
	
	public static ByteProcessor doBinaryThresholding(ImageProcessor image, int threshold) {
		byte[] pixels = (byte[]) image.getPixels();
		int[] pixels_int = new int[pixels.length];
		byte[] pixels_out = pixels.clone();
		
		for(int i=0; i<pixels.length; i++)
			pixels_int[i] = Integer.parseInt(""+pixels[i]);
		
		for(int i=0; i<pixels.length; i++) {
			int value = pixels[i] & 0xff; 
			if(value < threshold)
				pixels_out[i] = 0;
			else
				pixels_out[i] = (byte) 255;
		}
		
		ColorModel colorModel = new ComponentColorModel(
				ColorSpace.getInstance(ColorSpace.CS_GRAY), false, false, 1, 0);
		
		return new ByteProcessor(image.getWidth(),image.getHeight(),pixels_out,colorModel);
	}

	public static ByteProcessor doOtsuThresholding(ImageProcessor image) {
		double[] hist_norm = normalizedHistogram(image);
		int L = (int) Math.pow(2,image.getColorModel().getComponentSize(0)); // number of gray levels
		double[] P = new double[L];
		double[] m = new double[L];
		double mG;                     //Average intensity of entire image
		double[] var = new double[L];   //between-class variance
		double max_var = 0;
		int optimum_k = 0;             //Otsu's threshold value              
		
		for(int k = 0; k < L; k++) {
			if(k==0) {
				P[k] = hist_norm[k];
				m[k] = k*hist_norm[k];
			} else {
				P[k] = P[k-1] + hist_norm[k];   //Probability of pixel with intensity <= k
				m[k] = m[k-1] + k*hist_norm[k]; //Average intensity up to level k
			}
			/*for(int i = 0; i <= k; i++) {
				P[k] = P[k] + hist_norm[i];    //Probability of pixel with intensity <= k
				m[k] = m[k] + i*hist_norm[i];  //Average intensity up to level k
			}*/
		}
		mG = m[L-1];
		
		//Compute between-class variance
		for(int k = 0; k < L; k++) {
			var[k] = Math.pow( mG * P[k] - m[k], 2 ) / ( P[k] * (1-P[k]) );
			if(var[k] > max_var) {
				max_var = var[k];
				optimum_k = k;
			}
		}
		
		return doBinaryThresholding(image, optimum_k);
	}
	
	public static int countRBC(ImageProcessor im, int diameter) {
		ImagePlus imagep = new ImagePlus();
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

	public static ByteProcessor distanceTransform(ImageProcessor imageproc) {
		ImageProcessor image = imageproc.convertToByte(false);
		new ImagePlus("",image).show();
		//System.out.println(image.getSliceNumber());
		//int pixel_in, pixel_out;
		int out,max_graylevel=0,min_graylevel=255,pixelValue;
		byte ws_d;
		//byte[] pixels_in = (byte[]) image.getPixels();
		int height = image.getHeight();
		int width  = image.getWidth();
		ByteProcessor distanceImage = new ByteProcessor(width, height);
		ImagePlus distImagePlus =  new ImagePlus("",distanceImage);
		
		for (int yy=0; yy<height; yy++) {
			for(int xx=0; xx<width; xx++) {
				if(image.getPixel(xx,yy)==0) 
					distanceImage.putPixelValue(xx,yy,0);
			    else {
			        out = 0;
			        ws_d = 1;
			        while (out==0) {
			        	for(int y1=(yy-ws_d); y1<=(yy+ws_d); y1++) {
			        		for(int x1=(xx-ws_d); x1<=(xx+ws_d); x1++) {
			        			if( (y1>=0) && (x1>=0) && (y1<height) && (x1<width)) {
			        				if(image.getPixel(x1,y1)==0) {
			        					//new ImagePlus("",image).show();
			        					pixelValue = (255-ws_d) & 0xff;
			        					distanceImage.putPixel(xx,yy,pixelValue);
			        					out=1;
			        					if( (255-ws_d)>max_graylevel ) 
			        						max_graylevel = 255-ws_d;
			        					if( (255-ws_d)<min_graylevel )
			        						min_graylevel = 255-ws_d;
			                        }
			        				distImagePlus.show();
			                    }
			                    if(out==1) break;
			                 }
	                         if(out==1) break;
		                 }
			             ws_d++;
			        }
			    }
			}
		}
		
		distanceImage.setMinAndMax(0, max_graylevel);
		return distanceImage;
		/*ImageProcessor G_ = image.createProcessor(width, height); //new ByteProcessor(width, height);
		ImageProcessor G  = image.createProcessor(width, height); //new ByteProcessor(width, height);
		//int[] pixels_out = (int[]) G_.getPixels();
		
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < width; j++) {
				G_.putPixelValue(i, j, height*width);
				G.putPixelValue(i, j, height*width);
			}
		}
		
	// Algoritmo de Saito e Toriwaki
		
		// Transformacao 1 (linhas)
		// forward scan
		for(int i = 0; i<height; i++) {
			//pixel = pixels_in[i*width];
			pixel_in = image.getPixel(i, 0);
		    //if( pixel_in == 0) {
		    	//pixels_out[i*width] = 0;
		    	pixel_out = G_.getPixel(i, 0);
		    	pixel_out = 0;
		    	G_.putPixelValue(i, 0, 0);
		    //}
		    for (int j = 1; j<width; j++) {
		    	//pixel_in = pixels_in[i*width + j];
		    	pixel_in  = image.getPixel(i, j);
		    	pixel_out = G_.getPixel(i, j);
		        if(pixel_in != 0) {
		        	//pixels_out[i*width + j] = (G_.getPixel(i,j-1)^(1/2) + 1)^2;
		        	pixel_out = (G_.getPixel(i,j-1)^(1/2) + 1)^2;
		        	G_.putPixelValue(i, j, pixel_out);
		        } else {
		        	//pixels_out[i*width + j] = 0;
		        	//pixel_out = 0;
		        	G_.putPixelValue(i, 0, 0);
		        }
		    }
		}
		
		return G_;*/
		
	}
	
	public static int computeCutOffFrequency(ImageProcessor fftIm) {
		//int cutoffFreq = 110;
		//double[] fftProfile = fftIm.getLine(0, 0, fftIm.getWidth()-1, fftIm.getHeight()-1);
		double[] fftProfile = fftIm.getLine(0, fftIm.getHeight()/2, fftIm.getWidth()-1, fftIm.getHeight()/2);
		double[] xValues = new double[fftProfile.length];
		double avF = 0, sdF = 0; //average frequency, standard deviation
		int cutoffFreq = 0;
		   
		for(int i = 0; i < xValues.length; i++)
		  	xValues[i] = i;
		Plot fftProfilePlot = new Plot("FFT Profile", "", "Magnitude", xValues, fftProfile);
		fftProfilePlot.show();
		    
		for(int i = 0; i < fftProfile.length; i++) 
			avF += fftProfile[i];
		avF = avF/fftProfile.length;
		
		for(int i = 0; i < fftProfile.length; i++) 
			sdF += Math.pow(fftProfile[i] - avF, 2);
		sdF = Math.sqrt(sdF/fftProfile.length);	
		
		/*for(int i = 0; i < fftProfile.length; i++) { 
		   	if(fftProfile[i] >= avF + sdF) {
		   		cutoffFreq = fftProfile.length/2-(i-1);
		   		break;
		   	}
		}*/
		cutoffFreq = (int) avF + 2 * (int)sdF;
		return cutoffFreq;
	}

	public static ByteProcessor AND(ImageProcessor image1, ImageProcessor image2) {
		int width1 = image1.getWidth();
		int height1 = image1.getHeight();
		int width2 = image2.getWidth();
		int height2 = image2.getHeight();
		ByteProcessor imagesAND = null;
		int pixel1, pixel2;
		
		try {
			if(width1==width2 && height1==height2) {
				
				imagesAND = new ByteProcessor(width1,height1);
				for(int y = 0; y < height1; y++) {
					for(int x = 0; x < width1; x++) {
						pixel1 = image1.getPixel(x, y);
						pixel2 = image2.getPixel(x, y);
						if(pixel1==0 || pixel2==0) {
							imagesAND.set(x, y, 0);
						} else
							imagesAND.set(x, y, pixel1);
					}
				}
				return imagesAND;
			} else 
				throw new Exception();
		} catch (Exception e) {
			System.out.println("Images are not of the same size.");
		}
		return imagesAND;
	}

	public static ImagePlus runFFT(ImagePlus image) {
		ImagePlus fft = image;
		
		WindowManager.setTempCurrentImage(fft);
		IJ.run("FFT");
		
		fft = WindowManager.getCurrentImage();
		//fft.show();
		
		return fft;
	}
	
	public static ImagePlus runInverseFFT(ImagePlus fftIm) {
		ImagePlus fftFilteredIm = fftIm;
		
		WindowManager.setTempCurrentImage(fftFilteredIm);
		IJ.run("Inverse FFT");
		
		fftFilteredIm = WindowManager.getCurrentImage();
		fftFilteredIm.show();
		
		return fftFilteredIm;
	}

}
