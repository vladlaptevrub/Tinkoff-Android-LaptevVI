package fintech.tinkoff.fifthlab;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;

public class CustomGraphView extends View {
    private Paint mAxisPaint;
    private Paint mFuncPaint;
    private Paint mAxisName;
    private Paint mFuncLimit;
    private Path path;
    private int parentWidth;
    private int parentHeight;

    private boolean isVisible = true;
    private float scaleProgress = 16.0f;
    private float scale = 120.0f;
    private float startX = 0.0f;
    private float lastX = 25.0f;
    private float gridStep = 1.0f;
    private boolean isLimited = false;

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
    private ArrayList<Curve> curveList= new ArrayList<>();

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

        path = new Path();
        path.rLineTo(0, graphHeight + graphPadding * 2.0f);

        mAxisPaint = new Paint();
        mAxisPaint.setStrokeWidth(3);
        mAxisPaint.setColor(Color.BLACK);
        mAxisPaint.setTextSize(20.0f);
        mAxisPaint.setFakeBoldText(true);

        mAxisName = new Paint();
        mAxisName.setStrokeWidth(2);
        mAxisName.setTextSize(20.0f);
        mAxisName.setColor(Color.GRAY);
        mAxisName.setFakeBoldText(true);

        mFuncPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mFuncPaint.setStyle(Paint.Style.STROKE);
        mFuncPaint.setColor(Color.BLUE);
        mFuncPaint.setStrokeWidth(3);

        mFuncLimit = new Paint();
        mFuncLimit.setStyle(Paint.Style.STROKE);
        mFuncLimit.setColor(Color.RED);
        mFuncLimit.setStrokeWidth(3);
        mFuncLimit.setPathEffect(new DashPathEffect(new float[] { 30.0f, 15.0f}, 0));

        scale = 40.0f + scaleProgress * 5.0f;
        //lastX = (graphWidth - graphPadding * 2.0f) / scale;

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

        for (Curve customCurve:curveList){
            drawCurve(customCurve, graphPadding, graphWidth, graphHeight, scale, canvas);
        }

