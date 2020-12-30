package org.fairysoftw.fairyrogue.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import org.fairysoftw.fairyrogue.FairyRogue;

/**
 * AboutScreen implement the Screen interface or inherit the ScreenAdapter class.
 * The ScreenAdapter class implements all the methods of the Screen interface.
 * This screen will be displayed when press "ESCAPE" during the game.
 */
public class PauseScreen extends ScreenAdapter {
    // To facilitate interaction with other interfaces,
    // FairyRogue is passed in as a parameter when creating screen.
    private final FairyRogue fairyRogue;

    // Record whether it is in this interface to prevent false triggering
    private boolean activation = false;

    // stage
    private final Stage stage;

    // texture
    private Texture texture;

    // image
    Image image;

    // continue Button
    private Button continueButton;

    // restart Button
    private Button restartButton;

    // setting Button
    private Button settingButton;

    // exit Button
    private Button exitButton;

    /**
     * Constructs a <code>PauseScreen</code> object.
     * There is no create () method in screen, and the show () method may be called many times.
     * Generally, it is better to do some initialization operations in the construction method
     *
     * @param fairyRogue the parameter passed in to facilitate interaction with other interfaces.
     */
    public PauseScreen(FairyRogue fairyRogue) {

        this.fairyRogue = fairyRogue;

        stage = new Stage(new StretchViewport(FairyRogue.VIRTUAL_WIDTH, FairyRogue.VIRTUAL_HEIGHT));

        setBackground();

        setButton();

        // add image to the stage
        stage.addActor(image);
        //add all buttons to the stage
        stage.addActor(continueButton);
        stage.addActor(restartButton);
        stage.addActor(settingButton);
        stage.addActor(exitButton);

    }

    @Override
    public void show() {
        activation = true;

        // Set the input processing to the stage
        // it must be set, otherwise it will not work if you click the button
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void hide() {
        activation = false;
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0.6f, 0.8f, 0.8f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();
    }

    @Override
    public void dispose() {
        if (texture != null) {
            texture.dispose();
        }
        if (stage != null) {
            stage.dispose();
        }
    }

    /**
     * set the background of this screen
     */
    private void setBackground() {

        texture = new Texture(Gdx.files.internal("screen/pause.png"));

        image = new Image(new TextureRegion(texture));

        image.setScale(0.3f);

        image.setPosition(stage.getWidth() / 3 - 15,
                stage.getHeight() - image.getHeight() / 2 + 100);

        image.setColor(1, 1, 1, 1);
    }

    /**
     * set the button of this screen
     */
    private void setButton() {
        //Create textures in both bounce and press states
        Texture continueUpTexture = new Texture(Gdx.files.internal("screen/button/Continue.png"));
        Texture continueDownTexture = new Texture(Gdx.files.internal("screen/button/Continue_n.png"));

        Texture restartUpTexture = new Texture(Gdx.files.internal("screen/button/NewGame.png"));
        Texture restartDownTexture = new Texture(Gdx.files.internal("screen/button/NewGame_n.png"));

        Texture settingUpTexture = new Texture(Gdx.files.internal("screen/button/Settings.png"));
        Texture settingDownTexture = new Texture(Gdx.files.internal("screen/button/Settings_n.png"));

        Texture exitUpTexture = new Texture(Gdx.files.internal("screen/button/ReturnToMenu.png"));
        Texture exitDownTexture = new Texture(Gdx.files.internal("screen/button/ReturnToMenu_n.png"));

        //set ButtonStyle
        Button.ButtonStyle continueStyle = new Button.ButtonStyle();
        Button.ButtonStyle restartStyle = new Button.ButtonStyle();
        Button.ButtonStyle settingStyle = new Button.ButtonStyle();
        Button.ButtonStyle exitStyle = new Button.ButtonStyle();

        //Set the texture area of the pop-up and press state of the style and create the button
        continueStyle.up = new TextureRegionDrawable(new TextureRegion(continueUpTexture));
        continueStyle.down = new TextureRegionDrawable(new TextureRegion(continueDownTexture));
        continueButton = new Button(continueStyle);

        restartStyle.up = new TextureRegionDrawable(new TextureRegion(restartUpTexture));
        restartStyle.down = new TextureRegionDrawable(new TextureRegion(restartDownTexture));
        restartButton = new Button(restartStyle);

        settingStyle.up = new TextureRegionDrawable(new TextureRegion(settingUpTexture));
        settingStyle.down = new TextureRegionDrawable(new TextureRegion(settingDownTexture));
        settingButton = new Button(settingStyle);

        exitStyle.up = new TextureRegionDrawable(new TextureRegion(exitUpTexture));
        exitStyle.down = new TextureRegionDrawable(new TextureRegion(exitDownTexture));
        exitButton = new Button(exitStyle);

        //Set the zoom ratio and position of the button
        continueButton.setTransform(true);
        continueButton.setScale(0.3f);
        continueButton.setPosition(stage.getWidth() / 2 - continueButton.getWidth() / 5,
                stage.getHeight() / 2 - continueButton.getHeight() / 2 + 100);

        restartButton.setTransform(true);
        restartButton.setScale(0.3f);
        restartButton.setPosition(stage.getWidth() / 2 - restartButton.getWidth() / 5,
                stage.getHeight() / 2 - restartButton.getHeight() / 2 - 70 + 100);

        settingButton.setTransform(true);
        settingButton.setScale(0.3f);
        settingButton.setPosition(stage.getWidth() / 2 - settingButton.getWidth() / 5,
                stage.getHeight() / 2 - settingButton.getHeight() / 2 - 140 + 100);

        exitButton.setTransform(true);
        exitButton.setScale(0.3f);
        exitButton.setPosition(stage.getWidth() / 2 - exitButton.getWidth() / 5,
                stage.getHeight() / 2 - exitButton.getHeight() / 2 - 210 + 100);

        //Add click listener to start button
        continueButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!activation)
                    return;

                System.out.println("start clicked!");
                fairyRogue.showScreen(null, "mainScreen_continue");
            }
        });

        //Add click listener to restart button
        restartButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!activation)
                    return;

                System.out.println("restart clicked!");
                //fairyRogue.restartGame();
                fairyRogue.showScreen(null, "mainScreen");
            }
        });

        //Add click listener to set button
        settingButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!activation)
                    return;

                System.out.println("setting clicked!");
                fairyRogue.showScreen(null, "settingScreenInGame");
            }
        });

        //Add click listener to return button
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!activation)
                    return;

                System.out.println("return to menu clicked!");
                fairyRogue.showScreen(null, "startScreen");
            }
        });
    }
}
