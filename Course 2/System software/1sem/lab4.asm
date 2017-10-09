extrn pro1:far;
extrn pr_prmt:far;
extrn file_save:far;

DATA SEGMENT
 res db 10 dup(32)			    ;results aray
 len dw $-res                  
 file1 db "out.txt",0			;output filename
 filehandle dw ?       
 CUR1 db "Cursor on: $"         ;text of promts
 CUR2 db "Enter a number: $"   ;
 X db 7                        ;x position on screen
 Y db 5                         ;y position on screen
 STRIN db 3,0,0,0,0             ;buffer for keyboard input       
DATA ENDS

STCK SEGMENT STACK
 db 256 DUP(?)
STCK ENDS

CODE SEGMENT 'code'
ASSUME CS:CODE,DS:DATA

strt:
 .386
 mov AX,DATA
 mov DS,AX

mov AX,offset STRIN		;put to proc buffer for entring string
 push AX                        ;
 mov AX,offset CUR2            ;put to proc 2nd promt "Enter a number"
 push AX                        ;
 mov AL,X                       ;put to proc X
 push AX                        ;
 mov AL,Y                       ;put to proc Y
 push AX                        ;
 mov AX,offset CUR1            ;put to proc 1st promt "Cursor on.."
 push AX                        ;
 call pr_prmt                   ;call pr_prmt

 mov BL,strin[3]		;decode ascii string from keyboard
 mov AL,strin[2]                ;to numeric format
 sub AL,48                      ;
 mov CL,13                      ;
 cmp BL,CL                      ;
 je one_symb                    ; 
 sub BL,48                      ;
 mov CL,10                      ;
 mul CL                         ; 
 add AX,BX                      ;

one_symb:                       
 mov BX,10



;mov AX,16


init:       			;init of array in stek (all elements = 0)
 push 0     			;
 dec BX     			;
 cmp BX,0   			;
 JNE init   			;

 push AX    			
 call pro1  			;call our proc
 pop AX                         ;
 mov SI,0                       ;

put_to_array:         		;writing from stek to result array
 pop AX               		;
 add AX,48            		;uncomment to save file in ascii mode
 mov res[SI],AL       		;
 inc SI               		;
 mov AX,10            		;
 cmp AX,SI            		;
 JNE put_to_array     		;

 push offset res                ;put to proc result's array
 push len                       ;put to proc length of result's array
 push FileHandle                ;
 push offset file1              ;put to proc filename of result's file
 call file_save                 ;call file_save proc
 
 mov AX,4C00h         		;exit from program
 int 21h              		;

CODE ENDS
END strt