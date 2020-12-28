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
 * 开始界面, 实现 Screen 接口 或者 继承 ScreenAdapter 类, ScreenAdapter 类空实现了 Screen 接口的所有方法。
 * 这个界面包括了开始游戏、设置、关于、退出游戏四个选项，与其他几个界面相关联
 */
public class StartScreen extends ScreenAdapter {
    // 为了方便与 其他界面 进行交互, 创建 Screen 时将 FairyRogue 作为参数传进来。
    private final FairyRogue fairyRogue;

    //记录是否在这个界面，防止误触发
    private boolean activation = false;

    // 舞台
    private final Stage stage;

    // 纹理
    private Texture texture;

    // 图片控件
    Image image;

    // 开始按钮
    private Button startButton;

    // 设置按钮
    private Button settingsButton;

    // 关于按钮
    private Button aboutButton;

    // 退出按钮
    private Button exitButton;

    public StartScreen(FairyRogue fairyRogue) {

        this.fairyRogue = fairyRogue;

        // 在 Screen 中没有 create() 方法, show() 方法有可能被调用多次, 所有一般在构造方法中做一些初始化操作较好
        // 使用伸展视口（StretchViewport）创建舞台
        stage = new Stage(new StretchViewport(FairyRogue.VIRTUAL_WIDTH, FairyRogue.VIRTUAL_HEIGHT));

        setBackground();

        setButton();

        // 添加 image 到舞台
        stage.addActor(image);
        //添加 button 到舞台
        stage.addActor(startButton);
        stage.addActor(settingsButton);
        stage.addActor(aboutButton);
        stage.addActor(exitButton);

    }

    @Override
    public void show() {
        activation = true;
    }

    @Override
    public void render(float delta) {
        // 累计渲染时间步

        Gdx.gl.glClearColor(1, 1, 1, 1);
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
        if (stage != null) {
            stage.dispose();
        }
    }

    private void setBackground() {
        // 创建为纹理
        texture = new Texture(Gdx.files.internal("screen/background.jpg"));

        // 创建 Image
        image = new Image(new TextureRegion(texture));

        // 设置 image 的相关属性
        image.setPosition(stage.getWidth() / 2 - image.getWidth() / 2,
                stage.getHeight() / 2 - image.getHeight() / 2);

        image.setColor(1, 1, 1, 1);
    }

    private void setButton() {
        // 将输入处理设置到舞台（必须设置, 否则点击按钮没效果）
        Gdx.input.setInputProcessor(stage);

        //创建 弹起 和 按下 两种状态的纹理
        Texture startUpTexture = new Texture(Gdx.files.internal("screen/button/Start.png"));
        Texture startDownTexture = new Texture(Gdx.files.internal("screen/button/Start_n.png"));

        Texture settingsUpTexture = new Texture(Gdx.files.internal("screen/button/Settings.png"));
        Texture settingsDownTexture = new Texture(Gdx.files.internal("screen/button/Settings_n.png"));

        Texture aboutUpTexture = new Texture(Gdx.files.internal("screen/button/About.png"));
        Texture aboutDownTexture = new Texture(Gdx.files.internal("screen/button/About_n.png"));

        Texture exitUpTexture = new Texture(Gdx.files.internal("screen/button/Exit.png"));
        Texture exitDownTexture = new Texture(Gdx.files.internal("screen/button/Exit_n.png"));

        //创建 ButtonStyle
        Button.ButtonStyle startStyle = new Button.ButtonStyle();
        Button.ButtonStyle settingsStyle = new Button.ButtonStyle();
        Button.ButtonStyle aboutStyle = new Button.ButtonStyle();
        Button.ButtonStyle exitStyle = new Button.ButtonStyle();

        // 设置 style 的 弹起 和 按下 状态的纹理区域 并且创建 按钮
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

        // 设置按钮的缩放比和位置
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

        // 给开始按钮添加点击监听器
        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!activation)
                    return;

                System.out.println("start clicked!");
                fairyRogue.showScreen(null, "mainScreen");
            }
        });

        // 给设置按钮添加点击监听器
        settingsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!activation)
                    return;

                System.out.println("settings clicked!");
                fairyRogue.showScreen(null, "settingsScreen");
            }
        });

        // 给关于按钮添加点击监听器
        aboutButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!activation)
                    return;

                System.out.println("about clicked!");
                fairyRogue.showScreen(null, "aboutScreen");
            }
        });

        // 给退出按钮添加点击监听器
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