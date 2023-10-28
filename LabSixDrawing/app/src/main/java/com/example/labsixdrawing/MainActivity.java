package com.example.labsixdrawing;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new DrawView(this));
    }

    class DrawView extends View {
        Paint p;
        Rect rect;
        StringBuilder sb;
        Path hexagonPath;
        Path starPath;

        public DrawView(Context context) {
            super(context);
            p = new Paint();
            rect = new Rect(100, 200, 200, 300);
            sb = new StringBuilder();
            hexagonPath = new Path();
            starPath = new Path();
        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawARGB(80, 102, 204, 255);
            p.setColor(Color.BLUE);
            p.setStrokeWidth(10);
            p.setTextSize(30);

            hexagonPath.reset();
            float hexagonCenterX = canvas.getWidth() / 4;
            float hexagonCenterY = canvas.getHeight() / 2;
            float hexagonRadius = 100;

            for (int i = 0; i < 6; i++) {
                float angle = (float) (2 * Math.PI / 6 * i);
                float x = hexagonCenterX + hexagonRadius * (float) Math.cos(angle);
                float y = hexagonCenterY + hexagonRadius * (float) Math.sin(angle);

                if (i == 0) {
                    hexagonPath.moveTo(x, y);
                } else {
                    hexagonPath.lineTo(x, y);
                }
            }
            hexagonPath.close();
            canvas.drawPath(hexagonPath, p);

            starPath.reset();
            float starCenterX = canvas.getWidth() * 3 / 4;
            float starCenterY = canvas.getHeight() / 2;
            float starRadius = 100;
            float angleDeg = 72;

            for (int i = 0; i < 5; i++) {
                float outerAngle = (float) Math.toRadians(i * angleDeg);
                float innerAngle = (float) Math.toRadians((i * angleDeg) + angleDeg / 2);

                float outerX = starCenterX + starRadius * (float) Math.cos(outerAngle);
                float outerY = starCenterY + starRadius * (float) Math.sin(outerAngle);
                float innerX = starCenterX + (starRadius / 2) * (float) Math.cos(innerAngle);
                float innerY = starCenterY + (starRadius / 2) * (float) Math.sin(innerAngle);

                if (i == 0) {
                    starPath.moveTo(outerX, outerY);
                } else {
                    starPath.lineTo(outerX, outerY);
                }

                starPath.lineTo(innerX, innerY);
            }
            starPath.close();
            canvas.drawPath(starPath, p);

            // Create a string with the canvas size
            //sb.setLength(0);
            //sb.append("width=").append(canvas.getWidth()).append(" height=").append(canvas.getHeight());
            canvas.drawText(sb.toString(), 100, 100, p);
        }
    }
}
