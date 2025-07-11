package assignment9;

import java.util.LinkedList;

public class Snake {

    private static final double SEGMENT_SIZE = 0.02;
    private static final double MOVEMENT_SIZE = SEGMENT_SIZE * 1.5;
    private LinkedList<BodySegment> segments;
    private double deltaX;
    private double deltaY;
    private double lastX;
    private double lastY;

    public Snake() {
        segments = new LinkedList<>();
        // Start in the center of the screen
        segments.add(new BodySegment(0.5, 0.5, SEGMENT_SIZE));
        deltaX = 0;
        deltaY = 0;
    }

    public void changeDirection(int direction) {
        if (direction == 1) { // up
            deltaY = MOVEMENT_SIZE;
            deltaX = 0;
        } else if (direction == 2) { // down
            deltaY = -MOVEMENT_SIZE;
            deltaX = 0;
        } else if (direction == 3) { // left
            deltaY = 0;
            deltaX = -MOVEMENT_SIZE;
        } else if (direction == 4) { // right
            deltaY = 0;
            deltaX = MOVEMENT_SIZE;
        }
    }

    /**
     * Moves the snake by updating the position of each of the segments
     * based on the current direction of travel
     */
    public void move() {
        // Record tail position for potential growth
        BodySegment tail = segments.getLast();
        lastX = tail.getX();
        lastY = tail.getY();

        int n = segments.size();
        double[] xs = new double[n];
        double[] ys = new double[n];
        for (int i = 0; i < n; i++) {
            xs[i] = segments.get(i).getX();
            ys[i] = segments.get(i).getY();
        }

        // Move head
        segments.getFirst().setX(xs[0] + deltaX);
        segments.getFirst().setY(ys[0] + deltaY);

        // Move body
        for (int i = 1; i < n; i++) {
            segments.get(i).setX(xs[i - 1]);
            segments.get(i).setY(ys[i - 1]);
        }
    }

    /**
     * Draws the snake by drawing each segment
     */
    public void draw() {
        for (BodySegment segment : segments) {
            segment.draw();
        }
    }

    /**
     * The snake attempts to eat the given food, growing if it does so successfully
     * @param f the food to be eaten
     * @return true if the snake successfully ate the food
     */
    public boolean eatFood(Food f) {
        BodySegment head = segments.getFirst();
        double dx = head.getX() - f.getX();
        double dy = head.getY() - f.getY();
        double distance = Math.sqrt(dx * dx + dy * dy);
        if (distance < SEGMENT_SIZE / 2 + Food.FOOD_SIZE / 2) {
            // Grow the snake by adding a new segment at the old tail position
            segments.addLast(new BodySegment(lastX, lastY, SEGMENT_SIZE));
            return true;
        }
        return false;
    }

    /**
     * Returns true if the head of the snake is in bounds
     * @return whether or not the head is in the bounds of the window
     */
    public boolean isInbounds() {
        BodySegment head = segments.getFirst();
        double x = head.getX();
        double y = head.getY();
        double radius = SEGMENT_SIZE / 2;
        return x - radius >= 0 && x + radius <= 1 && y - radius >= 0 && y + radius <= 1;
    }
}

