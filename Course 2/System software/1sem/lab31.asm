public pro1

C1 SEGMENT 
 ASSUME CS:C1

pro1 proc far
   .386
   pusha        ;save all registrs
   mov bp,sp    ;
   add bp,20    ;
   mov ax,[bp]  ;
   add bp,20;   ;reposition to buttom element of array in stek
   mov bx,8     ;8 to bx - 'base of system'

   strt:       ;
   div bl       ;del and save to our array in stek
   mov [bp],ah  ;
   sub bp,2     ;
   mov ah,0     ;
   mov dx,0     ;
   cmp ax,dx    ;
   jne strt ;

   popa         ;return from
   ret          ;out proc
pro1 endp

C1 ENDS
END
