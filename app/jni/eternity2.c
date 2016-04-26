#include <fcntl.h>
#include <sys/stat.h>
#include <sys/inotify.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <string.h>

#include "log.h"


int main(int argc, char *argv[]) {
    int i;
    for (i = 0; i < 1000; i++) {
        pid_t pid = fork();
        if (pid < 0) {
            LOGE("fork error, %s", strerror(errno));
            break;
        }
        if (pid == 0) {
            setsid();
            chdir("/");
            umask(0);
            LOGE("child, deep:%d, pid:%d, pgrp:%d, ppid:%d", i, getpid(), getpgrp(), getppid());
        } else {
            LOGE("parent, deep:%d, pid:%d, pgrp:%d, ppid:%d", i, getpid(), getpgrp(), getppid());
        }
    }
}

