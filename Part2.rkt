#lang racket
#|
   CSI 2120 - Programming Paradigms [A]
   Mounir Aït Hamou  -  300296173
   Aroha Upreti      -  300283790
   
   References:
   https://stackoverflow.com/questions/16842811/racket-how-to-retrieve-the-path-of-the-running-file

   Given a histogram file, we want to find the 5 most similar images to that histogram in a directory.
     - Compare the histogram of the query to each histogram in the directory
     - Output the 5 most similar images to the given query

|#
(require racket/runtime-path)



;; normalize-all-histograms
;; Normalizes a list of histograms by using normalize-histogram on each.
;; Parameters:
;;   list files - list of histograms to be normalized.
;; Output:
;;   Normalized histograms as a list.
(define (normalize-all-histograms files)
  (map (lambda (histogram) (normalize-histogram histogram)) files)) ;uses normalize-histogram on all members of the list
                                                                    ;which represents all the histograms in the directory
;; normalize-histogram
;; Normalizes a histogram by dividing each element by the total count.
;; Parameters:
;;   list histogram - A histogram in the form of a list.
;; Output:
;;   Normalized histograms as a list.
(define (normalize-histogram histogram)
  (define total (apply + histogram)) ; Calculate the total count of pixels in the histogram
  (map (lambda (count) (/ count total)) histogram));divide each element by the total count


;; hist->list
;; Reads a histogram file and returns its contents as a list.
;; Parameters:
;;   string fileName - the name of the file to be read.
;; Output:
;;   List of numbers representing the histogram.
(define (hist->list fileName)
  ;; Opens a text file and reads its contents.
  ;; Returns all numbers in a list.
  ;; Parameters:
  ;;   string fileName - the name of the file to be read.
  (define (readl port)
    (let ((num (read port)))
      (if (eof-object? num)
          '()
          (cons num (readl port)))))
  
  (let ((port (open-input-file fileName)))
    (let ((result (readl port)))
      (close-input-port port)
      (cdr result))))


;; process-files
;; Reads and processes all files in a list of file names.
;; Parameters:
;;   list files - list of file names to be processed.
;; Output:
;;   Processed files as a list of histograms.
(define (process-files files)
  ;reads all files in a list of name of files
  (if (null? files)
      '() 
      (cons (hist->list (car files)) ;makes a histogram from a file name and cons with the rest
              (process-files (cdr files)))))



;; get-all-files
;; Retrieves all files in a directory, normalizes their histograms, and returns as a list.
;; Parameters:
;;   string directory - directory path containing the files.
;; Output:
;;   List of pairs containing file names and their normalized histograms.
(define (get-all-files directory)
  (define files (directory-list directory));gives a list of files in the form of their filepath
  (let ((full-paths (map (lambda (file) (build-path directory file)) files)));replace all files by the full path by using the directory name and the file name
    (map list (map path->string full-paths) (normalize-all-histograms (process-files (map path->string full-paths))))));we take all the paths, turn them to a list of strings,
                                                                                                                       ;we take all histograms, which we use the list of strings of paths to create
                                                                                                                       ;we make a list which contains all the normalized histograms in a list with the name of
                                                                                                                       ;the file that they represent.
                                                                                                                       ;the list looks like this:
                                                                                                                       ;'((("fullFilePath1") (x11 x12 x13 ... x1n)) (("fullFilePath2") (x21 x22 x23 ... x2n)) ... (("fullFilePathm") (xm1 xm2 xm3 ... xmn)))

#| Computes images that are similar to the query and displays it properly.
 #  Returns nothing
 #  Parameters:
 #    string queryHistogramFilename - the query to be compared with
 #    string imageDataSetDirectory - the directory containing the images to be compared
|# 
(define (similaritySearch queryImageName imageDatasetDirectory)

  (define queryHistogramFilename (string-append "queryImages\\" queryImageName ".txt"));the filepath becomes queryImages\\filename.txt

  (define queryHistogram (hist->list queryHistogramFilename));we get a histogram of that filepath
  
  
  (define normalizedQueryHistogram (normalize-histogram queryHistogram));we normalize the histogram
  
  
  (define datasetHistograms (get-all-files imageDatasetDirectory));we get all histograms in the dataset accompanied with their file name
  

  (define similarities
    (map (lambda (fileInfo);fileInfo is each list that contains a file name and a histogram
           (list (car fileInfo);car fileInfo will give the file name
                 (compute-similarity normalizedQueryHistogram (cadr fileInfo))));cadr fileInfo will give the histogram
         datasetHistograms));we make a list of all file names accompanied with how similar they are to the query image
  ;list example:
  ;'((("fileName1") (c1)) (("fileName2") (c2)) ... (("fileNameN") (cN))) 
  
  
  (define sortedSimilarities (sort similarities (lambda (a b) (> (cadr a) (cadr b)))));sort the histogram by how high their comparison with the query image is in descending order


  (clean-output (take sortedSimilarities 5)));take the first 5 list which contains a filename and how similar it is to the query, then output it properly


; compute-similarity
; Computes the similarity between two histograms.
; Parameters:
;   list hist1 - the first histogram.
;   list hist2 - the second histogram.
; Output:
;   The similarity score between the two histograms.
(define (compute-similarity hist1 hist2)
  (define (min-element x y)
    (if (< x y) x y));helper function that just compares two elements and returns the lowest
  
  (define lowestelemlist
    (map min-element hist1 hist2));makes a list which contains the lowest element between hist1 hist2 at every position
  
  (apply + lowestelemlist));returns the sum of all the elements contained in the list which contains the lowest elements

; clean-output
; Displays 5 most similar images in order and properly formatted
; Parameters:
;   list L - a list of the 5 most similar images to the queryImage 
; Output:
;   Nothing
(define (clean-output L)
  (displayln "5 closest images:")
  (define (print-entry entry counter)
    (when (not (null? entry))
      (let ((filename (car entry))
            (similarity (cadr (car entry))))
        (display counter)
        (display ". ")
        (let ((picture-number (substring (car filename) 20 (- (string-length (car filename)) 8))));generate picture number
          (display picture-number))
        (display ", Similarity of ")
        (display similarity)
        (newline)
        (print-entry (cdr entry) (+ counter 1)))))
  
  (print-entry L 1))
(similaritySearch "q07" "imageDataset2_15_20")