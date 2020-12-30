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
 * StartScreen implement the Screen interface or inherit the ScreenAdapter class.
 * The ScreenAdapter class implements all the methods of the Screen interface.
 * This screen is a very import screen class besides the game screen(MainScreen)
 * it is associated with other screes.
 */
public class StartScreen extends ScreenAdapter {
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

    // start Button
    private Button startButton;

    // settings Button
    private Button settingsButton;

    // about Button
    private Button aboutButton;

    // exit Button
    private Button exitButton;

    /**
     * Constructs a <code>StartScreen</code> object.
     * There is no create () method in screen, and the show () method may be called many times.
     * Generally, it is better to do some initialization operations in the construction method
     *
     * @param fairyRogue the parameter passed in to facilitate interaction with other interfaces.
     */
    public StartScreen(FairyRogue fairyRogue) {

        this.fairyRogue = fairyRogue;

        stage = new Stage(new StretchViewport(FairyRogue.VIRTUAL_WIDTH, FairyRogue.VIRTUAL_HEIGHT));

        setBackground();

        setButton();

        // add image to stage
        stage.addActor(image);
        //add button to stage
        stage.addActor(startButton);
        stage.addActor(settingsButton);
        stage.addActor(aboutButton);
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

        Gdx.gl.glClearColor(1, 1, 1, 1);
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
        texture = new Texture(Gdx.files.internal("screen/background.jpg"));

        image = new Image(new TextureRegion(texture));

        image.setPosition(stage.getWidth() / 2 - image.getWidth() / 2,
                stage.getHeight() / 2 - image.getHeight() / 2);

        image.setColor(1, 1, 1, 1);
    }

    /**
     * set the button of this screen
     */
    private void setButton() {
        // Create textures in both bounce and press states
        Texture startUpTexture = new Texture(Gdx.files.internal("screen/button/Start.png"));
        Texture startDownTexture = new Texture(Gdx.files.internal("screen/button/Start_n.png"));

        Texture settingsUpTexture = new Texture(Gdx.files.internal("screen/button/Settings.png"));
        Texture settingsDownTexture = new Texture(Gdx.files.internal("screen/button/Settings_n.png"));

        Texture aboutUpTexture = new Texture(Gdx.files.internal("screen/button/About.png"));
        Texture aboutDownTexture = new Texture(Gdx.files.internal("screen/button/About_n.png"));

        Texture exitUpTexture = new Texture(Gdx.files.internal("screen/button/Exit.png"));
        Texture exitDownTexture = new Texture(Gdx.files.internal("screen/button/Exit_n.png"));

        //create ButtonStyle
        Button.ButtonStyle startStyle = new Button.ButtonStyle();
        Button.ButtonStyle settingsStyle = new Button.ButtonStyle();
        Button.ButtonStyle aboutStyle = new Button.ButtonStyle();
        Button.ButtonStyle exitStyle = new Button.ButtonStyle();

        //Set the texture area of the pop-up and press state of the style and create the button
        startStyle.up = new TextureRegionDrawable(new TextureRegion(startUpTexture));
        startStyle.down = new TextureRegionDrawable(new TextureRegion(startDownTexture));
        startButton = new Button(startStyle);

        settingsStyle.up = new TextureRegionDrawable(new TextureRegion(settingsUpTexture));
        settingsStyle.down = new TextureRegionDrawable(new TextureRegion(settingsDownTexture));
        settingsButton = new Button(settingsStyle);

        aboutStyle.up = new TextureRegionDrawable(new TextureRegion(aboutUpTexture));
        aboutStyle.down = new TextureRegionDrawable(new TextureRegion(aboutDownTexture));
        aboutButton = new Button(aboutStyle);

        exitStyle.up = new TextureRegionDrawable(new TextureRegion(exitUpTexture));
        exitStyle.down = new TextureRegionDrawable(new TextureRegion(exitDownTexture));
        exitButton = new Button(exitStyle);

        //Set the zoom ratio and position of the button
        startButton.setTransform(true);
        startButton.setScale(0.3f);
        startButton.setPosition(stage.getWidth() / 2 - startButton.getWidth() / 5,
                stage.getHeight() / 2 - startButton.getHeight() / 2);

        settingsButton.setTransform(true);
        settingsButton.setScale(0.3f);
        settingsButton.setPosition(stage.getWidth() / 2 - settingsButton.getWidth() / 5,
                stage.getHeight() / 2 - settingsButton.getHeight() / 2 - 70);

        aboutButton.setTransform(true);
        aboutButton.setScale(0.3f);
        aboutButton.setPosition(stage.getWidth() / 2 - aboutButton.getWidth() / 5,
                stage.getHeight() / 2 - aboutButton.getHeight() / 2 - 140);

        exitButton.setTransform(true);
        exitButton.setScale(0.3f);
        exitButton.setPosition(stage.getWidth() / 2 - exitButton.getWidth() / 5,
                stage.getHeight() / 2 - exitButton.getHeight() / 2 - 210);

        //Add click listener to start button
        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!activation)
                    return;

                System.out.println("start clicked!");
                fairyRogue.showScreen(null, "mainScreen");
            }
        });

        //Add click listener to settings button
        settingsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!activation)
                    return;

                System.out.println("settings clicked!");
                fairyRogue.showScreen(null, "settingScreen");
            }
        });

        //Add click listener to about button
        aboutButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!activation)
                    return;

                System.out.println("about clicked!");
                fairyRogue.showScreen(null, "aboutScreen");
            }
        });

        //Add click listener to exit button
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!activation)
                    return;

                System.out.println("exit clicked!");
                fairyRogue.showScreen(null, "exit");
            }
        });
    }
}