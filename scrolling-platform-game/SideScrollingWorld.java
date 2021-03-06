import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Template for a side-scrolling platform game.
 * 
 * @author R. Gordon
 * @version May 8, 2019
 */
public class SideScrollingWorld extends World
{
    /**
     * Instance variables
     * 
     * These are available for use in any method below.
     */    
    // Tile size in pixels for world elements (blocks, clouds, etc)
    // TO STUDENTS: Modify if your game's tiles have different dimensions
    private static final int TILE_SIZE = 32;
    private static final int HALF_TILE_SIZE = TILE_SIZE / 2;

    // World size constants
    // TO STUDENTS: Modify only if you're sure
    //              Should be a resolution that's a multiple of TILE_SIZE
    private static final int VISIBLE_WIDTH = 640;
    private static final int VISIBLE_HEIGHT = 480;

    // Additional useful constants based on world size
    public static final int HALF_VISIBLE_WIDTH = VISIBLE_WIDTH / 2;
    private static final int HALF_VISIBLE_HEIGHT = VISIBLE_HEIGHT / 2;

    // Defining the boundaries of the scrollable world
    // TO STUDENTS: Modify SCROLLABLE_WIDTH if you wish to have a longer level
    public static final int SCROLLABLE_WIDTH = VISIBLE_WIDTH * 3;
    private static final int SCROLLABLE_HEIGHT = VISIBLE_HEIGHT;

    // Hero
    Hero theHero;

    // Track whether game is on
    private boolean isGameOver;

    // Track frames
    public int frames = 0;

    /**
     * Constructor for objects of class SideScrollingWorld.
     */
    public SideScrollingWorld()
    {    
        // Create a new world with 640x480 cells with a cell size of 1x1 pixels.
        // Final argument of 'false' means that actors in the world are not restricted to the world boundary.
        // See: https://www.greenfoot.org/files/javadoc/greenfoot/World.html#World-int-int-int-boolean-
        super(VISIBLE_WIDTH, VISIBLE_HEIGHT, 1, false);

        // Set up the starting scene
        setup();

        // Game on
        isGameOver = false;
    }

    /**
     * Set up the entire world.
     */
    private void setup()
    {
        // TO STUDENTS: Add, revise, or remove methods as needed to define your own game's world
        // Add clouds
        addClouds();

        // Add enemies
        addEnemy();

        // Add the hero
        addHero();

        // Add the platforms
        addPlatforms();

        // Add the decoration
        addDecoration();
    }

