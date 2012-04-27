import ij.IJ;
import ij.ImagePlus;
import ij.gui.Plot;
import ij.plugin.ContrastEnhancer;
import ij.process.ByteProcessor;
import ij.process.ImageProcessor;
import image.FreqFilter;
import image.ImageOperations;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
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
		
		diameterLabel = new JLabel("Diametro maximo da hemácia(px):");
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
	    	 analiseImage();
	 }
	 
	 private void analiseImage() {
		 try {
			 InputStream is = new BufferedInputStream(new FileInputStream(imageStr.getText()));
			 int diameter = Integer.parseInt(diameterStr.getText());
			 BufferedImage image = ImageIO.read(is);
			 new ImagePlus("Imagem Inicial",image).show();
			 
			// Convert to YCbCr
			 ByteProcessor[] YCbCr = ImageOperations.convertfromRGBtoYCbCr(image);
			 new ImagePlus("Plano Cr",YCbCr[2]).show();
			 
			// Low-pass filter
		    IJ.run("FFT");
		    ImagePlus fftImPlus = (ImagePlus) IJ.getImage().clone();
		    ImageProcessor fftIm = fftImPlus.getProcessor();
		    //fftIm.show();
		    
		    int cutoffFreq = 100;
		    //double[] fftProfile = fftIm.getLine(0, 0, fftIm.getWidth()-1, fftIm.getHeight()-1);
		    double[] fftProfile = fftIm.getLine(0, fftIm.getHeight()/2, fftIm.getWidth()-1, fftIm.getHeight()/2);
		    double[] xValues = new double[fftProfile.length];
		   
		    for(int i = 0; i < xValues.length; i++)
		    	xValues[i] = i;
		    
		    for(int i = 0; i < fftProfile.length; i++) { 
		    	if(fftProfile[i] >= 125) {
		    		cutoffFreq = fftProfile.length/2-(i-1);
		    		break;
		    	}
			}
		    Plot fftProfilePlot = new Plot("FFT Profile", "", "Magnitude", xValues, fftProfile);
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
		    new ImagePlus("Watershed", watershedIm).show();
		    int RBCcount = ImageOperations.countRBC(watershedIm, diameter);
		    JOptionPane.showMessageDialog(null, "Há "+RBCcount+" hemácias na amostra.");
		 
		 } catch (IOException e) {
			 e.printStackTrace();
		 }
	 }
}
