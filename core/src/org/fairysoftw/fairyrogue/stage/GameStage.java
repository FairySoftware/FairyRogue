package org.fairysoftw.fairyrogue.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import org.fairysoftw.fairyrogue.Assets;
import org.fairysoftw.fairyrogue.actor.*;
import org.fairysoftw.fairyrogue.props.Equipment;
import org.fairysoftw.fairyrogue.props.Key;
import org.fairysoftw.fairyrogue.props.Potion;
import org.fairysoftw.fairyrogue.props.Props;

public class GameStage extends Stage {

    public GameStage(Viewport viewport, Batch batch) {
        super(viewport, batch);
        for (MapObject object : Assets.map.getLayers().get("Objects").getObjects()) {
            TiledMapTileMapObject mapObject = (TiledMapTileMapObject) object;
            Actor actor = null;
            if (object.getName().contains("wall")) {
                actor = new WallActor(mapObject.getTextureRegion());
            }
            else if (object.getName().contains("door")) {
                actor = new DoorActor(mapObject);
            }
            else if (object.getName().contains("player")) {

                actor = new PlayerActor(mapObject);
                actor.setName("player");
            }
            else if (object.getName().contains("monster")) {
                actor = new MonsterActor(mapObject);
            }
            else if (object.getName().contains("npc")) {
                actor = new NpcActor(mapObject.getTextureRegion());
            }
            else if (object.getName().contains("props")) {
                actor = new PropsActor(mapObject);
            }
            if (actor != null) {
                actor.setX(mapObject.getX());
                actor.setY(mapObject.getY());
                this.addActor(actor);
            }
        }
    }

    public GameStage() {
        super();
    }

    @Override
    public void act() {
        super.act();
        PlayerActor playerActor = null;
        Rectangle playerRectangle = new Rectangle();
        Array<Rectangle> rectangles = new Array<>();
        Array<Actor> actors = this.getActors();
        for (Actor actor : actors) {
            if (actor instanceof PlayerActor) {
                playerActor = (PlayerActor) actor;
                playerRectangle = new Rectangle(playerActor.getX(), playerActor.getY(), playerActor.getWidth(), playerActor.getHeight());
            }
            else if (actor instanceof WallActor || actor instanceof NpcActor) {
                rectangles.add(new Rectangle(actor.getX(), actor.getY(), actor.getWidth(), actor.getHeight()));
            }
            else if(actor instanceof MonsterActor) {
                Rectangle monsterRectangle = new Rectangle(actor.getX(), actor.getY(), actor.getWidth(), actor.getHeight());
                if(monsterRectangle.overlaps(playerRectangle))
                {
                    playerActor.undoAct();
                    playerActor.takeBattle((CreatureActor) actor);
                }
            }
            else if (actor instanceof DoorActor) {
                DoorActor doorActor = (DoorActor) actor;
                Rectangle doorRectangle = new Rectangle(actor.getX(), actor.getY(), actor.getWidth(), actor.getHeight());
                if (doorActor.isLocked() || doorActor.isClosed()) {
                    rectangles.add(doorRectangle);
                }
                Vector2 distanceVector = new Vector2(doorRectangle.x - playerRectangle.x, doorRectangle.y - playerRectangle.y);
                double distance = Math.sqrt(distanceVector.x * distanceVector.x + distanceVector.y * distanceVector.y);
                if (distance <= 33 && Gdx.input.isKeyJustPressed(Input.Keys.F))
                {
                    if(doorActor.isLocked())
                    {
                        doorActor.unLock();
                    }
                    else if(doorActor.isClosed())
                    {
                        doorActor.open();
                    }
                    else
                    {
                        doorActor.close();
                    }
                }
            }
            else if (actor instanceof PropsActor) {
                Rectangle propsRectangle = new Rectangle(actor.getX(), actor.getY(), actor.getWidth(), actor.getHeight());
                if (propsRectangle.overlaps(playerRectangle) && Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                    Props props;
                    switch (((PropsActor) actor).getPropsType()) {
                        case POTION:
                            props = new Potion();
                            break;
                        case EQUIPMENT:
                            props = new Equipment(((PropsActor) actor).getProperties());
                            break;
                        case KEY:
                            props = new Key();
                            break;
                        default:
                            throw new IllegalStateException("Unexpected value: " + ((PropsActor) actor).getPropsType());
                    }
                    props.name = ((PropsActor) actor).getPropsName();
                    props.icon = ((PropsActor) actor).getTexture();
                    props.propsType = ((PropsActor) actor).getPropsType();
                    playerActor.pickUp(props);
                    actor.remove();
                    Gdx.app.debug("player", "picked up " + props.name);
                }
            }
        }

        if (rectangles.size > 0) {
            for (Rectangle rectangle : rectangles) {
                if (playerRectangle.overlaps(rectangle)) {
                    playerActor.undoAct();
                }
            }
        }
    }

    private void move()
    {

    }

    public Actor getActor(String name){
        for(Actor a:this.getActors()){
            if(name.equals(a.getName())){
                return a;
            }
        }
        return null;
    }
}