        if (isLimited){
            drawLimit(graphPadding, graphWidth, graphHeight, scale, isVisible, canvas);
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

    private void drawCurve(Curve curve, float graphPadding, float graphWidth, float graphHeight, float step, Canvas canvas){

        float firstX = curve.getStartX();
        float firstY = curve.getStartY();
        float secondX = curve.getStopX();
        float secondY = curve.getStopY();

        if (secondX != firstX) {
            float a = (secondY - firstY) / (secondX - firstX);
            float k = (secondX * firstY - firstX * secondY) / (secondX - firstX);
            float oldX = startX;
            float oldY = a * oldX + k;
            float newY = 0.0f;
            float newX = oldX + 0.1f;
            float limitX = (graphWidth - graphPadding * 2.0f) / scale;
            float finalX = -1.0f;
            float finalY = -1.0f;

            setColor(curve.getColor());

            //  Draw function
            for(; newX <= lastX; newX+= 0.05f) {
                if (newX > limitX) break;
                newY = a * newX + k;
            }   /*if (graphHeight - newY * step < graphHeight && graphHeight - newY * step > graphPadding) {
                    canvas.drawLine(graphPadding * 2.0f + oldX * step, graphHeight - oldY * step,
                            graphPadding * 2.0f + newX * step, graphHeight - newY * step, mFuncPaint);
                    finalX = newX;
                    finalY = newY;
                }
                oldX = newX;
                oldY = newY;*/
            //}

            canvas.drawLine(graphPadding * 2.0f + oldX * step, graphHeight - oldY * step,
                    graphPadding * 2.0f + newX * step, graphHeight - newY * step, mFuncPaint);
            finalX = newX;
            finalY = newY;

            if (finalX >= 0) {
                canvas.drawText("y = " + a + " * x + " + k, graphPadding * 2.0f + finalX / 2.0f * step - 30.0f, graphHeight - finalY / 2.0f * step - 15.0f, mAxisName);
            }

        } else if (firstX > startX && firstX < lastX){
            setColor(curve.getColor());

            canvas.drawLine(graphPadding * 2.0f + firstX * step, graphPadding,
                    graphPadding * 2.0f + secondX * step, graphHeight, mFuncPaint);

            canvas.drawText("x = " + firstX, graphPadding * 2.0f + firstX * step + 30.0f, graphPadding, mAxisName);
        }

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
        float yPos = gridStep;
        float xPos = gridStep;
        final float limitStepY = graphPadding + 30.0f;
        final float limitStepX = graphWidth - 30.0f;

        //  Draw grid for Y
        if (isVisible) {
            for (float nStep = graphHeight - step * gridStep; nStep > limitStepY; nStep -= step * gridStep) {
                canvas.drawText(String.valueOf(yPos), graphPadding + 15.0f, nStep + 10.0f, mAxisPaint);
                canvas.drawLine(graphPadding * 2.0f - 15.0f, nStep, graphPadding * 2.0f, nStep, mAxisPaint);
                canvas.drawLine(graphPadding * 2.0f, nStep, graphWidth, nStep, mAxisName);
                yPos += gridStep;
            }

            //  Draw grid for X
            for (float nStep = graphPadding * 2.0f + step * gridStep; nStep < limitStepX; nStep += step * gridStep) {
                canvas.drawLine(nStep, graphHeight, nStep, graphHeight + 15.0f, mAxisPaint);
                canvas.drawLine(nStep, graphHeight, nStep, graphPadding, mAxisName);
                canvas.drawText(String.valueOf(xPos), nStep - 10.0f, graphHeight + 40.0f, mAxisPaint);
                xPos += gridStep;
            }
        } else {
            for (float nStep = graphHeight - step * gridStep; nStep > limitStepY; nStep -= step * gridStep) {
                canvas.drawText(String.valueOf(yPos), graphPadding + 15.0f, nStep + 10.0f, mAxisPaint);
                canvas.drawLine(graphPadding * 2.0f - 15.0f, nStep, graphPadding * 2.0f, nStep, mAxisPaint);
                yPos += gridStep;
            }

            //  Draw grid for X
            for (float nStep = graphPadding * 2.0f + step * gridStep; nStep < limitStepX; nStep += step * gridStep) {
                canvas.drawLine(nStep, graphHeight, nStep, graphHeight + 15.0f, mAxisPaint);
                canvas.drawText(String.valueOf(xPos), nStep - 10.0f, graphHeight + 40.0f, mAxisPaint);
                xPos += gridStep;
            }
        }
    }

    private void drawLimit(float graphPadding, float graphWidth, float graphHeight, float step, boolean isVisible, Canvas canvas){
        canvas.save();

        if (graphPadding * 2.0f + startX * step < graphWidth) {
            canvas.translate(graphPadding * 2.0f + startX * step, graphPadding);
            canvas.drawPath(path, mFuncLimit);
        }

        if (graphPadding * 2.0f + lastX * step < graphWidth) {
            canvas.translate(graphPadding * 2.0f + lastX * step - (graphPadding * 2.0f + startX * step), 0.0f);
            canvas.drawPath(path, mFuncLimit);
        }

        canvas.restore();
    }

    public void clear(){
        funcList.clear();
        curveList.clear();
        invalidate();
    }

    private void drawSQRTFunc(float graphPadding, float graphWidth, float graphHeight, float step, int color, Canvas canvas){
        float oldX = startX;
        float oldY = (float)Math.sqrt((double)oldX);
        float newY = 0.0f;
        float newX = oldX + 0.1f;
        float limitX = (graphWidth - graphPadding * 2.0f) / scale;

        setColor(color);

        //  Draw function
        for(; newX < lastX; newX+= 0.05f){
            newY = (float)Math.sqrt((double)newX);

            if (graphHeight - newY * step < graphPadding) break;
            if (newX > limitX) break;

            canvas.drawLine(graphPadding * 2.0f + oldX * step, graphHeight - oldY * step,
                    graphPadding * 2.0f + newX * step, graphHeight - newY * step, mFuncPaint);
            oldX = newX;
            oldY = newY;

        }

        canvas.drawText("y = sqrt(x)", graphPadding + newX * step + 10.0f, graphHeight - newY * step - 30.0f, mAxisName);
    }

    private void drawSQFunc(float graphPadding, float graphWidth, float graphHeight, float step, int color, Canvas canvas){
        float oldX = startX;
        float oldY = oldX*oldX;
        float newY = 0.0f;
        float newX = oldX + 0.1f;
        float limitX = (graphWidth - graphPadding * 2.0f) / scale;

        setColor(color);

        //  Draw function
        for(; newX < lastX; newX+= 0.05f){
            newY = newX*newX;

            if (graphHeight - newY * step < graphPadding) break;
            if (newX > limitX) break;

            canvas.drawLine(graphPadding * 2.0f + oldX * step, graphHeight - oldY * step,
                    graphPadding * 2.0f + newX * step, graphHeight - newY * step, mFuncPaint);
            oldX = newX;
            oldY = newY;

        }

        canvas.drawText("y = x^2", graphPadding * 2.0f + newX * step + 10.0f, graphPadding + 20.0f, mAxisName);
    }

    private void drawDIVFunc(float graphPadding, float graphWidth, float graphHeight, float step, int color, Canvas canvas){
        float oldX = startX;
        if (oldX == 0.0f) oldX += 0.01f;
        float oldY = 1/oldX;
        float newY = 0.0f;
        float newX = oldX + 0.01f;
        float limitX = (graphWidth - graphPadding * 2.0f) / scale;

        setColor(color);

        //  Draw function
        for(; newX < lastX; newX+= 0.01f){
            if (newX > limitX) break;
            if (newX == 0.0f) newX += 0.01f;
            newY = 1/newX;

            if (graphHeight - newY * step < graphHeight && graphHeight - newY * step > graphPadding * 2.0f) {

                canvas.drawLine(graphPadding * 2.0f + oldX * step, graphHeight - oldY * step,
                        graphPadding * 2.0f + newX * step, graphHeight - newY * step, mFuncPaint);
            }
            oldX = newX;
            oldY = newY;

            canvas.drawText("y = 1/x", graphPadding * 2.0f + 2.0f * step + 100.0f, graphHeight - 0.5f * step, mAxisName);
        }
    }

    private void drawCOSFunc(float graphPadding, float graphWidth, float graphHeight, float step, int color, Canvas canvas){
        float oldX = startX;
        float oldY = (float)Math.cos((double)oldX);
        float newY = 0.0f;
        float newX = oldX + 0.1f;
        float limitX = (graphWidth - graphPadding * 2.0f) / scale;

        setColor(color);

        //  Draw function
        for(; newX < lastX; newX+= 0.05f){
            newY = (float)Math.cos((double)newX);

            if (graphHeight - newY * step < graphPadding) break;
            if (newX > limitX) break;

            canvas.drawLine(graphPadding * 2.0f + oldX * step, graphHeight - oldY * step,
                    graphPadding * 2.0f + newX * step, graphHeight - newY * step, mFuncPaint);
            oldX = newX;
            oldY = newY;
        }

        canvas.drawText("y = cos(x)", graphPadding * 2.0f + newX * step - 30.0f, graphHeight - newY * step, mAxisName);
    }

    private void drawSINFunc(float graphPadding, float graphWidth, float graphHeight, float step, int color, Canvas canvas){
        float oldX = startX;
        float oldY = (float)Math.sin((double)oldX);
        float newY = 0.0f;
        float newX = oldX + 0.1f;
        float limitX = (graphWidth - graphPadding * 2.0f) / scale;

        setColor(color);

        //  Draw function
        for(; newX < lastX; newX+= 0.05f){
            newY = (float)Math.sin((double)newX);

            if (graphHeight - newY * step < graphPadding) break;
            if (newX > limitX) break;

            canvas.drawLine(graphPadding * 2.0f + oldX * step, graphHeight - oldY * step,
                    graphPadding * 2.0f + newX * step, graphHeight - newY * step, mFuncPaint);
            oldX = newX;
            oldY = newY;
        }

        canvas.drawText("y = sin(x)", graphPadding * 2.0f + newX * step - 30.0f, graphHeight - newY * step, mAxisName);
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

    public void addCustomCurve(Curve curve){
        curveList.add(curve);
    }

    public void setStartX(float startPoint){
        startX = startPoint;
    }

    public void setLastX(float lastPoint){
        lastX = lastPoint;
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

    public void setDefaultGridStep(){
        gridStep = 1.0f;
        invalidate();
    }

    public void setGridStep(float newStep){
        gridStep = newStep;
        invalidate();
    }

    public void deleteLimit(){
        setLimit(false);
        startX = 0.0f;
        lastX = 25.0f;
        invalidate();
    }

    public void setLimit(boolean bool){
        isLimited = bool;
    }
}
