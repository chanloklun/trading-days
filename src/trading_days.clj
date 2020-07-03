; Clojure program to generate a output.txt plain text file
; having a trading day on each line.
;

(import (java.time LocalDate DayOfWeek))

; Change the value of THIS-YEAR to the year you want,
; then fill in the HOLIDAYS set with exchange holidays
; for the year you want
(def THIS-YEAR 2020)
(def HOLIDAYS #{(LocalDate/of 2020 1 1)
                (LocalDate/of 2020 1 20)
                (LocalDate/of 2020 2 17)
                (LocalDate/of 2020 4 10)
                (LocalDate/of 2020 5 25)
                (LocalDate/of 2020 7 3)
                (LocalDate/of 2020 9 7)
                (LocalDate/of 2020 11 26)
                (LocalDate/of 2020 12 25)})

(defn holiday? [day] (contains? HOLIDAYS day))
(defn weekend? [day]
  (let [dayOfWeek (.getDayOfWeek day)]
    (or (= dayOfWeek DayOfWeek/SATURDAY)
        (= dayOfWeek DayOfWeek/SUNDAY))))
(defn trading-day? [day] (and (not (holiday? day))
                              (not (weekend? day))))

(defn days-in-year-gen [year]
  (fn days-in-year
    ([] (days-in-year (LocalDate/of year 1 1)))
    ([day] (if (= (.getYear day) year)
             (lazy-seq (cons day (days-in-year (.plusDays day 1))))))))

(def days-in-this-year ((days-in-year-gen THIS-YEAR)))

(def trading-days-this-year (filter trading-day? days-in-this-year))

(with-open [w (clojure.java.io/writer "output.txt")]
  (doseq [day trading-days-this-year]
    (.write w (str day))
    (.newLine w)))