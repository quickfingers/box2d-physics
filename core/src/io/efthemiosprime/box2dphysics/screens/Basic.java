package io.efthemiosprime.box2dphysics.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import io.efthemiosprime.box2dphysics.Box2dPhysics;

public class Basic implements Screen {

    private Box2dPhysics bx2d;

    Texture img;
    Sprite sprite;
    World world;
    Body spriteBody;
    Body bodyEdgeScreen;

    OrthographicCamera camera;
    private Box2DDebugRenderer b2dr;

    final float PIXELS_TO_METERS= 1f;
    final short PHYSICS_ENTITY = 0x1;   // 0001
    final short WORLD_ENTITY = 0x1 << 1;    // 0010 or 0x2 in hex


    public Basic(Box2dPhysics bx2d) {
        this.bx2d = bx2d;
    }

    @Override
    public void show() {
        img = new Texture("rusty_ball.png");
        sprite = new Sprite(img);
        sprite.setPosition(-sprite.getWidth()/2 ,-sprite.getHeight()/2);

        world = new World(new Vector2(0, -80f), true);
        b2dr = new Box2DDebugRenderer();
        camera = new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());


        BodyDef bodyEdgeDef = new BodyDef();
        bodyEdgeDef.type = BodyDef.BodyType.StaticBody;

        float w = Gdx.graphics.getWidth()/PIXELS_TO_METERS;
        float h =  Gdx.graphics.getHeight()/PIXELS_TO_METERS;

        bodyEdgeDef.position.set(0, 0);



        FixtureDef edgeFixture = new FixtureDef();
        edgeFixture.filter.categoryBits = WORLD_ENTITY;
        edgeFixture.filter.maskBits = PHYSICS_ENTITY;

        EdgeShape edgeShape = new EdgeShape();
        edgeShape.set(-w / 2, -h / 2, w / 2, -h / 2);
        edgeFixture.shape = edgeShape;

        bodyEdgeScreen = world.createBody(bodyEdgeDef);
        bodyEdgeScreen.createFixture(edgeFixture);

        edgeShape.dispose();

        // sprite
        BodyDef spriteDef = new BodyDef();
        spriteDef.type = BodyDef.BodyType.DynamicBody;
        spriteDef.position.set((sprite.getX() + sprite.getWidth() / 2) / PIXELS_TO_METERS, (sprite.getY() + sprite.getHeight() / 2) / PIXELS_TO_METERS);

        spriteBody = world.createBody(spriteDef);


        CircleShape spiteShape = new CircleShape();
        spiteShape.setRadius(sprite.getWidth()/2 / PIXELS_TO_METERS);


        FixtureDef spriteFixture = new FixtureDef();
        spriteFixture.shape = spiteShape;
        spriteFixture.density = 0.1f;
        spriteFixture.restitution = 0.9f;
        spriteFixture.filter.categoryBits = PHYSICS_ENTITY;
        spriteFixture.filter.maskBits = WORLD_ENTITY;

        spriteBody.createFixture(spriteFixture);
    }

    @Override
    public void render(float delta) {
        camera.update();
        // Step the physics simulation forward at a rate of 60hz, un-pair this later
        world.step(1f / 60f, 6, 2);

        sprite.setPosition(spriteBody.getPosition().x - sprite.getWidth()/2,spriteBody.getPosition().y - sprite.getHeight()/2);
        sprite.setRotation((float)Math.toDegrees(spriteBody.getAngle()));

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        b2dr.render(world, camera.combined);

        bx2d.batch.setProjectionMatrix(camera.combined);
        bx2d.batch.begin();
        bx2d.batch.draw(sprite, sprite.getX(), sprite.getY(),sprite.getOriginX(),
                sprite.getOriginY(),
                sprite.getWidth(),sprite.getHeight(),sprite.getScaleX(),sprite.
                        getScaleY(),sprite.getRotation());


        bx2d.batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        img.dispose();
        world.dispose();
    }
}
