package com.story.scene.components.helpers;

import com.story.system.IDisposable;
import com.story.utils.Size;

import java.awt.*;
import java.util.*;

/**
 * Created by alex on 15.07.16.
 * Calculate values for component of map
 */
public class MapViewer implements IDisposable {
    private static final int DefaultSpeed = 10;

    /**
     * Descriptor of viewer
     */
    private ViewerDescriptor descriptor;
    /**
     * Position on map
     */
    private Point currentPosition;

    /**
     * The speed of moved
     */
    private int speed;
    /**
     * Frames for render
     */
    private Queue<Point> frames;

    /**
     * Initialize new instance of MapViewer
     * @param descriptor the descriptor of viewer
     */
    public MapViewer(ViewerDescriptor descriptor){
        if (descriptor == null){
            throw new IllegalArgumentException("Descriptor can't be null");
        }

        this.setCurrentPosition(new Point(-descriptor.getStartCoordinates().x, - descriptor.getStartCoordinates().y));
        this.descriptor = descriptor;
        this.speed = DefaultSpeed;
        this.frames = new LinkedList<>();
    }

    /**
     * Set current position on the tile map
     * @param point the point on map
     */
    private void setCurrentPosition(Point point){
        this.currentPosition = point;
    }

    /**
     * Move current position to selected point. Create frames for animate move.
     * @param tilePoint destination point                   
     */
    private void moveTo(Point tilePoint){
        if ((this.currentPosition.equals(tilePoint)) || (frames.size() > 0)){
            return;
        }

        Point startGlobalCoordinates = this.convertToGlobal(this.currentPosition);
        if (this.currentPosition.x - tilePoint.x != 0) {
            int diff = Math.abs(this.currentPosition.x - tilePoint.x);
            int countSteps = this.speed * diff;
            int stepLength = this.descriptor.getTileSize().getWidth() * diff / this.speed;
            int direction = this.currentPosition.x - tilePoint.x > 0 ? -1 : 1;

            this.createSteps(startGlobalCoordinates.x,
                    diff,
                    countSteps,
                    stepLength,
                    direction,
                    this.descriptor.getTileSize().getWidth()
            ).forEach(value -> this.frames.add(new Point(value, startGlobalCoordinates.y)));
        }

        if (this.currentPosition.y - tilePoint.y != 0){
            int diff = Math.abs(this.currentPosition.y - tilePoint.y);
            int countSteps = this.speed * diff;
            int stepLength = this.descriptor.getTileSize().getHeight() * diff / this.speed;
            int direction = this.currentPosition.y - tilePoint.y > 0 ? -1 : 1;

            this.createSteps(startGlobalCoordinates.y,
                    diff,
                    countSteps,
                    stepLength,
                    direction,
                    this.descriptor.getTileSize().getHeight()
            ).forEach(value -> this.frames.add(new Point(startGlobalCoordinates.x, value)));
        }

        this.setCurrentPosition(tilePoint);
    }

    /**
     * Generate steps for move between start point and ed point
     * @param startPoint start point for calculate, should have GLOBAL coordinate, not tile
     * @param difference the difference between start point and end point
     * @param countSteps the count steps for move to end point
     * @param stepLength the length of a step
     * @param direction direction of movement viewer
     * @param tileSize the size of tile
     * @return the array of steps
     */
    private ArrayList<Integer> createSteps(int startPoint,
                              int difference,
                              int countSteps,
                              int stepLength,
                              int direction,
                              int tileSize){
        ArrayList<Integer> stepPoints = new ArrayList<>();
        int currentStep = startPoint;
        for (int i = 0; i < countSteps; i++){
            currentStep += stepLength * direction;
            stepPoints.add(currentStep);
        }

        stepPoints.add(startPoint + difference * tileSize * direction);
        return stepPoints;
    }

    /**
     * Move viewer to right on one point(right tile)
     */
    public void moveRight(){
        this.moveTo(new Point(this.currentPosition.x - 1, this.currentPosition.y));
    }

    /**
     * Move viewer to left on one point(left tile)
     */
    public void moveLeft(){
        this.moveTo(new Point(this.currentPosition.x + 1, this.currentPosition.y));
    }

    /**
     * Move viewer to up on one point(up tile)
     */
    public void moveUp(){
        this.moveTo(new Point(this.currentPosition.x, this.currentPosition.y + 1));
    }

    /**
     * Move viewer to down on one point(down tile)
     */
    public void moveDown(){
        this.moveTo(new Point(this.currentPosition.x, this.currentPosition.y - 1));
    }

    /**
     * Convert current coordinate to global and return him
     * @return the point of global coordinates
     */
    public Point getGlobalCoordinates() {
        Point result;
        if (this.frames.size() == 0){
            result = this.convertToGlobal(currentPosition);
        }
        else{
            result = this.frames.poll();
        }

        return result;
    }

    /**
     * Check when object with coordinates, is visible in the viewer
     * @param coordinate the object coordinates
     * @return true, when coordinates is displayed on viewer
     */
    public boolean isVisibleOnViewer(Point coordinate){
        if (coordinate == null){
            throw new IllegalArgumentException("Coordinates can't be bull");
        }

        Point globalCoordinates = this.convertToGlobal(coordinate);
        Size globalSizeMap = new Size(this.descriptor.getMapSize().getWidth() * this.descriptor.getTileSize().getWidth(),
                coordinate.y * this.descriptor.getTileSize().getHeight() * this.descriptor.getTileSize().getHeight());

        //check point location on the map
        if (globalCoordinates.x > globalSizeMap.getWidth()
                || globalCoordinates.y > globalSizeMap.getHeight()){
            return false;
        }

        Point coordinateReference;
        if ((this.frames != null) && (this.frames.size() > 0)){
            coordinateReference = new Point(
                    this.frames.element().x / this.descriptor.getTileSize().getWidth(),
                    this.frames.element().y / this.descriptor.getTileSize().getHeight());

        }
        else {
            coordinateReference = new Point(this.currentPosition);
        }

        return coordinate.x >= coordinateReference.x
                && coordinate.x <= coordinateReference.x + this.descriptor.getScreenSizeAsTiles().getWidth() + 1
                && coordinate.y >= coordinateReference.y
                && coordinate.y <= coordinateReference.y + this.descriptor.getScreenSizeAsTiles().getHeight() + 1;
    }

    /**
     * Method which converting the map coordinates to global coordinates
     * @param mapCoordinate the coordinates of map
     * @return global coordinates
     */
    private Point convertToGlobal(Point mapCoordinate){
        return new Point(mapCoordinate.x * this.descriptor.getTileSize().getWidth(),
                mapCoordinate.y * this.descriptor.getTileSize().getWidth());
    }

    @Override
    public void dispose() {
        if (this.descriptor != null){
            this.descriptor.dispose();
        }

        this.descriptor = null;
        this.currentPosition = null;
        this.frames = null;
    }
}
