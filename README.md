# Using-KNN-for-Classification-Of-Films-Based-on-User-and-Critics-Ratings.

## Requirements
* Cassandra 2.4
* Windows/ Linux operating system
* JDK 7
* Eclipse IDE
* Minimum 512 MB RAM
* Data set 
* Cassandra and JAVA connectivity Drivers

## Introduction
Classification is a supervised learning model where the output of model contains target variables.  My project classifies if the particular movie  is  good or bad based on the users  and critics rating. It uses KNN algorithm for classification.  In this, some past training data is collected, analyzed and used to construct some classifier by using the classification algorithm. In k-NN classification, the output is a class membership. An object is classified by a majority vote of its neighbors, with the object being assigned to the class most common among its k nearest neighbors (k is a positive integer, typically small). If k = 1, then the object is simply assigned to the class of that single nearest neighbor.

## Scope
The naïve version of the algorithm is easy to implement by computing the distances from the test example to all stored examples, but it is computationally intensive for large training sets. Using an appropriate nearest neighbor search algorithm makes k-NN computationally tractable even for large data sets. Many nearest neighbor search algorithms have been proposed over the years; these generally seek to reduce the number of distance evaluations actually performed.
k-NN has some strong consistency results. As the amount of data approaches infinity, the algorithm is guaranteed to yield an error rate no worse than twice the Bayes error rate(the minimum achievable error rate given the distribution of the data). k-NN is guaranteed to approach the Bayes error rate for some value of k (where k increases as a function of the number of data points). Various improvements to k-NN are possible by using proximity graphs. 

## Algorithm
* Let K is any integer (K>=2). Value of K is to set any integer. Generally,
  * If number of classes =j=2 then set K=some odd integer
  * K must not be multiple of number of classes i.e  K is not equal to j.
* Find n Euclidean Distances between X0 and X1, X2…Xn.
* Arrange all distances calculated in step 2 in non-decreasing order.
* Select K number of shortest distances as K- distances.
* Find K-nearest neighbor of test instance X0.
* Among K instances selected in step 5 , some instances may belong to class y1, some y2 and so on. Let Ki is number of K-nearest instances belonging to class i. For each class I, find Ki Where i=1,2…j  K>=0.
* Select maximum of K1, K2, …Kj  classify X0 to class corresponding to maximum of (K1,K2,…Kj).
