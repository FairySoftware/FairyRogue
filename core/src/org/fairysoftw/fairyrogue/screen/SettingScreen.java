package org.fairysoftw.fairyrogue.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import org.fairysoftw.fairyrogue.Assets;
import org.fairysoftw.fairyrogue.FairyRogue;

/**
 * SettingScreen implement the Screen interface or inherit the ScreenAdapter class.
 * The ScreenAdapter class implements all the methods of the Screen interface.
 * This screen is used to change some settings in this game.
 */
public class SettingScreen extends ScreenAdapter {
    // To facilitate interaction with other interfaces,
    // FairyRogue is passed in as a parameter when creating screen.
    protected final FairyRogue fairyRogue;

    // Record whether it is in this interface to prevent false triggering
    protected boolean activation = false;

    // stage
    private final Stage stage;

    // texture
    private Texture texture;

    // image
    Image image;
    Image musicImage;
    Image soundImage;

    // checkbox On Texture
    private Texture checkboxOnTexture;

    // checkbox Off Texture
    private Texture checkboxOffTexture;

    // bitmap Font
    private BitmapFont bitmapFont;

    // checkBox
    private final CheckBox[] musicCheckBox = new CheckBox[5];
    private final CheckBox[] soundCheckBox = new CheckBox[2];

    // exit Button
    protected Button exitButton;

