import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Waddle_Dee here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Waddle_Dee extends Enemy
{
    /**
     * Instance variables
     */
    // Horizontal speed
    private int deltaX = 1;

    // Vertical speed
    private int deltaY = 4;

    // Acceleration of falling
    private int acceleration = 1;

    // For walking animation
    private GreenfootImage walkingRightImages[];
    private GreenfootImage walkingLeftImages[];
    private static final int WALK_ANIMATION_DELAY = 8;
    private static final int COUNT_OF_WALKING_IMAGES = 2;
    private int walkingFrames;
    /**
     * Constructor
     * 
     * Called once when object is created.
     */
    Waddle_Dee(int scrollableWorldX, int scrollableWorldY)
    {
        super(scrollableWorldX, scrollableWorldY);
    }

    /**
     * Act - do whatever the Waddle_Dee wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        // Add your action code here.
    }    
}
