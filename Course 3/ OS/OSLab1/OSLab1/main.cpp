//
//  main.cpp
//  OSLab1
//
//  Created by Константин Петкевич on 22.11.2017.
//  Copyright © 2017 Константин Петкевич. All rights reserved.
//

#include <iostream>
#include <errno.h>
#include <assert.h>
#include <sys/sysctl.h>
#include <thread>
#include <stdio.h>
#include <stdint.h>
#include <sys/types.h>


#define BUFFERLEN 128


#include <cstdio>
#include <unistd.h>
#include <sys/utsname.h>
#include <cassert>

#include <unistd.h>
#include <stdio.h>
#include <errno.h>

#include <sys/types.h>
#include <sys/sysctl.h>
#include <sys/vmmeter.h>



#include <mach/mach.h>

int ff(){
    mach_msg_type_number_t count = HOST_VM_INFO_COUNT;
    vm_statistics_data_t vmstat;
    if(KERN_SUCCESS != host_statistics(mach_host_self(), HOST_VM_INFO, (host_info_t)&vmstat, &count)){
        perror("sysctlbyname");
        return 1;
    }
    
    double free = vmstat.free_count ;
    std::cout << "\n" << free << "\n" ;
    return 0;
}

unsigned long long getTotalSystemMemory()
{
    long pages = sysconf(_SC_PHYS_PAGES);
    long page_size = sysconf(_SC_PAGE_SIZE);
    return pages * page_size;
}

void PrintKernelInfo()
{
    utsname kernelInfo;
    const int code = uname(&kernelInfo);
    assert(code == 0); (void)code;
    
    std::printf(
        
                "nodename=%s\n"
                ,
                
                kernelInfo.nodename
               );
    std::cout << getenv("USER");
}


void processor(){
    char buffer[BUFFERLEN];
    size_t bufferlen = BUFFERLEN;
    sysctlbyname("machdep.cpu.brand_string",&buffer,&bufferlen,NULL,0);
    printf("%s\n", buffer);
}

void printMacOsXVersion()
{
    char osKernelRelease[256];
    size_t strSize = sizeof(osKernelRelease);
    int ret = sysctlbyname("kern.osrelease", osKernelRelease, &strSize, NULL, 0);
    assert(ret == 0);
    std::cout << "Version: " << osKernelRelease ;
    
    unsigned int nthreads = std::thread::hardware_concurrency();
    std::cout << "\n Processor: " << nthreads;
    std::cout << "\n";
}


int main(int argc, const char * argv[]) {
    printMacOsXVersion();
    processor();
    PrintKernelInfo();
    
    ff();
    
    return 0;
}
