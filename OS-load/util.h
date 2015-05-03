#ifndef _UTIL_H_
#define _UTIL_H_

#include <stdbool.h>

typedef struct listNode {
	char* mem_ptr;
	int mem_size;
	bool occupied;

	struct listNode* next;
} lNode;

char* lFindSlot (lNode* head, int size_required);
int lFindPtr (lNode* head, char* ptr_to_free);
int lTraverse (lNode* head);
int compare(lNode* head, char *ptr, char val);
int check (lNode* head);

#endif
