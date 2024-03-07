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

   1. Create a function to read from the file that returns a list (DONE)
   2.

|#
(require racket/runtime-path)

(define (normalize-all-histograms files)
  (map (lambda (histogram) (normalize-histogram histogram)) files))

(define (normalize-histogram histogram)
  (define total (apply + histogram)) ; Calculate the total count of pixels in the histogram
  (map (lambda (count) (/ count total)) histogram));divide each element by the total count

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

(define (process-files files)
  ;reads all files in a list of name of files
  (if (null? files)
      '() 
      (cons (hist->list (car files)) 
              (process-files (cdr files)))))

(define (get-all-files directory)
  (define files (directory-list directory))
  (let ((full-paths (map (lambda (file) (build-path directory file)) files)))
    (map list (map path->string full-paths) (normalize-all-histograms (process-files (map path->string full-paths))))))

#| Computes images that are similar to the query.
 #  Returns the 5 most similar images.
 #  Parameters:
 #    string queryHistogramFilename - the query to be compared with
 #    string imageDataSetDirectory - the directory containing the images to be compared
|# 
(define (similaritySearch queryImageName imageDatasetDirectory)
  (define queryHistogramFilename (string-append "queryImages\\" queryImageName ".txt"))
  (define queryHistogram (hist->list queryHistogramFilename))
  
  
  (define normalizedQueryHistogram (normalize-histogram queryHistogram))
  
  
  (define datasetHistograms (get-all-files imageDatasetDirectory))
  
  
  (define similarities
    (map (lambda (fileInfo)
           (list (car fileInfo)
                 (compute-similarity normalizedQueryHistogram (cadr fileInfo))))
         datasetHistograms))
  
  
  (define sortedSimilarities (sort similarities (lambda (a b) (> (cadr a) (cadr b)))))


  (clean-output (take sortedSimilarities 5)))
(define (compute-similarity hist1 hist2)
  (define (min-element x y)
    (if (< x y) x y))
  
  (define similarities
    (map min-element hist1 hist2))
  
  (apply + similarities))
(define (clean-output L)
  (displayln "5 closest images:")
  (define (print-entry entry counter)
    (when (not (null? entry))
      (let ((filename (car entry))
            (similarity (cadr (car entry))))
        (display counter)
        (display ". ")
        (let ((picture-number (substring (car filename) 20 (- (string-length (car filename)) 8))))
          (display picture-number))
        (display ", Similarity of ")
        (display similarity)
        (newline)
        (print-entry (cdr entry) (+ counter 1)))))
  
  (print-entry L 1))
(similaritySearch "q00" "imageDataset2_15_20")