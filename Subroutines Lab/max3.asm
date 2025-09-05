# Program to use a subroutine (max3) to print the maximum of
# three inputted numbers.
# @author Brenna Ren
# @version 05/20/2024

.data
	# String that prompts the user to enter 3 numbers
	inputPrompt:	.asciiz	"Enter three numbers.\n"
	
	# String that describes the output (maximum)
	outputPrompt:	.asciiz	"Maximum: "
.text 0x00400000
.globl main

main:
	# prompts the user for 3 numbers
	li $v0, 4
	la $a0, inputPrompt
	syscall
	
	# gets the first number input and stores in $a0
	li $v0, 5
	syscall
	move $a0, $v0
	
	# gets the second number input and stores in $a1
	li $v0, 5
	syscall
	move $a1, $v0
	
	# gets the third number input and stores in $a2
	li $v0, 5
	syscall
	move $a2, $v0
	
	# store return address of the subroutine in the stack memory
	subu $sp, $sp, 4
	sw $ra, ($sp)
	
	# jump to max3 subroutine
	jal max3
	
	# restore stack memory
	lw $ra, ($sp)
	addu $sp, $sp, 4
	
	# store the return value of max3 in $t0
	move $t0, $v0
	
	# prints output prompt for the maximum
	li $v0, 4
	la $a0, outputPrompt
	syscall
	
	# prints the maximum
	move $a0, $t0
	li $v0, 1
	syscall
	
	# Normal termination
	li $v0, 10
	syscall
	
max3: # returns the maximum of the three parameters ($a0, $a1, and $a2)

	# store return address of the subroutine in the stack memory
	subu $sp, $sp, 4
	sw $ra, ($sp)
	
	# jump to max2 subroutine
	jal max2
	
	# restore stack memory
	lw $ra, ($sp)
	addu $sp, $sp, 4
	
	# store the return value of max2 in $t0
	move $t0, $v0
	
	# jumps to continue3 if max of the first two numbers is greater than the third number
	bgt $t0, $a2, continue3
	
	# otherwise, sets return value to third number
	move $v0, $a2
	
	# return and jump back to address in $ra
	continue3:
		jr $ra
	

max2: # returns the maximum of the two parameters ($a0 and $a1)
	
	# branches to firstGreater if first number is greater
	bgt $a0, $a1, firstGreater
	
	# otherwise, sets return value to second number
	move $v0, $a1
	
	# return and jump back to address in $ra
	continue2:
		jr $ra

firstGreater: # sets return value to first number
	move $v0, $a0
	j continue2