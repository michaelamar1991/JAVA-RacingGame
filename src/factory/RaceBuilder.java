package factory;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Iterator;

import javafx.scene.control.RadioButton;

import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

import builder.CarsRace;
import builder.CarsRaceBuilder;
import builder.CarsRaceEngineer;
import builder.OldCarsRaceBuilder;

import com.sun.java.swing.plaf.windows.resources.windows;
import com.sun.jndi.toolkit.ctx.Continuation;

import game.arenas.Arena;
import game.arenas.exceptions.RacerLimitException;
import game.arenas.exceptions.RacerTypeException;
import game.racers.Racer;
import game.racers.Wheeled;
import game.racers.air.Airplane;
import game.racers.air.Helicopter;
import game.racers.clone.CloneFactory;
import game.racers.decorator.ColoredRacer;
import game.racers.decorator.IRacer;
import game.racers.decorator.WheeledRacer;
import game.racers.land.Bicycle;
import game.racers.land.Car;
import game.racers.land.Horse;
import game.racers.sea.RowBoat;
import game.racers.sea.SpeedBoat;
import sun.font.Decoration;
import sun.invoke.empty.Empty;
import utilities.EnumContainer;
import utilities.Point;
import utilities.EnumContainer.Color;

/**
 * This RaceBuilder class Create Arena/Racer objects used by reflection and singleton pattern.
 * @version 1.2 16 May 2018
 * @author Aviv Yehuda, Michael Amar
 * @ID 304797376, 308104215
 * @Campus Beer-Sheva
 */
public class RaceBuilder extends JFrame {

	private static final long serialVersionUID = 1L;
	private static RaceBuilder instance;
	private static Arena arena;
	private static RacePanel racePanel;
	private ClassLoader classLoader;
	private Class<?> classObject;
	private Constructor<?> constructor;
	private JTextField arenaLenTField,maxRacersTField, racerNameTField, maxSpeedTField, accelerationTField, wheelsAmountTField, wheelsAmountDecoTField, carsAmountTField;
	private JLabel chooseArenaLbl, arenaLenLbl, maxRacersLbl, chooseRacerLbl, chooseColorLbl, racerNameLbl, maxSpeedLbl, accelerationLbl , wheelsAmountLbl, carsAmountLbl;
	private JButton buildArenaBtn, addRacerBtn, startRaceBtn, showInfoBtn , addDecoBtn,cloneBtn, okDecoBtn,okCloneBtn,carRaceBuilderBtn;
	private JPanel menuPanel;
	private JTable infoTable = new JTable();

	private String[] arenaStrings = {"AerialArena", "LandArena", "NavalArena"};
	private String[] racerStrings = {"Airplane","Helicopter", "Bicycle", "Car", "Horse", "RowBoat", "SpeedBoat"};
	private String[] colorStrings = {"Black", "Blue", "Green", "Red", "Yellow"};
	private static JComboBox<String> arenaCombo, racerCombo, colorCombo , colorComboDec,colorComboClone;
	private static final String PATH = "C:\\Users\\אביב\\workspace\\Task3\\src\\icons\\";
	private int width;
	private int height;
	private Color c=null;
	private IRacer r;

	/**
	 * RaceBuilder getin..
	 * @return
	 */
	public static RaceBuilder getInstance() {
		if (instance == null) {
			instance = new RaceBuilder();
		}
		return instance;
	}

