package org.fairysoftw.fairyrogue;

import com.badlogic.gdx.*;
import org.fairysoftw.fairyrogue.screen.*;

public class FairyRogue extends Game {
    public static final int VIRTUAL_WIDTH = 960;
    public static final int VIRTUAL_HEIGHT = 768;

    Screen enterScreen;
    Screen startScreen;
    Screen mainScreen;
    Screen settingScreen;
    Screen aboutScreen;
    Screen menuScreen;

    @Override
    public void create() {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);
        Assets.load();

        enterScreen = new EnterScreen(this);
        startScreen = new StartScreen(this);
        mainScreen = new MainScreen(this);
        settingScreen = new SettingScreen(this);
        aboutScreen = new AboutScreen(this);
        menuScreen = new MenuScreen(this);

        setScreen(enterScreen);
    }

    /**
     * 开始场景展示完毕后调用该方法切换到主游戏场景
     * TODO 这里之后改成设置成指定的游戏场景，一个函数就够用了！！！
     */
    public void showScreen(Screen screen, String type) {

        // 设置当前场景为主游戏场景
        switch (type) {
            case "startScreen":
                setScreen(startScreen);
                break;
            case "mainScreen":
                setScreen(mainScreen);
                break;
            case "settingScreen":
                setScreen(settingScreen);
                break;
            case "aboutScreen":
                setScreen(aboutScreen);
                break;
            case "exit":
                Gdx.app.exit();
            default:
                return;
        }

        if (screen instanceof EnterScreen) {
            // 由于 EnterScreen 只有在游戏启动时展示一下, 之后都不需要展示,
            // 所以启动完 GameScreen 后手动调用 EnterScreen 的 dispose() 方法销毁开始场景。
            screen.dispose();

            // 场景销毁后, 场景变量值空, 防止二次调用 dispose() 方法
            screen = null;
        }
    }

    @Override
    public void dispose() {
        Assets.dispose();
        startScreen.dispose();
        mainScreen.dispose();
        settingScreen.dispose();
        aboutScreen.dispose();
        menuScreen.dispose();
    }
}
