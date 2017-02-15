#include <stdio.h>
#include <stdlib.h>

// Include wait and other process type related
#include <sys/types.h>
#include <sys/wait.h>

// Include fork
#include <unistd.h>

// Include CTF trace generators
#include "hello-tp.h"

int main(void)
{
  pid_t child_pid, wpid;
  int status = 0;
  int i;
  int a[3] = {1, 2, 1};

  puts("Multiprocess CTF tracing test!\nPress Enter to continue...");
  /*
   * The following getchar() call is only placed here for the purpose
   * of this demonstration, to pause the application in order for
   * you to have time to list its tracepoints. It is not
   * needed otherwise.
   */
  getchar();

  /*
   * A tracepoint() call.
   *
   * Arguments, as defined in hello-tp.h:
   *
   * 1. Tracepoint provider name   (required)
   * 2. Tracepoint name            (required)
   * 3. my_integer_arg             (first user-defined argument)
   * 4. my_string_arg              (second user-defined argument)
   *
   * Notice the tracepoint provider and tracepoint names are
   * NOT strings: they are in fact parts of variables that the
   * macros in hello-tp.h create.
   */
  // Father starts
  tracepoint(hello_world, my_first_tracepoint, 0, "Father starts!");
  
  printf("parent_pid = %d\n", getpid());

  // Father spawn 3 childrens (multifork)
  for (i = 0; i < 3; i++)
  {
    printf("i = %d\n", i);
    if ((child_pid = fork()) == 0)
    {
      // Child starts
      tracepoint(hello_world, my_first_tracepoint, i, "Child starts!");
      
      printf("In child process (pid = %d)\n", getpid());
      if (a[i] < 2)
      {
	printf("Should be accept\n");

	// Child ends
	tracepoint(hello_world, my_first_tracepoint, i, "Child ends!");
	
	exit(1);
      }
      else
      {
	printf("Should be reject\n");

	// Child ends
	tracepoint(hello_world, my_first_tracepoint, i, "Child ends!");
	
	exit(0);
      }
      /*NOTREACHED*/
    }
  }

  // Father wait until all childrens return
  while ((wpid = wait(&status)) > 0)
  {
    printf("Exit status of %d was %d (%s)\n", (int)wpid, status,
	   (status > 0) ? "accept" : "reject");
  }

  // Father end
  tracepoint(hello_world, my_first_tracepoint, 0, "Father ends!");

  return 0;
}
