mkdir build
javac -d ./build/ src/utils/*.java
jar cvf orquestrador.jar build/*
