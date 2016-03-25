import java.util.Random;
import java.util.Arrays;

public class Sorting {

    private static Random randomGenerator;
    private static int[] tempArr;
    private static int numComparisons;

    private static void printArray(int[] arr, String msg) {
        System.out.print(msg + " [" + arr[0]);
        for (int i = 1; i < arr.length; i++) {
            System.out.print(", " + arr[i]);
        }
        System.out.println("]");
    }

    public static void insertSort(int[] arr, int left, int right) {
        // insertSort the subarray arr[left, right]
        int i, j;

        for (i = left + 1; i <= right; i++) {
            int temp = arr[i];           // store a[i] in temp
            j = i;                       // start shifts at i
            // until one is smaller,
            while (j > left && arr[j - 1] >= temp) {
                arr[j] = arr[j - 1];        // shift item to right
                --j;                      // go left one position
            }
            arr[j] = temp;              // insert stored item
        }  // end for
    }  // end insertSort()

    public static void insertionSort(int[] arr) {
        insertSort(arr, 0, arr.length - 1);
    } // end insertionSort()


    public static void maxheapify(int[] arr, int i, int n) {
        // Pre: the left and right subtrees of node i are max heaps.
        // Post: make the tree rooted at node i as max heap of n nodes.
        int max = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        if (left < n && arr[left] > arr[max]) max = left;
        if (right < n && arr[right] > arr[max]) max = right;

        if (max != i) {  // node i is not maximal
            exchange(arr, i, max);
            maxheapify(arr, max, n);
        }
    }

    public static void exchange(int[] arr, int i, int j) {
        int t = arr[i];
        arr[i] = arr[j];
        arr[j] = t;
    }

    public static void heapsort(int[] arr) {
        // Build an in-place bottom up max heap
        for (int i = arr.length / 2; i >= 0; i--) maxheapify(arr, i, arr.length);

        for (int i = arr.length - 1; i > 0; i--) {
            exchange(arr, 0, i);       // move max from heap to position i.
            maxheapify(arr, 0, i);     // adjust heap
        }
    }

    private static void mergesort(int[] arr, int low, int high) {
        // Check if low is smaller then high, if not then the array is sorted
        if (low < high) {
            // Get the index of the element which is in the middle
            int middle = low + (high - low) / 2;
            // Sort the left side of the array
            mergesort(arr, low, middle);
            // Sort the right side of the array
            mergesort(arr, middle + 1, high);
            // Combine them both
            merge(arr, low, middle, high);
        }
    }

    private static void merge(int[] arr, int low, int middle, int high) {
        // Copy first part into the arrCopy array
        for (int i = low; i <= middle; i++) {
            tempArr[i] = arr[i];
        }

        int i = low;
        int j = middle + 1;
        int k = low;

        // Copy the smallest values from either the left or the right side back
        // to the original array
        while (i <= middle && j <= high) {
            numComparisons++;
            if (tempArr[i] <= arr[j]) {
                arr[k] = tempArr[i];
                i++;
            } else {
                arr[k] = arr[j];
                j++;
            }
            k++;
        }

        // Copy the rest of the left part of the array into the target array
        while (i <= middle) {
            arr[k] = tempArr[i];
            k++;
            i++;
        }
    }

    private static void bottomUpMergeSort(int[] arr) {
        int t = 1;

        while (t < arr.length) {
            int s = t;
            t = 2*s;
            int i = 0;

            while (i + t <= arr.length) {
                merge(arr, i, i + s - 1, i + t - 1);
                i += t;
            }

            if (i + s < arr.length) {
                merge(arr, i, i + s - 1, arr.length - 1);
            }
        }
    }

    private static void quicksort(int[] arr, int low, int high) {
        int i = low, j = high;

        // Get the pivot element from the middle of the list
        int pivot = arr[(high + low) / 2];

        // Divide into two lists
        while (i <= j) {
            // If the current value from the left list is smaller then the pivot
            // element then get the next element from the left list
            while (arr[i] < pivot) i++;

            // If the current value from the right list is larger then the pivot
            // element then get the next element from the right list
            while (arr[j] > pivot) j--;

            // If we have found a value in the left list which is larger than
            // the pivot element and if we have found a value in the right list
            // which is smaller then the pivot element then we exchange the
            // values.
            // As we are done we can increase i and j
            if (i < j) {
                exchange(arr, i, j);
                i++;
                j--;
            } else if (i == j) {
                i++;
                j--;
            }
        }

        // Recursion
        if (low < j)
            quicksort(arr, low, j);
        if (i < high)
            quicksort(arr, i, high);
    }

