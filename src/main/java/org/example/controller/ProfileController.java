package org.example.controller;
import org.example.model.UserModel.FitnessGoals;
import org.example.model.UserModel.Profile;
import com.google.gson.Gson;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * Manages user profile data and interactions for the Meal Prep Assistant application.
 *
 * Representation Invariant:
 * 1. profilePath is not null and points to a valid JSON file
 * 2. gson is not null
 * 3. reader is not null and successfully opened
 * 4. user is not null
 * 5. user contains valid profile information
 *
 * Abstraction Function:
 * A profile controller representing:
 * 1. r.profilePath = file path to the user's profile JSON
 * 2. r.user = current user profile with personal and fitness information
 * 3. r.gson = JSON serialization/deserialization utility
 */
public class ProfileController {

    /**
     * A helper method that checks the state of the presentation invariant.
     */
    private void checkRep() {
        if (profilePath == null || profilePath.isEmpty()) {
            throw new IllegalStateException("Representation invariant violated: profilePath is null or empty");
        }

        if (gson == null) {
            throw new IllegalStateException("Representation invariant violated: gson is null");
        }

        if (reader == null) {
            throw new IllegalStateException("Representation invariant violated: reader is null");
        }

        if (user == null) {
            throw new IllegalStateException("Representation invariant violated: user is null");
        }
    }

    //TODO update profile.JSON through profileView
     String profilePath = "src/main/User/Profile.json";
     Gson gson = new Gson();


    FileReader reader;
    {
        try {
            reader = new FileReader(profilePath);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public Profile user = gson.fromJson(reader, Profile.class);

    /**
     * Updates the user profile with new information.
     *
     * Requires:
     * - reader is pointing to a valid JSON file
     *
     * Modifies:
     * - user profile attributes
     *
     * Effects:
     * - Reads updated profile from JSON file
     * - Replaces current user profile attributes
     *
     * @return boolean indicating if update was successful
     */
    public boolean updateProfile(){
        Profile update = gson.fromJson(reader, Profile.class);
        user.name = update.name;
        user.age = update.age;
        user. weight = update.weight;
        user.height = update.height;

        //add condition for successful update
        return true;
    }

    /**
     * Retrieves the user's fitness goal.
     *
     * Effects:
     * - Returns the user's current fitness goal
     * - Defaults to MAINTENANCE if no goal is set
     *
     * @return FitnessGoals representing the user's fitness goal
     */
    public FitnessGoals getGoal(){
        if(user.goal == null){
            return FitnessGoals.MAINTENANCE;
        }
        else return user.goal;
    }


}
