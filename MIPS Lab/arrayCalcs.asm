# Program to store 10 given numbers in an array and find the sum, average,
# minimum, and maximum of them. Prints the average as a decimal.
# @author Brenna Ren
# @version 04/25/2024

.data
	# String that prompts the user to enter 10 numbers
	lengthPrompt:	.asciiz	"Enter the 10 numbers line by line:\n"
	
	# String printing labels for the sum, average, minimum, and maximum
	sumPrint:	.asciiz	"\nSum: "
	avgPrint:	.asciiz	"\nAverage: "
	minPrint:	.asciiz	"\nMinimum: "
	maxPrint:	.asciiz	"\nMaximum: "
	
	# declares space for array of 10 integers (4 bytes each)
		.align	4
	arr:		.space	10
.text 0x00400000
.globl main

main:
	# prompts the user for ten numbers
	li $v0, 4
	la $a0, lengthPrompt
	syscall
	
	# index initialization
	li $t0, 0
	
	# sum of values initialization
	li $t1, 0
	
	# minimum initialization
	li $t2, 2147479548
	
	# maximum initialization
	li $t3, -2147479548
	
	# inputs the ten numbers
	input:
		# check if the loop has completed (10 times)
		bge $t0, 40, endInput
		
		# gets number input and sets to position in array
		li $v0, 5
		syscall
		sw $v0, arr($t0)
		
		# increments array index by 4 (integers take 4 bytes) and loops again
		addu $t0, $t0, 4
		j input
	
	endInput:
		# index initialization
		li $t0, 0
	
		# gets the ten numbers and adds to sum and checks for min/max
		loop:
			# check if the loop has completed (10 times)
			bge $t0, 40, endLoop
			
			lw $t5, arr($t0)
		
			# adds number to the sum
			addu $t1, $t1, $t5
		
			# new minimum
			blt $t5, $t2, newMin
		
			notMin:
				# new maximum
				bgt $t5, $t3, newMax
		
			notMax:
				# increments array index by 4 (integers take 4 bytes) and loops again
				addu $t0, $t0, 4
				j loop
	
		endLoop:
			# prints sum
			li $v0, 4
			la $a0, sumPrint
			syscall
		
			li $v0, 1
			move $a0, $t1
			syscall
		
			# finds average
			li $t4, 10
			div $t1, $t4
		
			# prints average
			li $v0, 4
			la $a0, avgPrint
			syscall
		
			li $v0, 1
			mflo $a0 # quotient
			syscall
		
			li $v0, 11
			la $a0, '.'
			syscall
		
			li $v0, 1
			mfhi $a0 # remainder
			bge $a0, 0, continue
			li $t4, 0 # if the remainder is negative
			subu $a0, $t4, $a0
		
			continue:
				syscall
		
				# prints minimum
				li $v0, 4
				la $a0, minPrint
				syscall
		
				li $v0, 1
				move $a0, $t2
				syscall
		
				# prints maximum
				li $v0, 4
				la $a0, maxPrint
				syscall
		
				li $v0, 1
				move $a0, $t3
				syscall
		
				li $v0, 10
				syscall
				# normal termination
		
newMin:	# sets new minimum
	move $t2, $t5
	j notMin # continues to check for max

newMax:	# sets new maximum
	move $t3, $t5
	j notMax # continues to increment index	