    /**
     * Add the platforms
     */
    private void addPlatforms()
    {
        for (int i = 0; i < 60; i += 1)
        {
            //       (x position)   + (centers the tile)
            int x = (0 * TILE_SIZE) + (i * TILE_SIZE) + HALF_TILE_SIZE;

            int y = (-1 * TILE_SIZE) + HALF_TILE_SIZE;
            GroundRight ground = new GroundRight(x, y);
            addObject(ground, x, y);
        }
        
        for (int i = 0; i < 14; i += 1)
        {
            //       (x position)   + (centers the tile)
            int x = (1 * TILE_SIZE) + HALF_TILE_SIZE;

            int y = (0 * TILE_SIZE) + (i * TILE_SIZE) + HALF_TILE_SIZE;
            GroundRight ground = new GroundRight(x, y);
            addObject(ground, x, y);
        }

        for (int i = 0; i < 8; i += 1)
        {
            //       (x position)   + (centers the tile)
            int x = (2 * TILE_SIZE) + (i * TILE_SIZE) + HALF_TILE_SIZE;

            int y = (14 * TILE_SIZE) + HALF_TILE_SIZE;
            GroundTopMiddle ground = new GroundTopMiddle(x, y);
            addObject(ground, x, y);
        }

        for (int i = 0; i < 5; i += 1)
        {
            //       (x position)   + (centers the tile)
            int x = (10 * TILE_SIZE) + HALF_TILE_SIZE;

            int y = (9 * TILE_SIZE) + (i * TILE_SIZE) + HALF_TILE_SIZE;
            GroundLeft ground = new GroundLeft(x, y);
            addObject(ground, x, y);
        }

        for (int i = 0; i < 5; i += 1)
        {
            //       (x position)   + (centers the tile)
            int x = (12 * TILE_SIZE) + HALF_TILE_SIZE;

            int y = (9 * TILE_SIZE) + (i * TILE_SIZE) + HALF_TILE_SIZE;
            GroundRight ground = new GroundRight(x, y);
            addObject(ground, x, y);
        }

        for (int i = 0; i < 11; i += 1)
        {
            //       (x position)   + (centers the tile)
            int x = (13 * TILE_SIZE) + (i * TILE_SIZE) + HALF_TILE_SIZE;

            int y = (14 * TILE_SIZE) + HALF_TILE_SIZE;
            GroundTopMiddle ground = new GroundTopMiddle(x, y);
            addObject(ground, x, y);
        }

        for (int i = 0; i < 6; i += 1)
        {
            //       (x position)   + (centers the tile)
            int x = (24 * TILE_SIZE) + HALF_TILE_SIZE;

            int y = (8 * TILE_SIZE) + (i * TILE_SIZE) + HALF_TILE_SIZE;
            GroundLeft ground = new GroundLeft(x, y);
            addObject(ground, x, y);
        }

        for (int i = 0; i < 5; i += 1)
        {
            //       (x position)   + (centers the tile)
            int x = (19 * TILE_SIZE) + (i * TILE_SIZE) + HALF_TILE_SIZE;

            int y = (7 * TILE_SIZE) + HALF_TILE_SIZE;
            GroundBottomMiddle ground = new GroundBottomMiddle(x, y);
            addObject(ground, x, y);
        }

        for (int i = 0; i < 2; i += 1)
        {
            //       (x position)   + (centers the tile)
            int x = (18 * TILE_SIZE) + HALF_TILE_SIZE;

            int y = (5 * TILE_SIZE) + (i * TILE_SIZE) + HALF_TILE_SIZE;
            GroundLeft ground = new GroundLeft(x, y);
            addObject(ground, x, y);
        }

        for (int i = 0; i < 7; i += 1)
        {
            //       (x position)   + (centers the tile)
            int x = (21 * TILE_SIZE) + (i * TILE_SIZE) + HALF_TILE_SIZE;

            int y = (6 * TILE_SIZE) + HALF_TILE_SIZE;
            GroundTopMiddle ground = new GroundTopMiddle(x, y);
            addObject(ground, x, y);
        }

        for (int i = 0; i < 8; i += 1)
        {
            //       (x position)   + (centers the tile)
            int x = (29 * TILE_SIZE) + HALF_TILE_SIZE;

            int y = (6 * TILE_SIZE) + (i * TILE_SIZE) + HALF_TILE_SIZE;
            GroundRight ground = new GroundRight(x, y);
            addObject(ground, x, y);
        }

        for (int i = 0; i < 13; i += 1)
        {
            //       (x position)   + (centers the tile)
            int x = (30 * TILE_SIZE) + (i * TILE_SIZE) + HALF_TILE_SIZE;

            int y = (14 * TILE_SIZE) + HALF_TILE_SIZE;
            GroundTopMiddle ground = new GroundTopMiddle(x, y);
            addObject(ground, x, y);
        }

        for (int i = 0; i < 9; i += 1)
        {
            //       (x position)   + (centers the tile)
            int x = (33 * TILE_SIZE) + HALF_TILE_SIZE;

            int y = (0 * TILE_SIZE) + (i * TILE_SIZE) + HALF_TILE_SIZE;
            GroundLeft ground = new GroundLeft(x, y);
            addObject(ground, x, y);
        }

        for (int i = 0; i < 3; i += 1)
        {
            //       (x position)   + (centers the tile)
            int x = (34 * TILE_SIZE) + (i * TILE_SIZE) + HALF_TILE_SIZE;

            int y = (9 * TILE_SIZE) + HALF_TILE_SIZE;
            GroundBottomMiddle ground = new GroundBottomMiddle(x, y);
            addObject(ground, x, y);
        }

        for (int i = 0; i < 3; i += 1)
        {
            //       (x position)   + (centers the tile)
            int x = (37 * TILE_SIZE) + HALF_TILE_SIZE;

            int y = (6 * TILE_SIZE) + (i * TILE_SIZE) + HALF_TILE_SIZE;
            GroundRight ground = new GroundRight(x, y);
            addObject(ground, x, y);
        }

        for (int i = 0; i < 2; i += 1)
        {
            //       (x position)   + (centers the tile)
            int x = (36 * TILE_SIZE) + HALF_TILE_SIZE;

            int y = (3 * TILE_SIZE) + (i * TILE_SIZE) + HALF_TILE_SIZE;
            GroundRight ground = new GroundRight(x, y);
            addObject(ground, x, y);
        }

        for (int i = 0; i < 2; i += 1)
        {
            //       (x position)   + (centers the tile)
            int x = (37 * TILE_SIZE) + HALF_TILE_SIZE;

            int y = (0 * TILE_SIZE) + (i * TILE_SIZE) + HALF_TILE_SIZE;
            GroundRight ground = new GroundRight(x, y);
            addObject(ground, x, y);
        }

        for (int i = 0; i < 2; i += 1)
        {
            //       (x position)   + (centers the tile)
            int x = (43 * TILE_SIZE) + HALF_TILE_SIZE;

            int y = (12 * TILE_SIZE) + (i * TILE_SIZE) + HALF_TILE_SIZE;
            GroundLeft ground = new GroundLeft(x, y);
            addObject(ground, x, y);
        }

        for (int i = 0; i < 2; i += 1)
        {
            //       (x position)   + (centers the tile)
            int x = (44 * TILE_SIZE) + HALF_TILE_SIZE;

            int y = (9 * TILE_SIZE) + (i * TILE_SIZE) + HALF_TILE_SIZE;
            GroundLeft ground = new GroundLeft(x, y);
            addObject(ground, x, y);
        }

        for (int i = 0; i < 4; i += 1)
        {
            //       (x position)   + (centers the tile)
            int x = (43 * TILE_SIZE) + HALF_TILE_SIZE;

            int y = (4 * TILE_SIZE) + (i * TILE_SIZE) + HALF_TILE_SIZE;
            GroundLeft ground = new GroundLeft(x, y);
            addObject(ground, x, y);
        }

        for (int i = 0; i < 11; i += 1)
        {
            //       (x position)   + (centers the tile)
            int x = (45 * TILE_SIZE) + HALF_TILE_SIZE;

            int y = (4 * TILE_SIZE) + (i * TILE_SIZE) + HALF_TILE_SIZE;
            GroundRight ground = new GroundRight(x, y);
            addObject(ground, x, y);
        }

        for (int i = 0; i < 6; i += 1)
        {
            //       (x position)   + (centers the tile)
            int x = (55 * TILE_SIZE) + HALF_TILE_SIZE;

            int y = (9 * TILE_SIZE) + (i * TILE_SIZE) + HALF_TILE_SIZE;
            GroundLeft ground = new GroundLeft(x, y);
            addObject(ground, x, y);
        }

        for (int i = 0; i < 4; i += 1)
        {
            //       (x position)   + (centers the tile)
            int x = (56 * TILE_SIZE) + (i * TILE_SIZE) + HALF_TILE_SIZE;

            int y = (8 * TILE_SIZE) + HALF_TILE_SIZE;
            GroundTopMiddle ground = new GroundTopMiddle(x, y);
            addObject(ground, x, y);
        }

        GroundTopLeft ground1 = new GroundTopLeft((10 * TILE_SIZE) + HALF_TILE_SIZE, (8 * TILE_SIZE) + HALF_TILE_SIZE);
        addObject(ground1, (10 * TILE_SIZE) + HALF_TILE_SIZE, (8 * TILE_SIZE) + HALF_TILE_SIZE);

        GroundTopMiddle ground2 = new GroundTopMiddle((11 * TILE_SIZE) + HALF_TILE_SIZE, (8 * TILE_SIZE) + HALF_TILE_SIZE);
        addObject(ground2, (11 * TILE_SIZE) + HALF_TILE_SIZE, (8 * TILE_SIZE) + HALF_TILE_SIZE);

        GroundTopRight ground3 = new GroundTopRight((12 * TILE_SIZE) + HALF_TILE_SIZE, (8 * TILE_SIZE) + HALF_TILE_SIZE);
        addObject(ground3, (12 * TILE_SIZE) + HALF_TILE_SIZE, (8 * TILE_SIZE) + HALF_TILE_SIZE);

        GroundBottomLeft ground4 = new GroundBottomLeft((18 * TILE_SIZE) + HALF_TILE_SIZE, (7 * TILE_SIZE) + HALF_TILE_SIZE);
        addObject(ground4, (18 * TILE_SIZE) + HALF_TILE_SIZE, (7 * TILE_SIZE) + HALF_TILE_SIZE);

        GroundTopLeft ground5 = new GroundTopLeft((18 * TILE_SIZE) + HALF_TILE_SIZE, (4 * TILE_SIZE) + HALF_TILE_SIZE);
        addObject(ground5, (18 * TILE_SIZE) + HALF_TILE_SIZE, (4 * TILE_SIZE) + HALF_TILE_SIZE);

        GroundTopMiddle ground6 = new GroundTopMiddle((19 * TILE_SIZE) + HALF_TILE_SIZE, (4 * TILE_SIZE) + HALF_TILE_SIZE);
        addObject(ground6, (19 * TILE_SIZE) + HALF_TILE_SIZE, (4 * TILE_SIZE) + HALF_TILE_SIZE);

        GroundTopRight ground7 = new GroundTopRight((20 * TILE_SIZE) + HALF_TILE_SIZE, (4 * TILE_SIZE) + HALF_TILE_SIZE);
        addObject(ground7, (20 * TILE_SIZE) + HALF_TILE_SIZE, (4 * TILE_SIZE) + HALF_TILE_SIZE);

        GroundRight ground8 = new GroundRight((20 * TILE_SIZE) + HALF_TILE_SIZE, (5 * TILE_SIZE) + HALF_TILE_SIZE);
        addObject(ground8, (20 * TILE_SIZE) + HALF_TILE_SIZE, (5 * TILE_SIZE) + HALF_TILE_SIZE);

        GroundTopLeft ground9 = new GroundTopLeft((28 * TILE_SIZE) + HALF_TILE_SIZE, (5 * TILE_SIZE) + HALF_TILE_SIZE);
        addObject(ground9, (28 * TILE_SIZE) + HALF_TILE_SIZE, (5 * TILE_SIZE) + HALF_TILE_SIZE);

        GroundTopRight ground10 = new GroundTopRight((29 * TILE_SIZE) + HALF_TILE_SIZE, (5 * TILE_SIZE) + HALF_TILE_SIZE);
        addObject(ground10, (29 * TILE_SIZE) + HALF_TILE_SIZE, (5 * TILE_SIZE) + HALF_TILE_SIZE);

        GroundBottomLeft ground11 = new GroundBottomLeft((33 * TILE_SIZE) + HALF_TILE_SIZE, (9 * TILE_SIZE) + HALF_TILE_SIZE);
        addObject(ground11, (33 * TILE_SIZE) + HALF_TILE_SIZE, (9 * TILE_SIZE) + HALF_TILE_SIZE);

        GroundBottomRight ground12 = new GroundBottomRight((37 * TILE_SIZE) + HALF_TILE_SIZE, (9 * TILE_SIZE) + HALF_TILE_SIZE);
        addObject(ground12, (37 * TILE_SIZE) + HALF_TILE_SIZE, (9 * TILE_SIZE) + HALF_TILE_SIZE);

        GroundBottomRight ground13 = new GroundBottomRight((37 * TILE_SIZE) + HALF_TILE_SIZE, (2 * TILE_SIZE) + HALF_TILE_SIZE);
        addObject(ground13, (37 * TILE_SIZE) + HALF_TILE_SIZE, (2 * TILE_SIZE) + HALF_TILE_SIZE);

        GroundTopRight ground14 = new GroundTopRight((37 * TILE_SIZE) + HALF_TILE_SIZE, (5 * TILE_SIZE) + HALF_TILE_SIZE);
        addObject(ground14, (37 * TILE_SIZE) + HALF_TILE_SIZE, (5 * TILE_SIZE) + HALF_TILE_SIZE);

        GroundTopLeft ground15 = new GroundTopLeft((43 * TILE_SIZE) + HALF_TILE_SIZE, (3 * TILE_SIZE) + HALF_TILE_SIZE);
        addObject(ground15, (43 * TILE_SIZE) + HALF_TILE_SIZE, (3 * TILE_SIZE) + HALF_TILE_SIZE);

        GroundTopLeft ground16 = new GroundTopLeft((43 * TILE_SIZE) + HALF_TILE_SIZE, (11 * TILE_SIZE) + HALF_TILE_SIZE);
        addObject(ground16, (43 * TILE_SIZE) + HALF_TILE_SIZE, (11 * TILE_SIZE) + HALF_TILE_SIZE);

        GroundBottomLeft ground17 = new GroundBottomLeft((43 * TILE_SIZE) + HALF_TILE_SIZE, (8 * TILE_SIZE) + HALF_TILE_SIZE);
        addObject(ground17, (43 * TILE_SIZE) + HALF_TILE_SIZE, (8 * TILE_SIZE) + HALF_TILE_SIZE);

        GroundTopRight ground18 = new GroundTopRight((45 * TILE_SIZE) + HALF_TILE_SIZE, (3 * TILE_SIZE) + HALF_TILE_SIZE);
        addObject(ground18, (45 * TILE_SIZE) + HALF_TILE_SIZE, (3 * TILE_SIZE) + HALF_TILE_SIZE);

        GroundTopMiddle ground19 = new GroundTopMiddle((44 * TILE_SIZE) + HALF_TILE_SIZE, (3 * TILE_SIZE) + HALF_TILE_SIZE);
        addObject(ground19, (44 * TILE_SIZE) + HALF_TILE_SIZE, (3 * TILE_SIZE) + HALF_TILE_SIZE);

        GroundTopLeft ground20 = new GroundTopLeft((55 * TILE_SIZE) + HALF_TILE_SIZE, (8 * TILE_SIZE) + HALF_TILE_SIZE);
        addObject(ground20, (55 * TILE_SIZE) + HALF_TILE_SIZE, (8 * TILE_SIZE) + HALF_TILE_SIZE);
    }

