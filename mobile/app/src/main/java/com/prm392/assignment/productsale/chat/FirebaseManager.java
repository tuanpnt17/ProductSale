package com.prm392.assignment.productsale.chat;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.prm392.assignment.productsale.model.UserModel;
import com.prm392.assignment.productsale.util.UserAccountManager;

import android.content.Context;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

public class FirebaseManager {
    private static final String TAG = "FirebaseManager";
    private static final String USERS_REF = "users";
    private static final String MESSAGES_REF = "messages";
    private static final String CONVERSATIONS_REF = "conversations";

    private FirebaseDatabase mDatabase;
    private DatabaseReference mUsersRef;
    private DatabaseReference mMessagesRef;
    private DatabaseReference mConversationsRef;
    private String currentUserId;
    private UserModel currentUser;
    private Context context;
    private String testPartnerId; // Store the test partner ID

    private static FirebaseManager instance;

    public static synchronized FirebaseManager getInstance(Context context) {
        if (instance == null) {
            instance = new FirebaseManager(context);
        }
        return instance;
    }

    private FirebaseManager(Context context) {
        this.context = context;
        mDatabase = FirebaseDatabase.getInstance();
        mUsersRef = mDatabase.getReference(USERS_REF);
        mMessagesRef = mDatabase.getReference(MESSAGES_REF);
        mConversationsRef = mDatabase.getReference(CONVERSATIONS_REF);

        // MODIFIED: Create fake user data instead of getting from UserAccountManager
        createFakeUser();

        // Save or update user info in Firebase for chat functionality
        saveUserToFirebase();
    }

    // ADDED: Method to create fake user data
    private void createFakeUser() {
        // Generate a random ID if needed
        String userId = UUID.randomUUID().toString().substring(0, 8);

        // Create a fake UserModel instance
        currentUser = new UserModel();
        currentUser.setId(userId);
        currentUser.setFullName("Test User");
        currentUser.setEmail("test@example.com");

        // Set the current user ID
        currentUserId = userId;
    }

    private void saveUserToFirebase() {
        if (currentUser != null) {
            Map<String, Object> userMap = new HashMap<>();
            userMap.put("id", currentUser.getId());
            userMap.put("name", currentUser.getFullName());
            userMap.put("email", currentUser.getEmail());
            userMap.put("role", currentUser.hasStore() ? "seller" : "buyer");
            userMap.put("profileImageUrl", currentUser.getImageLink());
            userMap.put("online", true);

            mUsersRef.child(currentUserId).updateChildren(userMap)
                    .addOnSuccessListener(aVoid -> {
                        Log.d(TAG, "User saved successfully, now creating test partner");
                        // Create another fake user to chat with once our user is saved
                        createFakePartnerUser();
                    })
                    .addOnFailureListener(e -> {
                        Log.e(TAG, "Failed to save user: " + e.getMessage());
                    });
        }
    }

    // MODIFIED: Method to create a fake partner user for testing with callback
    private void createFakePartnerUser() {
        testPartnerId = "partner-" + UUID.randomUUID().toString().substring(0, 6);

        Map<String, Object> partnerMap = new HashMap<>();
        partnerMap.put("id", testPartnerId);
        partnerMap.put("name", "Test Partner");
        partnerMap.put("email", "partner@example.com");
        partnerMap.put("role", "buyer");
        partnerMap.put("profileImageUrl", "https://ui-avatars.com/api/?name=Test+Partner&background=random");
        partnerMap.put("online", false);
        partnerMap.put("lastSeen", new SimpleDateFormat("dd MMM yyyy HH:mm", Locale.getDefault()).format(new Date()));

        mUsersRef.child(testPartnerId).setValue(partnerMap)
                .addOnSuccessListener(aVoid -> {
                    Log.d(TAG, "Test partner created successfully with ID: " + testPartnerId);
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Failed to create test partner: " + e.getMessage());
                });
    }

    public String getCurrentUserId() {
        if (currentUserId == null && currentUser != null) {
            currentUserId = currentUser.getId();
        }
        return currentUserId;
    }

    public UserModel getCurrentUser() {
        return currentUser;
    }

    // ADDED: Method to get test partner ID
    public String getTestPartnerId() {
        return testPartnerId;
    }

    // User Profile
    public void getUserProfile(String userId, ValueEventListener listener) {
        mUsersRef.child(userId).addValueEventListener(listener);
    }