    public static void garbage() {
        // randomGenerator = new Random();

        // // built-in sort
        // long start = System.currentTimeMillis();
        // if (size < 101) printArray("Initial array:");
        // Arrays.sort(arr);
        // if (size < 101) printArray("out");
        // long finish = System.currentTimeMillis();
        // System.out.println("Arrays.sort: " + (finish - start) + " milliseconds.");

        // // Heap sort
        // for (int i = 0; i < size; i++) arr[i] = arrCopy[i];
        // start = finish;
        // if (size < 101) printArray("in");
        // heapsort();
        // if (size < 101) printArray("out");
        // finish = System.currentTimeMillis();
        // System.out.println("heapsort: " + (finish - start) + " milliseconds.");

        // // Merge sort
        // for (int i = 0; i < size; i++) arr[i] = arrCopy[i];
        // start = finish;
        // if (size < 101) printArray("in");
        // mergesort(0, size - 1);
        // if (size < 101) printArray("out");
        // finish = System.currentTimeMillis();
        // System.out.println("mergesort: " + (finish - start) + " milliseconds.");

        // // Quick sort
        // for (int i = 0; i < size; i++) arr[i] = arrCopy[i];
        // start = finish;
        // if (size < 101) printArray("in");
        // quicksort(0, size - 1);
        // if (size < 101) printArray("out");
        // finish = System.currentTimeMillis();
        // System.out.println("quicksort: " + (finish - start) + " milliseconds.");

        // // arr[0..size-1] is already sorted. We randomly swap 100 pairs to make it nearly-sorted.
        // for (int i = 0; i < 100; i++) {
        //     int j = randomGenerator.nextInt(size);
        //     int k = randomGenerator.nextInt(size);
        //     exchange(j, k);
        // }
        // for (int i = 0; i < size; i++) arrCopy2[i] = arr[i];

        // // Quick sort on nearly-sorted array
        // start = finish;
        // if (size < 101) printArray("in");
        // quicksort(0, size - 1);
        // if (size < 101) printArray("out");
        // finish = System.currentTimeMillis();
        // System.out.println("quicksort on nearly-sorted: " + (finish - start) + " milliseconds.");

        // // Insert sort on nearly-sorted array
        // for (int i = 0; i < size; i++) arr[i] = arrCopy2[i];
        // start = finish;
        // if (size < 101) printArray("in");
        // insertionSort();
        // if (size < 101) printArray("out");
        // finish = System.currentTimeMillis();
        // System.out.println("insertsort on nearly-sorted: " + (finish - start) + " milliseconds.");
    }

    public static void main(String[] args) {
        randomGenerator = new Random();
        //problem1();
        problem2();
    }

    private static int[] getRandomArray(int max, int size) {
        int[] arr = new int[size];

        for (int i = 0; i < size; i++) {
            arr[i] = randomGenerator.nextInt(max);
        }

        return arr;
    }

    private static void problem1() {
        int numArrays = 100;
        int[] numIntsOpts = {1000000, 2000000, 4000000};
        int[] intRangeOpts = {1000, 1000000};

        for (int intRange : intRangeOpts) {
            for (int numInts : numIntsOpts) {
                // Generate arrays to sort
                System.out.println(String.format("Generating %d sets of %d ints between 0 and %d", numArrays, numInts, intRange));
                int[][] arrays = new int[numArrays][numInts];

                for (int[] arr : arrays) {
                    System.arraycopy(getRandomArray(intRange, numInts), 0, arr, 0, numInts);
                }

                int numStanMergeSorts = 0;
                int numBUMergeSorts = 0;
                int totalStanMergeTime = 0;
                int totalBUMergeTime = 0;
                long start;
                int[] arrayToSort = new int[numInts]; // array to pass as parameter to merging algorithms
                tempArr = new int[numInts]; // array to store temp arrays used in merge()

                // Sort arrays with standard merge sort
                for (int[] arr : arrays) {
                    numComparisons = 0;
                    System.arraycopy(arr, 0, arrayToSort, 0, arr.length);
                    System.arraycopy(arr, 0, tempArr, 0, arr.length);

                    start = System.currentTimeMillis();
                    mergesort(arrayToSort, 0, arrayToSort.length - 1);
                    totalStanMergeTime += System.currentTimeMillis() - start;
                    numStanMergeSorts++;
                }
                System.out.println("Average standard merge sort time = " + totalStanMergeTime/numStanMergeSorts + " ms");
                System.out.println("Average number of comparisons = " + numComparisons/numStanMergeSorts);

                // Sort arrays with bottom-up merge sort
                for (int[] arr : arrays) {
                    numComparisons = 0;
                    System.arraycopy(arr, 0, arrayToSort, 0, arr.length);
                    System.arraycopy(arr, 0, tempArr, 0, arr.length);

                    start = System.currentTimeMillis();
                    bottomUpMergeSort(arrayToSort);
                    totalBUMergeTime += System.currentTimeMillis() - start;
                    numBUMergeSorts++;
                }
                System.out.println("Average bottom-up merge sort time = " + totalBUMergeTime/numBUMergeSorts + " ms");
                System.out.println("Average number of comparisons = " + numComparisons/numBUMergeSorts);
                System.out.println();
            }
        }
    }

    private static void problem2() {
        
    }
}