    /**
     * Add the decoration.
     */
    private void addDecoration()
    {
        for (int i = 0; i < 14; i += 1)
        {
            //       (x position)   + (centers the tile)
            int x = (0 * TILE_SIZE) + HALF_TILE_SIZE;

            int y = (0 * TILE_SIZE) + (i * TILE_SIZE) + HALF_TILE_SIZE;
            GroundMiddle ground = new GroundMiddle(x, y);
            addObject(ground, x, y);
        }

        for (int i = 0; i < 2; i += 1)
        {
            //       (x position)   + (centers the tile)
            int x = (0 * TILE_SIZE) + (i * TILE_SIZE) + HALF_TILE_SIZE;

            int y = (14 * TILE_SIZE) + HALF_TILE_SIZE;
            GroundMiddle ground = new GroundMiddle(x, y);
            addObject(ground, x, y);
        }

        for (int i = 0; i < 5; i += 1)
        {
            //       (x position)   + (centers the tile)
            int x = (11 * TILE_SIZE) + HALF_TILE_SIZE;

            int y = (9 * TILE_SIZE) + (i * TILE_SIZE) + HALF_TILE_SIZE;
            GroundMiddle ground = new GroundMiddle(x, y);
            addObject(ground, x, y);
        }

        for (int i = 0; i < 3; i += 1)
        {
            //       (x position)   + (centers the tile)
            int x = (10 * TILE_SIZE) + (i * TILE_SIZE) + HALF_TILE_SIZE;

            int y = (14 * TILE_SIZE) + HALF_TILE_SIZE;
            GroundMiddle ground = new GroundMiddle(x, y);
            addObject(ground, x, y);
        }

        for (int i = 0; i < 4; i += 1)
        {
            //       (x position)   + (centers the tile)
            int x = (24 * TILE_SIZE) + (i * TILE_SIZE) + HALF_TILE_SIZE;

            int y = (7 * TILE_SIZE) + HALF_TILE_SIZE;
            GroundMiddle ground = new GroundMiddle(x, y);
            addObject(ground, x, y);
        }

        for (int i = 0; i < 2; i += 1)
        {
            //       (x position)   + (centers the tile)
            int x = (19 * TILE_SIZE) + HALF_TILE_SIZE;

            int y = (5 * TILE_SIZE) + (i * TILE_SIZE) + HALF_TILE_SIZE;
            GroundMiddle ground = new GroundMiddle(x, y);
            addObject(ground, x, y);
        }

        for (int i = 0; i < 6; i += 1)
        {
            //       (x position)   + (centers the tile)
            int x = (24 * TILE_SIZE) + (i * TILE_SIZE) + HALF_TILE_SIZE;

            int y = (14 * TILE_SIZE) + HALF_TILE_SIZE;
            GroundMiddle ground = new GroundMiddle(x, y);
            addObject(ground, x, y);
        }

        for (int i = 0; i < 8; i += 1)
        {
            //       (x position)   + (centers the tile)
            int x = (28 * TILE_SIZE) + HALF_TILE_SIZE;

            int y = (6 * TILE_SIZE) + (i * TILE_SIZE) + HALF_TILE_SIZE;
            GroundMiddle ground = new GroundMiddle(x, y);
            addObject(ground, x, y);
        }

        for (int i = 0; i < 3; i += 1)
        {
            for (int j = 0; j < 6; j += 1)
            {
                //       (x position)   + (centers the tile)
                int x = (25 * TILE_SIZE) + (i * TILE_SIZE) + HALF_TILE_SIZE;

                int y = (8 * TILE_SIZE) + (j * TILE_SIZE) + HALF_TILE_SIZE;
                GroundMiddle ground = new GroundMiddle(x, y);
                addObject(ground, x, y);
            }
        }

        for (int i = 0; i < 2; i += 1)
        {
            for (int j = 0; j < 9; j += 1)
            {
                //       (x position)   + (centers the tile)
                int x = (34 * TILE_SIZE) + (i * TILE_SIZE) + HALF_TILE_SIZE;

                int y = (0 * TILE_SIZE) + (j * TILE_SIZE) + HALF_TILE_SIZE;
                GroundMiddle ground = new GroundMiddle(x, y);
                addObject(ground, x, y);
            }
        }

        for (int i = 0; i < 3; i += 1)
        {
            //       (x position)   + (centers the tile)
            int x = (36 * TILE_SIZE) + HALF_TILE_SIZE;

            int y = (0 * TILE_SIZE) + (i * TILE_SIZE) + HALF_TILE_SIZE;
            GroundMiddle ground = new GroundMiddle(x, y);
            addObject(ground, x, y);
        }

        for (int i = 0; i < 4; i += 1)
        {
            //       (x position)   + (centers the tile)
            int x = (36 * TILE_SIZE) + HALF_TILE_SIZE;

            int y = (5 * TILE_SIZE) + (i * TILE_SIZE) + HALF_TILE_SIZE;
            GroundMiddle ground = new GroundMiddle(x, y);
            addObject(ground, x, y);
        }

        for (int i = 0; i < 5; i += 1)
        {
            //       (x position)   + (centers the tile)
            int x = (44 * TILE_SIZE) + HALF_TILE_SIZE;

            int y = (4 * TILE_SIZE) + (i * TILE_SIZE) + HALF_TILE_SIZE;
            GroundMiddle ground = new GroundMiddle(x, y);
            addObject(ground, x, y);
        }

        for (int i = 0; i < 3; i += 1)
        {
            //       (x position)   + (centers the tile)
            int x = (44 * TILE_SIZE) + HALF_TILE_SIZE;

            int y = (11 * TILE_SIZE) + (i * TILE_SIZE) + HALF_TILE_SIZE;
            GroundMiddle ground = new GroundMiddle(x, y);
            addObject(ground, x, y);
        }

        for (int i = 0; i < 2; i += 1)
        {
            //       (x position)   + (centers the tile)
            int x = (43 * TILE_SIZE) + (i * TILE_SIZE) + HALF_TILE_SIZE;

            int y = (14 * TILE_SIZE) + HALF_TILE_SIZE;
            GroundMiddle ground = new GroundMiddle(x, y);
            addObject(ground, x, y);
        }

        for (int i = 0; i < 4; i += 1)
        {
            for (int j = 0; j < 6; j += 1)
            {
                //       (x position)   + (centers the tile)
                int x = (56 * TILE_SIZE) + (i * TILE_SIZE) + HALF_TILE_SIZE;

                int y = (9 * TILE_SIZE) + (j * TILE_SIZE) + HALF_TILE_SIZE;
                GroundMiddle ground = new GroundMiddle(x, y);
                addObject(ground, x, y);
            }
        }
        
        GroundMiddle ground1 = new GroundMiddle((20 * TILE_SIZE) + HALF_TILE_SIZE, (6 * TILE_SIZE) + HALF_TILE_SIZE);
        addObject(ground1, (20 * TILE_SIZE) + HALF_TILE_SIZE, (6 * TILE_SIZE) + HALF_TILE_SIZE);
    }

