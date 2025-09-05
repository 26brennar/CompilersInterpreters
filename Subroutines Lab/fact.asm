# Program to use a subroutine (fact) to print the factorial of
# an inputted non-negative number.
# @author Brenna Ren
# @version 05/20/2024

.data
	# String that prompts user for a non-negative number
	inputPrompt:	.asciiz	"Enter a non-negative number.\n"
	
	# String that describes the ouput (factorial)
	outputPrompt:	.asciiz "Factorial: "
.text 0x00400000
.globl main

main:
	# prompts the user for a non-negative number
	li $v0, 4
	la $a0, inputPrompt
	syscall
	
	# gets the number input and stores in $a0
	li $v0, 5
	syscall
	move $a0, $v0
	
	# store return address of the subroutine in the stack memory
	subu $sp, $sp, 4
	sw $ra, ($sp)
	
	# jump to fact subroutine
	jal fact
	
	# restore stack memory
	lw $ra, ($sp)
	addu $sp, $sp, 4
	
	# store the return value of fact in $t0
	move $t0, $v0
	
	# prints output prompt for the factorial
	li $v0, 4
	la $a0, outputPrompt
	syscall
	
	# prints the factorial
	move $a0, $t0
	li $v0, 1
	syscall
	
	# Normal termination
	li $v0, 10
	syscall
	
fact: # returns the factorial of the parameter $a0 by recursively calling itself
	
	# branches to the base case when $a0 = 0
	beq $a0, 0, baseCase
	
	# subtracts 1 from the parameter to prepare for recursive call
	subu $a0, $a0, 1

	# store return address of the subroutine in the stack memory
	subu $sp, $sp, 4
	sw $ra, ($sp)
	
	# jump to fact subroutine (recursive call to fact(n-1))
	jal fact
	
	# restore stack memory
	lw $ra, ($sp)
	addu $sp, $sp, 4
	
	# adds 1 from the parameter to prepare for the return value
	addu $a0, $a0, 1
	
	# sets the return value to n * fact(n-1)
	mul $v0, $v0, $a0
	
	# return and jump back to address in $ra
	return:
		jr $ra

baseCase: # base case when $a0 = 0, which should return 1
	li $v0, 1
	j return