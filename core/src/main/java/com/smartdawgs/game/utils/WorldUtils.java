package com.smartdawgs.game.utils;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.math.Vector2;

public class WorldUtils {

    public static Vector2 getPoint(MapLayer mapLayer, String pointName) {
        MapObjects points = mapLayer.getObjects();
        for (MapObject object : points) {
            if (!(object.getName() == null) && object.getName().equals(pointName)) {
                if (object.getProperties().containsKey("x") && object.getProperties().containsKey("y")) {
                    float x = object.getProperties().get("x", Float.class);
                    float y = object.getProperties().get("y", Float.class);
                    return new Vector2(x, y);
                }
            }
        }
        return new Vector2(0, 0);
    }

    public static MapObject getNearlyPoint(MapLayer mapLayer, float xDist, float yDist, float distance) {
        MapObjects points = mapLayer.getObjects();
        for (MapObject object : points) {
            if (object.getProperties().containsKey("x") && object.getProperties().containsKey("y")) {
                float x = object.getProperties().get("x", Float.class);
                float y = object.getProperties().get("y", Float.class);
                if(distanceBetweenPoints(x, y, xDist, yDist, distance)) {
                    return object;
                }
            }
        }
        return null;
    }

    public static boolean distanceBetweenPoints(float x1, float y1, float x2, float y2, float distance) {
        return Math.abs(x1 - x2) < distance || Math.abs(y1 - y2) < distance;
    }
}
