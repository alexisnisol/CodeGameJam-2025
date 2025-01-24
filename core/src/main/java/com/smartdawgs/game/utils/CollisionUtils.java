package com.smartdawgs.game.utils;

import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.smartdawgs.game.entity.EntityPlayer;

public class CollisionUtils {

    public static void checkRectangleCollision(RectangleMapObject object, EntityPlayer player, float oldX, float oldY) {
        Rectangle obstacleRectangle = object.getRectangle();

        // Récupération du rectangle du joueur
        Rectangle playerRectangle = player.getPlayerRect();

        // Vérification de l'intersection entre les deux rectangles
        if (Intersector.overlaps(playerRectangle, obstacleRectangle)) {
            // Calcul des chevauchements sur chaque axe
            float overlapX = Math.min(
                playerRectangle.x + playerRectangle.width,
                obstacleRectangle.x + obstacleRectangle.width
            ) - Math.max(playerRectangle.x, obstacleRectangle.x);

            float overlapY = Math.min(
                playerRectangle.y + playerRectangle.height,
                obstacleRectangle.y + obstacleRectangle.height
            ) - Math.max(playerRectangle.y, obstacleRectangle.y);

            // Comparaison des chevauchements
            if (overlapX < overlapY) {
                // Collision principalement sur l'axe X
                if (playerRectangle.x < obstacleRectangle.x) {
                    player.setX(oldX - 0.08f);
                } else {
                    player.setX(oldX + 0.08f);
                }
            } else {
                // Collision principalement sur l'axe Y
                if (playerRectangle.y < obstacleRectangle.y) {
                    player.setY(oldY - 0.08f);
                } else {
                    player.setY(oldY + 0.08f);
                }
            }
        }
    }

    public static void checkPolygonRectangle(PolygonMapObject object, EntityPlayer player, float oldX, float oldY) {
        Polygon polygon = object.getPolygon();

        Polygon playerPolygon = rectangleToPolygon(player.getPlayerRect());

        // Vérifier la collision entre le polygone du joueur et celui de l'objet
        if (Intersector.overlapConvexPolygons(playerPolygon, polygon)) {
            player.setX(oldX); // Revenir à l'ancienne position
            player.setY(oldY);
        }
    }

    public static Polygon rectangleToPolygon(Rectangle rectangle) {
        float[] vertices = new float[]{
            rectangle.x, rectangle.y,  // Bottom-left
            rectangle.x + rectangle.width, rectangle.y,  // Bottom-right
            rectangle.x + rectangle.width, rectangle.y + rectangle.height,  // Top-right
            rectangle.x, rectangle.y + rectangle.height  // Top-left
        };
        return new Polygon(vertices);
    }

}
