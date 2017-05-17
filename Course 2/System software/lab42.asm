public pr_prmt
public file_save

C1 SEGMENT  
ASSUME CS:C1

pr_prmt proc far
 .386
 pusha        		        ;save all registrs
 mov bp,sp    			;
 add bp,20    			;
 mov DX,[bp]  			;get 1st cursor
 mov AH,9       	        ;print "Cursor on: X,Y"
 int 21h                        ;

 add bp,2
 mov BX,[bp]			;get Y in BX
 add bp,2
 mov CX,[bp]                    ;get X in CX
 add bp,2

 mov DL,CL                      ;print X in ascii
 add DL,48                      ;
 mov AH,2                       ;
 int 21h                      
 mov DL,44                      ;print ','
 int 21h                        ;
 mov DL,BL                     
 add DL,48                      ;print Y in ascii
 int 21h                        ;

targetY:                        ;set cursor at Y position
 dec BL                         ;
 mov DL,13                      ;
 int 21h                        ;
 mov DL,10                      ;
 int 21h                        ;
 cmp BL,1                       ;
 JNE targetY                    ;

targetX:                        ;set cursor at X position
 dec CL                         ;
 mov DL,32                      ;
 int 21h                        ;
 cmp CL,1                       ;
 JNE targetX                    ;

 mov DX, [bp]                   ;print "Enter a number: "
 mov AH,9                       ;
 int 21h                        ;

 mov AL,0			;clear keyboard's buffer
 mov AH,0Ch                     ;
 int 21h                        ;

 add bp,2                       ;get number from keyboard
 mov DX,[bp]                    ;
 mov AH,0Ah                     ;
 int 21h                        ;

 popa                           ;restore registers
 ret          	              	;exit from proc
pr_prmt endp

file_save proc far
 .386
 pusha        		        ;save all registrs
 mov bp,sp    			;
 add bp,20    			;
 mov DX,[bp]  			;get filename 

 mov ah,3dh                     ;try to open file to write
 mov al,1h                      ;
 int 21h                        ;
 mov BX,02h                     ;
 cmp AX,BX                      ;
 JNE write                      ;

 mov ah,3ch                     ;if file not found -> create a file
 mov cx,0                       ;
 int 21h                        ;

write:
 add bp,2                       ;set position in file
 mov [bp],ax                    ;
 mov ah,42h                     ;
 mov bx,[bp]                    ;
 mov al,2                       ;
 mov dx,0						;
 mov dx,0                      ;
 int 21h                        ;

 mov ah,40h                     ;write array to file
 mov bx,[bp]                    ;
 add bp,2                       ;
 mov cx,[bp]                    ;
 add bp,2                       ;
 mov dx,[bp]                    ;
 int 21h                        ;

 mov ah,40h                     ;truncate file
 sub bp,4                       ;
 mov bx,[bp]                    ;
 mov cx,0                       ;
 int 21h                        ;

 mov ah,3eh                     ;close file
 mov bx,[bp]
 int 21h                        ;

 popa                           ;restore all registers
 ret          	              	;exit from proc
file_save endp

C1 ENDS
END