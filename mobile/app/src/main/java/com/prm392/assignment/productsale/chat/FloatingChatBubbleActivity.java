package com.prm392.assignment.productsale.chat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.prm392.assignment.productsale.R;

public class FloatingChatBubbleActivity extends AppCompatActivity {

    private ImageView chatBubble;
    private RelativeLayout rootLayout;
    private WindowManager.LayoutParams params;
    private float xDelta, yDelta;
    private float lastX, lastY;
    private FirebaseManager firebaseManager;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_floating_chat_bubble);

        // Initialize FirebaseManager
        firebaseManager = FirebaseManager.getInstance(this);

        rootLayout = findViewById(R.id.root_layout);
        chatBubble = findViewById(R.id.chat_bubble);

        // Set up touch event for chat bubble
        chatBubble.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                        // Save position when touch starts
                        lastX = event.getRawX();
                        lastY = event.getRawY();
                        xDelta = view.getX() - event.getRawX();
                        yDelta = view.getY() - event.getRawY();
                        return true;

                    case MotionEvent.ACTION_MOVE:
                        // Move chat bubble with finger
                        float newX = event.getRawX() + xDelta;
                        float newY = event.getRawY() + yDelta;

                        // Limit to keep bubble on screen
                        if (newX < 0) newX = 0;
                        if (newX > rootLayout.getWidth() - view.getWidth())
                            newX = rootLayout.getWidth() - view.getWidth();
                        if (newY < 0) newY = 0;
                        if (newY > rootLayout.getHeight() - view.getHeight())
                            newY = rootLayout.getHeight() - view.getHeight();

                        view.setX(newX);
                        view.setY(newY);
                        return true;

                    case MotionEvent.ACTION_UP:
                        // Check if movement was short enough to be a click
                        float deltaX = event.getRawX() - lastX;
                        float deltaY = event.getRawY() - lastY;
                        float distance = (float) Math.sqrt(deltaX * deltaX + deltaY * deltaY);

                        if (distance < 10) { // Threshold to be considered a click
                            Intent intent;

                            // Check if the current user is a seller
                            if (firebaseManager.isCurrentUserSeller()) {
                                // Open UserListActivity for sellers
                                intent = new Intent(FloatingChatBubbleActivity.this, UserListActivity.class);
                            } else {
                                // Open ChatActivity for buyers (existing behavior)
                                intent = new Intent(FloatingChatBubbleActivity.this, ChatActivity.class);
                            }

                            startActivity(intent);
                        }
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Update user's online status
        if (firebaseManager != null) {
            firebaseManager.updateUserOnlineStatus(true);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Update user's offline status
        if (firebaseManager != null) {
            firebaseManager.updateUserOnlineStatus(false);
        }
    }
}