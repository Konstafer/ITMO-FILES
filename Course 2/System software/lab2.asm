.model	small
.stack	200h
.data
	str_len			dw	40h
	bit_mask		dq	0FFFFFFFFFFFFFFFFh
	str_target1_len	dw	0
	str_target2_len	dw	0
	
	str_source		db	'abchdasuas1$1dsaashd$dsadasdadhvabchdasuasd$1dsaashd$dsAdasdadhv'
	str_target1		db	40h dup(?)
	str_target2		db	40h dup(?)
	
	msg_target1	db	'result 1: ', 13,10, '$'
	msg_target2	db	'result 2: ', 13,10, '$'
	
	delimeter	db	13,10, '$'
	
.code

printDelimeter proc near
	push	ax
	push	dx
	lea		dx, delimeter
	mov		ah, 09h
	int		21h
	pop		dx
	pop		ax
	ret
printDelimeter endp
	
printDecChar proc near
	;*** save regirsters
	push	ax
	push	bx
	push	cx
	push	dx
	;*** procedure code
	cmp		ax, 0
	jge		proc_printDecChar_isPositive
	;Is Negative
	mov		bx, ax
	mov		ah, 02h
	mov		dl, '-'
	int		21h
	mov		ax, bx
	neg		ax
proc_printDecChar_isPositive:
	xor		cx, cx
	mov		bx, 10
proc_printDecChar_divLoop:
	xor		dx, dx
	div		bx
	push	dx
	inc		cx
	test	ax, ax
	jnz		proc_printDecChar_divLoop
	mov		ah, 02h
proc_printDecChar_print:
	pop		dx
	add		dx, 30h
	int		21h
	loop	proc_printDecChar_print
	;*** restore resitester
	pop		dx
	pop		cx
	pop		bx
	pop		ax
	ret
printDecChar endp
	
printChar proc near
	push	ax
	mov		ax, 02h
	int		21h
	pop		ax
printChar endp
	
main:
	mov		ax, @data
	mov		ds,	ax
	xor		ax, ax
	
;===============
;	Формирование первой строки
;	Все символы из исходной строки являющиеся прописными буквами
;===
	mov		cx, str_len
	mov		str_target1_len, 0
	xor		di, di
	xor		si, si
_loop1:
	;проверка бита в битной маске
	
	push	si
	push	di
	push	cx
	mov		ax, si
	xor		dx, dx
	mov		bx, 16
	div		bx
	mov		di, ax
	mov		ax, word ptr bit_mask[di]
	mov		cx, dx
_loop1_bitMask_ROL:
	rol		ax, 1
	loop	_loop1_bitMask_ROL
	pop		cx
	pop		di
	pop		si
	jnc		_loop1_maskFail
	
	;позиция символа прошла по маске, заносим теперь этот символ в ax
	mov		al, str_source[si]
	
	cmp		al, 'a'
	jl		_loop1_maskFail
	cmp		al, 'z'
	jg		_loop1_maskFail
	;символ подходит, заносим в результирующию
	mov		str_target1[di], al
	inc		str_target1_len
	inc		di
_loop1_maskFail:
	inc		si
	loop	_loop1
	
;===================
;	Формирование второй строки
;	до первого найденного в исходной строке символа '$' за которым следует цифра
;===
	xor		di, di		
	xor		si, si
	mov		str_target2_len, 0
	mov		cx, str_len
	cmp		cx, 0
	jle		_loop2_exit
_loop2:	
	mov		si, cx
	mov		al, str_source[si]
	cmp		al, '$'
	jne		_loop2_fail
	cmp		si, 1
	jl		_loop2_fail
	push	ax
	mov		al, str_source[si-1]
	cmp		al, '0'
	jl		_loop2_failRest
	cmp		al, '9'
	jg		_loop2_failRest
	jmp		_loop2_exit
_loop2_failRest:
	pop		ax
_loop2_fail:
	mov		str_target2[di], al
	inc		di
	inc		str_target2_len
	loop	_loop2
_loop2_exit:

;===============================
;	вывод результатов
	lea		dx, msg_target1
	mov		ah, 09h
	int		21h
	xor		si, si
	xor		ax, ax
	mov		cx, str_target1_len
	cmp		cx, 0
	jle		_print_loop1_exit
_print_loop1:
	mov		dl, str_target1[si]
	inc		si
	mov		ah, 02h
	int		21h
	loop	_print_loop1
_print_loop1_exit:

	call	printDelimeter
	lea		dx, msg_target2
	mov		ah, 09h
	int		21h
	xor		si, si
	mov		cx, str_target2_len
	cmp		cx, 0
	jle		_print_loop1_exit
_print_loop2:
	mov		dl, str_target2[si]
	mov		ah, 02h
	int		21h
	inc		si
	loop	_print_loop2
_print_loop2_exit:
	
	;HALT
	mov		ax, 4c00h
	int		21h
	int 	03h
end main