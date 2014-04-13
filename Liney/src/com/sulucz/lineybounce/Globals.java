package com.sulucz.lineybounce;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.Texture;
import com.sulucz.lineybounce.data.XMLLineController;
import com.sulucz.lineybounce.data.XMLLoader;
import com.sulucz.lineybounce.game.MainGame;
import com.sulucz.lineybounce.game.MainMenu;
import com.sulucz.lineybounce.game.components.Player;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Globals
{
    public static XMLLineController xmlLineController = new XMLLineController();
    public static MainMenu          mainMenu;
    public static MainGame          mainGame;
    public static float             viewHeight        = 22;
    public static Player            player;
    public static BitmapFont        standardFont;
    public static XMLLoader         loader;
}
