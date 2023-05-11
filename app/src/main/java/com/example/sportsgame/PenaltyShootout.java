package com.example.sportsgame;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Transformation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.google.android.material.snackbar.Snackbar;

public class PenaltyShootout extends AppCompatActivity {


    private long lastAnimationStarted;
    private int newGoalieX, newGoalieY;

    private int goalieDuration = 2000;
    private int ballDuration = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penalty_shootout);
        //ImageView background = (ImageView) this.findViewById(R.id.imageView);
        //background.setOnTouchListener(touch);

        View background = findViewById(R.id.background);
        ViewTreeObserver observer = background.getViewTreeObserver();

        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                ImageView goal = findViewById(R.id.bild);
                goal.setOnTouchListener(touch);
                Drawable drawable = goal.getDrawable();
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.goaltest);
                int width = bitmap.getWidth();
                int widthInDp = (int) (width / getResources().getDisplayMetrics().density);


                ImageView goalie = (ImageView) background.findViewById(R.id.quadrat);
                //moveQuadrat(quadrat);

                int newX = getRandomNumber(0, (background.getWidth() - goalie.getWidth()));
                int newY = getRandomNumber(0, (background.getHeight() - goalie.getHeight()));
                moveGoalie(goalie, newX, newY);

                moveBall(background, 0, 0, 0);



                /*
                int ballPosX=0;
                int ballPosY=0;
                moveBall(ball, ballPosX, ballPosY);

                 */

                // Unregister the listener to avoid multiple calls
                background.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }


    private View.OnTouchListener touch = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View background, MotionEvent event) {

            int x = (int) event.getX();
            int y = (int) event.getY();

            switch (event.getAction()) {

                case MotionEvent.ACTION_UP:
                    //Snackbar.make(view, ((View)view.getParent()).findViewById(R.id.ball), Snackbar.LENGTH_SHORT);

                    moveBall(background, x, y, 1000);
                    //Snackbar.make(view, x + " " + y, Snackbar.LENGTH_SHORT).show();
                    break;
            }
            return true;
        }
    };


    public void moveGoalie(ImageView goalie, int newX, int newY) {
        TranslateAnimation goalieAnimation = new TranslateAnimation(0, newX - goalie.getX(), 0, newY - goalie.getY());
        goalieAnimation.setDuration(goalieDuration);
        //Nach der Bewegung in der neuen position bleiben
        goalieAnimation.setFillAfter(true);
        lastAnimationStarted = System.currentTimeMillis();
        goalie.startAnimation(goalieAnimation);

        //Updaten der ImageView Daten nach der Animation
        goalie.postDelayed(new Runnable() {
            @Override
            public void run() {
                goalie.clearAnimation();
                goalie.setX(newX);
                goalie.setY(newY);

                newGoalieX = getRandomNumber(0, (((View) goalie.getParent()).getWidth() - goalie.getWidth()));
                newGoalieY = getRandomNumber(0, (((View) goalie.getParent()).getHeight() - goalie.getHeight()));

                moveGoalie(goalie, newGoalieX, newGoalieY); // move to X=100, Y=200
            }
            //delay für die Dauer der Animation
        }, goalieDuration);
    }


    public void moveBall(View background, int newX, int newY, int duration) {

        ImageView ball = ((View) background.getParent()).findViewById(R.id.ball);
        float ballcenterX = ball.getX() + (ball.getWidth() / 2);
        float ballcenterY = ball.getY() + (ball.getHeight() / 2);

        TranslateAnimation ballAnimation = new TranslateAnimation(0, newX - ballcenterX, 0, newY - ballcenterY);
        //Duration je nach Schusskraft stärker oder schwächer
        ballAnimation.setDuration(duration);
        ballAnimation.setFillAfter(true);
        ball.setAnimation(ballAnimation);

        ball.postDelayed(new Runnable() {
            @Override
            public void run() {
                ball.clearAnimation();
                ImageView goalie = ((View) background.getParent()).findViewById(R.id.quadrat);

                long currentTime = System.currentTimeMillis();

                long timeSinceStart = currentTime - lastAnimationStarted;
                double animationProgress = (1.0 / goalieDuration) * timeSinceStart;
                double currentX = goalie.getX() + (((newGoalieX - goalie.getX()) * animationProgress));
                double currentY = goalie.getY() + (((newGoalieY - goalie.getY()) * animationProgress));


                Rect goalieRect = new Rect((int) Math.round(currentX), (int) Math.round(currentY), (int) Math.round(currentX + goalie.getWidth()), (int) Math.round(currentY + goalie.getHeight()));
                Rect ballRect = new Rect((int) Math.round(newX - (ball.getWidth() / 2)), (int) Math.round(newY - (ball.getHeight() / 2)), (int) Math.round(newX + ball.getWidth() / 2), (int) Math.round(newY + ball.getHeight() / 2));


                if (duration != 0) {
                    if (Rect.intersects(ballRect, goalieRect)) {
                        Snackbar.make(background, "No goal!", Snackbar.LENGTH_SHORT).show();
                    } else if(ballInGoal(ballRect)){
                        Snackbar.make(background, "Goal!", Snackbar.LENGTH_SHORT).show();
                    }
                    else{
                        Snackbar.make(background, "Missed!", Snackbar.LENGTH_SHORT).show();
                    }
                }
                ball.setX((background.getWidth() / 2) - ball.getWidth() / 2);
                ball.setY(background.getHeight() - ball.getHeight());

                int newX2 = getRandomNumber(0, (background.getWidth() - ball.getWidth()));
                int newY2 = getRandomNumber(0, (background.getHeight() - ball.getHeight()));

                Log.d("myApp", goalieRect.toString() + ballRect.toString());
                Log.d("myApp", "Click: " + newX + " " + newY);


            }
            //delay für die Dauer der Animation
        }, duration);
    }

    private boolean ballInGoal(Rect ball) {

        View background = findViewById(R.id.background);
        int backgroundWidth = background.getWidth();
        int backgroundHeigth = background.getHeight();
        int goalWidth = (backgroundHeigth*2);
        int goalX = (backgroundWidth-goalWidth)/2;
        final float WIDTH_POST_PERCENTAGE = (float) 0.0231;
        final float HEIGTH_POST_PERCENTAGE = (float) 0.0476;

        if(ball.left > goalX +(goalWidth*WIDTH_POST_PERCENTAGE) && ball.right < (goalX+goalWidth)-(goalWidth*WIDTH_POST_PERCENTAGE)){

            return true;
        }

        return false;
    }
}