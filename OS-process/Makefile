all: retrieve

retrieve: retrieve.c util.o util.h
	gcc -g -o retrieve util.o retrieve.c -pthread

util.o: util.c util.h
	gcc -g -c util.c
