package tiles;

import java.awt.image.BufferedImage;

public class BrickTile extends Tile
{
    public BrickTile(BufferedImage textures, int id)
    {
        super(textures, id);
    }

    public boolean isSolid()
    {
        return true;
    }

}
