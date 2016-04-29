package com.story.core;

import java.awt.*;

/**
 * Created by alex on 29.04.16.
 */
public class Converter {

    /**
     *
     * @param object coordinates of object
     * @param tileWidth tile width
     * @param tileHeight tile height
     * @param margin offset from the beginning                           
     * @return map coordinates for object
     */
    public static Point ObjectPositionToMapPosition(Point object, int tileWidth, int tileHeight, int margin){
        object.x += margin;
        object.y += margin;

        Point temp = new Point(((object.x * tileWidth - tileWidth / 2) - GlobalVar.Width / 2) / tileWidth,
                ((object.y * tileHeight - tileHeight / 2) - GlobalVar.Height / 2) / tileHeight);

        temp.x *= tileWidth;
        temp.y *= tileHeight;

        return temp;
    }
}
