#
# Run time system for jcc
#
	.globl	 l6b8074
l6b8074:
	pushl	%ebp
	movl	%esp, %ebp
	pushl	%edi
	movl	8(%ebp), %edi
	subl	$12, %esp
	pushl	%edi
	calll	_strlen
	addl	$12, %esp
	pushl	%eax
	pushl	%edi
	pushl	$1
	calll	_write$UNIX2003
	addl	$16, %esp
	movl	$3, (%eax)
	popl	%edi
	popl	%ebp
	retl
