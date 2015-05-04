MyCode
======

Welcome to my repository.



ForestSimulator

simulates the number of trees in a forests. There're five forests, five climate, and four plants could be chosen. Real data applied.  User can create their own forest or choose scenario from our library, user can also chose the simulation time. Lifetime of the plants is affected by the enviroment, including fitness of the climate. 

Hashtable is used to store the results, where the keys are the name of plants, and values are the amount of them. It's printed at the end of main class.

The original purpose of this project is for doing an assignment of Geology class. When we put different plants in the same enviroment, some extinct very quickly, since the enviroment doesn't fit. For this reason, if they're put in desert, you will see the output goes to 0 very quickly. So I suggest start trying with defalut value, and then varies the factors around the defalut values. 

----------------------------------------------------

Cachelab-hand

The files are tests and makefiles given by the class, except for the trans.c and csim.c. The other files serves as helper files to help run the cache lab. 

csim.c
Cache simulator, which simulates the behavior of cache. Comments are included in the file.

trans.c
Perform the matrix wavefront transformation with less cache misses as possible.

Here is the fomula of matrix wavefront transform:

for(int j =1; j<N ; j ++)
{
	for(int i = 1; i < M; i++){
		A[i][j] = A[i-1][j-1] + A[i-1][j] +A[i][j-1];
	}
}

The way we do it is to cut the cache block into smaller blocks and perform the operation one by one, which helps reduces cache misses.


