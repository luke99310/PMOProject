package model;

import java.util.List;

public interface BoardInterface {

	List<Box> getBoxes();

	Box getBox(int boxIndex);
	
}