	/**
	 * c'tor RaceBuilder
	 */
	private RaceBuilder() {
		super("Race");
		setWidth(1128);
		setHeight(820);
		setSize(getWidth(),getHeight());
		setLocationRelativeTo(null); //frame appear centered screen
		setLayout(new BorderLayout(1,0));
		
		menuPanel = new JPanel(new GridLayout(37,1,5,5));
		racePanel = new RacePanel();
		
		//Create borders for the panels and set size for buttons
		Border raisedetched = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
		menuPanel.setBorder(raisedetched);
		racePanel.setBorder(raisedetched);
		

		//make car race builder sub menu
		carsAmountTField = new JTextField(2);
		carsAmountLbl = new JLabel("Cars amount:");
		
		
		//make arena sub menu
		chooseArenaLbl = new JLabel("Choose arena:");
		arenaLenLbl = new JLabel("Arena	Length:");
		maxRacersLbl = new JLabel("Max racers number:");
		arenaLenTField = new JTextField("1000",8);
		maxRacersTField = new JTextField("8", 8);
		arenaCombo = new JComboBox<String>(arenaStrings);
		arenaCombo.setSelectedIndex(0);
		buildArenaBtn = new JButton("Build Arena");
		
		
		carRaceBuilderBtn = new JButton("Build Car Race");
		
		//make racer sub menu
		chooseRacerLbl = new JLabel("Choose racer:");
		racerCombo = new JComboBox<String>(racerStrings);
		racerCombo.setSelectedIndex(0);
		chooseColorLbl = new JLabel("Choose color:");
		colorCombo = new JComboBox<String>(colorStrings);
		colorCombo.setSelectedIndex(0);
		colorComboDec = new JComboBox<String>(colorStrings);
		colorComboDec.setSelectedIndex(0);
		colorComboClone = new JComboBox<String>(colorStrings);
		colorComboClone.setSelectedIndex(0);
		racerNameLbl = new JLabel("Racer name:");
		racerNameTField = new JTextField(8);
		maxSpeedLbl = new JLabel("Max speed:");
		maxSpeedTField = new JTextField(8);
		accelerationLbl = new JLabel("Acceleration:");
		accelerationTField = new JTextField(8);
		wheelsAmountLbl = new JLabel("Wheels amount:");
		wheelsAmountTField = new JTextField("3",8);
		wheelsAmountDecoTField = new JTextField(2);
		addRacerBtn = new JButton("Add racer");
		addDecoBtn = new JButton("Decorate");
		cloneBtn = new JButton("Clone racer");
		okDecoBtn = new JButton("OK");
		
		//make start/info buttons
		startRaceBtn = new JButton("Start race");
		showInfoBtn = new JButton("Show info");
		
		//add arena components to panel
		menuPanel.add(chooseArenaLbl);
		menuPanel.add(arenaCombo);
		menuPanel.add(arenaLenLbl);
		menuPanel.add(arenaLenTField);	
		menuPanel.add(maxRacersLbl);
		menuPanel.add(maxRacersTField);
		menuPanel.add(buildArenaBtn);
		
		//separator&gap
		menuPanel.add(new JLabel()); //gap
		menuPanel.add(new JSeparator(JSeparator.HORIZONTAL));

		//add racer components to panel
		menuPanel.add(chooseRacerLbl);
		menuPanel.add(racerCombo);
		menuPanel.add(chooseColorLbl);
		menuPanel.add(colorCombo);
		menuPanel.add(racerNameLbl);
		menuPanel.add(racerNameTField);
		menuPanel.add(maxSpeedLbl);
		menuPanel.add(maxSpeedTField);
		menuPanel.add(accelerationLbl);
		menuPanel.add(accelerationTField);
		menuPanel.add(wheelsAmountLbl);
		menuPanel.add(wheelsAmountTField);
		menuPanel.add(new JLabel()); //gap
		menuPanel.add(addRacerBtn);
		menuPanel.add(addDecoBtn);
		menuPanel.add(cloneBtn);
		
		//separator&gap
		menuPanel.add(new JLabel()); //gap
		menuPanel.add(new JSeparator(JSeparator.HORIZONTAL));
		
		//add CarRaceBuilder component to panel
		menuPanel.add(new JLabel("**Default builder Cars Race**"));
		menuPanel.add(carsAmountLbl);
		menuPanel.add(carsAmountTField);
		menuPanel.add(carRaceBuilderBtn);
		
		//separator&gap
		menuPanel.add(new JLabel()); //gap
		menuPanel.add(new JSeparator(JSeparator.HORIZONTAL));
		
		//add start and info btns
		menuPanel.add(startRaceBtn);
		menuPanel.add(showInfoBtn);
		
		//add panels to frame
		add(racePanel);
		add(menuPanel, BorderLayout.EAST);
		
		
		
		//////////////
		

		
//		String string = "";
//		if(racerCombo.getSelectedItem() == "Airplane" || racerCombo.getSelectedItem() == "Helicopter")
//			
//		else if(racerCombo.getSelectedItem() == "Bicycle" || racerCombo.getSelectedItem() == "Car" || racerCombo.getSelectedItem() == "Horse")
//			checkArenaStr = "LandArena";
//		else if(racerCombo.getSelectedItem() == "RowBoat" || racerCombo.getSelectedItem() == "SpeedBoat")
//			checkArenaStr = "NavaArena";
//		
		//////////////

		MyHandler handler = new MyHandler();
		try{
		
		buildArenaBtn.addActionListener(handler);
		addRacerBtn.addActionListener(handler);
		startRaceBtn.addActionListener(handler);
		showInfoBtn.addActionListener(handler);
		addDecoBtn.addActionListener(handler);
		//okDecoBtn.addActionListener(handler);
		cloneBtn.addActionListener(handler);
		racerCombo.addActionListener(handler);
		carRaceBuilderBtn.addActionListener(handler);
		}
			
		catch(RuntimeException c1){}
		
		classLoader = ClassLoader.getSystemClassLoader();
	}
	
	/**
	 * build arena
	 * @param arenaType
	 * @param length
	 * @param maxRacers
	 * @return
	 * @throws ClassNotFoundException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	@SuppressWarnings("static-access")
	public Arena buildArena(String arenaType, double length, int maxRacers)
			throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException 
	{		
		
		racePanel.loadBackground(PATH + arenaCombo.getSelectedItem() + ".jpg");

	
//		this.classObject = classLoader.loadClass(arenaType);
//		this.constructor = classObject.getConstructor(double.class, int.class);
//		Arena arena = (Arena) this.constructor.newInstance(length, maxRacers);
		
		//here we used factory method design pattern
		ArenaFactory arenaFactory = new ArenaFactory();
		Arena arena = arenaFactory.makeArena(arenaType,length,maxRacers);
		
		
		this.arena = arena;
		return arena;

	}

	/**
	 * build racer
	 * @param racerType
	 * @param name
	 * @param maxSpeed
	 * @param acceleration
	 * @param color
	 * @return
	 * @throws ClassNotFoundException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws RacerLimitException
	 * @throws RacerTypeException
	 */
	public Racer buildRacer(String racerType, String name, double maxSpeed, double acceleration,
			utilities.EnumContainer.Color color)
			throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException, RacerLimitException, RacerTypeException 
	{
		if(arena.getMAX_RACERS() == arena.getAllRacers().size())
			throw new RacerLimitException(0, 0);

		
		String checkArenaStr = "";
		if(racerCombo.getSelectedItem() == "Airplane" || racerCombo.getSelectedItem() == "Helicopter")
			checkArenaStr = "AerialArena";
		else if(racerCombo.getSelectedItem() == "Bicycle" || racerCombo.getSelectedItem() == "Car" || racerCombo.getSelectedItem() == "Horse")
			checkArenaStr = "LandArena";
		else if(racerCombo.getSelectedItem() == "RowBoat" || racerCombo.getSelectedItem() == "SpeedBoat")
			checkArenaStr = "NavaArena";
		
		
		

		if(!arena.getClass().getSimpleName().equals(checkArenaStr))
		{
			if(arena.getClass().getSimpleName().equals("LandArena") && Integer.valueOf(wheelsAmountTField.getText())>0)
			{
				repaint();
			}
			else
				throw new RacerTypeException(" ", " ");
		}
	
//		 if(!arena.getClass().getSimpleName().equals(checkArenaStr))
//				throw new RacerTypeException(" ", " ");
		
		this.classObject = classLoader.loadClass(racerType);
		this.constructor = classObject.getConstructor(String.class, double.class, double.class,
				utilities.EnumContainer.Color.class);
		
		Racer racer = (Racer) this.constructor.newInstance(name, maxSpeed, acceleration, color);
		
		new WheeledRacer(racer, Integer.valueOf(wheelsAmountTField.getText())); // <<---


		arena.addRacer(racer);
		racePanel.loadRacer(arena,racer,PATH ,racer.getClass().getSimpleName(), color.toString());
		return racer;
	}
	
	
	
