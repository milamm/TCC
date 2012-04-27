
public class BloodCellsCount {

	
	public static void main(String[] args) {
			
		BloodCellsCountFrame frame = new BloodCellsCountFrame();
		
		/*try {
		    // Read from an input stream
		    InputStream is = new BufferedInputStream(new FileInputStream("images/hematology.org.normalRBC114.jpg"));
		    BufferedImage image = ImageIO.read(is);
		    
		    new ImagePlus("Imagem Inicial",image).show();
		    
		    // Convert to YCbCr
		    ByteProcessor[] YCbCr = ImageOperations.convertfromRGBtoYCbCr(image);
		    new ImagePlus("Plano Cb",YCbCr[2]).show();
		    		    
		    // Low-pass filter
		    IJ.run("FFT");
		    ImagePlus fftImPlus = (ImagePlus) IJ.getImage().clone();
		    ImageProcessor fftIm = fftImPlus.getProcessor();
		    //fftIm.show();
		    
		    int cutoffFreq = 100;
		    double[] fftProfile = fftIm.getLine(0, 0, fftIm.getWidth()-1, fftIm.getHeight()-1);
		    double[] xValues = new double[fftProfile.length];
		   
		    for(int i = 0; i < xValues.length; i++)
		    	xValues[i] = i;
		    
		    for(int i = 0; i < fftProfile.length; i++) { 
		    	if(fftProfile[i] >= 110) {
		    		cutoffFreq = fftProfile.length/2-(i-1);
		    		break;
		    	}
			}
		    Plot fftProfilePlot = new Plot("FFT Profile", "", "", xValues, fftProfile);
		    fftProfilePlot.show();
		    ImagePlus filteredfftImPlus = FreqFilter.filter(fftImPlus, true, cutoffFreq);
		    //filteredfftImPlus.show();
		    IJ.getImage().setImage(filteredfftImPlus);
		    IJ.run("Inverse FFT");
		    ImageProcessor filteredImage = IJ.getImage().getProcessor();
		    new ImagePlus("Filtered image", filteredImage).show();
		    //ImagePlus filteredImPlus = new ImagePlus("Filtereded Image",filteredImage);
		    
		    ContrastEnhancer contrastEnhancer = new ContrastEnhancer();
		    contrastEnhancer.equalize(filteredImage);
		    		   
		    int threshold = 130;
		    ImageProcessor thresholdIm = ImageOperations.doThresholding(filteredImage, threshold);
		    new ImagePlus("Thresholded Image",thresholdIm).show();
		    
		    // Watershed Segmentation 
		    ImageProcessor watershedIm = Watershed_Algorithm.performWatershed(filteredImage);
		    int RBCcount = ImageOperations.countRBC(watershedIm,28);
		    JOptionPane.showMessageDialog(null, "Há "+RBCcount+" hemácias na amostra.");

		} catch (IOException e) {
			e.printStackTrace();
		}*/
		
		//System.exit(0);

	}

}
