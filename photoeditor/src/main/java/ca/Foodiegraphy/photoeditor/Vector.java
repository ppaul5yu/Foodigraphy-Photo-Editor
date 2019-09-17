package ca.Foodiegraphy.photoeditor;

import android.graphics.PointF;

class Vector extends PointF {

    Vector() {
        super();
    }

    public Vector(float x, float y) {
        super(x, y);
    }

    static float getAngle(Vector vector1, Vector vector2) {
        vector1.normalize();
        vector2.normalize();
        double degrees = (180.0 / Math.PI) * (Math.atan2(vector2.y, vector2.x) - Math.atan2(vector1.y, vector1.x));
        return (float) degrees;
    }

    private void normalize() {
        float length = (float) Math.sqrt(x * x + y * y);
        x /= length;
        y /= length;
    }
}