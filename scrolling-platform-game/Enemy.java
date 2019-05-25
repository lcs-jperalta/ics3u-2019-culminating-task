import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Enemy here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Enemy extends Actor
{
    /**
     * Instance variables
     * 
     * Can be used by any method below.
     */
    // The location of this object in the scrollable world
    private int scrollableWorldPositionX;
    private int scrollableWorldPositionY;

    /**
     * Constructor
     * 
     * This runs once.
     */
    Enemy(int scrollableWorldX, int scrollableWorldY)
    {
        scrollableWorldPositionX = scrollableWorldX;
        scrollableWorldPositionY = scrollableWorldY;
    }

    /**
     * Move to left (to make hero look like they are moving right)
     */
    public void moveLeft(int speed)
    {
        setLocation(getX() - speed, getY());
    }

    /**
     * Move to right (to make hero look like they are moving left)
     */
    public void moveRight(int speed)
    {
        setLocation(getX() + speed, getY());
    }

    /**
     * Act - do whatever the Enemy wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        // Add your action code here.
    }    
}
