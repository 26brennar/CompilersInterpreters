# Program to generate a random number and have the user guess it.
# @author Brenna Ren
# @version 04/24/2024

.data
	# String that prompts the user to enter the upper bound of the generated number
	rangePrompt:	.asciiz	"Input the upper bound of the generated number:\n"
	
	# String that prompts the user to enter their guess
	guessPrompt:	.asciiz	"Input your guess:\n"
	
	# Messages for if the guess is too high or too low
	tooHigh:	.asciiz	"Too high!\n"
	tooLow:		.asciiz	"Too low!\n"
	
	# Congratulatory message for when the user guesses correctly
	doneMsg:	.asciiz	"Congrats! You guessed the number!\n"
.text 0x00400000
.globl main

main:
	# prompts the user for an input
	li $v0, 4
	la $a0, rangePrompt
	syscall
	
	# reads the upper bound and sets $a1 to the input
	li $v0, 5
	syscall
	move $a1, $v0
		
	# generates a random number within the given range and stores it in $t0
	li $v0, 42
	li $a0, 0
	syscall
	move $t0, $a0

	guess:
		# prompts the user for a guess
		li $v0, 4
		la $a0, guessPrompt
		syscall
		
		# reads the guess and sets $t1 to the guess
		li $v0, 5
		syscall
		move $t1, $v0
		
		# if the guess is less than the generated number
		blt $t1, $t0, low
		
		# if the guess is greater than the generated number
		bgt $t1, $t0, high
		
		# if the guess is equal to the generated number, prints congrats message
		li $v0, 4
		la $a0, doneMsg
		syscall
	
	li $v0, 10
	syscall
	# normal termination

low:	# prints too low and prompts user for another guess
	li $v0, 4
	la $a0, tooLow
	syscall
	
	# gets another guess
	j guess

high:	# prints too high and prompts user for another guess
	li $v0, 4
	la $a0, tooHigh
	syscall
	
	# gets another guess
	j guess