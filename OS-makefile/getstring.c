

#include <stdio.h>
#include <dirent.h>
#include <sys/stat.h>
#include <sys/types.h>
#include <fcntl.h>
#include <unistd.h>
#include "getstring.h"

#define WRITE_FLAGS (O_WRONLY | O_APPEND | O_CREAT)
#define READ_FLAGS  (O_RDONLY)
#define WRITE_PERMS (S_IRUSR | S_IWUSR)

int main(int argc, char* argv[]) {

	if (argc != 3) {
		fprintf(stderr, "ERROR: expect 3 arguments while get %d arguments!\n", argc); //TODO
	}
	
	int out_file;
	if ((out_file = open("./out.txt", WRITE_FLAGS, WRITE_PERMS)) == -1) {
		fprintf(stderr ,"ERROR: cannot create or open out.txt!\n");
		return 1;
	}

	int log_file;
	if ((log_file = open("./log.txt", WRITE_FLAGS, WRITE_PERMS)) == -1) {
		fprintf(stderr, "ERROR: cannot create or open log.txt!\n");
		return 1;
	}

	if (argv[1][strlen(argv[1])-1] == '/') {
		argv[1][strlen(argv[1])-1] = '\0';
	}

	search_dir(argv[1], argv[2], out_file, log_file);
	close(out_file);
	close(log_file);

}

void search_dir(const char* pathname, const char* search_string, \
		const int out_file, const int log_file) {

	DIR* directory;
	if ((directory = opendir(pathname)) == NULL) {
		perror("ERROR: cannot open directory\n");
		return 1;
	}
	
	struct dirent* directory_ent;
	while ((directory_ent = readdir(directory)) != NULL) {
		struct stat status;
		char* directory_ent_full_name = (char*) malloc(sizeof(char) * 256);
		
		if ( strcmp(directory_ent->d_name, ".") == 0 || strcmp(directory_ent->d_name, "..") == 0 ) {
			continue;
		}

		//reset the char array
		memset(directory_ent_full_name, '\0', sizeof(char) * 256);
		strcat(directory_ent_full_name, pathname);
		strcat(directory_ent_full_name, "/");
		strcat(directory_ent_full_name, directory_ent->d_name);

		if(write(log_file, directory_ent_full_name, strlen(directory_ent_full_name)) == -1){
			perror("ERROR: cannot write to log file\n");
			return 1;
		}
		if(write(log_file, "\n", strlen("\n")) == -1) {
			perror("ERROR: cannot write to log file\n");
			return 1;
		}

		if ( lstat(directory_ent_full_name, &status) == 0 ) {
			if ( status.st_mode & S_IFDIR ) { //a directory
				search_dir(directory_ent_full_name, search_string, out_file, log_file);
			} else { //a txt file
				if (S_ISLNK(status.st_mode)) {
					continue;
				} else {
					int fd[2];
					pipe(fd);
					pid_t childpid = fork();
					if (childpid == 0) { //child
						if(close(fd[1]) == -1){
							perror("ERROR: cannot close fd[1]");
							return 1;
						}
						if(dup2(fd[0], STDIN_FILENO) == -1){
							perror("ERROR: Cannot duplicate\n");
							return 1;
						}
						if(dup2(out_file, STDOUT_FILENO) == -1){
							perror("ERROR: Cannot duplicate\n");
							return 1;
						}
						execl("/bin/grep", "grep", search_string, NULL);
						perror("ERROR: cannot execute grep\n");
						return 1;

					} else { //parent
						if(close(fd[0]) == -1){
							perror("ERROR: cannot close fd[0]");
							return 1;
						}
						char* buffer = (char*) malloc(sizeof(char) * status.st_size);
						int file_d = open(directory_ent_full_name, READ_FLAGS);
						if(read(file_d, buffer, status.st_size) == -1){
							perror("ERROR: cannot read\n");
							return 1;
						}
						if(write(fd[1], buffer, status.st_size) == -1){
							perror("ERROR: cannot write");
							return 1;
						}
						free(buffer);
						if(close(fd[1]) == -1){
							perror("ERROR: cannot close fd[1]\n");
							return 1;
						}
						if(childpid != wait(NULL)){
							perror("ERROR: parent failed to wait\n");
							return 1;
						}
					}
				}
			}
		} else {
			printf("WARN: cannot get the file status!\n");
		}
		free(directory_ent_full_name);

	}
}
