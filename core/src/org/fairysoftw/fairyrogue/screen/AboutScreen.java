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
 * 关于界面, 实现 Screen 接口 或者 继承 ScreenAdapter 类, ScreenAdapter 类空实现了 Screen 接口的所有方法。
 * 这个场景是介绍我们的制作团队，这里会显示制作团队的相关信息，并且持续几秒种
 */
public class AboutScreen extends ScreenAdapter {
    // 为了方便与 其他界面 进行交互, 创建 Screen 时将 FairyRogue 作为参数传进来。
    private final FairyRogue fairyRogue;

    // 舞台
    private Stage stage;

    // 纹理
    private Texture texture;

    // 图片控件
    Image image;

    //记录是否在这个界面，防止误触发
    private boolean activation = false;

    // 退出按钮
    private Button exitButton;

    public AboutScreen(FairyRogue fairyRogue) {

        this.fairyRogue = fairyRogue;

        // 在 Screen 中没有 create() 方法, show() 方法有可能被调用多次, 所有一般在构造方法中做一些初始化操作较好
        setBackground();
        setButton();

        // 添加 image 到舞台
        stage.addActor(image);
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
        if (stage != null) {
            stage.dispose();
        }
    }

    private void setBackground() {
        // 使用伸展视口（StretchViewport）创建舞台
        stage = new Stage(new StretchViewport(FairyRogue.VIRTUAL_WIDTH, FairyRogue.VIRTUAL_HEIGHT));

        // 创建为纹理
        texture = new Texture(Gdx.files.internal("screen/about.png"));

        // 创建 Image
        image = new Image(new TextureRegion(texture));

        // 设置 image 的相关属性
        image.setPosition(stage.getWidth() / 2 - image.getWidth() / 2,
                stage.getHeight() / 2 - image.getHeight() / 2);

        image.setColor(1, 1, 1, 1);
    }

    private void setButton() {
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
                fairyRogue.showScreen(null, "startScreen");
            }
        });
    }
}