	synchronized public void decorateFrame() {
		JFrame decFrame = new JFrame("Decorate Racer");
		JPanel p1 = new JPanel(new FlowLayout());
		JPanel p2 = new JPanel(new FlowLayout());
		JPanel p3 = new JPanel(new FlowLayout());
		JPanel p4 = new JPanel(new FlowLayout());
		
		decFrame.setLayout(new GridLayout(4,5,5,5));
		
		p1.add(new JLabel("Please choose racer for decorate:"));		
		ButtonGroup groupBtns = new ButtonGroup();
		
		for(int i=0; i<arena.getAllRacers().size(); i++)
		{
			JRadioButton b = new JRadioButton(String.valueOf(arena.getAllRacers().get(i).getSerialNumber()));
			groupBtns.add(b);
			p1.add(b);
			//arena.getHt().get(arena.getAllRacers().get(i).getSerialNumber());
		}
		p2.add(new JLabel("Choose color:"));
		p2.add(colorComboDec);
		p3.add(new JLabel("Wheels amount:"));
		p3.add(wheelsAmountDecoTField);
		p4.add(okDecoBtn);
		
		
		decFrame.add(p1, BorderLayout.NORTH);
		decFrame.add(p2, BorderLayout.AFTER_LAST_LINE);
		decFrame.add(p3, BorderLayout.AFTER_LAST_LINE);
		decFrame.add(p4, BorderLayout.AFTER_LAST_LINE);
		
		decFrame.setLocationRelativeTo(null);
		decFrame.pack();
		decFrame.setResizable(false);
		decFrame.setVisible(true);
		
		
		okDecoBtn.addActionListener(new ActionListener() {
			
			@Override
			synchronized public void actionPerformed(ActionEvent e) {
				if(e.getSource() == okDecoBtn)
				{			
					String textChosenBtn = null;
					if(groupBtns.getSelection() != null)
					{
						
						for (Enumeration<AbstractButton> buttons = groupBtns.getElements(); buttons.hasMoreElements();) 
						{
			            	AbstractButton button = buttons.nextElement();
			
			            	if (button.isSelected()) 
			            		textChosenBtn =  button.getText();
				            	
				        }

						
						for(int i=0; i<arena.getAllRacers().size(); i++)
						{
							if(!containsOnlyNumbers(wheelsAmountDecoTField.getText()))
							{
								JOptionPane.showMessageDialog(null, "Invalid Wheels amount, try again.","Message",JOptionPane.INFORMATION_MESSAGE);
								break;
							}
							
							if(arena.getAllRacers().get(i).getSerialNumber() == Integer.valueOf(textChosenBtn))
							{
								//r=arena.getAllRacers().get(i);
								r = RaceBuilder.getArena().getHt().get(Integer.valueOf(textChosenBtn));
								System.out.println(r);
								new ColoredRacer(arena.getAllRacers().get(i), chosenColor(colorComboDec));
								new WheeledRacer(arena.getAllRacers().get(i), Integer.valueOf(wheelsAmountDecoTField.getText())); // <<---


								
								
								racePanel.loadRacer(arena, (Racer) arena.getAllRacers().get(i),PATH ,arena.getAllRacers().get(i).getClass().getSimpleName(), chosenColor(colorComboDec).toString());
								JOptionPane.showMessageDialog(null, "Racer #"+ Integer.valueOf(textChosenBtn) +" changed color to "+chosenColor(colorComboDec),"Message",JOptionPane.INFORMATION_MESSAGE);
								break;
								
							}
						}
						
					
					}
					else
					{
						JOptionPane.showMessageDialog(null, "Please choose racer first!","Message",JOptionPane.INFORMATION_MESSAGE);
					}
					groupBtns.clearSelection();
					
				}
				
			}
		});
		
	
	}	

