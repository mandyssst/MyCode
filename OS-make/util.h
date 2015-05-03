/********************
 * util.h
 *
 * You may put your utility function definitions here
 * also your structs, if you create any
 *********************/

#include <stdio.h>

// the following ifdef/def pair prevents us from having problems if 
// we've included util.h in multiple places... it's a handy trick
#ifndef _UTIL_H_
#define _UTIL_H_

// This stuff is for easy file reading
FILE* file_open(char*);
char* file_getline(char*, FILE*);

struct treeNode;
struct listNode;

/*** Build a tree ***/
typedef struct treeNode {
    char* target;
    char** commands;
    int numOfCommands;
    int targetExists;
    struct listNode* children;
} tNode;

/*** Build a linkedlist ***/
typedef struct listNode {
    struct treeNode* data;
    struct listNode* next;
} lNode;

void lAdd(lNode**, tNode*);
tNode* tAdd(tNode*, char*);
tNode* tTraverse(tNode*, const char*);
void postTraverse(tNode*);
void postTraverseN(tNode*);
void freemakeargv(char**);
int makeargv(const char*, const char*, char***);
#endif
