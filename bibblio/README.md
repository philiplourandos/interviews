# Requirements 

Java 8 Update 101/102. Install can be found at http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html

# Running

Run the following:

```
git clone https://github.com/philiplourandos/interviews.git
cd bibblio
./gradlew clean build assemble
cd build/libs
java -jar bibblio.jar "5 3" "1 1 E" "RFRFRFRF" "3 2 N" "FRRFLLFFRRFLL" "0 3 W" "LLFFFLFLFL"
```