	private class MyHandler implements ActionListener
	{
		public void addRacerToArena(Color color, String str1)
		{
			try {
				if(checkRacerInputs())
				{
					c=color;
					racePanel.setR_flag(true);
					r =buildRacer(str1, racerNameTField.getText(), Double.valueOf(maxSpeedTField.getText()), 
							Double.valueOf(accelerationTField.getText()), c);

				}

			
			} catch (ClassNotFoundException | NoSuchMethodException
					| SecurityException | InstantiationException
					| IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | RacerLimitException | RacerTypeException e1) {
				
				}
		}
		
		
		
//		private void decorateRacer() {
//			
//			JFrame decFrame = new JFrame("Decorate Racer");
//			JPanel p1 = new JPanel(new FlowLayout());
//			JPanel p2 = new JPanel(new FlowLayout());
//			JPanel p3 = new JPanel(new FlowLayout());
//			okDecoBtn2 = new JButton("OK");
//			decFrame.setLayout(new GridLayout(3,5,5,5));
//			
//			
//			p2.add(new JLabel("Choose color:"));
//			p2.add(colorComboDec2);
//			p3.add(okDecoBtn2);
//			
//			
//			decFrame.add(p1, BorderLayout.NORTH);
//			decFrame.add(p2, BorderLayout.AFTER_LAST_LINE);
//			decFrame.add(p3, BorderLayout.AFTER_LAST_LINE);
//
//			decFrame.setLocationRelativeTo(null);
//			decFrame.pack();
//			decFrame.setResizable(false);
//			decFrame.setVisible(true);
//			okDecoBtn2.addActionListener(new ActionListener() {
//				
//				@Override
//				synchronized public void actionPerformed(ActionEvent e) {
//					if(e.getSource() == okDecoBtn2)
//					{			
//						
//						//r = RaceBuilder.getArena().getHt().get());
//						c = chosenColor(colorComboDec2);
//						
//						new ColoredRacer(r, c);
//						racePanel.loadRacer(arena, (Racer) r,PATH ,r.getClass().getSimpleName(), c.toString());
//					
//						
//					}
//					
//				}
//			});
//			
//
//			
//		}
		
		
		
		
		
		
		@Override
		public void actionPerformed(ActionEvent e) {

			double len=0;
			int max=0;
			
			try{
			len = Double.parseDouble(arenaLenTField.getText());
			max = Integer.parseInt(maxRacersTField.getText());
			}catch(Exception c){}
			
			
			String str1 = "";
			if(racerCombo.getSelectedItem() == "Airplane")
				str1 = "game.racers.air.Airplane";
			else if(racerCombo.getSelectedItem() == "Helicopter")
				str1 = "game.racers.air.Helicopter";
			else if(racerCombo.getSelectedItem() == "Bicycle")
				str1 = "game.racers.land.Bicycle";
			else if(racerCombo.getSelectedItem() == "Car")
				str1 = "game.racers.land.Car";
			else if(racerCombo.getSelectedItem() == "Horse")
				str1 = "game.racers.land.Horse";
			else if(racerCombo.getSelectedItem() == "RowBoat")
				str1 = "game.racers.sea.RowBoat";
			else if(racerCombo.getSelectedItem() == "SpeedBoat")
				str1 = "game.racers.sea.SpeedBoat";
			
			
			if(e.getSource() == buildArenaBtn)
			{				
				addRacerBtn.setEnabled(true);
				cloneBtn.setEnabled(true);
				try {
					if(arena!=null) //if arena!=null then cleaning the panel from racers!
					{
						if(arena.getPool() !=null)
							if(!arena.getPool().isShutdown())
							{
								for(Racer r : arena.getAllRacers())
								{
									r.setCurrentSpeed(0);
								}
								arena.getPool().shutdown();

							}
								
							

						
						Racer.initCur();
						if(!arena.getActiveRacers().isEmpty() || !arena.getCompleatedRacers().isEmpty()
								|| !arena.getBrokenRacers().isEmpty() || !arena.getDisabledRacers().isEmpty() || !arena.getAllRacers().isEmpty())
						{
							arena.setActiveRacers(new ArrayList<Racer>());
							arena.setCompleatedRacers(new ArrayList<Racer>());
							arena.setBrokenRacers(new ArrayList<Racer>());
							arena.setDisabledRacers(new ArrayList<Racer>());
							arena.setAllRacers(new ArrayList<Racer>());
							racePanel.setR_flag(false);
						}
						else
							racePanel.setR_flag(true);
					}
					
					if(checkArenaInputs()) //make arena
					{
						if(Double.valueOf(maxRacersTField.getText()) > 13) //if max racers bigger than 13 -> make more place for players
						{
							Arena.setMIN_Y_GAP(39);
							Racer.setSIZE_IMAGE(39);
							Racer.initCur();
						}
					else
					{
						Arena.setMIN_Y_GAP(50);
						Racer.setSIZE_IMAGE(50);
						Racer.initCur();
					}
					racePanel.setBg_flag(true);
					buildArena(arenaCombo.getSelectedItem().toString(), len, max); 
					}
					else // clean arena
					{
						arena = null;
						racePanel.setBg_flag(false);
						racePanel.loadBackground(PATH + arenaCombo.getSelectedItem() + ".jpg");
					}
						
						
				} catch (ClassNotFoundException | NoSuchMethodException
						| SecurityException | InstantiationException
						| IllegalAccessException | IllegalArgumentException
						| InvocationTargetException e1) {
					
				}
			}
			else if(e.getSource() == addRacerBtn)
			{
				
				if(arena != null){
					if (arena.getActiveRacers().size() == 0 && arena.getCompleatedRacers().size() == 0)
					{
						addRacerToArena(chosenColor(colorCombo), str1);
					}
					else if (arena.getActiveRacers().size() > 0 && (!(arena.getActiveRacers().get(0).getCurrentLocation().getX() > 0.00)))
					{
						addRacerToArena(chosenColor(colorCombo), str1);
					}
					else	
						JOptionPane.showMessageDialog(null, "Race already STARTED or COMPLEATED!!","Message",JOptionPane.INFORMATION_MESSAGE);		

				}
				else
			 		JOptionPane.showMessageDialog(null, "Please build arena first!","Message",JOptionPane.INFORMATION_MESSAGE);		
			
				

			}

			
			else if(e.getSource() == addDecoBtn)
			{
				if(getArena() != null && !getArena().getAllRacers().isEmpty())
					decorateFrame();
				else
			 		JOptionPane.showMessageDialog(null, "Please add racer first!","Message",JOptionPane.INFORMATION_MESSAGE);

			}
			
			else if(e.getSource() == cloneBtn)
			{
				if(getArena() != null && !getArena().getAllRacers().isEmpty())
					cloneFrame();
				else
			 		JOptionPane.showMessageDialog(null, "Please add racer first!","Message",JOptionPane.INFORMATION_MESSAGE);

				
				
			}
			
			else if(e.getSource() == startRaceBtn)
			{
				
				if(arena != null)
				{
					if(arena.getActiveRacers().size() >= 1)
					{
						arena.initRace();
						arena.startRace();
						racePanel.repaint();
					}
					else
				 		JOptionPane.showMessageDialog(null, "Can't start race!","Message",JOptionPane.INFORMATION_MESSAGE);
					}
				else
			 		JOptionPane.showMessageDialog(null, "Please build arena first and add racers!","Message",JOptionPane.INFORMATION_MESSAGE);

				
			}
			
			else if(e.getSource() == showInfoBtn)
			{
				if(arena != null)
				{
						JFrame tableFrame = new JFrame("Racers information");
						tableFrame.setSize(600, 333);
						tableFrame.setLayout(new FlowLayout());
						tableFrame.setResizable(false);
						tableFrame.setLocationRelativeTo(null);
						
						String[] columnNames = {"Racer name", "Current speed","Max speed","Current X location", "Finished"};
						
						int size = arena.getAllRacers().size();
						
						Object[][] data = new Object[size][5];

						int j=0;
						int counter=0;
						for(int i=0 ; i<arena.getAllRacers().size() ; i++)
						{
							counter++;
								data[i][j] = new Object();
								data[i][j] = arena.getAllRacers().get(i).getName();
								j++;
								data[i][j] = new Object();
								data[i][j] = arena.getAllRacers().get(i).getCurrentSpeed();
								j++;
								data[i][j] = new Object();
								data[i][j] = arena.getAllRacers().get(i).getMaxSpeed();
								j++;
								data[i][j] = new Object();
								data[i][j] = arena.getAllRacers().get(i).getCurrentLocation().getX();
								j++;
								data[i][j] = new Object();
								if(arena.getAllRacers().get(i).getFakeLocation() == RaceBuilder.getRacePanel().getWidth() - Racer.getSIZE_IMAGE())
										{
											data[i][j] = "Yes";
											arena.getAllRacers().get(i).getCurrentLocation().setX(arena.getLength());
										}
									
								else
										data[i][j] = "No";

								j=0;

						}
						
						//print the info table by rating
						Arrays.sort(data, new java.util.Comparator<Object[]>() {
							@Override
							public int compare(Object[] o2, Object[] o1) {
								Double quantityOne =   (Double) o1[3];
								Double quantityTwo =  (Double) o2[3];
								return quantityOne.compareTo(quantityTwo);
							}
						});

						infoTable = new JTable(data,columnNames);
						infoTable.setPreferredScrollableViewportSize(new Dimension(500,100));
						infoTable.setFillsViewportHeight(true);
						
						JScrollPane scrollpane = new JScrollPane(infoTable);
						
						tableFrame.add(scrollpane);
						tableFrame.pack();
						tableFrame.setVisible(true);

					}
				

				else
			 		JOptionPane.showMessageDialog(null, "Please build arena first and add racers!","Message",JOptionPane.INFORMATION_MESSAGE);
			}
			
			
			else if(e.getSource() == racerCombo)
			{
				if(racerCombo.getSelectedItem().equals("Airplane"))
				{
					wheelsAmountTField.setText(String.valueOf(Airplane.getDefaultWheels()));
				}
				else if(racerCombo.getSelectedItem().equals("Helicopter"))
				{
					wheelsAmountTField.setText(String.valueOf(Helicopter.getDefaultWheels()));
				}
				else if(racerCombo.getSelectedItem().equals("Bicycle"))
				{
					wheelsAmountTField.setText(String.valueOf(Bicycle.getDefaultWheels()));
				}
				else if(racerCombo.getSelectedItem().equals("Car"))
				{
					wheelsAmountTField.setText(String.valueOf(Car.getDefaultWheels()));
				}
				else if(racerCombo.getSelectedItem().equals("Horse"))
				{
					wheelsAmountTField.setText(String.valueOf(Horse.getDefaultWheels()));
				}
				else if(racerCombo.getSelectedItem().equals("RowBoat"))
				{
					wheelsAmountTField.setText(String.valueOf(RowBoat.getDefaultWheels()));
				}
				else if(racerCombo.getSelectedItem().equals("SpeedBoat"))
				{
					wheelsAmountTField.setText(String.valueOf(SpeedBoat.getDefaultWheels()));
				}
				
				
			}
			
			else if(e.getSource() == carRaceBuilderBtn)
			{
				addRacerBtn.setEnabled(false);
				cloneBtn.setEnabled(false);
				
				racePanel.setR_flag(true);

				if(containsOnlyNumbers(carsAmountTField.getText()))
				{
					if(Integer.valueOf(carsAmountTField.getText()) >= 1 && Integer.valueOf(carsAmountTField.getText()) <= 20 )
					{
						arena = null;
						r=null;
						if(Double.valueOf(carsAmountTField.getText()) > 13) //if max racers bigger than 13 -> make more place for players
						{
							Arena.setMIN_Y_GAP(39);
							Racer.setSIZE_IMAGE(39);
							Racer.initCur();
						}
						else
						{
							Arena.setMIN_Y_GAP(50);
							Racer.setSIZE_IMAGE(50);
							Racer.initCur();
						}	
						CarsRaceBuilder oldStyleCarsRace = new OldCarsRaceBuilder();			
						CarsRaceEngineer carsRaceEngineer = new CarsRaceEngineer(oldStyleCarsRace);
						carsRaceEngineer.makeCarsRace();
						CarsRace firstCarsRace = carsRaceEngineer.getCarsRace();
						arena = carsRaceEngineer.getCarsRace().getRaceArena();
						r = carsRaceEngineer.getCarsRace().getRaceCarRacer();
						arena.getActiveRacers().add((Racer) r);
						arena.getAllRacers().add((Racer) r);
						CloneFactory racerMaker = new CloneFactory();
						racePanel.loadBackground(PATH + "LandArena" + ".jpg");
						racePanel.loadRacer(arena,(Racer) r, PATH, null, null);
						
						for(int i=0 ; i< Integer.valueOf(carsAmountTField.getText())-1 ; i++)
						{
							Car clonedCar = (Car) racerMaker.getClone((Racer) r);
							
							clonedCar.setSerialNumber(Racer.getLastSerialNumber());
							((Racer) clonedCar).setName(clonedCar.className() + " #" + clonedCar.getSerialNumber());
							Racer.setLast_cur_y(Racer.getLast_cur_y() + Arena.getMinYGap());
							clonedCar.setCurrentLocation(new Point(Racer.getLast_cur_x(),Racer.getLast_cur_y()));
							arena.getActiveRacers().add((Racer) clonedCar);
							arena.getAllRacers().add((Racer) clonedCar);
							
							racePanel.loadRacer(arena,clonedCar, PATH, null, null);							
							Racer.setLastSerialNumber(Racer.getLastSerialNumber()+1);

						}

						
						JOptionPane.showMessageDialog(null, "Cars Race built successfully!\npress 'Start Race' to begin the race.","Message",JOptionPane.INFORMATION_MESSAGE);

					}
					else
						JOptionPane.showMessageDialog(null, "Invalid Cars amount, try again. (1-20)","Message",JOptionPane.INFORMATION_MESSAGE);

				}
				else
					JOptionPane.showMessageDialog(null, "Invalid Cars amount, try again. (1-20)","Message",JOptionPane.INFORMATION_MESSAGE);

					
				
				

				
				
			}

		
		
		}



