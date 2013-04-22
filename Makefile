all: build run

build:
	javac -cp src:drivers:interfaces:test src/BicycleGarage.java

run:
	java -cp src:drivers:interfaces:test BicycleGarage
