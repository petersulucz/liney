package com.sulucz.lineybounce.game.components;

import com.sulucz.lineybounce.Globals;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class Player
{

    private World   world;
    private Camera  camera;
    private Body    ballbod;
    private Body    ballSensor;
    private Texture texture;
    private Sprite  sprite;
    private Vector2 size;
    private Color   color;
    private int     overlapCount;


    public Player(World world, Camera camera)
    {
        size = new Vector2(2f, 2f);
        this.camera = camera;
        this.world = world;
        this.setUpPhysics();
        this.texture = new Texture(Gdx.files.internal("data/player2.png"));
        this.sprite = new Sprite(this.texture);
        this.sprite.setSize(size.x, size.y);
        this.sprite.setOrigin(size.x / 2, size.y / 2);
        color = Color.WHITE.cpy();
    }


    public void reset()
    {
        this.overlapCount = 0;
        this.ballbod.setTransform(0f, 30f, 0f);
        this.ballbod.setLinearVelocity(0, 0);
        this.ballbod.setAngularVelocity(0);
    }


    public Vector2 getPosition()
    {
        return this.ballbod.getPosition();
    }


    private void setUpPhysics()
    {
        BodyDef ballDef = new BodyDef();
        ballDef.type = BodyType.DynamicBody;
        ballDef.position.set(0, 30);
        ballDef.bullet = true;

        ballbod = world.createBody(ballDef);
        this.ballSensor = world.createBody(ballDef);

        CircleShape circle = new CircleShape();
        circle.setRadius(1f);
        CircleShape csens = new CircleShape();
        csens.setRadius(1.02f);

        FixtureDef ballFix = new FixtureDef();
        ballFix.shape = circle;
        ballFix.density = 0.5f;
        ballFix.friction = 0.8f;
        ballFix.restitution = 0.2f;

        FixtureDef ballsensefix = new FixtureDef();
        ballsensefix.shape = csens;
        ballsensefix.isSensor = true;
        this.world.setContactListener(new CollisionListener());
        this.ballSensor.createFixture(ballsensefix);

        Fixture fix = ballbod.createFixture(ballFix);

        circle.dispose();
        csens.dispose();
    }

    boolean loaded = false;


    public void Update()
    {
        if (this.ballbod.getPosition().y < -50f)
        {
            Globals.mainMenu.start();
        }
        this.camera.position.set(
            ballbod.getPosition().x,
            this.ballbod.getPosition().y / 2,
            camera.position.z);
        this.camera.viewportHeight =
            Math.max(Globals.viewHeight, Math.abs(ballbod.getPosition().y) + 10);
        this.camera.viewportWidth =
            this.camera.viewportHeight * Globals.mainGame.getAspect();
        this.camera.update();

        if (this.ballbod.getPosition().x % 1000 > 900)
        {
            if (!loaded)
            {
                loaded = true;
                // Thread t = new
// Thread(Globals.loader.loadXMLURL((int)this.ballbod.getPosition());
                // Globals.loader.loadXMLURL((int)this.ballbod.getPosition().x +
// 200);
                Globals.loader.load((int)this.ballbod.getPosition().x + 200);
            }
        }
        else
        {
            loaded = false;
        }

        this.sprite
            .setRotation((float)(this.ballbod.getAngle() * 180 / Math.PI));
        this.sprite.setPosition(
            this.ballbod.getPosition().x - size.x / 2,
            this.ballbod.getPosition().y - size.y / 2);
        float rotspeed =
            1f - Math.min(
                Math.abs(this.ballbod.getAngularVelocity()) * 0.01f,
                1f);
        this.sprite.setColor(1f, rotspeed, rotspeed, 1f);
        this.ballSensor.setTransform(this.ballbod.getPosition(), 0f);
        if (Gdx.input.isTouched())
        {
            if (/* this.ballbod.linVelWorld.x < 0.3f && */overlapCount > 1 /*
                                                                            * &&
                                                                            * this
                                                                            * .
                                                                            * ballbod
                                                                            * .
                                                                            * getAngularVelocity
                                                                            * ()
                                                                            * >
                                                                            * -
                                                                            * Math
                                                                            * .
                                                                            * PI
                                                                            * /
                                                                            * 100f
                                                                            * m
                                                                            */)
            {
                // this.ballbod.applyForceToCenter(50f, 0f, true);
                // this.ballbod.setAngularVelocity(-5f);

                this.ballbod.applyAngularImpulse(
                    -0.5f * this.ballbod.getMass(),
                    true);
            }
            else
            {
                this.ballbod.applyForceToCenter(new Vector2(0.0f, -50f
                    * this.ballbod.getMass()), true);
            }
        }

    }


    public void Draw(SpriteBatch batch)
    {

        sprite.draw(batch);
    }


    class CollisionListener
        implements ContactListener
    {

        @Override
        public void beginContact(Contact contact)
        {
            if (contact.isTouching()
                && !ballbod.getFixtureList().contains(
                    contact.getFixtureA(),
                    true))
            {
                if(contact.getFixtureA().getBody().getUserData() instanceof Sprite)
                {
                    Sprite s = (Sprite)contact.getFixtureA().getBody().getUserData();
                    s.setColor(Color.YELLOW);
                    overlapCount++;
                }
                // touchingGround = true;

            }
        }


        @Override
        public void endContact(Contact contact)
        {
            overlapCount--;
        }


        @Override
        public void preSolve(Contact contact, Manifold oldManifold)
        {
            // TODO Auto-generated method stub

        }


        @Override
        public void postSolve(Contact contact, ContactImpulse impulse)
        {
            // TODO Auto-generated method stub

        }

    }


    public float getVelocity()
    {
        //System.out.println(this.ballbod.getLinearVelocity().len());
        return this.ballbod.getLinearVelocity().len();
    }
}
