package model.MonopolyTypes;

public enum BoxType {
	BLUE("Blue", 3),
	RED("Red", 3),
	YELLOW("Yellow", 3),
	GREEN("Green", 3),
	START("Start", 1),
	STATION("Station", 4),
	CHANCE("Chance", 3),
	UNEXPECTED("Unexpected", 3),
	TRANSIT("Transit", 6),
	JAIL("Jail", 1);

	// FIELDS
	private final String description;
	private final int numberOfStreets;

	 // PRIVATE CONSTRUCTOR
	 private BoxType(final String description, final int numberOfStreets) {
		 this.description = description;
	     this.numberOfStreets = numberOfStreets;
	 }

	 // METHODS
	 public String getDescription() {
		 return this.description;
	 }

	 public int getNumberOfStreets() {
		 return this.numberOfStreets;
	 }
	 
}
