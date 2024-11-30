For this release, we will be using gradle to build our project.
1. Start by cloning the git repository \
https://github.com/CPEN-221-2024/project-galmod-mealprepmoggers/tree/main

2. pull the latest version by running `git pull`
3.  To run the app, edit the run configuration to set
    the local env variable KEY. Below we have 4 api keys
    available to use, although each key caps out at 150
    requests per day. If using powershell, you can set this through
    `$env:KEY = "api_key"`
4. If you are running this through intellj, you should be able to set
configuration through the gui and run the program.
5. Alternatively, run `gradle build` followed by `gradle run`
6. You should now see a window with the application. Refer to the video
linked to see a demo.


API KEYS: \
KEY=a251c92995264dbfaa72fe29e0b5ccce
   KEY=c902738d454d48068e653e0b16bb9c5c
   KEY=256149fb49c348a99fd5d99d1579f4f4
   KEY=f4834a938888400b8b1dfbdd9f49e5ef

*We are working on "dockerizing" our project, although this is currently a
work in progress as we trouble shoot some dependency
issues related to docker. I have spoken to a TA
about this and was told to just submit what we have
for this milestone.\
-Mo*