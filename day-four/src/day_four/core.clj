(ns day-four.core
  (:require [digest :refer [md5]])
  (:gen-class))

(defn- starts-with-at-least-n-zeros? [n s]
  (and (>= (count s) n)
       (= (subs s 0 n) (apply str (repeat n "0")))))

(defn- find-smallest-md5-with-n-zeros [n key]
  (loop [x 0]
    (if (starts-with-at-least-n-zeros? n (md5 (str key x)))
      x
      (recur (inc x)))))

(def find-smallest-md5-with-five-zeros (partial find-smallest-md5-with-n-zeros 5))
(def find-smallest-md5-with-six-zeros (partial find-smallest-md5-with-n-zeros 6))
