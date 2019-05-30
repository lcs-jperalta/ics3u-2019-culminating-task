import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Bird here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Bird extends Enemy
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
    private static final int WALK_ANIMATION_DELAY = 6;
    private static final int COUNT_OF_WALKING_IMAGES = 3;
    private int walkingFrames;

    // Facing which direction
    private static final String FACING_LEFT = "left";
    private static final String FACING_RIGHT = "right";
    private String horizontalDirection;

    /**
     * Constructor
     * 
     * Called once when object is created.
     */
    Bird (int scrollableWorldX, int scrollableWorldY)
    {
        super(scrollableWorldX, scrollableWorldY);

        // Initialize the 'walking' arrays
        walkingRightImages = new GreenfootImage[COUNT_OF_WALKING_IMAGES];
        walkingLeftImages = new GreenfootImage[COUNT_OF_WALKING_IMAGES];

        // Facing left to start
        horizontalDirection = FACING_LEFT;

        // Load walking images from disk
        for (int i = 0; i < walkingRightImages.length; i++)
        {
            walkingRightImages[i] = new GreenfootImage("bird-walk-right-" + i + ".png");
            walkingLeftImages[i] = new GreenfootImage("bird-walk-left-" + i + ".png");
        }
    }

    /**
     * Act - do whatever the Bird wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        if (horizontalDirection == FACING_LEFT)
        {
            animateWalk(FACING_LEFT);
        }
        else
        {
            animateWalk(FACING_RIGHT);
        }

        if (touchingWall() == true)
        {
            if (horizontalDirection == FACING_RIGHT)
            {
                horizontalDirection = FACING_LEFT;
            }
            else
            {
                horizontalDirection = FACING_RIGHT;
            }
        }

        if (horizontalDirection == FACING_LEFT)
        {
            // Move to the left
            int newXPosition = getX() - deltaX;
            setLocation(newXPosition, getY());
        }

        if (horizontalDirection == FACING_RIGHT)
        {
            // Move to the left
            int newXPosition = getX() + deltaX;
            setLocation(newXPosition, getY());
        }
    }

    /**
     * Is the bird touching a wall?
     */
    public boolean touchingWall()
    {
        // When the waddle dee touches a wall, it changes direction
        Actor touchingWallFromFront = getOneObjectAtOffset(0 - getImage().getWidth() / 2, 0, Platform.class);
        Actor touchingWallFromBehind = getOneObjectAtOffset(0 + getImage().getWidth() / 2, 0, Platform.class);

        if (touchingWallFromFront == null && touchingWallFromBehind == null)
        {
            return false;
        }
        else
        {
            return true;
        }
 
    }

    /**
     * Animate walking
     */
    private void animateWalk(String direction)
    {
        // Track walking animation frames
        walkingFrames += 1;

        // Get current animation stage
        int stage = walkingFrames / WALK_ANIMATION_DELAY;

        // Animate
        if (stage < walkingRightImages.length)
        {
            // Set image for this stage of the animation
            if (direction == FACING_RIGHT)
            {
                setImage(walkingRightImages[stage]);
            }
            else
            {
                setImage(walkingLeftImages[stage]);
            }
        }
        else
        {
            // Start animation loop from beginning
            walkingFrames = 0;
        }
    }
}
