package sand.controller;

import sand.view.SandDisplay;
import java.awt.*;
import java.util.*;

public class SandLab
{
  //Step 4,6
  //add constants for particle types here
  public static final int EMPTY = 0;
  public static final int METAL = 1;
  public static final int SAND = 2;
  public static final int WATER = 3;
  public static final int ACID = 4;
  public static final int STEAM = 5;
  public static final int FIRE = 6;
  
  //do not add any more fields below
  private int[][] grid;
  private SandDisplay display;
  
  
  /**
   * Constructor for SandLab
   * @param numRows The number of rows to start with
   * @param numCols The number of columns to start with;
   */
  public SandLab(int numRows, int numCols)
  {
    String[] names;
    // Change this value to add more buttons
    //Step 4,6
    names = new String[7];
    // Each value needs a name for the button
    names[EMPTY] = "Empty";
    names[METAL] = "Metal";
    names[SAND] = "Sand";
    names[WATER] = "Water";
    names[ACID] = "Acid";
    names[STEAM] = "Gas";
    names[FIRE] = "Fire";
    
    //1. Add code to initialize the data member grid with same dimensions
    
    this.grid = new int [numRows] [numCols];
    
    display = new SandDisplay("Falling Sand", numRows, numCols, names);
  }
  
  //called when the user clicks on a location using the given tool
  private void locationClicked(int row, int col, int tool)
  {
    //2. Assign the values associated with the parameters to the grid
   grid [row][col] = tool;
  }

  //copies each element of grid into the display
  public void updateDisplay()
  {
      //Step 3
   //Hint - use a nested for loop
    for (int row = 0; row < grid.length; row++)
    {
    	for (int col = 0; col < grid[0].length; col++)
    	{
    		if (grid[row][col] == EMPTY)
    		{
    			display.setColor(row, col, Color.BLACK);
    		}
    		else if (grid[row][col] == METAL)
    		{
    			display.setColor(row, col, Color.GRAY);
    		}
    		else if (grid[row][col] == SAND)
    		{
    			display.setColor(row, col, Color.YELLOW);
    		}
    		else if (grid[row][col] == WATER)
    		{
    			display.setColor(row, col, Color.BLUE);
    		}
    		else if (grid[row][col] == ACID)
    		{
    			display.setColor(row, col, Color.GREEN);
    		}
    		else if (grid[row][col] == STEAM)
    		{
    			display.setColor(row, col, Color.LIGHT_GRAY);
    		}
    		else if (grid[row][col] == FIRE)
    		{
    			display.setColor(row, col, Color.ORANGE);
    		}
    	}
    }
  }
  
  private void updateSand(int row, int col)
  {
	  if (grid[row][col] == SAND && row + 1 < grid.length && (grid[row + 1][col] == EMPTY || grid[row + 1][col] == WATER || grid[row + 1][col] == ACID || grid[row + 1][col] == STEAM))
	  {
		  if (grid[row + 1][col] == WATER)
		  {
			  grid[row][col] = WATER;
		  }
		  else if (grid[row + 1][col] == ACID)
		  {
			  grid[row][col] = ACID;
		  }
		  else if (grid[row + 1][col] == STEAM)
		  {
			  grid[row][col] = STEAM;
		  }
		  else
		  {
			  grid[row][col] = EMPTY;
		  }
		  grid[row + 1][col] = SAND;
	  }
  }
  
  private void updateWater(int row, int col, int waterDirection)
  {
	  if (grid[row][col] == WATER)
	  {
		  if ((waterDirection == 0 || waterDirection == 1) && row + 1 < grid.length && (grid[row + 1][col] == EMPTY || grid[row + 1][col] == ACID || grid[row + 1][col] == STEAM))
		  {
			  if (grid[row + 1][col] == STEAM)
			  {
				  grid[row][col] = STEAM;
			  }
			  else if (grid[row + 1][col] == ACID)
			  {
				  grid[row][col] = ACID;
			  }
			  else
			  {
				  grid[row][col] = EMPTY;
			  }
			  grid[row + 1][col] = WATER;
		  }
		  if (waterDirection == 2 && col - 1 >= 0 && grid[row][col - 1] == EMPTY)
		  {
			  if (grid[row][col - 1] == STEAM)
			  {
				  grid[row][col] = STEAM;
			  }
			  else if (grid[row][col - 1] == ACID)
			  {
				  grid[row][col] = ACID;
			  }
			  else
			  {
				  grid[row][col] = EMPTY;
			  }
			  grid[row][col - 1] = WATER;
		  }
		  if (waterDirection == 3 && col + 1 < grid[0].length && grid[row][col + 1] == EMPTY)
		  {
			  if (grid[row][col + 1] == STEAM)
			  {
				  grid[row][col] = STEAM;
			  }
			  else if (grid[row][col + 1] == ACID)
			  {
				  grid[row][col] = ACID;
			  }
			  else
			  {
				  grid[row][col] = EMPTY;
			  }
			  grid[row][col + 1] = WATER;
		  }
	  }
  }

