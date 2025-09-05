# DOESNT WORK i give up
# @author Brenna Ren
# @version 04/25/2024

.data
	# String that prompts the user to enter 10 numbers
	lengthPrompt:	.asciiz	"Enter the 10 numbers line by line:\n"
	
	# [change]
	sumPrint:	.asciiz	"\nSum: "
	
	# declares space for array of 10 integers (4 bytes each)
		.align	4
	arr:		.space	1
.text 0x00400000
.globl main

main:
	# prompts the user for ten numbers
	li $v0, 4
	la $a0, lengthPrompt
	syscall
	
	# index initialization
	li $t0, 0
	
	# inputs the ten numbers
	input:
		# check if the loop has completed (10 times)
		bge $t0, 4, endInput
		
		# gets number input and sets to position in array
		li $v0, 5
		syscall
		sw $v0, arr($t0)
		
		# increments array index by 4 (integers take 4 bytes) and loops again
		addu $t0, $t0, 4
		j input
	
	endInput:
		li $a0, 0
		
		# store return address of the subroutine in the stack memory
		subu $sp, $sp, 4
		sw $ra, ($sp)
	
		# [change]
		jal newlistnode
	
		# restore stack memory
		lw $ra, ($sp)
		addu $sp, $sp, 4
		
		move $a0, $v0
		
		# store return address of the subroutine in the stack memory
		subu $sp, $sp, 4
		sw $ra, ($sp)
	
		# [change]
		jal sumList
	
		# restore stack memory
		lw $ra, ($sp)
		addu $sp, $sp, 4
		
		move $a0, $v0
		li $v0, 1
		syscall
		
		# Normal termination
		li $v0, 10
		syscall

test:
	beq $a0, 0, baseCase3

	li $v0 1
	syscall
	
	return3:
		jr $ra

baseCase3:
	j return3

sumList:
	lw $t0, $a0
	subu $a0, $a0, 4
	
	beq $a0, 0, baseCase2
	
	# store return address of the subroutine in the stack memory
	subu $sp, $sp, 4
	sw $ra, ($sp)
	
	# [change]
	jal sumList
	
	# restore stack memory
	lw $ra, ($sp)
	addu $sp, $sp, 4
	
	addu $v0, $t0, $v0
	
	return2:
		jr $ra

baseCase2:
	li $v0, 0
	j return2
		
	
newlistnode:
	bge $a0, 0, baseCase
	
	subu $sp, $sp, 4
	lw $t0, arr($a0)
	sw $t0, ($sp)
	
	subu $sp, $sp, 4
	lw $t0, ($sp)
	sw $t0, ($sp)
	
	addu $a0, $a0, 4
	
	# store return address of the subroutine in the stack memory
	subu $sp, $sp, 4
	sw $ra, ($sp)
	
	# [change]
	jal newlistnode
	
	# restore stack memory
	lw $ra, ($sp)
	addu $sp, $sp, 4
	
	return:
		jr $ra
	
baseCase:
	subu $sp, $sp, 4
	lw $t0, arr($a0)
	sw $t0, ($sp)
	
	subu $sp, $sp, 4
	li $t0, 0
	sw $t0, ($sp)
	
	j return