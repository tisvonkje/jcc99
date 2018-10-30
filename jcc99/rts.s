#
# Run time system for jcc
#
	.globl	 l6b8074
l6b8074:
	pushl	%ebp
	movl	%esp,%ebp
	andl	$0xfffffff0,%esp
	movl	8(%ebp),%edi
	pushl	$0
	pushl	$0
	pushl	$0
	pushl	%edi
	calll	_strlen
	addl	$16,%esp
	pushl	$0
	pushl	%eax
	pushl	%edi
	pushl	$1
	calll	_write$UNIX2003
//	addl	$16, %esp
	movl	%ebp,%esp
	popl	%ebp
	popl	%ecx
	addl	$4,%esp
	jmpl	*%ecx
