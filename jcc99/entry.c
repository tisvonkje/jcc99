/*
 * entry.c - runtime support
 */
#include <stdio.h>
#include <string.h>
#include <unistd.h>
#include <stdlib.h>

#define WORDSIZE 4
#define WORDMASK 0xfffffffc

extern void *Vector_java_lang_String;
/*
 * Externals
 */
extern void *heapptr;
/*
 * Convert a C-string to a character array
 */
int utf8(unsigned char *str, unsigned short *buffer)
{
  int c;
  unsigned char *p = str;
  unsigned short *q = buffer;
  int n = 0;
  int expect = 0;
  int collect = 0;
  while ((c = *p++) != 0)
  {
    if (expect == 0)
    {
      if ((c >> 7) == 0)
      {
        n++;
        if (q != NULL)
          *q++ = c;
      } else if ((c >> 6) == 2)
      {
        n++;
        if (q != NULL)
          *q++ = 0xFFDC;
      } else if (c >> 5 == 6)
      {
        expect = 1;
        collect = c & 0x1f;
      } else if (c >> 4 == 14)
      {
        expect = 2;
        collect = c & 0x0f;
      } else if (c >> 3 == 30)
      {
        expect = 3;
        collect = c & 0x07;
      } else
      {
        n++;
        if (q != NULL)
          *q++ = 0xfffd;
        ;
      }
    } else
    {
      if (c >> 6 != 2)
      {
        n++;
        if (q != NULL)
          *q++ = 0xfffd;
        expect = 0;
      } else
      {
        collect <<= 6;
        collect |= (c & 0x3f);
        expect--;
        if (expect == 0)
        {
          n++;
          if (q != NULL)
          {
            if (collect > 0xffff)
              collect = 0xfffd;
            *q++ = collect;
          }
        }
      }
    }
  }
  return n;
}

typedef struct CharArray {
  void *classVector;
  int size;
  unsigned short data[0];
} CharArray;

typedef struct ByteArray {
  void *classVector;
  int size;
  unsigned char data[0];
} ByteArray;

typedef struct String {
  void *classVector;
  CharArray *charArray;
} String;

typedef struct StringArray {
  void *classVector;
  int size;
  String *data[0];
} StringArray;

/*
** Create heap space for a CharArray
** num - number of array elements
*/
CharArray *newCharArray(int num)
{
  CharArray *result=heapptr;
  heapptr+=num*2+sizeof(CharArray)+3;
  heapptr=(void *)((int)heapptr&WORDMASK);
  result->classVector=0; //FIXME
  result->size=num;
  return result;
}
/*
** Create heap space for a ByteArray
** num - number of array elements
*/
ByteArray *newByteArray(int num)
{
  ByteArray *result=heapptr;
  heapptr+=num+sizeof(ByteArray)+3;
  heapptr=(void *)((int)heapptr&WORDMASK);
  result->classVector=0; //FIXME
  result->size=num;
  return result;
}
/*
** Create heap space for a StringArray
** num - number of array elements
*/
StringArray *newStringArray(int num)
{
  StringArray *result=heapptr;
  heapptr+=num*WORDSIZE+sizeof(StringArray);
  result->classVector=0; //FIXME
  result->size=num;
  return result;
}
/*
** Create heap space for a String
*/
String *newString()
{
  String *result=heapptr;
  heapptr+=sizeof(String);
  result->classVector=&Vector_java_lang_String;
  return result;
}
/*
** Entry
*/
StringArray *entry(int argc, unsigned char **argv)
{
  /*
   * Allocate storage for array of Strings
   */
  StringArray *result=newStringArray(argc);
  for(int i=0;i<argc;i++)
  {
    int size=utf8(argv[i],NULL);
    /*
     * Allocate storage for String
     */
    result->data[i]=newString();
    /*
     * Allocate storage for the char array
     */
    result->data[i]->charArray=newCharArray(size);
    /*
     * Write array
     */
    utf8(argv[i],result->data[i]->charArray->data);
  }
  return result;
}

ByteArray *sysgetcwd()
{
  char *buf=getcwd(NULL,0);
  int size=strlen(buf);
  ByteArray *result=newByteArray(size);
  memcpy(result->data,buf,size);
  free(buf);
  return result;
}