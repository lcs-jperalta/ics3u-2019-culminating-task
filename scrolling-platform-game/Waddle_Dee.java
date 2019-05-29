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

    // Facing which direction
    private static final String FACING_LEFT = "left";
    private static final String FACING_RIGHT = "right";
    private String horizontalDirection;

    /**
     * Constructor
     * 
     * Called once when object is created.
     */
    Waddle_Dee(int scrollableWorldX, int scrollableWorldY)
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
            walkingRightImages[i] = new GreenfootImage("waddle-dee-walk-right-" + i + ".png");
            walkingLeftImages[i] = new GreenfootImage("waddle-dee-walk-left-" + i + ".png");
        }
    }

    /**
     * Act - do whatever the Waddle_Dee wants to do. This method is called whenever
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

        if (onPlatform())
        {
            // Stop falling
            deltaY = 0;

            // Get a reference to any object that's created from a subclass of Platform,
            // that is below (or just below in front, or just below behind) the waddle dee
            Actor directlyUnder = getOneObjectAtOffset(0, getImage().getHeight() / 2, Platform.class);
            Actor frontUnder = getOneObjectAtOffset(getImage().getWidth() / 3, getImage().getHeight() / 2, Platform.class);
            Actor rearUnder = getOneObjectAtOffset(0 - getImage().getWidth() / 3, getImage().getHeight() / 2, Platform.class);

            // Bump the waddle dee back up so that they are not "submerged" in a platform object
            if (directlyUnder != null)
            {
                int correctedYPosition = directlyUnder.getY() - directlyUnder.getImage().getHeight() / 2 - this.getImage().getHeight() / 2;
                setLocation(getX(), correctedYPosition);
            }
            if (frontUnder != null)
            {
                int correctedYPosition = frontUnder.getY() - frontUnder.getImage().getHeight() / 2 - this.getImage().getHeight() / 2;
                setLocation(getX(), correctedYPosition);
            }
            if (rearUnder != null)
            {
                int correctedYPosition = rearUnder.getY() - rearUnder.getImage().getHeight() / 2 - this.getImage().getHeight() / 2;
                setLocation(getX(), correctedYPosition);
            }

        }
        else
        {
            fall();
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
     * Is the waddle dee currently touching a solid object? (any subclass of Platform)
     */
    public boolean onPlatform()
    {
        // Get an reference to a solid object (subclass of Platform) below the waddle dee, if one exists
        Actor directlyUnder = getOneObjectAtOffset(0, getImage().getHeight() / 2, Platform.class);
        Actor frontUnder = getOneObjectAtOffset(getImage().getWidth() / 3, getImage().getHeight() / 2, Platform.class);
        Actor rearUnder = getOneObjectAtOffset(0 - getImage().getWidth() / 3, getImage().getHeight() / 2, Platform.class);

        // If there is no solid object below (or slightly in front of or behind) the waddle dee...
        if (directlyUnder == null && frontUnder == null && rearUnder == null)
        {
            return false;   // Not on a solid object
        }
        else
        {
            return true;
        }

    }

    /**
     * Is the waddle dee touching a wall?
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

    /**
     * Make the waddle dee fall.
     */
    public void fall()
    {
        // Fall (move vertically)
        int newVisibleWorldYPosition = getY() + deltaY;
        setLocation(getX(), newVisibleWorldYPosition );

        // Accelerate (fall faster next time)
        deltaY = deltaY + acceleration;
    }
}