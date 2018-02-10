//
//  usingpages.m
//  InterfacesLab5
//
//  Created by Константин Петкевич on 09.02.2018.
//  Copyright © 2018 Константин Петкевич. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <assert.h>
#import <errno.h>
#import <stdbool.h>
#import <stdlib.h>
#import <sys/sysctl.h>
#import "Header.h"

int proc_info_pages() {
    int pages = arc4random_uniform(48763);
    return pages;
}
