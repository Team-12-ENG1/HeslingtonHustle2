package com.heshus.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;

public class Player extends Sprite implements InputProcessor {
    //movement velocity - Vector2 stores 2 values, for x and y
    private Vector2 velocity = new Vector2();
    private float speed = 200;
    boolean leftMove;
    boolean rightMove;
    boolean downMove;
    boolean upMove;
    private TiledMapTileLayer collisionLayer;

    /**
     * Instantiate Player object
     *
     * @param playerSprite   player sprite
     * @param collisionLayer the layer of the Tiled map where collision information is stored
     */
    public Player(Sprite playerSprite, TiledMapTileLayer collisionLayer) {
        //call super constructor - i.e. the constructor of the Sprite class, which takes the player sprite as an argument
        super(playerSprite);
        this.collisionLayer = collisionLayer;

    }

    /**
     * Return the layer of the tilemap where collision information is stored
     *
     * @return collisionLayer
     */
    public TiledMapTileLayer getCollisionLayer() {
        return collisionLayer;
    }

    /**
     * Draw is called to draw the Player to the screen
     *
     * @param spritebatch the Batch of the renderer responsible for drawing the Player
     */
    public void draw(Batch spritebatch) {
        //call the draw method of the parent class
        super.draw(spritebatch);
    }

    /**
     * Update is called once per frame
     *
     * @param delta the time since last update()
     */
    public void update(float delta) {
        // set velocity
        updateMotion();

        //**********************
        //  COLLISION DETECTION
        //**********************

        int cellX = (int) (getX() + (velocity.x * delta) + this.getWidth()/2)/16;
        int cellY = (int) (getY() + (velocity.y * delta) + this.getHeight()/2)/16;

        TiledMapTileLayer.Cell cell = collisionLayer.getCell(cellX, cellY);
        if (cell != null && !cell.getTile().getProperties().containsKey("collision")) {
            setX(getX() + velocity.x * delta);
            setY(getY() + velocity.y * delta);
        }

    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }

    public void updateMotion() {
        if (leftMove) {velocity.x = -speed;}
        else if (rightMove) {velocity.x = speed;}
        else {velocity.x = 0;}

        if (upMove) {velocity.y = speed;}
        else if (downMove) {velocity.y = -speed;}
        else {velocity.y = 0;}
    }

    public void setLeftMove(boolean t)
    {
        if(rightMove && t) rightMove = false;
        leftMove = t;
    }
    public void setRightMove(boolean t)
    {
        if(leftMove && t) leftMove = false;
        rightMove = t;
    }
    public void setUpMove(boolean t)
    {
        if(downMove && t) downMove = false;
        upMove = t;
    }
    public void setDownMove(boolean t)
    {
        if(upMove && t) upMove = false;
        downMove = t;
    }

    ////////////////////
    //INPUT HANDLING
    ///////////////////

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.W:
            case Input.Keys.UP:
                setUpMove(true);
                break;
            case Input.Keys.A:
            case Input.Keys.LEFT:
                setLeftMove(true);
                break;
            case Input.Keys.S:
            case Input.Keys.DOWN:
                setDownMove(true);
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                setRightMove(true);
                break;
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.W:
            case Input.Keys.UP:
                setUpMove(false);
                break;
            case Input.Keys.A:
            case Input.Keys.LEFT:
                setLeftMove(false);
                break;
            case Input.Keys.S:
            case Input.Keys.DOWN:
                setDownMove(false);
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                setRightMove(false);
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
}