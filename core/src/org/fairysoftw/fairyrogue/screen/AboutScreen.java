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
 * This scene is to introduce our production team.
 * The information about the production team will be displayed here for several seconds
 */
public class AboutScreen extends ScreenAdapter {

    // To facilitate interaction with other interfaces,
    // FairyRogue is passed in as a parameter when creating screen.
    private final FairyRogue fairyRogue;

    // stage
    private Stage stage;

    // texture
    private Texture texture;

    // image
    Image image;

    // Record whether it is in this interface to prevent false triggering
    private boolean activation = false;

    // exit button
    private Button exitButton;

    /**
     * Constructs a <code>DeathScreen</code> object.
     * There is no create () method in screen, and the show () method may be called many times.
     * Generally, it is better to do some initialization operations in the construction method
     *
     * @param fairyRogue the parameter passed in to facilitate interaction with other interfaces.
     */
    public AboutScreen(FairyRogue fairyRogue) {

        this.fairyRogue = fairyRogue;

        setBackground();
        setButton();

        // add image and exit button to the stage
        stage.addActor(image);
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

        float i = 1.0f;
        image.setColor(i, i, i, 1);
        Gdx.gl.glClearColor(i, i, i, 1);

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
        //Create a stage by the StretchViewport
        stage = new Stage(new StretchViewport(FairyRogue.VIRTUAL_WIDTH, FairyRogue.VIRTUAL_HEIGHT));

        texture = new Texture(Gdx.files.internal("screen/about.png"));

        image = new Image(new TextureRegion(texture));

        image.setPosition(stage.getWidth() / 2 - image.getWidth() / 2,
                stage.getHeight() / 2 - image.getHeight() / 2);

        image.setColor(1, 1, 1, 1);
    }

    /**
     * set the button of this screen
     */
    private void setButton() {
        //set the texture of up and down
        Texture exitUpTexture = new Texture(Gdx.files.internal("screen/button/Exit.png"));
        Texture exitDownTexture = new Texture(Gdx.files.internal("screen/button/Exit_n.png"));

        //set the style of this button
        Button.ButtonStyle exitStyle = new Button.ButtonStyle();

        exitStyle.up = new TextureRegionDrawable(new TextureRegion(exitUpTexture));
        exitStyle.down = new TextureRegionDrawable(new TextureRegion(exitDownTexture));
        exitButton = new Button(exitStyle);

        exitButton.setTransform(true);
        exitButton.setScale(0.3f);
        exitButton.setPosition(750, 0);

        //add listener to this button
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!activation)
                    return;

                System.out.println("exit clicked!");
                fairyRogue.showScreen(null, "startScreen");
            }
        });
    }
}