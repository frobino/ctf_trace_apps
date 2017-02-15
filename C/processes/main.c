#include <stdio.h>
#include <stdlib.h>

// Include wait and other process type related
#include <sys/types.h>
#include <sys/wait.h>

// Include fork
#include <unistd.h>

int main(void)
{
  pid_t child_pid, wpid;
  int status = 0;
  int i;
  int a[3] = {1, 2, 1};

  printf("parent_pid = %d\n", getpid());
  for (i = 0; i < 3; i++)
  {
    printf("i = %d\n", i);
    if ((child_pid = fork()) == 0)
    {
      printf("In child process (pid = %d)\n", getpid());
      if (a[i] < 2)
      {
	printf("Should be accept\n");
	exit(1);
      }
      else
      {
	printf("Should be reject\n");
	exit(0);
      }
      /*NOTREACHED*/
    }
  }

  while ((wpid = wait(&status)) > 0)
  {
    printf("Exit status of %d was %d (%s)\n", (int)wpid, status,
	   (status > 0) ? "accept" : "reject");
  }
  return 0;
}
