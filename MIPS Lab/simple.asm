# Program to multiply two numbers and print the result.
# @author Brenna Ren
# @version 04/26/2024

.text 0x00400000
.globl main

main:
	li $v0, 5	# read input into $v0
	syscall
	move $t0, $v0	# set $t0 to $v0
	
	li $v0, 5	# read input into $v0
	syscall
	move $t1, $v0	# set $t1 to $v0
	
	mul $t2, $t0, $t1	# store $t0 * $t1 into $t2
	
	# print the result
	li $v0, 1
	move $a0, $t2
	syscall
	
	li $v0, 10
	syscall
	# normal termination
