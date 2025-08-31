import java.awt.Graphics;
import java.util.Random;
import javax.swing.JPanel;

/** Circle for drawing in a JFrame
 *
 * @author Amy Larson
 */
public class Person extends JPanel {

	/** diameter of the circle */
	private static final int SIZE = 5;
	
	/** Population of which this person is part */
	private Population population;
	
	private static Random random = new Random();
	
	/** Box inside of which this person can move */
	private BoundingBox borders;
	
	/** Health status of the person. From enum Status */
	private Status status = Status.HEALTHY_VACCINATED;
	
	/** Position (column) relative to the origin of the population panel */
	private int locationX = 0;
	
	/** Position (row) relative to the origin of the population panel */
	private int locationY = 0;
	
	/** Movement along the x-axis at each time step */
	private int deltaX = 0;
	
	/** Movement along the y-axis at each time step */
	private int deltaY = 0;

	/** static variable for transmission */
	private static int transmissionProbability  = 10;

	private static int days = 0;

	private static int duration = 1;

	private static int dayInfected = 0;

	private static int morbidityRate = 0;

	/** Static variable for prevention level*/
	private static int preventionLevel = 0;

	/** Default constructor */
    public Person(BoundingBox borders, Population pop) {

        setLayout(null);
        
        this.borders = borders;
        this.population = pop;
        
        // get a random location from within their border
        locationX = borders.getRandomX();
        locationY = borders.getRandomY();

		// randomly place each person
    	setBounds(locationX, locationY, SIZE, SIZE);
		//System.out.println("created with bounds: " + getBounds()); 
		
		// randomly select rate of movement per time step
		deltaX = random.nextInt(4) - 2;		// range -2 to 2
		deltaY = random.nextInt(4) - 2;
		
		setVisible(true);
    }
    
    /** Move person based on their deltas along X and Y. */
    public void move() {
    
    	// change in each direction
    	locationX += deltaX;
    	locationY += deltaY;
    	
    	// determine if up against a border and need to turn around
    	if (locationX < borders.left) {
    		locationX = borders.left + SIZE;
    		deltaX = -deltaX;
    	} else if (locationX > borders.right) {
    		locationX = borders.right - SIZE;
    		deltaX = -deltaX;
    	}
    	// check if y (row) is out of bounds
    	if (locationY < borders.top) {
    		locationY = borders.top + SIZE;
    		deltaY = -deltaY;
    	} else if (locationY > borders.bottom) {
    		locationY = borders.bottom - SIZE;
    		deltaY = -deltaY;
    	}
    	
    	setLocation(locationX,locationY);


		//Issue 15. tested
		//Issue 27. I hate this but we don't have an infected variablle ;-;
		if (status == Status.ASYMPTOMATIC) {
			if ((days - dayInfected) >= duration) {
				if (random.nextInt(100) < morbidityRate) {
					status = Status.NOT_ALIVE;
					population.statusChanged(Status.ASYMPTOMATIC, Status.NOT_ALIVE);
					deltaX(0);
					deltaY(0);
				} else {
					status = Status.HEALTHY_RECOVERED;
					population.statusChanged(Status.ASYMPTOMATIC, Status.HEALTHY_RECOVERED);
				}
			}
		}

		if (status == Status.SYMPTOMATIC) {
			if ((days - dayInfected) >= duration) {
				if (random.nextInt(100) < morbidityRate) {
					status = Status.NOT_ALIVE;
					population.statusChanged(Status.SYMPTOMATIC, Status.NOT_ALIVE);
					deltaX(0);
					deltaY(0);
				} else {
					status = Status.HEALTHY_RECOVERED;
					population.statusChanged(Status.SYMPTOMATIC, Status.HEALTHY_RECOVERED);
				}
			}
		}
    }
    
    /** 
    * This person has been exposed to an infected individual. Determine if infection has been transmitted.
    * @param other Person encountered that might infect this person.
    */
    public void exposed(Person other) {
		int effectiveProbability = transmissionProbability;
		int preventionLevel = MessageBoard.preventionlevel();

		//ADDED: Apply reduction based on prevention level
		switch(preventionLevel){
			case 1:
			effectiveProbability = (int)(transmissionProbability * 0.75); // 25% reduction
			break;
		case 2:
			effectiveProbability = (int)(transmissionProbability * 0.5); // 50% reduction
			break;
		case 3:
			effectiveProbability = (int)(transmissionProbability * 0.25); // 75% reduction
			break;
			
			default:
			// Level 0 or invalid level - (no change)
			break;
	}

		// MODIFIED: Use effectiveProbability instead of transmissionProbability
		if (random.nextInt(100) < effectiveProbability) {
			Status oldStatus = this.status;
			status = Status.ASYMPTOMATIC;
			population.statusChanged(oldStatus, Status.ASYMPTOMATIC);
			dayInfected = MessageBoard.days();
		}
	}



	







    // part of Java Graphics. Called when repaint() is called.
    // this draws the circle inside the Person panel
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(status.color);
        g.fillOval(0, 0, SIZE, SIZE);
    }

    // ______________________ SETTERS and GETTERS
    
    public void borders(BoundingBox box) {
    	borders = box;
    }
        
    public int getCenterX() {
    	return (locationX + SIZE)/2;
    }
    public int getCenterY() {
    	return (locationY + SIZE)/2;
    }
    public static int SIZE() {
    	return SIZE;
    }
    
    public Status status() {
    	return status;
    }
    
    public void status(Status s) {
    	status = s;
    }
    
    public void deltaX(int dX) {
    	deltaX = dX;
    }
    
    public void deltaY(int dY) {
    	deltaY = dY;
    }
	public static void setTransmissionProbability(int probability){
		transmissionProbability = probability;
	}

	 // Getter for transmission probability
    public static int getTransmissionProbability() {
        return transmissionProbability;
    }

	public static void setDays(int d) {
		days = d;
	}

	public static void setDuration(int d) {
		duration = d;
	}
    
	public static void setMorbidityRate(int d) {
		morbidityRate = d;
	}

	//  Setter for prevention level
	public static void setPreventionLevel(int level) {
		preventionLevel = level;
	}
	
	//  Getter for prevention level
	public static int getPreventionLevel() {
		return preventionLevel;
	}

}