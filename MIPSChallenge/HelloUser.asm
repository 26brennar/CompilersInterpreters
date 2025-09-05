# Prompts the user for their name then print's Hello user name
# 
# @author  Brenna Ren, Aidan, Nikhil
# @version April 30, 2024

	.data
	
msg:	.asciiz		"Enter your name: "	# prompt for the user to enter name
hello:	.asciiz 	"Hello "		# used to display Hello user name
name:	.space		20			# space to save the name in memory
len:	.word		20			# max characters allowed in name


	.text

	.globl main

main:

	# Your code goes here
	
	# prompts the user for an input
	li $v0, 4
	la $a0, msg
	syscall
	
	# gets username input
	li $v0, 8
	la $a0, name
	la $a1, len
	syscall
	
	# prints hello
	li $v0, 4
	la $a0, hello
	syscall
	
	# prints name
	li $v0, 4
	la $a0, name
	syscall

	# Normal termination
	li $v0, 10
	syscall
