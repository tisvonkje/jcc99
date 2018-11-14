/*
 * entry.c - runtime support
 */
#include <stdio.h>
#include <string.h>

#define WORDSIZE 4
#define WORDMASK 0xfffffffc
/*
 * Externals
 */
extern void *heapptr;
/*
 * Convert C-string to String
 */
void *toString(char *cstring)
{
	/*
	 * Allocate storage for String
	 */
	void *result=heapptr;
	heapptr+=WORDSIZE;
	/*
	 * Allocate storage for the char array
	 */
	void *array=heapptr;
	heapptr+=WORDSIZE+strlen(cstring)*2;
	/*
	 * Keep heap on WORD boundary
	 */
	heapptr=(heapptr+WORDSIZE-1)&WORDMASK;
	/*
	 * Store array in String
	 */
	*((void **)result)=array;
}

int utf8(unsigned char *str,unsigned short *buffer)
{
  int c;
  unsigned char *p=str;
  unsigned short *q=buffer;
  int n=0;
  int expect=0;
  int collect=0;
  while((c=*p++)!=0)
  {
    if(expect==0)
    {
      if((c>>7)==0)
      {
        n++;
        if(q!=NULL)
          *q++=c;
      } else if((c>>6)==2)
      {
        n++;
        if(q!=NULL)
          *q++=0xFFDC;
      } else if(c>>5==6)
      {
        expect=1;
        collect=c&0x1f;
      }
      else if(c>>4==14)
      {
        expect=2;
        collect=c&0x0f;
      }
      else if(c>>3==30)
      {
        expect=3;
        collect=c&0x07;
      }
      else
      {
        n++;
        if(q!=NULL)
          *q++=0xfffd;;
      }
    } else
    {
      if(c>>6!=2)
      {
        n++;
        if(q!=NULL)
          *q++=0xfffd;
        expect=0;
      } else
      {
        collect<<=6;
        collect|=(c&0x3f);
        expect--;
        if(expect==0)
        {
          n++;
          if(q!=NULL)
          {
            if(collect>0xffff)
              collect=0xfffd;
            *q++=collect;
          }
        }
      }
    }
  }
  return n;
}
int main(int argc, char **argv)
{
  short buffer[2];
  printf("%d\n",utf8(argv[1],buffer));
  printf("%02x %02x\n",buffer[0],buffer[1]);
}
