/*
 * Population 
 */

 import java.util.ArrayList;
 import java.util.HashMap;
 import java.util.Random;
 
 /**
  * Models a collection of circles roaming about impacting other circles.
  * @author Amy Larson (with Erik Steinmetz)
  */
 public class Population {
 
	 /** Population consists of collection of Person(s) */
	 private ArrayList<Person> persons = new ArrayList<>();
	 
	 /** Size of population of all status */
	 private int populationSize = 500;
 
	 /** Pauses simulation then people do not move */
	 private boolean paused = false;
	 
	 /** Count of population in each status (in enum Status) */
	 HashMap<Status,Integer> statusCounts = new HashMap<>();
	 
	 Random random = new Random();
 
	 /** Default constructor. */
	 public Population() {
	 
		 /** Start with 0 persons with each health status */
		 for (Status s : Status.values()) {
			 System.out.println(s);
			 statusCounts.put(s,0);
		 }
	 }
	 
	 /** Create the population
	 * @param panel Place all in this JPanel
	 */
	 public void populate(PopulationPanel panel) {
 
		 Person.setDuration(MessageBoard.duration());
 
		 // populate each of the four areas equally
		 for (int area=0; area<4; area++) {
			 // make all the new persons
			 for (int i=0; i<populationSize/4; i++) {
				 // Boundaries define the area in which they can move
				 Person person = new Person(Layout.BOUNDARIES[area],this);
 
				 // Set deltaX and deltaY based on MOVEMENT level
				 int movementLevel = MessageBoard.movement();
				 double probability = 0.0;
				 switch (movementLevel) {
					 case 1:
						 probability = 0.75; // 75% chance to stop moving
						 break;
					 case 2:
						 probability = 0.50; // 50% chance to stop moving
						 break;
					 case 3:
						 probability = 0.25; // 25% chance to stop moving
						 break;
					 case 4:
						 probability = 0.10; // 10% chance to stop moving
						 break;
					 default:
						 probability = 0.0; // No change (keep moving)
						 break;
				 }
 
				 // Randomly set deltaX and deltaY to 0 based on probability
				 if (random.nextDouble() < probability) {
					 person.deltaX(0);
					 person.deltaY(0);
				 }
 
				 // add to the list (data structure)
				 persons.add(person);
				 // add to the Graphics panel
				 panel.add(person);
				 // adjust the count in the HashMap
				 statusCounts.put(person.status(), 
					 statusCounts.get(person.status()) + 1);
			 }
		 } // end for area
		 Person infected = persons.get(random.nextInt(persons.size()));
		 statusChanged(infected.status(), Status.SYMPTOMATIC);
		 infected.status(Status.SYMPTOMATIC);
 
		 shareStats();
	 } // end populate()
	 
 
	 public void shareStats(){
		 HashMap<Integer, String> cloned = (HashMap<Integer, String>) statusCounts.clone();
		 MessageBoard.update(Message.STATS, cloned);
	 }
 
	 // updates the status changes when a person is removed or added
	 public void statusChanged(Status remove, Status add){
		 int count = statusCounts.get(remove);
		 // if person status changes they get removed
		 if(count > 0){
			 statusCounts.put(remove, count - 1);
		 }
 
		 // when a new person is added
		 int newCount = statusCounts.get(add);
		 statusCounts.put(add, newCount + 1);
	 }
 
	 /** Execute a time step so that everyone moves accordingly */
	 public void update() {
		 Person.setDays(MessageBoard.days());
		 Person.setMorbidityRate(MessageBoard.morbidityRate());
		 Person.setPreventionLevel(MessageBoard.preventionlevel()); // ADDED: Set prevention level from MessageBoard
	 
		 for (Person person : persons) {
			 person.move();
		 }
		 // Determine if anyone has crossed paths with another
		 // potentially changing their status from healthy to asymptomatic
		 checkForEncounters();
	 }
	 
	 /** Determine if any of the persons are crossing paths. */
	 public void checkForEncounters() {
		 for (Person person : persons) {
			 // determine if person might change status of other
			 // this can occur only if person is contagious and other is healthy
			 if ( Status.HEALTHY_VACCINATED==person.status() || 
				  Status.HEALTHY_NOT_VACCINATED==person.status() ||
				  Status.HEALTHY_RECOVERED==person.status()) {
					 continue;
			 }
			 for (Person other: persons) {
				 // do not compare anyone to themselves
				 if (person.equals(other)) {
					 continue;
				 }
				 // if already infected, status cannot change
				 if (Status.SYMPTOMATIC==other.status() ||
					 Status.ASYMPTOMATIC==other.status()) {
					 continue;
				 }
				 // calculate the distance to the center of each person.
				 // if closer than 2*radius, they are overlapping
				 // use Pythagoreans theorem
				 int deltaX = person.getCenterX() - other.getCenterX();
				 int deltaY = person.getCenterY() - other.getCenterY();
				 double distance = Math.pow((deltaX*deltaX + deltaY*deltaY),0.5);
				 if (distance < Person.SIZE()) {
					 // they are overlapping, thus other is exposed to person
					 other.exposed(person);
				 }
			 }
		 }
	 } // end checkForEncounters
 
	 /** Pause the simulation - people no longer move. */
	 public void pause() {
		 paused = true;
	 }
 
	 /** Continue the simulation */
	 public void play() {
		 System.out.println("Playing now");
		 paused = false;
	 }
 
	 public ArrayList<Person> getPeople() {
		 return persons;
	 }
 }