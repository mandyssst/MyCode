/*******************************
 * main.c
 *
 * Source code for main
 *
 ******************************/

#include "util.h"
#include "main.h"
#include <unistd.h>
#include <string.h>
#include <stdio.h>
#include <stdlib.h>

/*********
 * Simple usage instructions
 *********/
void custmake_usage(char* progname) {
	fprintf(stderr, "Usage: %s [options] [target]\n", progname);
	fprintf(stderr, "-f FILE\t\tRead FILE as a custMakefile.\n");
	fprintf(stderr, "-h\t\tPrint this message and exit.\n");
	fprintf(stderr, "-n\t\tDon't actually execute commands, just print them.\n");
	exit(0);
}

/****************************** 
 * this is the function that, when given a proper filename, will
 * parse the custMakefile and read in the targets and commands
 ***************/
int parse_file(tNode* head, char* filename, char* commandTarget) {
	char* line = (char*) malloc(160*sizeof(char));
	FILE* fp = file_open(filename);
	tNode* currentNode;
	int currentNodeCommandIndex = 0;
	while((line = file_getline(line, fp)) != NULL) {
	
		// this loop will go through the given file, one line at a time
		// this is where you need to do the work of interpreting
		// each line of the file to be able to deal with it later

		char* colonPtr = strchr(line, ':'); // pointer to :
		if (colonPtr != NULL) { // target and dependencies line
			int targetLength = (int) (colonPtr - line);

			// "target" will have the target name
			char* target = (char*) malloc(sizeof(char) * (targetLength+1));
			strncpy(target, line, targetLength);
			target[targetLength] = '\0';

			/******
			 *target: target of the current line in the file
		 	*head->target: target of the root of the tree
			 *commandTarget: the target passed in from the command line (NULL if nothing passed in)
			 ******/
			if (head->target == NULL) {				
				// if no target is specified by the command line
				// or the target passed in by the command line maches the target of current line
				// we will make this target the root of the tree we're going to build
				if (commandTarget == NULL || strcmp(commandTarget,target) == 0) {
					currentNode = tAdd(head, target);
					currentNode->targetExists = 1;
				} else {
					continue;
				}

			} else { // if the root of the tree has been defined
				currentNode = tAdd(head, target);
				if (currentNode == NULL) {
					continue; // if the target in the file does not belong to the tree we are building
				} else {
					currentNode->targetExists = 1;
				}
			}

			char* dependencies = (char*) malloc(sizeof(char) * (strlen(line)-targetLength+1));
			strcpy(dependencies, line+targetLength+2);
			dependencies[strlen(line)-targetLength] = '\0';

			char* segment = strtok(dependencies, " :\n");
			while (segment != NULL) {

				tNode* newNode = (tNode*) malloc(sizeof(tNode));
				newNode->target = segment;
				lAdd(&(currentNode->children), newNode);

				segment = strtok(NULL, " :\n");
			}

		} else if (strncmp(line, "end", strlen("end")) == 0) { //end

			currentNode = NULL;
			currentNodeCommandIndex = 0;
		} else { // command lines
			if (currentNode != NULL) {
				if (currentNode->commands == NULL) {
					//currentNode->commands = (char**) malloc(sizeof(char*));
					currentNode->commands = (char**) malloc(10000);
				}
				currentNode->commands[currentNodeCommandIndex] = (char*) malloc(sizeof(char) * (strlen(line)+1));
				strcpy(currentNode->commands[currentNodeCommandIndex], line);
				currentNode->numOfCommands++;
				currentNodeCommandIndex++;
			}
		}
	}

	if(fclose(fp) != 0){
		perror("Error: closing file\n");
		return -1;
	}
	free(line);
	return 0;
}

int main(int argc, char* argv[]) {
	// Declarations for getopt
	extern int optind;
	extern char* optarg;
	int ch;
	char* format = "f:hn";
	
	// Variables you'll want to use
	char* filename = "custMakefile";
	bool execute = true;

	tNode* head = (tNode*) malloc(sizeof(tNode));

	// Use getopt code to take input appropriately (see section 3).
	while((ch = getopt(argc, argv, format)) != -1) {
		switch(ch) {
			case 'f':
				filename = strdup(optarg);
				break;
			case 'n':
				execute = false;
				break;
			case 'h':
				custmake_usage(argv[0]);
				break;
		}
	}
	argc -= optind;
	argv += optind;

	/* at this point, what is left in argv is the target that was
	specified on the command line. If getopt is still really confusing,
	try printing out what's in argv right here, then just run
	custmake with various command-line arguments. */

	if (parse_file(head, filename, argv[0]) == -1) {
		perror("ERROR: Failed parsing the file!\n");
	}
	/* after parsing the file, you'll want to execute the target
	that was specified on the command line, along with its dependencies, etc. */
	
	if (execute == true) {
		postTraverse(head);
	} else {
		postTraverseN(head);
	}

	return 0;
}
