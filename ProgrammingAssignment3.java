package programmingassignment.pkg3;

import java.util.Random;

public class ProgrammingAssignment3 {

    static int heapsize = 0;
    static long exchange = 0;
    static long comparisons = 0;
    public static void main(String[] args) {
        
        int n = 1;
        while (n < 6){
        exchange = 0;
        comparisons = 0;
        int size = 1000000;
        long max = (long) (Math.pow(2, 32) - 1);
        long [] data = new long[size + 1];
       
        Random random = new Random();
        for (int i = 1; i < size + 1; i++)
            data[i] = (long)random.nextInt()+(long)(1L<<31);
        
        double startTime = System.nanoTime();
        insertionSort(data,size);
        System.out.println("Comparison" + comparisons);
        
        System.out.println("Exchanges"+ exchange);
        double endTime = System.nanoTime();
        double duration = (endTime - startTime)/1000000000;
        System.out.println("That took " + (duration) + " seconds");
        n++;
        }
    }
    
    static void insertionSort(long[] data, int n){
	int i, j;
        long key;
        for (i = 1; i < n; i++) {
            comparisons++;
            key = data[i];
            exchange++;
            j = i-1;
            while (j >= 0 && data[j] > key){
                comparisons++;comparisons++;
                exchange++;
                data[j+1] = data[j];
                j = j-1;
            }
        data[j+1] = key;
        exchange++;
        }
        comparisons++;
    }

    static void merge(long[] data, int p, int q, int r) {
	int n1 = q - p + 1;
	int n2 = r - q;
	int i, j, k;
	i = j = k = 0;
        if (n1 < 0 || n2 < 0)
            return;
        
	long[] L = new long[n1]; 
        long[] R = new long[n2];
	
	for (; i < n1; i++){
            L[i] = data[p + i];
                comparisons++;
                exchange++;
        }
        comparisons++;

	for (; j < n2; j++){
            comparisons++;
            R[j] = data[q + j + 1];

            exchange++;
        }
        comparisons++;
    
	i = j = 0;
        k = p;
	while (i < n1 && j < n2){
            comparisons++;
            comparisons++;
            if (L[i] <= R[j]){
                data[k] = L[i];
                exchange++;
                i++;
            }
            else {
                data[k] = R[j];
                exchange++;
                j++;
            }
            comparisons++;
            k++;
        }
        
        while (i < n1){
            data[k] = L[i];
            i++;
            k++;
            exchange++;
            comparisons++;
        }
        comparisons++;
 
        while (j < n2){
            data[k] = R[j];
            j++;
            k++;
            exchange++;
            comparisons++;
        }
        comparisons++;
    }

    static void mergeSort(long[] data, int s, int e){
	int mid;
	if (s < e){
		mid = (s + e) / 2;
		mergeSort(data, s, mid);
		mergeSort(data, mid + 1, e);
		merge(data, s, mid, e);
	}
        comparisons++;
    }

    static int partition(long[] data, int p, int r){
	int i = p;
	for (int j = p; j < r - 1; ++j){
            comparisons++;
            if (data[j] <= data[r-1]){
                long temp  = data[i];
                data[i] = data[j];
                data[j] = temp;
                exchange++;exchange++;exchange++;
                i++;
            }
            comparisons++;
	}
        comparisons++;
        long temp = data[i];
        data[i] = data[r-1];
	data[r - 1] = temp;
        exchange++;exchange++;exchange++;
	return i;
    }   

    static int randomPartition(long[] data, int p, int r){	 
        int  random = (int) (Math.random() * r + p);
	int i = p;
	for (int j = p; j < r  ; ++j){
            comparisons++;
            if (j == random) 
                ++j;

            if (data[j] <= data[random]){
                long temp = data[i];
                data[i] = data[j];
                data[j] = temp;
                i++;
                exchange++;exchange++;exchange++;
            }
            comparisons++;
        }
        comparisons++;
        long temp = data[i];
        data[i] = data[random];
	data[random] = temp;
        exchange++;exchange++;exchange++;
	return i;
}

    static void quickSort(long[] data, int s, int e){
	if (s < e){
            int q = partition(data, s, e);
            quickSort(data, s, q);
            quickSort(data, q + 1, e);
        }
        comparisons++;
    }

    static void randomizedQuickSort(long[] data,int s,int e){
	if (s < e){
            int q = randomPartition(data, s, e);
            quickSort(data, s, q);
            quickSort(data, q + 1, e);
	}
        comparisons++;
    }

    static void maxHeapify(long [] data, int i){
	int left = 2 * i;
	int right = 2 * i + 1;
	int largest;
	if (left < heapsize && data[left] > data[i])
            largest = left;
        else
            largest = i;

        comparisons++;comparisons++;
	if (right < heapsize && data[right] > data[largest])
            largest = right;
        comparisons++;comparisons++;
	if (largest != i){
            long temp = data[i];
            data[i] =  data[largest];
            data[largest] = temp;
            exchange++;exchange++;exchange++;
            maxHeapify(data, largest);
	}
        comparisons++;
    }   

    static void buildMaxHeap(long[] data){
	heapsize = data.length;
	for (int i = data.length / 2; i >= 0; i--){
            maxHeapify(data, i);
            comparisons++;
        }
        comparisons++;
    }

    static void heapSort(long[] data){

	buildMaxHeap(data);
	for (int i = data.length - 1; i >= 1; i--){
            comparisons++;
            long temp = data[0];
            data[0] = data[i];
            data[i] = temp;
            heapsize -= 1;
            exchange++;exchange++;exchange++;
            maxHeapify(data, 0);
	}
        comparisons++;
    }
    
    static void iterativeMergeSort(long[]arr, int n){
        
        int curr_size;
        int left_start;
        for (curr_size=1; curr_size<=n-1; curr_size = 2*curr_size) {
            comparisons++;
            for (left_start=0; left_start<n-1; left_start += 2*curr_size){
                comparisons++;
                int mid = left_start + curr_size - 1;
                int right_end = Math.min(left_start + 2*curr_size - 1, n-1);
                comparisons++;
                merge(arr, left_start, mid, right_end);
            }
            comparisons++;
        }
        comparisons++;
    }
}
