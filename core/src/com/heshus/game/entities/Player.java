package com.heshus.game.entities;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;

/**
 * The Player class handles everything related to the player, from setting the player's avatar
 * to handling their movement. It implements {@link InputProcessor} as well as extending {@link Sprite}
 * in order to implement the former methods.
 */
public class Player extends Sprite implements InputProcessor {
    //movement velocity - Vector2 stores 2 values, for x and y
    private Vector2 velocity = new Vector2();
    boolean leftMove;
    boolean rightMove;
    boolean downMove;
    boolean upMove;
    private final TiledMapTileLayer collisionLayer;

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

        //  COLLISION DETECTION
        int cellX = (int) (getX() + (velocity.x * delta) + this.getWidth()/2)/16;
        int cellY = (int) (getY() + (velocity.y * delta) + this.getHeight()/2)/16;

        TiledMapTileLayer.Cell cell = collisionLayer.getCell(cellX, cellY);
        if (cell == null || !cell.getTile().getProperties().containsKey("collision")) {
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

    /**
     * Update the players motion (velocity) depending on their movement direction
     */
    public void updateMotion() {
        float speed = 200;
        float xDelta = 0;
        float yDelta = 0;


        if (leftMove) {xDelta += -speed;}
        else if (rightMove) {xDelta += speed;}
        else {xDelta = 0;}

        if (upMove) {yDelta += speed;}
        else if (downMove) {yDelta += -speed;}
        else {yDelta = 0;}
        if(xDelta != 0 && yDelta !=0){
            xDelta /= (float)Math.sqrt(2);
            yDelta /= (float)Math.sqrt(2);
        }
        velocity.x = xDelta;
        velocity.y = yDelta;
    }

    /**
     * Set the player's movement direction to left, cancelling the right movement if true
     * @param t player's moving left state
     */
    public void setLeftMove(boolean t)
    {
        if(rightMove && t) rightMove = false;
        leftMove = t;
    }

    /**
     * Set the player's movement direction to right, cancelling the left movement if true
     * @param t player's moving right state
     */
    public void setRightMove(boolean t)
    {
        if(leftMove && t) leftMove = false;
        rightMove = t;
    }

    /**
     * Set the player's movement direction to up, cancelling the down movement if true
     * @param t player's moving up state
     */
    public void setUpMove(boolean t)
    {
        if(downMove && t) downMove = false;
        upMove = t;
    }
    /**
     * Set the player's movement direction to down, cancelling the up movement if true
     * @param t player's moving down state
     */
    public void setDownMove(boolean t)
    {
        if(upMove && t) upMove = false;
        downMove = t;
    }

    ////////////////////
    //INPUT HANDLING
    ///////////////////

    /**
     * This sets a players movement when a key is pressed down
     *
     * @param keycode The keyboard key inputted
     * @return true
     */
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

    /**
     * This sets a players movement when a key is pressed down
     *
     * @param keycode The keyboard key inputted
     * @return true
     */
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