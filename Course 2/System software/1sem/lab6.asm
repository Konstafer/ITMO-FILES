.model tiny
.code
org 100h      ; смещение PSP
jmp Main      ; переход на нерезидентную часть

; РЕЗИДЕНТНЫЙ МОДУЛЬ (замена int 09h, адрес CS:0102h)
Resident:
cli           ; запрет прерываний
push ax       ; сохранение состояния 1
mov ah,2      ; чтение состояния управляющих клавиш
int 16h
test al,8     ; ALT зажат?
jz No_HK      ; нет - не хоткей
test al,3     ; хотя бы один SHIFT зажат?
jz No_HK      ; нет - не хоткей
in al,60h     ; чтение сканкода
cmp al,20h    ; клавиша 'D' ?
jne No_HK     ; нет - не хоткей
inc Flag      ; ХОТКЕЙ НАЖАТ - инвертировать флаг активации
in al,61h     ; и завершить прерывание
mov ah,al     ; чтобы нажатие хоткея не обрабатывалось системно
or al,80h
out 61h,al
mov al,ah
out 61h,al
mov al,20h
out 20h,al
jmp Break

No_HK:        ; обработка остальных нажатий (не хоткея)
pop ax        ; восстановление состояния 1
pushf         ; системная обработка
call far cs:[Vect]
push ax       ; сохранение состояния 2
test Flag,1   ; активация = включено?
jz Break      ; нет - выйти
mov ah,1      ; чтение последнего символа в буфере
int 16h
jz Break      ; буфер пуст - выход
cmp al,20h    ; проверка на символ 
jb Break      ; в буфере отображаемый символ?
pop ax        ; да - восстановить состояние 2
jmp far cs:[Vect] ; и произвести системную обработку ещё раз
Break:        ; если 1 или оба вызова системного обработчика не потребовались
pop ax        ; восстановление стека
sti           ; разрешение прерываний
iret          ; если вызов системного обработчика не потребовался
; Переменные резидента
Flag db 0     ; флаг активации
Vect dw ?,?   ; сохранённый вектор исходного обработчика

; НЕРЕЗИДЕНТНАЯ ЧАСТЬ
Main:
xor bh,bh        ; чтение командной строки
mov bl,[80h]
cmp [bx+80h],'+' ; параметр '+' - инсталлировать резидента
je Install
cmp [bx+80h],'-' ; параметр '-' - деинсталлировать резидента
je Uninstall

; Нет параматров (или любые другие) - выбрана проверка состояния
 lea dx,Msg_NotInstall ; по умолчанию считать неустановленным
 call GetState   ; проверка инсталляции
 jne GetRes      ; выдать сообщение по результату
 lea dx,Msg_Install
 GetRes:
 mov ah,9
 int 21h
 ret

; Выбрана установка
Install:
 call GetState   ; проверка инсталляции
 jne DoInstall   ; не установлен - перейти к инсталляции
 lea dx,Msg_AlreadyInstall
 mov ah,9        ; уже установлен - выдать сообщение
 int 21h
 ret             ; и выйти
 DoInstall:      ; инсталляция
 mov ax,3509h    ; чтение и сохранение исходного вектора
 int 21h
 mov Vect[0],bx
 mov Vect[2],es
 lea dx,Resident ; загрузка вектора резидента
 mov ax,2509h
 int 21h
 lea dx,Msg_Install
 mov ah,9        ; выдать сообщение об инсталляции
 int 21h
 lea dx,Main     ; указатель на конец резидентного модуля
 int 27h         ; завершиться, но остаться резидентным

; Выбрано удаление
Uninstall:
 call GetState   ; проверка инсталляции
 je DoUninstall  ; установлен - перейти к удалению
 lea dx,Msg_NotInstall
 mov ah,9        ; не установлен - выдать сообщение
 int 21h
 ret             ; и выйти
 DoUninstall:    ; удаление резидента
 mov ax,3509h    ; ищем резидентный модуль в памяти
 int 21h
 mov dx,es:[Vect]; читаем из резидента вектор исходного обработчика
 mov ds,es:[Vect+2]
 mov ax,2509h    ; восстанавливаем исходный обработчик
 int 21h
 mov ah,49h      ; освобождение памяти резидента (адрес остался в ES)
 int 21h
 push cs         ; восстановление DS
 pop ds
 lea dx,Msg_Uninstall
 mov ah,9        ; выдать сообщение о деинсталляции
 int 21h
 ret             ; и завершить программу

; Функция проверки состояния устаноки
GetState:
mov ax,3509h   ; первое слово обработчика
int 21h
lea si,Resident; сравниваем с первым словом резидента
mov ax,[si]
cmp ax,es:[bx] ; результат - флаг ZF (ZF=1: обработчик - это мы)
ret

; Строковые константы
Msg_AlreadyInstall db 'Resident is already installed$'
Msg_NotInstall db 'Resident not installed$'
Msg_Install db 'Resident installed$'
Msg_Uninstall db 'Resident uninstalled$'
end
