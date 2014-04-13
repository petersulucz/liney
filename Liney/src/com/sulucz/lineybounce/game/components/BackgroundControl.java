package com.sulucz.lineybounce.game.components;

import com.sulucz.lineybounce.Globals;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class BackgroundControl
{
    private Sprite sky;
    private Sprite ground;


    public BackgroundControl()
    {
        sky = new Sprite(new Texture(Gdx.files.internal("data/sky.png")));
    }


    public void dispose()
    {
        sky.getTexture().dispose();
    }


    public void Update()
    {
        sky.setBounds(
            Globals.player.getPosition().x
                - Globals.mainGame.getCamera().viewportWidth / 2 - 5,
            Globals.mainGame.getCamera().viewportWidth * 0.75f * -0.5f,
            Globals.mainGame.getCamera().viewportWidth + 10,
            (Globals.player.getPosition().y / 2)
                + Globals.mainGame.getCamera().viewportWidth * 0.75f);
        // float width = Globals.mainGame.getCamera().viewportWidth
        // sky.setBounds(Globals.player.getPosition().x -
// Globals.mainGame.getCamera().viewportWidth/2-5,
// Globals.mainGame.getCamera().viewportWidth * 0.75 / -2f,
// (Globals.mainGame.getCamera().viewportWidth+10)*0.75f);
        sky.draw(Globals.mainGame.getBatch());
    }
}
