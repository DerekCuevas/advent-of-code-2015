(ns day-four.core
  (:require [digest :refer [md5]])
  (:gen-class))

(defn- starts-with-at-least-five-zeros? [s]
  (and (>= (count s) 5)
       (= (subs s 0 5) "00000")))

(defn find-smallest [key]
  (loop [x 0]
    (if (starts-with-at-least-five-zeros? (md5 (str key x)))
      x
      (recur (inc x)))))