    // User Online Status
    public void updateUserOnlineStatus(boolean online) {
        String userId = getCurrentUserId();
        if (userId != null) {
            Map<String, Object> updates = new HashMap<>();
            updates.put("online", online);
            if (!online) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy HH:mm", Locale.getDefault());
                String lastSeenTime = sdf.format(new Date());
                updates.put("lastSeen", lastSeenTime);

                // Also update in UserModel
                if (currentUser != null) {
                    currentUser.setLastSeen(lastSeenTime);
                }
            }
            mUsersRef.child(userId).updateChildren(updates);
        }
    }

    // MODIFIED: Get all users for chat
    public void getAllUsers(ValueEventListener listener) {
        mUsersRef.addListenerForSingleValueEvent(listener);
    }

    // Messages
    public void sendMessage(String receiverId, String content, OnCompleteListener<Void> listener) {
        String senderId = getCurrentUserId();
        if (senderId == null) return;

        // Create a new message ID
        String messageId = mMessagesRef.push().getKey();
        if (messageId == null) return;

        // Create message object
        long timestamp = System.currentTimeMillis();
        Message message = new Message(messageId, senderId, receiverId, content, timestamp);

        // Save the message
        mMessagesRef.child(messageId).setValue(message)
                .addOnCompleteListener(listener);

        // Update conversation for sender
        updateConversation(senderId, receiverId, messageId, content, timestamp, false);

        // Update conversation for receiver
        updateConversation(receiverId, senderId, messageId, content, timestamp, true);
    }

    private void updateConversation(String userId, String partnerId, String lastMessageId,
                                    String lastMessageContent, long timestamp, boolean incrementUnread) {
        Map<String, Object> conversationMap = new HashMap<>();
        conversationMap.put("lastMessageId", lastMessageId);
        conversationMap.put("lastMessageContent", lastMessageContent);
        conversationMap.put("timestamp", timestamp);

        // Add or update the conversation
        if (incrementUnread) {
            // For receiver: increment unread count
            mConversationsRef.child(userId).child(partnerId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Map<String, Object> receiverUpdate = new HashMap<>(conversationMap);

                    // Increment unread count if the conversation exists
                    if (snapshot.exists() && snapshot.child("unreadCount").exists()) {
                        long currentUnreadCount = (long) snapshot.child("unreadCount").getValue();
                        receiverUpdate.put("unreadCount", currentUnreadCount + 1);
                    } else {
                        receiverUpdate.put("unreadCount", 1);
                    }

                    mConversationsRef.child(userId).child(partnerId).updateChildren(receiverUpdate);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.e(TAG, "Failed to update conversation: " + error.getMessage());
                }
            });
        } else {
            // For sender: reset unread count
            conversationMap.put("unreadCount", 0);
            mConversationsRef.child(userId).child(partnerId).updateChildren(conversationMap);
        }
    }

    public void getMessages(String partnerId, ValueEventListener listener) {
        String currentUserId = getCurrentUserId();
        if (currentUserId == null) return;

        Query query = mMessagesRef.orderByChild("timestamp");
        query.addValueEventListener(listener);
    }

    public void markMessagesAsRead(String partnerId) {
        String currentUserId = getCurrentUserId();
        if (currentUserId == null || partnerId == null) return;

        // Reset unread count
        mConversationsRef.child(currentUserId).child(partnerId).child("unreadCount").setValue(0);

        // Find unread messages from this sender
        Query query = mMessagesRef.orderByChild("senderId").equalTo(partnerId);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot messageSnapshot : snapshot.getChildren()) {
                    Message message = messageSnapshot.getValue(Message.class);
                    if (message != null && message.getReceiverId().equals(currentUserId) && !message.isReadBy(currentUserId)) {
                        // Mark as read
                        mMessagesRef.child(message.getMessageId()).child("readBy").child(currentUserId).setValue(true);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "Failed to mark messages as read: " + error.getMessage());
            }
        });
    }

    // Conversations
    public void getConversations(ValueEventListener listener) {
        String userId = getCurrentUserId();
        if (userId != null) {
            mConversationsRef.child(userId).orderByChild("timestamp").addValueEventListener(listener);
        }
    }

    // DEPRECATED: Use getTestPartnerId() or getAllUsers() instead
    public void getTestPartnerId(ValueEventListener listener) {
        mUsersRef.orderByChild("role").equalTo("buyer").limitToFirst(1).addListenerForSingleValueEvent(listener);
    }

    public boolean isCurrentUserSeller() {
        if (currentUser != null) {
            return currentUser.hasStore(); // Check if user has a store (is a seller)
        }
        return false;
    }
}