package org.fairysoftw.fairyrogue.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import org.fairysoftw.fairyrogue.FairyRogue;

/**
 * EnterScreen implement the Screen interface or inherit the ScreenAdapter class.
 * The ScreenAdapter class implements all the methods of the Screen interface.
 * This scene will just show our logo for a few seconds.
 */
public class EnterScreen extends ScreenAdapter {
    // To facilitate interaction with other interfaces,
    // FairyRogue is passed in as a parameter when creating screen.
    private final FairyRogue fairyRogue;

    // stage
    private final Stage stage;

    // texture
    private final Texture texture;

    // image
    Image image;

    //Total rendering time of the current scene
    private float deltaSum;

    private float i = 0.0f;

    /**
     * Constructs a <code>EnterScreen</code> object.
     * There is no create () method in screen, and the show () method may be called many times.
     * Generally, it is better to do some initialization operations in the construction method
     *
     * @param fairyRogue the parameter passed in to facilitate interaction with other interfaces.
     */
    public EnterScreen(FairyRogue fairyRogue) {

        this.fairyRogue = fairyRogue;

        stage = new Stage(new StretchViewport(FairyRogue.VIRTUAL_WIDTH, FairyRogue.VIRTUAL_HEIGHT));

        texture = new Texture(Gdx.files.internal("screen/logo.jpg"));

        image = new Image(new TextureRegion(texture));

        image.setPosition(stage.getWidth() / 2 - image.getWidth() / 2,
                stage.getHeight() / 2 - image.getHeight() / 2);

        image.setColor(1, 1, 1, 1);

        stage.addActor(image);
    }

    @Override
    public void show() {
        deltaSum = 0;
    }

    @Override
    public void render(float delta) {
        //Cumulative rendering time
        deltaSum += delta;

        if (deltaSum >= 5.0F || Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY)) {
            // the scene display for more than 5 seconds or press any key will inform FairyRogue to switch the scene
            if (fairyRogue != null) {
                fairyRogue.showScreen(this, "startScreen");
                return;
            }
        }

        //From dark to light, lasting for a few seconds, then from light to dark
        if (deltaSum < 1.75)
            i = i + 0.0002f;
        else if (deltaSum > 3.25)
            i = i - 0.0002f;

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
}