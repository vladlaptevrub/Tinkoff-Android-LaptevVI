package fintech.tinkoff.fifthlab;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;

public class CustomGraphView extends View {
    private Paint mAxisPaint;
    private Paint mFuncPaint;
    private Paint mAxisName;
    private int parentWidth;
    private int parentHeight;

    private boolean isVisible = true;
    private float scaleProgress = 2.0f;
    private float scale = 120.0f;

    private String graphName = "Your graph";
    private String axisNameY = "Axis Y";
    private String axisNameX = "Axis X";

    private final static int SQ = 1;
    private final static int SQRT = 2;
    private final static int DIV = 3;
    private final static int COS = 4;
    private final static int SIN = 5;

    private final static int BLACK = 1;
    private final static int BLUE = 2;
    private final static int RED = 3;
    private final static int GREEN = 4;
    private final static int YELLOW = 5;

    private ArrayList<Func> funcList= new ArrayList<>();
    private int funcCount = 0;

    private int func = SIN;
    private int funcColor = 2;

    public CustomGraphView(Context context) {
        super(context);
    }

    public CustomGraphView(Context context, AttributeSet st) {
        super(context, st);
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
        float graphPadding = 60.0f;
        float graphWidth = (float)parentWidth - graphPadding;
        float graphHeight = (float)parentHeight - graphPadding * 2.0f;
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

        scale = 40.0f + scaleProgress * 5.0f;

        drawNet(graphPadding, graphWidth, graphHeight, scale, isVisible, canvas);
        drawArrows(graphPadding, graphWidth, graphHeight, canvas);

        for (Func function:funcList){
            func = function.getName();
            funcColor = function.getFuncColor();

            switch (func) {
                case SQRT:
                    drawSQRTFunc(graphPadding, graphWidth, graphHeight, scale, funcColor, canvas);
                    break;
                case SQ:
                    drawSQFunc(graphPadding, graphWidth, graphHeight, scale, funcColor, canvas);
                    break;
                case DIV:
                    drawDIVFunc(graphPadding, graphWidth, graphHeight, scale, funcColor, canvas);
                    break;
                case COS:
                    drawCOSFunc(graphPadding, graphWidth, graphHeight, scale, funcColor, canvas);
                    break;
                case SIN:
                    drawSINFunc(graphPadding, graphWidth, graphHeight, scale, funcColor, canvas);
                    break;
                default:
                    break;
            }
        }

        canvas.save();
        canvas.rotate(-90.0f);
        canvas.drawText(axisNameY, graphHeight / -2.0f - graphPadding,  60.0f, mAxisName);
        canvas.restore();
        canvas.drawText(axisNameX, graphWidth / 2.0f + graphPadding, graphHeight + 90.0f, mAxisName);
        canvas.drawText(graphName + ":", graphPadding, graphPadding / 1.5f, mAxisName);
    }

