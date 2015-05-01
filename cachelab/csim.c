//Jinchuan Wei (Michael), 5120498
/* 
 * csim.c - A cache simulator that can replay traces from Valgrind
 *     and output statistics such as number of hits, misses, and
 *     evictions.  The replacement policy is LRU.
 *
 * The function printSummary() is given to print output.
 * Please use this function to print the number of hits, misses and evictions.
 * This is crucial for the driver to evaluate your work. 
 */
#include <getopt.h>
#include <stdlib.h>
#include <unistd.h>
#include <stdio.h>
#include <assert.h>
#include <math.h>
#include <limits.h>
#include <string.h>
#include <errno.h>
#include "cachelab.h"

//#define DEBUG_ON 
#define ADDRESS_LENGTH 64

/* Type: Memory address */
typedef unsigned long long int mem_addr_t;

/* Type: Cache line
   LRU is a counter used to implement LRU replacement policy  */
typedef struct cache_line {
    char valid;
    mem_addr_t tag;
    unsigned long long int lru;
} cache_line_t;

typedef cache_line_t* cache_set_t;
typedef cache_set_t* cache_t;

/* Globals set by command line args */
int verbosity = 0; /* print trace if set */
int s = 0; /* set index bits */
int b = 0; /* block offset bits */
int E = 0; /* associativity */
char* trace_file = NULL;
 
/* Derived from command line args */
int S; /* number of sets */
int B; /* block size (bytes) */

/* Counters used to record cache statistics */
int miss_count = 0;
int hit_count = 0;
int eviction_count = 0;
unsigned long long int lru_counter = 1;

/* The cache we are simulating */
cache_t cache;  

/* 
 * initCache - Allocate memory, and initialize the cache.
 */
void initCache()
{
   cache=(cache_t)malloc(sizeof(cache_set_t)*S);
   
   for (int Sind=0; Sind<S; Sind++)
   {  
       cache[Sind]=(cache_set_t)malloc(sizeof(cache_line_t)*E);
       for (int Eind=0; Eind<E; Eind++){
           cache[Sind][Eind].valid='0';
           cache[Sind][Eind].tag=0;
           cache[Sind][Eind].lru=0;
       }
   }

}


/* 
 * freeCache - free allocated memory
 */
void freeCache()
{
  for (int Sind=0; Sind<S; Sind++)  
       free(cache[Sind]);
  free(cache);   
}

int getset(void* addr)
{
    return (int)( ((long)addr >> b) & ((1 << s) - 1) );
}

// get the tag number from the address
long gettag(void* addr)
{
    return (long)((long)addr >> (s + b));
}

void Load(mem_addr_t* addr)
{
    cache_set_t this_set=cache[getset(addr)];

    int index;
    int full = 1;
    int empty_item = 0;         // if not full, keep track of the empty item
    int last_item = 0;          // if full, keep track of the evict item
    unsigned long long int last_time = this_set[0].lru;

    for (index = 0; index < E; index++)
    {
        // found, update the lru time
        if ((this_set[index]).valid == '1' && gettag(addr) == (this_set[index]).tag)
        {
            (this_set[index]).lru = ++lru_counter;
            break;
        }
        // not valid, then this entry is considered empty which means cache is not full
        else if ((this_set[index]).valid == '0')
        {
            full = 0;
            empty_item = index;
        }
        // valid but tag not equal
        else
        {
            // keep track of the LRU item, ready for eviction
            if ((this_set[index]).lru < last_time)
            {
                last_item = index;
                last_time = (this_set[index]).lru;
            }
        }
    }

    if (index == E) // miss
    {
        miss_count++;
        if (full)   //evict
        {
            (this_set[last_item]).tag=gettag(addr);
            (this_set[last_item]).lru = ++lru_counter;
            eviction_count++;
        }
        else
        {
            (this_set[empty_item]).valid = '1';
            (this_set[empty_item]).tag = gettag(addr);
            (this_set[empty_item]).lru = ++lru_counter;
        }
    }
    else    // hit
    {
        hit_count++;
    }

}

