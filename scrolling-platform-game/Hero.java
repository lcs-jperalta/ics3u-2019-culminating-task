import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;

/**
 * This is the class for the "main character" in the action.
 * 
 * @author R. Gordon
 * @version May 8, 2019
 */
public class Hero extends Actor
{
    /**
     * Instance variables
     * 
     * These are available for use in any method below.
     */

    // How much damage kirby can take
    public int lives = 3;

    // Score
    public int score = 0;

    // Track whether kirby is hurt
    private boolean isHurt;

    // Track number of frames kirby is invincible
    private int iframes = 0;

    // How many jumps kirby can use
    private int numberOfJumps = 3;

    // Track whether kirby's mouth is open
    private boolean isMouthOpen;

    // Track whether an enemy is swallowed
    private boolean isEnemySwallowed;

    // Tracks whether the up key is down
    private boolean isDown;

    // Tracks whether the space key is down
    private boolean isSpaceKeyDown;

    // Tracks whether kirby touched the ceiling
    private boolean hasTouchedCeiling;

    // Horizontal speed (change in horizontal position, or delta X)
    private int deltaX = 4;

    // Vertical speed (change in vertical position, or delta Y)
    private int deltaY = 1;

    // Acceleration for falls
    private int acceleration = 1;

    // Strength of a jump
    private int jumpStrength = -14;

    // Track current theoretical position in wider "scrollable" world
    private int currentScrollableWorldXPosition;

    // Track whether game is over or not
    private boolean isGameOver;

    // Constants to track vertical direction
    private static final String JUMPING_UP = "up";
    private static final String JUMPING_DOWN = "down";
    private String verticalDirection;

    // Constants to track horizontal direction
    private static final String FACING_RIGHT = "right";
    private static final String FACING_LEFT = "left";
    private String horizontalDirection;

    // For walking animation
    private GreenfootImage walkingRightImages[];
    private GreenfootImage walkingLeftImages[];
    private GreenfootImage walkingRightMouthOpenImages[];
    private GreenfootImage walkingLeftMouthOpenImages[];
    private static final int WALK_ANIMATION_DELAY = 8;
    private static final int COUNT_OF_WALKING_IMAGES = 3;
    private static final int COUNT_OF_ALTERNATE_WALKING_IMAGES = 2;
    private int walkingFrames;
    private int alternateWalkingFrames;

    // For mouth opening/closing animation
    private GreenfootImage openingMouthImages[];
    private GreenfootImage closingMouthImages[];
    private GreenfootImage openingMouthLeftImages[];
    private GreenfootImage closingMouthLeftImages[];
    private static final int COUNT_OF_MOUTH_OPENING_IMAGES = 2;
    private static final int COUNT_OF_MOUTH_CLOSING_IMAGES = 2;
    private int mouthOpeningFrames;
    private int mouthClosingFrames;

    /**
     * Constructor
     * 
     * This runs once when the Hero object is created.
     */
    Hero(int startingX)
    {
        // Set where hero begins horizontally
        currentScrollableWorldXPosition = startingX;

        // Game on
        isGameOver = false;

        // Sets the hero so that it isn't hurt
        isHurt = false;

        // First jump will be in 'down' direction
        verticalDirection = JUMPING_DOWN;

        // Facing right to start
        horizontalDirection = FACING_RIGHT;

        // Set image
        setImage("kirby-idle.png");

        // Initialize the 'walking' arrays
        walkingRightImages = new GreenfootImage[COUNT_OF_WALKING_IMAGES];
        walkingLeftImages = new GreenfootImage[COUNT_OF_WALKING_IMAGES];

        walkingRightMouthOpenImages = new GreenfootImage[COUNT_OF_ALTERNATE_WALKING_IMAGES];
        walkingLeftMouthOpenImages = new GreenfootImage[COUNT_OF_ALTERNATE_WALKING_IMAGES];

        // Load walking images from disk
        for (int i = 0; i < walkingRightImages.length; i++)
        {
            walkingRightImages[i] = new GreenfootImage("kirby-walk-right-" + i + ".png");

            // Create left-facing images by mirroring horizontally
            walkingLeftImages[i] = new GreenfootImage(walkingRightImages[i]);
            walkingLeftImages[i].mirrorHorizontally();
        }

        for (int i = 0; i < walkingRightMouthOpenImages.length; i++)
        {
            walkingRightMouthOpenImages[i] = new GreenfootImage("kirby-mouth-closed-walk-right-" + i + ".png");

            // Create left-facing images by mirroring horizontally
            walkingLeftMouthOpenImages[i] = new GreenfootImage(walkingRightMouthOpenImages[i]);
            walkingLeftMouthOpenImages[i].mirrorHorizontally();
        }

        // Track animation frames for walking
        walkingFrames = 0;
    }

