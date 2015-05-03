#include <stdbool.h>
#include <stdlib.h>
#include "util.h"

char* lFindSlot (lNode* head, int size_required) {

	lNode* cursor = head;
	while (cursor != NULL) {
		if (cursor->occupied == false && cursor->mem_size >= size_required) {

			int size_left;
			if ((size_left = cursor->mem_size - size_required) != 0) {
				lNode* newNode = (lNode*) malloc(sizeof(lNode));
				newNode->mem_ptr = cursor->mem_ptr + size_required;
				newNode->mem_size = size_left;
				newNode->occupied = false;
				newNode->next = cursor->next;
				cursor->next = newNode;
			}

			cursor->mem_size = size_required;
			cursor->occupied = true;

			return cursor->mem_ptr;
		}
		cursor = cursor->next;
	}
	
	return NULL;
}

int lFindPtr (lNode* head, char* ptr_to_free) {

	if (head == NULL) {
		return -1;
	}

	if (head->mem_ptr == ptr_to_free) {	//if the ptr points to the memory slot of the head
		
		if (head->occupied == false) {
			return -1;
		}

		if (head->next != NULL && head->next->occupied == false) {	//need to combine
			head->mem_size += head->next->mem_size;
			lNode* to_be_free = head->next;
			head->next = head->next->next;
			free(to_be_free);
		}
			
		head->occupied = false;
		return 0;
	} else {	//if the ptr points to the memory slot which is not the head

		lNode* cursor = head->next;
		lNode* previous = head;

		while (cursor != NULL) {
			if (cursor->mem_ptr == ptr_to_free) {
				
				if (cursor->occupied == false) {
					return -1;
				}

				cursor->occupied = false;
				if (cursor->next != NULL && cursor->next->occupied == false) {
					cursor->mem_size += cursor->next->mem_size;
					lNode* to_be_free = cursor->next;
					cursor->next = cursor->next->next;
					free(to_be_free);
				}

				if (previous->occupied == false) {
					previous->mem_size += cursor->mem_size;
					previous->next = cursor->next;
					free(cursor);
				}
				return 0;
			}
			cursor = cursor->next;
			previous = previous->next;
		}
	}

	return -1;
}

int lTraverse (lNode* head) {

	if (head == NULL) {return 0;}

	lNode* cursor = head;
	lNode* tmp = head;
	int num_free_blocks = 0;

	while (cursor != NULL) {
		if (cursor->occupied == false) {
			num_free_blocks++;
		}
		tmp = cursor->next;
		free(cursor);
		cursor = tmp;
	}

	return num_free_blocks;
}

int compare(lNode* head, char *ptr, char val) {

	lNode* cursor = head;

	while (cursor != NULL) {

		if (cursor->mem_ptr <= ptr && cursor->mem_ptr + cursor->mem_size > ptr && cursor->occupied == true) {
			*ptr = val;
			return 0;
		}
		cursor = cursor->next;
	}
	return -1;
}

int check (lNode* head) {

	if (head == NULL) {return 0;}

	lNode* cursor = head;
	int num_unfreed_blocks = 0;

	while (cursor != NULL) {
		if (cursor->occupied == true) {
			num_unfreed_blocks++;
		}
		cursor = cursor->next;
	}

	return num_unfreed_blocks;
}

