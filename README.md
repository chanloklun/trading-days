# Trading Day Generator

I keep an Excel file that tracks the daily closing value of S&P 500.  The
spreadsheet has several columns and one of them is date.  I usually pre-
populate it with trading days of the current year in advance and then just
fill out the S&P closing value each day.  I had a Java program that I used
previously that prints out all trading days for the year.  I would then take
this output file and just load it into Excel as my starting point.  Recently
I have been learning Clojure, and I think it would be a great idea to practice
writing Clojure by implementing what I had in Java with Clojure.  So, here it 
is.

To run the program, you'll need to find the constant THIS-YEAR and change
it to the year you want.  Next, find out the holidays of the stock exchange
for that year and populate the Clojure set named HOLIDAYS.  Finally, just
run the program with `clj src/main.clj` and look for the output file 
`output.txt` in the same directory you run the program.

Enjoy!