package image;

import ij.IJ;
import ij.ImagePlus;
import ij.WindowManager;
import ij.process.ByteProcessor;
import ij.process.ImageProcessor;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.RenderedImage;

import javax.media.jai.JAI;
import javax.media.jai.KernelJAI;
import javax.media.jai.RenderedOp;

public class MorphologicalOp {
	
	public static final int RECT = 0;
	public static final int DISK = 1;
	public static final int DIAMOND = 2;
	public static final int CROSS = 3;
	
	public static KernelJAI createKernel(int w, int h, int type) {
		float[] kernel_values = new float[w*h];
		int cy = (int) Math.floor(h/2);
		int cx = (int) Math.floor(w/2);
		
		switch (type) {
		case RECT:
			for(int i = 0; i < kernel_values.length; i++)
				kernel_values[i] = 1;
			break;

		case DISK:
			int es_r = (int) Math.floor( Math.sqrt(Math.pow(Math.floor(w/2), 2) + Math.pow(Math.floor(h/2), 2)) );
			double r;
			
			for(int y = 0; y < h; y++) {
				for(int x = 0; x < w; x++) {
					r = Math.sqrt( Math.pow(Math.abs(y - cy), 2) + Math.pow(Math.abs(x - cx), 2) );
					if(r > es_r)
						kernel_values[y*w + x] = 0;
					else
						kernel_values[y*w + x] = 1;
				}
			}
			break;
			
		case DIAMOND:
			
			break;
			
		case CROSS:
			for(int y = 0; y < h; y++)
				kernel_values[y*w + cx] = 1;
			for(int x = 0; x < w; x++)
				kernel_values[cy*w + x] = 1;
			break;

		default:
			break;
		}
		
		return new KernelJAI(w, h, kernel_values);
	}

	public static ImageProcessor erode(ImageProcessor imageproc, KernelJAI kernel) {
		Image image =  new ImagePlus("",imageproc).getImage();
		BufferedImage image_out = (BufferedImage) image;
		
		RenderedOp renderedOp = JAI.create("erode",(RenderedImage)image, kernel, null);
		image_out.setData(renderedOp.getData());

		return new ImagePlus("",image_out).getProcessor();
		
		/*Image image =  new ImagePlus("",ImageOperations.copyImage(imageproc)).getImage();
		BufferedImage image_aux = (BufferedImage) image;
		BufferedImage image_out;
		Raster data_out;
		int w,h;
		
		RenderedOp renderedOp = JAI.create("erode",(RenderedImage)image, kernel, null);
		image_aux.setData(renderedOp.getData());
		
		data_out = image_aux.getData();
		int value = data_out.getSample(0, 0, 0);
		
		int y, x;
		boolean stop = false;
		for(y = 0; y < image_aux.getHeight(); y++) {
			for(x = 1; x < image_aux.getWidth(); x++) {
				if(data_out.getSample(x, y, 0) != value) {
					stop = true;
					break; 
				}
			}
			if(stop)
				break;
		}
		
		if(y == 0)
			image_out = image_aux;
		else {
			w = image_aux.getWidth();
			h = image_aux.getHeight();
			image_out = new BufferedImage(w-2*y, h-2*y, image_aux.getType());
			image_out.setData(data_out.createChild(y, y, w-y, h-y, 0, 0, null));
		}
		
		return new ImagePlus("",image_out).getProcessor();*/
		
		
		/*Image image =  new ImagePlus("",imageproc).getImage();
		ImageProcessor image_out, imageproc_aux;
		BufferedImage image_aux = new BufferedImage(imageproc.getWidth(), imageproc.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
		RenderedOp renderedOp = null;
				
		renderedOp = JAI.create("erode",(RenderedImage)image, kernel, null);
				
		image_aux.setData(renderedOp.getData());
		imageproc_aux = new ImagePlus("",image_aux).getProcessor();
		new ImagePlus("",image_aux).show();
		
		int value = imageproc_aux.getPixel(0, 0);
		int y, x;
		boolean stop = false;
		for(y = 0; y < imageproc_aux.getHeight(); y++) {
			for(x = 1; x < image_aux.getWidth(); x++) {
				if(imageproc_aux.getPixel(x, y) != value) {
					stop = true;
					break; 
				}
			}
			if(stop)
				break;
		}
		
		if(y == 0)
			image_out = imageproc_aux;
		else {
			imageproc_aux.setRoi(y, y, imageproc_aux.getWidth()-2*y, imageproc_aux.getHeight()-2*y);
			image_out = imageproc_aux.crop();
		}
				
		for(y = 0; y < image_out.getHeight(); y++) {
			for(x = 0; x < image_out.getWidth(); x++) {
				if(image_out.getPixel(x, y) != 0 && image_out.getPixel(x, y) != 255)
					image_out.putPixel(x, y, 255);
			}
		}
	
		return image_out;*/
	}
	
	public static ImageProcessor dilate(ImageProcessor imageproc, KernelJAI kernel) {
		Image image =  new ImagePlus("",imageproc).getImage();
		BufferedImage image_out = (BufferedImage) image;
		
		RenderedOp renderedOp = JAI.create("dilate",(RenderedImage)image, kernel, null);
		image_out.setData(renderedOp.getData());

		return new ImagePlus("",image_out).getProcessor();
		
		/*Image image =  new ImagePlus("",imageproc).getImage();
		ImageProcessor image_out;
		BufferedImage image_aux = new BufferedImage(imageproc.getWidth(), imageproc.getHeight(),BufferedImage.TYPE_BYTE_GRAY);
		//new ImagePlus("",image_aux).show();
		RenderedOp renderedOp = null;
				
		renderedOp = JAI.create("dilate",(RenderedImage)image, kernel, null);
		new ImagePlus("",image).show();
				
		image_aux.setData(renderedOp.getData());
		image_out = new ImagePlus("",image_aux).getProcessor();
		new ImagePlus("",image_aux).show();
		
		return image_out;*/
	}
	