    /**
     * Act - do whatever the Hero wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        checkKeys();
        checkFall();
        checkForWalls();
        checkForEnemies();
        checkForCeiling();
        if (!isGameOver)
        {
            checkGameOver();
        }

        // Get object reference to world
        SideScrollingWorld world = (SideScrollingWorld) getWorld();

        world.showText("Lives: " + lives, 480, 20);
        world.showText("Score: " + score, 100, 20);

    }

    /**
     * Respond to keyboard action from the user.
     */
    private void checkKeys()
    {
        // Walking keys
        if (Greenfoot.isKeyDown("left") && !isGameOver && !isMouthOpen)
        {
            moveLeft();
        }
        else if (Greenfoot.isKeyDown("right") && !isGameOver && !isMouthOpen)
        {
            moveRight();
        }
        else if (!isGameOver && !isEnemySwallowed)
        {
            // Standing still; reset walking animation
            walkingFrames = 0;
        }
        else if (!isGameOver && isEnemySwallowed)
        {
            // Standing still; reset walking animation
            alternateWalkingFrames = 0;

        }

        // Jumping
        if (Greenfoot.isKeyDown("up") && !isGameOver && !isDown && !isMouthOpen)
        {
            // Set the key as down (so it won't jump each frame)
            isDown = true;

            // Only able to jump when kirby has jumped less than 5 times
            if (numberOfJumps > 0)
            {
                jump();
                numberOfJumps -= 1;
                Greenfoot.playSound("kirby-jumping.wav");
            }
        }
        // Up key isn't down anymore
        if (!Greenfoot.isKeyDown("up") && isDown)
        {
            isDown = false;
        }

        // Attacking
        if (Greenfoot.isKeyDown("space") && !isGameOver && !isSpaceKeyDown && !isEnemySwallowed && !isMouthOpen)
        {
            // Set the key as down
            isSpaceKeyDown = true;

            // Open kirby's mouth
            isMouthOpen = true;

            Greenfoot.playSound("kirby-attacking.wav");

            animateOpenMouth(horizontalDirection);
        }
        else if (Greenfoot.isKeyDown("space") && !isGameOver && !isSpaceKeyDown && !isEnemySwallowed && isMouthOpen)
        {
            // Set the key as down
            isSpaceKeyDown = true;

            // Close kirby's mouth
            isMouthOpen = false;

            isEnemySwallowed = false;
        }
        else if (Greenfoot.isKeyDown("space") && !isGameOver && !isSpaceKeyDown && isEnemySwallowed && isMouthOpen)
        {
            // Set the key as down
            isSpaceKeyDown = true;

            // Close kirby's mouth
            isMouthOpen = false;

            isEnemySwallowed = false;

            // Add to the score
            score += 150;
        }

        // Space key isn't down anymore
        if (!Greenfoot.isKeyDown("space") && isSpaceKeyDown)
        {
            isSpaceKeyDown = false;
        }

    }

