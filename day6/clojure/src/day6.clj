(ns day6)

(use
 'clojure.java.io
 'clojure.set)

(defn count-occurrence [group, reductor]
  (count (reduce reductor group)))

(defn load-groups [string]
  (for [group (clojure.string/split string #"\n\n")]
    (for [answer (clojure.string/split (clojure.string/trim group) #"\n")]
      (set (clojure.string/split answer #"")))))

(defn sum-by [groups group-reductor]
  (reduce + (for [group groups] (count-occurrence group group-reductor))))

(defn -main [& args]
  (def file-contents (slurp (clojure.java.io/resource "day6.txt")))
  (def groups (load-groups file-contents))
  (println (sum-by groups clojure.set/union))
  (println (sum-by groups clojure.set/intersection)))
