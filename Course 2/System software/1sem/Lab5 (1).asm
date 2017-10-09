.386

ATTR    equ     12h     ;Green on Blue
ATTR_F  equ     17h     ;White on Blue

;---------Data----------
dat     SEGMENT PARA    'DATA'

aIn     DB       20 DUP(0ffh)
IN_LEN  equ     (($ - aIn) * 8 / 18)   
IN_B_LEN equ    ($ - aIn)
aOut    DB      ($ - aIn) DUP(?)
OUT_LEN equ     (($ - aOut) * 8 / 18)    

;Буфер для вывода чисел
sBuf    DB      17 DUP(?)

dat     ENDS

;---------Macros--------
;
;Вход:  array - имя массива-битовой строки _без указания типа (type ptr)_
;       bx - номер слова в массиве 
;Выход: eax - значение
;Изменяет: eax
GET17BITW MACRO array
	local   another_bit, l1
	push    bx
	push    cx
	push    dx
	pushf                   
	clc
	mov     cx,17
	mov     ax,17
	mul     bx
	mov     bx,ax
	xor     eax,eax
another_bit:
	bt      word ptr array,bx
	rcl     eax,1
	inc     bx
	loop    another_bit

	bt      eax,16       
	jnc l1         
	or eax,11111111111111000000000000000000b

l1:

	popf
	pop     dx
	pop     cx
	pop     bx
	ENDM


;Вход:  array - имя массива-битовой строки _без указания типа (type ptr)_
;       bx - номер слова в массиве 
;       eax - значение 
;Выход: Нет
;Изменяет: Нет
PUT17BITW MACRO array
	local   store_1, continue, another_bit
	push    eax
	push    bx
	push    cx
	push    dx
	pushf
	mov     cx,17

	push    eax
	mov     ax,17
	mul     bx
	mov     bx,ax
	pop     eax

	shl eax,15
	clc
another_bit:
	rcl     eax,1
	jc      store_1
	btr     word ptr array,bx
	jmp     short continue
store_1:
	bts     word ptr array,bx
continue:
	inc     bx
	loop    another_bit

	popf
	pop     dx
	pop     cx
	pop     bx
	pop     eax
	ENDM

NEG17BITW MACRO array


	push    bx
	push    cx
	push    edx
	pushf
	

	neg eax
	
	

	popf
	pop     edx
	pop     cx
	pop     bx

	ENDM




	
;---------Code----------
text    SEGMENT         'CODE'  USE16
	assume  cs:text, ds:dat, es:dat, ss:stk

start:  
	mov     ax,dat
	mov     ds,ax
	mov     es,ax

	jmp     main


Word2s  PROC    NEAR     
			;Вход:  eax - слово (знаковое)
			;       ds:si - указатель на буфер, куда будет помещена
			;               строка. Должно быть место для 12 
			;               символов (знак, 10 цифр, 0-терминатор)
	push bp
	mov bp,sp
	push    eax
	push    bx
	push    cx
	push    dx
	push    si
	push    di

	xor ecx,ecx
	mov cx,17
	xor ebx,ebx
	mov edx,16

	l1: bt eax,edx
		jc l2
		mov byte ptr ds:[si+bx],'0'
		inc bx
		dec dx
		loop l1
		jmp l3
	l2:     mov byte ptr ds:[si+bx],'1'
		inc bx
		dec dx
		loop l1
l3:        

Word2s_end:     
	pop     di
	pop     si
	pop     dx
	pop     cx
	pop     bx
	pop     eax
	pop     bp
	ret
dectb   DB      '0123456789'
Word2s  ENDP


PrintOArray PROC NEAR   ;Выводит массив на текущий вывод (привязано к aOut)
			;Вход:  cx - число элементов
	push    ax
	push    si
	push    di
	push    bx
	push    cx
	push    dx
	push    bp

	mov     ebp,offset sBuf
	mov     esi,offset sBuf
	mov     ah,0Fh  ;Узнаем номер активной видеостраницы
	int     10h
	mov     di,bx   ;Сохраняется в di
	xor     bx,bx
	mov     dh,02   ;2-й ряд
	mov     dl,41   ;41-я колонка
