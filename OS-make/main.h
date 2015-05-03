/**************************
 * maum.h -- the header file for maum.c 
 *
 *
 *
 *
 *************************/

#include <stdlib.h>

#ifndef _MAIN_H_
#define _MAIN_H_

#define true 1
#define false 0
typedef int bool;

void custmake_usage(char*);
int parse_file(tNode*, char*, char*);

#endif
