.386					
.model flat, stdcall	
option casemap: none	

include windows.inc
include kernel32.inc
include masm32.inc
include msvcrt.inc

includelib kernel32.lib
includelib masm32.lib
includelib msvcrt.lib

.data
	; заголовок таблицы
	msgTable db 13, 10, 10, " | Xn       ", "|  Ln(x)   ", "| Series   ", "| Length   ", "|", 13 , 10, 0
	
	;сообщения
	msgStart db "Laboratornaya 7", 13, 10
	msgFun	 db "Function : Ln(x)", 13, 10, 13, 10
	wall 	db  '|', 0
	
	msgXn	db "Input Xn : ", 0			
	msgXk 	db "Input Xk : ", 0		
	msgDx 	db "Input Delta : ", 0			
	msgEps 	db "Input Epsilon : ", 0	
	
	crlf 	db  13, 10, 0
	; форматы преобразования
	fmtF 	db "%lf", 0
	fmtD 	db "%d", 0
	buff	db	12 dup (0)
	
	zero	dq	0	
	
.data?
	hIn		dd	?
	hOut	dd	?
	lpWrite	dd	?
	
	Xn		dq	?
	Xk		dq	?
	Ln		dq	?
	Sr		dq	?
	len		dq	?
	delta	dq	?
	epsilon	dq	?
	
.code

; инициализация
; ввод начальных условий и преобразование строк в вещественное число
Init proc

	pusha	

	; хендлы консоли
	invoke GetStdHandle,STD_INPUT_HANDLE
    mov hIn,eax	
	invoke GetStdHandle,STD_OUTPUT_HANDLE
    mov hOut,eax

	; вывести сообщения
	invoke WriteConsole, hOut, addr msgStart, sizeof msgStart, addr lpWrite, 0
	invoke WriteConsole, hOut, addr msgFun, sizeof msgFun, addr lpWrite, 0	 
	  
	invoke WriteConsole, hOut, addr msgXn, sizeof msgXn, addr lpWrite, 0 
	; преобразование к float
	invoke crt_scanf, addr fmtF, addr Xn
	
	invoke WriteConsole, hOut, addr msgXk, sizeof msgXk, addr lpWrite, 0 
	invoke crt_scanf, addr fmtF, addr Xk
	
	invoke WriteConsole, hOut, addr msgDx, sizeof msgDx, addr lpWrite, 0 
	invoke crt_scanf, addr fmtF, addr delta

	invoke WriteConsole, hOut, addr msgEps, sizeof msgEps,addr lpWrite,0 
	invoke crt_scanf, addr fmtF, addr epsilon
	
	popa
	ret
Init endp

;Очистка буфера
ClearBuff proc
	mov ecx, sizeof buff/4
	lea edi, buff
	xor eax, eax
	cld
	; заполняем нулями в цикле
	@@:
		stosd
	loop @b
	ret
ClearBuff endp

; вывод стаблицы
Print proc
	
	; читсим буфер
	call ClearBuff
	; коныертируем результат в строку
	invoke FloatToStr, Xn, offset buff
	; выводим сообщение	
	invoke WriteConsole, hOut, addr wall, sizeof wall, addr lpWrite, 0
	invoke WriteConsole, hOut, addr buff, sizeof buff-3, addr lpWrite, 0
		
	call ClearBuff
	invoke FloatToStr, Ln, offset buff	
	invoke WriteConsole, hOut, addr wall, sizeof wall, addr lpWrite, 0
	invoke WriteConsole, hOut, addr buff, sizeof buff-3, addr lpWrite, 0
	
	call ClearBuff
	invoke FloatToStr, Sr, offset buff	
	invoke WriteConsole, hOut, addr wall, sizeof wall, addr lpWrite, 0
	invoke WriteConsole, hOut, addr buff, sizeof buff-3, addr lpWrite, 0
	
	call ClearBuff
	invoke FloatToStr, len, offset buff	
	invoke WriteConsole, hOut, addr wall, sizeof wall, addr lpWrite, 0
	invoke WriteConsole, hOut, addr buff, sizeof buff-3, addr lpWrite, 0	

	invoke WriteConsole, hOut, addr wall, sizeof wall, addr lpWrite, 0
	invoke WriteConsole, hOut, addr crlf, sizeof crlf, addr lpWrite, 0
		
	ret
