package com.example.socialmediagamer.providers;

import com.example.socialmediagamer.models.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class UserProvider {

    private CollectionReference mCollection;

    public UserProvider() {
        mCollection = FirebaseFirestore.getInstance().collection("Users");
    }

    public Task<DocumentSnapshot> getUser(String id) {

        return mCollection.document(id).get();
    }

    public Task<Void> create(User usr) {

        return mCollection.document(usr.getId()).set(usr);
    }

    public Task<Void> update(User user) {

        Map<String, Object> map = new HashMap<>();
        map.put("userName", user.getUserName());
        return  mCollection.document(user.getId()).update(map);
    }




}
