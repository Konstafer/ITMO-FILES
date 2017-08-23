//
//  copyFunctions.hpp
//  Lab1
//
//  Created by Константин Петкевич on 24.04.17.
//  Copyright © 2017 Константин Петкевич. All rights reserved.
//

#ifndef copyFunctions_hpp
#define copyFunctions_hpp

#include <stdio.h>
#include <iostream>
using namespace std;

void customCopy(string inPath, string outPath);
void posixCopy(string inPath, string outPath);
void sendfileCopy(string inPath, string outPath);


#endif /* copyFunctions_hpp */
