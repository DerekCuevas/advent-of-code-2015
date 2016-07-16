(ns day-four.core
  (:require [clojure.string :refer [starts-with?]]
            [digest :refer [md5]])
  (:gen-class))

(defn- starts-with-n-zeros? [n s]
  (starts-with? s (apply str (repeat n "0"))))

(defn- find-smallest-md5 [n key]
  (loop [x 0]
    (if (starts-with-n-zeros? n (md5 (str key x)))
      x
      (recur (inc x)))))

(def find-smallest-md5-with-five-zeros (partial find-smallest-md5 5))
(def find-smallest-md5-with-six-zeros (partial find-smallest-md5 6))
