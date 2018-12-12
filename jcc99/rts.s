#
# Run time system for jcc
#

#
# private native void print(byte b)
#
	.globl	Method_java_io_FileOutputStream_print__q_B_p_V
Method_java_io_FileOutputStream_print__q_B_p_V:
# link stack frame
	pushl	%ebp
	movl	%esp,%ebp
# align stack
	andl	$0xfffffff0,%esp
# get address of parameter (we need pointer for w write)
	leal	8(%ebp),%edi
	pushl	$0 // padding
	pushl	$1 // numver of characters
	pushl	%edi // address
	movl	12(%ebp),%eax // PrintStream
	movl	4(%eax),%eax // get FileDescriptor
	pushl	4(%eax) // get fd
	calll	_write$UNIX2003
//	addl	$16, %esp
	movl	%ebp,%esp
	popl	%ebp
	popl	%ecx
	addl	$8,%esp
	jmpl	*%ecx
#
# private static native byte [] sysgetcwd();
#
	.globl	Method_java_lang_System_sysgetcwd__q__p__a_B
Method_java_lang_System_sysgetcwd__q__p__a_B:
# link stack frame
	pushl	%ebp
	movl	%esp,%ebp
# align stack
	andl	$0xfffffff0,%esp
	call	_getcwd

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
