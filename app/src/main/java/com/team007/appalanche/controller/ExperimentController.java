package com.team007.appalanche.controller;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.team007.appalanche.Experiment.Experiment;
import com.team007.appalanche.user.User;
import com.team007.appalanche.user.UserDocument;

import java.util.ArrayList;

/**
 * This view model maps the type of experiment to display (owned or subscribed) based on the index
 * - 0 maps to owned experiments
 * - 1 maps to subscribed experiments
 */

@SuppressWarnings("UnusedAssignment")
public class ExperimentController extends ViewModel {
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private MutableLiveData<Integer> experimentType = new MutableLiveData<>();
    private ArrayList<Experiment> ownedExperiments = new ArrayList<Experiment>();
    private ArrayList<Experiment> subscribedExperiments = new ArrayList<Experiment>();
    private String currentUserKey;

    public void setExperimentType(int index) {
        experimentType.setValue(index);
    }

    public ArrayList<Experiment> getExperiments () {
        if (experimentType.getValue() == 0) {
            return ownedExperiments;
        } else {
            return subscribedExperiments;
        }
    }

    public void loadExperiments() {
        // do firebase stuff by loading in the owned and subscribed experiments
        DocumentReference userDoc = db.collection("Users").document(currentUserKey);
        final User[] currentUser = new User[1];

//        userDoc.addSnapshotListener(new EventListener<QuerySnapshot>() {
//            @Override
//            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
//                // clear the old list
//                ownedExperiments.clear();
//                subscribedExperiments.clear();
//                for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
//                    Log.d(TAG, String.valueOf(doc.getData().get("user_posted_question")));
//                    ownedExperiments =  (ArrayList<Experiment>) userDoc.get("ownedExperiments");
//                    subscribedExperiments = (ArrayList<Experiment>) userDoc.get("subsribedExperiments");
//
//                    questionList.addQuestion(new Question(question, new User(user, null), new Date()));
//                }
//            }});
        userDoc.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    ownedExperiments = document.toObject(UserDocument.class).users;
                    //subscribedExperiments = document.toObject(UserDocument.class).users;
                }
            }
        });


        userDoc.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                currentUser[0] = documentSnapshot.toObject(User.class);
//                //ADDED THIS
                currentUser[0].setID(currentUserKey);

            }
        });

        if (currentUser[0] != null) {
            ownedExperiments = (ArrayList<Experiment>) currentUser[0].getOwnedExperiments();
            subscribedExperiments = (ArrayList<Experiment>) currentUser[0].getSubscribedExperiments();
        }

    }
    public void setCurrentUser(String userKey) {
        this.currentUserKey = userKey;
    }

    public void addExperiment(Experiment experiment) {
        DocumentReference newExperiment = db.collection("Experiments").document();
        newExperiment.set(experiment);
    }
}