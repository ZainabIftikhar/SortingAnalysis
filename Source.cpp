#include <iostream>
#include <iostream>
#include <vector>
#include <time.h>
#include <random>
#include <ctime>
#include <cstdlib>
#include <chrono>


typedef std::chrono::high_resolution_clock Clock;
using namespace std;

int heapsize = 0;
unsigned long long exchange = 0;
unsigned long long comparisons = 0;

void insertionSort(unsigned long data[], int n){
	int i, j;
	unsigned long key;
	for (i = 1; i < n; i++) {
		key = data[i];
		j = i - 1;
		while (j >= 0 && data[j] > key){
			comparisons++; //each time while is true
			exchange++;
			data[j + 1] = data[j];
			j = j - 1;
		}
		data[j + 1] = key;
		exchange++;
	}
}

void merge(unsigned long data[], int p, int q, int r) {
	int n1 = q - p + 1;
	int n2 = r - q;
	int i, j, k;
	i = j = k = 0;
	if (n1 < 0 || n2 < 0)
		return;

	unsigned long *L = new unsigned long[n1 + 1];
	unsigned long *R = new unsigned long[n2 + 1];

	for (; i < n1; i++){
		L[i] = data[p + i];
	}

	for (; j < n2; j++){
		R[j] = data[q + j + 1];
	}

	i = j = 0;
	k = p;
	while (i < n1 && j < n2){
		if (L[i] <= R[j]){
			data[k] = L[i];
			i++;
		}
		else {
			data[k] = R[j];
			j++;
		}
		exchange++;
		comparisons++;
		k++;
	}

	while (i < n1){
		data[k] = L[i];
		i++;
		k++;
		exchange++;
	}

	while (j < n2){
		data[k] = R[j];
		j++;
		k++;
		exchange++;
	}
}

void mergeSort(unsigned long data[], int s, int e){
	int mid;
	if (s < e){
		mid = (s + e) / 2;
		mergeSort(data, s, mid);
		mergeSort(data, mid + 1, e);
		merge(data, s, mid, e);
	}
}

int partition(unsigned long data[], int p, int r){
	unsigned long pivot = data[r];
	int i = p - 1;

	for (int j = p; j <= r - 1; j++){
		if (data[j] <= pivot){
			i++;
			long unsigned temp = data[i];
			data[i] = data[j];
			data[j] = temp;
			exchange += 2;
		}
		comparisons++;
	}
	long unsigned temp = data[i + 1];
	data[i + 1] = data[r];
	data[r] = temp;
	exchange += 2;
	return i + 1;
}

int randomPartition(unsigned long data[], int p, int r){
	int random = rand() % r - p;
	unsigned long pivot = data[random];
	int i = p - 1;

	for (int j = p; j <= r; j++){
		if (j == random)
			++j;

		if (data[j] <= pivot){
			i++;
			long unsigned temp = data[i];
			data[i] = data[j];
			data[j] = temp;
			exchange += 2;
		}
		comparisons++;
	}
	long unsigned temp = data[i + 1];
	data[i + 1] = data[random];
	data[random] = temp;
	exchange += 2;
	return i + 1;
}

void quickSort(unsigned long data[], int s, int e){
	if (s < e){
		int q = partition(data, s, e);
		quickSort(data, s, q - 1);
		quickSort(data, q + 1, e);
	}
}

void randomizedQuickSort(unsigned long data[], int s, int e){
	if (s < e){
		int q = randomPartition(data, s, e);
		quickSort(data, s, q - 1);
		quickSort(data, q + 1, e);
	}
}

void maxHeapify(unsigned long data[], int i){
	int left = 2 * i;
	int right = 2 * i + 1;
	int largest;
	if (left < heapsize && data[left] > data[i])
		largest = left;
	else
		largest = i;
	if (left < heapsize) comparisons++; //else won't be checked

	if (right < heapsize && data[right] > data[largest])
		largest = right;
	if (right < heapsize) comparisons++; //else won't be checked
	
	if (largest != i){
		long temp = data[i];
		data[i] = data[largest];
		data[largest] = temp;
		exchange += 2;
		maxHeapify(data, largest);
	}
}

void buildMaxHeap(unsigned long data[], int n){
	heapsize = n;
	for (int i = n / 2; i >= 0; i--){
		maxHeapify(data, i);
	}
}

void heapSort(unsigned long data[], int n){

	buildMaxHeap(data,n);
	for (int i = n - 1; i >= 1; i--){
		unsigned long temp = data[0];
		data[0] = data[i];
		data[i] = temp;
		heapsize -= 1;
		exchange += 2;
		maxHeapify(data, 0);
	}
}

void iterativeMergeSort(unsigned long data[], int n){
	int i;
	int left;
	for (i = 1; i <= n - 1; i = 2 * i) {
		for (left = 0; left < n - 1; left += 2 * i){
			int mid = left + i - 1;
			int right; 
			int x = left + 2 * i - 1;
			int y = n - 1;
			if (x < y)
				merge(data, left, mid, x);
			else
				merge(data, left, mid, y);
		}
	}
}

int main(){

	const unsigned long size = 10000;
	unsigned long max = unsigned long (pow(2, 32) - 1);
	int number = 0;

	double t1, t2;

	while (number < 4){

		comparisons = 0;
		exchange = 0;
		unsigned long *A = new unsigned long[size];
		for (int i = 0; i < size; i++) {
			long random = rand() % max;
			A[i] = random;
		}


		t1 = clock();
		//insertionSort(A, size);
		//mergeSort(A,0,size-1);
		//iterativeMergeSort(A, size);
		//quickSort(A, 0, size-1);
		//randomizedQuickSort(A,0,size-1);
		//heapSort(A, size);

		//run each at a time!
		t2 = clock();
		
		cout << "Time taken\t: " << (t2 - t1) / CLK_TCK << " sec\n";
		cout << "Number of exchanges " << exchange << endl;
		cout << "Number of comparisons " << comparisons << endl << endl;
		
		//for (int i = 0; i < size; i++)cout << A[i] << endl;

		number++;
	}
	system("pause");
	return 0;
}