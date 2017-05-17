.386					
.model flat, stdcall	
option casemap: none	

include windows.inc
include kernel32.inc
include user32.inc
include masm32.inc

includelib kernel32.lib
includelib user32.lib
includelib masm32.lib

;N 25
;��������� �����  ��������.
;�������� ���� ���� ������������� ��������� ��������� �������.   
 
.data
	array	dd	20   dup(0)
	buff1	db	1000 dup(0)	
	buff2	db	1000 dup(0)	
	len		dd	0

.data?
	hIn		dd	?
	hOut	dd	?
	lpWrite	dd	?
	hInst	dd	?
	flag	dd	?
	
.const
	N  = 25	
	L  = 15
	
;������ ������� ����� � ������� ������	
;����:  arr - ��� �������
;       len - ����� �����
;       ebx - ��������
;		ecx - �������� � �������(������)
;�����: ���
_put macro arr, len, ebx, ecx
	push ecx
	push ebx
	push edx
	
	mov eax, ecx
	; ������ ����� ����� � ������� ������
	imul eax, len
	xor  edx, edx
	mov  ecx, 8
	idiv ecx
	
	; ������ ����� ����� �� ����� ���������� ���
	mov ecx, edx
	and ebx, 01FFFFFFh
	shl ebx, cl	
	
	mov ecx, len
	@@:
		btr dword ptr [arr+eax], edx
		inc edx
	loop @b
	; ������� � ������� ������
	or dword ptr [arr+eax], ebx
	
	pop edx
	pop ebx
	pop ecx
endm

;������ ����� ����� �� ������� ������	
;����:  arr - ��� �������
;       len - ����� �����
;		ecx - �������� � �������(������)
;�����: eax - ��������
_get macro arr, len, ecx
	push ecx
	push ebx
	push edx
	
	mov eax, ecx
	; ������ ����� ����� � ������� ������
	imul eax, len
	xor  edx, edx
	mov  ebx, 8
	idiv ebx
	
	mov eax, dword ptr [arr+eax]
	; ������ ����� ������ �� ����� ���������� ���
	mov ecx, edx
	shr eax, cl	
	and eax, 01FFFFFFh
	
	pop edx
	pop ebx
	pop ecx
endm

;������ ������ ���� �����
;����:  arr - ��� �������
;       len - ����� �����
;		ecx - �������� � �������(������)
;�����: ���
_neg macro arr, len, ecx	
	pusha
	; ����� �����
	_get arr, len, ecx
	mov ebx, eax
	;������ ���� �����
	not ebx
	inc ebx
	and ebx, 01FFFFFFh
	; ������� �� �����
	_put arr, len, ebx, ecx	
	popa
endm


.code

;����� ����� � �������� ����
;����:  eax - ����� ��� ������
;�����: ���
ToBuff proc buff: DWORD
	pusha
	mov esi, [buff]
	mov ebx, len
	mov ecx, N
	@print:
		mov byte ptr [esi+ebx], '1'
		test eax, 1
		jnz @not_zero
			mov byte ptr [esi+ebx], '0'
		@not_zero:
		inc ebx
		shr eax, 1
	loop @print
	mov len, ebx
	popa
	ret
ToBuff endp

WinProc2 proc hWin:DWORD, uMsg:DWORD, wParam:DWORD, lParam:DWORD
local hdc: HDC
local rect:RECT
local ps : PAINTSTRUCT
	.if	uMsg == WM_PAINT
		invoke BeginPaint,hWin, addr ps
        mov    hdc,eax
        invoke GetClientRect, hWin, addr rect
        invoke DrawText, hdc, addr buff2, len, addr rect, DT_CENTER 
        invoke EndPaint, hWin, addr ps	
	.elseif	uMsg == WM_CLOSE
		invoke	EndDialog,hWin,0
	.endif
	xor	eax,eax
	ret
WinProc2	endp


WinProc1 proc hWin:DWORD, uMsg:DWORD, wParam:DWORD, lParam:DWORD
local hdc: HDC
local rect:RECT
local ps : PAINTSTRUCT

	.if	uMsg == WM_PAINT
		.if flag == 0
			mov flag, 1
			invoke	DialogBoxParam, hInst, 102, 0, addr WinProc2, 0
			invoke ShowWindow, eax, SW_SHOW; 
		.endif
		invoke BeginPaint,hWin, addr ps
        mov    hdc,eax
        invoke GetClientRect, hWin, addr rect
        invoke DrawText, hdc, addr buff1, len, addr rect, DT_CENTER 
        invoke EndPaint, hWin, addr ps			
	.elseif	uMsg == WM_CLOSE
		invoke	EndDialog,hWin,0
	.endif
	xor	eax,eax
	ret
WinProc1	endp

Start:	
	xor ecx, ecx
	; ��������� ������ ��������������� ����������
	@generate:
		mov eax, ecx
		xor edx, edx
		mov ebx, 7
		div ebx
		add eax, 013253647h
		rol eax, cl
		xor eax, 083571754h
		; ������ ���� � ����������� �� �������� �������
		test edx, 1
		jz @zero
			xor eax, 01000000h	
		@zero:	
		mov ebx, eax
		_put array, N, ebx, ecx
	inc ecx
	cmp ecx, L
	jne @generate
	
	
	mov len, 0
	xor ecx, ecx
	@output1:		
		_get array, N, ecx	
		;������ � �����	
		push offset buff1	
		call ToBuff	
		mov ebx, len
		mov byte ptr buff1[ebx], 13
		mov byte ptr buff1[ebx + 1], 10
		add len, 2
		_neg array, N, ecx				
	inc ecx
	cmp ecx, L
	jne @output1	
	
	mov len, 0
	xor ecx, ecx
	@output2:		
		_get array, N, ecx	
		;������ � �����	
		push offset buff2		
		call ToBuff	
		mov ebx, len
		mov byte ptr buff2[ebx], 13
		mov byte ptr buff2[ebx + 1], 10
		add len, 2			
	inc ecx
	cmp ecx, L
	jne @output2		

	mov flag, 0	
	invoke	GetModuleHandle, NULL
	invoke DialogBoxParam, eax, 101, 0, addr WinProc1, 0
	
    invoke ExitProcess, 0
end Start