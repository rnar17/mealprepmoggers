# Evaluating team tarannon (TraderBird):


## Issues Found

### 1. Missing Setup Instructions
   We had difficulty finding the file that instructs users on how to download and use the app. Without proper setup instructions, it was challenging to begin the evaluation of the app. It made it unclear how to get the app running on our local machines.

   We suggest to add a clear `README.md` or a setup-specific documentation file in the repository that explains the steps required to download, set up, and run the app. Right now the `README.md` file in the frontend folder does not have a simple step-by-step guide. It has a bunch of information which can be confusing for someone not familiar with React.

### 2. Broken Register Page (Localhost Undefined Error)
   When attempting to visit the register page, the app displayed an error message: "localhost undefined". This error prevented us from moving forward with testing the registration functionality and halted further evaluation.

   The error stopped us from continuing our testing and further investigating the functionality of the app. We recommend adding error handling to provide more helpful error messages that clarify what went wrong, instead of showing an undefined message.

### Note that the above issues were fixed after the group replied to us with a link to a website that worked. This link was not provided in any of the documents they had in their repository at the time of starting our evaluation.

### 3. Missing Tests
   Although stated in the `README.md` that there were extensive tests done, running the tests using the instructions found only revealed 1 test ran. Because of this contradiction, our team thinks there is an error with running the tests or there was insufficient testing.

### 4. Tab Refresh Issue
   Clicking tabs refreshes the login button and users are able to click it.

### 5. Multiple Decimals are able to be added to prices.

### 6. Long comments extend off the page of posts

### 7. The timestamp on posts are around a day ahead of what time the post was actually created from.

### 8. Creating a post with a large image prompts a message to say the post "failed", however the post still posts on the website, and the image would never load.

## Conclusion

To find these bugs, our team thought of ideas to that could potentially "break" TraderBird. Doing things like spamming buttons and creating posts with extreme comments were images were how we were able to come up with such bugs. Addressing the setup documentation was what our team deemed was the most prominant bug.