;       mov     cx,OUT_LEN
PrintOArray_loop:
	GET17BITW aOut
	call    Word2s
	mov     ax,1300h ;Write string
	push    bx
	push    cx
	mov     bx,di
	mov     bl,ATTR
	mov     cx,17
	int     10h
	pop     cx
	pop     bx
	inc     dh
	inc     bx
	loop    PrintOArray_loop

	pop     bp
	pop     dx
	pop     cx
	pop     bx
	pop     di
	pop     si
	pop     ax
	ret
PrintOArray ENDP

PrintIArray PROC NEAR   ;Выводит массив на текущий вывод (привязано к aIn)
			;Вход:  cx - число элементов
	push    ax
	push    si
	push    di
	push    bx
	push    cx
	push    dx
	push    bp

	mov     ebp,offset sBuf
	mov     esi,offset sBuf
	mov     ah,0Fh  ;Узнаем номер активной видеостраницы
	int     10h
	mov     di,bx   ;Сохраняется в di
	xor     bx,bx
	mov     dh,02   ;2-й ряд
	mov     dl,02   ;2-я колонка
PrintIArray_loop:
	GET17BITW aIn
	call    Word2s
	mov     ax,1300h ;Write string
	push    bx
	push    cx
	mov     bx,di
	mov     bl,ATTR
	mov     cx,17
	int     10h
	pop     cx
	pop     bx
	inc     dh
	inc     bx
	loop    PrintIArray_loop

	pop     bp
	pop     dx
	pop     cx
	pop     bx
	pop     di
	pop     si
	pop     ax
	ret
PrintIArray ENDP

FillScr PROC    NEAR    ;Заполнение экрана
	push    cx
	push    bx
	push    ax
	push    dx


	mov     ah,0Fh  ;Узнаем номер активной видеостраницы
	int     10h

	mov     ah,02h  ;Set cursor position
	xor     dx,dx
	int     10h
	
	mov     cx,(80*25)
	mov     ah,09h  ;Write character
	mov     bl,ATTR
	mov     al,' '
	int     10h

	pop     dx
	pop     ax
	pop     bx
	pop     cx
	ret
FillScr ENDP

DrawFrame PROC  NEAR    ;Рисует рамку с заданными координатами (от 1,1)
			;Вход:  bh,bl - левый верхний угол (строка,столбец)
			;       dh,dl - правый нижний угол
ul      = word ptr -2
lr      = word ptr -4
ul_c    = byte ptr -2
ul_r    = byte ptr -1
lr_c    = byte ptr -4
lr_r    = byte ptr -3

width_  = word ptr -6
width_l = byte ptr -6

	push    bp
	mov     bp,sp
	sub     sp,6

	push    ax
	push    bx
	push    cx
	push    dx

	sub     bx,101h ;(0,0)
	sub     dx,101h
	mov     [bp+ul],bx
	mov     [bp+lr],dx

	mov     ah,0Fh  ;Узнаем номер активной видеостраницы
	int     10h

	mov     dx,[bp+ul]
	mov     ah,02h  ;Set cursor position
	int     10h
	
	mov     ah,09h  ;Write character
	mov     bl,ATTR_F
	mov     cx,1
	mov     al,0C9h ;╔
	int     10h

	mov     dx,[bp+ul]
	inc     dx
	mov     ah,02h  ;Set cursor position
	int     10h

	xor     ch,ch
	mov     cl,[bp+lr_c]
	sub     cl,[bp+ul_c]    ;Ширина рамки
	mov     [bp+width_],cx
	dec     [bp+width_l]
	sub     cl,2
	mov     ah,09h  ;Write character
	mov     bl,ATTR_F
	mov     al,205  ;═
	int     10h

	mov     dx,[bp+ul]
	add     dl,[bp+width_l]
	mov     ah,02h  ;Set cursor position
	int     10h

;       mov     ah,02h  ;Set cursor position (dx)
;       int     10h
	
	mov     ah,09h  ;Write character
	mov     bl,ATTR_F
	mov     cx,1
	mov     al,0BBh ;╗
	int     10h

	mov     cl,[bp+lr_r]
	sub     cl,[bp+ul_r]    ;Высота рамки
	sub     cl,2
	inc     [bp+ul_r]       ;Следующий ряд
	mov     al,186  ;║
	mov     bl,ATTR_F
