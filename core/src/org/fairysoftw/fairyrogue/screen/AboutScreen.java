package org.fairysoftw.fairyrogue.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import org.fairysoftw.fairyrogue.FairyRogue;

/**
 * 欢迎界面, 实现 Screen 接口 或者 继承 ScreenAdapter 类, ScreenAdapter 类空实现了 Screen 接口的所有方法。
 * 这个场景使用我们团队自己的logo居中显示 3 秒钟当做是游戏的欢迎界面。
 * PS: 类似 Screen 这样的有许多方法的接口, 更多时候只需要实现其中一两个方法, 往往会有一个对应的便捷的空实现所有接口方法的 XXAdapter 类,
 * 例如 ApplicationListener >> ApplicationAdapter, InputProcessor >> InputAdapter
 */
public class AboutScreen extends ScreenAdapter {
    // 为了方便与 其他界面 进行交互, 创建 Screen 时将 FairyRogue 作为参数传进来。
    private final FairyRogue fairyRogue;

    // 舞台
    private final Stage stage;

    // 纹理
    private final Texture texture;

    // 图片控件
    Image image;

    // 渲染时间步累计变量（当前场景被展示的时间总和）
    private float deltaSum;

    private float i = 1;
    private float j = 1;
    private float k = 1;

    public AboutScreen(FairyRogue fairyRogue) {

        this.fairyRogue = fairyRogue;

        // 在 Screen 中没有 create() 方法, show() 方法有可能被调用多次, 所有一般在构造方法中做一些初始化操作较好

        // 使用伸展视口（StretchViewport）创建舞台
        stage = new Stage(new StretchViewport(FairyRogue.VIRTUAL_WIDTH, FairyRogue.VIRTUAL_HEIGHT));

        // 创建为纹理
        texture = new Texture(Gdx.files.internal("ui/logo.jpg"));

        // 创建 Image
        image = new Image(new TextureRegion(texture));

        // 设置 image 的相关属性
        image.setPosition(stage.getWidth() / 2 - image.getWidth() / 2,
                stage.getHeight() / 2 - image.getHeight() / 2);

        image.setColor(1, 1, 1, 1);

        // 添加 image 到舞台
        stage.addActor(image);
    }

    @Override
    public void show() {
        deltaSum = 0;
    }

    @Override
    public void render(float delta) {
        //TODO
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
}