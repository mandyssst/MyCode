/* CSci4061 S2014 PA4
Extra credit: implemented*/

#include <stdio.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <unistd.h>
#include <fcntl.h>
#include <string.h>
#include <pthread.h>
#include <libgen.h>
#include <stdlib.h>
#include <semaphore.h>
#include "util.h"

#define READ_FLAGS  (O_RDONLY) 
#define WRITE_FLAGS (O_WRONLY | O_APPEND | O_CREAT)	//TODO
#define NO_APPEND_WRITE_FLAGS (O_WRONLY | O_CREAT)	//TODO
#define WRITE_PERMS (S_IRUSR | S_IWUSR)

void* decrypt (void* args);
void* read_file (void* args);

// Client queue
q_node* q_head = NULL;
char* path;
char* log_file;
int queue_slots = 0;
int done_flag = 0;

pthread_mutex_t mutex_queue = PTHREAD_MUTEX_INITIALIZER;
sem_t mutex_log;
sem_t mutex_queue_slots;
sem_t mutex_queue_items;
pthread_cond_t items = PTHREAD_COND_INITIALIZER;

int main(int argc, char** argv) {

	int num_threads = 0;

	if (argc == 5) {
		num_threads = atoi(argv[3]);
		queue_slots = atoi(argv[4]);
	} else if (argc == 4) {
		num_threads = atoi(argv[3]);
		queue_slots = 7;
	} else if (argc == 3) {
		num_threads = 5;
		queue_slots = 7;
	} else {
		fprintf(stderr, "Usage: %s <clients.txt> <output> num_threads queue_size\n", argv[0]);
		return -1;
	}

	path = (char*) malloc(sizeof(char) * (strlen(argv[1])+1));
	strcpy(path, argv[1]);
	path = dirname(path);
	
	log_file = (char*) malloc(sizeof(char) * (strlen(argv[2]) + strlen("/") + strlen("log.txt") + 1));
	strcpy(log_file, argv[2]);
	strcat(log_file, "/");
	strcat(log_file, "log.txt"); 

	sem_init(&mutex_log, 0, 1);
	sem_init(&mutex_queue_slots, 0, queue_slots);
	sem_init(&mutex_queue_items, 0, 0);

	pthread_t* read_file_thread = (pthread_t*) malloc(sizeof(pthread_t));
	pthread_create(read_file_thread, NULL, read_file, (void*) argv[1]);
	pthread_t* tid = (pthread_t*) malloc(sizeof(pthread_t) * num_threads);
	int i;
	for (i=0; i < num_threads; i++) {
		if ( pthread_create(&tid[i], NULL, decrypt, (void*) argv[2]) != 0 ) {
			fprintf(stderr, "ERROR: failed to create a new thread!\n");
			return -1;
		}
	}

	for (i=0; i < num_threads; i++) {
		if ( pthread_join(tid[i], NULL) != 0 ) {
			fprintf(stderr, "ERROR: failed to join with a terminated thread!\n");
			return -1;
		}
	}

	if ( pthread_join(*read_file_thread, NULL) != 0 ) {
		fprintf(stderr, "ERROR: failed to join with a terminated 2nd thread!\n");
		return -1;
	}

	return 0;
}

void* read_file (void* args) {	//args = client.txt

	FILE* client_fd;
	if ((client_fd = fopen((char*) args, "r")) == NULL) {
		fprintf(stderr, "ERROR: Failed to open file %s\n", args);
		return;
	}

	char* client_line = NULL;
	size_t line_len = 0;
	
	while (getline(&client_line, &line_len, client_fd) != -1) {
	
		char* line_to_put_in_q = (char*) malloc(sizeof(char) * (strlen(client_line) + 1));
		strncpy(line_to_put_in_q, client_line, strlen(client_line)-1);

		int get_into_if = 0;	
		pthread_mutex_lock(&mutex_queue);	
		if (queue_slots == 0) {
			pthread_mutex_unlock(&mutex_queue);
			get_into_if = 1;

			sem_wait(&mutex_log);
			int log_file_fd;
			char* log_info = "INFO: Queue full. Waiting to add more clients.\n";
			if ((log_file_fd = open(log_file, WRITE_FLAGS, WRITE_PERMS)) == -1) {
				fprintf(stderr, "ERROR: Failed to open file %s\n", log_file);
			}
			if (r_write(log_file_fd, log_info, strlen(log_info)) == -1) {
				close(log_file_fd);
				fprintf(stderr, "WARN: Failed to write to the log file!\n");
			}
			close(log_file_fd);
			sem_post(&mutex_log);
		}
		if (get_into_if == 0) {
			pthread_mutex_unlock(&mutex_queue);
		}

		sem_wait(&mutex_queue_slots);
		pthread_mutex_lock(&mutex_queue);

		q_push(&q_head, line_to_put_in_q);
		queue_slots--;

		pthread_cond_broadcast(&items);
		pthread_mutex_unlock(&mutex_queue);
		sem_post(&mutex_queue_items);
	}
	done_flag = 1;
	fclose(client_fd);
	pthread_exit(0);
}

