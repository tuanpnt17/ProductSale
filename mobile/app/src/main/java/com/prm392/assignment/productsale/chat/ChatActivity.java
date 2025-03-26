package com.prm392.assignment.productsale.chat;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.prm392.assignment.productsale.R;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatActivity extends AppCompatActivity {
    private static final String TAG = "ChatActivity";
    private RecyclerView recyclerView;
    private ChatAdapter chatAdapter;
    private List<Message> messageList;
    private EditText messageEditText;
    private ImageView sendBtn, backBtn;
    private TextView chatName, statusText;
    private CircleImageView profilePic;

    private FirebaseManager firebaseManager;
    private String currentUserId;
    private String partnerId;
    private ChatUser partnerUser;
    private int retryCount = 0;
    private static final int MAX_RETRY = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        // Initialize Firebase Manager
        firebaseManager = FirebaseManager.getInstance(this);
        currentUserId = firebaseManager.getCurrentUserId();

        // Initialize views
        recyclerView = findViewById(R.id.chattingRecyclerView);
        messageEditText = findViewById(R.id.messageEditTxt);
        sendBtn = findViewById(R.id.sendBtn);
        backBtn = findViewById(R.id.backBtn);
        chatName = findViewById(R.id.chat_name);
        statusText = findViewById(R.id.status_text);
        profilePic = findViewById(R.id.profilePic);

        // Initialize message list
        messageList = new ArrayList<>();

        // Set up RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        chatAdapter = new ChatAdapter(this, messageList, currentUserId);
        recyclerView.setAdapter(chatAdapter);

        // Get partnerId from intent
        if (getIntent() != null) {
            partnerId = getIntent().getStringExtra("partner_id");
        }

        // If no partner_id, find a test partner
        if (partnerId == null) {
            // Show loading state
            statusText.setText("Finding a chat partner...");
            findChatPartner();
        } else {
            loadUserProfile();
            loadMessages();
            firebaseManager.markMessagesAsRead(partnerId);
        }

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (partnerId == null) {
                    Toast.makeText(ChatActivity.this, "No chat partner selected", Toast.LENGTH_SHORT).show();
                    return;
                }

                String msg = messageEditText.getText().toString().trim();
                if (!msg.isEmpty()) {
                    sendMessage(msg);
                }
            }
        });

        // Set up back button event
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void findChatPartner() {
        // First try - use the stored test partner ID
        String testPartnerId = firebaseManager.getTestPartnerId();

        if (testPartnerId != null) {
            Log.d(TAG, "Using stored test partner ID: " + testPartnerId);
            partnerId = testPartnerId;
            loadUserProfile();
            loadMessages();
            firebaseManager.markMessagesAsRead(partnerId);
            return;
        }

        // If no stored test partner, query all users
        Log.d(TAG, "No stored test partner ID, querying all users");
        firebaseManager.getAllUsers(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d(TAG, "User query snapshot exists: " + snapshot.exists());
                Log.d(TAG, "User query child count: " + snapshot.getChildrenCount());

                if (snapshot.exists() && snapshot.getChildrenCount() > 0) {
                    for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                        String userId = userSnapshot.getKey();
                        Log.d(TAG, "Found user with ID: " + userId);

                        if (userId != null && !userId.equals(currentUserId)) {
                            partnerId = userId;
                            Log.d(TAG, "Selected partner ID: " + partnerId);
                            loadUserProfile();
                            loadMessages();
                            firebaseManager.markMessagesAsRead(partnerId);
                            return;
                        }
                    }

                    // If we get here, no suitable partner was found
                    handleNoPartnerFound();
                } else {
                    Log.d(TAG, "No users found in database");
                    handleNoPartnerFound();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "User query cancelled: " + error.getMessage());
                Toast.makeText(ChatActivity.this, "Error finding chat partner: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void handleNoPartnerFound() {
        retryCount++;
        if (retryCount <= MAX_RETRY) {
            Log.d(TAG, "No partner found, retrying in 2 seconds (Attempt " + retryCount + "/" + MAX_RETRY + ")");
            statusText.setText("No partner found. Retrying in 2 seconds... (" + retryCount + "/" + MAX_RETRY + ")");

            // Wait 2 seconds before retrying
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    findChatPartner();
                }
            }, 2000);
        } else {
            // Create a fake partner as fallback
            Log.d(TAG, "Creating fallback partner after max retries");
            createFallbackPartner();
        }
    }

    private void createFallbackPartner() {
        // Create a fixed partner ID for testing
        partnerId = "fallback-partner-" + System.currentTimeMillis();
        Log.d(TAG, "Created fallback partner with ID: " + partnerId);

        // Directly use this partner without checking if it exists
        ChatUser fakeUser = new ChatUser(
                partnerId,
                "Fallback Partner",
                "fallback@example.com",
                "buyer",
                "https://ui-avatars.com/api/?name=Fallback+Partner&background=random"
        );

        // Display user info manually
        chatName.setText(fakeUser.getName());
        statusText.setText("Offline (Fallback Mode)");
        statusText.setTextColor(getResources().getColor(android.R.color.darker_gray));

        // Load placeholder image
        Glide.with(ChatActivity.this)
                .load(fakeUser.getProfileImageUrl())
                .placeholder(R.drawable.user_icon)
                .error(R.drawable.user_icon)
                .into(profilePic);

        // We won't actually load messages since this partner doesn't exist in Firebase
        Toast.makeText(ChatActivity.this, "Using fallback chat mode", Toast.LENGTH_SHORT).show();
    }

    private void loadUserProfile() {
        firebaseManager.getUserProfile(partnerId, new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d(TAG, "Partner profile snapshot exists: " + snapshot.exists());

                if (snapshot.exists()) {
                    partnerUser = snapshot.getValue(ChatUser.class);
                    if (partnerUser != null) {
                        // Display user information
                        chatName.setText(partnerUser.getName());

                        // Display online status
                        if (partnerUser.isOnline()) {
                            statusText.setText("Online");
                            statusText.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
                        } else {
                            String lastSeen = partnerUser.getLastSeen();
                            if (lastSeen != null && !lastSeen.isEmpty()) {
                                statusText.setText("Hoạt động lần cuối: " + lastSeen);
                            } else {
                                statusText.setText("Offline");
                            }
                            statusText.setTextColor(getResources().getColor(android.R.color.darker_gray));
                        }

                        // Load profile image
                        if (partnerUser.getProfileImageUrl() != null && !partnerUser.getProfileImageUrl().isEmpty()) {
                            Glide.with(ChatActivity.this)
                                    .load(partnerUser.getProfileImageUrl())
                                    .placeholder(R.drawable.user_icon)
                                    .error(R.drawable.user_icon)
                                    .into(profilePic);
                        }
                    }
                } else {
                    Log.d(TAG, "Partner profile not found");
                    statusText.setText("User profile not available");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "Profile query cancelled: " + error.getMessage());
                Toast.makeText(ChatActivity.this, "Lỗi tải thông tin người dùng: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadMessages() {
        Log.d(TAG, "Loading messages for partner ID: " + partnerId);
        firebaseManager.getMessages(partnerId, new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d(TAG, "Messages snapshot child count: " + snapshot.getChildrenCount());
                messageList.clear();

                for (DataSnapshot messageSnapshot : snapshot.getChildren()) {
                    Message message = messageSnapshot.getValue(Message.class);
                    if (message != null) {
                        // Filter messages between current user and partner
                        if ((message.getSenderId().equals(currentUserId) && message.getReceiverId().equals(partnerId)) ||
                                (message.getSenderId().equals(partnerId) && message.getReceiverId().equals(currentUserId))) {
                            messageList.add(message);
                        }
                    }
                }

                Log.d(TAG, "Filtered message count: " + messageList.size());

                // Update adapter and scroll to bottom
                chatAdapter.notifyDataSetChanged();
                if (messageList.size() > 0) {
                    recyclerView.scrollToPosition(messageList.size() - 1);
                }

                // Mark messages as read
                firebaseManager.markMessagesAsRead(partnerId);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "Message query cancelled: " + error.getMessage());
                Toast.makeText(ChatActivity.this, "Lỗi tải tin nhắn: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendMessage(String content) {
        firebaseManager.sendMessage(partnerId, content, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    // Clear input field
                    messageEditText.setText("");
                } else {
                    Log.e(TAG, "Failed to send message: " + task.getException().getMessage());
                    Toast.makeText(ChatActivity.this, "Lỗi gửi tin nhắn: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Update online status
        firebaseManager.updateUserOnlineStatus(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Update offline status
        firebaseManager.updateUserOnlineStatus(false);
        // Mark messages as read when leaving the screen
        if (partnerId != null) {
            firebaseManager.markMessagesAsRead(partnerId);
        }
    }
}