Print endp

; функция Ln(x)
Fx proc X: dword, T: dword
pusha
	finit 	
	fldln2		; st(0)=ln(2) 
	mov eax, X
	fld qword ptr [eax]
	fyl2x		; st(0)=log2(x)*ln(2)=ln(x) 	
	mov eax, T
	fstp qword ptr [eax]	
popa
	ret 8
Fx endp

; сравнение вещественных чисел
; выход eax = 0 если X < Y
;		eax = 1 eckb X > Y
Compare proc X: dword, Y: dword
	
pusha	
	mov eax, Y
	fld qword ptr [eax]	
	mov eax, X
	fld qword ptr [eax]
	; проводим сравннеи
	fcompp
	; запираем флаги
	fstsw ax	
	sahf
	; переход если меньше
	jna @f	
popa	
	mov eax, 1
	jmp @ext
@@:
	mov eax, 0
@ext:
	ret 8
Compare endp

; вычисление ряда
Series proc X: dword, S: dword, F: dword, e: dword, Len: dword
local pow: dword
local i: dword

pusha

	mov ebx, F
	mov edx, e
	
	finit 
	
	; число членов ряда в 0
	mov i, 0
	fild i
	; начальное решение в 0
	mov eax, S
	fstp qword ptr [eax]	
@begin:
	; берем текущий член ряда (изначально 0)
	mov eax, S
	fld qword ptr [eax]
		
	; находим 2n + 1	
	mov ecx, i
	imul ecx, 2
	inc ecx
	mov pow, ecx
	
	; берем X
	mov eax, X
	fld qword ptr [eax]	
	fld1
	; x - 1
	fsubp st(1), st
	
	; возводим в степень умножением
	; в цикле
	cmp pow, 1
	jle @f
	fld st
mov ecx, pow
sub ecx,2
@pow1:
	fmul st(1), st
loop @pow1
	fmulp st(1), st
@@:

	; находи X + 1
	mov eax, X
	fld qword ptr [eax]
	fld1
	faddp st(1), st
	
	; возводим в степень
	cmp pow, 1
	jle @f
	fld st
mov ecx, pow
sub ecx,2
@pow2:
	fmul st(1), st
loop @pow2
	fmulp st(1), st
@@:

	; (2n+1)((x+1)^2n+1)
	fild pow
	fmulp st(1), st
	
	; ((x-1)^2n+1) / (2n+1)((x+1)^2n+1)
	fdivp st(1), st
	
	; сложение с предыдущим членом ряда
	faddp st(1), st
	
	; увеличим число членов ряда на 1
	inc i	
	
	mov eax, S
	fst qword ptr [eax]
	; находим сумму членов ряда
	fadd st, st
	
	fld qword ptr [ebx]
	fxch
	; разница между рядом и LnX
	fsubp st(1), st
	
	; сравнение с epsilon
	fld qword ptr [edx]
	fcompp
	fstsw ax	
	sahf
; если меньше то продолжить
jna @begin	
			
	fild i
	; забираем число членов ряда
	mov eax, Len
	fstp qword ptr [eax]

	mov eax, S
	fld qword ptr [eax]
	; забираем результат
	fadd st, st
	fst qword ptr [eax]	
		
popa
	ret 20
Series endp


Start:
	call Init	; инициализаци
	; заголовок таблицы
	invoke WriteConsole, hOut, addr msgTable, sizeof msgTable, addr lpWrite, 0
	
	@@:
		; находим Ln(x)
		push offset Ln
		push offset Xn
		call Fx
	
		; находим через ряд
		push offset len
		push offset epsilon	
		push offset Ln
		push offset Sr
		push offset Xn
		call Series
	
		; выводим результаты
		call Print
	
		; увеличим X на delta
		fld Xn
		fadd delta
		fstp Xn
		
		push offset Xk
		push offset Xn
		; сравнить начальные и конечное X
		call Compare
		cmp eax, 0
	; продолжать если меньше
	je @b
	
	invoke ReadConsole, hIn, addr buff, 1, addr lpWrite, 0
    invoke ExitProcess, 0
end Start