		synchronized private void cloneFrame() {
			JFrame decFrame = new JFrame("Clone Racer");
			JPanel p1 = new JPanel(new FlowLayout());
			JPanel p2 = new JPanel(new FlowLayout());
			JPanel p3 = new JPanel(new FlowLayout());
			JPanel p4 = new JPanel(new FlowLayout());
			okCloneBtn = new JButton("OK");
			decFrame.setLayout(new GridLayout(4,5,5,5));
			
			p1.add(new JLabel("Please choose racer to clone:"));		
			ButtonGroup groupBtns = new ButtonGroup();
			
			for(int i=0; i<arena.getAllRacers().size(); i++)
			{
				JRadioButton b = new JRadioButton(String.valueOf(arena.getAllRacers().get(i).getSerialNumber()));
				groupBtns.add(b);
				p1.add(b);
				//arena.getHt().get(arena.getAllRacers().get(i).getSerialNumber());
			}
		
			
			JTextField serialNumTF = new JTextField(2);
			p2.add(new JLabel("Choose color:"));
			p2.add(colorComboClone);
			p3.add(new JLabel("Choose serial number:"));
			p3.add(serialNumTF);
			p4.add(okCloneBtn);
			
			
			decFrame.add(p1, BorderLayout.NORTH);
			decFrame.add(p2, BorderLayout.AFTER_LAST_LINE);
			decFrame.add(p3, BorderLayout.AFTER_LAST_LINE);
			decFrame.add(p4, BorderLayout.AFTER_LAST_LINE);
			
			decFrame.setLocationRelativeTo(null);
			decFrame.pack();
			decFrame.setResizable(false);
			decFrame.setVisible(true);
			okCloneBtn.addActionListener(new ActionListener() {
				
				@Override
				synchronized public void actionPerformed(ActionEvent e) {
					if(e.getSource() == okCloneBtn)
					{			
						String textChosenBtn = null;
						if(groupBtns.getSelection() != null)
						{
							
							for (Enumeration<AbstractButton> buttons = groupBtns.getElements(); buttons.hasMoreElements();) 
							{
				            	AbstractButton button = buttons.nextElement();
				
				            	if (button.isSelected()) 
				            		textChosenBtn =  button.getText();
					            	
					        }


							
							
							for(int i=0; i<arena.getAllRacers().size(); i++)
							{
								
								if(arena.getAllRacers().get(i).getSerialNumber() == Integer.valueOf(textChosenBtn))			
								{

									if(arena.getMAX_RACERS() == arena.getAllRacers().size())
									{
										JOptionPane.showMessageDialog(null, "Arena is full!!","Message",JOptionPane.INFORMATION_MESSAGE);
										break;
									}
									
									if(!containsOnlyNumbers(serialNumTF.getText()))
									{
										JOptionPane.showMessageDialog(null, "Invalid input values! Please try again.","Message",JOptionPane.INFORMATION_MESSAGE);
										break;
									}
									
									boolean isTrue=false;
									for(Racer racer : arena.getAllRacers())
									{
										isTrue = racer.getSerialNumber() == Integer.valueOf(serialNumTF.getText());
										if(isTrue)
										{
											JOptionPane.showMessageDialog(null, "Serial number is already exist, Please try again.","Message",JOptionPane.INFORMATION_MESSAGE);
											break;
										}
									}
									if(isTrue)
										break;
									
									CloneFactory racerMaker = new CloneFactory();
									if(arena.getAllRacers().get(i).getClass().getSimpleName().equals("Airplane"))
									{
										Airplane clonedAirplane = (Airplane) racerMaker.getClone((Racer) arena.getAllRacers().get(i));
										clonedAirplane.setSerialNumber(Integer.valueOf(serialNumTF.getText()));
										c = chosenColor(colorComboClone);

										new ColoredRacer(clonedAirplane, c);
										try {
											Racer.setLast_cur_y(Racer.getLast_cur_y() + Arena.getMinYGap());
											clonedAirplane.setCurrentLocation(new Point(Racer.getLast_cur_x(),Racer.getLast_cur_y()));
											arena.addRacer(clonedAirplane);
										} catch (RacerLimitException
												| RacerTypeException e1) {
						
										};
										
										racePanel.loadRacer(arena, (Racer) clonedAirplane,PATH ,clonedAirplane.getClass().getSimpleName(), c.toString());
									}
									
									else if(arena.getAllRacers().get(i).getClass().getSimpleName().equals("Helicopter"))
									{
										Helicopter clonedHelicopter = (Helicopter) racerMaker.getClone((Racer) arena.getAllRacers().get(i));
										clonedHelicopter.setSerialNumber(Integer.valueOf(serialNumTF.getText()));
										c = chosenColor(colorComboClone);

										new ColoredRacer(clonedHelicopter, c);
										try {
											Racer.setLast_cur_y(Racer.getLast_cur_y() + Arena.getMinYGap());
											clonedHelicopter.setCurrentLocation(new Point(Racer.getLast_cur_x(),Racer.getLast_cur_y()));
											arena.addRacer(clonedHelicopter);
										} catch (RacerLimitException
												| RacerTypeException e1) {
						
										};
										
										racePanel.loadRacer(arena, (Racer) clonedHelicopter,PATH ,clonedHelicopter.getClass().getSimpleName(), c.toString());


									}
									else if(arena.getAllRacers().get(i).getClass().getSimpleName().equals("Bicycle"))
									{
										Bicycle clonedBicycle = (Bicycle) racerMaker.getClone((Racer) arena.getAllRacers().get(i));
										clonedBicycle.setSerialNumber(Integer.valueOf(serialNumTF.getText()));
										c = chosenColor(colorComboClone);

										new ColoredRacer(clonedBicycle, c);
										try {
											Racer.setLast_cur_y(Racer.getLast_cur_y() + Arena.getMinYGap());
											clonedBicycle.setCurrentLocation(new Point(Racer.getLast_cur_x(),Racer.getLast_cur_y()));
											arena.addRacer(clonedBicycle);
										} catch (RacerLimitException
												| RacerTypeException e1) {
						
										};
										
										racePanel.loadRacer(arena, (Racer) clonedBicycle,PATH ,clonedBicycle.getClass().getSimpleName(), c.toString());


									}
									
									else if(arena.getAllRacers().get(i).getClass().getSimpleName().equals("Car"))
									{
										Car clonedCar = (Car) racerMaker.getClone((Racer) arena.getAllRacers().get(i));
										clonedCar.setSerialNumber(Integer.valueOf(serialNumTF.getText()));
										c = chosenColor(colorComboClone);

										new ColoredRacer(clonedCar, c);
										try {
											Racer.setLast_cur_y(Racer.getLast_cur_y() + Arena.getMinYGap());
											clonedCar.setCurrentLocation(new Point(Racer.getLast_cur_x(),Racer.getLast_cur_y()));
											arena.addRacer(clonedCar);
										} catch (RacerLimitException
												| RacerTypeException e1) {
						
										};
										
										racePanel.loadRacer(arena, (Racer) clonedCar,PATH ,clonedCar.getClass().getSimpleName(), c.toString());


									}
									
									else if(arena.getAllRacers().get(i).getClass().getSimpleName().equals("Horse"))
									{
										Horse clonedHorse = (Horse) racerMaker.getClone((Racer) arena.getAllRacers().get(i));
										clonedHorse.setSerialNumber(Integer.valueOf(serialNumTF.getText()));
										c = chosenColor(colorComboClone);

										new ColoredRacer(clonedHorse, c);
										try {
											Racer.setLast_cur_y(Racer.getLast_cur_y() + Arena.getMinYGap());
											clonedHorse.setCurrentLocation(new Point(Racer.getLast_cur_x(),Racer.getLast_cur_y()));
											arena.addRacer(clonedHorse);
										} catch (RacerLimitException
												| RacerTypeException e1) {
						
										};
										
										racePanel.loadRacer(arena, (Racer) clonedHorse,PATH ,clonedHorse.getClass().getSimpleName(), c.toString());


									}
									else if(arena.getAllRacers().get(i).getClass().getSimpleName().equals("RowBoat"))
									{
										RowBoat clonedRowBoat = (RowBoat) racerMaker.getClone((Racer) arena.getAllRacers().get(i));
										clonedRowBoat.setSerialNumber(Integer.valueOf(serialNumTF.getText()));
										c = chosenColor(colorComboClone);

										new ColoredRacer(clonedRowBoat, c);
										try {
											Racer.setLast_cur_y(Racer.getLast_cur_y() + Arena.getMinYGap());
											clonedRowBoat.setCurrentLocation(new Point(Racer.getLast_cur_x(),Racer.getLast_cur_y()));
											arena.addRacer(clonedRowBoat);
										} catch (RacerLimitException
												| RacerTypeException e1) {
						
										};
										
										racePanel.loadRacer(arena, (Racer) clonedRowBoat,PATH ,clonedRowBoat.getClass().getSimpleName(), c.toString());


									}
									else if(arena.getAllRacers().get(i).getClass().getSimpleName().equals("SpeedBoat"))
									{
										SpeedBoat clonedSpeedBoat = (SpeedBoat) racerMaker.getClone((Racer) arena.getAllRacers().get(i));
										clonedSpeedBoat.setSerialNumber(Integer.valueOf(serialNumTF.getText()));
										c = chosenColor(colorComboClone);

										new ColoredRacer(clonedSpeedBoat, c);
										try {
											Racer.setLast_cur_y(Racer.getLast_cur_y() + Arena.getMinYGap());
											clonedSpeedBoat.setCurrentLocation(new Point(Racer.getLast_cur_x(),Racer.getLast_cur_y()));
											arena.addRacer(clonedSpeedBoat);
										} catch (RacerLimitException
												| RacerTypeException e1) {
						
										};
										
										racePanel.loadRacer(arena, (Racer) clonedSpeedBoat,PATH ,clonedSpeedBoat.getClass().getSimpleName(), c.toString());


									}
									
									break;
									
								}
							}
							
						
						}
						else
						{
							JOptionPane.showMessageDialog(null, "Please choose racer first!","Message",JOptionPane.INFORMATION_MESSAGE);
						}
						groupBtns.clearSelection();
				
						
					}
					
				}
			});
			

			
		}
			
		

		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}
	
