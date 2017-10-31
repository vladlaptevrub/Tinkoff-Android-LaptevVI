package fintech.tinkoff.fifthlab;

public class Curve {
    private float startX;
    private float startY;
    private float stopX;
    private float stopY;
    private int color;
    private boolean isRounded;

    public Curve(float startX, float startY, float stopX, float stopY, int color, boolean isRounded){
        this.startX = startX;
        this.startY = startY;
        this.stopX = stopX;
        this.stopY = stopY;
        this.color = color;
        this.isRounded = isRounded;
    }

    public float getStartX(){
        return startX;
    }

    public float getStartY(){
        return startY;
    }

    public float getStopX(){
        return stopX;
    }

    public float getStopY(){
        return stopY;
    }

    public int getColor(){
        return color;
    }
}
