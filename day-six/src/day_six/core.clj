(ns day-six.core
  (:gen-class))

(defn grid [n init]
  (vec (for [x (range n)]
         (vec (repeat n init)))))
