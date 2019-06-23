package factory;

import game.arenas.Arena;
import game.racers.Racer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


/**
 * This RacePanel class create jpanel racer
 * @version 1.2 16 May 2018
 * @author Aviv Yehuda, Michael Amar
 * @ID 304797376, 308104215
 * @Campus Beer-Sheva
 */
public class RacePanel extends JPanel{
	 private BufferedImage img_bg = null;
	 private Racer racer = null;	
	 private Arena arena = null; 
	 private boolean bg_flag = true;
	 private boolean r_flag = true;
	
	    public RacePanel() 
	    {
	    	super(new FlowLayout());
		   
	    }

	     @Override
	     public void paintComponent(Graphics g) 
	     {
	         super.paintComponent(g);  //THIS LINE WAS ADDED
	         	         
	         g.drawImage(img_bg, 0, 0,getWidth(),getHeight(), this); // see javadoc for more info on the parameters
	        
	         if(racer != null){        	 
	        	 	for(int i = 0 ; i < arena.getAllRacers().size() ; i++)
	        	 		arena.getAllRacers().get(i).drawObject(g);
	         }
	     }

	     /**
	      * load the background
	      * @param path
	      */
	     public void loadBackground(String path)
	     {	    	
	    	 try {
	    		if(bg_flag)
	    			img_bg = ImageIO.read(new File(path));
	    		else
	    			img_bg = null;
	    		
	    		repaint();
	    		
	        } catch (IOException ex) {
		            System.out.println("Cannot open image! -> Please check path (factory.RaceBuilder.PATH)");
		        }
	     }

	     /**
	      * load the racer
	      * @param arena
	      * @param racer
	      * @param path
	      * @param typeRacer
	      * @param color
	      */
		public void loadRacer(Arena arena, Racer racer, String path, String typeRacer, String color) {
	
	    	 try {
	    		 if(r_flag)
	    		 {	
	    			 this.arena = arena;
	    			 this.racer = racer;
	    			 System.out.println(arena);
	    			 System.out.println(racer);
	    			 racer.setImage(ImageIO.read(new File(path + racer.className() + racer.getColor() + ".png")));

	    		 }
	    		 else
	    			 racer.setImage(null);
	    		 
	    		 repaint();

		        } catch (IOException ex) {
			            System.out.println("Cannot open image!");
			        }
	    	 
			}
		
		public boolean isR_flag() {
			return r_flag;
		}

		public void setR_flag(boolean r_flag) {
			this.r_flag = r_flag;
		}

		public boolean isBg_flag() {
			return bg_flag;
		}

		public void setBg_flag(boolean bg_flag) {
			this.bg_flag = bg_flag;
		}



		
		

	
}
