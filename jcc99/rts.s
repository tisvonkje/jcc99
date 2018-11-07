#
# Run time system for jcc
#

#
# private native void print(char c)
#
	.globl	la53c0e
la53c0e:
# link stack frame
	pushl	%ebp
	movl	%esp,%ebp
# align stack
	andl	$0xfffffff0,%esp
# get address of parameter
	leal	8(%ebp),%edi
	pushl	$0
	pushl	$1
	pushl	%edi
	movl	12(%ebp),%eax
	pushl	(%eax)
	calll	_write$UNIX2003
//	addl	$16, %esp
	movl	%ebp,%esp
	popl	%ebp
	popl	%ecx
	addl	$8,%esp
	jmpl	*%ecx

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
