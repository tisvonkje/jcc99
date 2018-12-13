#
# Run time system for jcc
#
	.globl	_heapptr
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
	pushl	$0		// buf
	pushl	$0		// size
	pushl	$0		// pad
	pushl	$0		// pad
	call	_getcwd
	addl	$16,%esp
	movl	_heapptr,%edx	// value to return
	movl	%edx,%ecx
	addl	$8,%ecx		// skip class vector and array size
L1:	movb	(%eax),%bl	// get a byte
	cmpb	$0,%bl
	je	L2		// nul byte? ready
	movb	%bl,(%ecx)	// store byte
	incl	4(%edx)		// increment array size
	incl	%eax		// increment source pointer
	incl	%ecx		// increment destination pointer
	jmp	L1		// loop back
L2:	addl	$3,%ecx		// align heap
	andl	$0xfffffffc,%ecx
	mov	%ecx,_heapptr	// store heap
	movl	%ebp,%esp	// unlink stack frame
	popl	%ebp
	popl	%ecx		// get return address
	pushl	%edx		// push result
	jmpl	*%ecx		// return

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
