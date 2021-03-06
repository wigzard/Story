package com.story.scene.components.helpers;

import com.story.scene.components.descriptors.ViewerDescriptor;
import com.story.system.IDisposable;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by alex on 15.07.16.
 * Calculate values for component of map
 */
public class MapViewer implements IDisposable {
    private static final int DefaultSpeed = 15;

    /**
     * Descriptor of viewer
     */
    private ViewerDescriptor descriptor;
    /**
     * Coordinates of left up point
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
     * Convert current coordinate to global and return they
     * @param isRemoveElement if true, then element removing from queue, else just return
     * @return the point of global coordinates
     */
    public Point getGlobalCoordinates(boolean isRemoveElement) {
        Point result;
        if (this.frames.size() == 0){
            result = this.convertToGlobal(this.currentPosition);
        }
        else{
            result = (isRemoveElement)? this.frames.poll() : this.frames.peek();
        }

        return result;
    }

    /**
     * Check when object with coordinates, is visible in the viewer
     * @param tilePoint the object coordinates
     * @return true, when coordinates can be displayed on viewer
     */
    public boolean isVisibleOnViewer(Point tilePoint){
        return CoordinateCalculator.isVisibleOnViewer(tilePoint,
                this.descriptor.getTileSize(),
                CoordinateCalculator.convertToGlobal(
                        new Point(-this.currentPosition.x, -this.currentPosition.y),
                        this.descriptor.getTileSize()));
    }

    /**
     * Method which converting the map coordinates to global coordinates
     * @param tilePoint the tile point on map
     * @return global coordinates
     */
    private Point convertToGlobal(Point tilePoint){
        return CoordinateCalculator.convertToGlobal(tilePoint, this.descriptor.getTileSize());
    }

    /**
     * Calculate center coordinates of viewer
     * @return global point
     */
    public Point getViewerCenterPosition(){
        return this.convertToGlobal(new Point(
                this.descriptor.getScreenSizeAsTiles().getWidth() / 2,
                this.descriptor.getScreenSizeAsTiles().getHeight() / 2
        ));
    }

    /**
     * Gets count of frames in queue
     * @return count of frames
     */
    public int getCountFrames(){
        return this.frames == null? 0: this.frames.size();
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