    /**
     * Should the hero be falling right now?
     */
    public void checkFall()
    {
        if (onPlatform())
        {
            // Stop falling
            deltaY = 0;

            // Reset the number of jumps kirby has
            numberOfJumps = 5;

            // Set image
            if (horizontalDirection == FACING_RIGHT && Greenfoot.isKeyDown("right") == false && !isEnemySwallowed && !isMouthOpen)
            {
                setImage("kirby-right.png");
            }
            else if (horizontalDirection == FACING_LEFT && Greenfoot.isKeyDown("left") == false && !isEnemySwallowed && !isMouthOpen)
            {
                setImage("kirby-left.png");
            }
            else if (horizontalDirection == FACING_RIGHT && Greenfoot.isKeyDown("right") == false && isEnemySwallowed && !isMouthOpen)
            {
                setImage("kirby-mouth-closed-right-idle.png");
            }
            else if (horizontalDirection == FACING_LEFT && Greenfoot.isKeyDown("left") == false && isEnemySwallowed && !isMouthOpen)
            {
                setImage("kirby-mouth-closed-left-idle.png");
            }
            else if (horizontalDirection == FACING_RIGHT && Greenfoot.isKeyDown("right") == false && !isEnemySwallowed && isMouthOpen)
            {
                setImage("kirby-mouth-open-right.png");
            }
            else if (horizontalDirection == FACING_LEFT && Greenfoot.isKeyDown("left") == false && !isEnemySwallowed && isMouthOpen)
            {
                setImage("kirby-mouth-open-left.png");
            }
            // Get a reference to any object that's created from a subclass of Platform,
            // that is below (or just below in front, or just below behind) the hero
            Actor directlyUnder = getOneObjectAtOffset(0, getImage().getHeight() / 2, Platform.class);
            Actor frontUnder = getOneObjectAtOffset(getImage().getWidth() / 3, getImage().getHeight() / 2, Platform.class);
            Actor rearUnder = getOneObjectAtOffset(0 - getImage().getWidth() / 3, getImage().getHeight() / 2, Platform.class);

            // Bump the hero back up so that they are not "submerged" in a platform object
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
    }

    /**
     * Is the hero currently touching a solid object? (any subclass of Platform)
     */
    public boolean onPlatform()
    {
        // Get an reference to a solid object (subclass of Platform) below the hero, if one exists
        Actor directlyUnder = getOneObjectAtOffset(0, getImage().getHeight() / 2, Platform.class);
        Actor frontUnder = getOneObjectAtOffset(getImage().getWidth() / 3, getImage().getHeight() / 2, Platform.class);
        Actor rearUnder = getOneObjectAtOffset(0 - getImage().getWidth() / 3, getImage().getHeight() / 2, Platform.class);

        // If there is no solid object below (or slightly in front of or behind) the hero...
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
     * Is the hero touching a wall?
     */
    public void checkForWalls()
    {
        // When the hero touches a wall, it is unable to go past the wall.
        Actor touchingWallFromFront = getOneObjectAtOffset(0 - getImage().getWidth() / 2, 0, Platform.class);
        Actor touchingWallFromBehind = getOneObjectAtOffset(0 + getImage().getWidth() / 2, 0, Platform.class);

        if (touchingWallFromFront != null  && horizontalDirection == FACING_LEFT || touchingWallFromBehind != null && horizontalDirection == FACING_RIGHT)
        {
            deltaX = 0;
        }
        else
        {
            deltaX = 4;
        }

    }

    /**
     * Is the hero touching a ceiling?
     */
    public void checkForCeiling()
    {
        // Get an reference to a solid object (subclass of Platform) below the hero, if one exists
        Actor directlyAbove = getOneObjectAtOffset(0, 0 - getImage().getWidth() / 2, Platform.class);

        // If there is no solid object below (or slightly in front of or behind) the hero...
        if (directlyAbove != null && !hasTouchedCeiling)
        {
            deltaY = 0;
            hasTouchedCeiling = true;
            int correctedYPosition = directlyAbove.getY() + directlyAbove.getImage().getHeight() / 2 + this.getImage().getHeight() / 2;
            setLocation(getX(), correctedYPosition);
        }
        else
        {
            hasTouchedCeiling = false;
        }
    }

    /**
     * Is the hero touching an enemy?
     */
    public void checkForEnemies()
    {
        if (isHurt == true)
        {
            if (iframes < 60)
            {
                iframes = iframes + 1;
            }
            if (iframes == 60)
            {
                iframes = 0;
                isHurt = false;
            }
        }

        // Reference to enemy
        Enemy touchingEnemy = (Enemy) getOneIntersectingObject(Enemy.class);

        // If the hero is touching the enemy and the hero is not invincible
        if (touchingEnemy != null && iframes == 0 && !isGameOver && !isMouthOpen)
        {
            // Lose a life when the enemy touches the hero.
            Greenfoot.playSound("kirby-hurt.wav");
            lives = lives - 1;
            isHurt = true;
        }
        else if (touchingEnemy != null && !isGameOver && isMouthOpen)
        {
            isEnemySwallowed = true;
            removeTouching(Enemy.class);
        }

    }

    /**
     * Make the hero jump.
     */
    public void jump()
    {
        // Track vertical direction
        verticalDirection = JUMPING_UP;

        // Set image
        if (horizontalDirection == FACING_RIGHT)
        {
            setImage("kirby-jump-up-right.png");
        }
        else
        {
            setImage("kirby-jump-up-left.png");
        }

        // Change the vertical speed to the power of the jump
        deltaY = jumpStrength;

        // Make the character move vertically 
        fall();
    }

    /**
     * Make the hero fall.
     */
    public void fall()
    {
        // See if direction has changed
        if (deltaY > 0)
        {
            verticalDirection = JUMPING_DOWN;

            // Set image
            if (horizontalDirection == FACING_RIGHT && !isMouthOpen)
            {
                setImage("kirby-jump-down-right.png");
            }
            else if (horizontalDirection == FACING_LEFT && !isMouthOpen)
            {
                setImage("kirby-jump-down-left.png");
            }
        }

        // Fall (move vertically)
        int newVisibleWorldYPosition = getY() + deltaY;
        setLocation(getX(), newVisibleWorldYPosition );

        // Accelerate (fall faster next time)
        if (deltaY < 10)
        {
            deltaY = deltaY + acceleration;
        }
    }

    /**
     * Animate opening the mouth
     */
    private void animateOpenMouth(String direction)
    {
        if (direction == FACING_RIGHT)
        {
            setImage("kirby-mouth-open-right.png");
        }
        if (direction == FACING_LEFT)
        {
            setImage("kirby-mouth-open-left.png");
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
        if (stage < walkingRightImages.length && !isEnemySwallowed)
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
        else if (stage < walkingRightMouthOpenImages.length && isEnemySwallowed)
        {
            // Set image for this stage of the animation
            if (direction == FACING_RIGHT)
            {
                setImage(walkingRightMouthOpenImages[stage]);
            }
            else
            {
                setImage(walkingLeftMouthOpenImages[stage]);
            }
        }
        else
        {
            // Start animation loop from beginning
            walkingFrames = 0;
        }
    }

    /**
     * Move the hero to the right.
     */
    public void moveRight()
    {
        // Track direction
        horizontalDirection = FACING_RIGHT;

        // Set image 
        if (onPlatform())
        {
            animateWalk(horizontalDirection);
        }
        else
        {
            // Set appropriate jumping image
            if (verticalDirection == JUMPING_UP)
            {
                setImage("kirby-jump-up-right.png");
            }
            else
            {
                setImage("kirby-jump-down-right.png");
            }
        }

        // Get object reference to world
        SideScrollingWorld world = (SideScrollingWorld) getWorld(); 

        // Decide whether to actually move, or make world's tiles move
        if (currentScrollableWorldXPosition < world.HALF_VISIBLE_WIDTH)
        {
            // HERO IS WITHIN EXTREME LEFT PORTION OF SCROLLABLE WORLD
            // So... actually move the actor within the visible world.

            // Move to right in visible world
            int newVisibleWorldXPosition = getX() + deltaX;
            setLocation(newVisibleWorldXPosition, getY());

            // Track position in wider scrolling world
            currentScrollableWorldXPosition = getX();
        }
        else if (currentScrollableWorldXPosition + deltaX * 2 > world.SCROLLABLE_WIDTH - world.HALF_VISIBLE_WIDTH)
        {
            // HERO IS WITHIN EXTREME RIGHT PORTION OF SCROLLABLE WORLD
            // So... actually move the actor within the visible world.

            // Allow movement only when not at edge of world
            if (currentScrollableWorldXPosition < world.SCROLLABLE_WIDTH - this.getImage().getWidth() / 2)
            {
                // Move to right in visible world
                int newVisibleWorldXPosition = getX() + deltaX;
                setLocation(newVisibleWorldXPosition, getY());

                // Track position in wider scrolling world
                currentScrollableWorldXPosition += deltaX;
            }
            else
            {
                isGameOver = true;
                world.setGameOver();
                Greenfoot.playSound("kirby-level-complete.wav");

                // Tell the user game is over
                world.showText("LEVEL COMPLETE", world.getWidth() / 2, world.getHeight() / 2);
            }

        }
        else
        {
            // HERO IS BETWEEN LEFT AND RIGHT PORTIONS OF SCROLLABLE WORLD
            // So... we move the other objects to create illusion of hero moving

            // Track position in wider scrolling world
            currentScrollableWorldXPosition += deltaX;

            List<Enemy> enemies = world.getObjects(Enemy.class);

            for (Enemy enemy : enemies)
            {
                enemy.moveLeft(deltaX);
            }
            // Get a list of all platforms (objects that need to move
            // to make hero look like they are moving)
            List<Platform> platforms = world.getObjects(Platform.class);

            // Move all the platform objects to make it look like hero is moving
            for (Platform platform : platforms)
            {
                // Platforms move left to make hero appear to move right
                platform.moveLeft(deltaX);
            }

            // Get a list of all decorations (objects that need to move
            // to make hero look like they are moving)
            List<Decoration> decorations = world.getObjects(Decoration.class);

            // Move all the decoration objects to make it look like hero is moving
            for (Decoration decoration: decorations)
            {
                // Platforms move left to make hero appear to move right
                decoration.moveLeft(deltaX);
            }

            // Get a list of all farAwayItems (objects that need to move
            // to make hero look like they are moving)
            List<FarAwayItem> farAwayItems = world.getObjects(FarAwayItem.class);

            // Move all the tile objects to make it look like hero is moving
            for (FarAwayItem farAwayItem : farAwayItems)
            {
                // FarAwayItems move left to make hero appear to move right
                farAwayItem.moveLeft(deltaX / 4);
            }

        }   

    }

    /**
     * Move the hero to the left.
     */
    public void moveLeft()
    {
        // Track direction
        horizontalDirection = FACING_LEFT;

        // Set image 
        if (onPlatform() && !isMouthOpen)
        {
            animateWalk(horizontalDirection);
        }
        else if (isMouthOpen)
        {

        }
        else
        {
            // Set appropriate jumping image
            if (verticalDirection == JUMPING_UP)
            {
                setImage("kirby-jump-up-left.png");
            }
            else
            {
                setImage("kirby-jump-down-left.png");
            }
        }

        // Get object reference to world
        SideScrollingWorld world = (SideScrollingWorld) getWorld(); 

        // Decide whether to actually move, or make world's tiles move
        if (currentScrollableWorldXPosition - deltaX < world.HALF_VISIBLE_WIDTH)
        {
            // HERO IS WITHIN EXTREME LEFT PORTION OF SCROLLABLE WORLD
            // So... actually move the actor within the visible world.

            // Don't let hero go off left edge of scrollable world 
            // (Allow movement only when not at left edge)
            if (currentScrollableWorldXPosition > 0)
            {
                // Move left in visible world
                int newVisibleWorldXPosition = getX() - deltaX;
                setLocation(newVisibleWorldXPosition, getY());

                // Track position in wider scrolling world
                currentScrollableWorldXPosition = getX();
            }            
        }
        else if (currentScrollableWorldXPosition + deltaX * 2 > world.SCROLLABLE_WIDTH - world.HALF_VISIBLE_WIDTH)
        {
            // HERO IS WITHIN EXTREME RIGHT PORTION OF SCROLLABLE WORLD
            // So... actually move the actor within the visible world.

            // Move left in visible world
            int newVisibleWorldXPosition = getX() - deltaX;
            setLocation(newVisibleWorldXPosition, getY());

            // Track position in wider scrolling world
            currentScrollableWorldXPosition -= deltaX;
        }        
        else
        {
            // HERO IS BETWEEN LEFT AND RIGHT PORTIONS OF SCROLLABLE WORLD
            // So... we move the other objects to create illusion of hero moving

            // Track position in wider scrolling world
            currentScrollableWorldXPosition -= deltaX;

            List<Enemy> enemies = world.getObjects(Enemy.class);

            for (Enemy enemy : enemies)
            {
                // Platforms move right to make hero appear to move left
                enemy.moveRight(deltaX);
            }

            // Get a list of all platforms (objects that need to move
            // to make hero look like they are moving)
            List<Platform> platforms = world.getObjects(Platform.class);

            // Move all the platform objects at same speed as hero
            for (Platform platform : platforms)
            {
                // Platforms move right to make hero appear to move left
                platform.moveRight(deltaX);
            }

            // Get a list of all decorations (objects that need to move
            // to make hero look like they are moving)
            List<Decoration> decorations = world.getObjects(Decoration.class);

            // Move all the decoration objects to make it look like hero is moving
            for (Decoration decoration: decorations)
            {
                // Platforms move right to make hero appear to move left
                decoration.moveRight(deltaX);
            }

            // Get a list of all items that are in the distance (far away items)
            List<FarAwayItem> farAwayItems = world.getObjects(FarAwayItem.class);

            // Move all the FarAwayItem objects at one quarter speed as hero to create depth illusion
            for (FarAwayItem farAwayItem : farAwayItems)
            {
                // FarAwayItems move right to make hero appear to move left
                farAwayItem.moveRight(deltaX / 4);
            }

        } 

    }

    /**
     * When the hero falls off the bottom of the screen,
     * game is over. We must remove them.
     */
    public void checkGameOver()
    {
        // Get object reference to world
        SideScrollingWorld world = (SideScrollingWorld) getWorld(); 

        // Vertical position where hero no longer visible
        int offScreenVerticalPosition = (world.getHeight() + this.getImage().getHeight() / 2);

        // Off bottom of screen?
        if (this.getY() > offScreenVerticalPosition || lives == 0)
        {
            // Remove the hero
            isGameOver = true;
            world.setGameOver();
            Greenfoot.playSound("kirby-game-over.wav");
            Greenfoot.playSound("kirby-fall.wav");
            // Tell the user game is over
            world.showText("GAME OVER", world.getWidth() / 2, world.getHeight() / 2);
        }
    }
}