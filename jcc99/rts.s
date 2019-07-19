#
# Run time system for jcc
#
	.globl	_heapptr, athrow
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
	pushl	$0 			// padding
	pushl	$1 			// number of characters
	pushl	%edi 			// address
	movl	12(%ebp),%eax 		// PrintStream
	movl	4(%eax),%eax 		// get FileDescriptor (offset)
	pushl	4(%eax) 		// get fd (offset)
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
	call	_sysgetcwd
	movl	%ebp,%esp	// unlink stack frame
	popl	%ebp
	popl	%ecx		// get return address
	pushl	%eax		// push result
	jmpl	*%ecx		// return
	
#
# Exception thrower
# Called by code generator with exception as argument
# Idea, handle this in machine language since we should be able to look up
# in stack frames and unroll stack and jump
# for the instance check call java
#
athrow:
# link stack frame
	pushl	%ebp
	movl	%esp,%ebp
# align stack
	andl	$0xfffffff0,%esp
	pushl	$0 			// padding
	pushl	$0 			// padding
	pushl	$0 			// padding
	pushl	8(%ebp)			// exception
	calll	_athrow
// this normal return should be changed to a jump to the exception handler returned by _athrow
// and of course include stack unrolling
//	addl	$16, %esp
	movl	%ebp,%esp
	popl	%ebp
	popl	%ecx
	addl	$8,%esp
	jmpl	*%ecx

