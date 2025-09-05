	.data
	varx:	.word	0
	vary:	.word	0
	varcount:	.word	0
	.text
	.globl main
main:
	li $v0 2
	sw $v0 varx
	lw $v0 varx
	subu $sp $sp 4
	sw $v0 ($sp)
	li $v0 1
	lw $t0 ($sp)
	addu $sp $sp 4
	addu $v0 $t0 $v0
	sw $v0 vary
	lw $v0 varx
	subu $sp $sp 4
	sw $v0 ($sp)
	lw $v0 vary
	lw $t0 ($sp)
	addu $sp $sp 4
	addu $v0 $t0 $v0
	sw $v0 varx
	lw $v0 varx
	subu $sp $sp 4
	sw $v0 ($sp)
	lw $v0 vary
	lw $t0 ($sp)
	addu $sp $sp 4
	mul $v0 $t0 $v0
	move $a0 $v0
	li $v0 1
	syscall
	li $v0 11
	li $a0 10
	syscall
	lw $v0 varx
	subu $sp $sp 4
	sw $v0 ($sp)
	lw $v0 vary
	lw $t0 ($sp)
	addu $sp $sp 4
	ble $t0 $v0 endif2
	lw $v0 varx
	move $a0 $v0
	li $v0 1
	syscall
	li $v0 11
	li $a0 10
	syscall
	lw $v0 vary
	move $a0 $v0
	li $v0 1
	syscall
	li $v0 11
	li $a0 10
	syscall
endif2:
	li $v0 14
	subu $sp $sp 4
	sw $v0 ($sp)
	li $v0 14
	lw $t0 ($sp)
	addu $sp $sp 4
	bne $t0 $v0 endif3
	li $v0 14
	subu $sp $sp 4
	sw $v0 ($sp)
	li $v0 14
	lw $t0 ($sp)
	addu $sp $sp 4
	beq $t0 $v0 endif4
	li $v0 3
	move $a0 $v0
	li $v0 1
	syscall
	li $v0 11
	li $a0 10
	syscall
endif4:
	li $v0 14
	subu $sp $sp 4
	sw $v0 ($sp)
	li $v0 14
	lw $t0 ($sp)
	addu $sp $sp 4
	bgt $t0 $v0 endif5
	li $v0 4
	move $a0 $v0
	li $v0 1
	syscall
	li $v0 11
	li $a0 10
	syscall
endif5:
endif3:
	li $v0 15
	subu $sp $sp 4
	sw $v0 ($sp)
	li $v0 14
	lw $t0 ($sp)
	addu $sp $sp 4
	ble $t0 $v0 endif6
	li $v0 5
	move $a0 $v0
	li $v0 1
	syscall
	li $v0 11
	li $a0 10
	syscall
endif6:
	li $v0 1
	sw $v0 varcount
loop7:
	lw $v0 varcount
	subu $sp $sp 4
	sw $v0 ($sp)
	li $v0 15
	lw $t0 ($sp)
	addu $sp $sp 4
	bgt $t0 $v0 endLoop7
	lw $v0 varcount
	move $a0 $v0
	li $v0 1
	syscall
	li $v0 11
	li $a0 10
	syscall
	lw $v0 varcount
	subu $sp $sp 4
	sw $v0 ($sp)
	li $v0 1
	lw $t0 ($sp)
	addu $sp $sp 4
	addu $v0 $t0 $v0
	sw $v0 varcount
	j loop7
endLoop7:
	li $v0,  10	# Normal termination
	syscall