	private Color chosenColor(JComboBox<String> newColorCombo)
	{
		utilities.EnumContainer.Color color = null;
		String str2=(String)newColorCombo.getSelectedItem();
		switch (str2) {
		case "Black":
			color = Color.BLACK;
			break;
		case "Blue":
			color = Color.BLUE;
			break;
		case "Green":
			color = Color.GREEN;
			break;
		case "Red":
			color = Color.RED;
			break;
		case "Yellow":
			color = Color.YELLOW;
			break;

		}
		
		return color;
	}
	
	private boolean checkArenaInputs()
	{
		if(!containsOnlyNumbers(arenaLenTField.getText()) || !containsOnlyNumbers(maxRacersTField.getText()))
			{
				JOptionPane.showMessageDialog(null, "Invalid input values! Please try again.","Message",JOptionPane.INFORMATION_MESSAGE);
				return false;
			}
		
		if(Double.valueOf(arenaLenTField.getText()) < 100 || Double.valueOf(arenaLenTField.getText()) > 3000
			|| Double.valueOf(maxRacersTField.getText()) < 1 || Double.valueOf(maxRacersTField.getText()) > 20	)
			{
				JOptionPane.showMessageDialog(null, "Invalid input values! Please try again.","Message",JOptionPane.INFORMATION_MESSAGE);
				return false;
			}
		return true;
	}
	
