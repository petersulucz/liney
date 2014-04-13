package com.sulucz.lineybounce;

import com.sulucz.lineybounce.data.XMLLoader;
import com.sulucz.lineybounce.game.MainGame;
import com.sulucz.lineybounce.game.MainMenu;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Game
    implements ApplicationListener
{
    private SpriteBatch batch;


    @Override
    public void create()
    {
        Globals.loader = new XMLLoader();
        Globals.loader.loadLevels();
        batch = new SpriteBatch();
        Globals.standardFont =
            new BitmapFont(
                Gdx.files.internal("data/Font/calibri32.fnt"),
                Gdx.files.internal("data/Font/calibri32_0.tga"),
                false);
        Globals.standardFont.setUseIntegerPositions(false);
        Globals.mainGame = new MainGame(batch);
        Globals.mainMenu = new MainMenu(batch);
        Globals.mainMenu.start();
    }


    @Override
    public void dispose()
    {
        batch.dispose();
    }


    @Override
    public void render()
    {
         Gdx.gl.glClearColor(1f, .612f, .612f,1f);
         Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        Globals.mainGame.render();
        Globals.mainMenu.render();
    }


    @Override
    public void resize(int width, int height)
    {
    }


    @Override
    public void pause()
    {
    }


    @Override
    public void resume()
    {
    }
}
