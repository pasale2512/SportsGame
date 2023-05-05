package com.example.sportsgame;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.google.android.material.snackbar.Snackbar;

public class PenaltyShootout extends AppCompatActivity {

    int bildTop =0;
    int bildLeft =0;
    int bildHeight =0;
    int bildWidth =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penalty_shootout);
        //ImageView background = (ImageView) this.findViewById(R.id.imageVie´);
        //background.setOnTouchListener(touch);

        View view = findViewById(R.id.background);
        ViewTreeObserver observer = view.getViewTreeObserver();

        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {


                ImageView bildView = findViewById(R.id.bild);
                Drawable drawable = bildView.getDrawable();
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.goaltest);
                int width = bitmap.getWidth();
                int widthInDp = (int) (width / getResources().getDisplayMetrics().density);

                bildTop =bildView.getTop();
                bildLeft =bildView.getWidth()-((bildView.getWidth())-widthInDp/2);
                bildHeight =bildView.getHeight();
                bildWidth=widthInDp;

                ImageView quadrat = (ImageView) view.findViewById(R.id.quadrat);
                //moveQuadrat(quadrat);


                int newX= getRandomNumber(bildLeft, (bildLeft +bildWidth-quadrat.getWidth()));
                int newY = getRandomNumber(bildTop, (bildTop + bildHeight -quadrat.getHeight()));

                moveImageView(quadrat, newX, newY); // move to X=100, Y=200

                // Unregister the listener to avoid multiple calls
                view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }



    private View.OnTouchListener touch = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent event) {

            int x = (int) event.getX();
            int y = (int) event.getY();

            switch(event.getAction()) {
                case MotionEvent.ACTION_UP:
                    Snackbar.make(view, x + " " + y, Snackbar.LENGTH_SHORT).show();
                    break;
            }
            return true;
        }
    };


    public void moveImageView(ImageView quadrat, int newX, int newY) {
        TranslateAnimation animation = new TranslateAnimation(0, newX - quadrat.getX(), 0, newY - quadrat.getY());
        animation.setDuration(500);
        //Nach der Bewegung in der neuen position bleiben
        animation.setFillAfter(true);
        quadrat.startAnimation(animation);

        //Updaten der ImageView Daten nach der Animation
        quadrat.postDelayed(new Runnable() {
            @Override
            public void run() {
                quadrat.clearAnimation();
                quadrat.setX(newX);
                quadrat.setY(newY);

                int newX2= getRandomNumber(bildLeft, (bildLeft +bildWidth-quadrat.getWidth()));
                int newY2 = getRandomNumber(bildTop, (bildTop + bildHeight -quadrat.getHeight()));

                moveImageView(quadrat, newX2, newY2); // move to X=100, Y=200
            }
            //delay für die Dauer der Animation
        }, 500);
    }


    public void moveBall(ImageView ball, int newX, int newY){

        TranslateAnimation ballAnimation = new TranslateAnimation(0, newX-ball.getX(), 0, newY-ball.getY());
        ballAnimation.setDuration(500);
        
    }
}