    /**
     * Add a few clouds for the opening scene.
     */
    private void addClouds()
    {
        Cloud cloud1 = new Cloud(170, 125);
        addObject(cloud1, 170, 125);
        Cloud cloud2 = new Cloud(450, 175);
        addObject(cloud2, 450, 175);
        Cloud cloud3 = new Cloud(775, 50);
        addObject(cloud3, 775, 50);
    }

    /**
     * Act
     * 
     * This method is called approximately 60 times per second.
     */
    public void act()
    {
        // Track the number of frames for time.
        frames += 1;

        // If statement is done every 60 frames (1 second)
        if ( (frames % 60 == 0) && !isGameOver)
        {
            showText("Time: " + frames / 60, 580, 20);
        }

        if ((frames % 9900 == 0) || frames == 1)
        {
            Greenfoot.playSound("kirby-bgm.mp3");
        }
    }

    /**
     * Add the enemies
     */
    private void addEnemy()
    {
        Waddle_Dee waddle_dee1 = new Waddle_Dee((9 * TILE_SIZE) + HALF_TILE_SIZE, (11 * TILE_SIZE) + HALF_TILE_SIZE);
        addObject(waddle_dee1, (9 * TILE_SIZE) + HALF_TILE_SIZE, (11 * TILE_SIZE) + HALF_TILE_SIZE);

        Waddle_Dee waddle_dee2 = new Waddle_Dee((21 * TILE_SIZE) + HALF_TILE_SIZE, (13 * TILE_SIZE) + HALF_TILE_SIZE);
        addObject(waddle_dee2, (21 * TILE_SIZE) + HALF_TILE_SIZE, (13 * TILE_SIZE) + HALF_TILE_SIZE);

        Waddle_Dee waddle_dee3 = new Waddle_Dee((27 * TILE_SIZE) + HALF_TILE_SIZE, (5 * TILE_SIZE) + HALF_TILE_SIZE);
        addObject(waddle_dee3, (27 * TILE_SIZE) + HALF_TILE_SIZE, (5 * TILE_SIZE) + HALF_TILE_SIZE);

        Waddle_Dee waddle_dee4 = new Waddle_Dee((40 * TILE_SIZE) + HALF_TILE_SIZE, (13 * TILE_SIZE) + HALF_TILE_SIZE);
        addObject(waddle_dee4, (40 * TILE_SIZE) + HALF_TILE_SIZE, (13 * TILE_SIZE) + HALF_TILE_SIZE);

        Bird bird1 = new Bird((22 * TILE_SIZE) + HALF_TILE_SIZE, (12 * TILE_SIZE) + HALF_TILE_SIZE);
        addObject(bird1, (22 * TILE_SIZE) + HALF_TILE_SIZE, (12 * TILE_SIZE) + HALF_TILE_SIZE);

        Bird bird2 = new Bird((38 * TILE_SIZE) + HALF_TILE_SIZE, (8 * TILE_SIZE) + HALF_TILE_SIZE);
        addObject(bird2, (38 * TILE_SIZE) + HALF_TILE_SIZE, (8 * TILE_SIZE) + HALF_TILE_SIZE);

        Bird bird3 = new Bird((41 * TILE_SIZE) + HALF_TILE_SIZE, (6 * TILE_SIZE) + HALF_TILE_SIZE);
        addObject(bird3, (41 * TILE_SIZE) + HALF_TILE_SIZE, (6 * TILE_SIZE) + HALF_TILE_SIZE);
    }

    /**
     * Add the hero to the world.
     */
    private void addHero()
    {
        // Initial horizontal position
        int initialX = TILE_SIZE * 3;

        // Instantiate the hero object
        theHero = new Hero(initialX);

        // Add hero in bottom left corner of screen
        addObject(theHero, initialX, 13 * TILE_SIZE + HALF_TILE_SIZE);
    }

    /**
     * Return an object reference to the hero.
     */
    public Hero getHero()
    {
        return theHero;
    }

    /**
     * Set game over
     */
    public void setGameOver()
    {
        isGameOver = true;
    }
}

