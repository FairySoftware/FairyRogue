package org.fairysoftw.fairyrogue.stage;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import org.fairysoftw.fairyrogue.actor.*;

public class GameStage extends Stage {

    public GameStage (Viewport viewport, Batch batch)
    {
        super(viewport, batch);
    }

    public GameStage()
    {
        super();
    }

    @Override
    public void act() {
        super.act();
        PlayerActor playerActor = null;
        Rectangle playerRectangle = null;
        Array<Rectangle> rectangles = new Array<>();
        Array<Actor> actors= this.getActors();
        for(Actor actor:actors)
        {
            if(actor instanceof PlayerActor)
            {
                playerActor = (PlayerActor) actor;
                playerRectangle = new Rectangle(playerActor.getX(), playerActor.getY(), playerActor.getWidth(), playerActor.getHeight());
            }
            else if(actor instanceof WallActor || actor instanceof MonsterActor || actor instanceof NpcActor)
            {
                rectangles.add(new Rectangle(actor.getX(), actor.getY(), actor.getWidth(), actor.getHeight()));
            }
            else if(actor instanceof DoorActor)
            {

            }
        }
        if(playerRectangle != null && rectangles.size > 0)
        {
            for(Rectangle rectangle: rectangles)
            {
                if (playerRectangle.overlaps(rectangle))
                {
                    playerActor.undoAct();
                }
            }
        }
    }
}
