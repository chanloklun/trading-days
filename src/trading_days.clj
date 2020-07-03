; Clojure program to generate a output.txt plain text file
; having a trading day on each line.
;

(import (java.time LocalDate DayOfWeek))

; Change the value of THIS-YEAR to the year you want,
; then fill in the HOLIDAYS set with exchange holidays
; for the year you want
(def THIS-YEAR 2021)
(def HOLIDAYS #{(LocalDate/of 2021 1 1)
                (LocalDate/of 2021 1 18)
                (LocalDate/of 2021 2 15)
                (LocalDate/of 2021 4 2)
                (LocalDate/of 2021 5 31)
                (LocalDate/of 2021 7 5)
                (LocalDate/of 2021 9 6)
                (LocalDate/of 2021 11 25)
                (LocalDate/of 2021 12 24)})

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