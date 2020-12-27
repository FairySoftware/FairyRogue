package org.fairysoftw.fairyrogue.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import org.fairysoftw.fairyrogue.actor.*;
import org.fairysoftw.fairyrogue.props.Equipment;
import org.fairysoftw.fairyrogue.props.Key;
import org.fairysoftw.fairyrogue.props.Potion;
import org.fairysoftw.fairyrogue.props.Props;

public class GameStage extends Stage {

    public GameStage(Viewport viewport, Batch batch) {
        super(viewport, batch);
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
        Array<CreatureActor> toLogActors = new Array<>();
        for (Actor actor : actors) {
            if (actor instanceof PlayerActor) {
                playerActor = (PlayerActor) actor;
                playerRectangle = new Rectangle(playerActor.getX(), playerActor.getY(), playerActor.getWidth(), playerActor.getHeight());
            }
        }
        for (Actor actor : actors) {
            if (actor instanceof WallActor) {
                rectangles.add(new Rectangle(actor.getX(), actor.getY(), actor.getWidth(), actor.getHeight()));
            }
            else if (actor instanceof NpcActor) {
                rectangles.add(new Rectangle(actor.getX(), actor.getY(), actor.getWidth(), actor.getHeight()));
                Vector2 distanceVector = new Vector2(actor.getX() - playerActor.getX(), actor.getY() - playerActor.getY());
                double distance = Math.sqrt(distanceVector.x * distanceVector.x + distanceVector.y * distanceVector.y);
                if (distance < 33 && Gdx.input.isKeyJustPressed(Input.Keys.F)) {
                    toLogActors.add((CreatureActor) actor);
                    playerActor.takeDialogue((NpcActor) actor);
                }
            }
            else if (actor instanceof MonsterActor) {
                if (!((CreatureActor) actor).isInBattle()) {
                    Rectangle monsterRectangle = new Rectangle(actor.getX(), actor.getY(), actor.getWidth(), actor.getHeight());
                    if (monsterRectangle.overlaps(playerRectangle)) {
                        playerActor.undoAct();
                        playerActor.takeBattle((CreatureActor) actor);
                    }
                }
                Vector2 distanceVector = new Vector2(actor.getX() - playerActor.getX(), actor.getY() - playerActor.getY());
                double distance = Math.sqrt(distanceVector.x * distanceVector.x + distanceVector.y * distanceVector.y);
                if (distance < 33) {
                    toLogActors.add((CreatureActor) actor);
                }
            }
            else if (actor instanceof DoorActor) {
                DoorActor doorActor = (DoorActor) actor;
                Rectangle doorRectangle = new Rectangle(actor.getX(), actor.getY(), actor.getWidth(), actor.getHeight());
                if (doorActor.isLocked() || doorActor.isClosed()) {
                    rectangles.add(doorRectangle);
                }
                Vector2 distanceVector = new Vector2(actor.getX() - playerActor.getX(), actor.getY() - playerActor.getY());
                double distance = Math.sqrt(distanceVector.x * distanceVector.x + distanceVector.y * distanceVector.y);
                if (distance <= 33 && Gdx.input.isKeyJustPressed(Input.Keys.F) && !playerRectangle.overlaps(doorRectangle)) {
                    if (doorActor.isLocked()) {
                        for (Props props : playerActor.backpack) {
                            if (props.propsType == PropsActor.PropsType.KEY) {
                                if (doorActor.unlock(((Key) props).getId())) {
                                    playerActor.backpack.removeValue(props, false);
                                }
                            }
                        }
                    }
                    else if (doorActor.isClosed()) {
                        doorActor.open();
                    }
                    else {
                        doorActor.close();
                    }
                }
            }
            else if (actor instanceof PropsActor) {
                Rectangle propsRectangle = new Rectangle(actor.getX(), actor.getY(), actor.getWidth(), actor.getHeight());
                if (propsRectangle.overlaps(playerRectangle) && Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                    Props props;
                    MapProperties properties = ((PropsActor) actor).getProperties();
                    switch (((PropsActor) actor).getPropsType()) {
                        case POTION:
                            props = new Potion(((PropsActor) actor).getMapObject());
                            break;
                        case EQUIPMENT:
                            props = new Equipment(((PropsActor) actor).getMapObject());
                            break;
                        case KEY:
                            props = new Key(properties);
                            break;
                        default:
                            throw new IllegalStateException("Unexpected value: " + ((PropsActor) actor).getPropsType());
                    }
                    props.name = ((PropsActor) actor).getPropsName();
                    props.icon = new Sprite(((PropsActor) actor).getTexture());
                    props.propsType = ((PropsActor) actor).getPropsType();
                    playerActor.pickUp(props);
                    actor.remove();
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

        String str = "";
        for (CreatureActor actor : toLogActors) {
            str += "HP: " + actor.getHealthPoint() + "\n" +
                    "MP: " + actor.getMagicPoint() + "\n" +
                    "AD: " + actor.getAttackDamage() + "\n" +
                    "AP: " + actor.getAbilityPower() + "\n" +
                    "AS: " + actor.getAttackSpeed() + "\n" +
                    "PD: " + actor.getPhysicalDefence() + "\n" +
                    "MD: " + actor.getMagicalDefence() + "\n\n";
        }
        this.getBatch().begin();
        BitmapFont font = new BitmapFont();
        font.setColor(Color.RED);
        font.draw(this.getBatch(), str, this.getCamera().position.x + 370, this.getCamera().position.y + 370);
        this.getBatch().end();
    }
}
