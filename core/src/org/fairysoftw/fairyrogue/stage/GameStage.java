package org.fairysoftw.fairyrogue.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import org.fairysoftw.fairyrogue.Assets;
import org.fairysoftw.fairyrogue.FairyRogue;
import org.fairysoftw.fairyrogue.actor.*;
import org.fairysoftw.fairyrogue.props.Equipment;
import org.fairysoftw.fairyrogue.props.Key;
import org.fairysoftw.fairyrogue.props.Potion;
import org.fairysoftw.fairyrogue.props.Props;
import org.fairysoftw.fairyrogue.screen.MainScreen;
import org.json.JSONException;
import org.json.JSONObject;


/**{@inheritDoc}
 * the stage that display the main game stage
 */
public class GameStage extends Stage {
    // main camera location x
    public static int MAIN_LOCATION_X=300;
    // main camera location y
    public static int MAIN_LOCATION_Y=0;
    // the tilemap current using
    private TiledMap currentMap;
    // the main map renderer
    private OrthogonalTiledMapRenderer mapRenderer;
    // the minimap stage
    private MiniMapStage miniMapStage;
    // the minimap camera
    private OrthographicCamera mainCamera;
    // the main viewport
    private Viewport mainViewport;
    // current player
    private PlayerActor playerActor;
    // player birth point
    private Rectangle birthPoint;
    // stage clear point
    private Rectangle clearPoint;
    // stage display attribute
    private AttributeStage attributeStage;

    /**
     * game stage constructor
     * @param viewport main viewport
     * @param batch draw batch
     */
    public GameStage(Viewport viewport, Batch batch) {
        super(viewport, batch);
    }