    public void addFunc(Func function){
        funcList.add(function);
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

    private void drawNet(float graphPadding, float graphWidth, float graphHeight, float step, boolean isVisible, Canvas canvas){
        int yPos = 1;
        int xPos = 1;
        final float limitStepY = graphPadding + 30.0f;
        final float limitStepX = graphWidth - 30.0f;

        //  Draw grid for Y
        if (isVisible) {
            for (float nStep = graphHeight - step; nStep > limitStepY; nStep -= step) {
                canvas.drawText(String.valueOf(yPos), graphPadding + 15.0f, nStep + 10.0f, mAxisPaint);
                canvas.drawLine(graphPadding * 2.0f - 15.0f, nStep, graphPadding * 2.0f, nStep, mAxisPaint);
                canvas.drawLine(graphPadding * 2.0f, nStep, graphWidth, nStep, mAxisName);
                yPos++;
            }

            //  Draw grid for X
            for (float nStep = graphPadding * 2.0f + step; nStep < limitStepX; nStep += step) {
                canvas.drawLine(nStep, graphHeight, nStep, graphHeight + 15.0f, mAxisPaint);
                canvas.drawLine(nStep, graphHeight, nStep, graphPadding, mAxisName);
                canvas.drawText(String.valueOf(xPos), nStep - 10.0f, graphHeight + 40.0f, mAxisPaint);
                xPos++;
            }
        } else {
            for (float nStep = graphHeight - step; nStep > limitStepY; nStep -= step) {
                canvas.drawText(String.valueOf(yPos), graphPadding + 15.0f, nStep + 10.0f, mAxisPaint);
                canvas.drawLine(graphPadding * 2.0f - 15.0f, nStep, graphPadding * 2.0f, nStep, mAxisPaint);
                yPos++;
            }

            //  Draw grid for X
            for (float nStep = graphPadding * 2.0f + step; nStep < limitStepX; nStep += step) {
                canvas.drawLine(nStep, graphHeight, nStep, graphHeight + 15.0f, mAxisPaint);
                canvas.drawText(String.valueOf(xPos), nStep - 10.0f, graphHeight + 40.0f, mAxisPaint);
                xPos++;
            }
        }
    }

    public void clear(){
        funcList.clear();
        invalidate();
    }

    private void drawSQRTFunc(float graphPadding, float graphWidth, float graphHeight, float step, int color, Canvas canvas){
        float oldY = 0.0f;
        float oldX = 0.0f;
        float newY = 0.0f;
        float newX = 0.0f;
        float limitX = (graphWidth - graphPadding * 2.0f) / step;

        setColor(color);

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

        canvas.drawText("y = sqrt(x)", newX * step - 30.0f, graphHeight - newY * step - 30.0f, mAxisName);
    }

    private void drawSQFunc(float graphPadding, float graphWidth, float graphHeight, float step, int color, Canvas canvas){
        float oldY = 0.0f;
        float oldX = 0.0f;
        float newY = 0.0f;
        float newX = 0.0f;
        float limitX = (graphWidth - graphPadding * 2.0f) / step;

        setColor(color);

        //  Draw function
        for(; newX < limitX; newX+= 0.1f){
            newY = newX*newX;

            if (graphHeight - newY * step < graphPadding) break;

            canvas.drawLine(graphPadding * 2.0f + oldX * step, graphHeight - oldY * step,
                    graphPadding * 2.0f + newX * step, graphHeight - newY * step, mFuncPaint);
            oldX = newX;
            oldY = newY;

        }

        canvas.drawText("y = x^2", newX * step + 100.0f, graphPadding + 20.0f, mAxisName);
    }

    private void drawDIVFunc(float graphPadding, float graphWidth, float graphHeight, float step, int color, Canvas canvas){
        float oldY = 0.0f;
        float oldX = 0.0f;
        float newY = 0.0f;
        float newX = 0.01f;
        float limitX = (graphWidth - graphPadding * 2.0f) / step;

        oldX = newX;
        oldY = 1.0f/newX;
        newX += 0.01f;

        setColor(color);

        //  Draw function
        for(; newX < limitX; newX+= 0.01f){
            newY = 1/newX;

            canvas.drawLine(graphPadding * 2.0f + oldX * step, graphHeight - oldY * step,
                    graphPadding * 2.0f + newX * step, graphHeight - newY * step, mFuncPaint);
            oldX = newX;
            oldY = newY;

            canvas.drawText("y = 1/x", 2.0f * step + 100.0f, graphHeight - 0.5f * step, mAxisName);
        }
    }

    private void drawCOSFunc(float graphPadding, float graphWidth, float graphHeight, float step, int color, Canvas canvas){
        float oldY = 1.0f;
        float oldX = 0.0f;
        float newY = 0.0f;
        float newX = 0.0f;
        float limitX = (graphWidth - graphPadding * 2.0f) / step;

        setColor(color);

        //  Draw function
        for(; newX < limitX; newX+= 0.1f){
            newY = (float)Math.cos((double)newX);

            if (graphHeight - newY * step < graphPadding) break;

            canvas.drawLine(graphPadding * 2.0f + oldX * step, graphHeight - oldY * step,
                    graphPadding * 2.0f + newX * step, graphHeight - newY * step, mFuncPaint);
            oldX = newX;
            oldY = newY;
        }

        canvas.drawText("y = cos(x)", newX * step, graphHeight - newY * step, mAxisName);
    }

    private void drawSINFunc(float graphPadding, float graphWidth, float graphHeight, float step, int color, Canvas canvas){
        float oldY = 0.0f;
        float oldX = 0.0f;
        float newY = 0.0f;
        float newX = 0.0f;
        float limitX = (graphWidth - graphPadding * 2.0f) / step;

        setColor(color);

        //  Draw function
        for(; newX < limitX; newX+= 0.1f){
            newY = (float)Math.sin((double)newX);

            if (graphHeight - newY * step < graphPadding) break;

            canvas.drawLine(graphPadding * 2.0f + oldX * step, graphHeight - oldY * step,
                    graphPadding * 2.0f + newX * step, graphHeight - newY * step, mFuncPaint);
            oldX = newX;
            oldY = newY;
        }

        canvas.drawText("y = sin(x)", newX * step, graphHeight - newY * step, mAxisName);
    }

    private void setColor(int color){
        switch (color){
            case BLACK:
                mFuncPaint.setColor(Color.BLACK);
                break;
            case BLUE:
                mFuncPaint.setColor(Color.BLUE);
                break;
            case RED:
                mFuncPaint.setColor(Color.RED);
                break;
            case YELLOW:
                mFuncPaint.setColor(Color.YELLOW);
                break;
            case GREEN:
                mFuncPaint.setColor(Color.GREEN);
                break;
            default:
                mFuncPaint.setColor(Color.BLUE);
                break;
        }
    }

    public void setVisible(boolean bool){
        isVisible = bool;
    }

    public void setScaleProgress(float progress){
        scaleProgress = progress;
    }

    public void setGraphName(String name){
        graphName = name;
    }

    public void setAxisNameY(String name){
        axisNameY = name;
    }

    public void setAxisNameX(String name){
        axisNameX = name;
    }
}
