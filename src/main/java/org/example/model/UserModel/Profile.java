package org.example.model.UserModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This class represents a user's personal profile for the meal prepping application.
 * <p>
 * Representation Invariant:
 * 1. name is not null and not an empty string
 * 2. age is a positive integer
 * 3. weight is a positive integer
 * 4. height is a positive integer
 * 5. allergies list is not null
 * <p>
 * Abstraction Function:
 * AF(r) = A user profile representing an individual's personal health and dietary information where:
 * 1. r.name = the user's name
 * 2. r.age = user's age in years
 * 3. r.weight = user's weight
 * 4. r.height = user's height
 * 5. r.allergies = list of food allergies
 * 6. r.restriction = dietary restrictions (e.g., vegetarian, vegan)
 * 7. r.goal = current fitness goal
 */
public class Profile {
    public String name;
    public int age;
    public int weight;
    public int height;
    public FitnessGoals goal;

    /**
     * Constructs a basic user profile with minimal details.
     *
     * @param name   User's name
     * @param age    User's age
     * @param weight User's weight
     * @param height User's height
     * @param goal   User's fitness goal
     */
    public Profile(String name, int age, int weight, int height, FitnessGoals goal){
        this.name = name;
        this.age = age;
        this.weight = weight;
        this.height = height;
        this.goal = goal;
    }

    /**
     * Sets the user's fitness goal.
     *
     * @param goal The new fitness goal to be set
     */
    public void setGoal(FitnessGoals goal) {
        checkRep();
        this.goal = goal;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Profile)) {
            return false;
        }
        Profile profile = (Profile) obj;

        return age == profile.age &&
            weight == profile.weight &&
            height == profile.height &&
            (name.compareTo(profile.name) == 0) &&
            goal == profile.goal;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age, weight, height, goal);
    }


    /**
     * Checks the representation invariants for the Profile.
     * <p>
     * Verifies:
     * - name is not null and not an empty string
     * - age is a positive integer
     * - weight is a positive integer
     * - height is a positive integer
     * - allergies list is not null
     * - age, weight, and height represent realistic human values
     *
     * @throws AssertionError if any representation invariant is violated
     */
    private void checkRep() {
        // Check name
        assert name != null : "Name cannot be null";
        assert !name.trim().isEmpty() : "Name cannot be an empty string";

        // Check age
        assert age > 0 : "Age must be a positive integer";

        // Check weight
        assert weight > 0 : "Weight must be a positive integer";

        // Check height
        assert height > 0 : "Height must be a positive integer";

        // Check allergies
        //assert allergies != null : "Allergies list cannot be null";

    }
}
