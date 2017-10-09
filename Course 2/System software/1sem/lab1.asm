.model small
.386

stack segment para stack 'stack'
DB 100h DUP(?)
STACK ENDS

DATA SEGMENT PARA PUBLIC 'data'
           	ARR1 DD 1,-3,9,5,-6,0,2,-1,3,4
            ARR2 DD 10 DUP(0)   
data ends 

CODE SEGMENT PARA PUBLIC 'code'
ASSUME CS: CODE, DS: DATA, SS:STACK
     
	START:       
	MOV EAX, DATA
	MOV DS, EAX
            PUSH ARR1[4*10-4]                
     
            
            XOR ESI, ESI                        
            XOR EDI, EDI
			MOV CX, 10                           
            MOV DI, CX                        
            DEC DI                            
            SHL DI, 2                         
            SHIFT:                         
                    MOV EAX, ARR1[SI]         
                    CMP AX, 0                
                    JGE SWAP                  
                    NOSWAP:                   
                            ADD SI, 4         
                            DEC CX			  
							JMP LOOP1_END     
                    SWAP:                     
                            MOV EBX, ARR1[DI]  
                            MOV ARR1[DI], EAX  
                            MOV ARR1[SI], EBX  
                            SUB DI, 4         
                                        
                    LOOP1_END:                
            LOOP SHIFT                    
     
            
            MOV SI, 0                          
            MOV CX, 9                          
            MOV EDX,ARR1[SI]                    
            ADEDITION:                      
                    ADD EDX,ARR1[SI]          
                    ADD SI,4                
            LOOP ADEDITION                    
            SHR DX, 2                        
           
             
             XOR SI, SI                      
             MOV CX, 9                         
             CLEAN:                            
                XOR ARR2[SI], 4                
             LOOP CLEAN                        
                ADD SI, 4                    

                                         
            LEA EBX, ARR2                       
            XOR ESI, ESI                       
            XOR EDI, EDI                       
            MOV CX, 10                        
            COPY_ARR:                        
                    MOV EAX, ARR1[SI]         
                    CMP AX, DX               
                    JLE LOOP_END             
                    COPY:                     
                            MOV [EBX+EDI], AX   
                            ADD DI, 4	         
                    LOOP_END:                
                            ADD SI, 4       
            LOOP COPY_ARR  

	            

mov ax, 4c00h
int 21h

CODE ENDS
END START
            
