all: main

main: main.o hello-tp.o
	gcc -o main.x main.o hello-tp.o -llttng-ust -ldl

main.o: main.c
	gcc -c main.c

hello-tp.o: hello-tp.c hello-tp.h
	gcc -c -I. hello-tp.c

clean:
	rm -rf *.o *.x
