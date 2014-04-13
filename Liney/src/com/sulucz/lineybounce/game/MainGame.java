package com.sulucz.lineybounce.game;

import com.sulucz.lineybounce.game.components.BackgroundControl;
import com.sulucz.lineybounce.Globals;
import com.sulucz.lineybounce.data.XMLLine;
import com.sulucz.lineybounce.game.components.BoundsController;
import com.sulucz.lineybounce.game.components.Player;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class MainGame
    extends GameScreen
{
    private World            world;
    private BoundsController boundsControl;
    public float[]           bgcolor;
    private float            bgdir = 1;
    private BackgroundControl backGroundControl;


    public MainGame(SpriteBatch batch)
    {
        super(batch);
        this.bgcolor = new float[] { 1f, 1f, 1f };
        this.camera.position.y = 5f;
        setUpPhysics();
        addBoundaries();
        Globals.player = new Player(world, camera);
        this.backGroundControl = new BackgroundControl();
    }

    private static float thick = 0.5f;


    private void addBoundaries()
    {
        this.boundsControl = new BoundsController(this.world);
    }


    private void setUpPhysics()
    {
        world = new World(new Vector2(0, -18), true);

    }


    @Override
    public void start()
    {
        this.visible = true;
        Globals.mainMenu.stop();

        this.reset();

    }


    @Override
    public void stop()
    {
        this.visible = false;
        this.bgcolor = new float[] { 1f, 1f, 1f };
    }


    @Override
    public void pause()
    {
        // TODO Auto-generated method stub

    }


    @Override
    public void reset()
    {
        this.bgcolor = new float[] { 1f, 1f, 1f };
        this.bgdir = 1;
        if (this.batch.isDrawing())
            this.batch.end();
        this.world.clearForces();
        Globals.xmlLineController.reset();
        Globals.player.reset();

    }

    public BoundsController getBoundsController()
    {
        return this.boundsControl;
    }

    float height = 200f;


    private void handlebackground()
    {
        this.bgcolor[0] =
            1f - Math.max(
                Math.min(Globals.player.getPosition().y / height, 0.5f),
                0);
        this.bgcolor[1] =
            1f - Math.max(
                Math.min(Globals.player.getPosition().y / height, 0.5f),
                0);
        this.bgcolor[1] =
            1f - Math.max(
                -3 * Math.min(Globals.player.getPosition().y / height, 0.5f),
                0);
        this.bgcolor[2] =
            1f - Math.max(
                -3 * Math.min(Globals.player.getPosition().y / height, 0.5f),
                0);
    }


    @Override
    public void render()
    {
        if (this.visible)
        {
            Globals.player.Update();
            Globals.standardFont.setScale(0.05f);
            this.handlebackground();
            this.boundsControl.Update();
            // debugRenderer.render(world, camera.combined);
            this.batch.setProjectionMatrix(this.camera.combined);
            this.batch.begin();
            this.backGroundControl.Update();
            Globals.player.Draw(this.batch);
            Globals.standardFont.draw(
                batch,
                "dst: " + (int)Globals.player.getPosition().x / 10,
                this.camera.position.x - this.camera.viewportWidth / 2,
                this.camera.position.y + this.camera.viewportHeight / 2 - 1f);
            this.boundsControl.Draw(camera);
            this.batch.end();
            world.step(1 / 60f, 6, 2);
        }
        else
        {
            this.boundsControl.removeFirstBound();
        }
    }
}
