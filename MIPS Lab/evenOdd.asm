# Program to read input of a number and prints if its even or odd.
# @author Brenna Ren
# @version 04/22/2024

.data
	# String that prompts the user to a number
	userPrompt:	.asciiz	"Input your number (0 to quit):\n"
	
	# String outputs that tell the user if the number is odd/even
	oddPrint:	.asciiz	"Odd\n"
	evenPrint:	.asciiz	"Even\n"
.text 0x00400000
.globl main

main:
	# keeps prompting the user for more numbers until they enter 0
	query:
		# prompts the user for an input
		li $v0, 4
		la $a0, userPrompt
		syscall
	
		# reads number and sets $t0 to the input
		li $v0, 5
		syscall
		move $t0, $v0
		
		# if the user enters 0, the program ends
		beq $t0, 0, end
	
		# divide input by 2
		li $t1, 2
		div $t0, $t1
	
		# retrieves remainder and checks if its 1 (positive) or -1 (negative)
		mfhi $t2
		beq $t2, 1, odd
		beq $t2, -1, odd
	
		# prints if its even
		li $v0, 4
		la $a0, evenPrint
		syscall
		
		# asks for another input
		j query
	
	end:
		li $v0, 10
		syscall
		# normal termination

odd:	# prints if its odd
	li $v0, 4
	la $a0, oddPrint
	syscall
	
	# asks for another input
	j query