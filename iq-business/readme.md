# Requirements

Please write a program to simulate a twitter-like feed. Your program will receive two seven-bit ASCII files. The first file contains a list of users and their followers. The second file contains tweets. Given the users, followers and tweets, the objective is to display a simulated twitter feed for each user to the console. 

The program should be well designed, handle errors and should be of sufficient quality to run on a production system. Indicate all assumptions made in completing the assignment. Please email the source code along with related build files.

Each line of a well-formed user file contains a user, followed by the word ‘follows’ and then a comma separated list of users they follow.  Where there is more than one entry for a user,  consider the union of all these entries to determine the users they follow.

Lines of the tweet file contain a user, followed by greater than, space and then a tweet that may be at most 140 characters in length. The tweets are considered to be posted by the each user in the order they are found in this file.

Your program needs to write console output as follows. For each user / follower (in alphabetical order) output their name on a line. Then for each tweet, emit a line with the following format: <tab>@user: <space>message.

Here is an example. Given user file named user.txt:

```
Ward follows Alan
Alan follows Martin
Ward follows Martin, Alan
```

And given tweet file named tweet.txt:
```
Alan> If you have a procedure with 10 parameters, you probably missed some.
Ward> There are only two hard things in Computer Science: cache invalidation, naming things and off-by-1 errors.
Alan>Random numbers should not be generated with a method chosen at random.
```
Invoking your program with user.txt and tweet.txt as arguments should produce the following console output:

```
Alan
    @Alan: If you have a procedure with 10 parameters, you probably missed some.
    @Alan: Random numbers should not be generated with a method chosen at random.
Martin
Ward
    @Alan: If you have a procedure with 10 parameters, you probably missed some.
    @Ward:There are only two hard things in Computer Science: cache invalidation, naming things and off-by-1 errors.
    @Alan: Random numbers should not be generated with a method chosen at random.
```

# Assumptions

+ Based on the expected file content and output listed in the *Requirements* section and the stated objectives I believe the actual tweet file look like:
```
Alan> If you have a procedure with 10 parameters, you probably missed some.
Ward> There are only two hard things in Computer Science: cache invalidation, naming things and off-by-1 errors.
Alan> Random numbers should not be generated with a method chosen at random.
```
and the expected output look like:
```
Alan
    @Alan: If you have a procedure with 10 parameters, you probably missed some.
    @Alan: Random numbers should not be generated with a method chosen at random.
Martin
Ward
    @Alan: If you have a procedure with 10 parameters, you probably missed some.
    @Ward: There are only two hard things in Computer Science: cache invalidation, naming things and off-by-1 errors.
    @Alan: Random numbers should not be generated with a method chosen at random.
```
+ User account names in the system are expected to be unique.
+ Users defined in the user file will have followers.
+ As the program stores the users and tweets in memory, there will an upper limit based on memory to the users and tweets to process.
+ If there is a tweet from a user that is not present in the user file, it will not be displayed.

# Software Requirements

1. Java 7 Update 55 installed and $JAVA_HOME set correctly
2. Git 1.9.1
3. A linux/MacOS X system, or cygwin installed on windows.

# Building the project

Listed below are the instructions to checkout and build the assessment project.

1. git clone https://github.com/philiplourandos/iq-business-assessment.git
2. ./gradlew clean dist (Note: This will download gradle and install it for you, and may take a while.)

The distributable artifact will be in *./build/distribution*.

# Running the application

1. Copy the zip file to the directory you want to place the application and go to the directory on the command line once finished copying.
2. unzip ag-twitter-0.0.1.zip
3. cd ag-twitter/bin
4. To show usage type: ./run.sh --help
5. To run application type: ./run.sh --tweets ../data/tweets.txt --users ../data/users.txt