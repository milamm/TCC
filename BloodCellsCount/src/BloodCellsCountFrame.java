import ij.IJ;
import ij.ImagePlus;
import ij.WindowManager;
import ij.gui.Plot;
import ij.plugin.ContrastEnhancer;
import ij.plugin.filter.RankFilters;
import ij.process.ByteProcessor;
import ij.process.ImageProcessor;
import image.FreqFilter;
import image.ImageOperations;
import image.MorphologicalOp;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.media.jai.KernelJAI;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import Watershed.Watershed_Algorithm;


public class BloodCellsCountFrame extends JFrame implements ActionListener {
	private JLabel imageLabel, diameterLabel;
	//private JFileChooser image;
	private JTextField imageStr, diameterStr;
	private JButton okBtn;
	private BufferedImage image;
	private Integer diameter;
	
	public BloodCellsCountFrame() {
		setTitle("Contagem de hemacias");
		setBounds(100,50,400,230);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Container c = getContentPane();
		c.setLayout(null);
		
		imageLabel = new JLabel("Imagem:");
		imageLabel.setBounds(16, 16, 300, 21);
		c.add(imageLabel);

		imageStr = new JTextField();
		imageStr.setBounds(16, 46, 300, 21);
		c.add(imageStr);
		
		diameterLabel = new JLabel("Diametro maximo da hem�cia(px):");
		diameterLabel.setBounds(16, 76, 300, 21);
		c.add(diameterLabel);
		
		diameterStr = new JTextField();
		diameterStr.setBounds(16, 106, 300, 21);
		c.add(diameterStr);
		
		okBtn = new JButton("OK");
		okBtn.setBounds(290, 140, 80, 30);
		okBtn.addActionListener(this);
		c.add(okBtn);
		
		setVisible(true);
	}
	
	 public void actionPerformed( ActionEvent e) { 
	     if (e.getSource() == okBtn)
	    	 setInput();
	    	 analiseImage();
	 }
	 
	 public void setInput() {
		 try {
		 	InputStream is = new BufferedInputStream(new FileInputStream(imageStr.getText()));
		 	this.diameter = Integer.parseInt(diameterStr.getText());
		 	this.image = ImageIO.read(is);
	 	 } catch (IOException e) {
			 e.printStackTrace();
		 }
	 }
	 
	 public void testAnaliseImage(String imageStr, Integer diameter) {
		 try {
			 InputStream is = new BufferedInputStream(new FileInputStream(imageStr));
			 this.diameter = diameter;
			 this.image = ImageIO.read(is);
			 analiseImage();
	 	} catch (IOException e) {
		 	e.printStackTrace();
	 	}
	 }

