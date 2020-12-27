package org.fairysoftw.fairyrogue;

import com.badlogic.gdx.*;
import org.fairysoftw.fairyrogue.screen.MainScreen;
import org.fairysoftw.fairyrogue.screen.MenuScreen;
import org.fairysoftw.fairyrogue.screen.SettingScreen;
import org.fairysoftw.fairyrogue.screen.StartScreen;

public class FairyRogue extends Game {
    public static final int VIRTUAL_WIDTH = 960;
    public static final int VIRTUAL_HEIGHT = 768;
    Screen mainScreen;
    Screen menuScreen;
    Screen settingScreen;
    Screen startScreen;

    @Override
    public void create() {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);
        Assets.load();

        startScreen = new StartScreen(this);
        mainScreen = new MainScreen(this);
        menuScreen = new MenuScreen(this);
        settingScreen = new SettingScreen(this);

        setScreen(startScreen);
    }

    /**
     * 开始场景展示完毕后调用该方法切换到主游戏场景
     */
    public void showMainScreen() {
        // 设置当前场景为主游戏场景
        setScreen(mainScreen);

        if (startScreen != null) {
            // 由于 StartScreen 只有在游戏启动时展示一下, 之后都不需要展示,
            // 所以启动完 GameScreen 后手动调用 StartScreen 的 dispose() 方法销毁开始场景。
            startScreen.dispose();

            // 场景销毁后, 场景变量值空, 防止二次调用 dispose() 方法
            startScreen = null;
        }
    }

    @Override
    public void dispose() {
        Assets.dispose();
        mainScreen.dispose();
    }
}