void Store(mem_addr_t* addr)
{
    
    cache_set_t this_set=cache[getset(addr)];

    int index;
    for (index = 0; index < E; index++)
    {
        if ((this_set[index]).valid == '1' && gettag(addr) == (this_set[index]).tag)    //found
        {
            (this_set[index]).lru = ++lru_counter;
            break;
        }
    }

    if (index == E) // store miss
    {
        Load(addr);   // if miss, then load
    }
    else            // store hit
    {
        hit_count++;
    }
}

void Modify(mem_addr_t* addr)
{
   Load(addr);
   Store(addr);
}

/*
 * replayTrace - replays the given trace file against the cache 
 *
 * YOU CAN RE-WRITE THIS FUNCTION IF YOU WANT TO.
 */
void replayTrace(char* trace_fn)
{
    char buf[1000];
    //mem_addr_t addr=0;
    //unsigned int len=0;
    char op[200];
    mem_addr_t* addr;
    int size;
    FILE* trace_fp = fopen(trace_fn, "r");

    if(!trace_fp){
        fprintf(stderr, "%s: %s\n", trace_fn, strerror(errno));
        exit(1);
    }

    while( fgets(buf, 1000, trace_fp) != NULL) {

        /* buf[Y] gives the Yth byte in the trace line */

        /* Read address and length from the trace using sscanf 
           E.g. sscanf(buf+3, "%llx,%u", &addr, &len);
         */

        /*  
         *    ACCESS THE CACHE, i.e. CALL accessData
	 *    Be careful to handle 'M' accesses	
         */
       sscanf(buf, "%s %p,%d", op, &addr, &size);
       if (*op == 'L')
        {
            Load(addr);
        }
        else if (*op == 'M')
        {
            Modify(addr);
        }
        else if (*op == 'S')
        {
            Store(addr);
        }

    }

    fclose(trace_fp);   
}

/*
 * printUsage - Print usage info
 */
void printUsage(char* argv[])
{
    printf("Usage: %s [-hv] -s <num> -E <num> -b <num> -t <file>\n", argv[0]);
    printf("Options:\n");
    printf("  -h         Print this help message.\n");
    printf("  -v         Optional verbose flag.\n");
    printf("  -s <num>   Number of set index bits.\n");
    printf("  -E <num>   Number of lines per set.\n");
    printf("  -b <num>   Number of block offset bits.\n");
    printf("  -t <file>  Trace file.\n");
    printf("\nExamples:\n");
    printf("  linux>  %s -s 4 -E 1 -b 4 -t traces/yi.trace\n", argv[0]);
    printf("  linux>  %s -v -s 8 -E 2 -b 4 -t traces/yi.trace\n", argv[0]);
    exit(0);
}

/*
 * main - Main routine 
 */
int main(int argc, char* argv[])
{
    char c;

    while( (c=getopt(argc,argv,"s:E:b:t:vh")) != -1){
        switch(c){
        case 's':
            s = atoi(optarg);
            break;
        case 'E':
            E = atoi(optarg);
            break;
        case 'b':
            b = atoi(optarg);
            break;
        case 't':
            trace_file = optarg;
            break;
        case 'v':
            verbosity = 1;
            break;
        case 'h':
            printUsage(argv);
            exit(0);
        default:
            printUsage(argv);
            exit(1);
        }
    }

    /* Make sure that all required command line args were specified */
    if (s == 0 || E == 0 || b == 0 || trace_file == NULL) {
        printf("%s: Missing required command line argument\n", argv[0]);
        printUsage(argv);
        exit(1);
    }

    /* Compute S, E and B from command line args */
     S=pow(2,s);
     B=pow(2,b);
    /* Initialize cache */
    initCache();

#ifdef DEBUG_ON
    printf("DEBUG: S:%u E:%u B:%u trace:%s\n", S, E, B, trace_file);
#endif
 
    /* Read the trace and access the cache */
    replayTrace(trace_file);

    /* Free allocated memory */
    freeCache();

    /* Output the hit and miss statistics for the autograder */
    printSummary(hit_count, miss_count, eviction_count);
    return 0;
}
