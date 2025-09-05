# This program prompts the user to enter two numbers in the range
# [1000 - 5000] inclusive. Once the user enters the two values successfully
# it prints the sum of the two numbers
#
# @author	Brenna Ren, Aidan, Nikhil
# @version 	April 30, 2024
	
	
	.data

msg: 	.asciiz		"Enter a number between 1000 and 5000\n"
tooSmall:	.asciiz	"Number is too small.\n"
tooLarge:	.asciiz	"Number is too large.\n"

	.text

	.globl main

main:

	#Your code goes here
	
	input1:
	
		# prompts the user for an input
		li $v0, 4
		la $a0, msg
		syscall
		
		# reads number and sets $t0 to the input
		li $v0, 5
		syscall
		move $t0, $v0
	
		blt $t0, 1000, tooSmall1
		bgt $t0, 5000, tooLarge1
	
	input2:
	
		# prompts the user for an input
		li $v0, 4
		la $a0, msg
		syscall
		
		# reads number and sets $t0 to the input
		li $v0, 5
		syscall
		move $t1, $v0
	
		blt $t1, 1000, tooSmall2
		bgt $t1, 5000, tooLarge2

	addu $t2, $t0, $t1
	
	li $v0, 1
	move $a0, $t2
	syscall
	
	
	li $v0, 10
	syscall
	
tooSmall1:
	# prints that the number is too small
	li $v0, 4
	la $a0, tooSmall
	syscall
	j input1

tooLarge1:
	# prints that the number is too large
	li $v0, 4
	la $a0, tooLarge
	syscall
	j input1

tooSmall2:
	# prints that the number is too small
	li $v0, 4
	la $a0, tooSmall
	syscall
	j input2

tooLarge2:
	# prints that the number is too large
	li $v0, 4
	la $a0, tooLarge
	syscall
	j input2
	

