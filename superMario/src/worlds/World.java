package worlds;

import entities.EntityManager;
import entities.creatures.*;
import entities.items.ItemManager;
import game.Handler;
import tiles.Tile;
import utils.Utils;

import java.awt.*;

public class World
{
    private Handler handler;
    private int width, height;
    private int spawnx, spawny;

    // Holds id's saying which tile should be rendered in which position.
    private int[][] tiles;

    //Entities
    private EntityManager entityManager;
    //ItemManager
    private ItemManager itemManager;

    // Load world from a file.
    public World(Handler handler, String path)
    {
        this.handler = handler;
        itemManager = new ItemManager(handler);
        loadWorld(path);
    }

    public void initEntities()
    {
        // player
        entityManager = new EntityManager(handler, new Mario(handler, 150, 895,1));

        // Enemies
        entityManager.addEntity(new GreenKoopaTroopa(handler, 33 * Tile.TILEWIDTH, handler.getHeight() - Creature.DEFAULT_32x32_HEIGHT * 2, 1));
        entityManager.addEntity(new GreenKoopaTroopa(handler, 30 * Tile.TILEWIDTH, handler.getHeight() - Creature.DEFAULT_32x32_HEIGHT * 2, 1));
        entityManager.addEntity(new Beetle(handler, 43 * Tile.TILEWIDTH, handler.getHeight() - Creature.DEFAULT_16x16_HEIGHT * 2, 1));
        entityManager.addEntity(new Lakitu(handler, 105 * Tile.TILEWIDTH, handler.getHeight() - Creature.DEFAULT_32x32_HEIGHT * 4 - 50, 1));
        //entityManager.addEntity(new Lakitu(handler, 95 * Tile.TILEWIDTH, handler.getHeight() - Creature.DEFAULT_32x32_HEIGHT * 4 - 50, 1));
        entityManager.addEntity(new Lakitu(handler, 85 * Tile.TILEWIDTH, handler.getHeight() - Creature.DEFAULT_32x32_HEIGHT * 4 - 50, 1));
        entityManager.addEntity(new Goomba(handler, 105 * Tile.TILEWIDTH, handler.getHeight() - Creature.DEFAULT_16x16_HEIGHT * 2, 1));
        entityManager.addEntity(new Goomba(handler, 95 * Tile.TILEWIDTH, handler.getHeight() - Creature.DEFAULT_16x16_HEIGHT * 2, 1));
        entityManager.addEntity(new Bowser(handler, 320 * Tile.TILEWIDTH, handler.getHeight() - Creature.DEFAULT_32x32_HEIGHT * 4, 1));
        entityManager.addEntity(new Goomba(handler, 273 * Tile.TILEWIDTH, handler.getHeight() - Creature.DEFAULT_16x16_HEIGHT * 2, 1));
        entityManager.addEntity(new Goomba(handler, 238 * Tile.TILEWIDTH, handler.getHeight() - Creature.DEFAULT_16x16_HEIGHT * 2, 1));
        entityManager.addEntity(new Goomba(handler, 213 * Tile.TILEWIDTH, handler.getHeight() - Creature.DEFAULT_16x16_HEIGHT * 2, 1));
        entityManager.addEntity(new Goomba(handler, 190 * Tile.TILEWIDTH, handler.getHeight() - Creature.DEFAULT_16x16_HEIGHT * 2, 1));
        entityManager.addEntity(new HammerBrother(handler, 312 * Tile.TILEWIDTH, handler.getHeight() - Creature.DEFAULT_32x32_HEIGHT * 2, 1));
        //entityManager.addEntity(new RedKoopaTroopa(handler, 200 * Tile.TILEWIDTH, handler.getHeight() - Creature.DEFAULT_32x32_HEIGHT * 4, 1));
        //entityManager.addEntity(new BulletBill(handler, 105 * Tile.TILEWIDTH, handler.getHeight() - Creature.DEFAULT_16x16_HEIGHT * 8, 1));
        //entityManager.addEntity(new PiranhaPlant(handler,41 * Tile.TILEWIDTH, handler.getHeight() - Creature.DEFAULT_32x32_HEIGHT * 2, 1));
        //entityManager.addEntity(new PiranhaPlant(handler,42 * Tile.TILEWIDTH, handler.getHeight() - Creature.DEFAULT_32x32_HEIGHT * 2, 1));

        //entityManager.addEntity(new FlyingGreenKoopaTroopa(handler, 45 * Tile.TILEWIDTH, handler.getHeight() - Creature.DEFAULT_32x32_HEIGHT * 3, 1));
        //entityManager.addEntity(new FlyingRedKoopaTroopa(handler, 50 * Tile.TILEWIDTH, handler.getHeight() - Creature.DEFAULT_32x32_HEIGHT * 2, 1));
        //entityManager.addEntity(new Coin(handler, 22 * Tile.TILEWIDTH, handler.getHeight() - Creature.DEFAULT_32x32_HEIGHT * 4, 1));

        entityManager.getMario().setX(spawnx);
        entityManager.getMario().setY(spawny);
    }

    public void tick()
    {
        itemManager.tick();
        entityManager.tick();
    }

    // Render every tile to the screen.
    public void render(Graphics g)
    {
        // Render efficiency

        // check if 0 is greater or less than offset in tiles in x - direction.
        // game.getGameCamera().getxOffset() / Tile.TILEWIDTH -> will convert offset in pixel to offset in tiles
        int xStart = (int) Math.max(0, handler.getGameCamera().getxOffset() / Tile.TILEWIDTH);
        // Check if width is greater or less than offset in tiles in x - direction.
        // (game.getGameCamera().getxOffset() + game.getWidth()) / Tile.TILEWIDTH + 1 -> will convert offset in pixel to offset in tiles
        int xEnd = (int) Math.min(width, (handler.getGameCamera().getxOffset() + handler.getWidth()) / Tile.TILEWIDTH + 1);

        for (int y = 0; y < height; y++)
        {
            for (int x = xStart; x < xEnd; x++)
            {
                getTile(x, y).render(g, (int) (x * Tile.TILEWIDTH - handler.getGameCamera().getxOffset()),
                        (int) (y * Tile.TILEHEIGHT - handler.getGameCamera().getyOffset()));
            }
        }

        //Entities
        entityManager.render(g);
        itemManager.render(g);
    }

    public Tile getTile(int x, int y)
    {
        // If player is outside of map return a tile so the game won't crash
        if (x < 0 || y < 0 || x >= width || y >= height)
        {
            return Tile.background;
        }


        Tile t = Tile.tiles[tiles[x][y]];
        // return a default tile.
        if (t == null)
        {
            return Tile.background;
        }

        return t;
    }

    // set values for the tiles array
    private void loadWorld(String path)
    {
        String file = Utils.loadFileAssString(path);
        String[] tokens = file.split("\\s+");

        width = Utils.parseInt(tokens[0]);
        height = Utils.parseInt(tokens[1]);
        spawnx = Utils.parseInt(tokens[2]);
        spawny = Utils.parseInt(tokens[3]);

        tiles = new int[width][height];

        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
                // (x + y * width) will convert x & y in loop to one dimensional array.
                tiles[x][y] = Utils.parseInt(tokens[(x + y * width) + 4]);
            }
        }
    }

    //Getters and Setters
    public EntityManager getEntityManager() {
        return entityManager;
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public ItemManager getItemManager() {

        return itemManager;
    }

    public void setItemManager(ItemManager itemManager) {
        this.itemManager = itemManager;
    }
}