    /**
     * Constructs a <code>SettingScreen</code> object.
     * There is no create () method in screen, and the show () method may be called many times.
     * Generally, it is better to do some initialization operations in the construction method
     *
     * @param fairyRogue the parameter passed in to facilitate interaction with other interfaces.
     */
    public SettingScreen(FairyRogue fairyRogue) {
        this.fairyRogue = fairyRogue;

        stage = new Stage(new StretchViewport(FairyRogue.VIRTUAL_WIDTH, FairyRogue.VIRTUAL_HEIGHT));

        setBackground();

        setCheckbox();

        setButton();

        // add image to stage
        stage.addActor(image);
        stage.addActor(musicImage);
        stage.addActor(soundImage);

        //add exit button to stage
        stage.addActor(exitButton);

        //add checkBox to stage
        for (int i = 0; i < 5; i++) {
            stage.addActor(musicCheckBox[i]);
        }
        for (int i = 0; i < 2; i++) {
            stage.addActor(soundCheckBox[i]);
        }
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
        if (checkboxOnTexture != null) {
            checkboxOnTexture.dispose();
        }
        if (checkboxOffTexture != null) {
            checkboxOffTexture.dispose();
        }
        if (bitmapFont != null) {
            bitmapFont.dispose();
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
        Texture musicTexture = new Texture(Gdx.files.internal("screen/button/Music.png"));
        Texture soundTexture = new Texture(Gdx.files.internal("screen/button/Sound.png"));

        image = new Image(new TextureRegion(texture));
        musicImage = new Image(new TextureRegion(musicTexture));
        soundImage = new Image(new TextureRegion(soundTexture));

        musicImage.setScale(0.3f);
        soundImage.setScale(0.3f);

        image.setPosition(stage.getWidth() / 2 - image.getWidth() / 2, stage.getHeight() / 2 - image.getHeight() / 2);
        musicImage.setPosition(150, 150);
        soundImage.setPosition(150, 100);

        image.setColor(1, 1, 1, 1);
        musicImage.setColor(1, 1, 1, 1);
        soundImage.setColor(1, 1, 1, 1);
    }

    /**
     * set the checkbox of this screen
     */
    private void setCheckbox() {

        // Create a check box checked and unselected texture,
        // and create a bitmap font (used to display the text of the check box)
        checkboxOnTexture = new Texture(Gdx.files.internal("screen/checkbox/checkbox_on.png"));
        checkboxOffTexture = new Texture(Gdx.files.internal("screen/checkbox/checkbox_off.png"));

        bitmapFont = new BitmapFont();

        // Create check box style
        CheckBox.CheckBoxStyle style = new CheckBox.CheckBoxStyle();

        // Set the selected and unselected texture regions of style
        style.checkboxOn = new TextureRegionDrawable(new TextureRegion(checkboxOnTexture));
        style.checkboxOff = new TextureRegionDrawable(new TextureRegion(checkboxOffTexture));

        // Sets the bitmap font for the check box text
        style.font = bitmapFont;

        // can also set the check box text font color (RGBA)
        // style.fontColor = new Color(1, 0, 0, 1);

        //create check box
        musicCheckBox[0] = new CheckBox("0%", style);
        musicCheckBox[1] = new CheckBox("25%", style);
        musicCheckBox[2] = new CheckBox("50%", style);
        musicCheckBox[3] = new CheckBox("75%", style);
        musicCheckBox[4] = new CheckBox("100%", style);

        soundCheckBox[0] = new CheckBox("Open", style);
        soundCheckBox[1] = new CheckBox("Close", style);

        // Set the location of the check box
        musicCheckBox[0].setPosition(450, 200);
        musicCheckBox[1].setPosition(550, 200);
        musicCheckBox[2].setPosition(650, 200);
        musicCheckBox[3].setPosition(750, 200);
        musicCheckBox[4].setPosition(850, 200);

        soundCheckBox[0].setPosition(550, 150);
        soundCheckBox[1].setPosition(750, 150);

        // manually set the selected / unselected state of the check box
        musicCheckBox[2].setChecked(true);
        soundCheckBox[0].setChecked(true);
        Assets.bgm.setVolume(0.5f);
        Assets.openSound();

        // Set the (checked / unselected) state change listener for the check box
        for (int index = 0; index < 5; index++) {

            final int finalIndex = index;

            musicCheckBox[index].addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    if (!activation)
                        return;
                    if (musicCheckBox[finalIndex].isChecked()) {
                        for (int i = 0; i < 5; i++) {
                            if (musicCheckBox[i].isChecked() && i != finalIndex)
                                musicCheckBox[i].setChecked(false);
                        }
                        //change volume
                        Assets.bgm.setVolume(finalIndex * 0.25f);
                    } else {
                        //Turn off the volume
                        // there is no way to make the check box unchangeable. So turn off the volume.
                        Assets.bgm.setVolume(0);
                    }

                    System.out.println("changed: " + finalIndex + " " + musicCheckBox[finalIndex].isChecked());
                }
            });
        }

        for (int index = 0; index < 2; index++) {

            final int finalIndex = index;

            soundCheckBox[index].addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    if (!activation)
                        return;
                    if (soundCheckBox[finalIndex].isChecked()) {
                        for (int i = 0; i < 2; i++) {
                            if (soundCheckBox[i].isChecked() && i != finalIndex)
                                soundCheckBox[i].setChecked(false);
                        }
                        //change volume
                        if (finalIndex == 0)
                            Assets.openSound();
                        else
                            Assets.closeSound();
                    } else {
                        //Turn off the volume
                        // there is no way to make the check box unchangeable. So turn off the volume.
                        Assets.closeSound();
                    }

                    System.out.println("changed: " + finalIndex + " " + soundCheckBox[finalIndex].isChecked());
                }
            });
        }
    }

    /**
     * set the button of this screen
     */
    protected void setButton() {
        // create textures in both bounce and press states
        Texture exitUpTexture = new Texture(Gdx.files.internal("screen/button/Exit.png"));
        Texture exitDownTexture = new Texture(Gdx.files.internal("screen/button/Exit_n.png"));

        // create ButtonStyle
        Button.ButtonStyle exitStyle = new Button.ButtonStyle();

        exitStyle.up = new TextureRegionDrawable(new TextureRegion(exitUpTexture));
        exitStyle.down = new TextureRegionDrawable(new TextureRegion(exitDownTexture));
        exitButton = new Button(exitStyle);

        exitButton.setTransform(true);
        exitButton.setScale(0.3f);
        exitButton.setPosition(750, 0);

        // add click listener to exit button
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!activation)
                    return;

                System.out.println("exit clicked!");
                fairyRogue.showScreen(null, fairyRogue.getSettingExitType());
            }
        });
    }
}