	 private void analiseImage() {
		 
		new ImagePlus("Imagem Inicial",image).show();
			 
		// Convert to YCbCr
		/*ByteProcessor[] YCbCr = ImageOperations.convertfromRGBtoYCbCr(image);
		new ImagePlus("Plano Y", YCbCr[0]).show();
		new ImagePlus("Plano Cb",YCbCr[1]).show();
		new ImagePlus("Plano Cr",YCbCr[2]).show();*/
		
		//***************************************************************************************
		// Convert to HSB
		ByteProcessor[] HSB = ImageOperations.convertfromRGBtoHSB(image);
		ImageProcessor imageH = HSB[0];
		ImagePlus imagePlusH = new ImagePlus("Plano H",imageH);
		imagePlusH.show();
		ImageProcessor imageS = HSB[1];
		ImagePlus imagePlusS = new ImagePlus("Plano S",imageS);
		imagePlusS.show();
		ImageProcessor imageB = HSB[2];
		ImagePlus imagePlusB = new ImagePlus("Plano B",imageB);
		imagePlusB.show();
		//***************************************************************************************
		
		ImageProcessor enhancedImH = imageImprovement(imageH);
		ImageProcessor enhancedImS = imageImprovement(imageS);
	    
	    //***************************************************************************************
		//Otsu Threshold - Plano H
	    ImageProcessor otsuThresholdImH = ImageOperations.doOtsuThresholding(enhancedImH);
		new ImagePlus("Otsu Threshold",otsuThresholdImH).show();
		//***************************************************************************************
		
		//***************************************************************************************
		// Mask
		ImageProcessor maskedIm = ImageOperations.AND(enhancedImS, otsuThresholdImH);
		new ImagePlus("Mask",maskedIm).show();
		//***************************************************************************************
		
		//***************************************************************************************
		// Equalize Histogram - Plano S
		//ContrastEnhancer contrastEnhancer = new ContrastEnhancer();
	    /*ImageProcessor enhancedImageS = ImageOperations.copyImage(maskedIm);//(ImageProcessor) filteredImPlus.getProcessor().clone();
	    contrastEnhancer.equalize(enhancedImageS);
	    new ImagePlus("Plano S - Enhanced Image", enhancedImageS).show();*/
			    
		//***************************************************************************************
		// Otsu Threshold - Plano S - segment RBCs
	    ImageProcessor thresholdImS = ImageOperations.doOtsuThresholding(maskedIm);
		new ImagePlus("Otsu Threshold",thresholdImS).show();
		//***************************************************************************************
		
		//***************************************************************************************
		// Morphological Operation
		
		//float[] kernel_values = {0,1,0,1,1,1,0,1,0};
		//float[] kernel_values = {1,1,1,1,1,1,1,1,1};
		//KernelJAI kernel = new KernelJAI(3, 3, kernel_values);
		KernelJAI kernel = MorphologicalOp.createKernel(3, 3, MorphologicalOp.RECT);
	    //ImageProcessor openedIm = ImageOperations.switchBlackWhite(thresholdImS);
		ImageProcessor thresholdImS_cp = ImageOperations.copyImage(thresholdImS);
		
		ImageProcessor morphoIm = MorphologicalOp.open(thresholdImS_cp, kernel);
		new ImagePlus("Morfological Operation",morphoIm).show();
		
		//ImageProcessor morphoIm1 = MorphologicalOp.erode(morphoIm, kernel);
		//new ImagePlus("Morfological Operation",morphoIm1).show();
		
		ImageProcessor fillHolesIm = MorphologicalOp.fillHoles(morphoIm);
		new ImagePlus("Fill Holes",fillHolesIm).show();
	    
		//***************************************************************************************
		
	    /*int threshold = 50;
	    ImageProcessor thresholdIm = ImageOperations.doBinaryThresholding(maskedIm, threshold);
	    new ImagePlus("Thresholded Image",thresholdIm).show();*/
	    
	    //ImageProcessor distanceIm = ImageOperations.distanceTransform(fillHolesIm);
	    //new ImagePlus("Distance Image",distanceIm).show();
	    
	    // Watershed Segmentation 
	    //ImageProcessor watershedIm = Watershed_Algorithm.performWatershed(distanceIm);
	    //new ImagePlus("Watershed", watershedIm).show();
	    /*int RBCcount = ImageOperations.countRBC(watershedIm, diameter);
	    JOptionPane.showMessageDialog(null, "H� "+RBCcount+" hem�cias na amostra.");*/
		 
	 }
	 
