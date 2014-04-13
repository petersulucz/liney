package com.sulucz.lineybounce.game;

import com.sulucz.lineybounce.Globals;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MainMenu
    extends GameScreen
    implements InputProcessor
{
    public MainMenu(SpriteBatch batch)
    {
        super(batch);
    }


    @Override
    public void start()
    {
        // TODO Auto-generated method stub
        this.visible = true;
        // Gdx.input.setInputProcessor(this);
        Globals.mainGame.stop();
        Globals.mainGame.getBoundsController().reset();
    }


    @Override
    public void stop()
    {
        this.visible = false;

        // TODO Auto-generated method stub

    }


    @Override
    public void pause()
    {
        // TODO Auto-generated method stub

    }


    @Override
    public void reset()
    {
        // TODO Auto-generated method stub

    }


    @Override
    public boolean keyDown(int keycode)
    {
        // TODO Auto-generated method stub
        return false;
    }


    @Override
    public boolean keyUp(int keycode)
    {
        // TODO Auto-generated method stub
        return false;
    }


    @Override
    public boolean keyTyped(char character)
    {
        // TODO Auto-generated method stub
        return false;
    }


    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button)
    {
        // TODO Auto-generated method stub
        return false;
    }


    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button)
    {
        // TODO Auto-generated method stub
        Globals.mainGame.start();
        return true;
    }


    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer)
    {
        // TODO Auto-generated method stub
        return false;
    }


    @Override
    public boolean mouseMoved(int screenX, int screenY)
    {
        // TODO Auto-generated method stub
        return false;
    }


    @Override
    public boolean scrolled(int amount)
    {
        // TODO Auto-generated method stub
        return false;
    }


    @Override
    public void render()
    {
        // TODO Auto-generated method stub
        if (this.visible)
        {
            if (Gdx.input.justTouched())
                Globals.mainGame.start();
            Globals.standardFont.setScale(0.1f);
            batch.setProjectionMatrix(camera.combined);
            batch.begin();
            Globals.standardFont.draw(batch, "LINEY", -2f, 0);
            Globals.standardFont.draw(batch, "click to start", -5f, -2f);
            batch.end();
        }
    }

}
