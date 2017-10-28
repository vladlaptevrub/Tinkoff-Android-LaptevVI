package fintech.tinkoff.fifthlab;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class CustomGraphView extends View {
    private Paint mAxisPaint;
    private Paint mFuncPaint;
    private Paint mAxisName;
    private int parentWidth;
    private int parentHeight;

    public CustomGraphView(Context context) {
        super(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        parentWidth = MeasureSpec.getSize(widthMeasureSpec);
        parentHeight = MeasureSpec.getSize(heightMeasureSpec);
        this.setMeasuredDimension(parentWidth/2, parentHeight);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float graphPadding = 80.0f;
        float graphWidth = (float)parentWidth - graphPadding;
        float graphHeight = (float)parentHeight / 2.0f - graphPadding;
        super.onDraw(canvas);

        mAxisPaint = new Paint();
        mAxisPaint.setStrokeWidth(3);
        mAxisPaint.setColor(Color.BLACK);
        mAxisPaint.setTextSize(25.0f);
        mAxisPaint.setFakeBoldText(true);

        mAxisName = new Paint();
        mAxisName.setStrokeWidth(2);
        mAxisName.setTextSize(30.0f);
        mAxisName.setColor(Color.GRAY);
        mAxisName.setFakeBoldText(true);

        mFuncPaint = new Paint();
        mFuncPaint.setColor(Color.BLUE);
        mFuncPaint.setStrokeWidth(3);

        canvas.save();
        canvas.rotate(-90.0f);
        canvas.drawText("Axis Y", graphHeight / -2.0f - graphPadding,  60.0f, mAxisName);
        canvas.restore();

        canvas.drawText("Axis X", graphWidth / 2.0f + graphPadding, graphHeight + 90.0f, mAxisName);

        drawNet(graphPadding, graphWidth, graphHeight, 50.0f, canvas);
        drawArrows(graphPadding, graphWidth, graphHeight, canvas);
        drawFunc(graphPadding, graphWidth, graphHeight, 50.0f, canvas);
    }

    private void drawArrows(float graphPadding, float graphWidth, float graphHeight, Canvas canvas){

        //  Draw arrow Y
        canvas.drawLine(graphPadding * 2.0f, graphPadding, graphPadding * 2.0f, graphHeight, mAxisPaint);
        canvas.drawLine(graphPadding  * 2.0f, graphPadding, graphPadding  * 2.0f - 10.0f, graphPadding + 20.0f, mAxisPaint);
        canvas.drawLine(graphPadding  * 2.0f, graphPadding, graphPadding  * 2.0f + 10.0f, graphPadding + 20.0f, mAxisPaint);

        //  Draw arrow X
        canvas.drawLine(graphPadding  * 2.0f, graphHeight, graphWidth, graphHeight, mAxisPaint);
        canvas.drawLine(graphWidth, graphHeight, graphWidth - 20.0f, graphHeight + 10.0f, mAxisPaint);
        canvas.drawLine(graphWidth, graphHeight, graphWidth - 20.0f, graphHeight - 10.0f, mAxisPaint);
    }

    private void drawNet(float graphPadding, float graphWidth, float graphHeight, float step, Canvas canvas){
        int yPos = 1;
        int xPos = 1;
        final float limitStepY = graphPadding + 30.0f;
        final float limitStepX = graphWidth - 30.0f;

        //  Draw grid for Y
        for (float nStep = graphHeight - step; nStep > limitStepY; nStep -= step){
            canvas.drawText(String.valueOf(yPos), graphPadding + 20.0f, nStep + 10.0f, mAxisPaint);
            canvas.drawLine(graphPadding * 2.0f - 15.0f, nStep, graphPadding * 2.0f, nStep, mAxisPaint);
            canvas.drawLine(graphPadding * 2.0f, nStep, graphWidth, nStep, mAxisName);
            yPos++;
        }

        //  Draw grid for X
        for (float nStep = graphPadding * 2.0f + step; nStep < limitStepX; nStep += step){
            canvas.drawLine(nStep, graphHeight, nStep, graphHeight + 15.0f, mAxisPaint);
            canvas.drawLine(nStep, graphHeight, nStep, graphPadding, mAxisName);
            canvas.drawText(String.valueOf(xPos), nStep - 10.0f, graphHeight + 40.0f, mAxisPaint);
            xPos++;
        }
    }

    private void drawFunc(float graphPadding, float graphWidth, float graphHeight, float step, Canvas canvas){
        float oldY = 0.0f;
        float oldX = 0.0f;
        float newY = 0.0f;
        float newX = 0.0f;
        float limitX = (graphWidth - graphPadding * 2.0f) / step;

        //  Draw function
        for(; newX < limitX; newX+= 0.1f){
            //newY = newX*newX;
            newY = (float)Math.sqrt((double)newX);

            if (graphHeight - newY * step < graphPadding) break;

            canvas.drawLine(graphPadding * 2.0f + oldX * step, graphHeight - oldY * step,
                    graphPadding * 2.0f + newX * step, graphHeight - newY * step, mFuncPaint);
            oldX = newX;
            oldY = newY;

        }
    }
}
