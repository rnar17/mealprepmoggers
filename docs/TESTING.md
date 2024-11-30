* All tests are located under src in the tests directory
./src/tests

* To run the tests edit the test configuration to set
the local env variable KEY. Below we have 4 api keys
available to use, although each key caps out at 150
requests per day.

KEY=a251c92995264dbfaa72fe29e0b5ccce
KEY=c902738d454d48068e653e0b16bb9c5c
KEY=256149fb49c348a99fd5d99d1579f4f4
KEY=f4834a938888400b8b1dfbdd9f49e5ef

*There is a considerable amount of view classes that do not have a focus for automated testing as it 
is difficult and time consuming given the amount of changes being added. We also decided to opt out of 
making the API calls in parallel as the overhead and increased complexity did not produce any substantial benefits
in performance. As such, the test coverage for these classes is low. The functionality testing of the front facing UI was
largely done manually. However, for backend implementations and controllers, there is 90%+ line and branch coverage
for methods that are used. One final note is that the tests for the API are reliant on the API and also require
you to set the env KEY locally. We have made testing this so that it is easy to change later on if need be*

* We are working on "dockerizing" our project, which
includes autotests, although this is currently a 
work in progress as we trouble shoot some dependency
issues related to docker. I have spoken to a TA
about this and was told to just submit what we have
for this milestone.
-Mo
