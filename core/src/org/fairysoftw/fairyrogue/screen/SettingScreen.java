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

public class SettingScreen extends ScreenAdapter {
    // 为了方便与 其他界面 进行交互, 创建 Screen 时将 FairyRogue 作为参数传进来。
    protected final FairyRogue fairyRogue;

    //记录是否在这个界面，防止误触发
    protected boolean activation = false;

    // 舞台
    private final Stage stage;

    // 纹理
    private Texture texture;

    // 图片控件
    Image image;
    Image musicImage;
    Image soundImage;

    // 复选框选中状态的纹理
    private Texture checkboxOnTexture;

    // 复选框未选中状态的纹理
    private Texture checkboxOffTexture;

    // 位图字体
    private BitmapFont bitmapFont;

    // 复选框
    private final CheckBox[] musicCheckBox = new CheckBox[5];
    private final CheckBox[] soundCheckBox = new CheckBox[2];

    // 退出按钮
    protected Button exitButton;

    public SettingScreen(FairyRogue fairyRogue) {
        this.fairyRogue = fairyRogue;

        // 在 Screen 中没有 create() 方法, show() 方法有可能被调用多次, 所有一般在构造方法中做一些初始化操作较好
        // 使用伸展视口（StretchViewport）创建舞台
        stage = new Stage(new StretchViewport(FairyRogue.VIRTUAL_WIDTH, FairyRogue.VIRTUAL_HEIGHT));

        setBackground();

        setCheckbox();

        setButton();

        // 添加 image 到舞台
        stage.addActor(image);
        stage.addActor(musicImage);
        stage.addActor(soundImage);

        //添加 exit button 到舞台
        stage.addActor(exitButton);

        //添加 checkBox 到舞台
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
        // 将输入处理设置到舞台（必须设置, 否则点击没效果）
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

        // 更新舞台逻辑
        stage.act();
        // 绘制舞台
        stage.draw();
    }

    @Override
    public void dispose() {
        // 当应用退出时释放资源
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

    private void setBackground() {
        // 创建为纹理
        texture = new Texture(Gdx.files.internal("screen/background.jpg"));
        Texture musicTexture = new Texture(Gdx.files.internal("screen/button/Music.png"));
        Texture soundTexture = new Texture(Gdx.files.internal("screen/button/Sound.png"));

        // 创建 Image
        image = new Image(new TextureRegion(texture));
        musicImage = new Image(new TextureRegion(musicTexture));
        soundImage = new Image(new TextureRegion(soundTexture));

        musicImage.setScale(0.3f);
        soundImage.setScale(0.3f);

        // 设置 image 的相关属性
        image.setPosition(stage.getWidth() / 2 - image.getWidth() / 2, stage.getHeight() / 2 - image.getHeight() / 2);
        musicImage.setPosition(150, 150);
        soundImage.setPosition(150, 100);

        image.setColor(1, 1, 1, 1);
        musicImage.setColor(1, 1, 1, 1);
        soundImage.setColor(1, 1, 1, 1);
    }

    private void setCheckbox() {

        /*
         * 第 1 步: 创建复选框 选中 和 未选中 两种状态的纹理, 以及创建位图字体（用于显示复选框的文本）
         */
        checkboxOnTexture = new Texture(Gdx.files.internal("screen/checkbox/checkbox_on.png"));
        checkboxOffTexture = new Texture(Gdx.files.internal("screen/checkbox/checkbox_off.png"));

        bitmapFont = new BitmapFont();

        /*
         * 第 2 步: 创建 CheckBoxStyle
         */
        CheckBox.CheckBoxStyle style = new CheckBox.CheckBoxStyle();

        // 设置 style 的 选中 和 未选中 状态的纹理区域
        style.checkboxOn = new TextureRegionDrawable(new TextureRegion(checkboxOnTexture));
        style.checkboxOff = new TextureRegionDrawable(new TextureRegion(checkboxOffTexture));

        // 设置复选框文本的位图字体
        style.font = bitmapFont;

        // 也可以设置复选框文本的字体颜色（RGBA）
        // style.fontColor = new Color(1, 0, 0, 1);

        /*
         * 第 3 步: 创建 CheckBox
         */
        musicCheckBox[0] = new CheckBox("0%", style);
        musicCheckBox[1] = new CheckBox("25%", style);
        musicCheckBox[2] = new CheckBox("50%", style);
        musicCheckBox[3] = new CheckBox("75%", style);
        musicCheckBox[4] = new CheckBox("100%", style);

        soundCheckBox[0] = new CheckBox("Open", style);
        soundCheckBox[1] = new CheckBox("Close", style);

        // 设置复选框的位置
        musicCheckBox[0].setPosition(450, 200);
        musicCheckBox[1].setPosition(550, 200);
        musicCheckBox[2].setPosition(650, 200);
        musicCheckBox[3].setPosition(750, 200);
        musicCheckBox[4].setPosition(850, 200);

        soundCheckBox[0].setPosition(550, 150);
        soundCheckBox[1].setPosition(750, 150);

        // 可以手动设置复选框的选中状/未选中态
        musicCheckBox[2].setChecked(true);
        soundCheckBox[0].setChecked(true);
        Assets.bgm.setVolume(0.5f);
        Assets.openSound();

        // 设置复选框的（选中/未选中）状态改变监听器
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
                        //改变音量
                        Assets.bgm.setVolume(finalIndex * 0.25f);
                    } else {
                        //关闭音量 （这里没有办法让复选框勾选后不可改变。所以就关闭音量）
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
                        //改变音量
                        if (finalIndex == 0)
                            Assets.openSound();
                        else
                            Assets.closeSound();
                    } else {
                        //关闭音量 （这里没有办法让复选框勾选后不可改变。所以就关闭音量）
                        Assets.closeSound();
                    }

                    System.out.println("changed: " + finalIndex + " " + soundCheckBox[finalIndex].isChecked());
                }
            });
        }
    }

    protected void setButton() {
        //创建 弹起 和 按下 两种状态的纹理
        Texture exitUpTexture = new Texture(Gdx.files.internal("screen/button/Exit.png"));
        Texture exitDownTexture = new Texture(Gdx.files.internal("screen/button/Exit_n.png"));

        //创建 ButtonStyle
        Button.ButtonStyle exitStyle = new Button.ButtonStyle();

        exitStyle.up = new TextureRegionDrawable(new TextureRegion(exitUpTexture));
        exitStyle.down = new TextureRegionDrawable(new TextureRegion(exitDownTexture));
        exitButton = new Button(exitStyle);

        exitButton.setTransform(true);
        exitButton.setScale(0.3f);
        exitButton.setPosition(750, 0);

        // 给退出按钮添加点击监听器
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
