# Evaluating team tarannon (TraderBird):


## Issues Found

### 1. **Missing Setup Instructions**
   We had difficulty finding the file that instructs users on how to download and use the app. Without proper setup instructions, it was challenging to begin the evaluation of the app. It made it unclear how to get the app running on our local machines.

   We suggest to add a clear `README.md` or a setup-specific documentation file in the repository that explains the steps required to download, set up, and run the app. Right now the `README.md` file in the frontend folder does not have a simple step-by-step guide. It has a bunch of information which can be confusing for someone not familiar with React.


### 2. **Broken Register Page (Localhost Undefined Error)**
   When attempting to visit the register page, the app displayed an error message: "localhost undefined". This error prevented us from moving forward with testing the registration functionality and halted further evaluation.

   The error stopped us from continuing our testing and further investigating the functionality of the app. We recommend adding error handling to provide more helpful error messages that clarify what went wrong, instead of showing an undefined message.


## Conclusion

These two issues hindered our ability to fully evaluate TraderBird. Addressing the setup documentation and fixing the broken register page would make it easier to begin proper evaluation and testing. We recommend focusing on these two issues as a priority.
