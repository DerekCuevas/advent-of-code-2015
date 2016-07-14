(ns not-quite-lisp.core
  (:gen-class))

(defn travel [directions]
  (->> directions
       (map #(case % \( 1 \) -1))
       (reduce +)))

(defn travel-to-basement [directions]
  (reduce-kv
   (fn [floor position direction]
     (let [next-floor (case direction \( (inc floor) \) (dec floor))]
       (if (= next-floor -1)
         (reduced (inc position))
         next-floor)))
   0
   (vec directions)))
