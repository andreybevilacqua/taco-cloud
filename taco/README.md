## Intro
Taco Cloud is a place where you can purchase tacos online. Taco Cloud wants to enable its customers to express their creative side and to design custom tacos from a rich palette of ingredients.

Spring In Action book exercise.

To start the backend: `java -jar taco-1.0-SNAPSHOT.jar`
To run ActiveMQ Artemis:
    - Download and unzip ActiveMQ Artemis;
    - Create an ARTEMIS_HOME variable in .bash_profile;
    - Create (and go to) a folder inside $ARTEMIS_HOME called: brokers;
    - run: $ARTEMIS_HOME/bin/./artemis create tacoBroker;
    - run: $ARTEMIS_HOME/brokers/tacoBroker/bin/./artemis run

To start the frontend: `ng serve --ssl true --ssl-key "/Users/andreybevilacqua/Documents/java/workspace/taco/mykeys.jks"`