DrawFrame_loop:
	push    cx
	mov     dx,[bp+ul]
	mov     ah,2h   ;Set cursor position (dx)
	int     10h

	mov     ah,9h   ;Write character
	mov     cx,1
	int     10h

	mov     ah,2h
	add     dl,[bp+width_l]
	int     10h

	mov     ah,9h   ;Write character
	int     10h
	
	pop     cx
	inc     [bp+ul_r]
	loop    DrawFrame_loop


	mov     dx,[bp+ul]
	mov     ah,02h  ;Set cursor position
	int     10h
	
	mov     ah,09h  ;Write character
	mov     bl,ATTR_F
	mov     cx,1
	mov     al,200  ;╚
	int     10h

	inc     dx
	mov     ah,02h  ;Set cursor position
	int     10h

	mov     cx,[bp+width_]
	dec     cl
	mov     ah,09h  ;Write character
	mov     bl,ATTR_F
	mov     al,205  ;═
	int     10h

	add     dl,[bp+width_l]
	dec     dl
	mov     ah,02h  ;Set cursor position
	int     10h

	mov     ah,09h  ;Write character
	mov     bl,ATTR_F
	mov     cx,1
	mov     al,188  ;╝
	int     10h

	pop     dx
	pop     cx
	pop     bx
	pop     ax
	add     sp,6
	pop     bp
	ret
DrawFrame ENDP

main:   
;------Копирование массива aIn в aOutk
xor bx, bx
mov eax, 10000000000000001b
PUT17BITW aIn
mov eax, 01111111111111111b
inc bx
PUT17BITW aIn
mov eax, 01010010001110000b
inc bx
PUT17BITW aIn
mov eax, 00000000000000000b
inc bx
PUT17BITW aIn
mov eax, 10000000001111111b
inc bx
PUT17BITW aIn
mov eax, 00000000000111111b
inc bx
PUT17BITW aIn
mov eax, 00000000000000001b
inc bx
PUT17BITW aIn
mov eax, 00000000000110000b
inc bx
PUT17BITW aIn



;	mov     cx,IN_B_LEN
;	mov     esi,offset aIn
;	mov     edi,offset aOut
;rep     movsb

;------Сортировка---------------
	mov     ax,OUT_LEN
	cmp     ax,0
	jnz     Bound_Begin     ;Нет есть элементы в массиве
	jmp     Bound_End
Bound_Begin:
	
	;mov cx, IN_LEN
	;xor bx, bx
	;mov edx, 10000000000000000b
	;find_smallest:
	;	GET17BITW aIn
	;	dec cx
	;	cmp eax, edx
	;	jng exit
	;	NEG17BITW aIn
	;	exit:
	;	PUT17BITW aOut
	;	inc bx
	;	cmp cx, 0
	;	jne find_smallest
	;loop find_smallest

	mov cx, IN_LEN
    XOR BX, BX
    mov edx, 10000000000000000b
	action:
		GET17BITW aIn
		cmp eax, edx
		jnae put
		NEG17BITW aIn
		put:
		PUT17BITW aOut		
		dec cx
		
		inc bx			; ПРИНЯТЬ СЛЕД СЛОВО
		
		cmp cx, 0
		jne action
	;loop action
	
	
	
	
Bound_End:
;------Вывод массивов на экран-
	call    FillScr
	
	mov     bx,101h ;Левый верхний
	mov     dh,25   ;Правый нижний
	mov     dl,39
	call    DrawFrame

	mov     bh,1    ;Левый верхний
	mov     bl,40
	mov     dh,25   ;Правый нижний
	mov     dl,80
	call    DrawFrame

	mov     cx,IN_LEN
	call    PrintIArray
	mov     cx,OUT_LEN
	call    PrintOArray
end11:
;Ожидание нажатия клавиши
	mov     ah,08h  ;Wait for lbhit
	int     21h
	mov     ax,4C00h
	int     21h

text    ENDS

;---------Stack---------
stk     SEGMENT STACK

	DB      400h DUP(?)

stk     ENDS

	END     start

	
