all: build run

build:
	javac -cp src:drivers:interfaces:test src/Garage.java

run:
	java -cp src:drivers:interfaces:test Garage
