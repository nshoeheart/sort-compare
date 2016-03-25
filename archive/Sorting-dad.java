import java.util.Arrays;
import java.util.Random;

public class Sorting {

    private static Random randomGenerator;
    private static int[] tempArr;
    private static int numComparisons;

    private static boolean stopRecursionIfSorted = false;
    private static boolean useInsertSortForShorties = false;

    private static boolean medianQuickSort = false;

    private static boolean isSorted(int[] arr, int lo, int hi) {
//System.out.println( "isSorted lo = " + lo + ", hi = " + hi );
        //for (int i = lo; i <= hi; i++) {
          //System.out.print( arr[ i ] + "," );
        //}
        //System.out.println();
        boolean result = true;
        for (int i = lo; i <= (hi - 1); i++) {
            if (arr[i] > arr[i + 1]) {
              result = false;
              break;
            }
        }
//System.out.println( "result = " + result );
        return result;
    }

    private static void printArray(int[] arr, String msg) {
      System.out.println(
          msg + 
          " isSorted = " + isSorted( arr, 0, arr.length - 1 )
      );
/*
        System.out.print(
          msg + 
          " isSorted = " + isSorted( arr, 0, arr.length - 1 ) +
          " [" + arr[0]);
        for (int i = 1; i < arr.length; i++) {
            System.out.print(", " + arr[i]);
        }
        System.out.println("]");
*/
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


    public static void maxHeapify(int[] arr, int i, int n) {
        // Pre: the left and right subtrees of node i are max heaps.
        // Post: make the tree rooted at node i as max heap of n nodes.
        int max = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        if (left < n && arr[left] > arr[max]) max = left;
        if (right < n && arr[right] > arr[max]) max = right;

        if (max != i) {  // node i is not maximal
            exchange(arr, i, max);
            maxHeapify(arr, max, n);
        }
    }

    public static void exchange(int[] arr, int i, int j) {
        int t = arr[i];
        arr[i] = arr[j];
        arr[j] = t;
    }

    public static void heapSort(int[] arr) {
        // Build an in-place bottom up max heap
        for (int i = arr.length / 2; i >= 0; i--) maxHeapify(arr, i, arr.length);

        for (int i = arr.length - 1; i > 0; i--) {
            exchange(arr, 0, i);       // move max from heap to position i.
            maxHeapify(arr, 0, i);     // adjust heap
        }
    }

    private static void mergeSort(int[] arr, int low, int high) {
        // Check if low is smaller then high, if not then the array is sorted
        if (low < high) {
            // Get the index of the element which is in the middle
            int middle = low + (high - low) / 2;

            if (stopRecursionIfSorted) {
                if (!isSorted(arr, low, middle)) {
                    if (useInsertSortForShorties && (middle - low < 100)) {
                        insertSort(arr, low, middle);
                    } else {
                        // don't useInsertSortForShorties
                        // Sort the left side of the array
                        mergeSort(arr, low, middle);
                    }
                }

                if (!isSorted(arr, middle + 1, high)) {
                    if (useInsertSortForShorties && (high - (middle + 1) < 100)) {
                        insertSort(arr, middle + 1, high);
                    } else {
                        // don't useInsertSortForShorties
                        // Sort the right side of the array
                        mergeSort(arr, middle + 1, high);
                    }
                }
            } else {
                if (useInsertSortForShorties && (middle - low < 100)) {
                    insertSort(arr, low, middle);
                } else {
                    // don't useInsertSortForShorties
                    // Sort the left side of the array
                    mergeSort(arr, low, middle);
                }

                if (useInsertSortForShorties && (high - (middle + 1) < 100)) {
                    insertSort(arr, middle + 1, high);
                } else {
                    // don't useInsertSortForShorties
                    // Sort the right side of the array
                    mergeSort(arr, middle + 1, high);
                }
            }

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
            t = 2 * s;
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

    private static void quickSort(int[] arr, int low, int high) {
        int i = low, j = high;
        int pivot;

        // Get the pivot element
        if (medianQuickSort) {
            int lo = arr[low];
            int md = arr[(high + low) / 2];
            int hi = arr[high];

            if (lo > md) {
                if (md > hi) {
                    pivot = md;
                } else if (lo > hi) {
                    pivot = hi;
                } else {
                    pivot = lo;
                }
            } else {
                if (lo > hi) {
                    pivot = lo;
                } else if (md > hi) {
                    pivot = hi;
                } else {
                    pivot = md;
                }
            }
        } else {
            pivot = arr[(high + low) / 2];
        }

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
        if (stopRecursionIfSorted) {
            if (low < j)
                if (!isSorted(arr, low, j)) {
                    if (useInsertSortForShorties && (j - low < 100)) {
                        insertSort(arr, low, j);
                    } else {
                        quickSort(arr, low, j);
                    }
                }
            if (i < high)
                if (!isSorted(arr, i, high)) {
                    if (useInsertSortForShorties && (high - i < 100)) {
                        insertSort(arr, i, high);
                    } else {
                        quickSort(arr, i, high);
                    }
                }
        } else {
            // Recursion
            if (low < j)
                if (useInsertSortForShorties && (j - low < 100)) {
                    insertSort(arr, low, j);
                } else {
                    quickSort(arr, low, j);
                }
            if (i < high)
                if (useInsertSortForShorties && (high - i < 100)) {
                    insertSort(arr, i, high);
                } else {
                    quickSort(arr, i, high);
                }
        }
    }

    private static int[] getRandomArray(int size, int max) {
        int[] arr = new int[size];

        for (int i = 0; i < size; i++) {
            arr[i] = randomGenerator.nextInt(max) + 1;
        }

        return arr;
    }

    private static int[] getNearlySortedArray(int size, int max, int numInversions) {
        int[] arr = getRandomArray(size, max);
        Arrays.sort(arr);

        for (int i = 0; i < numInversions; i++) {
            int j  = randomGenerator.nextInt(size);
            int k  = randomGenerator.nextInt(size);
            exchange(arr, j, k);
        }

        return arr;
    }

    private static int[] getReversedArray(int size, int max) {
        int[] arr = getRandomArray(size, max);
        Arrays.sort(arr);

        for (int i = 0; i < size / 2; i++) {
            int temp = arr[i];
            arr[i] = arr[size - 1 - i];
            arr[size - 1 - i] = temp;
        }

        return arr;
    }

    private static void runStdMergeSorts(int[][] arrays, int size) {
        int[] arrayToSort = new int[size];
        tempArr = new int[size];
        long start;
        long totalTime = 0;

        for (int[] arr : arrays) {
            System.arraycopy(arr, 0, arrayToSort, 0, size);
            System.arraycopy(arr, 0, tempArr, 0, size);

            // printArray(arrayToSort, "before StdMergeSort");
            start = System.currentTimeMillis();
            mergeSort(arrayToSort, 0, size - 1);
            totalTime += (System.currentTimeMillis() - start);
            printArray(arrayToSort, "after StdMergeSort");
        }

        System.out.println(String.format("Average standard merge sort time = %d ms", (totalTime / arrays.length)));
    }

    private static void runBUMergeSorts(int[][] arrays, int size) {
        int[] arrayToSort = new int[size];
        tempArr = new int[size];
        long start;
        long totalTime = 0;

        for (int[] arr : arrays) {
            System.arraycopy(arr, 0, arrayToSort, 0, size);
            System.arraycopy(arr, 0, tempArr, 0, size);

            // printArray(arrayToSort, "before BUMergeSort");
            start = System.currentTimeMillis();
            bottomUpMergeSort(arrayToSort);
            totalTime += (System.currentTimeMillis() - start);
            printArray(arrayToSort, "after BUMergeSort");
        }

        System.out.println(String.format("Average bottom-up merge sort time = %d ms", (totalTime / arrays.length)));
    }

    private static void runQuickSorts(int[][] arrays, int size) {
        int[] arrayToSort = new int[size];
        long start;
        long totalTime = 0;

        for (int[] arr : arrays) {
            System.arraycopy(arr, 0, arrayToSort, 0, size);

            start = System.currentTimeMillis();
            // printArray(arrayToSort, "before quickSort");
            quickSort(arrayToSort, 0, size - 1);
            totalTime += (System.currentTimeMillis() - start);
            printArray(arrayToSort, "after quickSort");
            //System.out.println(isSorted(arrayToSort, 0, size - 1));
        }

        System.out.println(String.format("Average quick sort time = %d ms", (totalTime / arrays.length)));
    }

    private static void runBuiltInSorts(int[][] arrays, int size) {
        int[] arrayToSort = new int[size];
        long start;
        long totalTime = 0;

        for (int[] arr : arrays) {
            System.arraycopy(arr, 0, arrayToSort, 0, size);

            // printArray(arrayToSort, "before builtinSort");
            start = System.currentTimeMillis();
            Arrays.sort(arrayToSort);
            totalTime += (System.currentTimeMillis() - start);
            printArray(arrayToSort, "after builtinSort");
        }

        System.out.println(String.format("Average built-in sort time = %d ms", (totalTime / arrays.length)));
    }

    private static void runHeapSorts(int[][] arrays, int size) {
        int[] arrayToSort = new int[size];
        long start;
        long totalTime = 0;

        for (int[] arr : arrays) {
            System.arraycopy(arr, 0, arrayToSort, 0, size);

            // printArray(arrayToSort, "before heapsorts");
            start = System.currentTimeMillis();
            heapSort(arrayToSort);
            totalTime += (System.currentTimeMillis() - start);
            printArray(arrayToSort, "after heapsorts");
        }

        System.out.println(String.format("Average heap sort time = %d ms", (totalTime / arrays.length)));
    }

    private static void runInsertionSorts(int[][] arrays, int size) {
        int[] arrayToSort = new int[size];
        long start;
        long totalTime = 0;

        for (int[] arr : arrays) {
            System.arraycopy(arr, 0, arrayToSort, 0, size);

            // printArray(arrayToSort, "before insertionSort");
            start = System.currentTimeMillis();
            insertionSort(arrayToSort);
            totalTime += (System.currentTimeMillis() - start);
            printArray(arrayToSort, "after insertionSort");
        }

        System.out.println(String.format("Average insertion sort time = %d ms", (totalTime / arrays.length)));
    }

    public static void main(String[] args) {
        randomGenerator = new Random();
        int numArrays = 10;

        // problem1(numArrays);
        problem2(numArrays);
        // problem3(numArrays);
        // problem4(numArrays);
        // problem5(numArrays);
    }

    private static void problem1(int numArrays) {
        System.out.println("\nRunning problem 1...\n");

        int[] numIntsOpts = {1000000, 2000000, 4000000};
        int[] intRangeOpts = {1000, 1000000};

        for (int intRange : intRangeOpts) {
            for (int numInts : numIntsOpts) {
                // Generate arrays to sort
                System.out.println(String.format("Generating %d sets of %d ints from 1 - %d", numArrays, numInts, intRange));
                int[][] arrays = new int[numArrays][numInts];

                for (int[] arr : arrays) {
                    System.arraycopy(getRandomArray(numInts, intRange), 0, arr, 0, numInts);
                }

                // Sort arrays with standard merge sort
                numComparisons = 0;
                runStdMergeSorts(arrays, numInts);
                System.out.println(String.format("Average number of comparisons = %d", numComparisons / numInts));

                // Sort arrays with bottom-up merge sort
                numComparisons = 0;
                runBUMergeSorts(arrays, numInts);
                System.out.println(String.format("Average number of comparisons = %d", numComparisons / numInts));
                System.out.println();
            }
        }
    }

    private static void problem2(int numArrays) {
        System.out.println("\nRunning problem 2...\n");

        int[] numIntsOpts = {1000000, 2000000, 4000000};
        int[] intRangeOpts = {1000, 1000000};
        //int[] numIntsOpts = {200};
        //int[] intRangeOpts = {1000};

        for (int intRange : intRangeOpts) {
            for (int numInts : numIntsOpts) {
                // Generate arrays to sort
                System.out.println(String.format("Generating %d sets of %d ints from 1 - %d", numArrays, numInts, intRange));
                int[][] arrays = new int[numArrays][numInts];

                for (int[] arr : arrays) {
                    System.arraycopy(getRandomArray(numInts, intRange), 0, arr, 0, numInts);
                }

                // Run algorithms for base case
                System.out.println("Running algorithms for base case");
                stopRecursionIfSorted = false;
                useInsertSortForShorties = false;
                runStdMergeSorts(arrays, numInts);
                runQuickSorts(arrays, numInts);

 // this one fails
                // Run algorithms for isSorted but not insertSort
                System.out.println("\nRunning algorithms for isSorted check on sub-arrays");
                stopRecursionIfSorted = true;
                useInsertSortForShorties = false;
                runStdMergeSorts(arrays, numInts);
                runQuickSorts(arrays, numInts);

// this one works:
                // Run algorithms for insertSort but not isSorted
                System.out.println("\nRunning algorithms for insertSort for sub-arrays < 100 in length");
                stopRecursionIfSorted = false;
                useInsertSortForShorties = true;
                runStdMergeSorts(arrays, numInts);
                runQuickSorts(arrays, numInts);

                // Run algorithms for both isSorted and insertSort
                System.out.println("\nRunning algorithms for both isSorted check and insertSort on sub-arrays");
                stopRecursionIfSorted = true;
                useInsertSortForShorties = true;
                runStdMergeSorts(arrays, numInts);
                runQuickSorts(arrays, numInts);

                System.out.println();
            }
        }
    }

    public static void problem3(int numArrays) {
        System.out.println("\nRunning problem 3...\n");

        int[] numIntsOpts = {1000000, 2000000, 4000000};
        int[] intRangeOpts = {1000, 1000000};

        for (int intRange : intRangeOpts) {
            for (int numInts : numIntsOpts) {
                // Generate arrays to sort
                System.out.println(String.format("Generating %d sets of %d ints from 1 - %d", numArrays, numInts, intRange));
                int[][] arrays = new int[numArrays][numInts];

                for (int[] arr : arrays) {
                    System.arraycopy(getRandomArray(numInts, intRange), 0, arr, 0, numInts);
                }

                System.out.print("Using median pivot best-case quick sort: ");
                medianQuickSort = true;
                stopRecursionIfSorted = true;
                useInsertSortForShorties = false;
                runQuickSorts(arrays, numInts);

                runBuiltInSorts(arrays, numInts);

                runHeapSorts(arrays, numInts);

                stopRecursionIfSorted = false;
                useInsertSortForShorties = true;
                runStdMergeSorts(arrays, numInts);

                System.out.print("Using middle pivot best-case quick sort: ");
                medianQuickSort = false;
                stopRecursionIfSorted = true;
                useInsertSortForShorties = false;
                runQuickSorts(arrays, numInts);

                System.out.println();
            }
        }
    }

    public static void problem4(int numArrays) {
        System.out.println("\nRunning problem 4...\n");

        int numInversions = 100;
        int numInts = 2000000;
        int[] intRangeOpts = {10000, 100000, 1000000};

        for (int intRange : intRangeOpts) {
            System.out.println(String.format("Generating %d sets of nearly-sorted arrays with %d ints from 1 - %d", numArrays, numInts, intRange));
            int[][] arrays = new int[numArrays][numInts];

            for (int[] arr : arrays) {
                System.arraycopy(getNearlySortedArray(numInts, intRange, numInversions), 0, arr, 0, numInts);
            }

            medianQuickSort = false;
            stopRecursionIfSorted = true;
            useInsertSortForShorties = false;
            runQuickSorts(arrays, numInts);

            runInsertionSorts(arrays, numInts);
        }
    }

    public static void problem5(int numArrays) {
        System.out.println("\nRunning problem 5...\n");

        int numInts = 2000000;
        int[] intRangeOpts = {10000, 100000, 1000000};

        for (int intRange : intRangeOpts) {
            System.out.println(String.format("Generating %d sets of reversed sorted arrays with %d ints from 1 - %d", numArrays, numInts, intRange));
            int[][] arrays = new int[numArrays][numInts];

            for (int[] arr : arrays) {
                System.arraycopy(getReversedArray(numInts, intRange), 0, arr, 0, numInts);
            }

            medianQuickSort = false;
            stopRecursionIfSorted = false;
            useInsertSortForShorties = false;
            runQuickSorts(arrays, numInts);

            runHeapSorts(arrays, numInts);
        }
    }
}