	public static ImageProcessor open(ImageProcessor imageproc, KernelJAI kernel) {
		String op_str = null, op_str1 = null; 
		ImagePlus image_outPlus;
		Image image =  new ImagePlus("",ImageOperations.copyImage(imageproc)).getImage();
		BufferedImage image_out = (BufferedImage) image;
		BufferedImage image_out_aux = new BufferedImage(image_out.getWidth(), image_out.getHeight(), image_out.getType());
		RenderedOp renderedOp = null, renderedOp_aux = null;
				
		op_str = "erode"; 
		op_str1 = "dilate"; 
			
		renderedOp_aux = JAI.create(op_str,(RenderedImage)image, kernel, null);
		image_out_aux.setData(renderedOp_aux.getData());
		//new ImagePlus("aux",image_out_aux).show();
		renderedOp = JAI.create(op_str1, image_out_aux, kernel, null);
				
		image_out.setData(renderedOp.getData());
		image_outPlus = new ImagePlus("out",image_out);
		//image_outPlus.show();
		
		return image_outPlus.getProcessor();
		
		/*ImageProcessor image_aux, image_out;
		
		image_aux = erode(imageproc, kernel);
		new ImagePlus("",image_aux).show();
		image_out = dilate(image_aux, kernel);
		new ImagePlus("",image_out).show();
		
		return image_out;*/
	}
	
	public static ImageProcessor close(ImageProcessor imageproc, KernelJAI kernel) {
		String op_str = null, op_str1 = null; 
		ImagePlus image_outPlus;
		Image image =  new ImagePlus("",ImageOperations.copyImage(imageproc)).getImage();
		BufferedImage image_out = (BufferedImage) image;
		BufferedImage image_out_aux = new BufferedImage(image_out.getWidth(), image_out.getHeight(), image_out.getType());
		RenderedOp renderedOp = null, renderedOp_aux = null;
				
		op_str = "dilate"; 
		op_str1 = "erode"; 
			
		renderedOp_aux = JAI.create(op_str,(RenderedImage)image, kernel, null);
		image_out_aux.setData(renderedOp_aux.getData());
		//new ImagePlus("aux",image_out_aux).show();
		renderedOp = JAI.create(op_str1, image_out_aux, kernel, null);
				
		image_out.setData(renderedOp.getData());
		image_outPlus = new ImagePlus("out",image_out);
		//image_outPlus.show();
		
		return image_outPlus.getProcessor();
		
		/*
		ImageProcessor image_aux, image_out;
		
		image_aux = dilate(imageproc, kernel);
		new ImagePlus("Aux-dilate",image_aux).show();
		image_out = erode(image_aux, kernel);
		new ImagePlus("Out-erode",image_out).show();
		
		return image_out;*/
	}
	
	public static ImageProcessor fillHoles(ImageProcessor imageproc) {
		ImagePlus image_Plus = new ImagePlus("", imageproc);
		ImageProcessor image_aux; 
		
		WindowManager.setTempCurrentImage(image_Plus);
		IJ.run("Make Binary");
		image_aux = ImageOperations.switchBlackWhite(imageproc);
		image_Plus.setProcessor(image_aux);
		IJ.run("Fill Holes");
		
		return ImageOperations.switchBlackWhite(image_aux);
	}
	
	/*public static ImageProcessor morphologicalOperator(ImageProcessor imageproc, KernelJAI kernel, int op) {
		String op_str = null, op_str1 = null; 
		Image image =  new ImagePlus("",ImageOperations.copyImage(imageproc)).getImage();
		BufferedImage image_out = (BufferedImage) image;
		BufferedImage image_out_aux = new BufferedImage(image_out.getWidth(), image_out.getHeight(), image_out.getType());
		ImagePlus image_outPlus = new ImagePlus("",image_out);
		RenderedOp renderedOp = null, renderedOp_aux = null;
				
		switch(op) { 
			case ERODE: {
				op_str = "erode"; break;
			}
			case DILATE: {
				op_str = "dilate"; break;
			}
			case OPEN: {
				op_str = "erode"; 
				op_str1 = "dilate"; break;
			}
			case CLOSE: {
				op_str = "dilate";
				op_str1 = "erode"; break;
			}
			default: {
				IJ.error("Not a valid morphological operator.");
				return null;
			}
		}
		
		//RenderedOp renderedOp = ErodeDescriptor.create((RenderedImage)image, kernel, null);
		if(op == ERODE || op == DILATE) 
			renderedOp = JAI.create(op_str,(RenderedImage)image, kernel, null);
		else {
			renderedOp_aux = JAI.create(op_str,(RenderedImage)image, kernel, null);
			image_out_aux.setData(renderedOp_aux.getData());
			renderedOp = JAI.create(op_str1, image_out_aux, kernel, null);
		}
		
		image_out.setData(renderedOp.getData());
		
		return image_outPlus.getProcessor();
	}*/
}