	private boolean checkRacerInputs()
	{
		if(!containsOnlyAlphabet(racerNameTField.getText()))
		{
			JOptionPane.showMessageDialog(null, "Invalid racer name! Please try again.","Message",JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		
		if(!containsOnlyNumbers(maxSpeedTField.getText()) || !containsOnlyNumbers(accelerationTField.getText()) || !containsOnlyNumbers(wheelsAmountTField.getText()))
			{
				JOptionPane.showMessageDialog(null, "Invalid input values! Please try again.","Message",JOptionPane.INFORMATION_MESSAGE);
				return false;
			}
		
		if(Double.valueOf(maxSpeedTField.getText()) < Double.valueOf(accelerationTField.getText()))
			{
				JOptionPane.showMessageDialog(null, "Invalid input values! Please try again.","Message",JOptionPane.INFORMATION_MESSAGE);
				return false;
			}
		
		return true;
	}
	
	private boolean containsOnlyNumbers(String str) 
	{        
	        //It can't contain only numbers if it's null or empty...
		if (str == null || str.length() == 0)
			return false;

		for (int i = 0; i < str.length(); i++) {

			//If we find a non-digit character we return false.
			if (!Character.isDigit(str.charAt(i)))
				return false;
		}
		
		return true;
	}
	
	private boolean containsOnlyAlphabet(String str) 
	{        
	        //It can't contain only numbers if it's null or empty...
		if (str == null || str.length() == 0)
			return false;

		for (int i = 0; i < str.length(); i++) {

			//If we find a non-digit character we return false.
			if (Character.isDigit(str.charAt(i)))
				return false;
		}

		return true;
	}
	
	
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	
	public static Arena getArena() {
		return arena;
	}

	public static void setArena(Arena arena) {
		RaceBuilder.arena = arena;
	}


	public static RacePanel getRacePanel() {
		return racePanel;
	}


	public static void setRacePanel(RacePanel racePanel) {
		RaceBuilder.racePanel = racePanel;
	}


	public static String getPath() {
		return PATH;
	}


	public static JComboBox<String> getArenaCombo() {
		return arenaCombo;
	}


	public static void setArenaCombo(JComboBox<String> arenaCombo) {
		RaceBuilder.arenaCombo = arenaCombo;
	}


	public static JComboBox<String> getRacerCombo() {
		return racerCombo;
	}


	public static void setRacerCombo(JComboBox<String> racerCombo) {
		RaceBuilder.racerCombo = racerCombo;
	}


	public static JComboBox<String> getColorCombo() {
		return colorCombo;
	}


	public static void setColorCombo(JComboBox<String> colorCombo) {
		RaceBuilder.colorCombo = colorCombo;
	}

}