	 private ImageProcessor imageImprovement(ImageProcessor image) {
		//***************************************************************************************
		// Smooth
		ImageProcessor filteredImage = ImageOperations.copyImage(image);
		filteredImage.smooth();
		ImagePlus filteredImPlus = new ImagePlus("Smooth", filteredImage);
		filteredImPlus.show();
		
		//***************************************************************************************
			
		//***************************************************************************************
		// Median Filter - 3x3
		/*
		ImageProcessor filteredImage = ImageOperations.copyImage(imageH);
		new RankFilters().rank(filteredImage, 3, RankFilters.MEDIAN);
		//filteredImage.medianFilter();
		ImagePlus filteredImPlus = new ImagePlus("Plano H - Median Filter", filteredImage);
		filteredImPlus.show();
		*/
		//***************************************************************************************
				 
		//*************************************************************************************** 
		// Low-pass filter 
		
		/*ImagePlus fftImPlus = ImageOperations.runFFT(imagePlusH);
		int cutOffFreq = ImageOperations.computeCutOffFrequency(fftImPlus.getProcessor());
		ImagePlus filteredFFTImPlus = FreqFilter.filter(fftImPlus, true, cutOffFreq);
		ImagePlus filteredImPlus = ImageOperations.runInverseFFT(filteredFFTImPlus);*/
		
		//***************************************************************************************
						
		//***************************************************************************************
		// Equalize Histogram
		ContrastEnhancer contrastEnhancer = new ContrastEnhancer();
	    contrastEnhancer.equalize(filteredImPlus.getProcessor());
	    ImageProcessor enhancedImage = ImageOperations.copyImage(filteredImPlus.getProcessor());
	    new ImagePlus("Enhanced Image", enhancedImage).show();
	    //***************************************************************************************
	    
	    return enhancedImage;
	 }
	 
	//***************************************************************************************
			// Smooth
	/*		ImageProcessor filteredImage = (ImageProcessor) imageH.clone();
			filteredImage.smooth();
			ImagePlus filteredImPlus = new ImagePlus("Plano H - Smooth", filteredImage);
			filteredImPlus.show();
			
			ImageProcessor filteredImageS = (ImageProcessor) imageS.clone();
			filteredImageS.smooth();
			ImagePlus filteredImPlusS = new ImagePlus("Plano S - Smooth", filteredImageS);
			filteredImPlusS.show();*/
			
			//***************************************************************************************
			
			//***************************************************************************************
			// Median Filter - 3x3
			/*
			 * 
			ImageProcessor filteredImage = ImageOperations.copyImage(imageH);
			new RankFilters().rank(filteredImage, 3, RankFilters.MEDIAN);
			//filteredImage.medianFilter();
			ImagePlus filteredImPlus = new ImagePlus("Plano H - Median Filter", filteredImage);
			filteredImPlus.show();
			
			ImageProcessor filteredImageS = ImageOperations.copyImage(imageS);
			new RankFilters().rank(filteredImage, 3, RankFilters.MEDIAN);
			//filteredImageS.medianFilter();
			ImagePlus filteredImPlusS = new ImagePlus("Plano S - Median Filter", filteredImageS);
			filteredImPlusS.show();
			*/
			//***************************************************************************************
				 
			//*************************************************************************************** 
			// Low-pass filter - plano H
			
			/*ImagePlus fftImPlus = ImageOperations.runFFT(imagePlusH);
			int cutOffFreq = ImageOperations.computeCutOffFrequency(fftImPlus.getProcessor());
			ImagePlus filteredFFTImPlus = FreqFilter.filter(fftImPlus, true, cutOffFreq);
			ImagePlus filteredImPlus = ImageOperations.runInverseFFT(filteredFFTImPlus);*/
			
			//***************************************************************************************
			
			//***************************************************************************************
			// Low-pass filter - plano S
			
			/*ImagePlus fftImPlusS = ImageOperations.runFFT(imagePlusS);
			int cutOffFreqS = ImageOperations.computeCutOffFrequency(fftImPlusS.getProcessor());
			ImagePlus filteredFFTImPlusS = FreqFilter.filter(fftImPlusS, true, cutOffFreqS);
			ImagePlus filteredImPlusS = ImageOperations.runInverseFFT(filteredFFTImPlusS);
			*/
			//***************************************************************************************
			
			//***************************************************************************************
			// Equalize Histogram
			/*ContrastEnhancer contrastEnhancer = new ContrastEnhancer();
		    contrastEnhancer.equalize(filteredImPlus.getProcessor());
		    ImageProcessor enhancedImage = (ImageProcessor) filteredImPlus.getProcessor().clone();
		    new ImagePlus("Enhanced Image", enhancedImage).show();*/
		    //***************************************************************************************
}