  private void updateAcid(int row, int col, int waterDirection)
  {
	  if (grid[row][col] == ACID)
	  {
		  if ((waterDirection == 0 || waterDirection == 1) && row + 1 < grid.length && grid[row + 1][col] == EMPTY)
		  {
			  grid[row][col] = EMPTY;
			  grid[row + 1][col] = ACID;
		  }
		  if (waterDirection == 2 && col - 1 >= 0 && grid[row][col - 1] == EMPTY)
		  {
			  grid[row][col] = EMPTY;
			  grid[row][col - 1] = ACID;
		  }
		  if (waterDirection == 3 && col + 1 < grid[0].length && grid[row][col + 1] == EMPTY)
		  {
			  grid[row][col] = EMPTY;
			  grid[row][col + 1] = ACID;
		  }
		  //unique acid stuff
		  if ((waterDirection == 0 || waterDirection == 1) && row + 1 < grid.length && grid[row + 1][col] != ACID)
		  {
			  grid[row][col] = EMPTY;
			  grid[row + 1][col] = EMPTY;
		  }
		  if (waterDirection == 2 && col - 1 >= 0 && grid[row][col - 1] != ACID)
		  {
			  grid[row][col] = EMPTY;
			  grid[row][col - 1] = EMPTY;
		  }
		  if (waterDirection == 3 && col + 1 < grid[0].length && grid[row][col + 1] != ACID)
		  {
			  grid[row][col] = EMPTY;
			  grid[row][col + 1] = EMPTY;
		  }
	  }
  }
  
  private void updateGas(int row, int col, int waterDirection)
  {
	  if (grid[row][col] == STEAM)
	  {
		  if ((waterDirection == 0 || waterDirection == 1) && row - 1 >= 0 && (grid[row - 1][col] == EMPTY))
		  {
			  grid[row][col] = EMPTY;
			  grid[row - 1][col] = STEAM;
		  }
		  if (waterDirection == 2 && col - 1 >= 0 && grid[row][col - 1] == EMPTY)
		  {
			  grid[row][col] = EMPTY;
			  grid[row][col - 1] = STEAM;
		  }
		  if (waterDirection == 3 && col + 1 < grid[0].length && grid[row][col + 1] == EMPTY)
		  {
			  grid[row][col] = EMPTY;
			  grid[row][col + 1] = STEAM;
		  }
	  }
  }
  private void updateFire(int row, int col, int waterDirection)
  {
	  if (grid[row][col] == FIRE)
	  {
		  if (waterDirection == 0)
		  {
			  grid[row][col] = EMPTY;
		  }
		  if (waterDirection == 1 && row - 1 >= 0 && (grid[row - 1][col] == EMPTY))
		  {
			  grid[row][col] = EMPTY;
			  grid[row - 1][col] = FIRE;
		  }
		  if (waterDirection == 2 && col - 1 >= 0 && grid[row][col - 1] == EMPTY)
		  {
			  grid[row][col] = EMPTY;
			  grid[row][col - 1] = FIRE;
		  }
		  if (waterDirection == 3 && col + 1 < grid[0].length && grid[row][col + 1] == EMPTY)
		  {
			  grid[row][col] = EMPTY;
			  grid[row][col + 1] = FIRE;
		  }
	  }
  }
  
  //Step 5,7
  //called repeatedly.
  //causes one random particle in grid to maybe do something.
  public void step()
  {
    //Remember, you need to access both row and column to specify a spot in the array
    //The scalar refers to how big the value could be
    //int someRandom = (int) (Math.random() * scalar)
    //remember that you need to watch for the edges of the array
	  
	  int row = (int) (Math.random() * grid.length);
	  int col = (int) (Math.random() * grid[0].length);
	  
	  int randDirection = (int) (Math.random() * 4);
	  
	  updateSand(row, col);
	  
	  updateWater(row, col, randDirection);
	  
	  updateAcid(row, col, randDirection);
	  
	  updateGas(row, col, randDirection);
	  
	  updateFire(row, col, randDirection);
  }
  
  //do not modify this method!
  public void run()
  {
    while (true) // infinite loop
    {
      for (int i = 0; i < display.getSpeed(); i++)
      {
        step();
      }
      updateDisplay();
      display.repaint();
      display.pause(1);  //wait for redrawing and for mouse
      int[] mouseLoc = display.getMouseLocation();
      if (mouseLoc != null)  //test if mouse clicked
      {
        locationClicked(mouseLoc[0], mouseLoc[1], display.getTool());
      }
    }
  }
}
