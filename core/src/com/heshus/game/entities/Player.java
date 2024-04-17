package com.heshus.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.heshus.game.manager.DayManager;

public class Player extends Sprite implements InputProcessor {
    //movement velocity - Vector2 stores 2 values, for x and y
    private Vector2 velocity = new Vector2();
    private float speed = 200;

    private TiledMapTileLayer collisionLayer;

    /**
     * Instantiate Player object
     * @param playerSprite player sprite
     * @param collisionLayer the layer of the Tiled map where collision information is stored
     */
    public Player(Sprite playerSprite, TiledMapTileLayer collisionLayer) {
        //call super constructor - i.e. the constructor of the Sprite class, which takes the player sprite as an argument
        super(playerSprite);
        this.collisionLayer = collisionLayer;

    }

    /**
     * Return the layer of the tilemap where collision information is stored
     * @return collisionLayer
     */
    public TiledMapTileLayer getCollisionLayer() {
        return collisionLayer;
    }

    /**
     * Draw is called to draw the Player to the screen
     * @param spritebatch the Batch of the renderer responsible for drawing the Player
     */
    public void draw(Batch spritebatch) {
        //call the draw method of the parent class
        super.draw(spritebatch);
    }

    /**
     * Update is called once per frame
     * @param delta the time since last update()
     */
    public void update(float delta) {

        //**********************
        //  COLLISION DETECTION
        //**********************

        //save old x,y positions
        float oldX = getX();
        float oldY = getY();

        //variables to say if we're colliding with something
        boolean collision = true;

        //map tile properties
        float tileWidth = collisionLayer.getTileWidth();
        float tileHeight = collisionLayer.getTileHeight();

        // determine new position
        float newX = oldX + velocity.x * delta;
        float newY = oldY + velocity.y * delta;

        int cellX = (int) (Math.floor(newX + this.getWidth()/2) / tileWidth);
        int cellY = (int) (Math.floor(newY + this.getHeight()/2) / tileHeight);

        TiledMapTileLayer.Cell cell;
        // convert the coordinates into cell number, bottom left is 0, 0
        try {
            cell = collisionLayer.getCell(cellX, cellY);
            // try upper bound
            if (cell.getTile().getProperties().containsKey("collision")) {
                collision = true;
            } else { collision = false; }
        } catch (Exception e) {
            // Catch the null exception - no tile exists there
            collision = false;
        }

        // don't move player
        if (collision) {
            velocity.x = 0;
            velocity.y = 0;
        } else {
            setX(newX);
            setY(newY);
        }
    }

    ////////////////////
    //INPUT HANDLING
    ///////////////////

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.W:
                velocity.y = speed;
                break;
            case Input.Keys.A:
                velocity.x = -speed;
                break;
            case Input.Keys.S:
                velocity.y = -speed;
                break;
            case Input.Keys.D:
                velocity.x = speed;
                break;
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
            case Input.Keys.D:
                velocity.x = 0;
                break;
            case Input.Keys.W:
            case Input.Keys.S:
                velocity.y = 0;
                break;
        }
        return true;
    }

    //DON'T NEED ANY OF THE REST OF THESE METHODS

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean mouseMoved (int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float a, float b) {
        return false;
    }
    public Vector2 getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }

}