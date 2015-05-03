test: mm.o util.o test.c
	gcc -g -o mm test.c mm.o util.o

mm.o: mm.c mm.h util.h
	gcc -g -c mm.c -o mm.o

util.o: util.c util.h
	gcc -g -c util.c -o util.o

run: test
	./mm

