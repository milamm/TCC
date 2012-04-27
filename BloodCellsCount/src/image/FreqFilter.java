package image;

import ij.ImagePlus;
import ij.process.ByteProcessor;
import ij.process.ImageProcessor;

/**
 * Class containing method to apply a frequency filter to an image.
 * 
 * @author Simon Horne.
 */
public class FreqFilter {

	/**
	 * Method to apply a high or low pass filter to an image.
	 * 
	 * @param input
	 *            ImageProcessor representing the image.
	 * @param h
	 *            boolean true - lowpass, false - highpass.
	 * @return ImageProcessor representing new image.
	 */
	public static ImagePlus filter(ImagePlus input, boolean flag, int r) {
		int w = input.getWidth();
		int h = input.getHeight();
		ImagePlus output = input;
		output.show();
		//output.setImage(input);
		
		ByteProcessor input_aux = DCToCentre(input.getProcessor());
		ByteProcessor output_aux = new ByteProcessor(input.getWidth(),input.getHeight());
		ByteProcessor outputProc = new ByteProcessor(input.getWidth(),input.getHeight());
		
		int i2, j2;
		double r2 = r * r;
		if (flag) {// if low pass filter
			for (int j = 0; j < w; ++j) {
				for (int i = 0; i < h; ++i) {
					if (i >= h / 2)
						i2 = i - h;
					else
						i2 = i;
					if (j >= w / 2)
						j2 = j - w;
					else
						j2 = j;
					double d2 = i2 * i2 + j2 * j2;
					if (d2 > r2)
						output_aux.putPixel(j, i, 0);
					else
						output_aux.putPixel(j, i, input_aux.getPixel(j,i));
				}
			}
		} else {// else high pass filter
			for (int j = 0; j < w; ++j) {
				for (int i = 0; i < h; ++i) {
					if (i >= h / 2)
						i2 = i - h;
					else
						i2 = i;
					if (j >= w / 2)
						j2 = j - w;
					else
						j2 = j;
					double d2 = i2 * i2 + j2 * j2;
					if (d2 > r2)
						output_aux.putPixel(j, i, input_aux.getPixel(j,i));
					else
						output_aux.putPixel(j, i, 0);
				}
			}
		}
		output.setProcessor(DCToCentre(output_aux));
		return output;
	}
	
	public static ByteProcessor DCToCentre(ImageProcessor input) {
		int width = input.getWidth();
		int height = input.getHeight();
		ByteProcessor output = new ByteProcessor(width,height);
		int x = width / 2;
		int y = height / 2;
		int i2, j2;
		for (int j = 0; j < height; ++j) {
			for (int i = 0; i < width; ++i) {
				i2 = i + x;
				j2 = j + y;
				if (i2 >= width)
					i2 = i2 % width;
				if (j2 >= height)
					j2 = j2 % height;
				output.putPixel(j, i, input.getPixel(j2, i2));
			}
		}
		return output;
	}

	/**
	 * Method to apply a high or low pass filter to an image.
	 * 
	 * @param input
	 *            TwoDArray representing the image.
	 * @param h
	 *            boolean true - highpass, false - lowpass.
	 * @return TwoDArray representing new image.
	 */
	/*public static TwoDArray filter(TwoDArray input, boolean flag, int r) {
		TwoDArray output = new TwoDArray(input.values, input.size, input.size);

		int i2, j2;
		double r2 = r * r;
		if (flag) {// if low pass filter
			for (int j = 0; j < input.size; ++j) {
				for (int i = 0; i < input.size; ++i) {
					if (i >= input.size / 2)
						i2 = i - input.size;
					else
						i2 = i;
					if (j >= input.size / 2)
						j2 = j - input.size;
					else
						j2 = j;
					double d2 = i2 * i2 + j2 * j2;
					if (d2 > r2)
						output.values[i][j] = new ComplexNumber(0, 0);
					else
						output.values[i][j] = input.values[i][j];
				}
			}
		} else {// else high pass filter
			for (int j = 0; j < input.size; ++j) {
				for (int i = 0; i < input.size; ++i) {
					if (i >= input.size / 2)
						i2 = i - input.size;
					else
						i2 = i;
					if (j >= input.size / 2)
						j2 = j - input.size;
					else
						j2 = j;
					double d2 = i2 * i2 + j2 * j2;
					if (d2 > r2)
						output.values[i][j] = input.values[i][j];
					else
						output.values[i][j] = new ComplexNumber(0, 0);
				}
			}
		}
		return output;
	}*/
}
