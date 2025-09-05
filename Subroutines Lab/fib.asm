# Program that prints the nth fibonacci sequence value of the inputted number n.
# Fibonacci Sequence: 0, 1, 1, 2, 3, 5, 8, ... , (n-1)+(n-2), ...
# @author Brenna Ren
# @version 05/20/2024

.data
	# String that prompts user for a non-negative number
	inputPrompt:	.asciiz	"Enter a non-negative number (n).\n"
	
	# String that describes the ouput (fibonacci)
	outputPrompt:	.asciiz "nth Fibonnacci term: "
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
	
	# jump to fib subroutine
	jal fib
	
	# restore stack memory
	lw $ra, ($sp)
	addu $sp, $sp, 4
	
	# store the return value of fib in $t0
	move $t0, $v0
	
	# prints output prompt for the fibonacci
	li $v0, 4
	la $a0, outputPrompt
	syscall
	
	# prints the fibonacci
	move $a0, $t0
	li $v0, 1
	syscall
	
	# Normal termination
	li $v0, 10
	syscall
	
fib: # recursive fibonacci method that calls fib(n-1) and fib(n-2)
	
	# base case when n <= 1
	ble $a0, 1, baseCase
	
	# stores n in the stack
	subu $sp, $sp, 4
	sw $a0, ($sp)
	
	# subtracts 1 from the parameter ($a0) to prepare for n-1 recursive call
	subu $a0, $a0, 1

	# store return address of the subroutine in the stack memory
	subu $sp, $sp, 4
	sw $ra, ($sp)
	
	# jumps to the fib subroutine for n-1 (finds fib(n-1))
	jal fib
	
	# restore stack memory
	lw $ra, ($sp)
	addu $sp, $sp, 4
	
	# loads n from the stack and restores stack memory (sets $a0 to n)
	lw $a0, ($sp)
	addu $sp, $sp, 4
	
	# stores fib(n-1) in the stack
	subu $sp, $sp, 4
	sw $v0, ($sp)
	
	# subtracts 2 from the parameter ($a0) to prepare for n-2 recursive call
	subu $a0, $a0, 2
	
	# store return address of the subroutine in the stack memory
	subu $sp, $sp, 4
	sw $ra, ($sp)
	
	# jumps to the fib subroutine for n-2 (finds fib(n-2))
	jal fib
	
	# restore stack memory
	lw $ra, ($sp)
	addu $sp, $sp, 4
	
	# loads fib(n-1) from the stack and restores stack memory (sets $t0 to fib(n-1))
	lw $t0, ($sp)
	addu $sp, $sp, 4
	
	# sets the return value to fib(n-1) + fib(n-2) ($t0 + $v0)
	addu $v0, $t0, $v0
	
	# return and jump back to address in $ra
	return:
		jr $ra

baseCase: # base case when n ($a0) <= 1, returns n
	move $v0, $a0
	j return