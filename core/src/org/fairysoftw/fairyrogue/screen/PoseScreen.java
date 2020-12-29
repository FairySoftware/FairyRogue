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

public class PoseScreen extends ScreenAdapter {
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
    private Button continueButton;

    // 设置按钮
    private Button restartButton;

    // 关于按钮
    private Button settingButton;

    // 退出按钮
    private Button exitButton;

    public PoseScreen(FairyRogue fairyRogue) {

        this.fairyRogue = fairyRogue;

        // 在 Screen 中没有 create() 方法, show() 方法有可能被调用多次, 所有一般在构造方法中做一些初始化操作较好
        // 使用伸展视口（StretchViewport）创建舞台
        stage = new Stage(new StretchViewport(FairyRogue.VIRTUAL_WIDTH, FairyRogue.VIRTUAL_HEIGHT));

        setBackground();

        setButton();

        // 添加 image 到舞台
        stage.addActor(image);
        //添加 button 到舞台
        stage.addActor(continueButton);
        stage.addActor(restartButton);
        stage.addActor(settingButton);
        stage.addActor(exitButton);

    }

    @Override
    public void show() {
        activation = true;

        // 将输入处理设置到舞台（必须设置, 否则点击按钮没效果）
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void hide() {
        activation = false;
    }

    @Override
    public void render(float delta) {
        // 累计渲染时间步

        Gdx.gl.glClearColor(0.6f, 0.8f, 0.8f, 1);
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
        texture = new Texture(Gdx.files.internal("screen/pose.png"));

        // 创建 Image
        image = new Image(new TextureRegion(texture));

        image.setScale(0.3f);

        // 设置 image 的相关属性
        image.setPosition(stage.getWidth() / 3 - 15,
                stage.getHeight() - image.getHeight() / 2 + 100);

        image.setColor(1, 1, 1, 1);
    }

    private void setButton() {
        //创建 弹起 和 按下 两种状态的纹理
        Texture continueUpTexture = new Texture(Gdx.files.internal("screen/button/Continue.png"));
        Texture continueDownTexture = new Texture(Gdx.files.internal("screen/button/Continue_n.png"));

        Texture restartUpTexture = new Texture(Gdx.files.internal("screen/button/NewGame.png"));
        Texture restartDownTexture = new Texture(Gdx.files.internal("screen/button/NewGame_n.png"));

        Texture settingUpTexture = new Texture(Gdx.files.internal("screen/button/Settings.png"));
        Texture settingDownTexture = new Texture(Gdx.files.internal("screen/button/Settings_n.png"));

        Texture exitUpTexture = new Texture(Gdx.files.internal("screen/button/ReturnToMenu.png"));
        Texture exitDownTexture = new Texture(Gdx.files.internal("screen/button/ReturnToMenu_n.png"));

        //创建 ButtonStyle
        Button.ButtonStyle continueStyle = new Button.ButtonStyle();
        Button.ButtonStyle restartStyle = new Button.ButtonStyle();
        Button.ButtonStyle settingStyle = new Button.ButtonStyle();
        Button.ButtonStyle exitStyle = new Button.ButtonStyle();

        // 设置 style 的 弹起 和 按下 状态的纹理区域 并且创建 按钮
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

        // 设置按钮的缩放比和位置
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

        // 给开始按钮添加点击监听器
        continueButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!activation)
                    return;

                System.out.println("start clicked!");
                fairyRogue.showScreen(null, "mainScreen_continue");
            }
        });

        // 给设置按钮添加点击监听器
        restartButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!activation)
                    return;

                System.out.println("restart clicked!");
                fairyRogue.restartGame();
                fairyRogue.showScreen(null, "mainScreen");
            }
        });

        // 给关于按钮添加点击监听器
        settingButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!activation)
                    return;

                System.out.println("setting clicked!");

                fairyRogue.showScreen(null, "settingScreenInGame");
            }
        });

        // 给退出按钮添加点击监听器
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
