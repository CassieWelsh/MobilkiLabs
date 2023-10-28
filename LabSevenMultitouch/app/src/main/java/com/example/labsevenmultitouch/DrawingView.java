package com.example.labsevenmultitouch;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import java.util.HashMap;
import java.util.Map;

public class DrawingView extends View {
    private Map<Integer, Path> paths = new HashMap<>();
    private Map<Integer, Paint> paints = new HashMap();

    public DrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFocusable(true);
        setFocusableInTouchMode(true);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        for (int pointerId : paths.keySet()) {
            Path path = paths.get(pointerId);
            path.reset();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (Path path : paths.values()) {
            canvas.drawPath(path, paints.get(getPathId(path)));
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getActionMasked();
        int pointerIndex = event.getActionIndex();
        int pointerId = event.getPointerId(pointerIndex);

        switch (action) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                Path path = new Path();
                path.moveTo(event.getX(pointerIndex), event.getY(pointerIndex));
                paths.put(pointerId, path);

                Paint paint = new Paint();
                paint.setColor(getRandomColor());
                paint.setStyle(Paint.Style.STROKE);
                paint.setStrokeWidth(10);
                paints.put(pointerId, paint);
                break;

            case MotionEvent.ACTION_MOVE:
                for (int i = 0; i < event.getPointerCount(); i++) {
                    int id = event.getPointerId(i);
                    paths.get(id).lineTo(event.getX(i), event.getY(i));
                }
                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                paths.remove(pointerId);
                paints.remove(pointerId);
                break;
        }

        invalidate();
        return true;
    }

    private int getRandomColor() {
        return Color.rgb((int) (Math.random() * 256), (int) (Math.random() * 256), (int) (Math.random() * 256));
    }

    private int getPathId(Path path) {
        for (Map.Entry<Integer, Path> entry : paths.entrySet()) {
            if (entry.getValue() == path) {
                return entry.getKey();
            }
        }
        return -1;
    }
}

