
package org.fairysoftw.fairyrogue.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import org.fairysoftw.fairyrogue.Assets;
import org.fairysoftw.fairyrogue.FairyRogue;
import org.fairysoftw.fairyrogue.actor.*;
import org.fairysoftw.fairyrogue.stage.GameStage;

public class MainScreen extends ScreenAdapter {
    private OrthogonalTiledMapRenderer mapRenderer;
    private Viewport currentViewport;
    private SpriteBatch batch;
    private GameStage stage;
    private PlayerActor playerActor;
    private OrthographicCamera mainCamera;


    private OrthographicCamera mapCamera;
    private Viewport mapViewport;
    private GameStage mapStage;
    private OrthogonalTiledMapRenderer mapRenderer2;

    public MainScreen(Game game) {
        batch = new SpriteBatch();


        mainCamera = new OrthographicCamera();
        currentViewport = new ExtendViewport(FairyRogue.VIRTUAL_WIDTH, FairyRogue.VIRTUAL_HEIGHT, mainCamera);
        stage = new GameStage(currentViewport, batch);
        mapRenderer = new OrthogonalTiledMapRenderer(Assets.map, batch);
        mapRenderer.setView(mainCamera);



        mapCamera=new OrthographicCamera();

        mapViewport=new ExtendViewport(FairyRogue.VIRTUAL_WIDTH,FairyRogue.VIRTUAL_HEIGHT,mapCamera);
        mapViewport.setScreenPosition(0,0);
        mapStage=new GameStage(mapViewport,batch);
        mapRenderer2 = new OrthogonalTiledMapRenderer(Assets.map, batch);
        mapRenderer2.setView(mapCamera);
        mapCamera.zoom=3;
        mapViewport.setScreenPosition(-FairyRogue.VIRTUAL_WIDTH/3,-FairyRogue.VIRTUAL_HEIGHT/3);
//        mapViewport.setScreenSize(FairyRogue.VIRTUAL_WIDTH/2,FairyRogue.VIRTUAL_HEIGHT/2);







    }

    @Override
    public void show() {
        stage.draw();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        updateCamera();



        currentViewport.apply();
        mapRenderer.setView(mainCamera);
        mapRenderer.render();
        stage.act();
        stage.draw();


        mapViewport.apply();
        mapRenderer2.setView(mapCamera);
        mapRenderer2.render();
        mapStage.act();
        mapStage.draw();
    }

    @Override
    public void resize(int width, int height) {
        currentViewport.update(width, height);
    }

    private void updateCamera() {
        mainCamera.position.x = stage.getActor("player").getX();
        mainCamera.position.y = stage.getActor("player").getY();
//        System.out.println(stage.getActor("player").getX()+"  "+stage.getActor("player").getY());

        mainCamera.update();
        mapCamera.update();
    }

    @Override
    public void dispose() {
        mapRenderer.dispose();
        stage.dispose();

        mapRenderer2.dispose();
        mapStage.dispose();
    }


}


/*=======
package org.fairysoftw.fairyrogue.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import org.fairysoftw.fairyrogue.Assets;
import org.fairysoftw.fairyrogue.FairyRogue;
import org.fairysoftw.fairyrogue.actor.*;
import org.fairysoftw.fairyrogue.stage.GameStage;
import org.fairysoftw.fairyrogue.stage.MapStage;

public class MainScreen extends ScreenAdapter {
    private OrthogonalTiledMapRenderer mapRenderer;
    private Viewport currentViewport;
    private SpriteBatch batch;
    private GameStage stage;
    private PlayerActor playerActor;
    private OrthographicCamera mainCamera;

    private OrthographicCamera mapCamera;
    private Viewport mapViewport;
    private GameStage mapStage;
    private OrthogonalTiledMapRenderer mapRenderer2;


    public MainScreen(Game game) {



        batch = new SpriteBatch();


        mainCamera = new OrthographicCamera();
        currentViewport = new ExtendViewport(FairyRogue.VIRTUAL_WIDTH/2, FairyRogue.VIRTUAL_HEIGHT/2, mainCamera);
        stage = new GameStage(currentViewport, batch);
        mapRenderer = new OrthogonalTiledMapRenderer(Assets.map, batch);
        mapRenderer.setView(mainCamera);



        mapCamera=new OrthographicCamera();

        mapViewport=new FitViewport(FairyRogue.VIRTUAL_WIDTH,FairyRogue.VIRTUAL_HEIGHT,mapCamera);
        mapViewport.setScreenPosition(0,0);
        mapStage=new GameStage(mapViewport,batch);
        mapRenderer2 = new OrthogonalTiledMapRenderer(Assets.map, batch);
        mapRenderer2.setView(mapCamera);
//        mapViewport.update(400,400,false);
        mapViewport.setScreenPosition(00,-50);
        mapViewport.setScreenSize(400,300);







    }

    @Override
    public void show() {
        stage.draw();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


currentViewport.apply(true);
        updateCamera();
        mapRenderer.setView(mainCamera);
        mapRenderer.render();
        stage.act();
        stage.draw();
        mapViewport.apply();
        mapRenderer2.setView(mapCamera);
        mapRenderer2.render();
        mapStage.act();
        mapStage.draw();


    }

    @Override
    public void resize(int width, int height) {
        currentViewport.update(width, height);
    }

    private void updateCamera() {
        mainCamera.position.x = stage.getActor("player").getX();
        mainCamera.position.y =stage.getActor("player").getY();

        mainCamera.update();
        mapCamera.update();
    }

    @Override
    public void dispose() {
        mapRenderer.dispose();
        stage.dispose();


        mapRenderer2.dispose();
        mapStage.dispose();
    }

}


>>>>>>> minimap*/