void* decrypt (void* args) {	//args is the output

	char* file_path;
	char* complete_file_path;
	int fd;

	while (1) {
		pthread_mutex_lock(&mutex_queue);
		while (q_peek(&q_head) == NULL && done_flag != 1) {
			pthread_cond_wait(&items, &mutex_queue);
		}

		if (q_peek(&q_head) == NULL && done_flag == 1) {
			pthread_mutex_unlock(&mutex_queue);
			pthread_exit(0);
		}
		file_path = q_pop(&q_head);
		queue_slots++;
		sem_post(&mutex_queue_slots);
		
		pthread_mutex_unlock(&mutex_queue);

		complete_file_path = (char*) malloc(sizeof(char)*(strlen(path)+strlen("/")+strlen(file_path)+1));
		strcpy(complete_file_path, path);
		strcat(complete_file_path, "/");
		strcat(complete_file_path, file_path); 
		if ((fd = open(complete_file_path, READ_FLAGS)) == -1) {
			fprintf(stderr, "WARN: Failed to open file %s.\n", complete_file_path);
		}

		struct stat status;
		if (stat(complete_file_path, &status) == -1) {
			fprintf(stderr, "ERROR: Failed to get file status!\n");
			return;
		}
		off_t client_size = status.st_size;
		char* buf = (char*) malloc(sizeof(char)*500);
		if (r_read(fd, buf, client_size) == -1) {
			close(fd);
			fprintf(stderr, "ERROR: Failed to read from the input file!\n");
			return;
		}
		close (fd);
		char* cursor = buf;
		while (*cursor != '\0') {
			if ( (*cursor >= 'a' && *cursor <= 'x') || (*cursor >= 'A' && *cursor <= 'X') ) {
				*cursor += 2;
			} else if ( (*cursor >= 'y' && *cursor <= 'z') || (*cursor >= 'Y' && *cursor <= 'Z') ) {
				*cursor = *cursor - 24;
			}
			cursor++;
		}
		char* file_name = (char*) malloc(sizeof(char) * (strlen(complete_file_path) + 1));
		strcpy(file_name, complete_file_path);
		file_name = basename(file_name); //Get the file name, the part after the final "/".
		char* output = (char*) args;
		char* output_file = (char*) malloc(sizeof(char) * (strlen(output)+strlen("/")+strlen(file_name)+strlen(".out")+1));

		strcpy(output_file, output); 
		strcat(output_file, "/");
		strcat(output_file, file_name);
		strcat(output_file, ".out");
		
		int output_file_fd;
		if ((output_file_fd = open(output_file, NO_APPEND_WRITE_FLAGS, WRITE_PERMS)) == -1) {
			fprintf(stderr, "WARN: Failed to open file %s\n", output_file);
		}
		
		if (r_write(output_file_fd, buf, strlen(buf)) == -1) {
			fprintf(stderr, "WARN: Failed to write to the output file!\n");
		}
		close(output_file_fd);

		char thread_id[65];
		sprintf(thread_id, "%ld", pthread_self());
		thread_id[strlen(thread_id)] = '\0';
		
		char* log_info = (char*) malloc(sizeof(char) * (strlen(complete_file_path)+strlen(": ")+strlen(thread_id)+strlen("\n")+1));
		strcat(log_info, complete_file_path);
		strcat(log_info, ": ");
		strcat(log_info, thread_id);
		strcat(log_info, "\n");
		int log_file_fd; 

		sem_wait(&mutex_log);
		if ((log_file_fd = open(log_file, WRITE_FLAGS, WRITE_PERMS)) == -1) {
			fprintf(stderr, "ERROR: Failed to open file %s\n", log_file);
		}
		if (r_write(log_file_fd, log_info, strlen(log_info)) == -1) {
			close(log_file_fd);
			fprintf(stderr, "WARN: Failed to write to the log file!\n");
		}
		close(log_file_fd);
		sem_post(&mutex_log);

	}
	
	pthread_exit(0);
}
