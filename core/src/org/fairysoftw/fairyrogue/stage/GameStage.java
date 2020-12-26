package org.fairysoftw.fairyrogue.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
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
        Rectangle playerRectangle = null;
        Array<Rectangle> rectangles = new Array<>();
        Array<Actor> actors = this.getActors();
        for (Actor actor : actors) {
            if (actor instanceof PlayerActor) {
                playerActor = (PlayerActor) actor;
                playerRectangle = new Rectangle(playerActor.getX(), playerActor.getY(), playerActor.getWidth(), playerActor.getHeight());
            }
            else if (actor instanceof WallActor || actor instanceof MonsterActor || actor instanceof NpcActor) {
                rectangles.add(new Rectangle(actor.getX(), actor.getY(), actor.getWidth(), actor.getHeight()));
            }
            else if (actor instanceof DoorActor) {
                //TODO: add locked door action
                if (((DoorActor) actor).isLocked()) {
                    rectangles.add(new Rectangle(actor.getX(), actor.getY(), actor.getWidth(), actor.getHeight()));
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
                    props.type = ((PropsActor) actor).getPropsType();
                    playerActor.pickUp(props);
                    actor.remove();
                }
            }
        }

        if (playerRectangle != null && rectangles.size > 0) {
            for (Rectangle rectangle : rectangles) {
                if (playerRectangle.overlaps(rectangle)) {
                    playerActor.undoAct();
                }
            }
        }
    }
}
