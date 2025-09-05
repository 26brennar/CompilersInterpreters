# Program to play music of a given length. The "music" consists of random
# notes with relatively middle pitches played for half a second each.
# @author Brenna Ren
# @version 04/25/2024

.data
	# String that prompts the user to enter duration
	lengthPrompt:	.asciiz	"How many seconds do you want the music to play for?\n"
.text 0x00400000
.globl main

main:
	# prompts the user for a music length
	li $v0, 4
	la $a0, lengthPrompt
	syscall
	
	# reads music length and sets it to $t1
	li $v0, 5
	syscall
	move $t1, $v0
	
	# multiplies the duration in seconds by 2 for the number of notes needed
	li $t3, 2
	mult $t1, $t3
	mflo $t1
	
	# index initialization
	li $t2, 0
	
	# loops through for each note and plays one
	loop:
		# checks if the loop has executed enough times
		bge $t2, $t1, end
		
		# generates random note to play
		li $v0, 42
		li $a0, 0
		li $a1, 60
		syscall

		# plays the random note
		addu $a0, $a0, 40
		li $a1, 500	# duration = 500 milliseconds
		li $a2, 0	# instrument = Acoustic Grand Piano
		li $a3, 100	# volume = 100
		li $v0, 33
		syscall
		
		# increments index by 1
		addu $t2, $t2, 1
		j loop
	end:
		li $v0, 10
		syscall
		# normal termination
		