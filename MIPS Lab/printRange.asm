# Program to print numbers in a given range and step.
# @author Brenna Ren
# @version 04/24/2024
.data
	# New line to separate each printed number
	newLine:	.asciiz	"\n"
.text 0x00400000
.globl main

main:
	# reads the lower bound and sets $t0 to the input
	li $v0, 5
	syscall
	move $t0, $v0

	# reads the upper bound and sets $t1 to the input
	li $v0, 5
	syscall
	move $t1, $v0
		
	# reads the step value and sets $t0 to the input
	li $v0, 5
	syscall
	move $t2, $v0
	
	# if the lower bound is greater than the upper bound, then it swaps them
	ble $t0, $t1, continue
	move $t3, $t0
	move $t0, $t1
	move $t1, $t3
	
	continue:
		loop:	# loops through all the numbers and prints them
			
			# checks if the number is out of range
			bgt $t0, $t1, endloop
			
			# prints a new line
			li $v0, 4
			la $a0, newLine
			syscall
			
			# prints the number
			li $v0, 1
			move $a0, $t0
			syscall
		
			# increments the index (lower bound) by the given step and loops again
			addu $t0, $t0, $t2
			j loop
	
		endloop:
			li $v0, 10
			syscall
			# normal termination