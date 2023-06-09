package entities.creatures;
import game.Handler;
import graphics.Animation;
import graphics.Assets;
import java.awt.*;
import java.awt.image.BufferedImage;

public class FlyingRedKoopaTroopa extends Creature {

    private Animation animFlyingRedKoopaRight, animFlyingRedKoopaLeft;
    private boolean spawned = false;

    public FlyingRedKoopaTroopa(Handler handler, float x, float y, int health)
    {
        super(handler, x, y, Creature.DEFAULT_16x16_WIDTH, Creature.DEFAULT_16x16_HEIGHT, health);

        animFlyingRedKoopaRight = new Animation(200, Assets.flyingRedKoopaTroopaRight);
        animFlyingRedKoopaLeft = new Animation(200, Assets.flyingRedKoopaTroopaLeft);

        bounds.x = 40;
        bounds.y = 80;
        bounds.width = 48;
        bounds.height = 48;
    }

    @Override
    public void tick()
    {
        // If mario is at middle of screen tick enemy
        if (x - handler.getWorld().getEntityManager().getMario().getX() < handler.getWidth() / 2 || spawned)
        {
            //Animations
            animFlyingRedKoopaRight.tick();
            animFlyingRedKoopaLeft.tick();
            //Movements
            getInput();
            move();

            spawned = true;
        }

    }

    @Override
    public void render(Graphics g)
    {
        if (spawned)
        {
            //g.setColor(Color.RED);
            //g.fillRect((int) (x + bounds.x - handler.getGameCamera().getxOffset()), (int) (y + bounds.y), bounds.width, bounds.height);
            g.drawImage(getCurrentAnimationFrame(), (int) (x - handler.getGameCamera().getxOffset()), (int) (y), DEFAULT_32x32_WIDTH, DEFAULT_32x32_HEIGHT, null);
        }
    }

    @Override
    public void die() {

    }

    private BufferedImage getCurrentAnimationFrame()
    {
        if (checkEntityCollisions(0f, yMove))
        {
            return animFlyingRedKoopaRight.getCurrentFrame();
        }
        else
        {
            return animFlyingRedKoopaLeft.getCurrentFrame();
        }

    }

    //private float temp = 0;
    //Double increment = 3.14 / (60 * 4);
    private void getInput()
    {
        xMove = 0;
        enemyGravity();

        xMove = -speed + 2;

        //temp -= increment;
        //xMove = (float) Math.sin(temp) * 4;
        //yMove = (float) Math.cos(temp) * 5;
    }
}