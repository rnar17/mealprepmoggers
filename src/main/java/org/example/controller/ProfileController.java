package org.example.controller;
import org.example.model.UserModel.FitnessGoals;
import org.example.model.UserModel.Profile;
import com.google.gson.Gson;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * This class serves as a controller for the profile view.
 * <p>
 * Representation Invariant:
 * 1. user is not null
 * 2. profilePath is non-empty and not null
 * 3. gson1 is not null
 * 4. gson2 is not null
 * <p>
 *  Abstraction Function:
 *  AF(c) = A controller for managing user profiles where:
 *  1. c.user = the user's profile containing their name, age, weight, height, and fitness goals
 *  2. c.profilePath = the file path to the JSON file storing the user's profile
 */
public class ProfileController {
    Profile user;
    String profilePath = "src/main/User/Profile.json";
    Gson gson1 = new Gson();
    Gson gson2 = new Gson();

    /**
     * A helper method that checks the state of the presentation invariant.
     */
    public void checkRep() {
        if (user == null) {
            throw new IllegalStateException("User is null");
        }

        if (profilePath == null || profilePath.isEmpty()) {
            throw new IllegalStateException("Profile path is null or empty");
        }

        if (gson1 == null || gson2 == null) {
            throw new IllegalStateException("Gson instances are null");
        }
    }

    /**
     * Fetches the user's profile data from the JSON file located at {@code profilePath}
     * and deserializes it into a {@link Profile} object.
     *
     * @return the {@link Profile} object containing the user's profile data.
     * @throws RuntimeException if an IOException occurs during reading from the file.
     */
    public Profile fetchProfile(){
        try (FileReader reader = new FileReader(profilePath)){
            user = gson1.fromJson(reader, Profile.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    /**
     * Updates the user's profile with the provided details and writes it back to the JSON file at {@code profilePath}.
     *
     * @param userName the user's name.
     * @param userAge the user's age.
     * @param userWeight the user's weight.
     * @param userHeight the user's height.
     * @param userGoal the user's fitness goal.
     * @return {@code true} if the profile was successfully updated, {@code false} otherwise.
     */
    public boolean updateProfile(String userName, int userAge, int userWeight, int userHeight, FitnessGoals userGoal){
        try (FileWriter writer = new FileWriter(profilePath)) {
            gson2.toJson(new Profile(userName,userAge,userWeight, userHeight, userGoal), writer);
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    /**
     * Retrieves the user's fitness goal.
     * If no goal is set, returns {@link FitnessGoals#NONE}.
     *
     * @return the user's fitness goal or {@link FitnessGoals#NONE} if no goal is set.
     */
    public FitnessGoals getGoal(){
        if(user.goal == null){
            return FitnessGoals.NONE;
        }
        else return user.goal;
    }



}




