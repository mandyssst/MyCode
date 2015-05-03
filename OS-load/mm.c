#include <stdio.h>
#include <stdlib.h>
#include "mm.h"
#include "util.h" 

static lNode* list_head;

int mm_init(unsigned long size) {
	/* create a memory pool and initialize data structure */
	if( (list_head = (lNode*) malloc(sizeof(lNode))) == NULL ) {
		perror("ERROR: cannot initiate memory!\n");
		return -1;
	}

	if ( (list_head->mem_ptr = (char*) malloc(sizeof(char) * size)) == NULL ) {
		perror("ERROR: cannot initiate memory!\n");
		return -1;
	}

	list_head->mem_size = size;
	list_head->occupied = false;

	return 0;
}


char *mm_alloc(unsigned long nbytes) {
	/* allocate nbytes memory space from the pool using first fit algorithm, and return it to the requester */
	char* slot = lFindSlot(list_head, nbytes); 
	if (slot == NULL) {
		FILE* fp;
		fp = fopen("out.log", "a+");
		fprintf(fp, "Request declined: no enough memory available!\n");
		fclose(fp);
	}

	return slot;
}


int mm_free(char *ptr) {
	/* Check if ptr is valid or not. Only free the valid pointer. Defragmentation. */
	if (lFindPtr(list_head, ptr) == -1) {
		FILE* fp;
		fp = fopen("out.log", "a+");
		fprintf(fp, "Free error: not the right pointer!\n");
		fclose(fp);

		return -1;
	}

	return 0;
}

void mm_end(unsigned long *free_num) {
	/* Count total allocated blocks. Count total free blocks. Clean up data structure. Free memory pool */
	*free_num = lTraverse(list_head);
	return;
}


/* Extra Credit Part */
int mm_assign(char *ptr, char val) {
	/* Check buffer overflow. If ptr is valid, *ptr=val. */
	int res = compare(list_head, ptr, val);
	if (res == -1) {
		FILE* fp;
		fp = fopen("out.log", "a+");
		fprintf(fp, "Buffer Overflow: Try to access illegal memory space.\n");		
		fclose(fp);
		return -1;
	}
	return res;
}

unsigned long mm_check() {
	/* Before calling mm_end, use this function to check memory leaks and return the number of leaking blocks */
	return check(list_head);
}

