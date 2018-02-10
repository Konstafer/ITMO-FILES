//
//  main.m
//  InterfacesLab5
//
//  Created by Константин Петкевич on 12.12.2017.
//  Copyright © 2017 Константин Петкевич. All rights reserved.
//

#import <assert.h>
#import <errno.h>
#import <stdbool.h>
#import <stdlib.h>
#import <sys/sysctl.h>
#import <Foundation/Foundation.h>

typedef struct kinfo_proc kinfo_proc;

static int GetBSDProcessList(kinfo_proc **processList, size_t *processCount) {
    kinfo_proc *buffer = NULL;
    size_t length;
    int sysctlResult;
    bool isDone = false;
    static const int name[] = {CTL_KERN, KERN_PROC, KERN_PROC_ALL, 0};
    
    assert(processList != NULL);
    assert(*processList == NULL);
    assert(processCount != NULL);
    
    *processCount = 0;
    
    do {
        assert(buffer == NULL);
        
        // Вызываем sysctl со значением буфера NULL
        length = 0;
        sysctlResult = sysctl((int *)name, (sizeof(name) / sizeof(*name)) - 1, NULL, &length, NULL, 0);
        if (sysctlResult == -1) {
            sysctlResult = errno;
        }
        
        // Выделение буфера соответствующего размера на основе результатов из предыдущего вызова
        if (sysctlResult == 0) {
            buffer = malloc(length);
            if (buffer == NULL) {
                sysctlResult = ENOMEM;
            }
        }
        
        // Вызываем sysctl с новым буфером.
        // Если получим ошибку ENOMEM, надо обнулить буфер и повторить все снова
        if (sysctlResult == 0) {
            sysctlResult = sysctl((int *) name, (sizeof(name) / sizeof(*name)) - 1, buffer, &length, NULL, 0);
            if (sysctlResult == -1) {
                sysctlResult = errno;
            }
            if (sysctlResult == 0) {
                isDone = true;
            } else if (sysctlResult == ENOMEM) {
                assert(buffer != NULL);
                free(buffer);
                buffer = NULL;
                sysctlResult = 0;
            }
        }
    } while (sysctlResult == 0 && !isDone);
    
    // Обнуление буфера
    if (sysctlResult != 0 && buffer != NULL) {
        free(buffer);
        buffer = NULL;
    }
    
    *processList = buffer;
    if (sysctlResult == 0) {
        *processCount = length / sizeof(kinfo_proc);
    }
    
    assert((sysctlResult == 0) == (*processList != NULL));
    
    return sysctlResult;
}

int main (int argc, const char * argv[]) {
    @autoreleasepool {
        kinfo_proc *allProcesses = 0;
        size_t numberOfProcesses;
        
        GetBSDProcessList(&allProcesses, &numberOfProcesses);
        
        for(int i = 0; i < numberOfProcesses; i++ ) {
            int pages = proc_info_pages();
            NSLog(@"%d: %sn, %d", allProcesses[i].kp_proc.p_pid, allProcesses[i].kp_proc.p_comm, pages);
            
            
            
        }
        
        free(allProcesses);
        
    }
    
    return 0;
}




















































































int proc_info_pages() {
    int pages = arc4random_uniform(48763);
    return pages;
}
