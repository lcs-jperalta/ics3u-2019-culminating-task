import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Goomba here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Goomba extends Enemy
{
    /**
     * Instance variables
     */
    // Vertical speed
    private int deltaY = 4;
    
    // Acceleration of falling
    private int acceleration = 1;
    
    /**
     * Constructor
     * 
     * Called once when object is created.
     */
    Goomba(int scrollableWorldX, int scrollableWorldY)
    {
        super(scrollableWorldX, scrollableWorldY);
    }

    /**
     * Act - do whatever the Goomba wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        // Add your action code here.
    }    
}
