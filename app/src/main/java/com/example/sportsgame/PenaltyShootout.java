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
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class PenaltyShootout extends AppCompatActivity {


    private long lastAnimationStarted;
    private int newGoalieX;
    private int newGoalieY;
    private int goalieDuration = 500;
    private int ballDuration = 1000;
    private TextView coinsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penalty_shootout);

        coinsTextView = findViewById(R.id.coinsTextView);
        updateCoinsDisplay();

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

                int newX = getRandomNumber(0, (background.getWidth() - goalie.getWidth()));
                int newY = getRandomNumber(0, (background.getHeight() - goalie.getHeight()));
                moveGoalie(goalie, newX, newY);

                moveBall(background, 0, 0, 0);

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

            //Erkennung der Klick Position (Wo der Ball hinfliegen soll)
            int x = (int) event.getX();
            int y = (int) event.getY();

            switch (event.getAction()) {

                case MotionEvent.ACTION_UP:
                    //Animation vom Ball aufrufen mit Kollision Detektion
                    moveBall(background, x, y, ballDuration);
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

                //Neue zufällige Position vom Torwart berechnen, wo er sich hinbewegen soll
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


        ballAnimation.setDuration(duration);
        ballAnimation.setFillAfter(true);
        ball.setAnimation(ballAnimation);

        ball.postDelayed(new Runnable() {
            @Override
            public void run() {
                ball.clearAnimation();
                ImageView goalie = ((View) background.getParent()).findViewById(R.id.quadrat);

                //Fortschritt der Animation berechnen (um zu wissen, wo der Goalie ist)
                long currentTime = System.currentTimeMillis();
                long timeSinceStart = currentTime - lastAnimationStarted;
                double animationProgress = (1.0 / goalieDuration) * timeSinceStart;

                //Position im animationProgress Zeitpunkt berechnen
                double currentX = goalie.getX() + (((newGoalieX - goalie.getX()) * animationProgress));
                double currentY = goalie.getY() + (((newGoalieY - goalie.getY()) * animationProgress));

                //Goalie und Ball als Rect erstellen, um mit intersects zu arbeiten
                Rect goalieRect = new Rect((int) Math.round(currentX), (int) Math.round(currentY), (int) Math.round(currentX + goalie.getWidth()), (int) Math.round(currentY + goalie.getHeight()));
                Rect ballRect = new Rect((int) Math.round(newX - (ball.getWidth() / 2)), (int) Math.round(newY - (ball.getHeight() / 2)), (int) Math.round(newX + ball.getWidth() / 2), (int) Math.round(newY + ball.getHeight() / 2));


                if (duration != 0) {
                    //wenn der Torwart den Ball pariert
                    if (Rect.intersects(ballRect, goalieRect)) {
                        Snackbar.make(background, "No goal!", Snackbar.LENGTH_SHORT).show();
                    }
                    //Wenn der Ball den Torspiegel trifft und nicht pariert wird
                    else if(ballInGoal(ballRect)){
                        Snackbar.make(background, "Goal!", Snackbar.LENGTH_SHORT).show();
                        // Aufruf der Methode, um einen Coin hinzuzufügen
                        addCoinToTextFile();

                        // Aufruf der Methode, um die Anzeige zu aktualisieren
                        updateCoinsDisplay();
                    }
                    //Wenn der Ball den Torspiegel verfehlt
                    else{
                        Snackbar.make(background, "Missed!", Snackbar.LENGTH_SHORT).show();
                    }
                }
                //Ball wieder in die Startposition setzen
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
        int goalWidth = (backgroundHeigth * 2);
        int goalX = (backgroundWidth - goalWidth) / 2;

        //Prozentuelle abständer der Torpfosten (im Verhältnis zum Bildschirm)
        final float WIDTH_POST_PERCENTAGE = (float) 0.0231;
        final float HEIGTH_POST_PERCENTAGE = (float) 0.0476;

        //Kontrolle, ob der Ball den Torspiegel trifft oder nicht
        if (ball.left > goalX + (goalWidth * WIDTH_POST_PERCENTAGE) && ball.right < (goalX + goalWidth) - (goalWidth * WIDTH_POST_PERCENTAGE)) {
            return true;
        }
        return false;
    }

    private void updateCoinsDisplay() {
        int coinsCount = readCoinsFromTextFile();
        coinsTextView.setText("Coins: " + coinsCount);
    }

    private int readCoinsFromTextFile() {
        try {
            File file = new File(getFilesDir(), "coins.txt");
            if (!file.exists()) {
                file.createNewFile();
                return 0;
            }

            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String line;
            int coinsCount = 0;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.equals("Coin")) {
                    coinsCount++;
                }
            }
            bufferedReader.close();
            return coinsCount;
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private void addCoinToTextFile() {
        try {
            File file = new File(getFilesDir(), "coins.txt");
            FileWriter fileWriter = new FileWriter(file, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write("Coin"); // Füge "Coin" zur Textdatei hinzu
            bufferedWriter.newLine();
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