    /**
     * game stage constructor
     * @param map the map current playing
     * @param lastStageActor last stage player
     */
    public GameStage(TiledMap map, PlayerActor lastStageActor) {
        super();
        currentMap = map;
        mainCamera = new OrthographicCamera();
        mainViewport = new ExtendViewport(FairyRogue.VIRTUAL_WIDTH, FairyRogue.VIRTUAL_HEIGHT, mainCamera);
        mapRenderer = new OrthogonalTiledMapRenderer(map, this.getBatch());
        miniMapStage = new MiniMapStage(mapRenderer, this.getBatch());
        this.setViewport(mainViewport);
        birthPoint = ((RectangleMapObject)map.getLayers().get("Points")
                .getObjects().get("birth_point")).getRectangle();
        clearPoint = ((RectangleMapObject)map.getLayers().get("Points")
                .getObjects().get("clear_point")).getRectangle();
        attributeStage = new AttributeStage(this.getBatch());

        // different door textures
        TextureRegion lockedIronDoor = null;
        TextureRegion closedIronDoor = null;
        TextureRegion openedIronDoor = null;
        TextureRegion closedWoodDoor = null;
        TextureRegion openedWoodDoor = null;

        MapObjects mapObjects = map.getLayers().get("Objects_to_use").getObjects();
        // for loop that loads door textures
        for (MapObject mapObject : mapObjects) {
            TiledMapTileMapObject tileMapObject = (TiledMapTileMapObject) mapObject;
            if (tileMapObject.getName().contains("door")) {
                MapProperties mapProperties = tileMapObject.getProperties();
                TextureRegion textureRegion = tileMapObject.getTextureRegion();
                if (mapProperties.get("material").equals("iron")) {
                    switch ((String) mapProperties.get("status")) {
                        case "locked":
                            lockedIronDoor = textureRegion;
                            break;
                        case "closed":
                            closedIronDoor = textureRegion;
                            break;
                        case "opened":
                            openedIronDoor = textureRegion;
                            break;
                    }
                }
                if (mapProperties.get("material").equals("wood")) {
                    switch ((String) mapProperties.get("status")) {
                        case "closed":
                            closedWoodDoor = textureRegion;
                            break;
                        case "opened":
                            openedWoodDoor = textureRegion;
                            break;
                    }
                }
            }
        }

        //for loop that loads game objects
        for (MapObject object : map.getLayers().get("Objects").getObjects()) {
            TiledMapTileMapObject mapObject = (TiledMapTileMapObject) object;
            Actor actor = null;
            if (object.getName().contains("wall")) {
                actor = new WallActor(mapObject.getTextureRegion());
            }
            else if (object.getName().contains("door")) {
                DoorActor doorActor = new DoorActor(mapObject);
                switch (doorActor.getMaterial()) {
                    case "wood":
                        doorActor.closedDoor = closedWoodDoor;
                        doorActor.openedDoor = openedWoodDoor;
                        break;
                    case "iron":
                        doorActor.lockedDoor = lockedIronDoor;
                        doorActor.closedDoor = closedIronDoor;
                        doorActor.openedDoor = openedIronDoor;
                        doorActor.id = (String) object.getProperties().get("id");
                        break;
                }
                doorActor.refresh();
                actor = doorActor;
            }
            else if (object.getName().contains("player")) {
                if(lastStageActor == null) {
                    this.playerActor = new PlayerActor(mapObject);
                    actor = this.playerActor;
                }
                else {
                    this.playerActor = lastStageActor;
                    this.addActor(playerActor);
                }
                this.playerActor.setX(birthPoint.x);
                this.playerActor.setY(birthPoint.y);
            }
            else if (object.getName().contains("monster")) {
                actor = new MonsterActor(mapObject);
            }
            else if (object.getName().contains("npc")) {
                String id = object.getProperties().get("id").toString();
                actor = new NpcActor(mapObject, map, loadNpcDialogues(id));
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

    /**
     *
     * @param id npc id use to load
     * @return dialogue json object
     */
    private JSONObject loadNpcDialogues(String id) {
        FileHandle handle;
        // string that holds json file data
        String content;
        try {
            handle = Gdx.files.local("dialogue/" + (String) currentMap.getProperties().get("npc_dialogue"));
             content = handle.readString();
        } catch (Exception e) {
            // if open file filed, return null
            return null;
        }
        JSONObject gameJson = new JSONObject(content);
        try{
            // get json object from json file
            return gameJson.getJSONObject(id).getJSONObject("dialogue");
        }
        catch (JSONException E){
            // if serialized json file filed, return null
            return null;
        }
    }

    /**
     * judge if stage is clear
     * @return if this stage is clear
     */
    public boolean isClear(){
        // judge if player arrived clear point
        Rectangle rectangle = new Rectangle(playerActor.getX(), playerActor.getY(),
                playerActor.getWidth(), playerActor.getHeight());
        return rectangle.overlaps(clearPoint);
    }

    /**
     * judge if player is died
     * @return if game over
     */
    public boolean isGameOver() {
        // judge if player is died
        return playerActor.isDead();
    }

    /** {@inheritDoc}
     * draw method called every tick
     */
    @Override
    public void draw() {
        updateCamera();
        // update viewport position & size
        mainViewport.setScreenPosition(MAIN_LOCATION_X,MAIN_LOCATION_Y);
        mainViewport.apply();
        // render main map
        mapRenderer.setView(mainCamera);
        mapRenderer.render();
        // draw main game stage
        super.draw();
        // draw mini map stage
        miniMapStage.draw();
        // draw attribute stage
        attributeStage.draw();
    }

    /**
     * resize method
     * @param width window width
     * @param height window height
     */
    public void resize(int width, int height) {
        mainViewport.update(width, height);
    }

    /** {@inheritDoc}
     * act function called every tick
     */
    @Override
    public void act() {
        super.act();
        PlayerActor playerActor = null;
        Rectangle playerRectangle = new Rectangle();
        Array<Rectangle> rectangles = new Array<>();
        Array<Actor> actors = this.getActors();
        Array<CreatureActor> toLogMonsters = new Array<>();
        // create actor's collision box
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
                // if player is near npc and press f key, take dialogue
                if (distance < 33 && Gdx.input.isKeyJustPressed(Input.Keys.F)) {
                    toLogMonsters.add((CreatureActor) actor);
                    playerActor.takeDialogue((NpcActor) actor, ((NpcActor)actor).getDialogue());
                }
            }
            else if (actor instanceof MonsterActor) {
                if (!((CreatureActor) actor).isInBattle()) {
                    Rectangle monsterRectangle = new Rectangle(actor.getX(), actor.getY(), actor.getWidth(), actor.getHeight());
                    // if player walk to monster, take battle
                    if (monsterRectangle.overlaps(playerRectangle)) {
                        playerActor.undoAct();
                        playerActor.takeBattle((CreatureActor) actor);
                    }
                }
                Vector2 distanceVector = new Vector2(actor.getX() - playerActor.getX(), actor.getY() - playerActor.getY());
                double distance = Math.sqrt(distanceVector.x * distanceVector.x + distanceVector.y * distanceVector.y);
                if (distance < 33) {
                    toLogMonsters.add((CreatureActor) actor);
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
                // if player near door and f key pressed, open/unlock/close door
                if (distance <= 33 && Gdx.input.isKeyJustPressed(Input.Keys.F) && !playerRectangle.overlaps(doorRectangle)) {
                    if (doorActor.isLocked()) {
                        // if door is closed, get key from backpack
                        for (Props props : playerActor.backpack) {
                            if (props.propsType == PropsActor.PropsType.KEY) {
                                if (doorActor.unlock(((Key) props).getId())) {
                                    if(Assets.isOpenSound())
                                        Assets.unlockSound.play();

                                    playerActor.backpack.removeValue(props, false);
                                }
                            }
                        }
                    }
                    // if door is closed, open door
                    else if (doorActor.isClosed()) {
                        if(Assets.isOpenSound())
                            Assets.doorSound.play();

                        doorActor.open();
                    }
                    // if door is opened, close door
                    else {
                        if(Assets.isOpenSound())
                            Assets.doorSound.play();

                        doorActor.close();
                    }
                }
            }
            // if actor standing in a props and space key pressed, pick up that props
            // if that props is a equipment, equip it
            // if that props is a potion, use it
            // if that props is a key, put it into backpack
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

        // if player walk into a wall, undo walk
        if (rectangles.size > 0) {
            for (Rectangle rectangle : rectangles) {
                if (playerRectangle.overlaps(rectangle)) {
                    playerActor.undoAct();
                }
            }
        }
        setAttribute(toLogMonsters);
    }

    /**
     * update camera method called every tick
     */
    private void updateCamera() {
        // put player in center
        if (!playerActor.isInBattle()) {
            mainCamera.position.x = playerActor.getX()+MAIN_LOCATION_X/2;
            mainCamera.position.y = playerActor.getY()+MAIN_LOCATION_Y/2;
        }

        mainCamera.update();
    }

    /** {@inheritDoc}
     * add actor to main stage & minimap stage
     * @param actor
     */
    @Override
    public void addActor(Actor actor) {
        super.addActor(actor);
        // if actor can be draw, put it into minimap stage
        if (actor instanceof SpriteActor) {
            ((SpriteActor) actor).miniMapStage = this.miniMapStage;
            if (actor instanceof PlayerActor) {
                this.playerActor = (PlayerActor) actor;
            }
        }
        // if actor is a widget, don't put it into minimap
        if(!(actor instanceof WidgetGroup))
        {
            miniMapStage.addActor(actor);
        }
    }

    /**
     * get player actor of current stage
     * @return player actor of current stage
     */
    public PlayerActor getPlayerActor() {
        return playerActor;
    }

    /**
     * get tilemap of current stage
     * @return tilemap of current stage
     */
    public TiledMap getCurrentMap() {
        return currentMap;
    }

    /** {@inheritDoc}
     * dispose method
     */
    @Override
    public void dispose() {
        super.dispose();
    }

    /**
     * display monsters attribute
     * @param toLogMonsters monsters to log attribute
     */
    public void displayAttribute(Array<CreatureActor> toLogMonsters) {
        updateCamera();
        mainViewport.apply();
        mapRenderer.setView(mainCamera);
        BitmapFont font = new BitmapFont();
        String playerAttribute = "";
        playerAttribute += "HP: " + playerActor.getHealthPoint() + "\n" +
                "MP: " + playerActor.getMagicPoint() + "\n" +
                "AD: " + playerActor.getAttackDamage() + "\n" +
                "AP: " + playerActor.getAbilityPower() + "\n" +
                "AS: " + playerActor.getAttackSpeed() + "\n" +
                "PD: " + playerActor.getPhysicalDefence() + "\n" +
                "MD: " + playerActor.getMagicalDefence() + "\n\n";

        this.getBatch().begin();
        font.draw(this.getBatch(), playerAttribute,
                this.getCamera().position.x - FairyRogue.VIRTUAL_WIDTH/2f + 20,
                this.getCamera().position.y+FairyRogue.VIRTUAL_HEIGHT/2f - 20);
        this.getBatch().end();

        String monstersAttribute = "";
        for (CreatureActor actor : toLogMonsters) {
            monstersAttribute += "HP: " + actor.getHealthPoint() + "\n" +
                    "MP: " + actor.getMagicPoint() + "\n" +
                    "AD: " + actor.getAttackDamage() + "\n" +
                    "AP: " + actor.getAbilityPower() + "\n" +
                    "AS: " + actor.getAttackSpeed() + "\n" +
                    "PD: " + actor.getPhysicalDefence() + "\n" +
                    "MD: " + actor.getMagicalDefence() + "\n\n";
        }
        this.getBatch().begin();
        font.setColor(Color.RED);
        font.draw(this.getBatch(), monstersAttribute,
                this.getCamera().position.x + FairyRogue.VIRTUAL_WIDTH/2f - 70,
                this.getCamera().position.y+FairyRogue.VIRTUAL_HEIGHT/2f - 20);
        this.getBatch().end();
    }

    /**
     * put actor array into attribute stage
     * @param toLogMonsters actor to log
     */
    public void setAttribute(Array<CreatureActor> toLogMonsters){
        String playerAttribute = "";
        playerAttribute += "HP: " + playerActor.getHealthPoint() + "\n" +
                "MP: " + playerActor.getMagicPoint() + "\n" +
                "AD: " + playerActor.getAttackDamage() + "\n" +
                "AP: " + playerActor.getAbilityPower() + "\n" +
                "AS: " + playerActor.getAttackSpeed() + "\n" +
                "PD: " + playerActor.getPhysicalDefence() + "\n" +
                "MD: " + playerActor.getMagicalDefence() + "\n\n";

        String monstersAttribute = "";
        for (CreatureActor actor : toLogMonsters) {
            monstersAttribute += "HP: " + actor.getHealthPoint() + "\n" +
                    "MP: " + actor.getMagicPoint() + "\n" +
                    "AD: " + actor.getAttackDamage() + "\n" +
                    "AP: " + actor.getAbilityPower() + "\n" +
                    "AS: " + actor.getAttackSpeed() + "\n" +
                    "PD: " + actor.getPhysicalDefence() + "\n" +
                    "MD: " + actor.getMagicalDefence() + "\n\n";
        }
        attributeStage.setAttributeToStr(playerAttribute, monstersAttribute);
    }
}
