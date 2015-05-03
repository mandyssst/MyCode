/************************
 * util.c
 *
 * utility functions
 *
 ************************/

#include "util.h"
#include "main.h"
#include <stdlib.h>
#include <stdio.h>
#include <unistd.h>
#include <errno.h>
#include <sys/stat.h>
#include <fcntl.h>


/***************
 * These functions are just some handy file functions.
 * We have not yet covered opening and reading from files in C,
 * so we're saving you the pain of dealing with it, for now.
 *******/
FILE* file_open(char* filename) {
	FILE* fp = fopen(filename, "r");
	if(fp == NULL) {
		fprintf(stderr, "ERROR: while opening file %s, abort.\n", filename);
		exit(1);
	}
	return fp;
}

/*******************
 * To use this function properly, create a char* and malloc 
 * 160 bytes for it. Then pass that char* in as the argument
 ******************/
char* file_getline(char* buffer, FILE* fp) {
	buffer = fgets(buffer, 160, fp);
	return buffer;
}

void lAdd(lNode** headPtr, tNode* data) {

	lNode* newNode = (lNode*) malloc(sizeof(lNode));
	newNode->data = data;

	if (*headPtr == NULL) {
		*headPtr = newNode;
	} else {

		lNode* cursor = *headPtr;
		while (cursor->next != NULL) {
			cursor = cursor->next;
		}
		cursor->next = newNode;
	}
}

tNode* tAdd(tNode* head, char* target) {
   
	//TODO: get value from one string to another

	char* target_name = (char*) malloc(sizeof(char) * (strlen(target)+1));
	strcpy(target_name, target);
	target_name[strlen(target)] = '\0';

	tNode* addedNode;
	if (head->target == NULL) {
		head->target = target;
		addedNode = head;
	} else {
		addedNode = tTraverse(head, target);
		if (addedNode != NULL) {
			addedNode->target = target;
		}
	}
	return addedNode;
}

// traverse the tree and find the node according to the target name
tNode* tTraverse(tNode* head, const char* target_name) {

	lNode* tmpLHead = NULL;
	lAdd(&tmpLHead, head);

	lNode* cursor = tmpLHead;

	while (cursor != NULL) {
		if (cursor->data != NULL && cursor->data->children != NULL) {
			lNode* innerCursor = cursor->data->children;
			while (innerCursor != NULL) {
				lAdd(&tmpLHead, innerCursor->data);
				innerCursor = innerCursor->next;
			}
		}
		cursor = cursor->next;
	}

	cursor = tmpLHead;
	while (cursor != NULL) {

		if (cursor->data != NULL) {
			tNode* currentData = cursor->data;
			char* currentTarget = currentData->target;
			
			if (currentTarget != NULL && strcmp(currentTarget, target_name) == 0) {
				return currentData;
			}
		}
		cursor = cursor->next;
	}
	return NULL;
}

// post traverse the tree and execute the commands
void postTraverse(tNode* tnode) {

	lNode* children = tnode->children;
	while (children != NULL && children -> data != NULL) {
		postTraverse(children->data);	   
		children = children->next;
	}
	
	int child_pid;
	int i;

	if (tnode != NULL) {
		if (tnode->targetExists == 0) {
			printf("WARN: target %s does not exist!\n", tnode->target);
		}
	}
	for (i=0; tnode != NULL && tnode->commands != NULL && i < tnode->numOfCommands; i++) {

		child_pid = fork();
		if(child_pid == -1){
			perror("ERROR: Failed to fork\n");
			return -1;
		}
		
		if (child_pid == 0) { //child process
		  
			char** argv;
			char* command_str = tnode->commands[i];
			
			if (strstr(command_str, ",") != NULL) {
				int inner_child_pid;

				int inner_commands_count = makeargv(command_str, ",\n", &argv);
				int j;
				for (j = 0; j<inner_commands_count;j++) {
	   				char** inner_argv;
					int inner_most_commands_count = makeargv(argv[j], " \n", &inner_argv);
					inner_child_pid = fork();

					if(inner_child_pid == -1){
						perror("ERROR: Failed to fork\n");
						return -1;
					}

					if (inner_child_pid == 0) { //child

						execvp(inner_argv[0], inner_argv);
						perror("ERROR: Child failed to exec\n");
						return -1;
					} else if (inner_child_pid>0) {
						int inner_cpid;
						while(inner_child_pid != inner_cpid) {
							inner_cpid = waitpid(inner_child_pid, NULL, WNOHANG);
							//sleep(1);
						}
					}
				}
		 		exit(0); //Important!
			} else {

				int arg_number = makeargv(tnode->commands[i], " \n", &argv);

				execvp(argv[0], argv);
				perror("ERROR: Parent failed to exec\n");
				return -1;
			}
			
		} else if (child_pid > 0) {

			int cpid;
			while (child_pid != cpid) {
				cpid = waitpid(child_pid, NULL, WNOHANG);
				//sleep(1);
			}
		}
	}
}

//traverse the tree but not execute the commands
void postTraverseN(tNode* tnode) {

	lNode* children = tnode->children;
	while (children != NULL && children -> data != NULL) {
		postTraverseN(children->data);	   
		children = children->next;
	}

	if (tnode != NULL) {
		if (tnode->targetExists == 0) {
			printf("WARN: target %s does not exist!\n", tnode->target);
		}
	}

	int child_pid;
	int i;
	for (i=0; tnode != NULL && tnode->commands != NULL && i < tnode->numOfCommands; i++) {
		
		char* command_str = tnode->commands[i];
		char* nonBlankPtr = command_str;
		int j;
		for (j=0; j<strlen(command_str); j++) {
			if (command_str[j] == ' ') {
				nonBlankPtr++;
			} else {
				break;
			}
		}
		printf("%s", nonBlankPtr);
                //printf("%s", command_str);
	}
}

void freemakeargv(char **argv) {
	if (argv == NULL)
		return;
	if (*argv != NULL)
		free(*argv);
	free(argv);
}

int makeargv(const char *s, const char *delimiters, char ***argvp) {
	int error;
	int i;
	int numtokens;
	const char *snew;
	char *t;

	if ((s == NULL) || (delimiters == NULL) || (argvp == NULL)) {
		errno = EINVAL;
		return -1;
	}
	*argvp = NULL;
	snew = s + strspn(s, delimiters);
	if ((t = malloc(strlen(snew) + 1)) == NULL)
		return -1;
	strcpy(t,snew);

	numtokens = 0;
	if (strtok(t, delimiters) != NULL)
		for (numtokens = 1; strtok(NULL, delimiters) != NULL; numtokens++) ;

	if ((*argvp = malloc((numtokens + 1)*sizeof(char *))) == NULL) {
		error = errno;
		free(t);
		errno = error;
		return -1;
	}

	if (numtokens == 0)
		free(t);
	else {
		strcpy(t,snew);
		**argvp = strtok(t,delimiters);
		for (i=1; i<numtokens; i++)
			*((*argvp) +i) = strtok(NULL,delimiters);
	}
	*((*argvp) + numtokens) = NULL;
	return numtokens;
}
