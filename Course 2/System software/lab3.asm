extrn pro1:far;

DATA SEGMENT
 res dw 10 dup(32)			;results aray
 len dw $-res                  
 file1 db "out.txt",0			;output filename
 filehandle dw ?              
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


mov AX,16

init:       ;init of array in stek (all elements = ascii code of '0')
 push 0     ;
 dec AX     ;
 cmp AX,0   ;
 JNE init   ;

 push 10    
 call pro1  

 pop AX
 mov SI,0

writing:              ;writing from stek to result array
 pop AX               ;
 add AX,48            ;
 mov res[SI],AX       ;
 add SI,2             ;
 mov AX,20            ;
 cmp AX,SI            ;
 JNE writing          ;

 mov ah,3ch
 mov dx,offset file1  ;writing to file
 mov cx,0
 int 21h
 mov FileHandle,ax
 mov ah,40h
 mov bx,FileHandle
 mov cx,len
 mov dx,offset res
 int 21h
 mov ah,40h
 mov bx,FileHandle
 mov cx,0
 int 21h
 mov ah,3eh
 mov bx,FileHandle
 int 21h

 mov AX,4C00h         ;exit from program
 int 21h              ;


CODE ENDS
END strt