; Clojure program to generate an output.txt plain text file
; having a trading day on each line.
;

(import (java.time LocalDate DayOfWeek))

; Change the value of THIS-YEAR to the year you want,
; then fill in the HOLIDAYS set with exchange holidays
; for the year you want
(def THIS-YEAR 2024)
(def HOLIDAYS #{(LocalDate/of 2024 1 1)
                (LocalDate/of 2024 1 15)
                (LocalDate/of 2024 2 19)
                (LocalDate/of 2024 3 29)
                (LocalDate/of 2024 5 27)
                (LocalDate/of 2024 6 19)
                (LocalDate/of 2024 7 4)
                (LocalDate/of 2024 9 2)
                (LocalDate/of 2024 11 28)
                (LocalDate/of 2024 12 25)})

(defn holiday? [day] (contains? HOLIDAYS day))
(defn weekend? [day]
  (let [dayOfWeek (.getDayOfWeek day)]
    (or (= dayOfWeek DayOfWeek/SATURDAY)
        (= dayOfWeek DayOfWeek/SUNDAY))))
(defn trading-day? [day] (and (not (holiday? day))
                              (not (weekend? day))))

; Given a year, returns a function that would return a sequence of
; days within that year.  This way I don't need to tie the logic of
; generating the day sequence to a particular year.
(defn days-in-year-gen [year]
  (fn days-in-year
    ([] (days-in-year (LocalDate/of year 1 1)))
    ([day] (if (= (.getYear day) year)  ; have we crossed into next year yet?
             (lazy-seq (cons day (days-in-year (.plusDays day 1))))))))

(def days-in-this-year ((days-in-year-gen THIS-YEAR)))

(def trading-days-this-year (filter trading-day? days-in-this-year))

(with-open [w (clojure.java.io/writer "output.txt")]
  (doseq [day trading-days-this-year]
    (.write w (str day))
    (.newLine w)))
