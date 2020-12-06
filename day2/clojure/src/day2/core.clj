(ns day2.core)

(use
 'clojure.java.io)

(defn is-valid
  [password]
  true)

(defn validate-line [line]
  (def matches (re-find #"(\d+)-(\d+) (\w): (\w+)" line))
  (def password
    {:value           (nth matches 4)
     :character       (nth matches 3)
     :min-occurrences (nth matches 1)
     :max-occurrences (nth matches 2)})
  (is-valid password))

(defn -main [& args]
  (with-open [rdr (reader (clojure.java.io/resource "day2.txt"))]
    (def validations
      (map validate-line (line-seq rdr)))
    (println validations)))
